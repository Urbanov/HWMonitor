package edu.pw.hwmonitor.controllers;

public class FeederCreateRequest {
    private String companyName;
    private Integer serial;
    private String secret;
    private String description;

    String getCompanyName() {return companyName; }
    void setCompanyName(String companyName) {this.companyName = companyName;}

    Integer getSerial() {return serial; }
    void setSerial(Integer serial) {this.serial = serial;}

    String getSecret() {return secret; }
    void setSecret(String secret) {this.secret = secret;}

    String getDescription() {return description; }
    void setDescription(String descriptiont) {this.description = description;}
}
