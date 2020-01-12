package com.eis.smsnetwork.commands;

import com.eis.communication.network.Command;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetSubscriberList;

/**
 * Command to remove a peer from the subscribers
 *
 * @author Edoardo Raimondi
 */
public class RemovePeer implements Command {

    private SMSPeer peer;
    private SMSNetSubscriberList netSubscribers;

    public RemovePeer(SMSPeer peer, SMSNetSubscriberList netSubscribers){
        this.peer = peer;
        this.netSubscribers = netSubscribers;
    }

    public void execute() {
        netSubscribers.removeSubscriber(peer);
        //TODO broadcast
    }

}
