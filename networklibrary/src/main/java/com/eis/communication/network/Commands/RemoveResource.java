package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetworkManager;

/**
 * Command to remove a resource from the network dictionary
 *
 * @author Edoardo Raimondi
 */
public class RemoveResource implements Command {

    private SMSNetworkManager networkManager;
    private String key;

    public RemoveResource(String key, SMSNetworkManager networkManager){
        this.key = key;
        this.networkManager = networkManager;
    }

    public void execute() {
        networkManager.getNetDictionary().removeResource(key);
    }
}
