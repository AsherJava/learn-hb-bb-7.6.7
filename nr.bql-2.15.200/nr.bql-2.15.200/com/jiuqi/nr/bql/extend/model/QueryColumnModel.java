/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.extend.model;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class QueryColumnModel
implements ColumnModelDefine {
    private static final long serialVersionUID = -4794449504978709485L;
    private DataField dataField;
    private DataFieldDeployInfo deployInfo;
    private String referTableID;
    private String referColumnID;

    public QueryColumnModel(DataField dataField, DataFieldDeployInfo deployInfo) {
        this.dataField = dataField;
        this.deployInfo = deployInfo;
    }

    public String getID() {
        return this.dataField.getKey();
    }

    public String getCode() {
        return this.dataField.getCode();
    }

    public String getTableID() {
        return this.dataField.getDataTableKey();
    }

    public String getName() {
        return this.deployInfo.getFieldName();
    }

    public String getTitle() {
        return this.dataField.getTitle();
    }

    public String getDesc() {
        return this.dataField.getDesc();
    }

    public String getCatagory() {
        return null;
    }

    public ColumnModelType getColumnType() {
        return ColumnModelType.forValue((int)this.dataField.getDataFieldType().getValue());
    }

    public int getPrecision() {
        return this.dataField.getPrecision() == null ? 0 : this.dataField.getPrecision();
    }

    public int getDecimal() {
        return this.dataField.getDecimal() == null ? 0 : this.dataField.getDecimal();
    }

    public boolean isNullAble() {
        return this.dataField.isNullable();
    }

    public String getDefaultValue() {
        return this.dataField.getDefaultValue();
    }

    public String getReferTableID() {
        return this.referTableID;
    }

    public String getReferColumnID() {
        return this.referColumnID;
    }

    public String getFilter() {
        return null;
    }

    public boolean isMultival() {
        return this.dataField.isAllowMultipleSelect();
    }

    public AggrType getAggrType() {
        return this.dataField.getDataFieldGatherType() == null ? AggrType.SUM : AggrType.forValue((int)this.dataField.getDataFieldGatherType().getValue());
    }

    public ApplyType getApplyType() {
        return this.dataField.getDataFieldApplyType() == null ? ApplyType.PERIODIC : ApplyType.forValue((int)this.dataField.getDataFieldApplyType().getValue());
    }

    public String getShowFormat() {
        return this.dataField.getFormatProperties() != null ? this.dataField.getFormatProperties().getPattern() : null;
    }

    public String getMeasureUnit() {
        return this.dataField.getMeasureUnit();
    }

    public ColumnModelKind getKind() {
        return ColumnModelKind.DEFAULT;
    }

    public double getOrder() {
        return 0.0;
    }

    public String getLocaleTitle() {
        return this.dataField.getTitle();
    }

    public void setReferTableID(String referTableID) {
        this.referTableID = referTableID;
    }

    public void setReferColumnID(String referColumnID) {
        this.referColumnID = referColumnID;
    }

    public String getSceneId() {
        return null;
    }

    public DataFieldDeployInfo getDeployInfo() {
        return this.deployInfo;
    }

    public void setID(String id) {
    }

    public void setTableID(String tableID) {
    }

    public void setCode(String code) {
    }

    public void setName(String name) {
    }

    public void setTitle(String title) {
    }

    public void setDesc(String desc) {
    }

    public void setCatagory(String catagory) {
    }

    public void setColumnType(ColumnModelType type) {
    }

    public void setPrecision(int precision) {
    }

    public void setDecimal(int decimal) {
    }

    public void setNullAble(boolean nullAble) {
    }

    public void setDefaultValue(String defaultValue) {
    }

    public void setFilter(String filter) {
    }

    public void setMultival(boolean multival) {
    }

    public void setAggrType(AggrType aggrType) {
    }

    public void setApplyType(ApplyType applyType) {
    }

    public void setShowFormat(String showFormat) {
    }

    public void setMeasureUnit(String measureUnit) {
    }

    public void setKind(ColumnModelKind kind) {
    }

    public void setOrder(double order) {
    }

    public void setSceneId(String sceneId) {
    }
}

