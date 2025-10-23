/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.common.Dimension;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.web.vo.FormatVO;
import com.jiuqi.nr.zb.scheme.web.vo.PropInfoVO;
import com.jiuqi.nr.zb.scheme.web.vo.ValidationRuleVO;
import java.util.List;
import java.util.Map;

public class BatchUpdateZbInfoVO {
    private List<String> keys;
    private ZbType zbType;
    private String desc;
    private ZbDataType dataFieldType;
    private Integer decimal;
    private Integer precision;
    private String defaultValue;
    private Boolean nullable;
    private String refDataEntityKey;
    private Boolean allowMultipleSelect;
    private Boolean allowUndefinedCode;
    private ZbGatherType dataFieldGatherType;
    private Boolean useAuthority;
    private ZbApplyType applyType;
    private FormatVO formatVO;
    private List<ValidationRuleVO> validationRules;
    private Map<String, PropInfoVO> expandPropsObj;
    private Dimension dimension;
    private String measureUnit;
    private String formula;
    private String formulaDesc;

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public ZbType getZbType() {
        return this.zbType;
    }

    public void setZbType(ZbType zbType) {
        this.zbType = zbType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ZbDataType getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(ZbDataType dataFieldType) {
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

    public Boolean isNullable() {
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

    public Boolean isAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public Boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public ZbGatherType getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(ZbGatherType dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public Boolean isUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    public ZbApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(ZbApplyType applyType) {
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

    public Map<String, PropInfoVO> getExpandPropsObj() {
        return this.expandPropsObj;
    }

    public void setExpandPropsObj(Map<String, PropInfoVO> expandPropsObj) {
        this.expandPropsObj = expandPropsObj;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
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
}

