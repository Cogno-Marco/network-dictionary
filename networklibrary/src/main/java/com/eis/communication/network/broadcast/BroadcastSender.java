package com.eis.communication.network.broadcast;

import com.eis.communication.network.SMSNetSubscribers;
import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * This class allows to send a command in broadcast to the entire network
 */
public class BroadcastSender {

    /**
     * Broadcasts a message to every subscriber on the net.
     * @param message
     * @param subscribers
     */
    public static void broadcastMessage(SMSMessage message, SMSNetSubscribers subscribers){
        //Naive implementation of a broadcast: this node sends a message to each subscriber
        for(SMSPeer peer : subscribers.getSubscribers()){
            SMSHandler.getInstance().sendMessage(message);
        }
    }

}
