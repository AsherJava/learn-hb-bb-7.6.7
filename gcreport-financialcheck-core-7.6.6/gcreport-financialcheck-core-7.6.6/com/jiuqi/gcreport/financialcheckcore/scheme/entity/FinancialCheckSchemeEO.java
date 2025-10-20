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
package com.jiuqi.gcreport.financialcheckcore.scheme.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FCSCHEME", title="\u5bf9\u8d26\u65b9\u6848\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_FINCHECKSCHEME_PARENT", columnsFields={"PARENTID"})})
public class FinancialCheckSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FCSCHEME";
    @DBColumn(nameInDB="SCHEMENAME", title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true)
    private String schemeName;
    @DBColumn(nameInDB="CHECKMODE", title="\u5bf9\u8d26\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar)
    private String checkMode;
    @DBColumn(nameInDB="CHECKATTRIBUTE", title="\u5bf9\u8d26\u5c5e\u6027", dbType=DBColumn.DBType.Varchar)
    private String checkAttribute;
    @DBColumn(nameInDB="UNITCODES", title="\u9002\u7528\u5355\u4f4d", dbType=DBColumn.DBType.Text)
    private String unitCodes;
    @DBColumn(nameInDB="CHECKDIMENSION", title="\u5bf9\u8d26\u7ef4\u5ea6", dbType=DBColumn.DBType.NVarchar, length=500)
    private String checkDimensions;
    @DBColumn(nameInDB="TOLERANCEENABLE", title="\u662f\u5426\u5bb9\u5dee", dbType=DBColumn.DBType.Int)
    private Integer toleranceEnable;
    @DBColumn(nameInDB="TOLERANCEAMOUNT", title="\u5bb9\u5dee\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double toleranceAmount;
    @DBColumn(nameInDB="TOLERANCERATE", title="\u5bb9\u5dee\u7a0e\u7387", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double toleranceRate;
    @DBColumn(nameInDB="checkAmount", title="\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Int)
    private Integer checkAmount;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int)
    private Integer acctYear;
    @DBColumn(nameInDB="DESCRIPTION", title="\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=200)
    private String description;
    @DBColumn(nameInDB="SCHEMETYPE", title="\u5206\u7ec4\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer schemeType;
    @DBColumn(nameInDB="PARENTID", title="\u7236\u8282\u70b9\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;
    @DBColumn(nameInDB="SCHEMEENABLE", title="\u662f\u5426\u542f\u7528\u6807\u8bc6", dbType=DBColumn.DBType.Int)
    private Integer enable;
    @DBColumn(nameInDB="SPECIALCHECK", title="\u7279\u6b8a\u573a\u666f\u5bf9\u8d26", dbType=DBColumn.DBType.Int)
    private Integer specialCheck;
    @DBColumn(nameInDB="SORTORDER", title="\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric, precision=10, scale=6)
    private Double sortOrder;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    @DBColumn(nameInDB="SCHEMECONDITION", title="\u9002\u7528\u6761\u4ef6", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String schemeCondition;
    @DBColumn(nameInDB="jsonstring", title="Json\u5b57\u7b26\u4e32", dbType=DBColumn.DBType.Text)
    private String jsonString;

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getCheckDimensions() {
        return this.checkDimensions;
    }

    public void setCheckDimensions(String checkDimensions) {
        this.checkDimensions = checkDimensions;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
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

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(String unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getCheckMode() {
        return this.checkMode;
    }

    public void setCheckMode(String checkMode) {
        this.checkMode = checkMode;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }

    public Integer getToleranceEnable() {
        return this.toleranceEnable;
    }

    public void setToleranceEnable(Integer toleranceEnable) {
        this.toleranceEnable = toleranceEnable;
    }

    public Double getToleranceAmount() {
        return this.toleranceAmount;
    }

    public void setToleranceAmount(Double toleranceAmount) {
        this.toleranceAmount = toleranceAmount;
    }

    public Double getToleranceRate() {
        return this.toleranceRate;
    }

    public void setToleranceRate(Double toleranceRate) {
        this.toleranceRate = toleranceRate;
    }

    public Integer getCheckAmount() {
        return this.checkAmount;
    }

    public void setCheckAmount(Integer checkAmount) {
        this.checkAmount = checkAmount;
    }

    public Integer getSchemeType() {
        return this.schemeType;
    }

    public void setSchemeType(Integer schemeType) {
        this.schemeType = schemeType;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getSpecialCheck() {
        return this.specialCheck;
    }

    public void setSpecialCheck(Integer specialCheck) {
        this.specialCheck = specialCheck;
    }

    public String getSchemeCondition() {
        return this.schemeCondition;
    }

    public void setSchemeCondition(String schemeCondition) {
        this.schemeCondition = schemeCondition;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}

