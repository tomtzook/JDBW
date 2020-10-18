package com.jdbw.sql.jdbc;

public class ConnectionConfig {

    private final String mUrl;
    private final String mUsername;
    private final String mPassword;
    private final String mDriver;

    public ConnectionConfig(String url, String username, String password, String driver) {
        mUrl = url;
        mUsername = username;
        mPassword = password;
        mDriver = driver;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getDriver() {
        return mDriver;
    }
}
