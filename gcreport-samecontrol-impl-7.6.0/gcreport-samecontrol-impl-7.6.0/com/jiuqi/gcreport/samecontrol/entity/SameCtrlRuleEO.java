/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import java.util.Objects;

@DBTable(name="GC_SAMECTRLRULE", title="\u540c\u63a7\u89c4\u5219\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_SAMECTRLRULE_PID_SYSID", columnsFields={"parentId", "reportSystem"}), @DBIndex(name="IDX_GC_SAMECTRLRULE_SYSID", columnsFields={"reportSystem"})})
public class SameCtrlRuleEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRLRULE";
    @DBColumn(title="\u7236\u7ea7ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;
    @DBColumn(title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Int)
    private Integer sortOrder;
    @DBColumn(title="\u8282\u70b9\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String title;
    @DBColumn(title="\u662f\u5426\u53f6\u5b50\u8282\u70b9", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer leafFlag;
    @DBColumn(title="\u662f\u5426\u542f\u7528", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer startFlag;
    @DBColumn(title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=80)
    private String creator;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u66f4\u65b0\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updator;
    @DBColumn(title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date updateTime;
    @DBColumn(title="Json\u5b57\u7b26\u4e32", dbType=DBColumn.DBType.Text)
    private String jsonString;
    @DBColumn(title="\u5408\u5e76\u62a5\u8868\u4f53\u7cfb", dbType=DBColumn.DBType.Varchar, length=36)
    private String reportSystem;
    @DBColumn(title="\u89c4\u5219\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String ruleType;
    @DBColumn(title="\u6570\u636e\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String dataType;
    @DBColumn(title="\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=80)
    private String ruleCode;
    @DBColumn(title="\u9002\u7528\u6761\u4ef6", dbType=DBColumn.DBType.NVarchar, length=300)
    private String ruleCondition;
    @DBColumn(title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578bCode", dbType=DBColumn.DBType.NVarchar, length=80)
    private String businessTypeCode;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Integer leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Integer getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Integer startFlag) {
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

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
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

    public String getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SameCtrlRuleEO)) {
            return false;
        }
        SameCtrlRuleEO ruleEO = (SameCtrlRuleEO)((Object)o);
        return Objects.equals(this.getId(), ruleEO.getId()) && Objects.equals(this.getParentId(), ruleEO.getParentId());
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getParentId());
    }
}

