package com.eis.communication.network.commands;

import com.eis.communication.Peer;
import com.eis.communication.network.NetSubscriberList;

/**
 * Command to accept an Invitation to the network
 *
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public abstract class AcceptInvite<T extends Peer> extends Command {

    protected final T inviter;

    /**
     * Constructor for AcceptInvite command, requires data to work
     *
     * @param inviter        The Peer who sent the invitation to his network
     */
    public AcceptInvite(T inviter) {
        this.inviter = inviter;
    }

    /**
     * Invites a peer to join a network
     */
    protected abstract void execute();
}
