package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetworkManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveResourceTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private String key1 = "key";
    private String value1 = "value";

    private String key2 = "lmao";
    private String value2 = "fuck";

    private AddResource addResource1 = new AddResource(key1, value1, networkManager);
    private AddResource addResource2 = new AddResource(key2, value2, networkManager);
    private RemoveResource removeResource = new RemoveResource(key1, networkManager);
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