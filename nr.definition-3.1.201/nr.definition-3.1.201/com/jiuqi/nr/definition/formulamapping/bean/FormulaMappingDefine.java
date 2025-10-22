/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.formulamapping.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.formulamapping.util.ExcelUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="SYS_FORMULA_MAPPING")
public class FormulaMappingDefine
implements Serializable,
ExcelUtils.ISheetRowData {
    private static final long serialVersionUID = 5417703024650871654L;
    public static final String DB_TABLE = "SYS_FORMULA_MAPPING";
    public static final String DB_FIELD_KEY = "FM_KEY";
    public static final String DB_FIELD_SCHEME_KEY = "FM_SCHEME_KEY";
    public static final String DB_FIELD_TARGET_FORM_KEY = "FM_TARGET_FORM_KEY";
    public static final String DB_FIELD_TARGET_CODE = "FM_TARGET_CODE";
    public static final String DB_FIELD_TARGET_EXP = "FM_TARGET_EXPRESSION";
    public static final String DB_FIELD_SOURCE_KEY = "FM_SOURCE_KEY";
    public static final String DB_FIELD_SOURCE_CHECKTYPE = "FM_SOURCE_CHECKTYPE";
    public static final String DB_FIELD_SOURCE_CODE = "FM_SOURCE_CODE";
    public static final String DB_FIELD_SOURCE_EXP = "FM_SOURCE_EXPRESSION";
    public static final String DB_FIELD_GROUP = "FM_GROUP";
    public static final String DB_FIELD_ORDER = "FM_ORDER";
    public static final String DB_FIELD_KIND = "FM_KIND";
    public static final String DB_FIELD_MODE = "FM_MODE";
    public static final String CLZ_FIELD_SCHEME_KEY = "schemeKey";
    public static final String CLZ_FIELD_TARGET_FORM_KEY = "targetFormKey";
    public static final String CLZ_FIELD_TARGET_GROUP = "group";
    @DBAnno.DBField(dbField="FM_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="FM_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="FM_TARGET_FORM_KEY")
    private String targetFormKey;
    @DBAnno.DBField(dbField="FM_TARGET_KEY")
    private String targetKey;
    @DBAnno.DBField(dbField="FM_TARGET_CODE")
    private String targetCode;
    @DBAnno.DBField(dbField="FM_TARGET_EXPRESSION", dbType=Clob.class)
    private String targetExpression;
    @DBAnno.DBField(dbField="FM_TARGET_CHECKTYPE")
    private int targetCheckType;
    @DBAnno.DBField(dbField="FM_SOURCE_KEY")
    private String sourceKey;
    @DBAnno.DBField(dbField="FM_SOURCE_CODE")
    private String sourceCode;
    @DBAnno.DBField(dbField="FM_SOURCE_EXPRESSION", dbType=Clob.class)
    private String sourceExpression;
    @DBAnno.DBField(dbField="FM_SOURCE_CHECKTYPE")
    private int sourceCheckType;
    @DBAnno.DBField(dbField="FM_MODE")
    private int mode;
    @DBAnno.DBField(dbField="FM_KIND")
    private int kind;
    @DBAnno.DBField(dbField="FM_GROUP")
    private String group;
    @DBAnno.DBField(dbField="FM_ORDER", isOrder=true)
    private BigDecimal order;
    @DBAnno.DBField(dbField="FM_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getTargetFormKey() {
        return this.targetFormKey;
    }

    public void setTargetFormKey(String targetFormKey) {
        this.targetFormKey = targetFormKey;
    }

    public String getTargetCode() {
        return this.targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetExpression() {
        return this.targetExpression;
    }

    public void setTargetExpression(String targetExpression) {
        this.targetExpression = targetExpression;
    }

    public int getTargetCheckType() {
        return this.targetCheckType;
    }

    public void setTargetCheckType(int targetCheckType) {
        this.targetCheckType = targetCheckType;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceExpression() {
        return this.sourceExpression;
    }

    public void setSourceExpression(String sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    public int getSourceCheckType() {
        return this.sourceCheckType;
    }

    public void setSourceCheckType(int sourceCheckType) {
        this.sourceCheckType = sourceCheckType;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getKind() {
        return this.kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getOrder() {
        return this.order.doubleValue();
    }

    public void setOrder(double order) {
        this.order = new BigDecimal(order);
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    @Override
    public Object[] getRowDatas() {
        return new Object[]{this.targetCode, this.targetExpression, this.sourceCode, this.sourceExpression};
    }

    @Override
    public void setRowDatas(Object[] datas) {
        if (datas.length > 0) {
            this.targetCode = (String)datas[0];
        }
        if (datas.length > 1) {
            this.targetExpression = (String)datas[1];
        }
        if (datas.length > 2) {
            this.sourceCode = (String)datas[2];
        }
        if (datas.length > 3) {
            this.sourceExpression = (String)datas[3];
        }
    }
}

