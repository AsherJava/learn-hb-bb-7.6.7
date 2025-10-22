/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.formula;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;

public class ConsolidatedFormulaVO {
    private String id;
    private String systemId;
    private String code;
    private String formula;
    private Integer inputFlag;
    private Integer antoFlag;
    private Integer manualFlag;
    private List<String> ruleIds;
    private List<AbstractUnionRule> ruleBaseData;
    private String sortOrder;
    private Integer carryOver;

    public Integer getCarryOver() {
        return this.carryOver;
    }

    public void setCarryOver(Integer carryOver) {
        this.carryOver = carryOver;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getInputFlag() {
        return this.inputFlag;
    }

    public void setInputFlag(Integer inputFlag) {
        this.inputFlag = inputFlag;
    }

    public Integer getAntoFlag() {
        return this.antoFlag;
    }

    public void setAntoFlag(Integer antoFlag) {
        this.antoFlag = antoFlag;
    }

    public Integer getManualFlag() {
        return this.manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<AbstractUnionRule> getRuleBaseData() {
        return this.ruleBaseData;
    }

    public void setRuleBaseData(List<AbstractUnionRule> ruleBaseData) {
        this.ruleBaseData = ruleBaseData;
    }
}

