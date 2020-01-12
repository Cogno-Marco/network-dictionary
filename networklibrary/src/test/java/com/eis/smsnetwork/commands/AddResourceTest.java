package com.eis.smsnetwork.commands;

import com.eis.smsnetwork.SMSNetworkManager;


import org.junit.Test;

import static org.junit.Assert.*;

public class AddResourceTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private String key = "key";
    private String value = "value";

    private AddResource addResource = new AddResource(key, value, networkManager.getNetDictionary());

    @Test
    public void execute() {
        addResource.execute();
        assertEquals(networkManager.getNetDictionary().getResource(key), value);
    }
}