/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.TreeRow
 *  com.jiuqi.va.query.template.enumerate.GatherTypeEnum
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.sql.transform;

import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.TreeRow;
import com.jiuqi.va.query.template.enumerate.GatherTypeEnum;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.util.QueryUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class TreeBuilder {
    private QueryParamVO queryParamVO;
    private List<TemplateFieldSettingVO> fields;
    private Map<String, TemplateFieldSettingVO> fieldMap;
    private static final String ORIGIN_DATA_MAP_KEY = "__ORIGIN_DATA_MAP__";

    public TreeBuilder(QueryParamVO queryParamVO) {
        this.queryParamVO = queryParamVO;
        this.fields = ((QueryFieldsPlugin)queryParamVO.getQueryTemplate().getPluginByClass(QueryFieldsPlugin.class)).getFields();
        this.fieldMap = this.fields.stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, field -> field));
    }

    public List<TreeRow> generateTree(List<Map<String, Object>> dataList) {
        List<String> groupByColumns = this.queryParamVO.getGroupFields().stream().map(QueryGroupField::getFieldName).collect(Collectors.toList());
        return this.buildTree(dataList, groupByColumns, 1, new LinkedHashMap<String, Object>());
    }

    public List<TreeRow> generateTree(List<Map<String, Object>> dataList, int level) {
        List<String> groupByColumns = this.queryParamVO.getGroupFields().stream().map(QueryGroupField::getFieldName).collect(Collectors.toList());
        LinkedHashMap<String, Object> parentGroupValues = new LinkedHashMap<String, Object>();
        for (int i = 0; i < level; ++i) {
            String fieldName = ((QueryGroupField)this.queryParamVO.getGroupFields().get(i)).getFieldName();
            parentGroupValues.put(fieldName, dataList.get(0).get(fieldName));
        }
        return this.buildTree(dataList, groupByColumns, 1, parentGroupValues);
    }

    private List<TreeRow> buildTree(List<Map<String, Object>> dataList, List<String> groupByColumns, int level, Map<String, Object> parentGroupValues) {
        if (level > groupByColumns.size()) {
            return Collections.emptyList();
        }
        String currentColumn = groupByColumns.get(level - 1);
        Map groupedData = dataList.stream().collect(Collectors.groupingBy(row -> {
            Map originData = (Map)row.get(ORIGIN_DATA_MAP_KEY);
            Object value = originData.get(currentColumn);
            return (value != null ? value : "").toString();
        }, LinkedHashMap::new, Collectors.toList()));
        return groupedData.entrySet().stream().map(entry -> this.createTreeRow((Map.Entry<String, List<Map<String, Object>>>)entry, groupByColumns, level, currentColumn, parentGroupValues)).collect(Collectors.toList());
    }

    private TreeRow createTreeRow(Map.Entry<String, List<Map<String, Object>>> entry, List<String> groupByColumns, int level, String currentColumn, Map<String, Object> parentGroupValues) {
        TreeRow treeRow = new TreeRow();
        LinkedHashMap<String, Object> rowValues = new LinkedHashMap<String, Object>();
        rowValues.putAll(parentGroupValues);
        Object currentGroupValue = entry.getValue().get(0).get(currentColumn);
        rowValues.put(currentColumn, currentGroupValue);
        parentGroupValues.put(currentColumn, currentGroupValue);
        HashMap originDataMap = new HashMap();
        originDataMap.put(currentColumn, ((Map)entry.getValue().get(0).get(ORIGIN_DATA_MAP_KEY)).get(currentColumn));
        rowValues.put(ORIGIN_DATA_MAP_KEY, originDataMap);
        treeRow.setRowValues(rowValues);
        this.processSummarizedRow(entry.getValue(), treeRow, currentColumn);
        if (level == groupByColumns.size()) {
            List children = entry.getValue().stream().map(row -> {
                TreeRow childRow = new TreeRow();
                childRow.setRowValues(row);
                return childRow;
            }).collect(Collectors.toList());
            treeRow.setChildren(children);
        } else {
            LinkedHashMap<String, Object> currentGroupValues = new LinkedHashMap<String, Object>(parentGroupValues);
            List<TreeRow> children = this.buildTree(entry.getValue(), groupByColumns, level + 1, currentGroupValues);
            treeRow.setChildren(children);
        }
        return treeRow;
    }

    private void processSummarizedRow(List<Map<String, Object>> children, TreeRow treeRow, String currentColumn) {
        for (TemplateFieldSettingVO field : this.fields) {
            Object fieldValue = treeRow.getRowValues().get(field.getName());
            if (field.getName().equalsIgnoreCase(currentColumn)) {
                treeRow.getRowValues().put(field.getName(), fieldValue);
                continue;
            }
            switch (GatherTypeEnum.getGatherTypeEnum((String)field.getGatherType())) {
                case AVG: {
                    fieldValue = this.calculateAverage(children, field);
                    break;
                }
                case SUM: {
                    fieldValue = this.calculateSum(children, field);
                    break;
                }
                case MAXIMUM: {
                    fieldValue = this.calculateMax(children, field);
                    break;
                }
                case MINIMUM: {
                    fieldValue = this.calculateMin(children, field);
                    break;
                }
            }
            treeRow.getRowValues().put(field.getName(), fieldValue);
        }
    }

    private Object calculateAverage(List<Map<String, Object>> children, TemplateFieldSettingVO field) {
        OptionalDouble average = children.stream().mapToDouble(row -> {
            Object value = ((Map)row.get(ORIGIN_DATA_MAP_KEY)).get(field.getName());
            return value != null ? Double.parseDouble(value.toString()) : 0.0;
        }).average();
        return average.isPresent() ? QueryUtils.getNumberValue(average.getAsDouble(), this.fieldMap.get(field.getName())) : null;
    }

    private Object calculateSum(List<Map<String, Object>> children, TemplateFieldSettingVO field) {
        double sum = children.stream().mapToDouble(row -> {
            Object value = ((Map)row.get(ORIGIN_DATA_MAP_KEY)).get(field.getName());
            return value != null ? Double.parseDouble(value.toString()) : 0.0;
        }).sum();
        return QueryUtils.getNumberValue(sum, this.fieldMap.get(field.getName()));
    }

    private Object calculateMax(List<Map<String, Object>> children, TemplateFieldSettingVO field) {
        OptionalDouble max = children.stream().mapToDouble(row -> {
            Object value = ((Map)row.get(ORIGIN_DATA_MAP_KEY)).get(field.getName());
            return value != null ? Double.parseDouble(value.toString()) : 0.0;
        }).max();
        return max.isPresent() ? QueryUtils.getNumberValue(max.getAsDouble(), this.fieldMap.get(field.getName())) : null;
    }

    private Object calculateMin(List<Map<String, Object>> children, TemplateFieldSettingVO field) {
        OptionalDouble min = children.stream().mapToDouble(row -> {
            Object value = ((Map)row.get(ORIGIN_DATA_MAP_KEY)).get(field.getName());
            return value != null ? Double.parseDouble(value.toString()) : 0.0;
        }).min();
        return min.isPresent() ? QueryUtils.getNumberValue(min.getAsDouble(), this.fieldMap.get(field.getName())) : null;
    }
}

