/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.multcheck2.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class MultcheckResItem {
    private String key;
    private String recordKey;
    private String schemeKey;
    private String itemKey;
    private CheckRestultState state;
    private int success;
    private int failed;
    private int ignore;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date begin;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end;
    private String runConfig;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public CheckRestultState getState() {
        return this.state;
    }

    public void setState(CheckRestultState state) {
        this.state = state;
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

    public int getIgnore() {
        return this.ignore;
    }

    public void setIgnore(int ignore) {
        this.ignore = ignore;
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

    public String getRunConfig() {
        return this.runConfig;
    }

    public void setRunConfig(String runConfig) {
        this.runConfig = runConfig;
    }
}

