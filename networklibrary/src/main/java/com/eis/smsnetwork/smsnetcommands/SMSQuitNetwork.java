package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.QuitNetwork;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.broadcast.BroadcastSender;

/**
 * Command to quit the current network.
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSQuitNetwork extends QuitNetwork<SMSPeer> {

    SMSJoinableNetManager netManager;

    /**
     * Constructor for the SMSQuitNetwork command, needs the data to operate
     *
     * @param netSubscribers The subscribers currently in the network
     * @param netManager A valid SMSJoinableNetManager, used by the command
     */
    public SMSQuitNetwork(@NonNull NetSubscriberList<SMSPeer> netSubscribers,
                          @NonNull SMSJoinableNetManager netManager) {
        super(netSubscribers);
        this.netManager = netManager;
    }

    /**
     * Removes myself from the subscribers list and broadcasts it to the net
     */
    protected void execute() {
        netManager.clear();
        String quitNetworkMessage = RequestType.QuitNetwork.asString();
        BroadcastSender.broadcastMessage(netSubscribers.getSubscribers(), quitNetworkMessage);
    }
}
