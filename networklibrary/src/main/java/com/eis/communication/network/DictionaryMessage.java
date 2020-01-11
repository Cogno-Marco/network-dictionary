package com.eis.communication.network;


import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi
 */
class DictionaryMessage {
    //Builder used to construct a DictionaryMessage
    private final MessageBuilder builder = new MessageBuilder();

    /**
     * @param peer     The destination of the message.
     * @param key      The key of the resource.
     * @param resource The resource associated to the key.
     * @param command  defines what to do with the passed arguments.
     */
    DictionaryMessage(SMSPeer peer, String key, String resource, RequestType command) {
        builder.setPeer(peer);
        builder.addArguments(command.ordinal() + "", key, resource);
    }

    /**
     * @return The message ready to be sent
     */
    SMSMessage buildMessage() {
        return builder.buildMessage();
    }

}