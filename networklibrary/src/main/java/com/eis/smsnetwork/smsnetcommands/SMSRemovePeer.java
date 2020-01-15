package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetSubscriberList;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSRemovePeer extends com.eis.communication.network.commands.RemovePeer<SMSPeer> {

    /**
     * Constructor for the SMSRemovePeer command, needs the data to operate
     *
     * @param peer           The peer to remove from the network
     * @param netSubscribers The subscribers currently in the network
     */
    public SMSRemovePeer(@NonNull SMSPeer peer, @NonNull NetSubscriberList<SMSPeer> netSubscribers) {
        super(peer, netSubscribers);
    }

    /**
     * Removes a peer from the subscribers list and broadcasts it to the net
     */
    protected void execute() {
        netSubscribers.removeSubscriber(peer);
        //TODO broadcast
    }

}
