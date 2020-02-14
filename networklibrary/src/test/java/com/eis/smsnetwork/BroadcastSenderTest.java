package com.eis.smsnetwork;

import com.eis.smslibrary.SMSPeer;

import org.junit.Before;

public class BroadcastSenderTest {

    private static final SMSPeer PEER1 = SMSNetSubscriberListTest.PEER1;
    private static final SMSPeer PEER2 = SMSNetSubscriberListTest.PEER2;
    private static final SMSPeer PEER3 = SMSNetSubscriberListTest.PEER3;

    private final SMSNetSubscriberList subscribers = new SMSNetSubscriberList();

    @Before
    public void setup() {
        subscribers.addSubscriber(PEER1);
        subscribers.addSubscriber(PEER2);
        subscribers.addSubscriber(PEER3);
    }

}
