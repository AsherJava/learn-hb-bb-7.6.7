/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  javax.validation.constraints.Max
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Pattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelCheck;
import com.jiuqi.nr.zb.scheme.utils.Convert;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.util.CollectionUtils;

public class ZbInfoDTO
implements ZbInfo {
    @Size(max=40, message="{zs.text.size}")
    private @Size(max=40, message="{zs.text.size}") String key;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbSchemeKey.notNull}")
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbSchemeKey.notNull}") String schemeKey;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbVersionKey.notNull}")
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbVersionKey.notNull}") String versionKey;
    @NotBlank(message="{zs.title.notBlank}")
    @Size(min=1, max=200, message="{zs.title.size}")
    private @NotBlank(message="{zs.title.notBlank}") @Size(min=1, max=200, message="{zs.title.size}") String title;
    @Pattern(regexp="^[A-Za-z]\\w{0,49}", message="{zs.code.notReg}")
    @NotNull(message="{zs.code.notNull}")
    private @Pattern(regexp="^[A-Za-z]\\w{0,49}", message="{zs.code.notReg}") @NotNull(message="{zs.code.notNull}") String code;
    @Size(max=40, message="{zs.text.size}")
    @NotNull(message="{zs.zbParentKey.notNull}")
    @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.parentCode.notReg}", groups={ExcelCheck.class})
    private @Size(max=40, message="{zs.text.size}") @NotNull(message="{zs.zbParentKey.notNull}") @Pattern(regexp="^GROUP_(\\d{2})+$", message="{zs.parentCode.notReg}", groups={ExcelCheck.class}) String parentKey;
    @Size(max=1000, message="{zs.desc.size}")
    private @Size(max=1000, message="{zs.desc.size}") String desc;
    @NotNull(message="{zs.field.type.notNull}")
    private @NotNull(message="{zs.field.type.notNull}") ZbType type;
    @NotNull(message="{zs.field.dataType.notNull}")
    private @NotNull(message="{zs.field.dataType.notNull}") ZbDataType dataType;
    private ZbGatherType gatherType;
    @Size(max=2000, message="{zs.formula.size}")
    private @Size(max=2000, message="{zs.formula.size}") String formula;
    @Size(max=2000, message="{zs.formulaDesc.size}")
    private @Size(max=2000, message="{zs.formulaDesc.size}") String formulaDesc;
    @Size(max=500, message="{zs.field.defaultValue.size}")
    private @Size(max=500, message="{zs.field.defaultValue.size}") String defaultValue;
    @Size(max=100, message="{zs.text.size}")
    private @Size(max=100, message="{zs.text.size}") String measureUnit;
    @Size(max=40, message="{zs.text.size}")
    private @Size(max=40, message="{zs.text.size}") String refEntityId;
    private List<ValidationRule> validationRules;
    private FormatProperties format;
    @Min(value=0L, message="{zs.field.precision.min}")
    @Max(value=2000L, message="{zs.field.precision.max}")
    private @Min(value=0L, message="{zs.field.precision.min}") @Max(value=2000L, message="{zs.field.precision.max}") Integer precision;
    @Min(value=0L, message="{zs.field.decimal.min}")
    @Max(value=127L, message="{zs.field.decimal.max}")
    private @Min(value=0L, message="{zs.field.decimal.min}") @Max(value=127L, message="{zs.field.decimal.max}") Integer decimal;
    @Deprecated
    private Boolean nullable;
    private ZbApplyType applyType;
    private Boolean allowUndefinedCode;
    private Boolean allowMultipleSelect;
    private Instant updateTime;
    private String level;
    private String order;
    private List<PropInfo> extProp;

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
    public String getVersionKey() {
        return this.versionKey;
    }

    @Override
    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
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
    public ZbDataType getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(ZbDataType dataType) {
        this.dataType = dataType;
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
    public List<ValidationRule> getValidationRules() {
        if (CollectionUtils.isEmpty(this.validationRules)) {
            this.validationRules = new ArrayList<ValidationRule>();
        }
        return this.validationRules;
    }

    @Override
    public void setValidationRules(List<ValidationRule> validationRules) {
        this.validationRules = Convert.setValidationRules(this.validationRules, validationRules);
    }

    @Override
    public FormatProperties getFormatProperties() {
        return this.format;
    }

    @Override
    public void setFormatProperties(FormatProperties format) {
        this.format = format;
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
    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
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
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
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
    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    @Override
    public Boolean getNullable() {
        return Convert.isNullable(this.validationRules);
    }

    @Override
    public void setNullable(Boolean nullable) {
        this.validationRules = Convert.setNullable(nullable, this.validationRules);
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    @Override
    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    @Override
    public Map<String, Object> getExtPropMap() {
        List<PropInfo> prop = this.getExtProp();
        if (CollectionUtils.isEmpty(prop)) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>(prop.size());
        for (PropInfo p : prop) {
            result.put(p.getFieldName(), p.getValue());
        }
        return Collections.unmodifiableMap(result);
    }
}

