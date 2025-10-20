/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import com.jiuqi.va.query.sql.vo.QueryExpandField;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QuerySortVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import java.util.List;
import java.util.Map;

public class QueryParamVO {
    private Integer pageNum;
    private Integer pageSize;
    private List<QuerySortVO> sortFields;
    private List<QueryGroupField> groupFields;
    private List<QueryExpandField> expandFields;
    private Integer expandLevel;
    private Map<String, Object> params;
    private QueryTemplate queryTemplate;
    private String actionID;

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

    public List<QuerySortVO> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<QuerySortVO> sortFields) {
        this.sortFields = sortFields;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public QueryTemplate getQueryTemplate() {
        return this.queryTemplate;
    }

    public void setQueryTemplate(QueryTemplate queryTemplate) {
        this.queryTemplate = queryTemplate;
    }

    public List<QueryGroupField> getGroupFields() {
        return this.groupFields;
    }

    public void setGroupFields(List<QueryGroupField> groupFields) {
        this.groupFields = groupFields;
    }

    public List<QueryExpandField> getExpandFields() {
        return this.expandFields;
    }

    public void setExpandFields(List<QueryExpandField> expandFields) {
        this.expandFields = expandFields;
    }

    public String getActionID() {
        return this.actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public Integer getExpandLevel() {
        if (this.expandLevel == null) {
            return 0;
        }
        return this.expandLevel;
    }

    public void setExpandLevel(Integer expandLevel) {
        this.expandLevel = expandLevel;
    }
}

