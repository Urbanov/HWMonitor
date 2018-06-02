package edu.pw.hwmonitor.controllers.requests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRequest {
    private Long companyId;
    private Integer serial;
    private LocalDateTime timel;
    private LocalDateTime timeh;

    // TODO refactor this

    public Long getCompanyId() {return companyId; }

    public void setCompanyId(Long v) {this.companyId=v;}

    public Integer getSerial() {return serial; }

    public void setSerial(Integer v) {this.serial=v;}

    public void setTimel(String v) {
        this.timel = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimel(){return timel;}

    public void setTimeh(String v) {
        this.timeh = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public LocalDateTime getTimeh(){return timeh;}
}
