package edu.pw.hwmonitor.network.messages.sent;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

import java.nio.ByteBuffer;

public class ConnectedMessage extends Message implements SerializableMessage {
    public ConnectedMessage() {
        super(MessageType.Connected);
    }

    @Override
    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(getType().value());
        return buffer.array();
    }
}
