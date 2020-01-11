package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetworkManager;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemovePeerTest {

    private final SMSPeer peer1 = new SMSPeer("+393408140326");
    private final SMSPeer peer2 = new SMSPeer("+393408140366");

    private final SMSNetworkManager networkManager = new SMSNetworkManager();

    private final AddPeer addPeer1 = new AddPeer(peer1, networkManager.getNetSubscribers());
    private final AddPeer addPeer2 = new AddPeer(peer2, networkManager.getNetSubscribers());

    private final RemovePeer removePeer1 = new RemovePeer(peer1, networkManager.getNetSubscribers());

    @Before
    public void setUp() {
        addPeer1.execute();
        addPeer2.execute();
    }

    @Test
    public void execute() {
        removePeer1.execute();
        assertFalse(networkManager.getNetSubscribers().getSubscribers().contains(peer1));
    }
}