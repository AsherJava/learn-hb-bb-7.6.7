/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.dc.integration.execute.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class ConvertLogVO
implements Serializable {
    private static final long serialVersionUID = -5936211443918048217L;
    private String id;
    private String runnerId;
    private String dataSchemeCode;
    private String dataSchemeName;
    private String schemeType;
    private String dataName;
    private Integer executeState;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    private String userName;
    private Date createTime;
    private String executeParams;
    private Integer failed;
    private Integer success;
    private Integer unExecute;
    private Integer executing;
    private Integer total;
    private String message;

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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeName() {
        return this.dataSchemeName;
    }

    public void setDataSchemeName(String dataSchemeName) {
        this.dataSchemeName = dataSchemeName;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(int executeState) {
        this.executeState = executeState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public String getExecuteParams() {
        return this.executeParams;
    }

    public void setExecuteParams(String executeParams) {
        this.executeParams = executeParams;
    }

    public Integer getFailed() {
        return this.failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getSuccess() {
        return this.success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getUnExecute() {
        return this.unExecute;
    }

    public void setUnExecute(Integer unExecute) {
        this.unExecute = unExecute;
    }

    public Integer getExecuting() {
        return this.executing;
    }

    public void setExecuting(Integer executing) {
        this.executing = executing;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

