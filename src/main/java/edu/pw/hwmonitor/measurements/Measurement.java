package edu.pw.hwmonitor.measurements;


import edu.pw.hwmonitor.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "MEASUREMENTS")
public class Measurement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEEDER_ID")
    private Long feederId;

    @Column(name = "VALUE")
    private Integer value;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "TIME")
    private LocalDateTime time;



    public Long getId() {
        return id;
    }

    public Long getFeederId() {
        return feederId;
    }

    public void setFeederId(Long v) {
        this.feederId = v;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer v) {
        this.value = v;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime v) {
        this.time = v;
    }

    public void setTime(String v) {
        this.time = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
