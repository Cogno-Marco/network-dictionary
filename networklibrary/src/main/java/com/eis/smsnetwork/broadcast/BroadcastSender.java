package com.eis.smsnetwork.broadcast;

import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetSubscriberList;

import java.util.List;

/**
 * This class allows to send a command in broadcast to the entire network
 */
public class BroadcastSender {

    /**
     * Broadcasts a message to the specified subscribers.
     *
     * @param receivers The {@link com.eis.smsnetwork.SMSNetSubscriberList} to which to send the message.
     * @param textMessage The {@link String} to broadcast.
     */
    public static void broadcastMessage(List<SMSPeer> receivers, String textMessage) {
        //Naive implementation of a broadcast: this node sends a message to each subscriber
        for (SMSPeer peer : receivers) {
            SMSMessage message = new SMSMessage(peer, textMessage);
            SMSHandler.getInstance().sendMessage(message);
        }
    }

}
