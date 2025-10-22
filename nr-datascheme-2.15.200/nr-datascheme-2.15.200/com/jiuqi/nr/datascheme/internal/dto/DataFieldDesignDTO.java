/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 *  javax.validation.constraints.Max
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Pattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.util.CollectionUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFieldDesignDTO
implements DesignDataField {
    private static final long serialVersionUID = -9143372268655185552L;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String key;
    @Pattern(regexp="^[A-Za-z]\\w{0,49}", message="{code.notReg}")
    @NotNull(message="{code.notNull}")
    protected @Pattern(regexp="^[A-Za-z]\\w{0,49}", message="{code.notReg}") @NotNull(message="{code.notNull}") String code;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String alias;
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    protected @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @Size(max=10, message="{text.size}")
    protected @Size(max=10, message="{text.size}") String order;
    @Size(max=40, message="{text.size}")
    @NotNull(message="{dataSchemeKey.notNull}")
    protected @Size(max=40, message="{text.size}") @NotNull(message="{dataSchemeKey.notNull}") String dataSchemeKey;
    @Size(max=40, message="{text.size}")
    @NotNull(message="{field.dataTableKey.notNull}")
    protected @Size(max=40, message="{text.size}") @NotNull(message="{field.dataTableKey.notNull}") String dataTableKey;
    protected DataFieldApplyType dataFieldApplyType;
    @NotNull(message="{field.dataFieldKind.notNull}")
    protected @NotNull(message="{field.dataFieldKind.notNull}") DataFieldKind dataFieldKind;
    @Size(max=20, message="{text.size}")
    protected @Size(max=20, message="{text.size}") String version;
    @Size(max=5, message="{text.size}")
    protected @Size(max=5, message="{text.size}") String level;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    protected Instant updateTime;
    @Size(max=1000, message="{desc.size}")
    protected @Size(max=1000, message="{desc.size}") String desc;
    @Size(max=500, message="{field.defaultValue.size}")
    protected @Size(max=500, message="{field.defaultValue.size}") String defaultValue;
    @Min(value=0L, message="{field.precision.min}")
    @Max(value=2000L, message="{field.precision.max}")
    protected @Min(value=0L, message="{field.precision.min}") @Max(value=2000L, message="{field.precision.max}") Integer precision;
    @NotNull(message="{field.dataFieldType.notNull}")
    protected @NotNull(message="{field.dataFieldType.notNull}") DataFieldType dataFieldType;
    @Min(value=0L, message="{field.decimal.min}")
    @Max(value=127L, message="{field.decimal.max}")
    protected @Min(value=0L, message="{field.decimal.min}") @Max(value=127L, message="{field.decimal.max}") Integer decimal;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String refDataEntityKey;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String refDataFieldKey;
    protected List<ValidationRule> validationRules;
    @Size(max=100, message="{text.size}")
    protected @Size(max=100, message="{text.size}") String measureUnit;
    protected DataFieldGatherType dataFieldGatherType;
    protected Boolean allowMultipleSelect;
    protected Boolean onlyLeaf = false;
    protected FormatProperties formatProperties;
    protected Integer secretLevel;
    @NotNull(message="{field.useAuthority.notNull}")
    protected @NotNull(message="{field.useAuthority.notNull}") Boolean useAuthority = false;
    protected Boolean allowUndefinedCode;
    protected Boolean changeWithPeriod;
    protected Boolean generateVersion;
    protected Boolean allowTreeSum;
    protected Boolean visible = true;
    protected Boolean encrypted;
    protected ZbType zbType;
    protected String versionKey;
    protected String formula;
    protected String formulaDesc;
    protected String dataMaskCode;
    protected DataFieldRestrictType restrictType;
    protected String refParameter;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
        return Convert.isNullable(this.validationRules);
    }

    public void setNullable(Boolean nullable) {
        this.validationRules = Convert.setNullable(nullable, this.validationRules);
    }

    public String getRefDataFieldKey() {
        return this.refDataFieldKey;
    }

    public void setRefDataFieldKey(String refDataFieldKey) {
        this.refDataFieldKey = refDataFieldKey;
    }

    public List<ValidationRule> getValidationRules() {
        if (CollectionUtils.isEmpty(this.validationRules)) {
            this.validationRules = new ArrayList<ValidationRule>();
        }
        return this.validationRules;
    }

    public void setValidationRules(List<ValidationRule> validationRules) {
        this.validationRules = Convert.setValidationRules(this.validationRules, validationRules);
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

    public void setOnlyLeaf(Boolean onlyLeaf) {
        this.onlyLeaf = onlyLeaf;
    }

    public Boolean getOnlyLeaf() {
        return this.onlyLeaf;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
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

    public DataFieldApplyType getDataFieldApplyType() {
        return this.dataFieldApplyType;
    }

    public void setDataFieldApplyType(DataFieldApplyType dataFieldApplyType) {
        this.dataFieldApplyType = dataFieldApplyType;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Boolean getChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public void setChangeWithPeriod(Boolean changeWithPeriod) {
        this.changeWithPeriod = changeWithPeriod;
    }

    public Boolean getGenerateVersion() {
        return this.generateVersion;
    }

    public void setGenerateVersion(Boolean generateVersion) {
        this.generateVersion = generateVersion;
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

    public ZbType getZbType() {
        return this.zbType;
    }

    public void setZbType(ZbType zbType) {
        this.zbType = zbType;
    }

    public String getZbSchemeVersion() {
        return this.versionKey;
    }

    public void setZbSchemeVersion(String versionKey) {
        this.versionKey = versionKey;
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

    public String getDataMaskCode() {
        return this.dataMaskCode;
    }

    public void setDataMaskCode(String dataMaskCode) {
        this.dataMaskCode = dataMaskCode;
    }

    public DataFieldRestrictType getRestrictType() {
        return this.restrictType;
    }

    public void setRestrictType(DataFieldRestrictType restrictType) {
        this.restrictType = restrictType;
    }

    public String getRefParameter() {
        return this.refParameter;
    }

    public void setRefParameter(String refParameter) {
        this.refParameter = refParameter;
    }

    public DataFieldDesignDTO clone() {
        try {
            return (DataFieldDesignDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public String toString() {
        return "DataFieldDesignDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", alias='" + this.alias + '\'' + ", title='" + this.title + '\'' + ", order='" + this.order + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataTableKey='" + this.dataTableKey + '\'' + ", dataFieldApplyType=" + this.dataFieldApplyType + ", dataFieldKind=" + this.dataFieldKind + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", updateTime=" + this.updateTime + ", desc='" + this.desc + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", precision=" + this.precision + ", dataFieldType=" + this.dataFieldType + ", decimal=" + this.decimal + ", refDataEntityKey='" + this.refDataEntityKey + '\'' + ", refDataFieldKey='" + this.refDataFieldKey + '\'' + ", validationRules=" + this.validationRules + ", measureUnit='" + this.measureUnit + '\'' + ", dataFieldGatherType=" + this.dataFieldGatherType + ", allowMultipleSelect=" + this.allowMultipleSelect + ", onlyLeaf=" + this.onlyLeaf + ", formatProperties=" + this.formatProperties + ", secretLevel=" + this.secretLevel + ", useAuthority=" + this.useAuthority + ", allowUndefinedCode=" + this.allowUndefinedCode + ", allowTreeSum=" + this.allowTreeSum + '}';
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public static DataFieldDesignDTO valueOf(DataField dataField) {
        if (dataField == null) {
            return null;
        }
        DataFieldDesignDTO dto = new DataFieldDesignDTO();
        DataFieldDesignDTO.copyProperties(dataField, dto);
        return dto;
    }

    public static void copyProperties(DataField o, DataFieldDesignDTO t) {
        t.setKey(o.getKey());
        t.setCode(o.getCode());
        t.setAlias(o.getAlias());
        t.setTitle(o.getTitle());
        t.setOrder(o.getOrder());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setDataTableKey(o.getDataTableKey());
        t.setDataFieldApplyType(o.getDataFieldApplyType());
        t.setDataFieldKind(o.getDataFieldKind());
        t.setVersion(o.getVersion());
        t.setLevel(o.getLevel());
        t.setUpdateTime(o.getUpdateTime());
        t.setDesc(o.getDesc());
        t.setDefaultValue(o.getDefaultValue());
        t.setPrecision(o.getPrecision());
        t.setDataFieldType(o.getDataFieldType());
        t.setDecimal(o.getDecimal());
        t.setRefDataEntityKey(o.getRefDataEntityKey());
        t.setRefDataFieldKey(o.getRefDataFieldKey());
        t.setMeasureUnit(o.getMeasureUnit());
        t.setDataFieldGatherType(o.getDataFieldGatherType());
        t.setAllowMultipleSelect(o.getAllowMultipleSelect());
        t.setFormatProperties(o.getFormatProperties());
        t.setSecretLevel(o.getSecretLevel());
        t.setUseAuthority(o.getUseAuthority());
        t.setAllowUndefinedCode(o.getAllowUndefinedCode());
        t.setChangeWithPeriod(o.getChangeWithPeriod());
        t.setGenerateVersion(o.getGenerateVersion());
        t.setValidationRules(o.getValidationRules());
        t.setAllowTreeSum(o.getAllowTreeSum());
        t.setVisible(o.getVisible());
        t.setEncrypted(o.getEncrypted());
        t.setZbType(o.getZbType());
        t.setZbSchemeVersion(o.getZbSchemeVersion());
        t.setFormula(o.getFormula());
        t.setFormulaDesc(o.getFormulaDesc());
        t.setOnlyLeaf(o.getOnlyLeaf());
        t.setDataMaskCode(o.getDataMaskCode());
        t.setRestrictType(o.getRestrictType());
        t.setRefParameter(o.getRefParameter());
    }
}

