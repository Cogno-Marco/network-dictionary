package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetSubscribers;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 */
public class RemovePeer implements Command {

    private final SMSPeer peer;
    private final SMSNetSubscribers netSubscribers;

    public RemovePeer(SMSPeer peer, SMSNetSubscribers netSubscribers) {
        this.peer = peer;
        this.netSubscribers = netSubscribers;
    }

    public void execute() {
        netSubscribers.removeSubscriber(peer);
        //TODO broadcast
    }

}
