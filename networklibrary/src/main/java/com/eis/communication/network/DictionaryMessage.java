package com.eis.communication.network;


import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import java.util.Dictionary;

/**
 *
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi
 */
public class DictionaryMessage {
    //Builder used to construct a DictionaryMessage
    private MessageBuilder builder = new MessageBuilder();

    /**
     * @param peer destination of the message
     * @param key of the resource
     * @param resource
     * @param command information (to add or remove it)
     */
    DictionaryMessage(SMSPeer peer, String key, String resource, RequestType command){
        builder.setPeer(peer);
        builder.addArguments(command.ordinal()+"", key, resource);
    }

    /**
     * @return The message ready to be sent
     */
    public SMSMessage buildMessage(){
        return builder.buildMessage();
    }

}