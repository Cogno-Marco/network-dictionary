package com.eis.smsnetwork.broadcast;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.SMSNetSubscriberList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SMSJoinableNetManager.class)
public class BroadcastReceiverTest {

    @Test
    public void onMessageReceived_garbageIsIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        SMSMessage garbage = new SMSMessage(sender, "aidsajfksda;ds");

        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(new SMSNetSubscriberList());
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(garbage);
        PowerMockito.verifyStatic(never());
        SMSJoinableNetManager.getInstance();
    }

    @Test
    public void onMessageReceived_InviteWithGarbage_IsIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String garbageText = RequestType.Invite.asString() + ">";
        SMSMessage garbageMessage = new SMSMessage(sender, garbageText);

        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(new SMSNetSubscriberList());
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(garbageMessage);
        verify(mockManager, never()).checkInvitation(any());
    }

    @Test
    public void onMessageReceived_RemovePeerWithGarbage_IsIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String garbageText = RequestType.RemovePeer.asString() + ">";
        SMSMessage garbageMessage = new SMSMessage(sender, garbageText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(garbageMessage);
        verify(mockSubscribers, never()).removeSubscriber(any());
    }

    //TODO: write tests for messages with correct fields[0], and garbage in successive fields
}