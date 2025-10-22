/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import java.util.Date;

public class GcCalcResultVO
extends GcBaseTaskStateVO {
    private String orgName;
    private String prcessResult;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @JsonProperty
    private Date startTime;
    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date endTime;
    private Long useTime;

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPrcessResult() {
        return this.prcessResult;
    }

    public void setPrcessResult(String prcessResult) {
        this.prcessResult = prcessResult;
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

    public Long getUseTime() {
        return this.useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public String toString() {
        return "GcCalcResultVO{orgName='" + this.orgName + '\'' + ", prcessResult='" + this.prcessResult + '\'' + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime + '\'' + ", useTime='" + this.useTime + '\'' + '}';
    }
}

