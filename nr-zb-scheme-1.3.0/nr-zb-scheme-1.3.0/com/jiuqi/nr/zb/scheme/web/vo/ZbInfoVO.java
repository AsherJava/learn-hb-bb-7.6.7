/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.zb.scheme.common.Dimension;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.web.vo.FormatVO;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ValidationRuleVO;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ZbInfoVO
implements Serializable {
    private Integer index;
    private Integer pageIndex;
    private String key;
    private String schemeKey;
    private String period;
    private String title;
    private String code;
    private String parentKey;
    private String desc;
    @JsonProperty(value="zbType")
    private ZbType type;
    @JsonProperty(value="dataFieldType")
    private ZbDataType dataType;
    @JsonProperty(value="dataFieldGatherType")
    private ZbGatherType gatherType;
    private String formula;
    private String formulaDesc;
    private String defaultValue;
    private Dimension dimension;
    private String measureUnit;
    @JsonProperty(value="refDataEntityKey")
    private String refEntityId;
    @JsonProperty(value="refDataEntityTitle")
    private String refEntityTitle;
    private Integer precision;
    private Integer decimal;
    private Boolean nullable;
    private ZbApplyType applyType;
    private Boolean allowUndefinedCode = false;
    private Boolean allowMultipleSelect = false;
    private Instant updateTime;
    private String level;
    private String order;
    private FormatVO formatVO;
    private List<ValidationRuleVO> validationRules;
    @JsonProperty(value="expandPropsObj")
    private Map<String, PropInfoVO> propData;
    private Boolean hasRefer = false;
    private Boolean useOld = false;
    private String versionKey;
    @JsonIgnore
    private List<PropInfoVO> propList;

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ZbType getType() {
        return this.type;
    }

    public void setType(ZbType type) {
        this.type = type;
    }

    public ZbDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(ZbDataType dataType) {
        this.dataType = dataType;
    }

    public ZbGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(ZbGatherType gatherType) {
        this.gatherType = gatherType;
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

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
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

    public Boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public ZbApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(ZbApplyType applyType) {
        this.applyType = applyType;
    }

    public Boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public Map<String, PropInfoVO> getPropData() {
        if (this.propData == null) {
            this.propData = new HashMap<String, PropInfoVO>();
        }
        return this.propData;
    }

    public void setPropData(Map<String, PropInfoVO> propData) {
        this.propData = propData;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Boolean isAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public Boolean isHasRefer() {
        return this.hasRefer;
    }

    public void setHasRefer(Boolean hasRefer) {
        this.hasRefer = hasRefer;
    }

    public Boolean isUseOld() {
        return this.useOld;
    }

    public void setUseOld(Boolean useOld) {
        this.useOld = useOld;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<PropInfoVO> getPropList() {
        return this.propList;
    }

    public void setPropList(List<PropInfoVO> propList) {
        this.propList = propList;
    }
}

