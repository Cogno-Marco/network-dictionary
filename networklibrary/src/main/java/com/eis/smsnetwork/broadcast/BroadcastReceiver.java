package com.eis.smsnetwork.broadcast;

import android.util.Log;

import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSInvitation;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.SMSNetSubscriberList;
import com.eis.smsnetwork.smsnetcommands.SMSAddPeer;

import java.util.ArrayList;

/**
 * @author Marco Cognolato, Giovanni Velludo, Enrico Cestaro
 */
public class BroadcastReceiver extends SMSReceivedServiceListener {

    private static final int NUM_OF_REQUEST_FIELDS = 1;
    public static final String FIELD_SEPARATOR = "¤";
    static final String SEPARATOR_REGEX = "(?<!\\\\)" + FIELD_SEPARATOR;


    /**
     * Receives messages from other peers in the network and acts according to the content of those
     * messages. Messages are composed of different fields, separated by
     * {@link BroadcastReceiver#FIELD_SEPARATOR}. {@link BroadcastReceiver#FIELD_SEPARATOR}s
     * preceded by a backslash do not separate fields.
     * Field 0 contains the {@link RequestType} of the request contained in this message.
     * The rest of the message varies depending on the {@link RequestType}:
     * <ul>
     * <li>Invite: there are no other fields</li>
     * <li>AcceptInvitation: there are no other fields</li>
     * <li>AddPeer: fields from 1 to the last one contain the phone numbers of each
     * {@link com.eis.smslibrary.SMSPeer} we have to add to our network</li>
     * <li>QuitNetwork: there are no other fields, because this request can only be sent by the
     * {@link com.eis.smslibrary.SMSPeer} who wants to be removed</li>
     * <li>AddResource: starting from 1, fields with odd numbers contain keys, their following (even)
     * field contains the corresponding value</li>
     * <li>RemoveResource: fields from 1 to the last one contain the keys to remove</li>
     * </ul>
     *
     * @param message The message passed by {@link com.eis.smslibrary.SMSReceivedBroadcastReceiver}.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        Log.d("BR_RECEIVER", "Message received: " + message.getPeer() + " " + message.getData());
        // To allow FIELD_SEPARATOR in keys and resources, we escape it with a backslash. Therefore
        // we only split the message when FIELD_SEPARATOR is not preceded by a backslash.
        String[] fields = message.getData().split(SEPARATOR_REGEX);
        RequestType request;
        try {
            request = RequestType.get(fields[0]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return;
        }
        if (request == null) return;

        SMSPeer sender = message.getPeer();

        SMSNetSubscriberList subscribers = (SMSNetSubscriberList) SMSJoinableNetManager.getInstance().getNetSubscriberList();
        SMSNetDictionary dictionary = (SMSNetDictionary) SMSJoinableNetManager.getInstance().getNetDictionary();
        boolean senderIsNotSubscriber = !subscribers.getSubscribers().contains(sender);

        switch (request) {
            case Invite: {
                if (fields.length > 1) return;
                SMSJoinableNetManager.getInstance().checkInvitation(new SMSInvitation(sender));
                break;
            }
            case AcceptInvitation: {
                if (fields.length > 1) return;
                //Verifying if the sender has been invited to join the network
                ArrayList<SMSPeer> invitedPeers = SMSJoinableNetManager.getInstance()
                        .getInvitedPeers();
                if (!invitedPeers.contains(sender))
                    return;
                invitedPeers.remove(sender);

                //Sending to the invited peer my subscribers list
                StringBuilder myNetwork = new StringBuilder(RequestType.AddPeer.asString() +
                        FIELD_SEPARATOR);
                for (SMSPeer peerToAdd : subscribers.getSubscribers())
                    myNetwork.append(peerToAdd).append(FIELD_SEPARATOR);
                SMSMessage myNetworkMessage = new SMSMessage(
                        sender, myNetwork.deleteCharAt(myNetwork.length() - 1).toString());
                SMSManager.getInstance().sendMessage(myNetworkMessage);

                //Sending to the invited peer my dictionary
                String myDictionary = RequestType.AddResource.asString() + FIELD_SEPARATOR +
                        dictionary.getAllKeyResourcePairsForSMS();
                SMSMessage myDictionaryMessage = new SMSMessage(sender, myDictionary);
                SMSManager.getInstance().sendMessage(myDictionaryMessage);

                //Broadcasting to the previous subscribers the new subscriber
                CommandExecutor.execute(new SMSAddPeer(sender, subscribers));
                //Updating my local subscribers list
                subscribers.addSubscriber(sender);
            }
            case AddPeer: {
                if (senderIsNotSubscriber) return;
                SMSPeer[] peersToAdd;
                try {
                    peersToAdd = new SMSPeer[fields.length - NUM_OF_REQUEST_FIELDS];
                } catch (NegativeArraySizeException e) {
                    return;
                }
                try {
                    for (int i = NUM_OF_REQUEST_FIELDS; i < fields.length; i++)
                        peersToAdd[i - NUM_OF_REQUEST_FIELDS] = new SMSPeer(fields[i]);
                } catch (InvalidTelephoneNumberException | ArrayIndexOutOfBoundsException e) {
                    return;
                }
                for (SMSPeer peer : peersToAdd)
                    subscribers.addSubscriber(peer);
                break;
            }
            case QuitNetwork: {
                if (fields.length > 1) return;
                try {
                    subscribers.removeSubscriber(sender);
                } catch (IllegalArgumentException e) {
                    return;
                }
                break;
            }
            case AddResource: {
                // if the number of fields is even, that means not every key will have a
                // corresponding value, so the message we received is garbage. For example, with 4
                // fields we'll have: requestType, key, value, key
                if (senderIsNotSubscriber || fields.length % 2 == 0) return;
                String[] keys;
                String[] values;
                try {
                    keys = new String[(fields.length - NUM_OF_REQUEST_FIELDS) / 2];
                    values = new String[keys.length];
                } catch (NegativeArraySizeException e) {
                    return;
                }
                try {
                    for (int i = 0, j = NUM_OF_REQUEST_FIELDS; j < fields.length; i++) {
                        keys[i] = fields[j++];
                        values[i] = fields[j++];
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                for (int i = 0; i < keys.length; i++) {
                    try {
                        dictionary.addResourceFromSMS(keys[i], values[i]);
                    } catch (IllegalArgumentException e) {
                        // this can only happen in the last element of values, because it's the only
                        // one that can contain a backslash
                        return;
                    }
                }
                break;
            }
            case RemoveResource: {
                if (senderIsNotSubscriber) return;
                try {
                    for (int i = NUM_OF_REQUEST_FIELDS; i < fields.length; i++)
                        dictionary.removeResourceFromSMS(fields[i]);
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    return;
                }
                break;
            }
        }
    }
}
