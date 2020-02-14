package com.eis.smsnetwork.broadcast;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetSubscriberList;

import java.util.Set;

/**
 * This class allows to send a command in broadcast to the entire network
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class BroadcastSender {

    /**
     * Broadcasts a message to the specified subscribers.
     *
     * @param receivers   The {@link SMSNetSubscriberList#getSubscribers()} to which to send the message.
     * @param textMessage The {@link String} to broadcast.
     */
    public static void broadcastMessage(Set<SMSPeer> receivers, String textMessage) {
        //Naive implementation of a broadcast: this node sends a message to each subscriber
        for (SMSPeer peer : receivers) {
            SMSMessage message = new SMSMessage(peer, textMessage);
            SMSManager.getInstance().sendMessage(message);
        }
    }

}
