package com.eis.communication.network.commands;

import com.eis.communication.network.commands.AddPeer;
import com.eis.communication.network.commands.RemovePeer;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetworkManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemovePeerTest {

    private SMSPeer peer1 = new SMSPeer("+393408140326");
    private SMSPeer peer2 = new SMSPeer("+393408140366");

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private AddPeer addPeer1 = new AddPeer(peer1, networkManager.getNetSubscriberList());
    private AddPeer addPeer2 = new AddPeer(peer2, networkManager.getNetSubscriberList());

    private RemovePeer removePeer1 = new RemovePeer(peer1, networkManager.getNetSubscriberList());

    @Before
    public void setUp(){
        addPeer1.execute();
        addPeer2.execute();
    }

    @Test
    public void execute() {
        removePeer1.execute();
        assertFalse(networkManager.getNetSubscriberList().getSubscribers().contains(peer1));
    }
}