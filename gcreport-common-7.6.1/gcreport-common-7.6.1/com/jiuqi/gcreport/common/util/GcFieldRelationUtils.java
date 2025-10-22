/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GcFieldRelationUtils {
    private static Map<String, Map<String, String>> table2fieldName2dbFieldNameMap = new ConcurrentHashMap<String, Map<String, String>>(16);
    private static Map<String, Map<String, String>> table2dbFieldName2fieldNameMap = new ConcurrentHashMap<String, Map<String, String>>(16);

    public static String getDbFieldName(DefaultTableEntity entity, String fieldName) {
        String tableName = entity.getTableName();
        if (!table2fieldName2dbFieldNameMap.containsKey(tableName)) {
            GcFieldRelationUtils.load(entity);
        }
        return table2fieldName2dbFieldNameMap.get(tableName).getOrDefault(fieldName, fieldName);
    }

    public static String getFieldName(DefaultTableEntity entity, String dbFieldName, boolean isDefault) {
        String tableName = entity.getTableName();
        if (!table2dbFieldName2fieldNameMap.containsKey(tableName)) {
            GcFieldRelationUtils.load(entity);
        }
        if (isDefault) {
            return table2dbFieldName2fieldNameMap.get(tableName).getOrDefault(dbFieldName, dbFieldName);
        }
        return table2dbFieldName2fieldNameMap.get(tableName).get(dbFieldName);
    }

    private static synchronized void load(DefaultTableEntity entity) {
        Field[] fields;
        String tableName = entity.getTableName();
        if (table2dbFieldName2fieldNameMap.containsKey(tableName)) {
            return;
        }
        HashMap<String, String> fieldName2dbFieldNameMap = new HashMap<String, String>(16);
        HashMap<String, String> dbFieldName2fieldNameMap = new HashMap<String, String>(16);
        Class<?> c = entity.getClass();
        for (Field field : fields = c.getDeclaredFields()) {
            DBColumn fieldAnno;
            if (!field.isAnnotationPresent(DBColumn.class) || null == (fieldAnno = field.getAnnotation(DBColumn.class))) continue;
            String dbFieldName = fieldAnno.nameInDB().toUpperCase();
            String fieldName = field.getName();
            if (fieldName.equals(dbFieldName)) continue;
            fieldName2dbFieldNameMap.put(fieldName, dbFieldName);
            dbFieldName2fieldNameMap.put(dbFieldName, fieldName);
        }
        table2fieldName2dbFieldNameMap.put(entity.getTableName(), fieldName2dbFieldNameMap);
        table2dbFieldName2fieldNameMap.put(entity.getTableName(), dbFieldName2fieldNameMap);
    }
}

