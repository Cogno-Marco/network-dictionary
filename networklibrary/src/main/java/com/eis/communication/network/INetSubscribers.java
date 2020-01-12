package com.eis.communication.network;

import com.eis.communication.Peer;

import java.util.Set;

/**
 * Interface which defines common operations for Subscribers of a network
 * @param <T> The type of subscribers, must implement the {@link Peer} interface
 *
 * @author Marco Cognolato, Giovanni Velludo
 */
public interface INetSubscribers<T extends Peer> {

    /**
     * Adds a subscriber to this network
     * @param subscriber The subscriber to add to the net
     */
    void addSubscriber(T subscriber);

    /**
     * @return Returns the list of all the current subscribers to the net
     */
    Set<T> getSubscribers();

    /**
     * Removes a given subscriber from the subscribers
     * @param subscriber The subscriber to remove
     */
    void removeSubscriber(T subscriber);
}
