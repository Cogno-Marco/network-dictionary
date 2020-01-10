package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetSubscribers;
import com.eis.communication.network.SMSNetworkManager;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to add a peer to Subscribers
 */
public class AddPeer implements Command {

    private SMSPeer peer;
    private SMSNetworkManager networkManager;

    public AddPeer(SMSPeer peer, SMSNetworkManager networkManager){
        this.peer = peer;
        this.networkManager = networkManager;
    }

    /**
     * Add the peer to subscribers
     */
    public void execute(){
        networkManager.getNetSubscribers().addSubscriber(peer);
        //TODO broadcast
    }
}
