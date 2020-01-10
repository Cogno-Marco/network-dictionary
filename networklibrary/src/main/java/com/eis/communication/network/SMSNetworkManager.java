package com.eis.communication.network;

/**
 * The manager class of the network.
 */
public class SMSNetworkManager /*implements NetworkManager*/ {

    private SMSNetSubscribers netSubscribers = new SMSNetSubscribers();
    private SMSNetDictionary netDictionary = new SMSNetDictionary();

    public SMSNetSubscribers getNetSubscribers(){ return netSubscribers;  }
}
