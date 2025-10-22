/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.samecontrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SameCtrlOffsetCond {
    private String taskId;
    private String schemeId;
    private String systemId;
    private String periodStr;
    private String sameCtrlChgId;
    private String pentrateType;
    private String showTabType;
    private String currencyCode;
    private String selectAdjustCode;
    private String orgType;
    private String mergeUnitCode;
    private String changedOrgCode;
    private List<String> inputUnitCodes;
    private String changedUnitCode;
    private String sameParentCode;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date changeDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date disposalDate;
    private List<String> mRecids;
    private boolean extractAllParentsUnitFlag = false;
    private int pageNum;
    private int pageSize;

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

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getMergeUnitCode() {
        return this.mergeUnitCode;
    }

    public void setMergeUnitCode(String mergeUnitCode) {
        this.mergeUnitCode = mergeUnitCode;
    }

    public String getChangedUnitCode() {
        return this.changedUnitCode;
    }

    public List<String> getChangedUnitCodeChilds() {
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)this.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, this.getPeriodStr()));
        List childOrgs = orgCenterTool.listAllOrgByParentIdContainsSelf(this.getChangedUnitCode());
        ArrayList<String> childUnitCodes = new ArrayList<String>(childOrgs.size());
        childOrgs.forEach(childOrg -> childUnitCodes.add(childOrg.getCode()));
        return childUnitCodes;
    }

    public void setChangedUnitCode(String changedUnitCode) {
        this.changedUnitCode = changedUnitCode;
    }

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

    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Date getDisposalDate() {
        return this.disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getSameParentCode() {
        return this.sameParentCode;
    }

    public void setSameParentCode(String sameParentCode) {
        this.sameParentCode = sameParentCode;
    }

    public String getSameCtrlChgId() {
        return this.sameCtrlChgId;
    }

    public void setSameCtrlChgId(String sameCtrlChgId) {
        this.sameCtrlChgId = sameCtrlChgId;
    }

    public String getPentrateType() {
        return this.pentrateType;
    }

    public void setPentrateType(String pentrateType) {
        this.pentrateType = pentrateType;
    }

    public String getShowTabType() {
        return this.showTabType;
    }

    public void setShowTabType(String showTabType) {
        this.showTabType = showTabType;
    }

    public List<String> getmRecids() {
        return this.mRecids;
    }

    public void setmRecids(List<String> mRecids) {
        this.mRecids = mRecids;
    }

    public List<String> getInputUnitCodes() {
        return this.inputUnitCodes;
    }

    public void setInputUnitCodes(List<String> inputUnitCodes) {
        this.inputUnitCodes = inputUnitCodes;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getChangedOrgCode() {
        return this.changedOrgCode;
    }

    public void setChangedOrgCode(String changedOrgCode) {
        this.changedOrgCode = changedOrgCode;
    }

    public boolean isExtractAllParentsUnitFlag() {
        return this.extractAllParentsUnitFlag;
    }

    public void setExtractAllParentsUnitFlag(boolean extractAllParentsUnitFlag) {
        this.extractAllParentsUnitFlag = extractAllParentsUnitFlag;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

