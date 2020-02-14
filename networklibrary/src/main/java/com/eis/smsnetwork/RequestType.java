package com.eis.smsnetwork;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum contains the Request Types, which are the CODE placed at the beginning of every message
 * sent through the network. They identify the command contained in each message. Depending upon
 * the RequestType received, different actions are performed.
 * It is identifies by the listener receiver.
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public enum RequestType {

    // Requests to manage subscribers
    Invite("@"),
    AcceptInvitation("£"),
    AddPeer("$"),
    QuitNetwork("¥"),

    // Requests to manage the dictionary
    AddResource("è"),
    RemoveResource("é");

    private final String command;

    RequestType(String command) {
        this.command = command;
    }

    public String asString() {
        return command;
    }

    private static final Map<String, RequestType> lookup = new HashMap<>();

    /*
     * Populate the lookup table on loading time
     * The lookup table is used to change a command string into a RequestType
     * Trick learned online. Since that block is static it will get executed really early,
     * even before the constructor.
     *
     * @author Marco Cognolato
     */
    static {
        for (RequestType command : RequestType.values()) {
            lookup.put(command.asString(), command);
        }
    }

    /**
     * Returns the RequestType given the string identifier
     *
     * @param command The String command of this
     * @return Returns the corresponding RequestType if it exists, null otherwise
     * @author Marco Cognolato
     */
    public static RequestType get(String command) {
        return lookup.get(command);
    }
}
