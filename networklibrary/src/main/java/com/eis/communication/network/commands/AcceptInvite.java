package com.eis.communication.network.commands;

import com.eis.communication.Peer;
import com.eis.communication.network.NetSubscriberList;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public abstract class AcceptInvite<T extends Peer> extends Command {

    protected final T inviter;
    protected final NetSubscriberList<T> netSubscribers;

    /**
     * Constructor for SMSAcceptInvite command, requires data to work
     *
     * @param inviter        The Peer who sent the invitation to his network
     * @param netSubscribers The list of subscribers of this network (so they can be joined)
     */
    public AcceptInvite(T inviter, NetSubscriberList<T> netSubscribers) {
        this.inviter = inviter;
        this.netSubscribers = netSubscribers;
    }

    protected abstract void execute();
}
