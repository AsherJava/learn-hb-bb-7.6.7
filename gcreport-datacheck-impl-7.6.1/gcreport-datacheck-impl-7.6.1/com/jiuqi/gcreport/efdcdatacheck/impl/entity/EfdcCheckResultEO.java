/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_EFDCCHECKRESULT", title="\u6279\u91cf\u6570\u636e\u7a3d\u6838\u7ed3\u679c\u8868", inStorage=true, indexs={})
public class EfdcCheckResultEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_EFDCCHECKRESULT";
    @DBColumn(nameInDB="ASYNTASKID", title="\u5355\u6b21\u5f02\u6b65\u64cd\u4f5cID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String asynTaskId;
    @DBColumn(nameInDB="FORMKEY", title="\u62a5\u8868ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String formKey;
    @DBColumn(nameInDB="fieldKey", title="\u6307\u6807ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String fieldKey;
    @DBColumn(nameInDB="expression", title="efdc\u53d6\u6570\u516c\u5f0f", dbType=DBColumn.DBType.NVarchar, length=1000, isRequired=true)
    private String expression;
    @DBColumn(nameInDB="zbValue", title="\u6307\u6807\u503c", dbType=DBColumn.DBType.Numeric, precision=19, scale=4, isRequired=true)
    private Double zbValue;
    @DBColumn(nameInDB="efdcValue", title="efdc\u503c", dbType=DBColumn.DBType.Numeric, precision=19, scale=4, isRequired=true)
    private Double efdcValue;
    @DBColumn(nameInDB="orgId", title="\u5355\u4f4dId", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgId;
    @DBColumn(nameInDB="currency", title="\u5e01\u79cd", dbType=DBColumn.DBType.Varchar, length=36)
    private String currency;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Date, isRequired=true)
    protected Date createTime;
    @DBColumn(nameInDB="DIMENSIONVALUESET", title="\u7ef4\u5ea6\u503c", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String dimensionValueSet;

    public String getAsynTaskId() {
        return this.asynTaskId;
    }

    public void setAsynTaskId(String asynTaskId) {
        this.asynTaskId = asynTaskId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getZbValue() {
        return this.zbValue;
    }

    public void setZbValue(Double zbValue) {
        this.zbValue = zbValue;
    }

    public Double getEfdcValue() {
        return this.efdcValue;
    }

    public void setEfdcValue(Double efdcValue) {
        this.efdcValue = efdcValue;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static String getTablename() {
        return TABLENAME;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public String getFieldKeyStr() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(String dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

