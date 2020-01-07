package com.eis.communication.network;

import com.eis.communication.Peer;

/**
 * Listener for sent invitations to join the network.
 * <p>
 * Only used in {@link JoinableNetworkManager}
 *
 * @param <P> The type of address used.
 * @author Luca Crema
 */
public interface InviteListener<P extends Peer> {

    /**
     * Callback for successful sent invitation.
     *
     * @param invitedPeer Who has been invited.
     */
    void onInvitationSent(P invitedPeer);

    /**
     * Callback for failed sending of invitation.
     *
     * @param notInvitedPeer Who were to be invited.
     */
    void onInvitationNotSent(P notInvitedPeer);

}
