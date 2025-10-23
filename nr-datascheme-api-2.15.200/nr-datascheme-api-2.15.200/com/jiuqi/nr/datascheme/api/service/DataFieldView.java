/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.AmountUnit;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DataFieldView {
    private Integer index;
    private String key;
    private String title;
    private String code;
    private String physicalFieldName;
    private String tableKey;
    private String physicalTableName;
    private String zbType;
    private String dataFieldType;
    private Integer precision;
    private Integer decimal;
    private String nullable;
    private String defaultValue;
    private String desc;
    private String refEntityId;
    private String refEntityTitle;
    private String dataFieldGatherType;
    private String measureType;
    private String measureUnit;
    private Map<String, Object> extProp;
    private String formula;
    private String formulaDesc;

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhysicalFieldName() {
        return this.physicalFieldName;
    }

    public void setPhysicalFieldName(String physicalFieldName) {
        this.physicalFieldName = physicalFieldName;
    }

    public String getPhysicalTableName() {
        return this.physicalTableName;
    }

    public void setPhysicalTableName(String physicalTableName) {
        this.physicalTableName = physicalTableName;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getZbType() {
        return this.zbType;
    }

    public void setZbType(String zbType) {
        this.zbType = zbType;
    }

    public String getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(String dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRefEntityId() {
        return this.refEntityId;
    }

    public void setRefEntityId(String refEntityId) {
        this.refEntityId = refEntityId;
    }

    public String getRefEntityTitle() {
        return this.refEntityTitle;
    }

    public void setRefEntityTitle(String refEntityTitle) {
        this.refEntityTitle = refEntityTitle;
    }

    public String getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(String dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public String getMeasureType() {
        return this.measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaDesc() {
        return this.formulaDesc;
    }

    public void setFormulaDesc(String formulaDesc) {
        this.formulaDesc = formulaDesc;
    }

    public Map<String, Object> getExtProp() {
        return this.extProp;
    }

    public void setExtProp(Map<String, Object> extProp) {
        this.extProp = extProp;
    }

    public static DataFieldView valueOf(DataField dataField, ZbInfo zbInfo) {
        if (zbInfo == null) {
            return DataFieldView.valueOf(dataField);
        }
        if (dataField == null) {
            return null;
        }
        DataFieldView view = new DataFieldView();
        view.setCode(dataField.getCode());
        view.setTableKey(dataField.getDataTableKey());
        view.setKey(zbInfo.getKey());
        view.setTitle(zbInfo.getTitle());
        view.setFormula(zbInfo.getFormula());
        view.setFormulaDesc(zbInfo.getFormulaDesc());
        view.setExtProp(zbInfo.getExtPropMap());
        if (null != zbInfo.getType()) {
            view.setZbType(zbInfo.getType().getTitle());
        }
        if (null != zbInfo.getDataType()) {
            view.setDataFieldType(zbInfo.getDataType().getTitle());
        }
        view.setPrecision(zbInfo.getPrecision());
        view.setDecimal(zbInfo.getDecimal());
        view.setNullable(zbInfo.isNullable() ? "\u662f" : "\u5426");
        view.setDefaultValue(zbInfo.getDefaultValue());
        view.setDesc(zbInfo.getDesc());
        view.setRefEntityId(zbInfo.getRefEntityId());
        if (null != zbInfo.getGatherType()) {
            view.setDataFieldGatherType(zbInfo.getGatherType().getTitle());
        }
        if (null != zbInfo.getMeasureUnit()) {
            AmountUnit unit;
            String replace = zbInfo.getMeasureUnit().replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
            if (!"NotDimession".equals(replace) && !"-".equals(replace)) {
                view.setMeasureType("\u91d1\u989d");
            }
            if ((unit = AmountUnit.getByCode(replace)) != null) {
                view.setMeasureUnit(unit.getTitle());
            }
        }
        return view;
    }

    public static DataFieldView valueOf(DataField dataField) {
        if (dataField == null) {
            return null;
        }
        DataFieldView view = new DataFieldView();
        view.setCode(dataField.getCode());
        view.setTableKey(dataField.getDataTableKey());
        view.setKey(dataField.getKey());
        view.setTitle(dataField.getTitle());
        view.setFormula(dataField.getFormula());
        view.setFormulaDesc(dataField.getFormulaDesc());
        if (null != dataField.getZbType()) {
            view.setZbType(dataField.getZbType().getTitle());
        }
        if (null != dataField.getDataFieldType()) {
            view.setDataFieldType(dataField.getDataFieldType().getTitle());
        }
        view.setPrecision(dataField.getPrecision());
        view.setDecimal(dataField.getDecimal());
        view.setNullable(dataField.isNullable() ? "\u662f" : "\u5426");
        view.setDefaultValue(dataField.getDefaultValue());
        view.setDesc(dataField.getDesc());
        view.setRefEntityId(dataField.getRefDataEntityKey());
        if (null != dataField.getDataFieldGatherType()) {
            view.setDataFieldGatherType(dataField.getDataFieldGatherType().getTitle());
        }
        if (null != dataField.getMeasureUnit()) {
            AmountUnit unit;
            String replace = dataField.getMeasureUnit().replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
            if (!"NotDimession".equals(replace) && !"-".equals(replace)) {
                view.setMeasureType("\u91d1\u989d");
            }
            if ((unit = AmountUnit.getByCode(replace)) != null) {
                view.setMeasureUnit(unit.getTitle());
            }
        }
        return view;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.JSON_STYLE).append("index", (Object)this.index).append("key", (Object)this.key).append("title", (Object)this.title).append("code", (Object)this.code).append("physicalFieldCode", (Object)this.physicalFieldName).append("tableKey", (Object)this.tableKey).append("physicalTableCode", (Object)this.physicalTableName).append("zbType", (Object)this.zbType).append("dataFieldType", (Object)this.dataFieldType).append("precision", (Object)this.precision).append("decimal", (Object)this.decimal).append("nullable", (Object)this.nullable).append("defaultValue", (Object)this.defaultValue).append("desc", (Object)this.desc).append("refEntityId", (Object)this.refEntityId).append("refEntityTitle", (Object)this.refEntityTitle).append("dataFieldGatherType", (Object)this.dataFieldGatherType).append("measureType", (Object)this.measureType).append("measureUnit", (Object)this.measureUnit).append("extProp", this.extProp).append("formula", (Object)this.formula).append("formulaDesc", (Object)this.formulaDesc).toString();
    }
}

