/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlrule;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import java.sql.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

public class SameCtrlRuleVO
extends BaseRuleVO {
    @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a") String parentId;
    private Integer sortOrder;
    private Boolean leafFlag;
    private Boolean startFlag;
    private String creator;
    private Date createTime;
    private String updator;
    private Date updateTime;
    private String jsonString;
    @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a") String reportSystem;
    private SameCtrlRuleTypeEnum ruleType;
    private String dataType;
    private String dataTypeDescription;
    private String ruleCode;
    private String ruleCondition;
    @NotNull(message="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") GcBaseDataVO businessTypeCode;
    private List<SameCtrlRuleVO> children;
    private String taskId;

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Boolean getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public SameCtrlRuleTypeEnum getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(SameCtrlRuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }

    public String getDataTypeDescription() {
        return this.dataTypeDescription;
    }

    public void setDataTypeDescription(String dataTypeDescription) {
        this.dataTypeDescription = dataTypeDescription;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleCondition() {
        return this.ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public GcBaseDataVO getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(GcBaseDataVO businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public List<SameCtrlRuleVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<SameCtrlRuleVO> children) {
        this.children = children;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLabel() {
        return this.getTitle();
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String toString() {
        return "SameCtrlRuleVO{parentId='" + this.parentId + '\'' + ", sortOrder=" + this.sortOrder + ", leafFlag=" + this.leafFlag + ", startFlag=" + this.startFlag + ", creator='" + this.creator + '\'' + ", createTime=" + this.createTime + ", updator='" + this.updator + '\'' + ", updateTime=" + this.updateTime + ", jsonString='" + this.jsonString + '\'' + ", reportSystem='" + this.reportSystem + '\'' + ", ruleType='" + (Object)((Object)this.ruleType) + '\'' + ", dataTypeDescription='" + this.dataTypeDescription + '\'' + ", ruleCode='" + this.ruleCode + '\'' + ", ruleCondition='" + this.ruleCondition + '\'' + ", businessTypeCode=" + this.businessTypeCode + ", children=" + this.children + ", dataType=" + this.dataType + '}';
    }
}

