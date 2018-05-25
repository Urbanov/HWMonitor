package edu.pw.hwmonitor.companies;


import javax.persistence.*;


@Entity
@Table(name = "COMPANIES")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;

    @Column(name = "COMPANY_NAME", length = 20)
    private String name;

    @Column(name = "COMPANY_ROLE", length = 20)
    private String role;

    public Long getId() {
        return company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        this.name = v;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String v) {
        this.role = v;
    }
}
