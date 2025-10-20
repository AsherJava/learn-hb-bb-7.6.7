/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import java.util.List;
import javax.validation.constraints.NotNull;

public class GcDiffProcessCondition {
    private String taskId;
    private String schemeId;
    private String currency;
    private String periodStr;
    private String orgType;
    private String adjTypeId;
    @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d")
    private @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d") String orgId;
    @NotNull(message="\u5408\u5e76\u5c42\u7ea7\u4e0d\u80fd\u4e3a\u7a7a!")
    private @NotNull(message="\u5408\u5e76\u5c42\u7ea7\u4e0d\u80fd\u4e3a\u7a7a!") MergeTypeEnum mergeType;
    private String currFormKey;
    private boolean allDifferenceProcess;
    private List<String> formKeyScope;
    private String sn;
    private String selectAdjustCode;

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

    public String getAdjTypeId() {
        return this.adjTypeId;
    }

    public void setAdjTypeId(String adjTypeId) {
        this.adjTypeId = adjTypeId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public MergeTypeEnum getMergeType() {
        return this.mergeType;
    }

    public void setMergeType(MergeTypeEnum mergeType) {
        this.mergeType = mergeType;
    }

    public String getCurrFormKey() {
        return this.currFormKey;
    }

    public void setCurrFormKey(String currFormKey) {
        this.currFormKey = currFormKey;
    }

    public boolean isAllDifferenceProcess() {
        return this.allDifferenceProcess;
    }

    public void setAllDifferenceProcess(boolean allDifferenceProcess) {
        this.allDifferenceProcess = allDifferenceProcess;
    }

    public List<String> getFormKeyScope() {
        return this.formKeyScope;
    }

    public void setFormKeyScope(List<String> formKeyScope) {
        this.formKeyScope = formKeyScope;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

