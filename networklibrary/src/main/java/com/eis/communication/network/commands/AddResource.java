package com.eis.communication.network.commands;

import com.eis.communication.network.SMSNetDictionary;

/**
 * Command to add a resource
 *
 * @author Edoardo Raimondi
 */
public class AddResource implements Command {

    /*Using strings to be coherent to SMSNetDictionary class*/
    private final String key;
    private final String value;
    private final SMSNetDictionary netDictionary;

    public AddResource(String key, String value, SMSNetDictionary netDictionary) {
        this.key = key;
        this.value = value;
        this.netDictionary = netDictionary;
    }

    public void execute() {
        netDictionary.addResource(key, value);
        //TODO broadcast
    }
}
