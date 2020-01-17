package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSNetworkManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SMSManager.class})
public class SMSAddPeerTest {

    private SMSPeer peer = new SMSPeer("+393408140326");
    private SMSNetworkManager networkManager = new SMSNetworkManager();
    private SMSAddPeer addPeer = new SMSAddPeer(peer, networkManager.getNetSubscriberList());

    @Test
    public void execute() {
        SMSManager mockManager = mock(SMSManager.class);
        PowerMockito.mockStatic(SMSManager.class);
        when(SMSManager.getInstance()).thenReturn(mockManager);
        CommandExecutor.execute(addPeer);
        assertTrue(networkManager.getNetSubscriberList().getSubscribers().contains(peer));
    }
}