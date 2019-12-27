package com.eis.communication.network;

/**
 * Listener for invitations to join a network.
 *
 * @param <U> The type of user that sends invitations.
 * @author Luca Crema
 * @author Marco Mariotto
 * @author Alessandra Tonin
 */
public interface JoinListener<U extends NetworkUser>{

    /**
     * Callback for received invitation to join a network from another user.
     * @param invitation The received invitation.
     */
    void onJoinInvitationReceived(Invitation<U> invitation);

}
