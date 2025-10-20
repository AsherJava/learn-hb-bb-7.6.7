/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FloatLineRuleDTO
extends AbstractUnionRule {
    private String fetchUnit;
    private String floatLineDataRegion;
    private Boolean proportionOffsetDiffFlag;
    List<Item> debitItemList;
    List<Item> creditItemList;

    @Override
    public String getRuleType() {
        return RuleTypeEnum.FLOAT_LINE.getCode();
    }

    @Override
    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        if (CollectionUtils.isEmpty(this.debitItemList)) {
            return Lists.newArrayList();
        }
        return this.debitItemList.stream().map(Item::getSubject).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        if (CollectionUtils.isEmpty(this.creditItemList)) {
            return Lists.newArrayList();
        }
        return this.creditItemList.stream().map(Item::getSubject).collect(Collectors.toList());
    }

    public String getFetchUnit() {
        return this.fetchUnit;
    }

    public void setFetchUnit(String fetchUnit) {
        this.fetchUnit = fetchUnit;
    }

    public String getFloatLineDataRegion() {
        return this.floatLineDataRegion;
    }

    public void setFloatLineDataRegion(String floatLineDataRegion) {
        this.floatLineDataRegion = floatLineDataRegion;
    }

    public Boolean getProportionOffsetDiffFlag() {
        return this.proportionOffsetDiffFlag;
    }

    public void setProportionOffsetDiffFlag(Boolean proportionOffsetDiffFlag) {
        this.proportionOffsetDiffFlag = proportionOffsetDiffFlag;
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
        private String unit;
        private String subject;
        private String amt;
        private String descriptionInfo;
        private Map<String, String> dimensions;

        public String getUnit() {
            return this.unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSubject() {
            return this.subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getAmt() {
            return this.amt;
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }

        public String getDescriptionInfo() {
            return this.descriptionInfo;
        }

        public void setDescriptionInfo(String descriptionInfo) {
            this.descriptionInfo = descriptionInfo;
        }

        public Map<String, String> getDimensions() {
            return this.dimensions;
        }

        public void setDimensions(Map<String, String> dimensions) {
            this.dimensions = dimensions;
        }
    }
}

