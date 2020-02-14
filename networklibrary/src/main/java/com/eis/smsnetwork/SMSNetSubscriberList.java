package com.eis.smsnetwork;


import androidx.annotation.NonNull;

import com.eis.communication.network.NetSubscriberList;
import com.eis.smslibrary.SMSPeer;

import java.util.HashSet;
import java.util.Set;

/**
 * Concrete implementation of a {@link NetSubscriberList} interface
 *
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSNetSubscriberList implements NetSubscriberList<SMSPeer> {

    private Set<SMSPeer> subscribers = new HashSet<>();

    /**
     * Adds a subscriber to this network
     *
     * @param subscriber The subscriber to add to the net
     * @throws IllegalArgumentException If subscriber is null
     */
    public void addSubscriber(@NonNull final SMSPeer subscriber) {
        if (subscriber == null) throw new IllegalArgumentException("Cannot add a null peer!");
        subscribers.add(subscriber);
    }

    /**
     * @return Returns the set of all the current subscribers to the net
     */
    public Set<SMSPeer> getSubscribers() {
        return subscribers;
    }

    /**
     * Removes a given subscriber from the subscribers
     *
     * @param subscriber The subscriber to remove
     * @throws IllegalArgumentException If a non present subscriber is removed
     */
    public void removeSubscriber(@NonNull final SMSPeer subscriber) {
        if (!subscribers.contains(subscriber))
            throw new IllegalArgumentException("The subscriber you're trying to remove is not present!");
        subscribers.remove(subscriber);
    }
}
