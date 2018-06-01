package edu.pw.hwmonitor.network.messages.sent;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class HelloChallengeMessage extends Message implements SerializableMessage {
    private int id;
    private String challenge;

    public HelloChallengeMessage(int id, String challenge) {
        super(MessageType.HelloChallenge);
        this.id = id;
        this.challenge = challenge;
    }

    @Override
    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.putInt(getType().value());
        buffer.putInt(id);
        buffer.put(challenge.getBytes(StandardCharsets.US_ASCII));
        return buffer.array();
    }
}
