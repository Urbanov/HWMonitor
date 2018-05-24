package edu.pw.hwmonitor.feeders;


import javax.persistence.*;

@Entity
@Table(name = "FEEDERS")
public class Feeder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeder_id;

    @Column(name = "COMPANY_ID")
    private Long company_id;


    @Column(name = "FEEDER_DESC", length = 64)
    private String feeder_desc;

    public Long getId() {
        return feeder_id;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getFeeder_desc() {
        return feeder_desc;
    }

    public void setFeeder_desc(String v) {
        this.feeder_desc = v;
    }
}
