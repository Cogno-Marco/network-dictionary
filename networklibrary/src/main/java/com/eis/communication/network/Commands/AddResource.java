package com.eis.communication.network.Commands;

import com.eis.communication.network.SMSNetworkManager;

/**
 * Command to add a resource
 *
 * @author Edoardo Raimondi
 */
public class AddResource implements Command {

    /*Using strings to be coherent to SMSNetDictionary class*/
    private String key;
    private String value;
    private SMSNetworkManager networkManager;

    public AddResource(String key, String value, SMSNetworkManager networkManager){
        this.key = key;
        this.value = value;
        this.networkManager = networkManager;
    }

    public void execute() {
        networkManager.getNetDictionary().addResource(key, value);
        //TODO broadcast
    }
}
