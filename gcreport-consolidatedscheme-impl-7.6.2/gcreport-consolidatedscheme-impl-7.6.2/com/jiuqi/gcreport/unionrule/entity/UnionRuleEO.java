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
package com.jiuqi.gcreport.unionrule.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;
import java.util.Objects;

@DBTable(name="GC_UNIONRULE", title="\u5408\u5e76\u89c4\u5219\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_UNIONRULE_PID_SYSID", columnsFields={"PARENTID", "REPORTSYSTEM"}), @DBIndex(name="IDX_GC_UNIONRULE_SYSID", columnsFields={"REPORTSYSTEM"})})
public class UnionRuleEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_UNIONRULE";
    @DBColumn(nameInDB="parentid", title="\u7236\u7ea7ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Int)
    private Integer sortOrder;
    @DBColumn(nameInDB="title", title="\u8282\u70b9\u540d\u79f0", dbType=DBColumn.DBType.NVarchar)
    private String title;
    @DBColumn(nameInDB="leafflag", title="\u662f\u5426\u53f6\u5b50\u8282\u70b9", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer leafFlag;
    @DBColumn(nameInDB="startflag", title="\u662f\u5426\u542f\u7528", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer startFlag;
    @DBColumn(nameInDB="creator", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=80)
    private String creator;
    @DBColumn(nameInDB="createtime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="UPDATOR", title="\u66f4\u65b0\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updator;
    @DBColumn(nameInDB="updatetime", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date updateTime;
    @DBColumn(nameInDB="jsonstring", title="Json\u5b57\u7b26\u4e32", dbType=DBColumn.DBType.Text)
    private String jsonString;
    @DBColumn(nameInDB="reportsystem", title="\u5408\u5e76\u62a5\u8868\u4f53\u7cfb", dbType=DBColumn.DBType.Varchar, length=36)
    private String reportSystem;
    @DBColumn(nameInDB="ruletype", title="\u89c4\u5219\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String ruleType;
    @DBColumn(nameInDB="rulecode", title="\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=80)
    private String ruleCode;
    @DBColumn(nameInDB="rulecondition", title="\u9002\u7528\u6761\u4ef6", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String ruleCondition;
    @DBColumn(nameInDB="businesstypecode", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578bCode", dbType=DBColumn.DBType.NVarchar, length=80)
    private String businessTypeCode;
    @DBColumn(nameInDB="enabletoleranceflag", title="\u662f\u5426\u542f\u7528\u5bb9\u5dee", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer enableToleranceFlag;
    @DBColumn(nameInDB="offsettype", title="\u62b5\u6d88\u65b9\u5f0f", dbType=DBColumn.DBType.NVarchar)
    private String offsetType;
    @DBColumn(nameInDB="offsetformula", title="\u62b5\u6d88\u65b9\u5f0f\u516c\u5f0f", dbType=DBColumn.DBType.NVarchar, length=80)
    private String offsetFormula;
    @DBColumn(nameInDB="tolerancetype", title="\u5bb9\u5dee\u65b9\u5f0f", dbType=DBColumn.DBType.NVarchar)
    private String toleranceType;
    @DBColumn(nameInDB="tolerancerange", title="\u5bb9\u5dee\u8303\u56f4", dbType=DBColumn.DBType.Double)
    private Double toleranceRange;
    @DBColumn(nameInDB="inittypeflag", title="\u521d\u59cb\u89c4\u5219\u7c7b\u578b\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer initTypeFlag;
    @DBColumn(nameInDB="applyGcUnits", title="\u9002\u7528\u5408\u5e76\u5355\u4f4d", dbType=DBColumn.DBType.Text)
    private String applyGcUnits;

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

    public Integer getEnableToleranceFlag() {
        return this.enableToleranceFlag;
    }

    public void setEnableToleranceFlag(Integer enableToleranceFlag) {
        this.enableToleranceFlag = enableToleranceFlag;
    }

    public String getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }

    public String getOffsetFormula() {
        return this.offsetFormula;
    }

    public void setOffsetFormula(String offsetFormula) {
        this.offsetFormula = offsetFormula;
    }

    public String getToleranceType() {
        return this.toleranceType;
    }

    public void setToleranceType(String toleranceType) {
        this.toleranceType = toleranceType;
    }

    public Double getToleranceRange() {
        return this.toleranceRange;
    }

    public void setToleranceRange(Double toleranceRange) {
        this.toleranceRange = toleranceRange;
    }

    public Integer getInitTypeFlag() {
        return this.initTypeFlag;
    }

    public void setInitTypeFlag(Integer initTypeFlag) {
        this.initTypeFlag = initTypeFlag;
    }

    public String getApplyGcUnits() {
        return this.applyGcUnits;
    }

    public void setApplyGcUnits(String applyGcUnits) {
        this.applyGcUnits = applyGcUnits;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnionRuleEO)) {
            return false;
        }
        UnionRuleEO ruleEO = (UnionRuleEO)((Object)o);
        return Objects.equals(this.getId(), ruleEO.getId()) && Objects.equals(this.getParentId(), ruleEO.getParentId());
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getParentId());
    }
}

