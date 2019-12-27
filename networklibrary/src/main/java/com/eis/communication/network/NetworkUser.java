package com.eis.communication.network;

import com.eis.communication.Peer;

public interface NetworkUser<P extends Peer>{

    P getPeer();

}
