/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.Lists
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class PublicValueAdjustmentRuleDTO
extends AbstractUnionRule {
    private List<String> initialMerge;
    private List<String> goingConcern;
    private List<String> dealWith;
    List<Item> debitItemList;
    List<Item> creditItemList;

    public List<String> getInitialMerge() {
        return this.initialMerge;
    }

    public void setInitialMerge(List<String> initialMerge) {
        this.initialMerge = initialMerge;
    }

    public List<String> getGoingConcern() {
        return this.goingConcern;
    }

    public void setGoingConcern(List<String> goingConcern) {
        this.goingConcern = goingConcern;
    }

    public List<String> getDealWith() {
        return this.dealWith;
    }

    public void setDealWith(List<String> dealWith) {
        this.dealWith = dealWith;
    }

    @Override
    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        if (CollectionUtils.isEmpty(this.debitItemList)) {
            return Lists.newArrayList();
        }
        return this.debitItemList.stream().map(Item::getSubjectCode).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        if (CollectionUtils.isEmpty(this.creditItemList)) {
            return Lists.newArrayList();
        }
        return this.creditItemList.stream().map(Item::getSubjectCode).collect(Collectors.toList());
    }

    @Override
    public String getRuleType() {
        return RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode();
    }

    public List<Item> getDebitItemList() {
        return this.debitItemList;
    }

    public void setDebitItemList(List<Item> debitItemList) {
        this.debitItemList = debitItemList;
    }

    public List<Item> getCreditItemList() {
        return this.creditItemList;
    }

    public void setCreditItemList(List<Item> creditItemList) {
        this.creditItemList = creditItemList;
    }

    public static class Item {
        private String assetTypeCode;
        private FixedAssetsTypeEnum type;
        private String subjectCode;
        private String fetchFormula;
        private InvestmentUnitEnum investmentUnit;
        private Integer sort;
        private Map<String, String> dimensions;

        public InvestmentUnitEnum getInvestmentUnit() {
            return this.investmentUnit;
        }

        public void setInvestmentUnit(InvestmentUnitEnum investmentUnit) {
            this.investmentUnit = investmentUnit;
        }

        public String getAssetTypeCode() {
            return this.assetTypeCode;
        }

        public void setAssetTypeCode(String assetTypeCode) {
            this.assetTypeCode = assetTypeCode;
        }

        public FixedAssetsTypeEnum getType() {
            return this.type;
        }

        public void setType(FixedAssetsTypeEnum type) {
            this.type = type;
        }

        public String getSubjectCode() {
            return this.subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public String getFetchFormula() {
            return this.fetchFormula;
        }

        public void setFetchFormula(String fetchFormula) {
            this.fetchFormula = fetchFormula;
        }

        public Integer getSort() {
            return this.sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Map<String, String> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, String> dimensions) {
            this.dimensions = dimensions;
        }
    }
}

