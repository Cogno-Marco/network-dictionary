package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetSubscriberList;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to add a peer to the Subscribers list
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class AddPeer extends com.eis.communication.network.commands.AddPeer<SMSPeer> {

    /**
     * AddPeer command constructor, receives the data it needs to operate on.
     *
     * @param peer           The SMSPeer to add to the network
     * @param netSubscribers The subscribers to notify of the newest member
     */
    public AddPeer(@NonNull SMSPeer peer, @NonNull NetSubscriberList<SMSPeer> netSubscribers) {
        super(peer, netSubscribers);
    }

    /**
     * Adds the peer to the subscribers list and broadcasts it to the net
     */
    protected void execute() {
        netSubscribers.addSubscriber(peer);
        //TODO broadcast
    }
}
