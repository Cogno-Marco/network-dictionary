package com.eis.smsnetwork;

import com.eis.communication.network.Invitation;
import com.eis.smslibrary.SMSPeer;

/**
 * Represents a received invitation to join a network.
 */
public class SMSInvitation implements Invitation<SMSPeer> {
    private SMSPeer inviter;

    public SMSInvitation(SMSPeer inviter) {
        this.inviter = inviter;
    }

    /**
     * Returns the {@link SMSPeer} who sent the Invitation to join a new network
     */
    @Override
    public SMSPeer getInviterPeer() {
        return inviter;
    }
}
