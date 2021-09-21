package com.codetreatise.view;

public enum dataList {
        SQL_SERVER_DRIVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
        ORACLE_SQL_DRIVER("oracle.jdbc.OracleDriver"),
        MYSQL_DRIVER("com.mysql.jdbc.Driver");

    String description;

    dataList(String description){
        this.description=description;
    }

    public String getDescription(){

        return this.description;
    }

    public String getName(){

        return this.name();
    }
}
