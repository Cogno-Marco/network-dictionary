package com.eis.communication.network;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 *
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi
 */
public class SubscribersMessage {

    private MessageBuilder builder = new MessageBuilder();

    /**
     *
     * @param peer destination of the message
     * @param peerToAdd peer to be added/removed to the subscribes
     * @param command information (to add or remove it)
     */
    public SubscribersMessage(SMSPeer peer, SMSPeer peerToAdd, RequestType command){
        String subscriber = peerToAdd.toString();
        builder.setPeer(peer);
        builder.addArguments(command.ordinal()+"", subscriber);
    }

    /**
     * @return the message ready to be sent
     */
    public SMSMessage build(){
        return builder.buildMessage();
    }
}
