package edu.pw.hwmonitor.measurements;

import edu.pw.hwmonitor.LocalDateTimeConverter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "MEASUREMENTS")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEEDER_ID")
    private Integer feederId;

    @Column(name = "VALUE")
    private Integer value;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "TIME")
    private LocalDateTime time;

    public Long getId() {
        return id;
    }

    public Integer getFeederId() {
        return feederId;
    }

    public void setFeederId(Integer feederId) {
        this.feederId = feederId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
