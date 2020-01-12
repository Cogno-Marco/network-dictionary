package com.eis.smsnetwork.commands;

import com.eis.communication.network.Command;
import com.eis.smsnetwork.SMSNetDictionary;


/**
 * Command to add a resource to the net dictionary
 *
 * @author Edoardo Raimondi
 */
public class AddResource implements Command {

    /*Using strings to be coherent to SMSNetDictionary class*/
    private String key;
    private String value;
    private SMSNetDictionary netDictionary;

    public AddResource(String key, String value, SMSNetDictionary netDictionary){
        this.key = key;
        this.value = value;
        this.netDictionary = netDictionary;
    }

    public void execute() {
        netDictionary.addResource(key, value);
        //TODO broadcast
    }
}
