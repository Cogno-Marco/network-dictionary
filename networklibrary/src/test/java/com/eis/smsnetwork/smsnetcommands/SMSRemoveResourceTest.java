package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.AddResource;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.communication.network.commands.RemoveResource;
import com.eis.smsnetwork.SMSNetworkManager;
import com.eis.smsnetwork.smsnetcommands.SMSAddResource;
import com.eis.smsnetwork.smsnetcommands.SMSRemoveResource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMSRemoveResourceTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private String key1 = "key";
    private String value1 = "value";

    private String key2 = "lmao";
    private String value2 = "fuck";

    private SMSAddResource addResource1 = new SMSAddResource(key1, value1, networkManager.getNetDictionary());
    private SMSAddResource addResource2 = new SMSAddResource(key2, value2, networkManager.getNetDictionary());
    private SMSRemoveResource removeResource = new SMSRemoveResource(key1, networkManager.getNetDictionary());
    @Before
    public void setUp(){
        CommandExecutor.execute(addResource1);
        CommandExecutor.execute(addResource2);
    }

    @Test
    public void execute1() {
        CommandExecutor.execute(removeResource);
        assertNull(networkManager.getNetDictionary().getResource(key1));
    }

    @Test
    public void execute2(){
        CommandExecutor.execute(removeResource);
        assertNotNull(networkManager.getNetDictionary().getResource(key2));
    }
}