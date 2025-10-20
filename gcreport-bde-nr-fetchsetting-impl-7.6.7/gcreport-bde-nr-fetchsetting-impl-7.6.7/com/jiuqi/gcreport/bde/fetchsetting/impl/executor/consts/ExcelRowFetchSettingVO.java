/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.intf.Dimension
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.intf.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExcelRowFetchSettingVO
extends FixedFetchSourceRowSettingVO {
    private String logicFormula;
    private String wildcardFormula;
    private String fieldDefineCode;
    private String fieldDefineTitle;
    private String fetchSourceName;
    private String fetchTypeName;
    private String dimTypeName;
    private String sumTypeName;
    private String baseDataCode;
    private String customFetch;
    private String adaptFormula;
    private String description;
    private Integer rowNum;
    private String errorLog;
    private String fieldType;
    private String fieldValue;
    private String agingRangeTypeName;
    private Map<String, Dimension> dimSettingValueMap;
    private String agingGroupName;
    private String sheetName;

    public ExcelRowFetchSettingVO() {
        this.dimSettingValueMap = new HashMap<String, Dimension>();
    }

    public ExcelRowFetchSettingVO(FixedFetchSourceRowSettingVO fetchSourceRowSettingVO) {
        BeanUtils.copyProperties(fetchSourceRowSettingVO, (Object)this);
    }

    public ExcelRowFetchSettingVO(String fieldDefineCode, String fieldDefineTitle) {
        this.fieldDefineCode = fieldDefineCode;
        this.fieldDefineTitle = fieldDefineTitle;
        this.dimSettingValueMap = new HashMap<String, Dimension>();
    }

    public String getLogicFormula() {
        return this.logicFormula == null ? "" : this.logicFormula;
    }

    public void setLogicFormula(String logicFormula) {
        this.logicFormula = logicFormula;
    }

    public String getWildcardFormula() {
        return this.wildcardFormula;
    }

    public void setWildcardFormula(String wildcardFormula) {
        this.wildcardFormula = wildcardFormula;
    }

    public String getFieldDefineCode() {
        return this.fieldDefineCode;
    }

    public void setFieldDefineCode(String fieldDefineCode) {
        this.fieldDefineCode = fieldDefineCode;
    }

    public String getFieldDefineTitle() {
        return this.fieldDefineTitle;
    }

    public void setFieldDefineTitle(String fieldDefineTitle) {
        this.fieldDefineTitle = fieldDefineTitle;
    }

    public String getFetchSourceName() {
        return this.fetchSourceName;
    }

    public void setFetchSourceName(String fetchSourceName) {
        this.fetchSourceName = fetchSourceName;
    }

    public String getFetchTypeName() {
        return this.fetchTypeName;
    }

    public void setFetchTypeName(String fetchTypeName) {
        this.fetchTypeName = fetchTypeName;
    }

    public String getDimTypeName() {
        return this.dimTypeName;
    }

    public void setDimTypeName(String dimTypeName) {
        this.dimTypeName = dimTypeName;
    }

    public String getSumTypeName() {
        return this.sumTypeName;
    }

    public void setSumTypeName(String sumTypeName) {
        this.sumTypeName = sumTypeName;
    }

    public String getBaseDataCode() {
        return this.baseDataCode;
    }

    public void setBaseDataCode(String baseDataCode) {
        this.baseDataCode = baseDataCode;
    }

    public String getCustomFetch() {
        return this.customFetch;
    }

    public void setCustomFetch(String customFetch) {
        this.customFetch = customFetch;
    }

    public String getAdaptFormula() {
        return this.adaptFormula == null ? "" : this.adaptFormula;
    }

    public void setAdaptFormula(String adaptFormula) {
        this.adaptFormula = adaptFormula;
    }

    public Integer getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getAgingRangeTypeName() {
        return this.agingRangeTypeName;
    }

    public void setAgingRangeTypeName(String agingRangeTypeName) {
        this.agingRangeTypeName = agingRangeTypeName;
    }

    public void setDimSettingValue(List<Dimension> dimSettingValue) {
        this.dimSettingValueMap = dimSettingValue == null ? new HashMap<String, Dimension>() : dimSettingValue.stream().collect(Collectors.toMap(Dimension::getDimCode, Function.identity(), (K1, K2) -> K1));
    }

    public String getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public Map<String, Dimension> getDimSettingValueMap() {
        return this.dimSettingValueMap;
    }

    public void setDimSettingValueMap(Map<String, Dimension> dimSettingValueMap) {
        this.dimSettingValueMap = dimSettingValueMap;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getAgingGroupName() {
        return this.agingGroupName;
    }

    public void setAgingGroupName(String agingGroupName) {
        this.agingGroupName = agingGroupName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

