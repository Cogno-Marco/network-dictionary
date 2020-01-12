package com.eis.smsnetwork.broadcast;

import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.eis.smsnetwork.SMSNetInvitation;
import com.eis.smsnetwork.smsnetcommands.AddPeer;
import com.eis.smsnetwork.smsnetcommands.AddResource;
import com.eis.smsnetwork.smsnetcommands.RemovePeer;
import com.eis.smsnetwork.smsnetcommands.RemoveResource;

/**
 * This class receives messages from other peers in the network and acts according to the content of
 * those messages. Messages are composed of different fields, separated by spaces.
 * Field 0 contains the {@link RequestType} of the request contained in this message.
 * The rest of the message varies depending on the {@link RequestType}:
 * <ul>
 * <li>Invite: there are no other fields</li>
 * <li>AcceptInvitation: there are no other fields</li>
 * <li>AddPeer: fields from 1 to the last contain the phone numbers of each
 * {@link com.eis.smslibrary.SMSPeer} we have to add to our network</li>
 * <li>RemovePeer: there are no other fields, because this request can only be sent by the
 * {@link com.eis.smslibrary.SMSPeer} who wants to be removed</li>
 * <li>AddResource: starting from 1, fields with odd numbers contain keys, their following (even)
 * field contains the corresponding value</li>
 * <li>RemoveResource: fields from 1 to the last contain the keys to remove</li>
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
        NetSubscriberList<SMSPeer> subscribers = SMSJoinableNetManager.getInstance().getNetSubscriberList();
        NetDictionary<String, String> dictionary = SMSJoinableNetManager.getInstance().getNetDictionary();
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
                SMSNetInvitation netInvitation = new SMSNetInvitation(sender);
                SMSJoinableNetManager.getInstance().checkInvitation(netInvitation);
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
                CommandExecutor.execute(new AddPeer(peerToAdd, subscribers));
                break;
            case RemovePeer:
                CommandExecutor.execute(new RemovePeer(sender, subscribers));
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
                CommandExecutor.execute(new AddResource(key, value, dictionary));
                break;
            case RemoveResource:
                if (senderIsNotSubscriber) return;
                try {
                    key = fields[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                CommandExecutor.execute(new RemoveResource(key, dictionary));
        }
    }
}
