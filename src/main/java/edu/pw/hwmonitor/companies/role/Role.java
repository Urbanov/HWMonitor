package edu.pw.hwmonitor.companies.role;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLES")
public class Role {

    @Id
    @Column(name = "USER_ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", length = 10)
    private String username;

    @Column(name = "ROLE", length = 20)
    private String role;

    public Long getId() {return this.id;}

    public String getUsername() {return username; }
    public void setUsername(String username) {this.username = username;}

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role;}
}
