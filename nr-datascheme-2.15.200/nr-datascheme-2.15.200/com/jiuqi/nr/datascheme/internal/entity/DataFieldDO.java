/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
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
package com.jiuqi.nr.datascheme.internal.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_FIELD")
public class DataFieldDO
implements DataField {
    private static final long serialVersionUID = 4668689517971149559L;
    @DBAnno.DBField(dbField="DF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="DF_CODE")
    protected String code;
    @DBAnno.DBField(dbField="DF_ALIAS")
    protected String alias;
    @DBAnno.DBField(dbField="DF_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="DF_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="DF_DS_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="DF_DT_KEY")
    protected String dataTableKey;
    @DBAnno.DBField(dbField="DF_APPLY_TYPE", tranWith="transDataFieldApplyType", appType=DataFieldApplyType.class, dbType=Integer.class)
    protected DataFieldApplyType dataFieldApplyType;
    @DBAnno.DBField(dbField="DF_KIND", tranWith="transDataFieldKind", appType=DataFieldKind.class, dbType=Integer.class)
    protected DataFieldKind dataFieldKind;
    @DBAnno.DBField(dbField="DF_VERSION")
    protected String version;
    @DBAnno.DBField(dbField="DF_LEVEL")
    protected String level;
    @DBAnno.DBField(dbField="DF_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="DF_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="DF_DEFAULT")
    protected String defaultValue;
    @DBAnno.DBField(dbField="DF_PRECISION")
    protected Integer precision;
    @DBAnno.DBField(dbField="DF_DATATYPE", tranWith="transDataFieldType", appType=DataFieldType.class, dbType=int.class)
    protected DataFieldType dataFieldType;
    @DBAnno.DBField(dbField="DF_DECIMAL")
    protected Integer decimal;
    @DBAnno.DBField(dbField="DF_NULLABLE", get="getNullableTmp", set="setNullableTmp", dbType=Integer.class, appType=Boolean.class)
    protected Boolean nullable = true;
    @DBAnno.DBField(dbField="DF_REF_ENTITY_ID")
    protected String refDataEntityKey;
    @DBAnno.DBField(dbField="DF_REF_FIELD_ID")
    protected String refDataFieldKey;
    @DBAnno.DBField(dbField="DF_VALIDATION_RULE", get="getValidationRulesStr", set="setValidationRulesStr", dbType=String.class)
    protected List<ValidationRule> validationRules;
    @DBAnno.DBField(dbField="DF_MEASUREUNIT")
    protected String measureUnit;
    @DBAnno.DBField(dbField="DF_AGGRTYPE", tranWith="transDataFieldGatherType", appType=DataFieldGatherType.class, dbType=Integer.class)
    protected DataFieldGatherType dataFieldGatherType;
    @DBAnno.DBField(dbField="DF_MULTIVAL", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean allowMultipleSelect;
    @DBAnno.DBField(dbField="DF_ONLY_LEAF", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean onlyLeaf = false;
    @DBAnno.DBField(dbField="DF_SHOWFORMAT", get="getFormatPropertiesStr", set="setFormatPropertiesStr", dbType=String.class)
    protected FormatProperties formatProperties;
    @DBAnno.DBField(dbField="DF_SECRET_LEVEL")
    protected Integer secretLevel;
    @DBAnno.DBField(dbField="DF_USEAUTHORITY", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean useAuthority = false;
    @DBAnno.DBField(dbField="DF_ALLOW_UNDEFINED_CODE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean allowUndefinedCode;
    @DBAnno.DBField(dbField="DF_CHANGE_WITH_PERIOD", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean changeWithPeriod;
    @DBAnno.DBField(dbField="DF_GENERATE_VERSION", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean generateVersion;
    @DBAnno.DBField(dbField="DF_ALLOW_TREE_SUM", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean allowTreeSum;
    @DBAnno.DBField(dbField="DF_VISIBLE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean visible = true;
    @DBAnno.DBField(dbField="DF_ENCRYPTED", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean encrypted;
    @DBAnno.DBField(dbField="DF_ZB_TYPE", tranWith="transZbType", appType=ZbType.class, dbType=Integer.class)
    protected ZbType zbType = ZbType.GENERAL_ZB;
    @DBAnno.DBField(dbField="DF_ZB_VERSION")
    protected String versionKey;
    @DBAnno.DBField(dbField="DF_ZB_FORMULA")
    protected String formula;
    @DBAnno.DBField(dbField="DF_ZB_FORMULA_DESC")
    protected String formulaDesc;
    @DBAnno.DBField(dbField="DF_MASK_CODE")
    protected String dataMaskCode;
    @DBAnno.DBField(dbField="DF_RESTRICT_TYPE", tranWith="transRestrictType", appType=DataFieldRestrictType.class, dbType=Integer.class)
    protected DataFieldRestrictType restrictType;
    @DBAnno.DBField(dbField="DF_REF_PARAMETER")
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
            return new ArrayList<ValidationRule>();
        }
        return this.validationRules;
    }

    public void setValidationRules(List<ValidationRule> validationRules) {
        this.validationRules = Convert.setValidationRules(this.validationRules, validationRules);
    }

    public String getValidationRulesStr() throws JsonProcessingException {
        if (CollectionUtils.isEmpty(this.validationRules)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this.validationRules);
    }

    public void setValidationRulesStr(String validationRules) throws JsonProcessingException {
        if (!StringUtils.hasLength(validationRules)) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            List validationRuleDTOS = (List)mapper.readValue(validationRules, (TypeReference)new TypeReference<List<ValidationRuleDTO>>(){});
            this.validationRules = new ArrayList<ValidationRule>(validationRuleDTOS);
        }
        catch (Exception exception) {
            // empty catch block
        }
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

    public String getFormatPropertiesStr() throws JsonProcessingException {
        if (this.formatProperties == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString((Object)this.formatProperties);
    }

    public void setFormatPropertiesStr(String formatProperties) throws JsonProcessingException {
        if (!StringUtils.hasLength(formatProperties)) {
            return;
        }
        if (formatProperties.startsWith("@")) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.formatProperties = (FormatProperties)mapper.readValue(formatProperties, FormatProperties.class);
        }
        catch (Exception exception) {
            // empty catch block
        }
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

    public Integer getNullableTmp() {
        return 1;
    }

    public void setNullableTmp(Integer nullable) {
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getEncrypted() {
        return null != this.encrypted && this.encrypted != false;
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

    public DataFieldDO clone() {
        try {
            return (DataFieldDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataFieldDO that = (DataFieldDO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static DataFieldDO valueOf(DataField o) {
        if (o == null) {
            return null;
        }
        DataFieldDO t = new DataFieldDO();
        DataFieldDO.copyProperties(o, t);
        return t;
    }

    public static void copyProperties(DataField o, DataFieldDO t) {
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
        t.setEncrypted(o.isEncrypted());
        t.setZbType(o.getZbType());
        t.setZbSchemeVersion(o.getZbSchemeVersion());
        t.setFormula(o.getFormula());
        t.setFormulaDesc(o.getFormulaDesc());
        t.setOnlyLeaf(o.getOnlyLeaf());
        t.setDataMaskCode(o.getDataMaskCode());
        t.setRestrictType(o.getRestrictType());
        t.setRefParameter(o.getRefParameter());
    }

    public String toString() {
        return "DataFieldDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", alias='" + this.alias + '\'' + ", title='" + this.title + '\'' + ", order='" + this.order + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataTableKey='" + this.dataTableKey + '\'' + ", dataFieldApplyType=" + this.dataFieldApplyType + ", dataFieldKind=" + this.dataFieldKind + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", updateTime=" + this.updateTime + ", desc='" + this.desc + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", precision=" + this.precision + ", dataFieldType=" + this.dataFieldType + ", decimal=" + this.decimal + ", refDataEntityKey='" + this.refDataEntityKey + '\'' + ", refDataFieldKey='" + this.refDataFieldKey + '\'' + ", validationRules=" + this.validationRules + ", measureUnit='" + this.measureUnit + '\'' + ", dataFieldGatherType=" + this.dataFieldGatherType + ", allowMultipleSelect=" + this.allowMultipleSelect + ", onlyLeaf=" + this.onlyLeaf + ", formatProperties=" + this.formatProperties + ", secretLevel=" + this.secretLevel + ", useAuthority=" + this.useAuthority + ", allowUndefinedCode=" + this.allowUndefinedCode + ", changeWithPeriod=" + this.changeWithPeriod + ", generateVersion=" + this.generateVersion + ", allowTreeSum=" + this.allowTreeSum + '}';
    }
}

