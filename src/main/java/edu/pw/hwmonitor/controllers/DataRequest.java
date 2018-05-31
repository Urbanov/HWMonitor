package edu.pw.hwmonitor.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRequest {
    private Integer serial;
    private LocalDateTime timel;
    private LocalDateTime timeh;

    Integer getSerial() {return serial; }

    void setSerial(Integer v) {this.serial=v;}

    public void setTimel(String v) {
        v=v.replace('T',' ');
        v=v.substring(0,v.length()-5);
        this.timel = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimel(){return timel;}

    public void setTimeh(String v) {
        v=v.replace('T',' ');
        v=v.substring(0,v.length()-5);
        this.timeh = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimeh(){return timeh;}
}
