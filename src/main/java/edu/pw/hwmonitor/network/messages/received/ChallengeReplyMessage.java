package edu.pw.hwmonitor.network.messages.received;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

public class ChallengeReplyMessage extends Message {
    private String challengeReply;

    public ChallengeReplyMessage(String challengeReply) {
        super(MessageType.ChallengeReply);
        this.challengeReply = challengeReply;
    }

    public String getChallengeReply() {
        return challengeReply;
    }
}
