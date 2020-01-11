package com.eis.communication.network.broadcast;

import com.eis.communication.network.RequestType;
import com.eis.smslibrary.SMSMessage;
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
 * <li>RemoveResource: field 1 contains the key, field 2 contains the value</li>
 * </ul>
 */
public class BroadcastReceiver extends SMSReceivedServiceListener {

    @Override
    public void onMessageReceived(SMSMessage message) {

    }
}