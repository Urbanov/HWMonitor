package edu.pw.hwmonitor.network.messages.received;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ReceivedMessageFactory {
    public static Message build(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        MessageType type = Optional.ofNullable(MessageType.from(buffer.getInt())).orElseThrow(IOException::new);

        switch (type) {
            case Hello:
                return buildHelloMessage(buffer);
            case ChallengeReply:
                return buildChallengeReplyMessage(buffer);
            case Data:
                return buildDataMessage(buffer);
            default:
                throw new IOException();
        }
    }

    private static HelloMessage buildHelloMessage(ByteBuffer buffer) {
        byte[] nameArray = new byte[10];
        buffer.get(nameArray);
        int length = 0;
        for (int i = 0; i < 10 && nameArray[i] != 0; ++i) {
            ++length;
        }
        String name = new String(nameArray, 0, length, StandardCharsets.US_ASCII);

        int id = buffer.getInt();

        return new HelloMessage(name, id);
    }

    private static ChallengeReplyMessage buildChallengeReplyMessage(ByteBuffer buffer) {
        byte[] md5Array = new byte[32];
        buffer.get(md5Array);
        String md5 = new String(md5Array, 0, 32, StandardCharsets.US_ASCII);

        return new ChallengeReplyMessage(md5);
    }

    private static DataMessage buildDataMessage(ByteBuffer buffer) {
        int id = buffer.getInt();
        int temperature = buffer.getInt();
        long timestamp = buffer.getLong();

        return new DataMessage(id, temperature, timestamp);
    }
}
