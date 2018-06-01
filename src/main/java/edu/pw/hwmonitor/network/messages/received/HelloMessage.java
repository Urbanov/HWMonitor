package edu.pw.hwmonitor.network.messages.received;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

public class HelloMessage extends Message {
    private String name;
    private int id;

    public HelloMessage(String name, int id) {
        super(MessageType.Hello);
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
