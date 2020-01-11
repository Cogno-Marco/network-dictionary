package com.eis.communication.network;

/**
 * This enum contains the Request Types, which are the CODE placed at the beginning of every message
 * sent through the network. They identify the command contained in each message. Depending upon
 * the RequestType received, different actions are performed.
 * It is identifies by the listener receiver.
 *
 * @author Edoardo Raimondi
 */
public enum RequestType {
    //Request to manage the subscribers
    AddPeer,
    RemovePeer,

    //Request to manage the dictionary
    AddResource,
    RemoveResource
}
