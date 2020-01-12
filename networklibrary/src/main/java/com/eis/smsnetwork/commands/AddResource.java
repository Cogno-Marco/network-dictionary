package com.eis.smsnetwork.commands;

import androidx.annotation.NonNull;

import com.eis.communication.network.Command;
import com.eis.smsnetwork.SMSNetDictionary;

/**
 * Command to add a resource to the net dictionary
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public class AddResource implements Command {

    /*Using strings to be coherent to SMSNetDictionary class*/
    private String key;
    private String value;
    private SMSNetDictionary netDictionary;

    /**
     * Constructor for the AddResource command, needs the data to operate
     * @param key The key of the resource to add
     * @param value The value of the resource to add
     * @param netDictionary The dictionary to add the resource in
     */
    public AddResource(@NonNull String key,@NonNull String value,@NonNull SMSNetDictionary netDictionary){
        this.key = key;
        this.value = value;
        this.netDictionary = netDictionary;
    }

    /**
     * Adds the key-resource pair to the dictionary, then broadcasts the message
     */
    public void execute() {
        netDictionary.addResource(key, value);
        //TODO broadcast
    }
}
