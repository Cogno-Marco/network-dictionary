package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;

/**
 * Command to add a resource to the net dictionary
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class AddResource extends com.eis.communication.network.commands.AddResource<String, String> {

    /**
     * Constructor for the AddResource command, needs the data to operate
     *
     * @param key           The key of the resource to add
     * @param value         The value of the resource to add
     * @param netDictionary The dictionary to add the resource in
     */
    public AddResource(@NonNull String key, @NonNull String value, @NonNull NetDictionary<String, String> netDictionary) {
        super(key, value, netDictionary);
    }

    /**
     * Adds the key-resource pair to the dictionary, then broadcasts the message
     */
    protected void execute() {
        netDictionary.addResource(key, value);
        //TODO broadcast
    }
}
