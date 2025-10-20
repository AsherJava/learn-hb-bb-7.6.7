/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class FlexibleRuleDTO
extends AbstractUnionRule {
    private List<String> offsetGroupingField;
    private Boolean realTimeOffsetFlag = false;
    private Boolean reconciliationOffsetFlag = false;
    private Boolean oneToOneOffsetFlag = false;
    private Boolean unilateralOffsetFlag = false;
    private Boolean proportionOffsetDiffFlag = false;
    private Boolean generatePHSFlag = false;
    private Boolean checkOffsetFlag = false;
    private Boolean corporateOffsetFlag = false;
    private Boolean unCheckOffsetFlag = false;
    List<String> debitItemList;
    List<String> creditItemList;
    List<FlexibleFetchConfig> fetchConfigList;
    private String diffSubjectCode;
    private Set<String> srcDebitAllChildrenCodes;
    private Set<String> srcCreditAllChildrenCodes;

    @Override
    public String getRuleType() {
        String ruleType = super.getRuleType();
        return null == ruleType ? RuleTypeEnum.FLEXIBLE.getCode() : ruleType;
    }

    @Override
    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        this.debitItemList = CollectionUtils.isEmpty(this.debitItemList) ? Lists.newArrayList() : this.debitItemList;
        return this.debitItemList;
    }

    @Override
    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        this.creditItemList = CollectionUtils.isEmpty(this.creditItemList) ? Lists.newArrayList() : this.creditItemList;
        return this.creditItemList;
    }

    public List<String> getOffsetGroupingField() {
        return this.offsetGroupingField;
    }

    public void setOffsetGroupingField(List<String> offsetGroupingField) {
        this.offsetGroupingField = offsetGroupingField;
    }

    public Boolean getRealTimeOffsetFlag() {
        return this.realTimeOffsetFlag;
    }

    public void setRealTimeOffsetFlag(Boolean realTimeOffsetFlag) {
        this.realTimeOffsetFlag = realTimeOffsetFlag;
    }

    public Boolean getReconciliationOffsetFlag() {
        return this.reconciliationOffsetFlag;
    }

    public void setReconciliationOffsetFlag(Boolean reconciliationOffsetFlag) {
        this.reconciliationOffsetFlag = reconciliationOffsetFlag;
    }

    public Boolean getOneToOneOffsetFlag() {
        return this.oneToOneOffsetFlag;
    }

    public void setOneToOneOffsetFlag(Boolean oneToOneOffsetFlag) {
        this.oneToOneOffsetFlag = oneToOneOffsetFlag;
    }

    public Boolean getUnilateralOffsetFlag() {
        return this.unilateralOffsetFlag;
    }

    public void setUnilateralOffsetFlag(Boolean unilateralOffsetFlag) {
        this.unilateralOffsetFlag = unilateralOffsetFlag;
    }

    public Boolean getProportionOffsetDiffFlag() {
        return this.proportionOffsetDiffFlag;
    }

    public void setProportionOffsetDiffFlag(Boolean proportionOffsetDiffFlag) {
        this.proportionOffsetDiffFlag = proportionOffsetDiffFlag;
    }

    public Boolean getGeneratePHSFlag() {
        return this.generatePHSFlag;
    }

    public void setGeneratePHSFlag(Boolean generatePHSFlag) {
        this.generatePHSFlag = generatePHSFlag;
    }

    public Boolean getCorporateOffsetFlag() {
        return this.corporateOffsetFlag;
    }

    public void setCorporateOffsetFlag(Boolean corporateOffsetFlag) {
        this.corporateOffsetFlag = corporateOffsetFlag;
    }

    public List<String> getDebitItemList() {
        return this.debitItemList;
    }

    public void setDebitItemList(List<String> debitItemList) {
        this.debitItemList = debitItemList;
    }

    public List<String> getCreditItemList() {
        return this.creditItemList;
    }

    public void setCreditItemList(List<String> creditItemList) {
        this.creditItemList = creditItemList;
    }

    public List<FlexibleFetchConfig> getFetchConfigList() {
        return this.fetchConfigList;
    }

    public void setFetchConfigList(List<FlexibleFetchConfig> fetchConfigList) {
        this.fetchConfigList = fetchConfigList;
    }

    public String getDiffSubjectCode() {
        return this.diffSubjectCode;
    }

    public void setDiffSubjectCode(String diffSubjectCode) {
        this.diffSubjectCode = diffSubjectCode;
    }

    public Boolean getCheckOffsetFlag() {
        return this.checkOffsetFlag;
    }

    public void setCheckOffsetFlag(Boolean checkOffsetFlag) {
        this.checkOffsetFlag = checkOffsetFlag;
    }

    public Boolean getUnCheckOffsetFlag() {
        return this.unCheckOffsetFlag;
    }

    public void setUnCheckOffsetFlag(Boolean unCheckOffsetFlag) {
        this.unCheckOffsetFlag = unCheckOffsetFlag;
    }

    public Set<String> getSrcDebitAllChildrenCodes() {
        return this.srcDebitAllChildrenCodes;
    }

    public void setSrcDebitAllChildrenCodes(Set<String> srcDebitAllChildrenCodes) {
        this.srcDebitAllChildrenCodes = srcDebitAllChildrenCodes;
    }

    public Set<String> getSrcCreditAllChildrenCodes() {
        return this.srcCreditAllChildrenCodes;
    }

    public void setSrcCreditAllChildrenCodes(Set<String> srcCreditAllChildrenCodes) {
        this.srcCreditAllChildrenCodes = srcCreditAllChildrenCodes;
    }

    public static void main(String[] args) {
    }

    public String toString() {
        return JsonUtils.writeValueAsString((Object)this);
    }
}

