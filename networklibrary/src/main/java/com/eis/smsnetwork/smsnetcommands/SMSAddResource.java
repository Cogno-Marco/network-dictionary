package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.broadcast.BroadcastReceiver;
import com.eis.smsnetwork.broadcast.BroadcastSender;

/**
 * Command to add a resource to the net dictionary
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSAddResource extends com.eis.communication.network.commands.AddResource<String, String> {

    /**
     * Constructor for the SMSAddResource command, needs the data to operate
     *
     * @param key           The key of the resource to add
     * @param value         The value of the resource to add
     * @param netDictionary The dictionary to add the resource in
     */
    public SMSAddResource(@NonNull String key, @NonNull String value, @NonNull NetDictionary<String, String> netDictionary) {
        super(key, value, netDictionary);
    }

    /**
     * Adds the key-resource pair to the dictionary, then broadcasts the message
     *
     * @throws IllegalArgumentException if fields key or value contain a backslash as their last
     *                                  character.
     */
    protected void execute() {
        netDictionary.addResource(key, value);
        String addResourceMessage = RequestType.AddResource.asString() +
                BroadcastReceiver.FIELD_SEPARATOR + SMSNetDictionary.addEscapes(key) +
                BroadcastReceiver.FIELD_SEPARATOR + SMSNetDictionary.addEscapes(value);
        BroadcastSender.broadcastMessage(SMSJoinableNetManager.getInstance().getNetSubscriberList()
                .getSubscribers(), addResourceMessage);
    }
}
