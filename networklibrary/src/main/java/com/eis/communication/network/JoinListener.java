package com.eis.communication.network;

import com.eis.communication.Peer;

/**
 * Listener for invitations to join a network.
 *
 * @param <P> The type of address used.
 * @author Luca Crema
 * @author Marco Mariotto
 * @author Alessandra Tonin
 */
public interface JoinListener<P extends Peer> {

    /**
     * Callback for received invitation to join a network from another user.
     * @param invitation The received invitation.
     */
    void onJoinInvitationReceived(Invitation<P> invitation);

}
