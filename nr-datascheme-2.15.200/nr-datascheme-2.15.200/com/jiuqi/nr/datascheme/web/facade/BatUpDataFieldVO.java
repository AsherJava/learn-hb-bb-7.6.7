/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.fasterxml.jackson.annotation.Nulls
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  io.swagger.annotations.ApiModel
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import io.swagger.annotations.ApiModel;
import java.util.List;

@ApiModel(value="\u6279\u91cf\u66f4\u65b0\u6307\u6807\u6216\u5b57\u6bb5")
public class BatUpDataFieldVO {
    private List<String> keys;
    private String desc;
    private DataFieldType dataFieldType;
    private Integer decimal;
    private Integer precision;
    private String defaultValue;
    private Boolean nullable;
    private String refDataEntityKey;
    private Boolean allowMultipleSelect;
    private Boolean allowUndefinedCode;
    private Integer dimension;
    private String measureUnit;
    private DataFieldGatherType dataFieldGatherType;
    private Boolean useAuthority;
    private DataFieldApplyType applyType;
    private FormatVO formatVO;
    private List<ValidationRuleVO> validationRules;
    private Boolean generateVersion;
    private Boolean changeWithPeriod;
    private Boolean allowTreeSum;
    private Boolean visible;
    private Boolean encrypted;
    private String formula;
    private String formulaDesc;
    private String dataMaskCode;

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

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
    }

    @JsonSetter(value="dataFieldType", nulls=Nulls.SKIP)
    public void setDataFieldType(int dataFieldType) {
        this.dataFieldType = DataFieldType.valueOf((int)dataFieldType);
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Integer getDimension() {
        return this.dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public DataFieldGatherType getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    @JsonSetter(value="dataFieldGatherType", nulls=Nulls.SKIP)
    public void setDataFieldGatherType(int dataFieldGatherType) {
        this.dataFieldGatherType = DataFieldGatherType.valueOf((int)dataFieldGatherType);
    }

    public void setDataFieldGatherType(DataFieldGatherType dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public Boolean getUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    public DataFieldApplyType getApplyType() {
        return this.applyType;
    }

    @JsonSetter(value="applyType", nulls=Nulls.SKIP)
    public void setApplyType(int applyType) {
        this.applyType = DataFieldApplyType.valueOf((int)applyType);
    }

    public void setApplyType(DataFieldApplyType applyType) {
        this.applyType = applyType;
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

    public String getDataMaskCode() {
        return this.dataMaskCode;
    }

    public void setDataMaskCode(String dataMaskCode) {
        this.dataMaskCode = dataMaskCode;
    }
}

