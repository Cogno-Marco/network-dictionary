package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.commands.AddResource;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smsnetwork.SMSNetworkManager;
import com.eis.smsnetwork.smsnetcommands.SMSAddResource;


import org.junit.Test;

import static org.junit.Assert.*;

public class SMSAddResourceTest {

    private SMSNetworkManager networkManager = new SMSNetworkManager();

    private String key = "key";
    private String value = "value";

    private SMSAddResource addResource = new SMSAddResource(key, value, networkManager.getNetDictionary());

    @Test
    public void execute() {
        CommandExecutor.execute(addResource);
        assertEquals(networkManager.getNetDictionary().getResource(key), value);
    }
}