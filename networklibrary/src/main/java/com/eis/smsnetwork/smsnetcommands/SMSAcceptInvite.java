package com.eis.smsnetwork.smsnetcommands;

import android.util.Log;

import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSJoinableNetManager;
import com.eis.smsnetwork.SMSNetDictionary;
import com.eis.smsnetwork.SMSNetSubscriberList;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSAcceptInvite extends com.eis.communication.network.commands.AcceptInvite<SMSPeer> {

    /**
     * Constructor for SMSAcceptInvite command, requires data to work
     *
     * @param inviter        The SMSPeer who sent the invitation to his network
     */
    public SMSAcceptInvite(SMSPeer inviter) {
        super(inviter);
    }

    protected void execute() {
        SMSJoinableNetManager.getInstance().setNetSubscriberList(new SMSNetSubscriberList());
        SMSJoinableNetManager.getInstance().setNetDictionary(new SMSNetDictionary());
        SMSJoinableNetManager.getInstance().getNetSubscriberList().addSubscriber(inviter);
        SMSManager.getInstance().sendMessage(new SMSMessage(inviter, RequestType.AcceptInvitation.asString()));
        Log.d("ACCEPTINVITE_COMMAND", "Accepting invite from: " + inviter);
    }
}
