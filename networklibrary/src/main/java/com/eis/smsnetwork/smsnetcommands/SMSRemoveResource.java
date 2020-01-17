package com.eis.smsnetwork.smsnetcommands;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.broadcast.BroadcastReceiver;
import com.eis.smsnetwork.broadcast.BroadcastSender;

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
     *
     * @throws IllegalArgumentException if fields key or value contain a backslash as their last
     *                                  character.
     */
    protected void execute() {
        netDictionary.removeResource(key);
        String removeResourceMessage =
                RequestType.RemoveResource.asString() + BroadcastReceiver.FIELD_SEPARATOR +
                        SMSNetDictionary.addEscapes(key);
        BroadcastSender.broadcastMessage(SMSJoinableNetManager.getInstance().getNetSubscriberList()
                .getSubscribers(), removeResourceMessage);
    }
}
