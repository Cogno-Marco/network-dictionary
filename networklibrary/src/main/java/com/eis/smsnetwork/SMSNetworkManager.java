package com.eis.smsnetwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eis.communication.MessageParseStrategy;
import com.eis.communication.network.commands.CommandExecutor;
import com.eis.communication.network.NetDictionary;
import com.eis.communication.network.NetSubscriberList;
import com.eis.communication.network.NetworkManager;
import com.eis.communication.network.listeners.GetResourceListener;
import com.eis.communication.network.listeners.InviteListener;
import com.eis.communication.network.listeners.RemoveResourceListener;
import com.eis.communication.network.listeners.SetResourceListener;
import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSMessageHandler;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.broadcast.BroadcastReceiver;
import com.eis.smsnetwork.smsnetcommands.SMSAddResource;
import com.eis.smsnetwork.smsnetcommands.SMSSendInvitation;
import com.eis.smsnetwork.smsnetcommands.SMSRemoveResource;

import java.util.ArrayList;

/**
 * The manager class of the network.
 *
 * @author Edoardo Raimondi
 * @author Marco Cognolato
 * @author Giovanni Velludo
 */
public class SMSNetworkManager implements NetworkManager<String, String, SMSPeer, SMSFailReason> {

    private NetSubscriberList<SMSPeer> netSubscribers = new SMSNetSubscriberList();
    private NetDictionary<String, String> netDictionary = new SMSNetDictionary();
    private ArrayList<SMSPeer> invitedPeers = new ArrayList<SMSPeer>();

    private String LOG_KEY = "NET_MANAGER";

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
     * @return A {@link NetSubscriberList} containing Peers who were invited to join the network and
     * haven't answered yet.
     */
    public ArrayList<SMSPeer> getInvitedPeers() {
        return invitedPeers;
    }

    /**
     * Starts a setResource request to the net
     *
     * @param key                 The key identifier for the resource. It cannot have a backslash as
     *                            its last character.
     * @param value               The identified value of the resource.It cannot have a backslash as
     *                            its last character.
     * @param setResourceListener Listener called on resource successfully saved or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void setResource(String key, String value, SetResourceListener<String, String, SMSFailReason> setResourceListener) {
        try {
            CommandExecutor.execute(new SMSAddResource(key, value, netDictionary));
        } catch (Exception e) {
            Log.e(LOG_KEY, "There's been an error: " + e);
            setResourceListener.onResourceSetFail(key, value, SMSFailReason.MESSAGE_SEND_ERROR);
            return;
        }
        setResourceListener.onResourceSet(key, value);
    }

    /**
     * Starts a getResource request to the net
     *
     * @param key                 The key identifier for the resource. It cannot have a backslash as
     *                            its last character.
     * @param getResourceListener Listener called on resource successfully retrieved or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void getResource(String key, GetResourceListener<String, String, SMSFailReason> getResourceListener) {
        String resource = netDictionary.getResource(key);
        if (resource != null) getResourceListener.onGetResource(key, resource);
        else {
            getResourceListener.onGetResourceFailed(key, SMSFailReason.NO_RESOURCE);
        }
    }

    /**
     * Starts a remove resource request to the net
     *
     * @param key                    The key identifier for the resource. It cannot have a backslash
     *                               as its last character.
     * @param removeResourceListener Listener called on resource successfully removed or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void removeResource(String key, RemoveResourceListener<String, SMSFailReason> removeResourceListener) {
        try {
            CommandExecutor.execute(new SMSRemoveResource(key, netDictionary));
        } catch (Exception e) {
            Log.e(LOG_KEY, "There's been an error: " + e);
            removeResourceListener.onResourceRemoveFail(key, SMSFailReason.MESSAGE_SEND_ERROR);
            return;
        }
        removeResourceListener.onResourceRemoved(key);
    }

    /**
     * Starts an invite operation to the net
     *
     * @param peer           The address of the user to invite to join the network.
     * @param inviteListener Listener called on user invited or on fail.
     * @author Marco Cognolato
     */
    @Override
    public void invite(SMSPeer peer, InviteListener<SMSPeer, SMSFailReason> inviteListener) {
        try {
            SMSInvitation invitation = new SMSInvitation(peer);
            CommandExecutor.execute(new SMSSendInvitation(invitation, this));
        } catch (Exception e) {
            Log.e(LOG_KEY, "There's been an error: " + e);
            inviteListener.onInvitationNotSent(peer, SMSFailReason.MESSAGE_SEND_ERROR);
            return;
        }
        inviteListener.onInvitationSent(peer);
    }

    /**
     * Sets a given list of subscribers, to provide the network
     * with your own implementation
     *
     * @param list A NetSubscriberList of type <SMSPeer> to provide
     */
    public void setNetSubscriberList(@NonNull NetSubscriberList<SMSPeer> list) {
        for (SMSPeer sub : list.getSubscribers())
            netSubscribers.addSubscriber(sub);
    }

    /**
     * Sets a given dictionary of resources, to provide the network
     * with your own implementation
     *
     * @param dictionary A NetDictionary of type <String,String> to provide
     */
    public void setNetDictionary(@NonNull NetDictionary<String, String> dictionary) {
        netDictionary = dictionary;
    }

    /**
     * Setups all the basic android-related operations to let the network function
     *
     * @param context The android context to make this work on
     */
    public void setup(Context context) {
        SMSManager.getInstance().setReceivedListener(BroadcastReceiver.class,
                context.getApplicationContext());
        SMSMessageHandler.getInstance()
                .setMessageParseStrategy(new MessageParseStrategy<String, SMSPeer, SMSMessage>() {
                    private final String HIDDEN_CHARACTER = "¤";

                    @Override
                    public SMSMessage parseMessage(String channelData, SMSPeer channelPeer) {
                        if (!channelData.startsWith(HIDDEN_CHARACTER))
                            return null;
                        String messageData = channelData.substring(1);
                        return new SMSMessage(channelPeer, messageData);
                    }

                    @Override
                    public String parseData(SMSMessage message) {
                        return HIDDEN_CHARACTER + message.getData();
                    }
                });
    }
}
