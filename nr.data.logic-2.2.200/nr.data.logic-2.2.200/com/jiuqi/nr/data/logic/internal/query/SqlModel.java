/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.data.logic.internal.query;

import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.internal.query.UnionModel;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlModel {
    private Date queryDate;
    private String batchId;
    private String mainTableName;
    private String joinTableName;
    private final List<String> mainColumns = new ArrayList<String>();
    private final List<String> joinColumns = new ArrayList<String>();
    private final Map<String, List<String>> dimFilters = new LinkedHashMap<String, List<String>>();
    private final Map<String, List<String>> columnFilters = new LinkedHashMap<String, List<String>>();
    private Map<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
    private QueryCondition queryCondition;
    private List<UnionModel> unionModels;
    private String filterCondition;
    private int pageStart = -1;
    private int pageEnd = -1;
    private final List<String> orderByColumns = new ArrayList<String>();
    private final Map<String, AggrType> groupByColumns = new LinkedHashMap<String, AggrType>();

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public String getJoinTableName() {
        return this.joinTableName;
    }

    public void setJoinTableName(String joinTableName) {
        this.joinTableName = joinTableName;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public List<String> getMainColumns() {
        return this.mainColumns;
    }

    public List<String> getJoinColumns() {
        return this.joinColumns;
    }

    public Map<String, List<String>> getColumnFilters() {
        return this.columnFilters;
    }

    public List<String> getOrderByColumns() {
        return this.orderByColumns;
    }

    public Map<String, AggrType> getGroupByColumns() {
        return this.groupByColumns;
    }

    public Map<Integer, Boolean> getCheckTypes() {
        return this.checkTypes;
    }

    public void setCheckTypes(Map<Integer, Boolean> checkTypes) {
        this.checkTypes = checkTypes;
    }

    public int getPageStart() {
        return this.pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return this.pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public Map<String, List<String>> getDimFilters() {
        return this.dimFilters;
    }

    public List<UnionModel> getUnionModels() {
        return this.unionModels;
    }

    public void setUnionModels(List<UnionModel> unionModels) {
        this.unionModels = unionModels;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Date getQueryDate() {
        return this.queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public QueryCondition getQueryCondition() {
        return this.queryCondition;
    }

    public void setQueryCondition(QueryCondition queryCondition) {
        this.queryCondition = queryCondition;
    }
}

