package com.eis.communication.network.commands;

import androidx.annotation.NonNull;

import com.eis.communication.Peer;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public abstract class InvitePeer<T extends Peer> extends Command {

    protected final T peerToInvite;

    /**
     * Constructor for the InvitePeer command, requires data to work
     *
     * @param peerToInvite The Peer to invite to the network
     */
    public InvitePeer(@NonNull T peerToInvite) {
        this.peerToInvite = peerToInvite;
    }

    /**
     * Execute the InvitePeer logic: sends a request to join a network
     */
    protected abstract void execute();
}
