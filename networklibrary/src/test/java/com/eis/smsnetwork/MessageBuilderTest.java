package com.eis.smsnetwork;

import com.eis.communication.network.commands.AddResource;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageBuilderTest {
    private final SMSPeer PEER1 = new SMSPeer("+393423541601");
    private final SMSPeer PEER2 = new SMSPeer("+393423541602");
    private final SMSPeer PEER3 = new SMSPeer("+393423541603");

    private final String ARG1 = "Arg1";
    private final String ARG2 = "Arg2";
    private final String ARG3 = "Arg number 3";

    private final String BLANK = "/";

    @Test
    public void constructValidMessage_standardUse(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1, ARG2, ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + ARG2 + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void constructValidMessage_differentOrder(){
        SMSMessage constructedMessage = new MessageBuilder()
                .addArguments(ARG1, ARG2, ARG3)
                .setPeer(PEER1)
                .buildMessage();
        String expectedMessage = ARG1 + " " + ARG2 + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void constructValidMessage_moreCalls(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1)
                .addArguments(ARG2)
                .addArguments(ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + ARG2 + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void nullArgument_getsConstructed(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1)
                .addArguments(null)
                .addArguments(ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + BLANK + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void soloNullArgument_getsConstructed(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(null)
                .buildMessage();
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), BLANK);
    }

    @Test
    public void nullArgumentMultiple_getsConstructed(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1, null, ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + BLANK + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void emptyArgument_getsConstructed(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1)
                .addArguments("")
                .addArguments(ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + BLANK + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void blankArgument_getsConstructed(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1)
                .addArguments(BLANK)
                .addArguments(ARG3)
                .buildMessage();
        String expectedMessage = ARG1 + " " + BLANK + " " + ARG3;
        assertEquals(constructedMessage.getPeer(), PEER1);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test
    public void resetBuilder_correctConstruction(){
        SMSMessage constructedMessage = new MessageBuilder()
                .setPeer(PEER1)
                .addArguments(ARG1)
                .reset()
                .setPeer(PEER2)
                .addArguments(ARG2)
                .addArguments(ARG3)
                .addArguments(ARG1)
                .buildMessage();
        String expectedMessage = ARG2 + " " + ARG3 + " " + ARG1;
        assertEquals(constructedMessage.getPeer(), PEER2);
        assertEquals(constructedMessage.getData(), expectedMessage);
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutPeer_throws(){
        new MessageBuilder()
                .addArguments(ARG1)
                .addArguments(ARG2)
                .addArguments(ARG3)
                .buildMessage();
    }

    @Test(expected = IllegalStateException.class)
    public void buildWithoutMessage_throws(){
        new MessageBuilder()
                .setPeer(PEER2)
                .buildMessage();
    }

    @Test(expected = IllegalStateException.class)
    public void buildEmpty_throws(){
        new MessageBuilder()
                .buildMessage();
    }

    @Test(expected = IllegalStateException.class)
    public void buildResetted_throws(){
        new MessageBuilder()
                .setPeer(PEER2)
                .addArguments(ARG1)
                .addArguments(ARG2)
                .addArguments(ARG3)
                .reset()
                .buildMessage();
    }
}
