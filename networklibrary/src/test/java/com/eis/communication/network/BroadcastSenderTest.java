package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

import org.junit.Before;

public class BroadcastSenderTest {

    private static final SMSPeer PEER1 = SMSNetSubscribersTest.PEER1;
    private static final SMSPeer PEER2 = SMSNetSubscribersTest.PEER2;
    private static final SMSPeer PEER3 = SMSNetSubscribersTest.PEER3;

    private final SMSNetSubscribers subscribers = new SMSNetSubscribers();

    @Before
    public void setup() {
        subscribers.addSubscriber(PEER1);
        subscribers.addSubscriber(PEER2);
        subscribers.addSubscriber(PEER3);
    }

}
