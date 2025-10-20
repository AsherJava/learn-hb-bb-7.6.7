/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.bizmodel.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BizModelAllPropsDTO {
    private String id;
    private String code;
    private String name;
    private String computationModelCode;
    private String computationModelName;
    private Integer stopFlag;
    private BigDecimal ordinal;
    private String baseDataDefine;
    private List<String> fetchFields;
    private String baseDataTableName;
    private String fetchFieldNames;
    private String fetchTable;
    private String fixedCondition;
    private List<CustomCondition> customConditions;
    private List<SelectField> selectFieldList;
    private String fetchFieldShow;
    private String customConditionShow;
    private Map<String, SelectField> selectFieldMap;
    private Map<String, CustomCondition> customConditionMap;
    private List<String> fetchTypes = new ArrayList<String>();
    private String fetchTypeNames;
    private List<Dimension> dimensions = new ArrayList<Dimension>();
    private String dimensionNames;
    private Map<String, String> dimensionMap;
    private Boolean selectAll;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComputationModelCode() {
        return this.computationModelCode;
    }

    public void setComputationModelCode(String computationModelCode) {
        this.computationModelCode = computationModelCode;
    }

    public String getComputationModelName() {
        return this.computationModelName;
    }

    public void setComputationModelName(String computationModelName) {
        this.computationModelName = computationModelName;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public List<String> getFetchFields() {
        return this.fetchFields;
    }

    public void setFetchFields(List<String> fetchFields) {
        this.fetchFields = fetchFields;
    }

    public String getBaseDataTableName() {
        return this.baseDataTableName;
    }

    public void setBaseDataTableName(String baseDataTableName) {
        this.baseDataTableName = baseDataTableName;
    }

    public String getFetchFieldNames() {
        return this.fetchFieldNames;
    }

    public void setFetchFieldNames(String fetchFieldNames) {
        this.fetchFieldNames = fetchFieldNames;
    }

    public String getFetchTable() {
        return this.fetchTable;
    }

    public void setFetchTable(String fetchTable) {
        this.fetchTable = fetchTable;
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
    }

    public List<SelectField> getSelectFieldList() {
        return this.selectFieldList;
    }

    public void setSelectFieldList(List<SelectField> selectFieldList) {
        this.selectFieldList = selectFieldList;
    }

    public String getFetchFieldShow() {
        return this.fetchFieldShow;
    }

    public void setFetchFieldShow(String fetchFieldShow) {
        this.fetchFieldShow = fetchFieldShow;
    }

    public String getCustomConditionShow() {
        return this.customConditionShow;
    }

    public void setCustomConditionShow(String customConditionShow) {
        this.customConditionShow = customConditionShow;
    }

    public Map<String, SelectField> getSelectFieldMap() {
        return this.selectFieldMap;
    }

    public void setSelectFieldMap(Map<String, SelectField> selectFieldMap) {
        this.selectFieldMap = selectFieldMap;
    }

    public Map<String, CustomCondition> getCustomConditionMap() {
        return this.customConditionMap;
    }

    public void setCustomConditionMap(Map<String, CustomCondition> customConditionMap) {
        this.customConditionMap = customConditionMap;
    }

    public List<String> getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(List<String> fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public String getFetchTypeNames() {
        return this.fetchTypeNames;
    }

    public void setFetchTypeNames(String fetchTypeNames) {
        this.fetchTypeNames = fetchTypeNames;
    }

    public List<Dimension> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public String getDimensionNames() {
        return this.dimensionNames;
    }

    public void setDimensionNames(String dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public Map<String, String> getDimensionMap() {
        return this.dimensionMap;
    }

    public void setDimensionMap(Map<String, String> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public Boolean getSelectAll() {
        return this.selectAll;
    }

    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }
}

