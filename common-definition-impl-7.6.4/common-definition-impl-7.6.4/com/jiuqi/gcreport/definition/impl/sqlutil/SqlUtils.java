/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.definition.impl.sqlutil;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public class SqlUtils {
    private static final int IN_MAXNUM = 500;
    public static final String SQL_QUERY_ITEM = "define query queryItem() \nbegin \n\tselect %s from %s as e \n %s \nend";

    public static TempTableCondition getConditionOfIds(Collection<String> ids, String fieldName, Relation relation) {
        return SqlUtils.getConditionOfIds(ids, fieldName, relation, false);
    }

    public static TempTableCondition getConditionOfIds(Collection<String> ids, String fieldName, Relation relation, boolean isDNASql) {
        String condition = SqlUtils.preGetConditionOfIds(ids, fieldName, relation);
        if (!StringUtils.isEmpty((String)condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = UUIDUtils.newUUIDStr();
        IdTemporaryTableUtils.insertTempDb(batchId, ids);
        String inFilter = Relation.POSITIVE == relation ? "in" : "not in";
        String tempTableAlias = isDNASql ? " as " : "";
        condition = fieldName + " " + inFilter + " (select idtemp.tbId as id from " + "GC_IDTEMPORARY" + tempTableAlias + " idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    public static TempTableCondition getNewConditionOfIds(Collection<String> ids, String fieldName) {
        return SqlUtils.getNewConditionOfIds(ids, fieldName, Relation.POSITIVE);
    }

    public static TempTableCondition getNewConditionOfIds(Collection<String> ids, String fieldName, Relation relation) {
        return SqlUtils.getNewConditionOfIds(ids, fieldName, relation, true);
    }

    public static TempTableCondition getNewConditionOfIds(Collection<String> ids, String fieldName, Relation relation, boolean isDNASql) {
        String condition = SqlUtils.preGetConditionOfIds(ids, fieldName, relation);
        if (!StringUtils.isEmpty((String)condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = UUIDUtils.newUUIDStr();
        IdTemporaryTableUtils.insertTempDb(batchId, ids);
        String inFilter = Relation.POSITIVE == relation ? "in" : "not in";
        String tempTableAlias = isDNASql ? "  " : "";
        condition = fieldName + " " + inFilter + " (select idtemp.tbId  id from " + "GC_IDTEMPORARY" + tempTableAlias + " idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    public static TempTableCondition getConditionOfIds(Collection<String> ids, String fieldName) {
        return SqlUtils.getConditionOfIds(ids, fieldName, Relation.POSITIVE);
    }

    public static TempTableCondition getNegetiveConditionOfIds(Collection<String> ids, String fieldName) {
        return SqlUtils.getConditionOfIds(ids, fieldName, Relation.NEGATIVE);
    }

    public static String getConditionOfIdsUseOr(Collection<String> ids, String fieldName) {
        return SqlUtils.getConditionOfIdsUseOr(ids, fieldName, Relation.POSITIVE);
    }

    public static String getConditionOfIdsUseOr(Collection<String> ids, String fieldName, Relation relation) {
        String condition = SqlUtils.preGetConditionOfIds(ids, fieldName, relation);
        if (!StringUtils.isEmpty((String)condition)) {
            return condition;
        }
        String beginStr = fieldName + " %sin (";
        beginStr = String.format(beginStr, Relation.NEGATIVE == relation ? "not " : "");
        StringBuilder result = new StringBuilder("(" + beginStr);
        int index = 0;
        for (String id : ids) {
            if (id == null) continue;
            result.append(String.format("'%s',", id.toString()));
            if (500 != ++index) continue;
            result.delete(result.length() - 1, result.length()).append(") ").append(Relation.NEGATIVE == relation ? "and" : "or").append(" ").append(beginStr).append(String.format("'%s',", id.toString()));
            index = 0;
        }
        result.delete(result.length() - 1, result.length()).append(")) \n");
        return result.toString();
    }

    private static String preGetConditionOfIds(Collection<String> ids, String fieldName, Relation relation) {
        if (org.springframework.util.StringUtils.isEmpty(fieldName) || org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return "1 = 2 \n";
        }
        if (ids.size() == 1) {
            return fieldName + (relation == Relation.NEGATIVE ? " <> '" : " = '") + ids.iterator().next() + "' \n";
        }
        if (ids.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(relation == Relation.NEGATIVE ? " not in (" : " in (");
            ids.forEach(id -> sql.append("'").append((String)id).append("',"));
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }

    public static TempTableCondition getConditionOfMulStr(Collection<String> mulStr, String fieldName) {
        return SqlUtils.getConditionOfMulStr(mulStr, fieldName, true);
    }

    public static TempTableCondition getConditionOfMulStr(Collection<String> mulStr, String fieldName, boolean isDNASql) {
        String condition = SqlUtils.preGetConditionOfMulStr(mulStr, fieldName);
        if (!StringUtils.isEmpty((String)condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = UUIDUtils.newUUIDStr();
        String tempTableAlias = isDNASql ? " as " : "";
        IdTemporaryTableUtils.insertTempStr(batchId, mulStr);
        condition = fieldName + " in (select idtemp.tbCode as code from " + "GC_IDTEMPORARY" + tempTableAlias + " idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    public static TempTableCondition getNewConditionOfMulStr(Collection<String> mulStr, String fieldName) {
        return SqlUtils.getNewConditionOfMulStr(mulStr, fieldName, true);
    }

    public static TempTableCondition getNewConditionOfMulStr(Collection<String> mulStr, String fieldName, boolean isDNASql) {
        String condition = SqlUtils.preGetConditionOfMulStr(mulStr, fieldName);
        if (!StringUtils.isEmpty((String)condition)) {
            return new TempTableCondition(condition, null);
        }
        String batchId = UUIDUtils.newUUIDStr();
        String tempTableAlias = isDNASql ? "  " : "";
        IdTemporaryTableUtils.insertTempStr(batchId, mulStr);
        condition = fieldName + " in (select idtemp.tbCode  code from " + "GC_IDTEMPORARY" + tempTableAlias + " idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
        return new TempTableCondition(condition, batchId);
    }

    public static String getConditionOfMulStrUseOr(Collection<String> mulStr, String fieldName) {
        String condition = SqlUtils.preGetConditionOfMulStr(mulStr, fieldName);
        if (!StringUtils.isEmpty((String)condition)) {
            return condition;
        }
        String beginStr = fieldName + " in (";
        StringBuilder result = new StringBuilder("(" + beginStr);
        int index = 0;
        for (String str : mulStr) {
            result.append(String.format("'%s',", str));
            if (500 != ++index) continue;
            result.delete(result.length() - 1, result.length()).append(") or ").append(beginStr).append(String.format("'%s',", str));
            index = 0;
        }
        result.delete(result.length() - 1, result.length()).append(")) \n");
        return result.toString();
    }

    private static String preGetConditionOfMulStr(Collection<String> mulStr, String fieldName) {
        if (StringUtils.isEmpty((String)fieldName) || org.springframework.util.CollectionUtils.isEmpty(mulStr)) {
            return "1 = 2 \n";
        }
        if (mulStr.size() == 1) {
            return fieldName + " = '" + mulStr.iterator().next() + "' \n";
        }
        if (mulStr.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(" in (");
            mulStr.forEach(str -> sql.append("'").append((String)str).append("',"));
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }

    public static String getConditionOfMulDouble(Collection<? extends Number> mulNum, String fieldName) {
        String condition = SqlUtils.preGetConditionOfMulDouble(mulNum, fieldName);
        if (!StringUtils.isEmpty((String)condition)) {
            return condition;
        }
        String batchId = UUIDUtils.newUUIDStr();
        IdTemporaryTableUtils.insertTempDouble(batchId, mulNum);
        return fieldName + " in (select idtemp.tbNum as num from " + "GC_IDTEMPORARY" + " as idtemp where idtemp.GROUP_ID = '" + batchId + "') \n";
    }

    private static String preGetConditionOfMulDouble(Collection<? extends Number> mulNum, String fieldName) {
        if (StringUtils.isEmpty((String)fieldName) || org.springframework.util.CollectionUtils.isEmpty(mulNum)) {
            return "1 = 2 \n";
        }
        if (mulNum.size() == 1) {
            return fieldName + " = " + mulNum.iterator().next() + " \n";
        }
        if (mulNum.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(" in (");
            mulNum.forEach(num -> sql.append(num).append(","));
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }

    public static String getColumnsSqlByEntity(Class<? extends DefaultTableEntity> entityClass, String alias) {
        alias = StringUtils.isEmpty((String)alias) ? entityClass.getName() : alias;
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine inputDataTable = entTableDefineProvider.getTableDefineByType(entityClass);
        StringBuilder columnBuilder = new StringBuilder();
        for (EntFieldDefine c : inputDataTable.getAllFields()) {
            columnBuilder.append("\n ").append(alias).append(".").append(c.getName()).append(" as ").append(c.getName()).append(",");
        }
        StringBuilder columns = columnBuilder.delete(columnBuilder.length() - 1, columnBuilder.length());
        return columns.toString();
    }

    public static String getColumnsSqlByEntity(Class<? extends DefaultTableEntity> entityClass) {
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine inputDataTable = entTableDefineProvider.getTableDefineByType(entityClass);
        StringBuilder columnBuilder = new StringBuilder();
        for (EntFieldDefine c : inputDataTable.getAllFields()) {
            columnBuilder.append("\n ").append(c.getName()).append(",");
        }
        StringBuilder columns = columnBuilder.delete(columnBuilder.length() - 1, columnBuilder.length());
        return columns.toString();
    }

    public static String getNewColumnsSqlByEntity(Class<? extends DefaultTableEntity> entityClass, String alias) {
        alias = StringUtils.isEmpty((String)alias) ? entityClass.getName() : alias;
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine inputDataTable = entTableDefineProvider.getTableDefineByType(entityClass);
        StringBuilder columnBuilder = new StringBuilder();
        for (EntFieldDefine c : inputDataTable.getAllFields()) {
            columnBuilder.append("\n ").append(alias).append(".").append(c.getName()).append(" ").append(c.getName()).append(",");
        }
        StringBuilder columns = columnBuilder.delete(columnBuilder.length() - 1, columnBuilder.length());
        return columns.toString();
    }

    public static String getColumnsSqlByEntity(Class<? extends DefaultTableEntity> entityClass, Collection<String> otherColumns, String alias) {
        alias = StringUtils.isEmpty((String)alias) ? entityClass.getName() : alias;
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine inputDataTable = entTableDefineProvider.getTableDefineByType(entityClass);
        StringBuilder columnBuilder = new StringBuilder();
        HashSet<String> queryFileds = new HashSet<String>();
        for (EntFieldDefine entFieldDefine : inputDataTable.getAllFields()) {
            queryFileds.add(entFieldDefine.getName());
            columnBuilder.append("\n ").append(alias).append(".").append(entFieldDefine.getName()).append(" as ").append(entFieldDefine.getName()).append(",");
        }
        if (otherColumns != null) {
            for (String string : otherColumns) {
                if (queryFileds.contains(string)) continue;
                columnBuilder.append("\n ").append(alias).append(".").append(string).append(" as ").append(string).append(",");
            }
        }
        StringBuilder columns = columnBuilder.delete(columnBuilder.length() - 1, columnBuilder.length());
        return columns.toString();
    }

    public static String getColumnsSqlByTableDefine(String tableName, String alias) {
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine tableDefine = entTableDefineProvider.getTableDefine(tableName);
        if (tableDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
        }
        StringBuilder buf = new StringBuilder();
        String tableAlias = StringUtils.isEmpty((String)alias) ? tableName : alias;
        for (EntFieldDefine c : tableDefine.getAllFields()) {
            buf.append("\n ").append(tableAlias).append(".").append(c.getName()).append(" as ").append(c.getName()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length());
        return buf.toString();
    }

    public static String getNewColumnsSqlByTableDefine(String tableName, String alias) {
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine tableDefine = entTableDefineProvider.getTableDefine(tableName);
        if (tableDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
        }
        StringBuilder buf = new StringBuilder();
        String tableAlias = StringUtils.isEmpty((String)alias) ? tableName : alias;
        for (EntFieldDefine c : tableDefine.getAllFields()) {
            buf.append("\n ").append(tableAlias).append(".").append(c.getName()).append(" ").append(c.getName()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length());
        return buf.toString();
    }

    public static String getColumnsSql(String[] columnNames, String alias) {
        if (CollectionUtils.isEmpty((Object[])columnNames)) {
            return "";
        }
        StringBuilder buf = new StringBuilder(128);
        for (String column : columnNames) {
            buf.append(alias).append(".").append(column).append(" as ").append(column).append(",");
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }

    public static String getNewColumnsSql(String[] columnNames, String alias) {
        if (CollectionUtils.isEmpty((Object[])columnNames)) {
            return "";
        }
        StringBuilder buf = new StringBuilder(128);
        for (String column : columnNames) {
            buf.append(alias).append(".").append(column).append(" as ").append(column).append(",");
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }

    public static String getConditionOfObject(Object value, String fieldName) {
        if (value instanceof String) {
            if ("".equals(value)) {
                return "(" + fieldName + "='' or " + fieldName + " is null )";
            }
            return fieldName + "='" + value + "'";
        }
        if (value instanceof Number) {
            return fieldName + "=" + value;
        }
        if (value instanceof UUID) {
            return fieldName + "='" + value + "'";
        }
        if (value instanceof Collection) {
            Collection values = (Collection)value;
            if (values.isEmpty()) {
                return null;
            }
            Object object = values.iterator().next();
            if (object instanceof UUID) {
                return SqlUtils.getConditionOfIdsUseOr(values, fieldName);
            }
            if (object instanceof String) {
                return SqlUtils.getConditionOfMulStrUseOr(values, fieldName);
            }
            return null;
        }
        return null;
    }

    public static Collection<String> getColumnNamesByTableDefine(String tableName) {
        HashSet<String> fieldNames = new HashSet<String>();
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        EntTableDefine tableDefine = entTableDefineProvider.getTableDefine(tableName);
        if (tableDefine == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8868\u540d\u4e3a\u201c" + tableName + "\u201d\u7684\u8868\u5b9a\u4e49\u3002");
        }
        for (EntFieldDefine c : tableDefine.getAllFields()) {
            fieldNames.add(c.getName());
        }
        return fieldNames;
    }

    public static String getWhereSql(String[] columnNamesInDb, Object[] values, String orderByStr, String alias) {
        StringBuilder conditionSql = new StringBuilder(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(" ").append(alias).append(".").append(SqlUtils.getConditionOfObject(values[i], columnNamesInDb[i])).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        if (!StringUtils.isEmpty((String)orderByStr)) {
            whereSql = whereSql + " order by " + orderByStr;
        }
        return whereSql;
    }

    public static String getConditionOfIdsUseByPlaceholder(Collection<String> ids, String fieldName) {
        return SqlUtils.getConditionOfIdsUseByPlaceholders(ids, fieldName, Relation.POSITIVE);
    }

    public static String getConditionOfIdsUseByPlaceholders(Collection<String> ids, String fieldName, Relation relation) {
        String condition = SqlUtils.preGetConditionOfIdsByPlaceholder(ids, fieldName, relation);
        if (!StringUtils.isEmpty((String)condition)) {
            return condition;
        }
        String beginStr = fieldName + " %sin (";
        beginStr = String.format(beginStr, Relation.NEGATIVE == relation ? "not " : "");
        StringBuilder result = new StringBuilder("(" + beginStr);
        int index = 0;
        for (String id : ids) {
            if (id == null) continue;
            result.append("?,");
            if (500 != ++index) continue;
            result.delete(result.length() - 1, result.length()).append(") ").append(Relation.NEGATIVE == relation ? "and" : "or").append(" ").append(beginStr).append("?,");
            index = 0;
        }
        result.delete(result.length() - 1, result.length()).append(")) \n");
        return result.toString();
    }

    private static String preGetConditionOfIdsByPlaceholder(Collection<String> ids, String fieldName, Relation relation) {
        if (org.springframework.util.StringUtils.isEmpty(fieldName) || org.springframework.util.CollectionUtils.isEmpty(ids)) {
            return "1 = 2 \n";
        }
        if (ids.size() == 1) {
            return fieldName + (relation == Relation.NEGATIVE ? " <> '" : " = ") + "? \n";
        }
        if (ids.size() < 500) {
            StringBuilder sql = new StringBuilder();
            sql.append(fieldName).append(relation == Relation.NEGATIVE ? " not in (" : " in (");
            sql.append(SqlUtils.generatePlaceholders(ids.size()));
            sql.append(") \n");
            return sql.toString();
        }
        return "";
    }

    private static String generatePlaceholders(int count) {
        return String.join((CharSequence)",", Collections.nCopies(count, "?"));
    }

    public static enum Relation {
        POSITIVE,
        NEGATIVE;

    }
}

