package com.eis.communication.network;

/**
 * Enumeration of reasons why a resource get or set wasn't performed correctly.
 * Can be extended in order to have more reasons.
 * @author Luca Crema
 */
public enum FailReason {
    NETWORK_ERROR,
    NO_NETWORK,
    GENERIC_FAIL
}
