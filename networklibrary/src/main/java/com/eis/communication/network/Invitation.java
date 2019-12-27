package com.eis.communication.network;

/**
 * Represents an invitation to join a network.
 * <p>Could contain network name or invitation message if needed.
 *
 * @param <U> The type of network user for the network.
 * @author Luca Crema
 */
public interface Invitation<U extends NetworkUser> {

    /**
     * @return Who sent this invitation.
     */
    U getInviter();

}
