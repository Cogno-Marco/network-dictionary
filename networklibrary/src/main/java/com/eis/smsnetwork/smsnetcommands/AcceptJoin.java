package com.eis.smsnetwork.smsnetcommands;

import com.eis.communication.network.Command;
import com.eis.communication.network.CommandExecutor;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.commands.AddPeer;
import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.RequestType;

public class AcceptJoin implements Command {

    private SMSPeer inviter;
    private NetSubscriberList netSubscribers;

    /**
     * Constructor for AcceptJoin command, requires data to work
     * @param inviter The SMSPeer inviter to his network
     * @param netSubscribers The list of subscribers of this network (so they can be joined)
     */
    public AcceptJoin(SMSPeer inviter, NetSubscriberList netSubscribers){
        this.inviter = inviter;
        this.netSubscribers = netSubscribers;
    }

    public void execute(){
        //TODO other than sending the AcceptInvitation, you should send all the peers in my network,
        //so he can notify we were added at the same time
        SMSHandler.getInstance().sendMessage(new SMSMessage(inviter, RequestType.AcceptInvitation.asString()));
        CommandExecutor.execute(new AddPeer(inviter, netSubscribers));
    }
}
