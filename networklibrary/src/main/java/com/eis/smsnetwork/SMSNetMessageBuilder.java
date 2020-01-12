package com.eis.smsnetwork;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * This class is similar to the Director part of the
 * <a href="https://refactoring.guru/design-patterns/builder">Builder Design Pattern</a>,
 * which also works as a Builder for the same pattern.
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 */
public class SMSNetMessageBuilder extends MessageBuilder {

    private String commandMessage = null;
    private String messageText = null;

    /**
     * Constructor for this builder
     */
    public SMSNetMessageBuilder() {
    }

    /**
     * Sets a peer as a Peer for this SMSMessage
     *
     * @param peer The peer to set
     * @return Returns an instance of this builder to concatenate calls
     */
    @Override
    public SMSNetMessageBuilder setPeer(SMSPeer peer) {
        super.setPeer(peer);
        return this;
    }

    /**
     * Sets the given request as the first parameter of the message
     *
     * @param command The RequestType to set
     * @return Returns an instance of this builder to concatenate calls
     */
    public SMSNetMessageBuilder setRequest(RequestType command) {
        commandMessage = command.asString();
        return this;
    }

    /**
     * Adds given arguments to the message, separated by a space
     *
     * @param message The parameters to add
     * @return Returns an instance of this builder to concatenate calls
     * @author Marco Cognolato
     */
    @Override
    public SMSNetMessageBuilder addArguments(String... message) {
        //convert input to string
        String input = "";
        if (message == null) {
            input = "/";
        } else {
            StringBuilder builder = new StringBuilder(input);
            for (String arg : message) {
                builder.append(arg);
                builder.append(" ");
            }
            input = builder.toString().trim();
        }

        //add new input to old one
        if (messageText == null)
            messageText = input;
        else
            messageText += " " + input;
        return this;
    }

    /**
     * @return The message ready to be sent
     */
    @Override
    public SMSMessage buildMessage() {
        super.addArguments(commandMessage);
        if (messageText != null)
            super.addArguments(messageText);
        return super.buildMessage();
    }
}
