package com.eis.communication.network.commands;

import androidx.annotation.NonNull;

import com.eis.communication.network.Command;
import com.eis.communication.network.NetDictionary;


/**
 * Command to remove a resource from the network dictionary
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 */
public class RemoveResource implements Command {

    private NetDictionary netDictionary;
    private String key;

    /**
     * Constructor for the RemoveResource command, needs the data to operate
     *
     * @param key           The key identifier of the resource to remove
     * @param netDictionary The dictionary to remove the resource from
     */
    public RemoveResource(@NonNull String key, @NonNull NetDictionary netDictionary) {
        this.key = key;
        this.netDictionary = netDictionary;
    }

    /**
     * Removes a Resource from the dictionary, then broadcasts it to the net
     */
    public void execute() {
        netDictionary.removeResource(key);
        //TODO broadcast
    }
}
