/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.io.record.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.sql.Date;

public class ImportHistoryVO
implements Serializable {
    private int num;
    private String recKey;
    private String taskTitle;
    private String caliberEntityTitle;
    private String dateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private Date importTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    private Date endTime;
    private String mappingSchemeTitle;
    private int state;
    private String userName;
    private String paramType;

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getCaliberEntityTitle() {
        return this.caliberEntityTitle;
    }

    public void setCaliberEntityTitle(String caliberEntityTitle) {
        this.caliberEntityTitle = caliberEntityTitle;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Date getImportTime() {
        return this.importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMappingSchemeTitle() {
        return this.mappingSchemeTitle;
    }

    public void setMappingSchemeTitle(String mappingSchemeTitle) {
        this.mappingSchemeTitle = mappingSchemeTitle;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}

