/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 */
package com.jiuqi.dc.taskscheduling.logquery.client.dto;

import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import java.util.Date;
import java.util.List;

public class LogManagerDTO
extends DcTenantDTO {
    private static final long serialVersionUID = -4084771817144751018L;
    private String id;
    private String taskType;
    private String instanceId;
    private String preNodeId;
    private String dimType;
    private String dimCode;
    private List<String> dimCodes;
    private List<Integer> executeStates;
    private Date startTime;
    private Date endTime;
    private Integer page;
    private Integer pageSize;
    private String runnerId;
    private String showType;
    private Boolean containSql = false;
    private Boolean errorLog = false;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPreNodeId() {
        return this.preNodeId;
    }

    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public List<String> getDimCodes() {
        return this.dimCodes;
    }

    public void setDimCodes(List<String> dimCodes) {
        this.dimCodes = dimCodes;
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

    public Integer getPage() {
        return this.page;
    }

    public List<Integer> getExecuteStates() {
        return this.executeStates;
    }

    public void setExecuteStates(List<Integer> executeStates) {
        this.executeStates = executeStates;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Boolean getContainSql() {
        return this.containSql;
    }

    public void setContainSql(Boolean containSql) {
        this.containSql = containSql;
    }

    public Boolean getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(Boolean errorLog) {
        this.errorLog = errorLog;
    }
}

