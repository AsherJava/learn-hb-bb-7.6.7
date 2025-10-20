/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.multicriteria.client.vo;

import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class GcMultiCriteriaConditionVO {
    private String taskId;
    private String schemeId;
    private List<String> zbs;
    private Boolean beforeReport;
    private String currency;
    private String periodStr;
    private String orgType;
    private String selectAdjustCode;
    @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d")
    private @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d") String orgId;
    private String currFormKey;
    private Set<String> afterFormKeys;
    private String showType;
    private List<GcMultiCriteriaZbDataVO> multiCriteriaZbDatas;
    private Boolean allExport;

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

    public List<String> getZbs() {
        return this.zbs;
    }

    public void setZbs(List<String> zbs) {
        this.zbs = zbs;
    }

    public Boolean getBeforeReport() {
        return this.beforeReport;
    }

    public void setBeforeReport(Boolean beforeReport) {
        this.beforeReport = beforeReport;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCurrFormKey() {
        return this.currFormKey;
    }

    public void setCurrFormKey(String currFormKey) {
        this.currFormKey = currFormKey;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public List<GcMultiCriteriaZbDataVO> getMultiCriteriaZbDatas() {
        return this.multiCriteriaZbDatas;
    }

    public void setMultiCriteriaZbDatas(List<GcMultiCriteriaZbDataVO> multiCriteriaZbDatas) {
        this.multiCriteriaZbDatas = multiCriteriaZbDatas;
    }

    public Boolean getAllExport() {
        return this.allExport;
    }

    public void setAllExport(Boolean allExport) {
        this.allExport = allExport;
    }

    public Set<String> getAfterFormKeys() {
        return this.afterFormKeys;
    }

    public void setAfterFormKeys(Set<String> afterFormKeys) {
        this.afterFormKeys = afterFormKeys;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

