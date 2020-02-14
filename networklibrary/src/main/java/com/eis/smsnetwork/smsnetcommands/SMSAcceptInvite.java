package com.eis.smsnetwork.smsnetcommands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eis.communication.network.commands.CommandExecutor;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;
import com.eis.smsnetwork.SMSInvitation;
import com.eis.smsnetwork.SMSJoinableNetManager;

/**
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSAcceptInvite extends com.eis.communication.network.commands.AcceptInvite<SMSInvitation> {

    SMSJoinableNetManager netManager;

    /**
     * Constructor for SMSAcceptInvite command, requires data to work
     *
     * @param invitation The SMSInvitation to a network
     * @param netManager A valid SMSJoinableNetManager, used by the command
     */
    public SMSAcceptInvite(@NonNull SMSInvitation invitation, @NonNull SMSJoinableNetManager netManager) {
        super(invitation);
        this.netManager = netManager;
    }

    protected void execute() {
        SMSPeer inviter = invitation.getInviterPeer();
        SMSJoinableNetManager instance = SMSJoinableNetManager.getInstance();
        CommandExecutor.execute(new SMSQuitNetwork(instance.getNetSubscriberList(), instance));
        netManager.getNetSubscriberList().addSubscriber(inviter);
        SMSManager.getInstance().sendMessage(new SMSMessage(inviter, RequestType.AcceptInvitation.asString()));
        Log.d("ACCEPTINVITE_COMMAND", "Accepting invite from: " + inviter);
    }
}
