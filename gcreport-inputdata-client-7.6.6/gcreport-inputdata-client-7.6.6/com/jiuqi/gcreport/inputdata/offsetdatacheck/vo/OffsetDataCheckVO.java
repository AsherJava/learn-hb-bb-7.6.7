/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.inputdata.offsetdatacheck.vo;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import javax.validation.constraints.NotNull;

public class OffsetDataCheckVO {
    @NotNull(message="\u4efb\u52a1\u76f8\u5173\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u4efb\u52a1\u76f8\u5173\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") GcTaskBaseArguments taskBaseArguments;
    @NotNull(message="\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a") String orgId;
    @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u80fd\u4e3a\u7a7a\u3002")
    private @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u80fd\u4e3a\u7a7a\u3002") String orgType;
    private String currency;
    private String selectAdjustCode;
    private Boolean summary;
    private Integer pageNum = 1;
    private Integer pageSize = Integer.MAX_VALUE;
    private static final int DEFAULTPAGESIZE = Integer.MAX_VALUE;

    public GcTaskBaseArguments getTaskBaseArguments() {
        return this.taskBaseArguments;
    }

    public void setTaskBaseArguments(GcTaskBaseArguments taskBaseArguments) {
        this.taskBaseArguments = taskBaseArguments;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getSummary() {
        return this.summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        this.pageSize = this.pageSize == null ? Integer.MAX_VALUE : this.pageSize;
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartPosition() {
        return (this.pageNum - 1) * this.pageSize;
    }
}

