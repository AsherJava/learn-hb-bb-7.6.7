/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonGetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.util.List;

@ApiModel(value="\u6307\u6807\u6216\u5b57\u6bb5")
public class DataFieldVO
extends BaseDataVO {
    @ApiModelProperty(value="\u522b\u540d")
    private String alias;
    @ApiModelProperty(value="\u6240\u5c5e\u6570\u636e\u65b9\u6848KEY")
    private String dataSchemeKey;
    @ApiModelProperty(value="\u6240\u5c5e\u6570\u636e\u65b9\u6848KEY")
    private String dataTableKey;
    @ApiModelProperty(value="\u7c7b\u578b(0:\u6307\u6807;1:\u5b57\u6bb5;2:\u7ef4\u5ea6\u6307\u6807/\u5b57\u6bb5)")
    private DataFieldKind dataFieldKind;
    @ApiModelProperty(value="\u6392\u5e8f")
    private String order;
    @ApiModelProperty(value="\u7248\u672c")
    private String version;
    @ApiModelProperty(value="\u7ea7\u6b21")
    private String level;
    private Instant updatetime;
    private String defaultValue;
    private Integer precision = 0;
    private DataFieldType dataFieldType;
    private Integer decimal = 0;
    private Boolean nullable = false;
    private String refDataEntityKey;
    private String refDataFieldKey;
    private Integer dimension;
    private String measureUnit;
    private DataFieldGatherType dataFieldGatherType;
    private Boolean allowMultipleSelect = false;
    private Integer secretLevel;
    private Boolean useAuthority = false;
    private DataFieldApplyType applyType;
    private String tableName;
    private String fieldName;
    private String refDataEntityTitle;
    private FormatVO formatVO;
    private List<ValidationRuleVO> validationRules;
    private boolean entityAttr;
    private Boolean allowUndefinedCode;
    private Boolean generateVersion;
    private Boolean changeWithPeriod;
    private Boolean allowTreeSum;
    private Boolean visible = true;
    private int index;
    private Boolean encrypted;
    private Boolean runEncrypted;
    private String dataMaskCode;
    private String dataMaskName;
    private String zbSchemeVersion;
    private ZbType zbType;
    private String formula;
    private String formulaDesc;

    public DataFieldVO(IEntityAttribute next, String tableName) {
        this.key = next.getID();
        this.code = next.getCode();
        this.title = next.getTitle();
        ColumnModelType columnType = next.getColumnType();
        if (columnType != null) {
            this.dataFieldType = DataFieldType.valueOf((int)columnType.getValue());
        }
        this.precision = next.getPrecision();
        this.decimal = next.getDecimal();
        this.defaultValue = next.getDefaultValue();
        this.nullable = next.isNullAble();
        this.entityAttr = true;
        this.fieldName = this.code;
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @JsonIgnore
    public DataFieldKind getDataFieldKind() {
        return this.dataFieldKind;
    }

    @JsonGetter(value="dataFieldKind")
    public int getDataFieldKindValue() {
        return null == this.dataFieldKind ? DataFieldKind.FIELD_ZB.getValue() : this.dataFieldKind.getValue();
    }

    @JsonSetter(value="dataFieldKind")
    public void setDataFieldKind(int dataFieldKind) {
        this.dataFieldKind = DataFieldKind.valueOf((int)dataFieldKind);
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Instant getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Instant updatetime) {
        this.updatetime = updatetime;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @JsonIgnore
    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
    }

    @JsonGetter(value="dataFieldType")
    public int getDataFieldTypeValue() {
        return null == this.dataFieldType ? DataFieldType.BIGDECIMAL.getValue() : this.dataFieldType.getValue();
    }

    @JsonSetter(value="dataFieldType")
    public void setDataFieldType(int dataFieldType) {
        this.dataFieldType = DataFieldType.valueOf((int)dataFieldType);
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public Boolean getNullable() {
        return null != this.nullable && this.nullable != false;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getRefDataFieldKey() {
        return this.refDataFieldKey;
    }

    public void setRefDataFieldKey(String refDataFieldKey) {
        this.refDataFieldKey = refDataFieldKey;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @JsonIgnore
    public DataFieldGatherType getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    @JsonGetter(value="dataFieldGatherType")
    public int getDataFieldGatherTypeValue() {
        return null == this.dataFieldGatherType ? DataFieldGatherType.NONE.getValue() : this.dataFieldGatherType.getValue();
    }

    @JsonSetter(value="dataFieldGatherType")
    public void setDataFieldGatherType(int dataFieldGatherType) {
        this.dataFieldGatherType = DataFieldGatherType.valueOf((int)dataFieldGatherType);
    }

    public Boolean getAllowMultipleSelect() {
        return null != this.allowMultipleSelect && this.allowMultipleSelect != false;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public Integer getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel;
    }

    public Boolean getUseAuthority() {
        return null != this.useAuthority && this.useAuthority != false;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    @JsonIgnore
    public DataFieldApplyType getApplyType() {
        return this.applyType;
    }

    @JsonGetter(value="applyType")
    public int getApplyTypeValue() {
        return null == this.applyType ? DataFieldApplyType.NONE.getValue() : this.applyType.getValue();
    }

    @JsonSetter(value="applyType")
    public void setApplyType(int applyType) {
        this.applyType = DataFieldApplyType.valueOf((int)applyType);
    }

    public void setDataFieldKind(DataFieldKind dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public void setDataFieldGatherType(DataFieldGatherType dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public void setApplyType(DataFieldApplyType applyType) {
        this.applyType = applyType;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public String getRefDataEntityTitle() {
        return this.refDataEntityTitle;
    }

    public void setRefDataEntityTitle(String refDataEntityTitle) {
        this.refDataEntityTitle = refDataEntityTitle;
    }

    public FormatVO getFormatVO() {
        return this.formatVO;
    }

    public void setFormatVO(FormatVO formatVO) {
        this.formatVO = formatVO;
    }

    public List<ValidationRuleVO> getValidationRules() {
        return this.validationRules;
    }

    public void setValidationRules(List<ValidationRuleVO> validationRules) {
        this.validationRules = validationRules;
    }

    public boolean isEntityAttr() {
        return this.entityAttr;
    }

    public void setEntityAttr(boolean entityAttr) {
        this.entityAttr = entityAttr;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DataFieldVO() {
    }

    public Boolean getGenerateVersion() {
        return this.generateVersion;
    }

    public void setGenerateVersion(Boolean generateVersion) {
        this.generateVersion = generateVersion;
    }

    public Boolean getChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public void setChangeWithPeriod(Boolean changeWithPeriod) {
        this.changeWithPeriod = changeWithPeriod;
    }

    public Integer getDimension() {
        return this.dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    public Boolean getAllowTreeSum() {
        return this.allowTreeSum;
    }

    public void setAllowTreeSum(Boolean allowTreeSum) {
        this.allowTreeSum = allowTreeSum;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public Boolean getRunEncrypted() {
        return this.runEncrypted;
    }

    public void setRunEncrypted(Boolean runEncrypted) {
        this.runEncrypted = runEncrypted;
    }

    public String getZbSchemeVersion() {
        return this.zbSchemeVersion;
    }

    public void setZbSchemeVersion(String zbSchemeVersion) {
        this.zbSchemeVersion = zbSchemeVersion;
    }

    public ZbType getZbType() {
        return this.zbType;
    }

    public void setZbType(ZbType zbType) {
        this.zbType = zbType;
    }

    public String getFormulaDesc() {
        return this.formulaDesc;
    }

    public void setFormulaDesc(String formulaDesc) {
        this.formulaDesc = formulaDesc;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDataMaskCode() {
        return this.dataMaskCode;
    }

    public void setDataMaskCode(String dataMaskCode) {
        this.dataMaskCode = dataMaskCode;
    }

    public String getDataMaskName() {
        return this.dataMaskName;
    }

    public void setDataMaskName(String dataMaskName) {
        this.dataMaskName = dataMaskName;
    }
}

