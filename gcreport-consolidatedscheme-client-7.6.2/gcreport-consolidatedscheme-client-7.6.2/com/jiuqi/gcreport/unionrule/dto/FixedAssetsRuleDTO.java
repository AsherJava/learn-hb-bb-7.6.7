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
import com.jiuqi.gcreport.unionrule.enums.PeriodTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FixedAssetsRuleDTO
extends AbstractUnionRule {
    private PeriodTypeEnum periodType;
    private Boolean scrappedFlag = false;
    List<Item> debitItemList;
    List<Item> creditItemList;

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
        return RuleTypeEnum.FIXED_ASSETS.getCode();
    }

    public PeriodTypeEnum getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodTypeEnum periodType) {
        this.periodType = periodType;
    }

    public Boolean getScrappedFlag() {
        return this.scrappedFlag;
    }

    public void setScrappedFlag(Boolean scrappedFlag) {
        this.scrappedFlag = scrappedFlag;
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
        private FixedAssetsTypeEnum type;
        private String subjectCode;
        private String fetchFormula;
        private Integer sort;
        private Map<String, String> dimensions;

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

