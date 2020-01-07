package com.eis.communication.network;

import com.eis.communication.Peer;

public interface InviteListener<P extends Peer> {

    void onInvitationSent(P invtedPeer);

}
