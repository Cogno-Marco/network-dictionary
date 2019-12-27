package com.eis.communication.network;

/**
 * Manager of any type of network, contains methods that should be in every network
 *
 * @param <RK> Type of resource key handled by the network
 * @param <RV> Type of resource value handled by the network
 *
 * @author Luca Crema
 * @author Marco Mariotto
 * @author Alberto Ursino
 * @author Alessandra Tonin
 */
public interface NetworkManager<RK, RV, U extends NetworkUser>{

    public void setResource(RK key, RV value);

    public void getResource(RK key);

    public void invite(U user);

    public void join(Invitation<U> invitation);

}
