package com.eis.communication.network;

import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for the SMSNetSubscribers class
 *
 * @author Marco Cognolato
 */
public class SMSNetSubscribersTest {

    public final SMSPeer PEER1 = new SMSPeer("+393423541601");
    public final SMSPeer PEER2 = new SMSPeer("+393423541601");
    public final SMSPeer PEER3 = new SMSPeer("+393423541601");

    public ArrayList<SMSPeer> totalPeers = new ArrayList<>();

    private SMSNetSubscribers netSubscribers;

    @Before
    public void setup(){
        totalPeers.add(PEER1);
        totalPeers.add(PEER2);
        totalPeers.add(PEER3);

        netSubscribers = new SMSNetSubscribers();
    }

    @Test
    public void addOnePeer_getsAdded() {
        netSubscribers.addSubscriber(PEER1);
        assertArrayEquals(netSubscribers.getSubscribers().toArray(),
                new SMSPeer[] {PEER1});
    }

    @Test
    public void addTwoPeer_getsAdded() {
        netSubscribers.addSubscriber(PEER1);
        netSubscribers.addSubscriber(PEER2);
        assertArrayEquals(netSubscribers.getSubscribers().toArray(),
                new SMSPeer[] {PEER1, PEER2});
    }

    @Test
    public void addThreePeer_getsAdded() {
        netSubscribers.addSubscriber(PEER1);
        netSubscribers.addSubscriber(PEER2);
        netSubscribers.addSubscriber(PEER3);
        assertArrayEquals(netSubscribers.getSubscribers().toArray(),
                new SMSPeer[] {PEER1, PEER2, PEER3});
    }

    @Test
    public void removePeer_getsRemoved() {
        netSubscribers.addSubscriber(PEER1);
        netSubscribers.removeSubcriber(PEER1);
        assertArrayEquals(netSubscribers.getSubscribers().toArray(),
                new SMSPeer[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNonPresentPeer_throws() {
        netSubscribers.removeSubcriber(PEER1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullPeer_throws(){
        netSubscribers.addSubscriber(null);
    }
}