/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.client.dto;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomBizModelDTO
extends BizModelDTO {
    private String fetchTable;
    private List<String> fetchFields;
    private String fixedCondition;
    private List<CustomCondition> customConditions;
    private List<SelectField> selectFieldList;
    private String fetchFieldNames;
    private List<String> fetchFieldShow;
    private List<String> customConditionShow;
    private Map<String, SelectField> selectFieldMap;
    private Map<String, CustomCondition> customConditionMap;
    private String applyScope;
    private String applyScopeShow;
    private String dataSourceCode;
    private String dataSourceCodeShow;
    private String queryTemplateId;
    private String queryTemplateShow;

    public String getFetchTable() {
        return this.fetchTable;
    }

    public void setFetchTable(String fetchTable) {
        this.fetchTable = fetchTable;
    }

    public List<String> getFetchFields() {
        return this.fetchFields;
    }

    public void setFetchFields(List<String> fetchFields) {
        this.fetchFields = fetchFields;
    }

    public String getFixedCondition() {
        return this.fixedCondition;
    }

    public void setFixedCondition(String fixedCondition) {
        this.fixedCondition = fixedCondition;
    }

    public List<CustomCondition> getCustomConditions() {
        return this.customConditions;
    }

    public void setCustomConditions(List<CustomCondition> customConditions) {
        this.customConditions = customConditions;
        this.customConditionMap = CollectionUtils.isEmpty(customConditions) ? new HashMap<String, CustomCondition>() : customConditions.stream().collect(Collectors.toMap(CustomCondition::getParamsCode, Function.identity(), (K1, K2) -> K1));
    }

    public List<SelectField> getSelectFieldList() {
        return this.selectFieldList;
    }

    public void setSelectFieldList(List<SelectField> selectFieldList) {
        this.selectFieldList = selectFieldList;
        this.selectFieldMap = CollectionUtils.isEmpty(selectFieldList) ? new HashMap<String, SelectField>() : selectFieldList.stream().collect(Collectors.toMap(SelectField::getFieldCode, Function.identity(), (K1, K2) -> K1));
    }

    public Map<String, SelectField> getSelectFieldMap() {
        return this.selectFieldMap;
    }

    public Map<String, CustomCondition> getCustomConditionMap() {
        return this.customConditionMap;
    }

    public String getFetchFieldNames() {
        return this.fetchFieldNames;
    }

    public void setFetchFieldNames(String fetchFieldNames) {
        this.fetchFieldNames = fetchFieldNames;
    }

    public List<String> getFetchFieldShow() {
        return this.fetchFieldShow;
    }

    public void setFetchFieldShow(List<String> fetchFieldShow) {
        this.fetchFieldShow = fetchFieldShow;
    }

    public List<String> getCustomConditionShow() {
        return this.customConditionShow;
    }

    public void setCustomConditionShow(List<String> customConditionShow) {
        this.customConditionShow = customConditionShow;
    }

    public String getApplyScope() {
        return this.applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public String getApplyScopeShow() {
        return this.applyScopeShow;
    }

    public void setApplyScopeShow(String applyScopeShow) {
        this.applyScopeShow = applyScopeShow;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getDataSourceCodeShow() {
        return this.dataSourceCodeShow;
    }

    public void setDataSourceCodeShow(String dataSourceCodeShow) {
        this.dataSourceCodeShow = dataSourceCodeShow;
    }

    public String getQueryTemplateId() {
        return this.queryTemplateId;
    }

    public void setQueryTemplateId(String queryTemplateId) {
        this.queryTemplateId = queryTemplateId;
    }

    public String getQueryTemplateShow() {
        return this.queryTemplateShow;
    }

    public void setQueryTemplateShow(String queryTemplateShow) {
        this.queryTemplateShow = queryTemplateShow;
    }

    public String toString() {
        return "CustomBizModelDTO [fetchTable=" + this.fetchTable + ", fetchFields=" + this.fetchFields + ", fixedCondition=" + this.fixedCondition + ", customConditions=" + this.customConditions + ", selectFieldList=" + this.selectFieldList + ", fetchFieldNames=" + this.fetchFieldNames + ", fetchFieldShow=" + this.fetchFieldShow + ", customConditionShow=" + this.customConditionShow + ", selectFieldMap=" + this.selectFieldMap + ", customConditionMap=" + this.customConditionMap + ", applyScope=" + this.applyScope + ", applyScopeShow=" + this.applyScopeShow + ", dataSourceCode=" + this.dataSourceCode + "]";
    }
}

