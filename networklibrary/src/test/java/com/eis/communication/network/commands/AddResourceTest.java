package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetworkManager;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddResourceTest {

    private final SMSNetworkManager networkManager = new SMSNetworkManager();

    private final String key = "key";
    private final String value = "value";

    private final AddResource addResource = new AddResource(key, value, networkManager.getNetDictionary());

    @Test
    public void execute() {
        addResource.execute();
        assertEquals(networkManager.getNetDictionary().getResource(key), value);
    }
}