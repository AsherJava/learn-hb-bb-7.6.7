/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.period.common.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeriodDataObject {
    @JsonProperty(value="key")
    private String key;
    @JsonProperty(value="code")
    private String code;
    @JsonProperty(value="alias")
    private String alias;
    @JsonProperty(value="title")
    private String title;
    @JsonProperty(value="startdate")
    private String startdate;
    @JsonProperty(value="enddate")
    private String enddate;
    @JsonProperty(value="periodKey")
    private String periodKey;
    @JsonProperty(value="group")
    private String group;
    private String token;
    private String type;
    private String period13;
    private String simpleTitle;

    public String getSimpleTitle() {
        return this.simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }

    public String getPeriod13() {
        return this.period13;
    }

    public void setPeriod13(String period13) {
        this.period13 = period13;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartdate() {
        return this.startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return this.enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

