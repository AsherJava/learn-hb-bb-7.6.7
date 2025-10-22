/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.LeaseFetchTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Map;

public class LeaseRuleDTO
extends AbstractUnionRule {
    private String billDefineId;
    private List<Item> debitItemList;
    private List<Item> creditItemList;

    @Override
    public String getRuleType() {
        return RuleTypeEnum.LEASE.getCode();
    }

    public String getBillDefineId() {
        return this.billDefineId;
    }

    public void setBillDefineId(String billDefineId) {
        this.billDefineId = billDefineId;
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

    public String toString() {
        return "LeaseRuleDTO{billDefineId='" + this.billDefineId + '\'' + ", debitItemList=" + this.debitItemList + ", creditItemList=" + this.creditItemList + '}';
    }

    public static class Item {
        private LeaseFetchTypeEnum type;
        private String subjectCode;
        private String fetchFormula;
        private Map<String, String> dimensions;
        private Integer sort;

        public LeaseFetchTypeEnum getType() {
            return this.type;
        }

        public void setType(LeaseFetchTypeEnum type) {
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

        public Map<String, String> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, String> dimensions) {
            this.dimensions = dimensions;
        }

        public Integer getSort() {
            return this.sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String toString() {
            return "Item{type=" + (Object)((Object)this.type) + ", subjectCode='" + this.subjectCode + '\'' + ", fetchFormula='" + this.fetchFormula + '\'' + ", dimensions=" + this.dimensions + ", sort=" + this.sort + '}';
        }
    }
}

