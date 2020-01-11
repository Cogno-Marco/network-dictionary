package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetSubscribers;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to add a peer to Subscribers
 */
public class AddPeer implements Command {

    private SMSPeer peer;
    private SMSNetSubscribers netSubscribers;

    public AddPeer(SMSPeer peer, SMSNetSubscribers netSubscribers){
        this.peer = peer;
        this.netSubscribers = netSubscribers;
    }

    /**
     * Add the peer to subscribers
     */
    public void execute(){
        netSubscribers.addSubscriber(peer);
        //TODO broadcast
    }
}
