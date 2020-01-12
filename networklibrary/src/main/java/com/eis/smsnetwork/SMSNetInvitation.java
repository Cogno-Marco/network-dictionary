package com.eis.smsnetwork;

import androidx.annotation.NonNull;

import com.eis.communication.network.Invitation;
import com.eis.smslibrary.SMSPeer;

public class SMSNetInvitation implements Invitation<SMSPeer> {

    private SMSPeer invited;

    public SMSNetInvitation(@NonNull SMSPeer invited){
        this.invited = invited;
    }

    /**
     * @return The address of who sent this invitation.
     */
    public SMSPeer getInviterPeer(){
        return invited;
    }
}
