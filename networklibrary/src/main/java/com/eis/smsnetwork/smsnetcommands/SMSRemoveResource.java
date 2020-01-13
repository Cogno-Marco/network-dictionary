package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;

/**
 * Command to remove a resource from the network dictionary
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSRemoveResource extends com.eis.communication.network.commands.RemoveResource<String, String> {

    /**
     * Constructor for the SMSRemoveResource command, needs the data to operate
     *
     * @param key           The key identifier of the resource to remove
     * @param netDictionary The dictionary to remove the resource from
     */
    public SMSRemoveResource(@NonNull String key, @NonNull NetDictionary<String, String> netDictionary) {
        super(key, netDictionary);
    }

    /**
     * Removes a Resource from the dictionary, then broadcasts it to the net
     */
    protected void execute() {
        netDictionary.removeResource(key);
        //TODO broadcast
    }
}
