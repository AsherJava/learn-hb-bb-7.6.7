/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.formula.dto.FormulaConditionDTO;
import com.jiuqi.nr.formula.dto.StatusDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FormulaDTO
extends StatusDTO {
    private String key;
    private String code;
    private String expression;
    private Integer checkType;
    private String order;
    private String description;
    private boolean useCalculate;
    private boolean useBalance;
    private boolean useCheck;
    private String formulaSchemeKey;
    private String formKey;
    private String level;
    private String balanceZBExp;
    private BigDecimal ordinal;
    private boolean isPrivate;
    private String unit;
    private List<FormulaConditionDTO> conditions;

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

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getCheckType() {
        return this.checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean getUseCalculate() {
        return this.useCalculate;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public boolean getUseBalance() {
        return this.useBalance;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    public boolean getUseCheck() {
        return this.useCheck;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPrivate() {
        return this.isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        this.isPrivate = aPrivate;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<FormulaConditionDTO> getConditions() {
        if (this.conditions == null) {
            this.conditions = new ArrayList<FormulaConditionDTO>();
        }
        return this.conditions;
    }

    public void setConditions(List<FormulaConditionDTO> conditions) {
        this.conditions = conditions;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

