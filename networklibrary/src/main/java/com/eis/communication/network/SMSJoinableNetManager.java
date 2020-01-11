package com.eis.communication.network;

import com.eis.communication.network.listeners.JoinInvitationListener;
import com.eis.smslibrary.SMSPeer;

/**
 * Concrete JoinableNetwork for SMS Messages
 *
 * @author Marco Cognolato
 */
public class SMSJoinableNetManager extends SMSNetworkManager
        implements JoinableNetworkManager<String, String, SMSPeer, FailReason, Invitation<SMSPeer>> {

    /**
     * Accepts a given join invitation.
     * If a listener is NOT set (using {@link #setJoinInvitationListener(JoinInvitationListener)})
     * this method will be called automatically, else you should call this from the listener
     * if you want to accept an invitation
     * @param invitation The invitation previously received.
     */
    @Override
    public void acceptJoinInvitation(Invitation invitation) {
        //redirects the call to the acceptJoinInvitation in the parent class.
        super.acceptJoinInvitation(invitation);
    }

    /**
     * Sets a listener waiting for network invitations
     * @param joinInvitationListener Listener called upon invitation received.
     */
    @Override
    public void setJoinInvitationListener(JoinInvitationListener<Invitation<SMSPeer>> joinInvitationListener) {

    }
}
