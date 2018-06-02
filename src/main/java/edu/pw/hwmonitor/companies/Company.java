package edu.pw.hwmonitor.companies;


import javax.persistence.*;


@Entity
@Table(name = "COMPANIES")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(name = "COMPANY_NAME", length = 10)
    private String name;

    @Column(name = "COMPANY_ROLE", length = 20)
    private String role;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
