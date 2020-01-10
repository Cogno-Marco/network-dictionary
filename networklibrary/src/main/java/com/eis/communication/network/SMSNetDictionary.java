package com.eis.communication.network;

/**
 * Concrete implementation of a INetworkDictionary
 *
 * @author Marco Cognolato
 */
public class SMSNetDictionary implements INetworkDictionary<String, String> {

    /**
     * Adds a resource to the network dictionary
     * @param key The key which defines the resource
     * @param resource The resource to add
     */
    public void addResource(String key, String resource){

    }

    /**
     * Removes a resource from the dictionary
     * @param key The key which defines the resource
     */
    public void removeResource(String key){

    }

    /**
     * Returns a resource in the dictionary
     * @param key The key which defines the resource to get
     * @return Returns a resource corresponding to the key if present in the dictionary,
     * else returns null
     */
    public String getResource(String key){
        return null;
    }
}
