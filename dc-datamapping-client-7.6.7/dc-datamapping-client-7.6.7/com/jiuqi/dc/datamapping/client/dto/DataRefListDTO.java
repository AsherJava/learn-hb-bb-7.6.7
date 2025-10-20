/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.datamapping.client.enums.DataRefParamType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataRefListDTO
implements Serializable {
    private static final long serialVersionUID = 3792631938560715107L;
    private String dataSchemeCode;
    private List<String> dataSchemeCodeList;
    private String tableName;
    private String filterType;
    private String paramType = DataRefParamType.LIKE.name();
    private Map<String, String> filterParam = new HashMap<String, String>();
    private List<String> filterParamList;
    private Boolean includeUnRefCount;
    private Boolean customFlag;
    private Boolean pagination;
    private Integer pageNum;
    private Integer pageSize;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public List<String> getDataSchemeCodeList() {
        return this.dataSchemeCodeList;
    }

    public void setDataSchemeCodeList(List<String> dataSchemeCodeList) {
        this.dataSchemeCodeList = dataSchemeCodeList;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFilterType() {
        return this.filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public Map<String, String> getFilterParam() {
        return this.filterParam;
    }

    public void setFilterParam(Map<String, String> filterParam) {
        this.filterParam = filterParam;
    }

    public List<String> getFilterParamList() {
        return this.filterParamList;
    }

    public void setFilterParamList(List<String> filterParamSet) {
        this.filterParamList = filterParamSet;
    }

    public Boolean getIncludeUnRefCount() {
        return this.includeUnRefCount;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public Boolean isIncludeUnRefCount() {
        return this.includeUnRefCount;
    }

    public void setIncludeUnRefCount(Boolean includeUnRefCount) {
        this.includeUnRefCount = includeUnRefCount;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getCustomFlag() {
        return this.customFlag;
    }

    public void setCustomFlag(Boolean customFlag) {
        this.customFlag = customFlag;
    }
}

