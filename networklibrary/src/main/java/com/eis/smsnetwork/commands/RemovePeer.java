package com.eis.smsnetwork.commands;

import androidx.annotation.NonNull;

import com.eis.communication.network.Command;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetSubscriberList;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public class RemovePeer implements Command {

    private SMSPeer peer;
    private SMSNetSubscriberList netSubscribers;

    /**
     * Constructor for the RemovePeer command, needs the data to operate
     * @param peer The peer to remove from the network
     * @param netSubscribers The subscribers currently in the network
     */
    public RemovePeer(@NonNull SMSPeer peer,@NonNull SMSNetSubscriberList netSubscribers){
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
