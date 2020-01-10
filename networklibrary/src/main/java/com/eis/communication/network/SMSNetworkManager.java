package com.eis.communication.network;

/**
 * The manager class of the network.
 *
 * @author Edoardo Raimondi
 */
public class SMSNetworkManager /*implements NetworkManager*/ {

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
}
