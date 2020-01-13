package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSAcceptInvite extends com.eis.communication.network.commands.AcceptInvite<SMSPeer> {

    /**
     * Constructor for SMSAcceptInvite command, requires data to work
     *
     * @param inviter        The SMSPeer who sent the invitation to his network
     * @param netSubscribers The list of subscribers of this network (so they can be joined)
     */
    public SMSAcceptInvite(SMSPeer inviter, NetSubscriberList<SMSPeer> netSubscribers) {
        super(inviter, netSubscribers);
    }

    protected void execute() {
        //TODO other than sending the AcceptInvitation, you should send all the peers in my network,
        //so he can notify we were added at the same time
        SMSHandler.getInstance().sendMessage(new SMSMessage(inviter, RequestType.AcceptInvitation.asString()));
        CommandExecutor.execute(new SMSAddPeer(inviter, netSubscribers));
    }
}
