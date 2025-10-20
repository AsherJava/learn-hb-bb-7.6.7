/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.sql.transform;

import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DataTransform {
    public static Object groupListData(List<Map<String, Object>> databaseResult, List<QueryGroupField> groupFields, List<TemplateFieldSettingVO> fields) {
        HashMap<String, List<Map<String, Object>>> groupedData = new HashMap<String, List<Map<String, Object>>>();
        List<String> groupingColumns = groupFields.stream().map(QueryGroupField::getFieldName).collect(Collectors.toList());
        for (Map<String, Object> row : databaseResult) {
            String key = DataTransform.generateKey(row, groupingColumns);
            groupedData.computeIfAbsent(key, k -> new ArrayList()).add(row);
        }
        List<Map<String, Object>> list = DataTransform.buildTree(groupedData, groupingColumns, 0);
        return groupedData;
    }

    private static List<Map<String, Object>> buildTree(Map<String, List<Map<String, Object>>> groupedData, List<String> groupingColumns, int level) {
        if (level >= groupingColumns.size()) {
            return new ArrayList<Map<String, Object>>();
        }
        String currentColumn = groupingColumns.get(level);
        return groupedData.entrySet().stream().map(entry -> {
            HashMap<String, Object> node = new HashMap<String, Object>();
            node.put(currentColumn, entry.getKey());
            Map<String, List<Map<String, Object>>> listMap = ((List)entry.getValue()).stream().collect(Collectors.groupingBy(row -> DataTransform.generateKey(row, groupingColumns.subList(level + 1, groupingColumns.size()))));
            List<Map<String, Object>> children = DataTransform.buildTree(listMap, groupingColumns, level + 1);
            if (!children.isEmpty()) {
                node.put("children", children);
            }
            return node;
        }).collect(Collectors.toList());
    }

    private static String generateKey(Map<String, Object> row, List<String> groupingColumns) {
        return groupingColumns.stream().map(row::get).map(Object::toString).collect(Collectors.joining("|"));
    }
}

