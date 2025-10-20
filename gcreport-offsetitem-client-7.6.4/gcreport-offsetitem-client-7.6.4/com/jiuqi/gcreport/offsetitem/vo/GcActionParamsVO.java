/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.np.core.context.NpContext;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GcActionParamsVO {
    @NotNull(message="\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a") String taskId;
    @NotNull(message="\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a") String schemeId;
    private String currency;
    @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a") Integer acctYear;
    @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a") Integer acctPeriod;
    @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a") Integer periodType;
    private String periodStr;
    @NotBlank(message="\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String orgType;
    private String adjTypeId;
    @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d")
    private @NotNull(message="\u8bf7\u5148\u9009\u62e9\u5355\u4f4d") String orgId;
    @NotNull(message="\u5173\u8054\u7684\u4e3b\u4f53\u7f3a\u5931!")
    private @NotNull(message="\u5173\u8054\u7684\u4e3b\u4f53\u7f3a\u5931!") List<String> defines;
    @NotNull(message="\u5373\u65f6\u65e5\u5fd7\u7d22\u5f15\u4e0d\u80fd\u4e3a\u7a7a!")
    private @NotNull(message="\u5373\u65f6\u65e5\u5fd7\u7d22\u5f15\u4e0d\u80fd\u4e3a\u7a7a!") String taskLogId;
    private List<String> ruleIds;
    private List<String> taskCodes;
    private NpContext npContext;
    private String ipAddr;
    private String configSchemeName;
    private String selectAdjustCode;

    public String getConfigSchemeName() {
        return this.configSchemeName;
    }

    public void setConfigSchemeName(String configSchemeName) {
        this.configSchemeName = configSchemeName;
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

    public String getTaskLogId() {
        return this.taskLogId;
    }

    public void setTaskLogId(String taskLogId) {
        this.taskLogId = taskLogId;
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

    public List<String> getDefines() {
        return this.defines;
    }

    public void setDefines(List<String> defines) {
        this.defines = defines;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAdjTypeId() {
        return this.adjTypeId;
    }

    public void setAdjTypeId(String adjTypeId) {
        this.adjTypeId = adjTypeId;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<String> getTaskCodes() {
        return this.taskCodes;
    }

    public void setTaskCodes(List<String> taskCodes) {
        this.taskCodes = taskCodes;
    }

    public NpContext getNpContext() {
        return this.npContext;
    }

    public void setNpContext(NpContext npContext) {
        this.npContext = npContext;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

