package com.eis.communication.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of a INetworkDictionary
 *
 * @author Marco Cognolato
 */
public class SMSNetDictionary implements INetworkDictionary<String, String> {

    private final Map<String, String> dict = new HashMap<>();

    /**
     * Adds a resource to the network dictionary
     *
     * @param key      The key which defines the resource
     * @param resource The resource to add
     * @throws IllegalArgumentException If the key is invalid.
     *                                  A key is said to be valid only if it's composed of a single word
     */
    public void addResource(String key, String resource) {
        checkKeyValidity(key);
        dict.put(key, resource);
    }

    /**
     * Removes a resource from the dictionary
     *
     * @param key The key which defines the resource
     * @throws IllegalArgumentException If the key is invalid.
     *                                  A key is said to be valid only if it's composed of a single word
     */
    public void removeResource(String key) {
        checkKeyValidity(key);
        dict.remove(key);
    }

    /**
     * Returns a resource in the dictionary
     *
     * @param key The key which defines the resource to get
     * @return Returns a resource corresponding to the key if present in the dictionary,
     * else returns null
     * @throws IllegalArgumentException If the key is invalid.
     *                                  A key is said to be valid only if it's composed of a single word
     */
    public String getResource(String key) {
        checkKeyValidity(key);
        return dict.get(key);
    }

    /**
     * Checks if a given key is valid, else throws IllegalArgumentException.
     * A key is said to be valid only if it's composed of one word
     *
     * @param key The key to check
     */
    private void checkKeyValidity(String key) {
        if (key == null || !key.matches("^\\w+$"))
            throw new IllegalArgumentException("The given key is not valid! Given key was: " + key);
    }
}
