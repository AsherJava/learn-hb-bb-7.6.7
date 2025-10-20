/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datasource.vo;

public class DataSourceInfoVO {
    private String id;
    private String name;
    private String code;
    private String driver;
    private String url;
    private String userName;
    private String passWord;
    private String dataBaseParam;
    private String dataBaseType;
    private String connectionType;
    private String ip;
    private String port;
    private String dataBaseName;
    private Boolean enableTempTable;
    private Integer inParamValueMaxCount;
    private Long updateTime;

    public String getDriver() {
        return this.driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDataBaseType() {
        return this.dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String toString() {
        return "DataSourceInfoDTO [driver=" + this.driver + ", url=" + this.url + ", userName=" + this.userName + ", password=" + this.passWord + ", code=" + this.code + ", name=" + this.name + ", connectionType=" + this.connectionType + "]";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataBaseName() {
        return this.dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public Integer getInParamValueMaxCount() {
        return this.inParamValueMaxCount;
    }

    public void setInParamValueMaxCount(Integer inParamValueMaxCount) {
        this.inParamValueMaxCount = inParamValueMaxCount;
    }

    public Boolean getEnableTempTable() {
        return this.enableTempTable;
    }

    public void setEnableTempTable(Boolean enableTempTable) {
        this.enableTempTable = enableTempTable;
    }

    public String getDataBaseParam() {
        return this.dataBaseParam;
    }

    public void setDataBaseParam(String dataBaseParam) {
        this.dataBaseParam = dataBaseParam;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return this.connectionType;
    }
}

