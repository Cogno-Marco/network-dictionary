package com.eis.smsnetwork;

import androidx.annotation.NonNull;

import com.eis.communication.network.FailReason;

public class SMSFailReason extends FailReason {

    /**
     * Error when a resource is not available
     */
    public static final SMSFailReason NO_RESOURCE = new SMSFailReason("ResourceNotAvailable");

    /**
     * Error when a peer is not available
     */
    public static final SMSFailReason NO_PEER = new SMSFailReason("PeerNotAvailable");

    /**
     * Error when a request takes too long
     */
    public static final SMSFailReason REQUEST_EXPIRED = new SMSFailReason("RequestExpired");

    /**
     * Error when a message couldn't be sent
     */
    public static final SMSFailReason MESSAGE_SEND_ERROR  = new SMSFailReason("ErrorWhileSendingMessage");

    /**
     * Private constructor as suggested in the TypeSafe enum pattern.
     *
     * @param name The name of the enumeration.
     */
    protected SMSFailReason(final @NonNull String name) {
        super(name);
    }

    /**
     * toString override.
     *
     * @return The name of the fail reason.
     */
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
