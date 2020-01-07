package com.eis.communication.network;

import com.eis.communication.Peer;

/**
 * Manager of any type of network, contains methods that should be in every network
 *
 * @param <RK> Type of resource key handled by the network
 * @param <RV> Type of resource value handled by the network
 * @param <P>  Type of peer/address handled by the network
 * @param <FR> The enumeration used to define fail reasons for the network.
 * @author Luca Crema
 * @author Marco Mariotto
 * @author Alberto Ursino
 * @author Alessandra Tonin
 */
public interface NetworkManager<RK, RV, P extends Peer, FR extends FailReason> {

    /**
     * Saves a resource value in the network for the specified key. If the save was successful
     * {@link SetResourceListener#onResourceSet(Object, Object)} will be called.
     *
     * @param key                 The key identifier for the resource.
     * @param value               The identified value of the resource.
     * @param setResourceListener Listener called on resource successfully saved or on fail.
     */
    void setResource(RK key, RV value, SetResourceListener<RK, RV, FR> setResourceListener);

    /**
     * Retrieves a resource value from the network for the specified key. The value is returned inside
     * {@link GetResourceListener#onGetResource(Object, Object)}.
     *
     * @param key                 The key identifier for the resource.
     * @param getResourceListener Listener called on resource successfully retrieved or on fail.
     */
    void getResource(RK key, GetResourceListener<RK, RV, FR> getResourceListener);

    /**
     * Invites another user to join the network. If the invitation is sent correctly
     * {@link InviteListener#onInvitationSent(Peer)} will be called
     *
     * @param peer           The address of the user to invite to join the network.
     * @param inviteListener Listener called on user invited or on fail.
     */
    void invite(P peer, InviteListener<P, FR> inviteListener);

}
