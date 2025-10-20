/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SqlBuildUtil
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.enums.FieldBatchAttribute;
import com.jiuqi.dc.base.common.exception.SQLExecuteRunetimeException;
import com.jiuqi.dc.base.common.intf.impl.FieldAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public final class SqlExecutorUtil {
    private static final String INSERT_SQL_FORMAT = "insert into %1$s (%2$s) values (%3$s) \n";
    private static final String UPDATE_SQL_FORMAT = "update %1$s set %2$s where 1 = 1 %3$s \n";
    private static final String SELECT_COUNT_SQL_FORMAT = "select count(1) from %1$s where 1 = 1 %2$s \n";
    private static final String SELECT_FIELDS_SQL_FORMAT = "select %2$s from %1$s where 1 = 1 %3$s \n";

    private SqlExecutorUtil() {
        throw new IllegalStateException();
    }

    public static int insert(Connection conn, String tableName, List<FieldAttribute> fieldAttributeList) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        Assert.isNotEmpty(fieldAttributeList, (String)"\u6267\u884cSQL\u53c2\u6570\u63d2\u5165\u5b57\u6bb5\u503c\u5217\u8868\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldSql = new StringBuilder();
        StringBuilder valueArgSql = new StringBuilder();
        for (FieldAttribute fieldAttribute : fieldAttributeList) {
            fieldSql.append(fieldAttribute.getFieldName()).append(", ");
            valueArgSql.append("?, ");
        }
        fieldSql.deleteCharAt(fieldSql.lastIndexOf(","));
        valueArgSql.deleteCharAt(valueArgSql.lastIndexOf(","));
        String insertSql = String.format(INSERT_SQL_FORMAT, tableName, fieldSql.toString(), valueArgSql.toString());
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(insertSql);
            int index = 1;
            for (FieldAttribute fieldAttribute : fieldAttributeList) {
                fieldAttribute.getFieldType().setArgumentValue(pstmt, index++, fieldAttribute.getFieldValue());
            }
            count = pstmt.executeUpdate();
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u901a\u7528\u6570\u636e\u63d2\u5165\u6267\u884c\u5931\u8d25\uff01", e);
            throw new SQLExecuteRunetimeException("\u901a\u7528\u6570\u636e\u63d2\u5165\u6267\u884c\u5931\u8d25\uff01", e, insertSql);
        }
        finally {
            SqlExecutorUtil.closePreparedStatement(pstmt);
        }
        return count;
    }

    public static void batchInsert(Connection conn, String tableName, List<FieldBatchAttribute> fieldBatchAttributeList, int threshold) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        Assert.isNotEmpty(fieldBatchAttributeList, (String)"\u6267\u884cSQL\u53c2\u6570\u63d2\u5165\u5b57\u6bb5\u503c\u5217\u8868\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldSql = new StringBuilder();
        StringBuilder valueArgSql = new StringBuilder();
        for (FieldBatchAttribute fieldBatchAttribute : fieldBatchAttributeList) {
            fieldSql.append(fieldBatchAttribute.getFieldName()).append(", ");
            valueArgSql.append("?, ");
        }
        fieldSql.deleteCharAt(fieldSql.lastIndexOf(","));
        valueArgSql.deleteCharAt(valueArgSql.lastIndexOf(","));
        String insertSql = String.format(INSERT_SQL_FORMAT, tableName, fieldSql.toString(), valueArgSql.toString());
        int count = fieldBatchAttributeList.get(0).getFieldValueList().size();
        int group = count % threshold == 0 ? count / threshold : count / threshold + 1;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(insertSql);
            for (int j = 0; j < group; ++j) {
                for (int i = j * threshold; i < count && i < threshold * (j + 1); ++i) {
                    int index = 1;
                    for (FieldBatchAttribute fieldBatchAttribute : fieldBatchAttributeList) {
                        fieldBatchAttribute.getFieldType().setArgumentValue(pstmt, index++, fieldBatchAttribute.getFieldValueList().get(i));
                    }
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u901a\u7528\u6570\u636e\u6279\u91cf\u63d2\u5165\u6267\u884c\u5931\u8d25\uff01", e);
            throw new SQLExecuteRunetimeException("\u901a\u7528\u6570\u636e\u6279\u91cf\u63d2\u5165\u6267\u884c\u5931\u8d25\uff01", e, insertSql);
        }
        finally {
            SqlExecutorUtil.closePreparedStatement(pstmt);
        }
    }

    public static int update(Connection conn, String tableName, List<FieldAttribute> setFieldAttributeList, List<FieldAttribute> whereFieldAttributeList) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        Assert.isNotEmpty(setFieldAttributeList, (String)"\u6267\u884cSQL\u53c2\u6570\u63d2\u5165\u5b57\u6bb5\u503c\u5217\u8868\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldValueSql = new StringBuilder();
        for (FieldAttribute fieldAttribute : setFieldAttributeList) {
            fieldValueSql.append(String.format(" %1$s = ?, ", fieldAttribute.getFieldName()));
        }
        fieldValueSql.deleteCharAt(fieldValueSql.lastIndexOf(","));
        StringBuilder condiArgSql = new StringBuilder();
        if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
            for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                condiArgSql.append(String.format(" and %1$s = ? ", fieldAttribute.getFieldName()));
            }
        }
        String string = String.format(UPDATE_SQL_FORMAT, tableName, fieldValueSql.toString(), condiArgSql.toString());
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(string);
            int index = 1;
            for (FieldAttribute fieldAttribute : setFieldAttributeList) {
                fieldAttribute.getFieldType().setArgumentValue(pstmt, index++, fieldAttribute.getFieldValue());
            }
            if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
                for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                    fieldAttribute.getFieldType().setArgumentValue(pstmt, index++, fieldAttribute.getFieldValue());
                }
            }
            count = pstmt.executeUpdate();
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u901a\u7528\u6570\u636e\u66f4\u65b0\u6267\u884c\u5931\u8d25\uff01", e);
            throw new SQLExecuteRunetimeException("\u901a\u7528\u6570\u636e\u66f4\u65b0\u6267\u884c\u5931\u8d25\uff01", e, string);
        }
        finally {
            SqlExecutorUtil.closePreparedStatement(pstmt);
        }
        return count;
    }

    public static int queryCount(Connection conn, String tableName, List<FieldAttribute> whereFieldAttributeList) {
        ResultSet rs;
        PreparedStatement pstmt;
        block8: {
            int n;
            Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
            StringBuilder condiArgSql = new StringBuilder();
            if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
                for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                    condiArgSql.append(String.format(" and %1$s = ? ", fieldAttribute.getFieldName()));
                }
            }
            String selectSql = String.format(SELECT_COUNT_SQL_FORMAT, tableName, condiArgSql.toString());
            pstmt = null;
            rs = null;
            try {
                pstmt = conn.prepareStatement(selectSql, 1004, 1007);
                int index = 1;
                if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
                    for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                        fieldAttribute.getFieldType().setArgumentValue(pstmt, index++, fieldAttribute.getFieldValue());
                    }
                }
                if (!(rs = pstmt.executeQuery()).first()) break block8;
                n = rs.getInt(1);
            }
            catch (SQLException e) {
                try {
                    LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u6570\u91cf\u6267\u884c\u5931\u8d25\uff01", e);
                    throw new SQLExecuteRunetimeException("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u6570\u91cf\u6267\u884c\u5931\u8d25\uff01", e, selectSql);
                }
                catch (Throwable throwable) {
                    SqlExecutorUtil.closeResultSet(rs);
                    SqlExecutorUtil.closePreparedStatement(pstmt);
                    throw throwable;
                }
            }
            SqlExecutorUtil.closeResultSet(rs);
            SqlExecutorUtil.closePreparedStatement(pstmt);
            return n;
        }
        SqlExecutorUtil.closeResultSet(rs);
        SqlExecutorUtil.closePreparedStatement(pstmt);
        return 0;
    }

    public static List<Map<String, Object>> queryResult(Connection conn, String tableName, List<FieldAttribute> selectFieldAttributeList, List<FieldAttribute> whereFieldAttributeList) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldValueSql = new StringBuilder();
        for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
            fieldValueSql.append(String.format(" %1$s, ", fieldAttribute.getFieldName()));
        }
        fieldValueSql.deleteCharAt(fieldValueSql.lastIndexOf(","));
        StringBuilder condiArgSql = new StringBuilder();
        if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
            for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                condiArgSql.append(String.format(" and %1$s = ? ", fieldAttribute.getFieldName()));
            }
        }
        String string = String.format(SELECT_FIELDS_SQL_FORMAT, tableName, fieldValueSql.toString(), condiArgSql.toString());
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            pstmt = conn.prepareStatement(string, 1004, 1007);
            int parameterIndex = 1;
            if (whereFieldAttributeList != null && whereFieldAttributeList.size() > 0) {
                for (FieldAttribute fieldAttribute : whereFieldAttributeList) {
                    fieldAttribute.getFieldType().setArgumentValue(pstmt, parameterIndex++, fieldAttribute.getFieldValue());
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int columnIndex = 1;
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
                    map.put(fieldAttribute.getFieldName(), fieldAttribute.getFieldType().getFieldValue(rs, columnIndex++));
                }
                resultList.add(map);
            }
        }
        catch (SQLException e) {
            try {
                LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u5217\u503c\u96c6\u5408\u6267\u884c\u5931\u8d25\uff01", e);
                throw new SQLExecuteRunetimeException("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u5217\u503c\u96c6\u5408\u6267\u884c\u5931\u8d25\uff01", e, string);
            }
            catch (Throwable throwable) {
                SqlExecutorUtil.closeResultSet(rs);
                SqlExecutorUtil.closePreparedStatement(pstmt);
                throw throwable;
            }
        }
        SqlExecutorUtil.closeResultSet(rs);
        SqlExecutorUtil.closePreparedStatement(pstmt);
        return resultList;
    }

    public static List<Map<String, Object>> queryResultByValues(Connection conn, String tableName, List<FieldAttribute> selectFieldAttributeList, FieldAttribute whereFieldAttribute, List<String> values) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldValueSql = new StringBuilder();
        for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
            fieldValueSql.append(String.format(" %1$s, ", fieldAttribute.getFieldName()));
        }
        fieldValueSql.deleteCharAt(fieldValueSql.lastIndexOf(","));
        StringBuilder condiArgSql = new StringBuilder();
        if (whereFieldAttribute != null) {
            condiArgSql.append(String.format(" and %1$s ", SqlBuildUtil.getStrInCondi((String)whereFieldAttribute.getFieldName(), values)));
        }
        String selectSql = String.format(SELECT_FIELDS_SQL_FORMAT, tableName, fieldValueSql, condiArgSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            pstmt = conn.prepareStatement(selectSql, 1003, 1007);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int columnIndex = 1;
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
                    map.put(fieldAttribute.getFieldName(), fieldAttribute.getFieldType().getFieldValue(rs, columnIndex++));
                }
                resultList.add(map);
            }
        }
        catch (SQLException e) {
            try {
                LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u6839\u636e\u503c\u96c6\u67e5\u8be2\u7ed3\u679c\u6267\u884c\u5931\u8d25\uff01", e);
                throw new SQLExecuteRunetimeException("\u6839\u636e\u503c\u96c6\u67e5\u8be2\u7ed3\u679c\u6267\u884c\u5931\u8d25\uff01", e, selectSql);
            }
            catch (Throwable throwable) {
                SqlExecutorUtil.closeResultSet(rs);
                SqlExecutorUtil.closePreparedStatement(pstmt);
                throw throwable;
            }
        }
        SqlExecutorUtil.closeResultSet(rs);
        SqlExecutorUtil.closePreparedStatement(pstmt);
        return resultList;
    }

    public static List<Map<String, Object>> queryResultByValues(Connection conn, String tableName, List<FieldAttribute> selectFieldAttributeList, List<FieldAttribute> whereFieldAttributes, List<List<Object>> values) {
        Assert.isNotEmpty((String)tableName, (String)"\u6267\u884cSQL\u53c2\u6570\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002", (Object[])new Object[0]);
        StringBuilder fieldValueSql = new StringBuilder();
        for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
            fieldValueSql.append(String.format(" %1$s, ", fieldAttribute.getFieldName()));
        }
        fieldValueSql.deleteCharAt(fieldValueSql.lastIndexOf(","));
        StringBuilder condiArgSql = new StringBuilder();
        if (whereFieldAttributes != null) {
            for (FieldAttribute whereFieldAttribute : whereFieldAttributes) {
                condiArgSql.append(String.format(" and %1$s = ? ", whereFieldAttribute.getFieldName()));
            }
        }
        String string = String.format(SELECT_FIELDS_SQL_FORMAT, tableName, fieldValueSql.toString(), condiArgSql.toString());
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            pstmt = conn.prepareStatement(string, 1003, 1007);
            for (List<Object> params : values) {
                for (int i = 0; i < params.size(); ++i) {
                    pstmt.setObject(i + 1, params.get(i));
                }
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int columnIndex = 1;
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    for (FieldAttribute fieldAttribute : selectFieldAttributeList) {
                        map.put(fieldAttribute.getFieldName(), fieldAttribute.getFieldType().getFieldValue(rs, columnIndex++));
                    }
                    resultList.add(map);
                }
            }
        }
        catch (SQLException e) {
            LoggerFactory.getLogger(SqlExecutorUtil.class).error("\u6839\u636e\u503c\u5217\u8868(List<List<Object>>)\u67e5\u8be2\u7ed3\u679c\u6267\u884c\u5931\u8d25\uff01", e);
            throw new SQLExecuteRunetimeException("\u6839\u636e\u503c\u5217\u8868(List<List<Object>>)\u67e5\u8be2\u7ed3\u679c\u6267\u884c\u5931\u8d25\uff01", e, string);
        }
        finally {
            SqlExecutorUtil.closeResultSet(rs);
            SqlExecutorUtil.closePreparedStatement(pstmt);
        }
        return resultList;
    }

    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            }
            catch (SQLException e) {
                LoggerFactory.getLogger(SqlExecutorUtil.class).error("PreparedStatement\u5173\u95ed\u5931\u8d25\uff01", e);
                pstmt = null;
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                LoggerFactory.getLogger(SqlExecutorUtil.class).error("ResultSet\u5173\u95ed\u5931\u8d25\uff01", e);
                rs = null;
            }
        }
    }
}

