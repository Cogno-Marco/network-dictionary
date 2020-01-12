package com.eis.smsnetwork.commands;

import com.eis.communication.network.commands.AddResource;
import com.eis.communication.network.commands.RemoveResource;
import com.eis.smsnetwork.SMSNetworkManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveResourceTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private String key1 = "key";
    private String value1 = "value";

    private String key2 = "lmao";
    private String value2 = "fuck";

    private AddResource addResource1 = new AddResource(key1, value1, networkManager.getNetDictionary());
    private AddResource addResource2 = new AddResource(key2, value2, networkManager.getNetDictionary());
    private RemoveResource removeResource = new RemoveResource(key1, networkManager.getNetDictionary());
    @Before
    public void setUp(){
        addResource1.execute();
        addResource2.execute();
    }

    @Test
    public void execute1() {
        removeResource.execute();
        assertNull(networkManager.getNetDictionary().getResource(key1));
    }

    @Test
    public void execute2(){
        removeResource.execute();
        assertNotNull(networkManager.getNetDictionary().getResource(key2));
    }
}