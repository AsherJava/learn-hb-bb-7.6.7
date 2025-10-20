/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class EfdcCheckReportLogVO {
    private String id;
    private String unitId;
    private String groupId;
    private Integer acctYear;
    private Integer acctPeriod;
    private String fileName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createDate;
    private String createUser;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

