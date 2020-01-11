package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetworkManager;
import com.eis.smslibrary.SMSPeer;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddPeerTest {

    private final SMSPeer peer = new SMSPeer("+393408140326");
    private final SMSNetworkManager networkManager = new SMSNetworkManager();
    private final AddPeer addPeer = new AddPeer(peer, networkManager.getNetSubscribers());

    @Test
    public void execute() {
        addPeer.execute();
        assertTrue(networkManager.getNetSubscribers().getSubscribers().contains(peer));
    }
}