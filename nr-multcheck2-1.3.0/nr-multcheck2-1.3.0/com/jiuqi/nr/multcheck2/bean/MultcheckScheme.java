/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.multcheck2.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class MultcheckScheme {
    private String key;
    private String code;
    private String title;
    private String task;
    private String formScheme;
    private OrgType orgType;
    private String orgFml;
    private SchemeType type;
    private String order;
    private String desc;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String level = "";
    private String org;
    private String reportDim;

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

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public OrgType getOrgType() {
        return this.orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public String getOrgFml() {
        return this.orgFml;
    }

    public void setOrgFml(String orgFml) {
        this.orgFml = orgFml;
    }

    public SchemeType getType() {
        return this.type;
    }

    public void setType(SchemeType type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getReportDim() {
        return this.reportDim;
    }

    public void setReportDim(String reportDim) {
        this.reportDim = reportDim;
    }
}

