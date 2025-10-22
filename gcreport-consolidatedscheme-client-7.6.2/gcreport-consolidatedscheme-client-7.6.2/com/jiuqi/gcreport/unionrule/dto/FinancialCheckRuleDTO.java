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
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class FinancialCheckRuleDTO
extends AbstractUnionRule {
    List<String> debitItemList;
    List<String> creditItemList;
    boolean checked;
    private Boolean reconciliationOffsetFlag;
    private Boolean unilateralOffsetFlag;
    private Boolean proportionOffsetDiffFlag;
    private Boolean delCheckedOffsetFlag;
    private List<String> offsetGroupingField;
    List<FinancialCheckFetchConfig> fetchConfigList;

    public List<FinancialCheckFetchConfig> getFetchConfigList() {
        return this.fetchConfigList;
    }

    public void setFetchConfigList(List<FinancialCheckFetchConfig> fetchConfigList) {
        this.fetchConfigList = fetchConfigList;
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

    public void setDebitItemList(List<String> debitItemList) {
        this.debitItemList = debitItemList;
    }

    public void setCreditItemList(List<String> creditItemList) {
        this.creditItemList = creditItemList;
    }

    public List<String> getDebitItemList() {
        return this.debitItemList;
    }

    public List<String> getCreditItemList() {
        return this.creditItemList;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Boolean getReconciliationOffsetFlag() {
        return this.reconciliationOffsetFlag;
    }

    public Boolean getUnilateralOffsetFlag() {
        return this.unilateralOffsetFlag;
    }

    public Boolean getProportionOffsetDiffFlag() {
        return this.proportionOffsetDiffFlag;
    }

    public void setReconciliationOffsetFlag(Boolean reconciliationOffsetFlag) {
        this.reconciliationOffsetFlag = reconciliationOffsetFlag;
    }

    public void setUnilateralOffsetFlag(Boolean unilateralOffsetFlag) {
        this.unilateralOffsetFlag = unilateralOffsetFlag;
    }

    public void setProportionOffsetDiffFlag(Boolean proportionOffsetDiffFlag) {
        this.proportionOffsetDiffFlag = proportionOffsetDiffFlag;
    }

    public List<String> getOffsetGroupingField() {
        return this.offsetGroupingField;
    }

    public void setOffsetGroupingField(List<String> offsetGroupingField) {
        this.offsetGroupingField = offsetGroupingField;
    }

    public String toString() {
        return JsonUtils.writeValueAsString((Object)this);
    }

    public Boolean getDelCheckedOffsetFlag() {
        return this.delCheckedOffsetFlag;
    }

    public void setDelCheckedOffsetFlag(Boolean delCheckedOffsetFlag) {
        this.delCheckedOffsetFlag = delCheckedOffsetFlag;
    }

    public FinancialCheckRuleDTO clone() {
        FinancialCheckRuleDTO result = new FinancialCheckRuleDTO();
        BeanUtils.copyProperties(this, result);
        result.setDebitItemList(this.copyList(this.getDebitItemList()));
        result.setCreditItemList(this.copyList(this.getCreditItemList()));
        result.setFetchConfigList(this.copyList(this.getFetchConfigList()));
        return result;
    }

    private List copyList(List src) {
        ArrayList result = new ArrayList();
        if (null != src) {
            result.addAll(src);
        }
        return result;
    }
}

