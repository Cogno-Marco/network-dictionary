package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.Command;
import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;

public class InvitePeer implements Command {
    private SMSPeer peerToInvite;

    /**
     * Constructor for the InvitePeer command, requires data to work
     * @param peerToInvite The SMSPeer to invite to the network
     */
    public InvitePeer(@NonNull SMSPeer peerToInvite){
        this.peerToInvite = peerToInvite;
    }

    /**
     * Execute the InvitePeer logic: sends a request to join a network
     */
    public void execute(){
        String message = RequestType.Invite.asString();
        SMSMessage messageToSend = new SMSMessage(peerToInvite, message);
        SMSHandler.getInstance().sendMessage(messageToSend);
    }
}
