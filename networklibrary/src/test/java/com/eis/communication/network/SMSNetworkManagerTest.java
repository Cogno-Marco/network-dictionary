package com.eis.communication.network;

import com.eis.communication.network.smsnetwork.SMSNetworkManager;

import org.junit.Test;

import static org.junit.Assert.*;

public class SMSNetworkManagerTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    @Test
    public void getNetSubscribers() {
        assertEquals(networkManager.getNetSubscribers(), networkManager.getNetSubscribers());
    }

    @Test
    public void getNetDictionary() {
        assertEquals(networkManager.getNetDictionary(), networkManager.getNetDictionary());
    }
}