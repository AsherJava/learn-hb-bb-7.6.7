/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.period.modal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.Date;

public class PeriodDataDefineImpl
implements IPeriodRow {
    private static final long serialVersionUID = 1L;
    private String key;
    private String code;
    private String alias;
    private String title;
    private int year;
    private int quarter;
    private int month;
    private int days;
    private Date startDate;
    private Date endDate;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private String updateUser;
    private int day;
    private String timeKey;
    private String simpleTitle;

    @Override
    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String getTimeKey() {
        return this.timeKey;
    }

    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }

    public PeriodDataDefineImpl() {
    }

    public PeriodDataDefineImpl(String key, String code, String alias, String title, int year, int quarter, int month, int days, Date startDate, Date endDate, String createUser, Date createTime, Date updateTime, String updateUser) {
        this.key = key;
        this.code = code;
        this.alias = alias;
        this.title = title;
        this.year = year;
        this.quarter = quarter;
        this.month = month;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getQuarter() {
        return this.quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    @Override
    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String getSimpleTitle() {
        return this.simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }

    public String toString() {
        return "PeriodDataDefineImpl [key=" + this.key + ", code=" + this.code + ", alias=" + this.alias + ", title=" + this.title + ", year=" + this.year + ", quarter=" + this.quarter + ", month=" + this.month + ", days=" + this.days + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", createUser=" + this.createUser + ", createTime=" + this.createTime + ", updateTime=" + this.updateTime + ", updateUser=" + this.updateUser + ", day=" + this.day + ", timeKey=" + this.timeKey + "]";
    }

    @Override
    @JsonIgnore
    public String getOrder() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getVersion() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getOwnerLevelAndId() {
        return null;
    }

    public String getID() {
        return this.key;
    }
}

