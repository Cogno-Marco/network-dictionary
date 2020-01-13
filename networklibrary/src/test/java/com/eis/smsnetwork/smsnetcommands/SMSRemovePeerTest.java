package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.AddPeer;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.communication.network.commands.RemovePeer;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetworkManager;
import com.eis.smsnetwork.smsnetcommands.SMSAddPeer;
import com.eis.smsnetwork.smsnetcommands.SMSRemovePeer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMSRemovePeerTest {

    private SMSPeer peer1 = new SMSPeer("+393408140326");
    private SMSPeer peer2 = new SMSPeer("+393408140366");

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private SMSAddPeer addPeer1 = new SMSAddPeer(peer1, networkManager.getNetSubscriberList());
    private SMSAddPeer addPeer2 = new SMSAddPeer(peer2, networkManager.getNetSubscriberList());

    private SMSRemovePeer removePeer1 = new SMSRemovePeer(peer1, networkManager.getNetSubscriberList());

    @Before
    public void setUp(){
        CommandExecutor.execute(addPeer1);
        CommandExecutor.execute(addPeer2);
    }

    @Test
    public void execute() {
        CommandExecutor.execute(removePeer1);
        assertFalse(networkManager.getNetSubscriberList().getSubscribers().contains(peer1));
    }
}