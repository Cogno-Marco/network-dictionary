package com.eis.smsnetwork;

import com.eis.communication.network.FailReason;
import com.eis.communication.network.Invitation;
import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.communication.network.NetworkManager;
import com.eis.smslibrary.SMSPeer;

/**
 * The manager class of the network.
 *
 * @author Edoardo Raimondi, Marco Cognolato
 */
public class SMSNetworkManager implements NetworkManager<String, String, SMSPeer, FailReason> {

    private SMSNetSubscriberList netSubscribers = new SMSNetSubscriberList();
    private SMSNetDictionary netDictionary = new SMSNetDictionary();

    /**
     * @return netSubscribers
     */
    public SMSNetSubscriberList getNetSubscriberList(){ return netSubscribers; }

    /**
     * @return netDictionary
     */
    public SMSNetDictionary getNetDictionary(){ return netDictionary; }

    /**
     * Starts a setResource request to the net
     * @param key                 The key identifier for the resource.
     * @param value               The identified value of the resource.
     * @param setResourceListener Listener called on resource successfully saved or on fail.
     *
     * @author Marco Cognolato
     */
    @Override
    public void setResource(String key, String value, SetResourceListener<String, String, FailReason> setResourceListener) {

    }

    /**
     * Starts a getResource request to the net
     * @param key                 The key identifier for the resource.
     * @param getResourceListener Listener called on resource successfully retrieved or on fail.
     *
     * @author Marco Cognolato
     */
    @Override
    public void getResource(String key, GetResourceListener<String, String, FailReason> getResourceListener) {

    }

    /**
     * Starts a remove resource request to the net
     * @param key                    The key identifier for the resource.
     * @param removeResourceListener Listener called on resource successfully removed or on fail.
     *
     * @author Marco Cognolato
     */
    @Override
    public void removeResource(String key, RemoveResourceListener<String, FailReason> removeResourceListener) {

    }

    /**
     * Starts an invite operation to the net
     * @param peer           The address of the user to invite to join the network.
     * @param inviteListener Listener called on user invited or on fail.
     *
     * @author Marco Cognolato
     */
    @Override
    public void invite(SMSPeer peer, InviteListener<SMSPeer, FailReason> inviteListener) {

    }

    /**
     * Accepts a given join invitation.
     * @param invitation The invitation previously received.
     */
    public void acceptJoinInvitation(Invitation invitation) {
        // N.B. this function provides an implementation for automatically joining a network.
        // while SMSJoinableNetManager uses this function by sending the request to the user
        // using a listener set by the user.
    }
}
