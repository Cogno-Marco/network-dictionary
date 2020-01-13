package com.eis.smsnetwork.broadcast;

import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSHandler;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.eis.smsnetwork.SMSNetInvitation;
import com.eis.smsnetwork.smsnetcommands.SMSAddPeer;
import com.eis.smsnetwork.smsnetcommands.SMSAddResource;
import com.eis.smsnetwork.smsnetcommands.SMSRemovePeer;
import com.eis.smsnetwork.smsnetcommands.SMSRemoveResource;

import java.util.ArrayList;
import java.util.List;

/**
 * This class receives messages from other peers in the network and acts according to the content of
 * those messages. Messages are composed of different fields, separated by spaces.
 * Field 0 contains the {@link RequestType} of the request contained in this message.
 * The rest of the message varies depending on the {@link RequestType}:
 * <ul>
 * <li>Invite: there are no other fields</li>
 * <li>AcceptInvitation: there are no other fields</li>
 * <li>SMSAddPeer: fields from 1 to the last contain the phone numbers of each
 * {@link com.eis.smslibrary.SMSPeer} we have to add to our network</li>
 * <li>SMSRemovePeer: there are no other fields, because this request can only be sent by the
 * {@link com.eis.smslibrary.SMSPeer} who wants to be removed</li>
 * <li>SMSAddResource: starting from 1, fields with odd numbers contain keys, their following (even)
 * field contains the corresponding value</li>
 * <li>SMSRemoveResource: fields from 1 to the last contain the keys to remove</li>
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
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return;
        }
        if (request == null) return;

        SMSPeer sender;
        // TODO: reminder that the exception is not thrown in the latest version of the
        //  library, there's a method for checking the validity of the SMSPeer instead
        try {
            sender = message.getPeer();
        } catch (InvalidTelephoneNumberException e) {
            return;
        }

        BroadcastSender broadcastSender = new BroadcastSender();
        NetSubscriberList<SMSPeer> subscribers = SMSJoinableNetManager.getInstance().getNetSubscriberList();
        NetDictionary<String, String> dictionary = SMSJoinableNetManager.getInstance().getNetDictionary();
        boolean senderIsNotSubscriber = !subscribers.getSubscribers().contains(sender);

        switch (request) {
            case Invite:
                SMSNetInvitation netInvitation = new SMSNetInvitation(sender);
                SMSJoinableNetManager.getInstance().checkInvitation(netInvitation);
                break;
            case AcceptInvitation:
                // TODO: check if we invited the peer, if yes then accept the invitation
                List<SMSPeer> newMembers = new ArrayList<>();
                for(int i=1; i<fields.length; i++) newMembers.add(new SMSPeer(fields[i]));

                String myNetwork = RequestType.AddPeer.asString() + " ";
                for(SMSPeer peerToAdd : subscribers.getSubscribers())
                    myNetwork += peerToAdd + " ";
                BroadcastSender.broadcastMessage(newMembers, myNetwork);

                String newNetwork = RequestType.AddPeer.asString() + " ";
                for(SMSPeer peerToAdd : newMembers)
                    newNetwork += peerToAdd + " ";
                BroadcastSender.broadcastMessage(subscribers.getSubscribers(), newNetwork);
                break;
            case AddPeer:
                if (senderIsNotSubscriber) return;
                List<SMSPeer> peersToAdd = new ArrayList<>();
                // TODO: reminder that the exception is not thrown in the latest version of the
                //  library, there's a method for checking the validity of the SMSPeer instead
                try {
                    for (int i = 0; i < fields.length; i++) peersToAdd.add(new SMSPeer(fields[i]));
                } catch (InvalidTelephoneNumberException e) {
                    return;
                }
                for (SMSPeer peerToAdd : peersToAdd)
                    subscribers.addSubscriber(peerToAdd);
                break;
            case RemovePeer:
                if (senderIsNotSubscriber) return;
                SMSPeer peerToRemove;
                try {
                    peerToRemove = new SMSPeer(fields[1]);
                } catch (InvalidTelephoneNumberException e) {
                    return;
                }
                subscribers.removeSubscriber(peerToRemove);
                break;
            case AddResource:
                if (senderIsNotSubscriber) return;
                String key, value;
                try {
                    key = fields[1];
                    value = fields[2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                dictionary.addResource(key, value);
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
