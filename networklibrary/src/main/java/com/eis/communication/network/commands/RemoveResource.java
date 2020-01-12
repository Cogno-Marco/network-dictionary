package com.eis.communication.network.commands;

import com.eis.communication.network.smsnetwork.SMSNetDictionary;

/**
 * Command to remove a resource from the network dictionary
 *
 * @author Edoardo Raimondi
 */
public class RemoveResource implements Command {

    private SMSNetDictionary netDictionary;
    private String key;

    public RemoveResource(String key, SMSNetDictionary netDictionary){
        this.key = key;
        this.netDictionary = netDictionary;
    }

    public void execute() {
        netDictionary.removeResource(key);
        //TODO broadcast
    }
}
