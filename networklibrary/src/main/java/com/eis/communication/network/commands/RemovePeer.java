package com.eis.communication.network.commands;

import androidx.annotation.NonNull;

import com.eis.communication.Peer;
import com.eis.communication.network.NetSubscriberList;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public abstract class RemovePeer<T extends Peer> extends Command {

    protected final T peer;
    protected final NetSubscriberList<T> netSubscribers;

    /**
     * Constructor for the RemovePeer command, needs the data to operate
     *
     * @param peer           The peer to remove from the network
     * @param netSubscribers The subscribers currently in the network
     */
    public RemovePeer(@NonNull T peer, @NonNull NetSubscriberList<T> netSubscribers) {
        this.peer = peer;
        this.netSubscribers = netSubscribers;
    }

    /**
     * Removes a peer from the subscribers list and broadcasts it to the net
     */
    protected abstract void execute();

}
