package edu.pw.hwmonitor.controllers;

public class CompanyCreateRequest {
    private String name;
    private String username;
    private String password;

    String getName() {return name; }
    void setName(String name) {this.name = name;}

    String getUsername() {return username; }
    void setUsername(String username) {this.username = username;}

    String getPassword() {return password; }
    void setPassword(String password) {this.password = password;}
}
