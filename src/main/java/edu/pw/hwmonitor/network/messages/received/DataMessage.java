package edu.pw.hwmonitor.network.messages.received;

import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.MessageType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DataMessage extends Message {
    private int id;
    private int temperature;
    private LocalDateTime timestamp;

    public DataMessage(int id, int temperature, long timestamp) {
        super(MessageType.Data);
        this.id = id;
        this.temperature = temperature;
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    public int getId() {
        return id;
    }

    public int getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
