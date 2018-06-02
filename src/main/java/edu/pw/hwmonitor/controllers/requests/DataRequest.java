package edu.pw.hwmonitor.controllers.requests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRequest {
    private Integer serial;
    private LocalDateTime begin;
    private LocalDateTime end;

    public Integer getSerial() {
        return serial;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public void setBegin(String begin) {
        this.begin = LocalDateTime.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setEnd(String end) {
        this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
