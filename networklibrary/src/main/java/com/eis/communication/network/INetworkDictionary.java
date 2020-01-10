package com.eis.communication.network;

/**
 * Interface to define a network dictionary
 * @param <K> The Key for each resource
 * @param <R> The Resource
 *
 * @author Marco Cognolato
 */
public interface INetworkDictionary<K,R>{

    /**
     * Adds a resource to the network dictionary
     * @param key The key which defines the resource
     * @param resource The resource to add
     */
    void addResource(K key, R resource);

    /**
     * Removes a resource from the dictionary
     * @param key The key which defines the resource
     */
    void removeResource(K key);

    /**
     * Returns a resource in the dictionary
     * @param key The key which defines the resource to get
     * @return Returns a resource corresponding to the key if present in the dictionary,
     * else returns null
     */
    R getResource(K key);
}
