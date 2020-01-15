package com.eis.smsnetwork.broadcast;

import android.util.Log;

import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.AddPeer;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessageHandler;
import com.eis.smslibrary.SMSReceivedBroadcastReceiver;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSInvitation;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.eis.smsnetwork.smsnetcommands.SMSAddPeer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marco Cognolato, Giovanni Velludo, Enrico Cestaro
 */
public class BroadcastReceiver extends SMSReceivedServiceListener {
    //TODO: write tests

    private static final int NUM_OF_REQUEST_FIELDS = 1;
    public static final String FIELD_SEPARATOR = " ";

    /**
     * Receives messages from other peers in the network and acts according to the content of those
     * messages. Messages are composed of different fields, separated by spaces.
     * Field 0 contains the {@link RequestType} of the request contained in this message.
     * The rest of the message varies depending on the {@link RequestType}:
     * <ul>
     * <li>Invite: there are no other fields</li>
     * <li>AcceptInvitation: fields from 1 to the last one contain the phone numbers of each
     * * {@link com.eis.smslibrary.SMSPeer} subscriber of the network that merged with mine</li>
     * <li>AddPeer: fields from 1 to the last one contain the phone numbers of each
     * {@link com.eis.smslibrary.SMSPeer} we have to add to our network</li>
     * <li>RemovePeer: there are no other fields, because this request can only be sent by the
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
        String[] fields = message.getData().split(FIELD_SEPARATOR);
        RequestType request;
        try {
            request = RequestType.get(fields[0]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return;
        }
        if (request == null) return;

        SMSPeer sender = message.getPeer();

        NetSubscriberList<SMSPeer> subscribers = SMSJoinableNetManager.getInstance().getNetSubscriberList();
        NetDictionary<String, String> dictionary = SMSJoinableNetManager.getInstance().getNetDictionary();
        boolean senderIsNotSubscriber = !subscribers.getSubscribers().contains(sender);

        switch (request) {
            case Invite: {
                if (fields.length > 1) return;
                SMSJoinableNetManager.getInstance().checkInvitation(new SMSInvitation(sender));
                break;
            }
            case AcceptInvitation: {
                //Verifying if the sender has been invited to joi the network
                NetSubscriberList<SMSPeer> invitedPeers = SMSJoinableNetManager.getInstance()
                        .getInvitedPeers();
                if (invitedPeers.getSubscribers().contains(sender))
                    invitedPeers.removeSubscriber(sender);
                else return;

                //Sending to the invited peer my subscribers list
                StringBuilder myNetwork = new StringBuilder(RequestType.AddPeer.asString() + " ");
                for (SMSPeer peerToAdd : subscribers.getSubscribers())
                    myNetwork.append(peerToAdd).append(" ");
                SMSMessage myNetworkMessage = new SMSMessage(sender, myNetwork.toString());
                SMSManager.getInstance().sendMessage(myNetworkMessage);

                //Broadcasting to the previous subscribers the new subscriber
                CommandExecutor.execute(new SMSAddPeer(sender, subscribers));
                //Updating my local subscribers list
                subscribers.addSubscriber(sender);

                //TODO add the resources sending process


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
            case RemovePeer: {
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
                    dictionary.addResource(keys[i], values[i]);
                }
                break;
            }
            case RemoveResource: {
                if (senderIsNotSubscriber) return;
                try {
                    for (int i = NUM_OF_REQUEST_FIELDS; i < fields.length; i++)
                        dictionary.removeResource(fields[i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                break;
            }
        }
    }
}
