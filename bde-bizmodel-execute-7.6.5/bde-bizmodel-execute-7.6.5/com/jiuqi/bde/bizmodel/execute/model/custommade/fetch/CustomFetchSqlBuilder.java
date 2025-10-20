/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class CustomFetchSqlBuilder {
    private static final String CUSTOM_FETCH_TEMPLATE_SQL = "SELECT %1$S FROM %2$s MAINTABLE %3$s WHERE 1=1 %4$s %5$s";
    private Set<String> selectFieldList = new LinkedHashSet<String>();
    private String tableName;
    private Set<String> joinList = new HashSet<String>();
    private Set<String> conditionList = new HashSet<String>();
    private Set<String> groupFieldList = new HashSet<String>();
    private Boolean needGroup = false;

    public CustomFetchSqlBuilder() {
    }

    public CustomFetchSqlBuilder(Set<String> selectFieldList, String tableName, Set<String> conditionList) {
        this.selectFieldList = selectFieldList;
        this.tableName = tableName;
        this.conditionList = conditionList;
    }

    public CustomFetchSqlBuilder(Set<String> selectFieldList, String tableName, Set<String> joinList, Set<String> conditionList, Set<String> groupFieldList) {
        this.selectFieldList = selectFieldList;
        this.tableName = tableName;
        this.joinList = joinList;
        this.conditionList = conditionList;
        this.groupFieldList = groupFieldList;
    }

    public static CustomFetchSqlBuilder initSqlBuilderByRuleGroup(String ruleGroup, CustomBizModelDTO bizModel) {
        Map ruleGroupMap = (Map)JsonUtils.readValue((String)ruleGroup, (TypeReference)new TypeReference<Map<String, String>>(){});
        CustomFetchSqlBuilder customFetchSqlBuilder = new CustomFetchSqlBuilder();
        customFetchSqlBuilder.setTableName(bizModel.getFetchTable());
        if (!StringUtils.isEmpty((String)bizModel.getFixedCondition())) {
            customFetchSqlBuilder.addConditionSql(" AND " + bizModel.getFixedCondition());
        }
        customFetchSqlBuilder.setNeedGroup(!AggregateFuncEnum.ORIGINAL.getFuncCode().equals(ruleGroupMap.get("funcCode")));
        return customFetchSqlBuilder;
    }

    public void addSelectField(String selectField) {
        this.selectFieldList.add(selectField);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addJoinSql(String joinSql) {
        this.joinList.add(joinSql);
    }

    public void addConditionSql(String conditionSql) {
        this.conditionList.add(conditionSql);
    }

    public void addGroupFieldList(String groupField) {
        this.groupFieldList.add(groupField);
    }

    public String build() {
        return String.format(CUSTOM_FETCH_TEMPLATE_SQL, CollectionUtils.toString(new ArrayList<String>(this.selectFieldList)), this.tableName, CollectionUtils.toString(new ArrayList<String>(this.joinList), (String)" "), CollectionUtils.toString(new ArrayList<String>(this.conditionList), (String)" "), !CollectionUtils.isEmpty(new ArrayList<String>(this.groupFieldList)) && this.needGroup != false ? " GROUP BY " + CollectionUtils.toString(new ArrayList<String>(this.groupFieldList)) : "");
    }

    public void setNeedGroup(Boolean needGroup) {
        this.needGroup = needGroup;
    }
}

