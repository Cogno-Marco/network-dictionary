package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

public class BroadcastSenderTest {

    public static final SMSPeer PEER1 = SMSNetSubscribersTest.PEER1;
    public static final SMSPeer PEER2 = SMSNetSubscribersTest.PEER2;
    public static final SMSPeer PEER3 = SMSNetSubscribersTest.PEER3;

    private SMSNetSubscribers subscribers = new SMSNetSubscribers();

    @Before
    public void setup(){
        subscribers.addSubscriber(PEER1);
        subscribers.addSubscriber(PEER2);
        subscribers.addSubscriber(PEER3);
    }
    
}
