package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetSubscriberList;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.broadcast.BroadcastReceiver;
import com.eis.smsnetwork.broadcast.BroadcastSender;

/**
 * Command to add a peer to the Subscribers list
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSAddPeer extends com.eis.communication.network.commands.AddPeer<SMSPeer> {

    /**
     * SMSAddPeer command constructor, receives the data it needs to operate on.
     *
     * @param peer           The SMSPeer to add to the network
     * @param netSubscribers The subscribers to notify of the newest member
     */
    public SMSAddPeer(@NonNull SMSPeer peer, @NonNull NetSubscriberList<SMSPeer> netSubscribers) {
        super(peer, netSubscribers);
    }

    /**
     * Adds the peer to the subscribers list and broadcasts it to the net
     */
    protected void execute() {
        netSubscribers.addSubscriber(peer);
        String addPeerMessage = RequestType.AddPeer.asString() + BroadcastReceiver.FIELD_SEPARATOR +
                peer.getAddress();
        BroadcastSender.broadcastMessage(netSubscribers.getSubscribers(), addPeerMessage);
    }
}
