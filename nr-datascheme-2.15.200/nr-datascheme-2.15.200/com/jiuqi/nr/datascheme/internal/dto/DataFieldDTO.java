/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFieldDTO
implements DataField,
FieldDefine {
    private static final long serialVersionUID = 4668689517971149559L;
    private String key;
    private String code;
    private String alias;
    private String title;
    private String desc;
    private String dataSchemeKey;
    private String dataTableKey;
    private DataFieldKind dataFieldKind;
    private String level;
    private String order;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant updateTime;
    private int precision;
    private int decimal;
    private String defaultValue;
    private String measureUnit;
    private FormatProperties formatProperties;
    private List<ValidationRule> validationRules;
    private DataFieldType dataFieldType;
    private DataFieldApplyType dataFieldApplyType;
    private DataFieldGatherType dataFieldGatherType;
    private String refDataEntityKey;
    private boolean allowUndefinedCode;
    private boolean allowMultipleSelect;
    private boolean onlyLeaf = false;
    private boolean allowTreeSum;
    private boolean visible = true;
    private boolean encrypted;
    private boolean useAuthority = false;
    private boolean changeWithPeriod;
    private boolean generateVersion;
    private ZbType zbType;
    private String versionKey;
    private String formula;
    private String formulaDesc;
    private String dataMaskCode;
    private DataFieldRestrictType restrictType;
    private String refParameter;

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

    public String getVersion() {
        return null;
    }

    public void setDataFieldKind(DataFieldKind dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
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
        return this.precision == -1 ? null : Integer.valueOf(this.precision);
    }

    public void setPrecision(Integer precision) {
        this.precision = null == precision ? -1 : precision;
    }

    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public Integer getDecimal() {
        return this.decimal == -1 ? null : Integer.valueOf(this.decimal);
    }

    public void setDecimal(Integer decimal) {
        this.decimal = null == decimal ? -1 : decimal;
    }

    public Boolean getNullable() {
        return Convert.isNullable(this.validationRules);
    }

    public boolean isNullable() {
        return super.isNullable();
    }

    public void setNullable(Boolean nullable) {
        this.validationRules = Convert.setNullable(nullable, this.validationRules);
    }

    @JsonIgnore
    public String getRefDataFieldKey() {
        if (null == this.refDataEntityKey || this.refDataEntityKey.isEmpty()) {
            return null;
        }
        return DataSchemeUtils.getEntityBizKeys(this.refDataEntityKey);
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

    public boolean isAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = null != allowMultipleSelect && allowMultipleSelect != false;
    }

    public Boolean getOnlyLeaf() {
        return this.onlyLeaf;
    }

    public boolean isOnlyLeaf() {
        return this.onlyLeaf;
    }

    public void setOnlyLeaf(Boolean onlyLeaf) {
        this.onlyLeaf = null != onlyLeaf && onlyLeaf != false;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public Integer getSecretLevel() {
        return 0;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public Boolean getUseAuthority() {
        return this.useAuthority;
    }

    public boolean isUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = null != useAuthority && useAuthority != false;
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

    public boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = null != allowUndefinedCode && allowUndefinedCode != false;
    }

    public Boolean getChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public boolean isChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public void setChangeWithPeriod(Boolean changeWithPeriod) {
        this.changeWithPeriod = null != changeWithPeriod && changeWithPeriod != false;
    }

    public Boolean getGenerateVersion() {
        return this.generateVersion;
    }

    public boolean isGenerateVersion() {
        return super.isGenerateVersion();
    }

    public void setGenerateVersion(Boolean generateVersion) {
        this.generateVersion = null != generateVersion && generateVersion != false;
    }

    public Boolean getAllowTreeSum() {
        return this.allowTreeSum;
    }

    public boolean isAllowTreeSum() {
        return this.allowTreeSum;
    }

    public void setAllowTreeSum(Boolean allowTreeSum) {
        this.allowTreeSum = null != allowTreeSum && allowTreeSum != false;
    }

    @Deprecated
    @JsonIgnore
    public FieldValueType getValueType() {
        return super.getValueType();
    }

    @Deprecated
    @JsonIgnore
    public FieldGatherType getGatherType() {
        return super.getGatherType();
    }

    @Deprecated
    @JsonIgnore
    public String getEntityKey() {
        return super.getEntityKey();
    }

    @Deprecated
    @JsonIgnore
    public String getDescription() {
        return super.getDescription();
    }

    @Deprecated
    @JsonIgnore
    public Integer getFractionDigits() {
        return super.getFractionDigits();
    }

    @Deprecated
    @JsonIgnore
    public FieldType getType() {
        return super.getType();
    }

    @Deprecated
    @JsonIgnore
    public Integer getSize() {
        return super.getSize();
    }

    @Deprecated
    @JsonIgnore
    public String getOwnerTableKey() {
        return super.getOwnerTableKey();
    }

    @Deprecated
    @JsonIgnore
    public String getOwnerLevelAndId() {
        return super.getOwnerLevelAndId();
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = null != visible && visible != false;
    }

    public Boolean getEncrypted() {
        return this.encrypted;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = null != encrypted && encrypted != false;
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

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public DataFieldDTO clone() {
        try {
            return (DataFieldDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataFieldDTO that = (DataFieldDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public String toString() {
        return "DataFieldDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", alias='" + this.alias + '\'' + ", title='" + this.title + '\'' + ", order='" + this.order + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataTableKey='" + this.dataTableKey + '\'' + ", dataFieldApplyType=" + this.dataFieldApplyType + ", dataFieldKind=" + this.dataFieldKind + ", level='" + this.level + '\'' + ", updateTime=" + this.updateTime + ", desc='" + this.desc + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", precision=" + this.precision + ", dataFieldType=" + this.dataFieldType + ", decimal=" + this.decimal + ", refDataEntityKey='" + this.refDataEntityKey + '\'' + ", validationRules=" + this.validationRules + ", measureUnit='" + this.measureUnit + '\'' + ", dataFieldGatherType=" + this.dataFieldGatherType + ", allowMultipleSelect=" + this.allowMultipleSelect + ", onlyLeaf=" + this.onlyLeaf + ", formatProperties=" + this.formatProperties + ", useAuthority=" + this.useAuthority + ", allowUndefinedCode=" + this.allowUndefinedCode + ", changeWithPeriod=" + this.changeWithPeriod + ", generateVersion=" + this.generateVersion + ", allowTreeSum=" + this.allowTreeSum + '}';
    }

    public static DataFieldDTO valueOf(DataField dataField) {
        if (dataField == null) {
            return null;
        }
        DataFieldDTO dto = new DataFieldDTO();
        DataFieldDTO.copyProperties(dataField, dto);
        return dto;
    }

    public static void copyProperties(DataField o, DataFieldDTO t) {
        t.key = o.getKey();
        t.code = o.getCode();
        t.alias = o.getAlias();
        t.title = o.getTitle();
        t.desc = o.getDesc();
        t.dataSchemeKey = o.getDataSchemeKey();
        t.dataTableKey = o.getDataTableKey();
        t.dataFieldKind = o.getDataFieldKind();
        t.level = o.getLevel();
        t.order = o.getOrder();
        t.updateTime = o.getUpdateTime();
        t.precision = null == o.getPrecision() ? -1 : o.getPrecision();
        t.decimal = null == o.getDecimal() ? -1 : o.getDecimal();
        t.defaultValue = o.getDefaultValue();
        t.measureUnit = o.getMeasureUnit();
        t.formatProperties = o.getFormatProperties();
        t.validationRules = o.getValidationRules();
        t.dataFieldType = o.getDataFieldType();
        t.dataFieldApplyType = o.getDataFieldApplyType();
        t.dataFieldGatherType = o.getDataFieldGatherType();
        t.refDataEntityKey = o.getRefDataEntityKey();
        t.allowUndefinedCode = o.isAllowUndefinedCode();
        t.allowMultipleSelect = o.isAllowMultipleSelect();
        t.onlyLeaf = o.isOnlyLeaf();
        t.allowTreeSum = o.isAllowTreeSum();
        t.visible = o.isVisible();
        t.encrypted = o.isEncrypted();
        t.useAuthority = o.isUseAuthority();
        t.changeWithPeriod = o.isChangeWithPeriod();
        t.generateVersion = o.isGenerateVersion();
        t.zbType = o.getZbType();
        t.versionKey = o.getZbSchemeVersion();
        t.formula = o.getFormula();
        t.formulaDesc = o.getFormulaDesc();
        t.setDataMaskCode(o.getDataMaskCode());
        t.setRestrictType(o.getRestrictType());
        t.setRefParameter(o.getRefParameter());
    }
}

