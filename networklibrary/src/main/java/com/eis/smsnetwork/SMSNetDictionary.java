package com.eis.smsnetwork;

import androidx.annotation.NonNull;

import com.eis.communication.network.NetDictionary;
import com.eis.smsnetwork.broadcast.BroadcastReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Concrete implementation of a NetDictionary
 *
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSNetDictionary implements NetDictionary<String, String> {

    private final Map<String, String> dict = new HashMap<>();

    /**
     * Adds a resource to the network dictionary
     *
     * @param key      The key which defines the resource
     * @param resource The resource to add
     * @throws IllegalArgumentException if one of the arguments contains a backslash as its last
     *                                  character.
     */
    public void addResource(String key, String resource) {
        checkValidity(key);
        checkValidity(resource);
        dict.put(key, resource);
    }

    /**
     * Removes a resource from the dictionary
     *
     * @param key The key which defines the resource
     */
    public void removeResource(String key) {
        dict.remove(key);
    }

    /**
     * Returns a resource in the dictionary
     *
     * @param key The key which defines the resource to get
     * @return Returns a resource corresponding to the key if present in the dictionary,
     * else returns null
     */
    public String getResource(String key) {
        return dict.get(key);
    }

    /**
     * Removes all keys and resources from the dictionary.
     */
    public void clear() {
        dict.clear();
    }

    /**
     * Adds a resource parsed from an SMS to the network dictionary
     *
     * @param key      The key which defines the resource
     * @param resource The resource to add
     * @throws IllegalArgumentException if one of the arguments contains a backslash as its last
     *                                  character.
     */
    public void addResourceFromSMS(@NonNull String key, @NonNull String resource) {
        addResource(removeEscapes(key), removeEscapes(resource));
    }

    /**
     * Removes a resource from the dictionary, given a key parsed from an SMS
     *
     * @param key The key which defines the resource
     * @throws IllegalArgumentException if the argument contains a backslash as its last
     *                                  character.
     */
    public void removeResourceFromSMS(@NonNull String key) {
        removeResource(removeEscapes(key));
    }

    /**
     * Returns a String containing all keys and resources from the dictionary, in the format
     * "key1 resource1 key2 resource2", after calling {@link SMSNetDictionary#addEscapes(String)} on each of them.
     *
     * @return All keys and resources present in the dictionary, ready to be sent through an SMS.
     * If there are none, returns null.
     */
    public String getAllKeyResourcePairsForSMS() {
        Set<String> keySet = dict.keySet();
        ArrayList<String> keys = new ArrayList<>(keySet.size());
        ArrayList<String> resources = new ArrayList<>(keySet.size());
        for (String key : keySet) {
            String resource = getResource(key);
            resources.add(addEscapes(resource));
            keys.add(addEscapes(key));
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            result.append(keys.get(i)).append(BroadcastReceiver.FIELD_SEPARATOR);
            result.append(resources.get(i)).append(BroadcastReceiver.FIELD_SEPARATOR);
        }
        try {
            return result.deleteCharAt(result.length() - 1).toString();
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Adds a backslash before every character corresponding to
     * {@link com.eis.smsnetwork.broadcast.BroadcastReceiver#FIELD_SEPARATOR}. Needed when reading
     * keys and resources before sending them through an SMS.
     *
     * @param string The String where to escape every occurrence of
     *               {@link com.eis.smsnetwork.broadcast.BroadcastReceiver#FIELD_SEPARATOR}.
     * @return A String where every
     * {@link com.eis.smsnetwork.broadcast.BroadcastReceiver#FIELD_SEPARATOR} is escaped with a
     * backslash.
     */
    public static String addEscapes(@NonNull String string) {
        String replacement = Matcher.quoteReplacement("\\" + BroadcastReceiver.FIELD_SEPARATOR);
        return string.replaceAll(BroadcastReceiver.FIELD_SEPARATOR, replacement);
    }

    /**
     * Whenever a backslash precedes a {@link BroadcastReceiver#FIELD_SEPARATOR}, removes that
     * backslash. Needed when reading keys and resources from an SMS, before adding them to the
     * dictionary.
     *
     * @param string The String where to remove every backslash preceding an occurrence of
     *               {@link com.eis.smsnetwork.broadcast.BroadcastReceiver#FIELD_SEPARATOR}.
     * @return A String where every backslash preceding an occurrence of
     * {@link com.eis.smsnetwork.broadcast.BroadcastReceiver#FIELD_SEPARATOR} was removed.
     */
    public static String removeEscapes(@NonNull String string) {
        String toReplaceRegex = Matcher.quoteReplacement("\\" + BroadcastReceiver.FIELD_SEPARATOR);
        return string.replaceAll(toReplaceRegex, BroadcastReceiver.FIELD_SEPARATOR);
    }

    /**
     * Checks if a given key or resource is valid, else throws IllegalArgumentException.
     * A key or resource is said to be valid only if its last character is not a backslash "\".
     * <p>
     * This is because when a Key-Resource pair gets embedded in an SMSMessage they need a separator.
     * We decided to use {@link BroadcastReceiver#FIELD_SEPARATOR} as a separator between different
     * objects in SMS messages, and {@link BroadcastReceiver#FIELD_SEPARATOR} characters contained
     * in keys and resources are replaced by a backslash and a
     * {@link BroadcastReceiver#FIELD_SEPARATOR}, so if we allow keys and resource to have a
     * backslash character as their last one, this might result in two different objects being
     * considered one single object.
     *
     * @param string The string to check
     * @throws IllegalArgumentException if the argument contains a backslash as its last character.
     */
    private static void checkValidity(String string) {
        if (string == null || string.matches("\\p{all}*\\\\$"))
            throw new IllegalArgumentException("The given string is not valid! Given string was: " + string);
    }
}
