package edu.pw.hwmonitor.feeders;


import javax.persistence.*;

@Entity
@Table(name = "FEEDERS")
public class Feeder {

    @Id
    @Column(name = "FEEDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SERIAL")
    private Integer serial;

    @Column(name = "COMPANY_ID")
    private Long companyId;

    @Column(name = "FEEDER_DESC", length = 64)
    private String description;

    @Column(name = "SECRET", length = 64)
    private String secret;

    public Integer getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
