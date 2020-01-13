package com.eis.smsnetwork;

import com.eis.communication.network.listeners.GetResourceListener;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SMSNetworkManagerTest {

    private final SMSNetworkManager networkManager = new SMSNetworkManager();

    private String KEY1 = "Key1";
    private String RES1 = "Res1";

    private boolean hasPassed = false;
    private GetResourceListener<String, String, SMSFailReason> listenerMock = mock(GetResourceListener.class);

    @Before
    public void setup(){
        networkManager.getNetDictionary().removeResource(KEY1);
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
        SMSNetDictionary dictionary = (SMSNetDictionary)networkManager.getNetDictionary();
        dictionary.addResource(KEY1, RES1);

        networkManager.getResource(KEY1, listenerMock);
        verify(listenerMock, times(1)).onGetResource(KEY1, RES1);
    }

    @Test
    public void getResource_notAvailable() {
        networkManager.getResource(KEY1, listenerMock);
        verify(listenerMock, times(1)).onGetResourceFailed(KEY1, SMSFailReason.NO_RESOURCE);
    }

}