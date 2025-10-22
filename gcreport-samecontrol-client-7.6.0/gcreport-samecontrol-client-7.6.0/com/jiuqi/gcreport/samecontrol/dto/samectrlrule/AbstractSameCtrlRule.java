/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.Lists
 */
package com.jiuqi.gcreport.samecontrol.dto.samectrlrule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.enums.InvestmentUnitSameCtrlEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public abstract class AbstractSameCtrlRule
extends AbstractCommonRule {
    private List<Item> debitItemList;
    private List<Item> creditItemList;

    @Override
    public abstract SameCtrlRuleTypeEnum getRuleType();

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
        private String subjectCode;
        private String fetchFormula;
        private InvestmentUnitSameCtrlEnum investmentUnit;
        private Integer sort;

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

        public InvestmentUnitSameCtrlEnum getInvestmentUnit() {
            return this.investmentUnit;
        }

        public void setInvestmentUnit(InvestmentUnitSameCtrlEnum investmentUnit) {
            this.investmentUnit = investmentUnit;
        }

        public Integer getSort() {
            return this.sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }
}

