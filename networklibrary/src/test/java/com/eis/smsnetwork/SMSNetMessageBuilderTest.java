package com.eis.smsnetwork;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SMSNetMessageBuilderTest {
    private final SMSPeer PEER1 = new SMSPeer("+393423541601");
    private final SMSPeer PEER2 = new SMSPeer("+393423541602");
    private final SMSPeer PEER3 = new SMSPeer("+393423541603");

    private final String ARG1 = "Arg1";
    private final String ARG2 = "Arg2";
    private final String ARG3 = "Arg number 3";

    private final RequestType REQ1 = RequestType.AddPeer;
    private final RequestType REQ2 = RequestType.Invite;

    private final String BLANK = "/";

    @Test
    public void buildNetMessage_asExpected(){
        SMSMessage constructedMessage = new SMSNetMessageBuilder()
                .setPeer(PEER1)
                .setRequest(REQ1)
                .addArguments(ARG1, ARG3, PEER3.toString())
                .buildMessage();
        String expectedMessage = REQ1.asString() + " " + ARG1 + " " + ARG3 + " " + PEER3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void buildNetMessage_DifferentOrder(){
        SMSMessage constructedMessage = new SMSNetMessageBuilder()
                .setRequest(REQ1)
                .addArguments(ARG1, ARG3, PEER3.toString())
                .setPeer(PEER1)
                .buildMessage();
        String expectedMessage = REQ1.asString() + " " + ARG1 + " " + ARG3 + " " + PEER3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void buildNetMessage_MultipleCalls(){
        SMSMessage constructedMessage = new SMSNetMessageBuilder()
                .setRequest(REQ1)
                .addArguments(ARG1)
                .addArguments(ARG3)
                .addArguments(PEER3.toString())
                .setPeer(PEER1)
                .buildMessage();
        String expectedMessage = REQ1.asString() + " " + ARG1 + " " + ARG3 + " " + PEER3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test(expected = IllegalStateException.class)
    public void setNullPeer_throws(){
        new SMSNetMessageBuilder().setPeer(null).buildMessage();
    }

    @Test(expected = NullPointerException.class)
    public void setNullRequest_throws(){
        new SMSNetMessageBuilder().setRequest(null).buildMessage();
    }

}
