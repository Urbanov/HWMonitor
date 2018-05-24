package edu.pw.hwmonitor.companies;


import javax.persistence.*;


@Entity
@Table(name = "COMPANIES")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;

    @Column(name = "COMPANY_NAME", length = 20)
    private String company_name;

    @Column(name = "COMPANY_ROLE", length = 20)
    private String company_role;

    public Long getId() {
        return company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String v) {
        this.company_name = v;
    }

    public String getCompany_role() {
        return company_role;
    }

    public void setCompany_role(String v) {
        this.company_role = v;
    }
}
