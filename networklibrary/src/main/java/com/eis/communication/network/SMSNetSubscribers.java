package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

import java.util.ArrayList;

/**
 * Concrete implementation of a {@link INetSubscribers} interface
 *
 * @author Marco Cognolato
 */
public class SMSNetSubscribers implements INetSubscribers<SMSPeer>  {

    private ArrayList<SMSPeer> subscribers = new ArrayList<>();

    /**
     * Adds a subscriber to this network
     * @param subscriber The subscriber to add to the net
     * @throws IllegalArgumentException If subscriber is null
     */
    public void addSubscriber(SMSPeer subscriber){
        if(subscriber == null) throw new IllegalArgumentException("Cannot add a null peer!");
        subscribers.add(subscriber);
    }

    /**
     * @return Returns the list of all the current subscribers to the net
     */
    public ArrayList<SMSPeer> getSubscribers(){
        return subscribers;
    }

    /**
     * Removes a given subscriber from the subscribers
     * @param subscriber The subscriber to remove
     * @throws IllegalArgumentException If a non present subscriber is removed
     */
    public void removeSubcriber(SMSPeer subscriber){
        if(!subscribers.contains(subscriber))
            throw new IllegalArgumentException("The subscriber you're trying to remove is not present!");
        subscribers.remove(subscriber);
    }
}