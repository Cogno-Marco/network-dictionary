package com.eis.communication.network;

/**
 * This enum contains the Request Types, which are the CODE placed at the beginning of every message
 * sent through the network. They identify the command contained in each message. Depending upon
 * the RequestType received, different actions are performed.
 * It is identifies by the listener receiver.
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public enum RequestType {

    // Requests to manage subscribers
    Invite("IN"),
    AcceptInvitation("AI"),
    AddPeer("AP"),
    RemovePeer("RP"),

    // Requests to manage the dictionary
    AddResource("AR"),
    RemoveResource("RR");

    private final String command;

    RequestType(String command) {
        this.command = command;
    }

    public String asString() {
        return command;
    }
}
