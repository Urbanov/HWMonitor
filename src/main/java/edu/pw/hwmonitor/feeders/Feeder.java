package edu.pw.hwmonitor.feeders;


import javax.persistence.*;

@Entity
@Table(name = "FEEDERS")
public class Feeder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeder_id;

    @Column(name = "SERIAL")
    private Integer serial;

    @Column(name = "COMPANY_ID")
    private Long companyId;


    @Column(name = "FEEDER_DESC", length = 64)
    private String desc;

    @Column(name = "SECRET", length = 64)
    private String secret;

    public Long getId() { return feeder_id; }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long company_id) {
        this.companyId = company_id;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer v) {
        this.serial = v;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String v) {
        this.desc = v;
    }

    public String getSecret() { return secret; }

    public void setSecret(String v) {
        this.secret = v;
    }
}
