package com.eis.smsnetwork.broadcast;

import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.eis.smsnetwork.SMSNetInvitation;

import java.util.ArrayList;
import java.util.List;

/**
 * This class receives messages from other peers in the network and acts according to the content of
 * those messages. Messages are composed of different fields, separated by spaces.
 * Field 0 contains the {@link RequestType} of the request contained in this message.
 * The rest of the message varies depending on the {@link RequestType}:
 * <ul>
 * <li>Invite: there are no other fields</li>
 * <li>AcceptInvitation: fields from 1 to the last one contain the phone numbers of each
 *  * {@link com.eis.smslibrary.SMSPeer} subscriber of the network that merged with mine</li>
 * <li>AddPeer: fields from 1 to the last one contain the phone numbers of each
 * {@link com.eis.smslibrary.SMSPeer} we have to add to our network</li>
 * <li>RemovePeer: there are no other fields, because this request can only be sent by the
 * {@link com.eis.smslibrary.SMSPeer} who wants to be removed</li>
 * <li>AddResource: starting from 1, fields with odd numbers contain keys, their following (even)
 * field contains the corresponding value</li>
 * <li>RemoveResource: fields from 1 to the last one contain the keys to remove</li>
 * </ul>
 *
 * @author Marco Cognolato, Giovanni Velludo
 */
public class BroadcastReceiver extends SMSReceivedServiceListener {
    //TODO: write tests

    @Override
    public void onMessageReceived(SMSMessage message) {
        String[] fields = message.getData().split(" ");
        RequestType request;
        try {
            request = RequestType.get(fields[0]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return;
        }
        if (request == null) return;

        SMSPeer sender;
        try {
            sender = message.getPeer();
        } catch (InvalidTelephoneNumberException e) {
            return;
        }

        NetSubscriberList<SMSPeer> subscribers = SMSJoinableNetManager.getInstance().getNetSubscriberList();
        NetDictionary<String, String> dictionary = SMSJoinableNetManager.getInstance().getNetDictionary();
        boolean senderIsNotSubscriber = !subscribers.getSubscribers().contains(sender);

        switch (request) {
            case Invite:
                SMSNetInvitation netInvitation = new SMSNetInvitation(sender);
                SMSJoinableNetManager.getInstance().checkInvitation(netInvitation);
                break;

            case AcceptInvitation:
                // TODO: check if I invited the peer, if I did then accept the invitation
                //Creating the list of new members,
                List<SMSPeer> newMembers = new ArrayList<>();
                for (int i = 1; i < fields.length; i++) newMembers.add(new SMSPeer(fields[i]));
                String myNetwork = RequestType.AddPeer.asString() + " ";
                //Converting the list into a String
                for (SMSPeer peerToAdd : subscribers.getSubscribers())
                    myNetwork += peerToAdd + " ";
                //Broadcasting the new peers to add to my old subscribers list
                BroadcastSender.broadcastMessage(newMembers, myNetwork);

                //Obtaining the list of old subscribers, converting it into a String
                String newNetwork = RequestType.AddPeer.asString() + " ";
                for (SMSPeer peerToAdd : newMembers)
                    newNetwork += peerToAdd + " ";
                //Broadcasting my old peers to the new subscribers, so that they can add them
                BroadcastSender.broadcastMessage(subscribers.getSubscribers(), newNetwork);

                //Updating my local subscribers list
                for (SMSPeer peerToAddLocally : newMembers)
                    subscribers.addSubscriber(peerToAddLocally);
                break;

            case AddPeer:
                if (senderIsNotSubscriber) return;
                SMSPeer[] peersToAdd;
                try {
                    peersToAdd = new SMSPeer[fields.length - 1];
                } catch (NegativeArraySizeException e) {
                    return;
                }
                try {
                    for (int i = 1; i < fields.length; i++)
                        peersToAdd[i] = new SMSPeer(fields[i]);
                } catch (InvalidTelephoneNumberException | ArrayIndexOutOfBoundsException e) {
                    return;
                }
                for (SMSPeer peer : peersToAdd)
                    SMSJoinableNetManager.getInstance().getNetSubscriberList().addSubscriber(peer);
                break;

            case RemovePeer:
                SMSJoinableNetManager.getInstance().getNetSubscriberList().removeSubscriber(sender);
                break;

            case AddResource:
                // if the number of fields is even, that means not every key will have a
                // corresponding value, so the message we received is garbage. For example, with 4
                // fields we'll have: requestType, key, value, key
                if (senderIsNotSubscriber || fields.length % 2 == 0) return;
                String[] keys;
                String[] values;
                try {
                    keys = new String[(fields.length - 1) / 2];
                    values = new String[keys.length];
                } catch (NegativeArraySizeException e) {
                    return;
                }
                try {
                    for (int i = 0, j = 1; j < fields.length; i++) {
                        keys[i] = fields[j++];
                        values[i] = fields[j++];
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                for (int i = 0; i < keys.length; i++) {
                    SMSJoinableNetManager.getInstance().getNetDictionary()
                            .addResource(keys[i], values[i]);
                }
                break;

            case RemoveResource:
                if (senderIsNotSubscriber) return;
                try {
                    key = fields[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                dictionary.removeResource(key);
        }
    }
}
