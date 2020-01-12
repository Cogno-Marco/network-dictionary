package com.eis.smsnetwork.commands;

import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetworkManager;
import com.eis.smsnetwork.commands.AddPeer;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddPeerTest {

    private SMSPeer peer = new SMSPeer("+393408140326");
    private SMSNetworkManager networkManager = new SMSNetworkManager();
    private AddPeer addPeer = new AddPeer(peer, networkManager.getNetSubscribers());

    @Test
    public void execute() {
        addPeer.execute();
        assertTrue(networkManager.getNetSubscribers().getSubscribers().contains(peer));
    }
}