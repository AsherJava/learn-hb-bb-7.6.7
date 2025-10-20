/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class ConsolidatedTaskVO {
    private String id;
    @NotNull(message="\u627e\u4e0d\u5230\u91c7\u96c6\u4efb\u52a1key")
    private @NotNull(message="\u627e\u4e0d\u5230\u91c7\u96c6\u4efb\u52a1key") String taskKey;
    private String dataScheme;
    @NotNull(message="\u627e\u4e0d\u5230\u7ba1\u7406\u67b6\u6784\u4efb\u52a1key")
    private @NotNull(message="\u627e\u4e0d\u5230\u7ba1\u7406\u67b6\u6784\u4efb\u52a1key") List<String> manageTaskKeys;
    private List<OrgTypeVO> taskOrgTypeVoList;
    private String corporateEntity;
    private List<String> manageEntityList;
    private boolean isTaskVersion2_0 = false;
    private String fromPeriod;
    private String toPeriod;
    private String periodTypeTitle;
    private Boolean enableCalc;
    private Boolean enableInputAdjust;
    private Boolean enableManualOffset;
    private Boolean enableLossGain;
    private Boolean enableReclassify;
    private Boolean enableFinishCalc;
    private Boolean enableDeferredIncomeTax;
    private Boolean enableInvestWokrPaper;
    private Boolean enableMinLossGainRecovery;
    private Boolean enableReduceReclassify;
    private String taskTitle;
    private List<TaskInfoVO> manageTaskInfos;
    private TaskInfoVO inputTaskInfo;
    @NotNull(message="\u83b7\u53d6\u4f53\u7cfb\u5931\u8d25.")
    private @NotNull(message="\u83b7\u53d6\u4f53\u7cfb\u5931\u8d25.") String systemId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifiedTime;
    private String modifiedUser;
    private String sortOrder;
    private String diffRewriteWay;
    private LinkedHashSet<String> manageCalcUnitCodes;
    private String moreUnitOffset;
    private List<Map<String, String>> enableRange;

    public boolean isTaskVersion2_0() {
        return this.isTaskVersion2_0;
    }

    public void setTaskVersion2_0(boolean taskVersion2_0) {
        this.isTaskVersion2_0 = taskVersion2_0;
    }

    public List<OrgTypeVO> getTaskOrgTypeVoList() {
        return this.taskOrgTypeVoList;
    }

    public void setTaskOrgTypeVoList(List<OrgTypeVO> taskOrgTypeVoList) {
        this.taskOrgTypeVoList = taskOrgTypeVoList;
    }

    public String getCorporateEntity() {
        return this.corporateEntity;
    }

    public void setCorporateEntity(String corporateEntity) {
        this.corporateEntity = corporateEntity;
    }

    public List<String> getManageEntityList() {
        return this.manageEntityList;
    }

    public void setManageEntityList(List<String> manageEntityList) {
        this.manageEntityList = manageEntityList;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Date getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedUser() {
        return this.modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getPeriodTypeTitle() {
        return this.periodTypeTitle;
    }

    public void setPeriodTypeTitle(String periodTypeTitle) {
        this.periodTypeTitle = periodTypeTitle;
    }

    public Boolean getEnableCalc() {
        return !Boolean.FALSE.equals(this.enableCalc);
    }

    public void setEnableCalc(Boolean enableCalc) {
        this.enableCalc = enableCalc;
    }

    public Boolean getEnableInputAdjust() {
        return !Boolean.FALSE.equals(this.enableInputAdjust);
    }

    public void setEnableInputAdjust(Boolean enableInputAdjust) {
        this.enableInputAdjust = enableInputAdjust;
    }

    public Boolean getEnableManualOffset() {
        return !Boolean.FALSE.equals(this.enableManualOffset);
    }

    public void setEnableManualOffset(Boolean enableManualOffset) {
        this.enableManualOffset = enableManualOffset;
    }

    public Boolean getEnableLossGain() {
        return !Boolean.FALSE.equals(this.enableLossGain);
    }

    public void setEnableLossGain(Boolean enableLossGain) {
        this.enableLossGain = enableLossGain;
    }

    public Boolean getEnableFinishCalc() {
        return !Boolean.FALSE.equals(this.enableFinishCalc);
    }

    public void setEnableFinishCalc(Boolean enableFinishCalc) {
        this.enableFinishCalc = enableFinishCalc;
    }

    public Boolean getEnableDeferredIncomeTax() {
        return Boolean.TRUE.equals(this.enableDeferredIncomeTax);
    }

    public void setEnableDeferredIncomeTax(Boolean enableDeferredIncomeTax) {
        this.enableDeferredIncomeTax = enableDeferredIncomeTax;
    }

    public LinkedHashSet<String> getManageCalcUnitCodes() {
        return this.manageCalcUnitCodes;
    }

    public void setManageCalcUnitCodes(LinkedHashSet<String> manageCalcUnitCodes) {
        this.manageCalcUnitCodes = manageCalcUnitCodes;
    }

    public String getMoreUnitOffset() {
        if (this.moreUnitOffset == null) {
            return "0";
        }
        return this.moreUnitOffset;
    }

    public void setMoreUnitOffset(String moreUnitOffset) {
        this.moreUnitOffset = moreUnitOffset;
    }

    public void manualSetMoreInfo(String moreInfo) {
        if (StringUtils.isEmpty((String)moreInfo)) {
            return;
        }
        char[] charArray = moreInfo.toCharArray();
        int disable = 48;
        int index = charArray.length - 1;
        this.moreUnitOffset = charArray.length < 10 ? "0" : String.valueOf(charArray[index]);
        this.enableCalc = --index < 0 || '0' != charArray[--index + 1];
        this.enableInputAdjust = index-- < 0 || '0' != charArray[index + 1];
        this.enableManualOffset = index-- < 0 || '0' != charArray[index + 1];
        this.enableLossGain = index-- < 0 || '0' != charArray[index + 1];
        this.enableFinishCalc = index-- < 0 || '0' != charArray[index + 1];
        this.enableDeferredIncomeTax = index-- >= 0 && '0' != charArray[index + 1];
        this.enableInvestWokrPaper = index-- < 0 || '0' != charArray[index + 1];
        this.enableMinLossGainRecovery = index-- >= 0 && '0' != charArray[index + 1];
        this.enableReclassify = index-- >= 0 && '0' != charArray[index + 1];
        this.enableReduceReclassify = index-- >= 0 && '0' != charArray[index + 1];
    }

    public String manualGetMoreInfo() {
        StringBuffer result = new StringBuffer(9);
        result.append(this.getEnableReduceReclassify() != false ? "1" : "0");
        result.append(this.getEnableReclassify() != false ? "1" : "0");
        result.append(this.getEnableMinLossGainRecovery() != false ? "1" : "0");
        result.append(this.getEnableInvestWokrPaper() != false ? "1" : "0");
        result.append(this.getEnableDeferredIncomeTax() != false ? "1" : "0");
        result.append(this.getEnableFinishCalc() != false ? "1" : "0");
        result.append(this.getEnableLossGain() != false ? "1" : "0");
        result.append(this.getEnableManualOffset() != false ? "1" : "0");
        result.append(this.getEnableInputAdjust() != false ? "1" : "0");
        result.append(this.getEnableCalc() != false ? "1" : "0");
        result.append(this.getMoreUnitOffset());
        return result.toString();
    }

    public Boolean getEnableInvestWokrPaper() {
        return Boolean.TRUE.equals(this.enableInvestWokrPaper);
    }

    public void setEnableInvestWokrPaper(Boolean enableInvestWokrPaper) {
        this.enableInvestWokrPaper = enableInvestWokrPaper;
    }

    public Boolean getEnableMinLossGainRecovery() {
        return Boolean.TRUE.equals(this.enableMinLossGainRecovery);
    }

    public void setEnableMinLossGainRecovery(Boolean enableMinLossGainRecovery) {
        this.enableMinLossGainRecovery = enableMinLossGainRecovery;
    }

    public Boolean getEnableReclassify() {
        return Boolean.TRUE.equals(this.enableReclassify);
    }

    public void setEnableReclassify(Boolean enableReclassify) {
        this.enableReclassify = enableReclassify;
    }

    public String getDiffRewriteWay() {
        return this.diffRewriteWay;
    }

    public void setDiffRewriteWay(String diffRewriteWay) {
        this.diffRewriteWay = diffRewriteWay;
    }

    public List<String> getManageTaskKeys() {
        return this.manageTaskKeys;
    }

    public void setManageTaskKeys(List<String> manageTaskKeys) {
        this.manageTaskKeys = manageTaskKeys;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<TaskInfoVO> getManageTaskInfos() {
        return this.manageTaskInfos;
    }

    public void setManageTaskInfos(List<TaskInfoVO> manageTaskInfos) {
        this.manageTaskInfos = manageTaskInfos;
    }

    public TaskInfoVO getInputTaskInfo() {
        return this.inputTaskInfo;
    }

    public void setInputTaskInfo(TaskInfoVO inputTaskInfo) {
        this.inputTaskInfo = inputTaskInfo;
    }

    public List<Map<String, String>> getEnableRange() {
        return this.enableRange;
    }

    public void setEnableRange(List<Map<String, String>> enableRange) {
        this.enableRange = enableRange;
    }

    public Boolean getEnableReduceReclassify() {
        return Boolean.TRUE.equals(this.enableReduceReclassify);
    }

    public void setEnableReduceReclassify(Boolean enableReduceReclassify) {
        this.enableReduceReclassify = enableReduceReclassify;
    }
}

