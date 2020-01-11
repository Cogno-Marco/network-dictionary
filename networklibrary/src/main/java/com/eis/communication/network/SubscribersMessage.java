package com.eis.communication.network;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 *
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public class SubscribersMessage extends MessageBuilder {

    private String commandMessage = null;
    private String[] messageText = null;
    private SMSPeer peerToAdd = null;

    public SubscribersMessage(){ }

    @Override
    public SubscribersMessage setPeer(SMSPeer peer){
        super.setPeer(peer);
        return this;
    }

    public SubscribersMessage setRequest(RequestType command){
        commandMessage = command.asString();
        return this;
    }

    public SubscribersMessage setPeerToAdd(SMSPeer peerToAdd){
        this.peerToAdd = peerToAdd;
        return this;
    }

    @Override
    public SubscribersMessage addArguments(String... message){
        messageText = message;
        return this;
    }

    /**
     * @return the message ready to be sent
     */
    @Override
    public SMSMessage buildMessage(){
        super.addArguments(commandMessage, peerToAdd.toString());
        for(String command : messageText)
            super.addArguments(command);
        return super.buildMessage();
    }
}
