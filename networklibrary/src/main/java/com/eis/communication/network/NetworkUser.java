package com.eis.communication.network;

import com.eis.communication.Peer;

/**
 * Represents a user of the network.
 * <p>Its implementation could have a username and the age or its public preferences and so on.
 * @param <P> Communication channel address type of the user.
 * @author Luca Crema
 */
public interface NetworkUser<P extends Peer>{

    /**
     * @return The communication contact information/address to communicate with the user.
     */
    P getPeer();

}
