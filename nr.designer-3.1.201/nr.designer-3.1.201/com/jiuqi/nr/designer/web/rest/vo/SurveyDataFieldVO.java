/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.web.facade.FormatVO
 *  com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.util.List;

public class SurveyDataFieldVO {
    private String code;
    private String title;
    @ApiModelProperty(value="\u522b\u540d")
    private String alias;
    @ApiModelProperty(value="\u6240\u5c5e\u6570\u636e\u65b9\u6848KEY")
    private String dataSchemeKey;
    @ApiModelProperty(value="\u6240\u5c5e\u5b58\u50a8\u8868KEY")
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
    private int index;

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

    public DataFieldKind getDataFieldKind() {
        return this.dataFieldKind;
    }

    public void setDataFieldKind(DataFieldKind dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
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

    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
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

    public String getRefDataFieldKey() {
        return this.refDataFieldKey;
    }

    public void setRefDataFieldKey(String refDataFieldKey) {
        this.refDataFieldKey = refDataFieldKey;
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

    public void setDataFieldGatherType(DataFieldGatherType dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
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
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    public DataFieldApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(DataFieldApplyType applyType) {
        this.applyType = applyType;
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

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
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
}

