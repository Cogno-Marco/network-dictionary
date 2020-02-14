package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSJoinableNetManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SMSManager.class})
public class SMSQuitNetworkTest {

    private SMSPeer peer1 = new SMSPeer("+393408140326");
    private SMSPeer peer2 = new SMSPeer("+393408140366");

    private SMSJoinableNetManager networkManager = SMSJoinableNetManager.getInstance();

    private SMSAddPeer addPeer1 = new SMSAddPeer(peer1, networkManager.getNetSubscriberList());
    private SMSAddPeer addPeer2 = new SMSAddPeer(peer2, networkManager.getNetSubscriberList());

    private SMSQuitNetwork removePeer1 = new SMSQuitNetwork(networkManager.getNetSubscriberList(), networkManager);

    @Before
    public void setUp() {
        SMSManager mockManager = mock(SMSManager.class);
        mockStatic(SMSManager.class);
        when(SMSManager.getInstance()).thenReturn(mockManager);
        CommandExecutor.execute(addPeer1);
        CommandExecutor.execute(addPeer2);
    }

    @Test
    public void execute() {
        CommandExecutor.execute(removePeer1);
        assertTrue(SMSJoinableNetManager.getInstance().getNetSubscriberList().getSubscribers().isEmpty());
    }
}