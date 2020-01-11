package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetworkManager;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemovePeerTest {

    private SMSPeer peer1 = new SMSPeer("+393408140326");
    private SMSPeer peer2 = new SMSPeer("+393408140366");

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private AddPeer addPeer1 = new AddPeer(peer1, networkManager.getNetSubscribers());
    private AddPeer addPeer2 = new AddPeer(peer2, networkManager.getNetSubscribers());

    private RemovePeer removePeer1 = new RemovePeer(peer1, networkManager.getNetSubscribers());

    @Before
    public void setUp(){
        addPeer1.execute();
        addPeer2.execute();
    }

    @Test
    public void execute() {
        removePeer1.execute();
        assertFalse(networkManager.getNetSubscribers().getSubscribers().contains(peer1));
    }
}