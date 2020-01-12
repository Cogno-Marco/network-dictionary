package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetworkManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveResourceTest {

    private final SMSNetworkManager networkManager = new SMSNetworkManager();

    private final String key1 = "key";
    private final String value1 = "value";

    private final String key2 = "lmao";
    private final String value2 = "fuck";

    private final AddResource addResource1 = new AddResource(key1, value1, networkManager.getNetDictionary());
    private final AddResource addResource2 = new AddResource(key2, value2, networkManager.getNetDictionary());
    private final RemoveResource removeResource = new RemoveResource(key1, networkManager.getNetDictionary());

    @Before
    public void setUp() {
        addResource1.execute();
        addResource2.execute();
    }

    @Test
    public void execute1() {
        removeResource.execute();
        assertNull(networkManager.getNetDictionary().getResource(key1));
    }

    @Test
    public void execute2() {
        removeResource.execute();
        assertNotNull(networkManager.getNetDictionary().getResource(key2));
    }
}