package edu.pw.hwmonitor.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRequest {
    private Long companyId;
    private Integer serial;
    private LocalDateTime timel;
    private LocalDateTime timeh;

    Long getCompanyId() {return companyId; }

    void setCompanyId(Long v) {this.companyId=v;}

    Integer getSerial() {return serial; }

    void setSerial(Integer v) {this.serial=v;}

    public void setTimel(String v) {
        this.timel = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimel(){return timel;}

    public void setTimeh(String v) {
        this.timeh = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimeh(){return timeh;}
}
