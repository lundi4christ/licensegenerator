package com.licensemanager.bean;

import javax.persistence.*;


@Entity
@Table(name = "datadbsource", uniqueConstraints = {@UniqueConstraint(columnNames = {"datasourcenm"})})
public class dataSourceDb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "jdbcdriver")
    private String jdbcdriver;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private int port;

    @Column(name = "databasenm")
    private String databasenm;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="datasourcenm", nullable = false, unique = true)
    private String datasourcenm;



    //no arg constructor
    public dataSourceDb() {

    }

    public dataSourceDb(String jdbcdriver, String host, int port, String databasenm, String username, String password) {

    }

    public dataSourceDb(String dabasenm) {
    }

    //getters and setters for the fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJdbcdriver() {
        return jdbcdriver;
    }

    public void setJdbcdriver(String jdbcdriver) {
        this.jdbcdriver = jdbcdriver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabasenm() {
        return databasenm;
    }

    public void setDatabasenm(String databasenm) {
        this.databasenm = databasenm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getDatasourcenm(){
        return datasourcenm;
    }

    public void setDatasourcenm(String datasourcenm){
        this.datasourcenm=datasourcenm;
    }


    //toString()

    @Override
    public String toString() {
        return "dataSource{" +
                "jdbcdriver='" + jdbcdriver + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", database='" + databasenm + '\'' +
                ", username='" + username + '\'' +
                ", datasourcenm='" + datasourcenm + '\''+
                '}';
    }
}
