package com.eis.communication.network.commands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;

/**
 * Command to remove a resource from the network dictionary
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public abstract class RemoveResource<K, R> extends Command {

    protected final NetDictionary<K, R> netDictionary;
    protected final K key;

    /**
     * Constructor for the SMSRemoveResource command, needs the data to operate
     *
     * @param key           The key identifier of the resource to remove
     * @param netDictionary The dictionary to remove the resource from
     */
    public RemoveResource(@NonNull K key, @NonNull NetDictionary<K, R> netDictionary) {
        this.key = key;
        this.netDictionary = netDictionary;
    }

    /**
     * Removes a Resource from the dictionary, then broadcasts it to the net
     */
    protected abstract void execute();
}
