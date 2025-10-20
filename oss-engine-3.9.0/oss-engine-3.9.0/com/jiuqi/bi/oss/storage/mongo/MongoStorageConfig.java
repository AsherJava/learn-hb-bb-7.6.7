/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss.storage.mongo;

import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConfigParam;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class MongoStorageConfig
extends StorageConfig {
    private String host;
    private int port = 27017;
    private String dbName;
    private int chunkSize;
    private boolean credential = false;
    private String username;
    private String pwd;
    private static final String ATTR_HOST = "host";
    private static final String ATTR_PORT = "port";
    private static final String ATTR_DB_NAME = "dbName";
    private static final String ATTR_CHUNK_SIZE = "chunkSize";
    private static final String ATTR_CREDENTIAL = "credential";
    private static final String ATTR_USERNAME = "username";
    private static final String ATTR_PWD = "pwd";

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public void setCredential(boolean credential) {
        this.credential = credential;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isCredential() {
        return this.credential;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPwd() {
        return this.pwd;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.putOpt(ATTR_HOST, (Object)this.host);
        json.putOpt(ATTR_PORT, (Object)this.port);
        json.putOpt(ATTR_DB_NAME, (Object)this.dbName);
        json.putOpt(ATTR_CHUNK_SIZE, (Object)this.chunkSize);
        json.putOpt(ATTR_CREDENTIAL, (Object)this.credential);
        json.putOpt(ATTR_USERNAME, (Object)this.username);
        json.putOpt(ATTR_PWD, (Object)this.pwd);
        return json;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        this.host = jsonObject.optString(ATTR_HOST);
        this.port = jsonObject.optInt(ATTR_PORT);
        this.dbName = jsonObject.optString(ATTR_DB_NAME);
        this.chunkSize = jsonObject.optInt(ATTR_CHUNK_SIZE);
        this.credential = jsonObject.optBoolean(ATTR_CREDENTIAL);
        this.username = jsonObject.optString(ATTR_USERNAME);
        this.pwd = jsonObject.optString(ATTR_PWD);
    }

    public String toString() {
        return "MongoDB: " + this.host + ":" + this.port + "/" + this.dbName;
    }

    @Override
    public List<StorageConfigParam> params() {
        ArrayList<StorageConfigParam> p = new ArrayList<StorageConfigParam>();
        p.add(new StorageConfigParam(ATTR_HOST, "\u6570\u636e\u5e93\u5730\u5740", false));
        p.add(new StorageConfigParam(ATTR_DB_NAME, "\u6570\u636e\u5e93\u540d\u79f0", false));
        p.add(new StorageConfigParam(ATTR_PORT, "\u7aef\u53e3\u53f7", "27017", false));
        p.add(new StorageConfigParam(ATTR_CHUNK_SIZE, "\u5757\u5927\u5c0f"));
        StorageConfigParam credParam = new StorageConfigParam(ATTR_CREDENTIAL, "\u542f\u7528\u8ba4\u8bc1", "false", true);
        credParam.setWidgetType("checkbox");
        p.add(credParam);
        p.add(new StorageConfigParam(ATTR_USERNAME, "\u7528\u6237\u540d"));
        p.add(new StorageConfigParam(ATTR_PWD, "\u5bc6\u7801"));
        return p;
    }
}

