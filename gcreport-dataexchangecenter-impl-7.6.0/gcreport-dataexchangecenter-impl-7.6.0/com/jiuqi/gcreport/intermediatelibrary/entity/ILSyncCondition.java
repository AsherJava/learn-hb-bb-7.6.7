/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.sql.Timestamp;

public class ILSyncCondition {
    private String code;
    private String title;
    private String descr;
    private Integer dataType;
    private Integer accuracy;
    private Integer decimal;
    private String floatarea;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String fieldId;
    private FieldDefine fieldDefine;
    private String formId;
    private DataRegionDefine dataRegionDefine;
    private TableDefine tableDefine;
    private String belongForm;

    public String getBelongForm() {
        return this.belongForm;
    }

    public void setBelongForm(String belongForm) {
        this.belongForm = belongForm;
    }

    public TableDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getFloatarea() {
        return this.floatarea;
    }

    public void setFloatarea(String floatarea) {
        this.floatarea = floatarea;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}

