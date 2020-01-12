package com.eis.communication.network;


import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.MessageBuilder;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSNetMessageBuilder;

/**
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi
 */
class Message {
    //Builder used to construct a DictionaryMessage
    private final SMSNetMessageBuilder builder = new SMSNetMessageBuilder();

    /**
     * Constructor to create a dictionary command
     *
     * @param peer     The destination of the message.
     * @param command  Defines what to do with the passed arguments.
     * @param key      The key of the resource.
     * @param resource The resource associated to the key.
     */
    Message(SMSPeer peer, RequestType command, String key, String resource ) {
        builder.setPeer(peer);
        builder.setRequest(command);
        builder.addArguments(key, resource);
    }

    /**
     * Constructor to create a subscribers command
     *
     * @param peer          The destination of the message
     * @param command       Defines what to do with the passed arguments
     * @param peerToManage  The peer to add or remove from the subscribers list
     */
    Message(SMSPeer peer, RequestType command, SMSPeer peerToManage ) {
        builder.setPeer(peer);
        builder.setRequest(command);
        builder.addArguments(peerToManage.toString());
    }

    /**
     * @return The message ready to be sent
     */
    SMSMessage buildMessage() {
        return builder.buildMessage();
    }

}