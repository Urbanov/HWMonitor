package edu.pw.hwmonitor.network.messages;

public enum MessageType {
    Hello(1),
    HelloChallenge(2),
    ChallengeReply(3),
    AccessDenied(4),
    Connected(5),
    Data(6);

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static MessageType from(int value) {
        MessageType[] types = MessageType.values();
        for (MessageType type : types) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
