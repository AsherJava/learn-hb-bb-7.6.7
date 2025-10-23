/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_ZB_INFO")
public class ZbInfoDO
implements ZbInfo {
    @DBAnno.DBField(dbField="ZB_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZB_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="ZB_VERSION")
    private String versionKey;
    @DBAnno.DBField(dbField="ZB_TITLE")
    private String title;
    @DBAnno.DBField(dbField="ZB_CODE")
    private String code;
    @DBAnno.DBField(dbField="ZB_PARENT")
    private String parentKey;
    @DBAnno.DBField(dbField="ZB_DESC")
    private String desc;
    @DBAnno.DBField(dbField="ZB_TYPE", tranWith="transZbType", dbType=Integer.class, appType=ZbType.class)
    private ZbType type;
    @DBAnno.DBField(dbField="ZB_DATA_TYPE", tranWith="transZbDataType", dbType=Integer.class, appType=ZbDataType.class)
    private ZbDataType dataType;
    @DBAnno.DBField(dbField="ZB_GATHER_TYPE", tranWith="transZbGatherType", dbType=Integer.class, appType=ZbGatherType.class)
    private ZbGatherType gatherType;
    @DBAnno.DBField(dbField="ZB_FORMULA")
    private String formula;
    @DBAnno.DBField(dbField="ZB_FORMULA_DES")
    private String formulaDesc;
    @DBAnno.DBField(dbField="ZB_DEFAULT_VALUE")
    private String defaultValue;
    @DBAnno.DBField(dbField="ZB_MEASUREUNIT")
    private String measureUnit;
    @DBAnno.DBField(dbField="ZB_VALIDATION_RULE", get="getValidationRulesValue", set="setValidationRulesValue")
    private List<ValidationRule> validationRules;
    @DBAnno.DBField(dbField="ZB_SHOWFORMAT", get="getFormatPropertiesValue", set="setFormatPropertiesValue")
    private FormatProperties formatProperties;
    @DBAnno.DBField(dbField="ZB_REF_ENTITY_ID")
    private String refEntityId;
    @DBAnno.DBField(dbField="ZB_PRECISION")
    private Integer precision;
    @DBAnno.DBField(dbField="ZB_DECIMAL")
    private Integer decimal;
    @DBAnno.DBField(dbField="ZB_NULLABLE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean nullable = false;
    @DBAnno.DBField(dbField="ZB_APPLY_TYPE", tranWith="transZbApplyType", dbType=Integer.class, appType=ZbApplyType.class)
    private ZbApplyType applyType;
    @DBAnno.DBField(dbField="ZB_ALLOW_UNDEFINED_CODE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean allowUndefinedCode = false;
    @DBAnno.DBField(dbField="ZB_MULTIVAL", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean allowMultipleSelect;
    @DBAnno.DBField(dbField="ZB_UPDATE_TIME", tranWith="transTimeStampByInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="ZB_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ZB_LEVEL")
    private String level;
    private List<PropInfo> extProp;
    private Map<String, Object> extPropMap;

    @Override
    public List<PropInfo> getExtProp() {
        if (this.extProp == null) {
            this.extProp = new ArrayList<PropInfo>();
        }
        return this.extProp;
    }

    @Override
    public void setExtProp(List<PropInfo> extProp) {
        this.extProp = extProp;
    }

    @Override
    public ZbDataType getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(ZbDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public ZbType getType() {
        return this.type;
    }

    @Override
    public void setType(ZbType type) {
        this.type = type;
    }

    @Override
    public ZbGatherType getGatherType() {
        return this.gatherType;
    }

    @Override
    public void setGatherType(ZbGatherType gatherType) {
        this.gatherType = gatherType;
    }

    @Override
    public String getFormula() {
        return this.formula;
    }

    @Override
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String getFormulaDesc() {
        return this.formulaDesc;
    }

    @Override
    public void setFormulaDesc(String formulaDesc) {
        this.formulaDesc = formulaDesc;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public void setValidationRules(List<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    @Override
    public void setFormatProperties(FormatProperties format) {
        this.formatProperties = format;
    }

    @Override
    public List<ValidationRule> getValidationRules() {
        return this.validationRules;
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    @Override
    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    @Override
    public String getRefEntityId() {
        return this.refEntityId;
    }

    @Override
    public void setRefEntityId(String refEntityId) {
        this.refEntityId = refEntityId;
    }

    @Override
    public Integer getPrecision() {
        return this.precision;
    }

    @Override
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Override
    public Integer getDecimal() {
        return this.decimal;
    }

    @Override
    public Boolean getNullable() {
        return null;
    }

    @Override
    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    @Override
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public ZbApplyType getApplyType() {
        return this.applyType;
    }

    @Override
    public void setApplyType(ZbApplyType applyType) {
        this.applyType = applyType;
    }

    @Override
    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    @Override
    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    @Override
    public String getVersionKey() {
        return this.versionKey;
    }

    @Override
    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getFormatPropertiesValue() {
        if (this.formatProperties == null) {
            return null;
        }
        return JsonUtils.toJson(this.formatProperties);
    }

    public void setFormatPropertiesValue(String value) {
        if (StringUtils.hasLength(value)) {
            this.formatProperties = JsonUtils.fromJson(value, FormatProperties.class);
        }
    }

    public String getValidationRulesValue() {
        if (CollectionUtils.isEmpty(this.validationRules)) {
            return null;
        }
        return JsonUtils.toJson(this.validationRules);
    }

    public void setValidationRulesValue(String value) {
        List<ValidationRuleDTO> ruleDTOS;
        if (StringUtils.hasLength(value) && !CollectionUtils.isEmpty(ruleDTOS = JsonUtils.fromJson(value, new TypeReference<List<ValidationRuleDTO>>(){}))) {
            this.validationRules = new ArrayList<ValidationRuleDTO>(ruleDTOS);
        }
    }

    @Override
    public Map<String, Object> getExtPropMap() {
        if (this.extPropMap == null) {
            List<PropInfo> prop = this.getExtProp();
            if (CollectionUtils.isEmpty(prop)) {
                this.extPropMap = new LinkedHashMap<String, Object>();
                return this.extPropMap;
            }
            this.extPropMap = new LinkedHashMap<String, Object>(prop.size());
            for (PropInfo p : prop) {
                this.extPropMap.put(p.getFieldName(), p.getValue());
            }
            return this.extPropMap;
        }
        return this.extPropMap;
    }

    @Override
    public void setExtPropMap(Map<String, Object> extProp) {
        this.extPropMap = extProp;
    }
}

