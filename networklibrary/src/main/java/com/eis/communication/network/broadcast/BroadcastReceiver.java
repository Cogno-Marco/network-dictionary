package com.eis.communication.network.broadcast;

import com.eis.communication.network.RequestType;
import com.eis.communication.network.SMSJoinableNetManager;
import com.eis.communication.network.SMSNetDictionary;
import com.eis.communication.network.SMSNetSubscribers;
import com.eis.communication.network.commands.AddPeer;
import com.eis.communication.network.commands.AddResource;
import com.eis.communication.network.commands.RemovePeer;
import com.eis.communication.network.commands.RemoveResource;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

/**
 * This class receives messages from other peers in the network and acts according to the content of
 * those messages. Messages are composed of different fields, separated by spaces.
 * Field 0 contains the {@link RequestType} of the request contained in this message.
 * The rest of the message varies depending on the {@link RequestType}:
 * <ul>
 * <li>Invite: there are no other fields</li>
 * <li>AcceptInvitation: there are no other fields</li>
 * <li>AddPeer: field 1 contains the phone number of the {@link com.eis.smslibrary.SMSPeer} we
 * have to add to our network</li>
 * <li>RemovePeer: there are no other fields, because this request can only be sent by the
 * {@link com.eis.smslibrary.SMSPeer} who wants to be removed</li>
 * <li>AddResource: field 1 contains the key, field 2 contains the value</li>
 * <li>RemoveResource: field 1 contains the key</li>
 * </ul>
 *
 * @author Marco Cognolato, Giovanni Velludo
 */
public class BroadcastReceiver extends SMSReceivedServiceListener {
    //TODO: write tests

    @Override
    public void onMessageReceived(SMSMessage message) {
        String[] fields = message.getData().split(" ");
        RequestType request = RequestType.values()[Integer.parseInt(fields[0])];
        SMSPeer sender;
        // TODO: reminder that the exception is not thrown in the latest version of the
        //  library, there's a method for checking the validity of the SMSPeer instead
        try {
            sender = message.getPeer();
        } catch (InvalidTelephoneNumberException e) {
            return;
        }
        SMSNetSubscribers subscribers = SMSJoinableNetManager.getInstance().getNetSubscribers();
        SMSNetDictionary dictionary = SMSJoinableNetManager.getInstance().getNetDictionary();
        boolean senderIsNotSubscriber = !subscribers.getSubscribers().contains(sender);
        switch (request) {
            case Invite:
                if (subscribers.getSubscribers().isEmpty()) {
                    // if there's nobody in my network, which means that I'm not in a network
                    // TODO: ask user if they want to join the network, then act accordingly
                } else {
                    // TODO: ask the user if they want to leave the current network and join the new
                    //  one
                }
                break;
            case AcceptInvitation:
                // TODO: check if we invited the peer, if yes then accept the invitation
                SMSJoinableNetManager.getInstance().acceptJoinInvitation(() -> sender);
                // TODO: broadcast AddPeer for the new peer, we should probably add a Command for
                //  the whole AcceptInvitation process
                break;
            case AddPeer:
                if (senderIsNotSubscriber) return;
                SMSPeer peerToAdd;
                // TODO: reminder that the exception is not thrown in the latest version of the
                //  library, there's a method for checking the validity of the SMSPeer instead
                try {
                    peerToAdd = new SMSPeer(fields[1]);
                } catch (InvalidTelephoneNumberException e) {
                    return;
                }
                new AddPeer(peerToAdd, subscribers).execute();
                break;
            case RemovePeer:
                new RemovePeer(sender, subscribers).execute();
                break;
            case AddResource:
                if (senderIsNotSubscriber) return;
                String key = fields[1];
                String value = fields[2];
                new AddResource(key, value, dictionary).execute();
                break;
            case RemoveResource:
                if (senderIsNotSubscriber) return;
                key = fields[1];
                new RemoveResource(key, dictionary).execute();
        }
    }
}