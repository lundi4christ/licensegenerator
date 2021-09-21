package com.licensemanager.bean;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="licensegendb")
public class licenseGeneratorDb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="productname")
    private String productname;

    @Column(name="status")
    private String status;

    @Column(name="opsystem")
    private String opsystem;

    @Column(name="env")
    private String env;

    @Column(name="deploy")
    private String deploy;

    @Column(name="version")
    private String version;

    @Column(name="effectivedate")
    private LocalDate effectivedate;

    @Column(name="durationdays")
    private int durationdays;

    @Column(name="hostname")
    private String hostname;

    @Column(name="hostmac")
    private String hostmac;

    @Column(name="remdays")
    private int remdays;

    @Column(name="gracedays")
    private int gracedays;

    @Column(name="expirydate")
    private LocalDate expirydate;

    @Column(name="dbsourcenm")
    private String dbsourcenm;

// no arg constructor
    public licenseGeneratorDb(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpsystem() {
        return opsystem;
    }

    public void setOpsystem(String opsystem) {
        this.opsystem = opsystem;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDeploy() {
        return deploy;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDate effectivedate) {
        this.effectivedate = effectivedate;
    }

    public int getDurationdays() {
        return durationdays;
    }

    public void setDurationdays(int durationdays) {
        this.durationdays = durationdays;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostmac() {
        return hostmac;
    }

    public void setHostmac(String hostmac) {
        this.hostmac = hostmac;
    }

    public int getRemdays() {
        return remdays;
    }

    public void setRemdays(int remdays) {
        this.remdays = remdays;
    }

    public int getGracedays() {
        return gracedays;
    }

    public void setGracedays(int gracedays) {
        this.gracedays = gracedays;
    }

    public LocalDate getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(LocalDate expirydate) {
        this.expirydate = expirydate;
    }

    public String getDbsourcenm() {
        return dbsourcenm;
    }

    public void setDbsourcenm(String dbsourcenm) {
        this.dbsourcenm = dbsourcenm;
    }

    @Override
    public String toString() {
        return "licenseGeneratorDb{" +
                "id=" + id +
                ", productname='" + productname + '\'' +
                ", status='" + status + '\'' +
                ", opsystem='" + opsystem + '\'' +
                ", env='" + env + '\'' +
                ", deploy='" + deploy + '\'' +
                ", version='" + version + '\'' +
                ", effectivedate='" + effectivedate + '\'' +
                ", durationdays=" + durationdays +
                ", hostname='" + hostname + '\'' +
                ", hostmac='" + hostmac + '\'' +
                ", remdays=" + remdays +
                ", gracedays=" + gracedays +
                ", dbsourcenm=" + dbsourcenm +
                ", expirydate=" + expirydate +
                '}';
    }
}
