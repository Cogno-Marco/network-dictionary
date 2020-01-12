package com.eis.communication.network.commands;

import androidx.annotation.NonNull;

import com.eis.communication.Peer;
import com.eis.communication.network.Command;
import com.eis.communication.network.NetSubscriberList;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 */
public class RemovePeer implements Command {

    private Peer peer;
    private NetSubscriberList netSubscribers;

    /**
     * Constructor for the RemovePeer command, needs the data to operate
     *
     * @param peer           The peer to remove from the network
     * @param netSubscribers The subscribers currently in the network
     */
    public RemovePeer(@NonNull Peer peer, @NonNull NetSubscriberList netSubscribers) {
        this.peer = peer;
        this.netSubscribers = netSubscribers;
    }

    /**
     * Removes a peer from the subscribers list and broadcasts it to the net
     */
    public void execute() {
        netSubscribers.removeSubscriber(peer);
        //TODO broadcast
    }

}
