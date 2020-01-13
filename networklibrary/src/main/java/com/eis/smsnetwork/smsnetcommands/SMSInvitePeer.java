package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSInvitePeer extends com.eis.communication.network.commands.InvitePeer<SMSPeer> {

    /**
     * Constructor for the SMSInvitePeer command, requires data to work
     *
     * @param peerToInvite The SMSPeer to invite to the network
     */
    public SMSInvitePeer(@NonNull SMSPeer peerToInvite) {
        super(peerToInvite);
    }

    /**
     * Execute the SMSInvitePeer logic: sends a request to join a network
     */
    protected void execute() {
        String message = RequestType.Invite.asString();
        SMSMessage messageToSend = new SMSMessage(peerToInvite, message);
        SMSHandler.getInstance().sendMessage(messageToSend);
    }
}
