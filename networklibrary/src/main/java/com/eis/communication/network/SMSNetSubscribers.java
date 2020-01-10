package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

import java.util.ArrayList;

/**
 * Concrete implementation of a {@link INetSubscribers} interface
 *
 * @author Marco Cognolato
 */
public class SMSNetSubscribers implements INetSubscribers<SMSPeer>  {

    
    /**
     * Adds a subscriber to this network
     * @param subscriber The subscriber to add to the net
     */
    public void addSubscriber(SMSPeer subscriber){

    }

    /**
     * @return Returns the list of all the current subscribers to the net
     */
    public ArrayList<SMSPeer> getSubscribers(){
        return null;
    }

    /**
     * Removes a given subscriber from the subscribers
     * @param subscriber The subscriber to remove
     */
    public void removeSubcriber(SMSPeer subscriber){

    }
}
