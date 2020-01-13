package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetworkManager;
import com.eis.smsnetwork.smsnetcommands.SMSAddPeer;

import org.junit.Test;

import static org.junit.Assert.*;

public class SMSAddPeerTest {

    private SMSPeer peer = new SMSPeer("+393408140326");
    private SMSNetworkManager networkManager = new SMSNetworkManager();
    private SMSAddPeer addPeer = new SMSAddPeer(peer, networkManager.getNetSubscriberList());

    @Test
    public void execute() {
        CommandExecutor.execute(addPeer);
        assertTrue(networkManager.getNetSubscriberList().getSubscribers().contains(peer));
    }
}