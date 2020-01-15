package com.eis.smsnetwork.broadcast;

import android.util.Log;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSInvitation;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.SMSNetSubscriberList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Giovanni Velludo
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SMSJoinableNetManager.class, Log.class})
public class BroadcastReceiverTest {

    @Captor
    ArgumentCaptor<SMSInvitation> invitationCaptor;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Log.class);
        when(Log.d(anyString(), anyString())).thenReturn(0);
    }

    @Test
    public void onMessageReceived_garbage_isIgnored() {
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
    public void onMessageReceived_inviteWithGarbage_isIgnored() {
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
    public void onMessageReceived_correctInvite() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.Invite.asString();
        SMSMessage correctMessage = new SMSMessage(sender, correctText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(correctMessage);
        verify(mockManager).checkInvitation(invitationCaptor.capture());
        Assert.assertEquals(sender, invitationCaptor.getValue().getInviterPeer());
    }

    @Test
    public void onMessageReceived_removePeerWithGarbage_isIgnored() {
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

    @Test
    public void onMessageReceived_correctRemovePeer() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.RemovePeer.asString();
        SMSMessage correctMessage = new SMSMessage(sender, correctText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(correctMessage);
        verify(mockSubscribers).removeSubscriber(sender);
    }

    @Test
    public void onMessageReceived_addPeerFromNonSubscriber_isIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String garbageText = RequestType.AddPeer.asString() + " +393478512584 +393338512123";
        SMSMessage garbageMessage = new SMSMessage(sender, garbageText);
        Set<SMSPeer> subscribersSet = new HashSet<>();

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(garbageMessage);
        verify(mockSubscribers, never()).addSubscriber(any());
    }

    @Test
    public void onMessageReceived_addPeerWithWrongNumber_isIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String garbageText = RequestType.AddPeer.asString() + " +393478512584 +0000333812123";
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
        verify(mockSubscribers, never()).addSubscriber(any());
    }

    @Test
    public void onMessageReceived_correctAddPeer() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.AddPeer.asString() + " +393478512584 +39333812123";
        SMSMessage correctMessage = new SMSMessage(sender, correctText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(new SMSNetDictionary());
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(correctMessage);
        verify(mockSubscribers).addSubscriber(new SMSPeer("+393478512584"));
        verify(mockSubscribers).addSubscriber(new SMSPeer("+39333812123"));
    }



    //TODO: write tests for messages with correct fields[0], and garbage in successive fields
}