package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetworkManager;
import com.eis.smslibrary.SMSPeer;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 */
public class RemovePeer implements Command {

    private SMSPeer peer;
    private SMSNetworkManager networkManager;

    public RemovePeer(SMSPeer peer, SMSNetworkManager networkManager){
        this.peer = peer;
        this.networkManager = networkManager;
    }

    public void execute() {
        networkManager.getNetSubscribers().removeSubcriber(peer);
        //TODO broadcast
    }

}
