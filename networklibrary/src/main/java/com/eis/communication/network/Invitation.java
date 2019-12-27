package com.eis.communication.network;

public interface Invitation<U extends NetworkUser>{

    U getInviter();

}
