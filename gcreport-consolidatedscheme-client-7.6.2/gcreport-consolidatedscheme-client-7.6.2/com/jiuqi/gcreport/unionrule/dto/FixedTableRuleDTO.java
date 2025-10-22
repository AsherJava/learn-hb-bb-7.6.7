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
import com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FixedTableRuleDTO
extends AbstractUnionRule {
    private List<Item> debitItemList;
    private List<Item> creditItemList;

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
        return RuleTypeEnum.FIXED_TABLE.getCode();
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
        private FetchRangeEnum fetchRange;
        private String unit;
        private String subjectCode;
        private String fetchFormula;
        private Integer sort;
        private String fetchUnit;
        private String oppUnitId;
        private Map<String, String> dimensions;
        private String fixedTableRuleItemId;

        public FetchRangeEnum getFetchRange() {
            return this.fetchRange;
        }

        public void setFetchRange(FetchRangeEnum fetchRange) {
            this.fetchRange = fetchRange;
        }

        public String getUnit() {
            return this.unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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

        public String getFetchUnit() {
            return this.fetchUnit;
        }

        public void setFetchUnit(String fetchUnit) {
            this.fetchUnit = fetchUnit;
        }

        public Map<String, String> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, String> dimensions) {
            this.dimensions = dimensions;
        }

        public String getOppUnitId() {
            return this.oppUnitId;
        }

        public void setOppUnitId(String oppUnitId) {
            this.oppUnitId = oppUnitId;
        }

        public String getFixedTableRuleItemId() {
            return this.fixedTableRuleItemId;
        }

        public void setFixedTableRuleItemId(String fixedTableRuleItemId) {
            this.fixedTableRuleItemId = fixedTableRuleItemId;
        }
    }
}

