/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.multcheck2.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import java.util.Date;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;

public class MultcheckResRecord {
    private String key;
    private String task;
    private String period;
    private Map<String, String> dims;
    private CheckSource source;
    private String schemeKey;
    private int success;
    private int failed;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date begin;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end;
    private String user;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, String> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, String> dims) {
        this.dims = dims;
    }

    public CheckSource getSource() {
        return this.source;
    }

    public void setSource(CheckSource source) {
        this.source = source;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return this.failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public Date getBegin() {
        return this.begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

