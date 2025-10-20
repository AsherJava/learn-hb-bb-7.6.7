/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.carryover.vo;

import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.io.Serializable;
import java.util.List;

public class QueryParamsVO
implements Serializable {
    private String carryOverSchemeId;
    private String typeCode;
    private String taskId;
    private String schemeId;
    private Integer acctYear;
    private Integer periodType;
    private String periodStr;
    private String selectAdjustCode;
    private String taskLogId;
    private String systemId;
    private String consSystemId;
    private List<GcOrgCacheVO> orgList;
    private String orgType;
    private ConsolidatedTaskVO taskVO;
    private int pageNum;
    private int pageSize;

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCarryOverSchemeId() {
        return this.carryOverSchemeId;
    }

    public void setCarryOverSchemeId(String carryOverSchemeId) {
        this.carryOverSchemeId = carryOverSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getTaskLogId() {
        return this.taskLogId;
    }

    public void setTaskLogId(String taskLogId) {
        this.taskLogId = taskLogId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getConsSystemId() {
        return this.consSystemId;
    }

    public void setConsSystemId(String consSystemId) {
        this.consSystemId = consSystemId;
    }

    public List<GcOrgCacheVO> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<GcOrgCacheVO> orgList) {
        this.orgList = orgList;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public ConsolidatedTaskVO getTaskVO() {
        return this.taskVO;
    }

    public void setTaskVO(ConsolidatedTaskVO taskVO) {
        this.taskVO = taskVO;
    }
}

