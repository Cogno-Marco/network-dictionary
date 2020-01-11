package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

/**
 * The manager class of the network.
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public class SMSNetworkManager /*implements NetworkManager<String, String, SMSPeer, FailReason>*/ {

    private SMSNetSubscribers netSubscribers = new SMSNetSubscribers();
    private SMSNetDictionary netDictionary = new SMSNetDictionary();

    /**
     * @return netSubscribers
     */
    public SMSNetSubscribers getNetSubscribers(){ return netSubscribers; }

    /**
     * @return netDictionary
     */
    public SMSNetDictionary getNetDictionary(){ return netDictionary; }


    /**
     * Accepts a given join invitation.
     * @param invitation The invitation previously received.
     */
    public void acceptJoinInvitation(Invitation invitation) {
        // N.B. this function provides an implementation for automatically joining a network.
        // while SMSJoinableNetManager uses this function by sending the request to the user
        // using a listener set by the user.
    }
}
