/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Id
 */
package com.jiuqi.dc.integration.execute.impl.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

public class ConvertLogDO {
    public static final String TABLENAME = "DC_LOG_DATACONVERT";
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="RUNNERID")
    private String runnerId;
    @Column(name="DATASCHEMECODE")
    private String dataSchemeCode;
    @Column(name="SCHEMETYPE")
    private String schemeType;
    @Column(name="DATANAME")
    private String dataName;
    @Column(name="EXECUTEPARAMS")
    private String executeParams;
    @Column(name="USERNAME")
    private String userName;
    @Column(name="EXECUTESTATE")
    private Integer executeState;
    @Column(name="CREATETIME")
    private Date createTime;
    @Column(name="MESSAGE")
    private String message;

    public ConvertLogDO() {
    }

    public ConvertLogDO(String id, String dataSchemeCode, String schemeType, String dataName, String executeParams, String userName, Integer executeState, Date createTime) {
        this.id = id;
        this.dataSchemeCode = dataSchemeCode;
        this.schemeType = schemeType;
        this.dataName = dataName;
        this.executeParams = executeParams;
        this.userName = userName;
        this.executeState = executeState;
        this.createTime = createTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getSchemeType() {
        return this.schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getExecuteParams() {
        return this.executeParams;
    }

    public void setExecuteParams(String executeParams) {
        this.executeParams = executeParams;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

