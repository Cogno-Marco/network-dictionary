package com.eis.smsnetwork;


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