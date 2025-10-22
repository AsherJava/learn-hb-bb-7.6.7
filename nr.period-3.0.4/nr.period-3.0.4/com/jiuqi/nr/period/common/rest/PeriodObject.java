/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.period.common.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;

public class PeriodObject {
    @JsonProperty(value="key")
    private String key;
    @JsonProperty(value="code")
    private String code;
    @JsonProperty(value="title")
    private String title;
    @JsonProperty(value="type")
    private String type;
    @JsonProperty(value="startdate")
    private String startdate;
    @JsonProperty(value="enddate")
    private String enddate;
    @JsonProperty(value="periodPropertyGroup")
    private PeriodPropertyGroup periodPropertyGroup;
    private String token;
    private String period13;

    public String getPeriod13() {
        return this.period13;
    }

    public void setPeriod13(String period13) {
        this.period13 = period13;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.periodPropertyGroup;
    }

    public void setPeriodPropertyGroup(PeriodPropertyGroup periodPropertyGroup) {
        this.periodPropertyGroup = periodPropertyGroup;
    }
}

