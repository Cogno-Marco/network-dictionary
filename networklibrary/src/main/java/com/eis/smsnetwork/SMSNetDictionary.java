package com.eis.smsnetwork;


import com.eis.communication.network.NetDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of a NetDictionary
 *
 * @author Marco Cognolato
 */
public class SMSNetDictionary implements NetDictionary<String, String> {

    Map<String, String> dict = new HashMap<>();

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
     * A key is said to be valid only if it's composed of one word.
     * <p>
     * This is because when a Key-Resource pair gets embedded in an SMSMessage they need a separator.
     * We decided to use a space as a separator, so if we allow multiple words keys we run into a
     * problem: understanding how to distinguish a key from a resource.
     * For example if a message has "a b c" is it the key "a" and the resource "b c", or the
     * key "a b" and the resource "c".?
     * <p>
     * The immediate response would be "use a different separator" but the same logic applies to
     * that separator. For example if we use "_" as a separator, the file
     * really_big_image.jpg would be separated into the key "really" and the resource "big image.jpg"
     * which is wrong.
     *
     * @param key The key to check
     */
    private void checkKeyValidity(String key) {
        if (key == null || !key.matches("^\\w+$"))
            throw new IllegalArgumentException("The given key is not valid! Given key was: " + key);
    }
}
