package com.eis.smsnetwork;

import androidx.annotation.NonNull;

import com.eis.communication.network.commands.CommandExecutor;
import com.eis.communication.network.FailReason;
import com.eis.communication.network.Invitation;
import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.NetworkManager;
import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.smsnetcommands.AcceptInvite;
import com.eis.smsnetwork.smsnetcommands.AddResource;
import com.eis.smsnetwork.smsnetcommands.InvitePeer;
import com.eis.smsnetwork.smsnetcommands.RemoveResource;

/**
 * The manager class of the network.
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 */
public class SMSNetworkManager implements NetworkManager<String, String, SMSPeer, FailReason> {

    private NetSubscriberList<SMSPeer> netSubscribers = new SMSNetSubscriberList();
    private NetDictionary<String, String> netDictionary = new SMSNetDictionary();

    /**
     * @return netSubscribers
     */
    public NetSubscriberList<SMSPeer> getNetSubscriberList() {
        return netSubscribers;
    }

    /**
     * @return netDictionary
     */
    public NetDictionary<String, String> getNetDictionary() {
        return netDictionary;
    }

    /**
     * Starts a setResource request to the net
     *
     * @param key                 The key identifier for the resource.
     * @param value               The identified value of the resource.
     * @param setResourceListener Listener called on resource successfully saved or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void setResource(String key, String value, SetResourceListener<String, String, FailReason> setResourceListener) {
        CommandExecutor.execute(new AddResource(key, value, netDictionary));
    }

    /**
     * Starts a getResource request to the net
     *
     * @param key                 The key identifier for the resource.
     * @param getResourceListener Listener called on resource successfully retrieved or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void getResource(String key, GetResourceListener<String, String, FailReason> getResourceListener) {
        String resource = netDictionary.getResource(key);
        if (resource != null) getResourceListener.onGetResource(key, resource);
        else{
            //TODO: define FailReason for a resource not being on the net
        }
    }

    /**
     * Starts a remove resource request to the net
     *
     * @param key                    The key identifier for the resource.
     * @param removeResourceListener Listener called on resource successfully removed or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void removeResource(String key, RemoveResourceListener<String, FailReason> removeResourceListener) {
        CommandExecutor.execute(new RemoveResource(key, netDictionary));
    }

    /**
     * Starts an invite operation to the net
     *
     * @param peer           The address of the user to invite to join the network.
     * @param inviteListener Listener called on user invited or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void invite(SMSPeer peer, InviteListener<SMSPeer, FailReason> inviteListener) {
        CommandExecutor.execute(new InvitePeer(peer));
    }

    /**
     * Accepts a given join invitation.
     *
     * @param invitation The invitation previously received.
     */
    public void acceptJoinInvitation(Invitation invitation) {
        // N.B. this function provides an implementation for automatically joining a network.
        // while SMSJoinableNetManager uses this function by sending the request to the user
        // using a listener set by the user.
        CommandExecutor.execute(new AcceptInvite((SMSPeer)invitation.getInviterPeer(), netSubscribers));
    }

    /**
     * Sets a given list of subscribers, to provide the network
     * with your own implementation
     *
     * @param list A NetSubscriberList of type <SMSPeer> to provide
     */
    public void setNetSubscriberList(@NonNull NetSubscriberList<SMSPeer> list) {

    }

    /**
     * Sets a given dictionary of resources, to provide the network
     * with your own implementation
     *
     * @param dictionary A NetDictionary of type <String,String> to provide
     */
    public void setNetDictionary(@NonNull NetDictionary<String, String> dictionary) {

    }
}
