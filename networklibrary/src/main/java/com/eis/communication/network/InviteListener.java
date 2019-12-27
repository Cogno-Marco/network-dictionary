package com.eis.communication.network;

public interface InviteListener<U extends NetworkUser>{

    void onInvitationSent(U userInvited);

}
