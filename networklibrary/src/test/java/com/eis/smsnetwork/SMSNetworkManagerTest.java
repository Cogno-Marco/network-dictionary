package com.eis.smsnetwork;

import android.util.Log;

import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SMSManager.class, Log.class})
public class SMSNetworkManagerTest {

    private SMSJoinableNetManager networkManager;
    private SMSNetDictionary localDictionary;
    private SMSNetSubscriberList localSubscribers;

    private String KEY1 = "Key1";
    private String RES1 = "Res1";
    private String RES2 = "Res2";
    private SMSPeer VALID_PEER = new SMSPeer("+393479281192");

    private boolean hasPassed = false;
    private GetResourceListener<String, String, SMSFailReason> getListenerMock = mock(GetResourceListener.class);
    private SetResourceListener setListenerMock = mock(SetResourceListener.class);
    private RemoveResourceListener removeListenerMock = mock(RemoveResourceListener.class);
    private InviteListener inviteListenerMock = mock(InviteListener.class);

    @Before
    public void setup() {
        networkManager = SMSJoinableNetManager.getInstance();
        networkManager.clear();
        localDictionary = (SMSNetDictionary) networkManager.getNetDictionary();
        localSubscribers = (SMSNetSubscriberList) networkManager.getNetSubscriberList();
    }

    @Test
    public void getNetSubscribers() {
        assertEquals(networkManager.getNetSubscriberList(), networkManager.getNetSubscriberList());
    }

    @Test
    public void getNetDictionary() {
        assertEquals(networkManager.getNetDictionary(), networkManager.getNetDictionary());
    }

    @Test
    public void getResource_available() {
        localDictionary.addResource(KEY1, RES1);

        networkManager.getResource(KEY1, getListenerMock);
        verify(getListenerMock, times(1)).onGetResource(KEY1, RES1);
    }

    @Test
    public void getResource_notAvailable() {
        networkManager.getResource(KEY1, getListenerMock);
        verify(getListenerMock, times(1)).onGetResourceFailed(KEY1, SMSFailReason.NO_RESOURCE);
    }

    @Test
    public void setResource_available() {
        localDictionary.addResource(KEY1, RES1);
        networkManager.setResource(KEY1, RES2, setListenerMock);
        verify(setListenerMock, times(1)).onResourceSet(KEY1, RES2);
    }

    @Test
    /*
     * Tested the modification of a given <key, resource> pair; the value is modified even if it has
     * never been added to the Dictionary, but there is no error since, if the pair is not found, is
     * added to the Dictionary as a new pair.
     * It's basically an addResource()
     */
    public void setResource_notAvailable() {
        networkManager.setResource(KEY1, RES2, setListenerMock);
        verify(setListenerMock, times(0)).onResourceSetFail(KEY1, RES2, SMSFailReason.MESSAGE_SEND_ERROR);
    }

    @Test
    public void removeResource_available() {
        localDictionary.addResource(KEY1, RES1);
        networkManager.removeResource(KEY1, removeListenerMock);
        verify(removeListenerMock, times(1)).onResourceRemoved(KEY1);
    }

    @Test
    /*
     * The system is not able to send the message
     */
    public void invite_failed() {
        PowerMockito.mockStatic(Log.class);
        when(Log.e(anyString(), anyString())).thenReturn(0);
        networkManager.invite(VALID_PEER, inviteListenerMock);
        verify(inviteListenerMock, times(1)).onInvitationNotSent(VALID_PEER, SMSFailReason.MESSAGE_SEND_ERROR);
    }

    //TODO mock SMSHandler
    @Test
    public void invite_succeded() {
        //SMSHandler smsHandler = mock(SMSHandler.class);
        //when(smsHandler.getInstance()).thenReturn(smsHandler);
        //networkManager.invite(VALID_PEER, inviteListenerMock);
    }

    @Test
    public void acceptJoinInvitation() {
        PowerMockito.mockStatic(Log.class);
        when(Log.d(anyString(), anyString())).thenReturn(0);
        SMSManager mockManager = mock(SMSManager.class);
        mockStatic(SMSManager.class);
        when(SMSManager.getInstance()).thenReturn(mockManager);
        SMSInvitation invitation = new SMSInvitation(VALID_PEER);
        networkManager.acceptJoinInvitation(invitation);
    }

}