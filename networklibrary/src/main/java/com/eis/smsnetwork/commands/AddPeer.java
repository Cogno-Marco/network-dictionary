package com.eis.smsnetwork.commands;

import com.eis.communication.network.Command;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetSubscriberList;

/**
 * Command to add a peer to the Subscribers list
 *
 * @author Edoardo Raimondi
 */
public class AddPeer implements Command {

    private SMSPeer peer;
    private SMSNetSubscriberList netSubscribers;

    public AddPeer(SMSPeer peer, SMSNetSubscriberList netSubscribers){
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
