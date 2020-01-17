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
    public void splitMessage() {
        String message = "è¤saluto¤buon\\¤giorno¤albero¤pino\\¤marittimo\\¤";
        String[] expectedFields0 = {"è", "saluto", "buon\\¤giorno", "albero", "pino\\¤marittimo\\¤"};
        String[] fields = message.split(BroadcastReceiver.SEPARATOR_REGEX);
        Assert.assertArrayEquals(expectedFields0, fields);

        message = "\\¤\\¤¤\\ciao";
        String[] expectedFields1 = {"\\¤\\¤", "\\ciao"};
        fields = message.split(BroadcastReceiver.SEPARATOR_REGEX);
        Assert.assertArrayEquals(expectedFields1, fields);

        message = "parola¤\\\\¤parola¤\\¤parola";
        String[] expectedFields2 = {"parola", "\\\\¤parola", "\\¤parola"};
        fields = message.split(BroadcastReceiver.SEPARATOR_REGEX);
        Assert.assertArrayEquals(expectedFields2, fields);
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
    public void onMessageReceived_quitNetworkWithGarbage_isIgnored() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String garbageText = RequestType.QuitNetwork.asString() + ">";
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
    public void onMessageReceived_correctQuitNetwork() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.QuitNetwork.asString();
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
        String garbageText = RequestType.AddPeer.asString() + "¤+393478512584¤+393338512123";
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
        String garbageText = RequestType.AddPeer.asString() + "¤+393478512584¤+0000333812123";
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
        String correctText = RequestType.AddPeer.asString() + "¤+393478512584¤+39333812123";
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

    @Test
    public void onMessageReceived_correctAddResource() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.AddResource.asString() +
                "¤the cat is on¤the table¤the book is¤under the table";
        SMSMessage correctMessage = new SMSMessage(sender, correctText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSNetDictionary mockDictionary = mock(SMSNetDictionary.class);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(mockDictionary);
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(correctMessage);
        verify(mockDictionary).addResourceFromSMS("the cat is on", "the table");
        verify(mockDictionary).addResourceFromSMS("the book is", "under the table");
    }

    @Test
    public void onMessageReceived_correctRemoveResource() {
        BroadcastReceiver instance = new BroadcastReceiver();
        SMSPeer sender = new SMSPeer("+393492794133");
        String correctText = RequestType.RemoveResource.asString() +
                "¤the cat is on¤the table";
        SMSMessage correctMessage = new SMSMessage(sender, correctText);
        Set<SMSPeer> subscribersSet = new HashSet<>();
        subscribersSet.add(sender);

        SMSNetSubscriberList mockSubscribers = mock(SMSNetSubscriberList.class);
        when(mockSubscribers.getSubscribers()).thenReturn(subscribersSet);
        SMSNetDictionary mockDictionary = mock(SMSNetDictionary.class);
        SMSJoinableNetManager mockManager = mock(SMSJoinableNetManager.class);
        when(mockManager.getNetSubscriberList()).thenReturn(mockSubscribers);
        when(mockManager.getNetDictionary()).thenReturn(mockDictionary);
        PowerMockito.mockStatic(SMSJoinableNetManager.class);
        when(SMSJoinableNetManager.getInstance()).thenReturn(mockManager);

        instance.onMessageReceived(correctMessage);
        verify(mockDictionary).removeResourceFromSMS("the cat is on");
        verify(mockDictionary).removeResourceFromSMS("the table");
    }
}