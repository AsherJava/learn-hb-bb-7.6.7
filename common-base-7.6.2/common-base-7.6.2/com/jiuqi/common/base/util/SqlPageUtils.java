/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.DBUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.UUID;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class SqlPageUtils {
    public static final String DATABSE_TYPE_MYSQL = "mysql";
    public static final String DATABSE_TYPE_POSTGRE = "postgresql";
    public static final String DATABSE_TYPE_ORACLE = "oracle";
    public static final String DATABSE_TYPE_SQLSERVER = "sqlserver";
    public static final String MYSQL_SQL = "select pagetable.* from ( {0}) pagetable limit {1},{2}";
    public static final String POSTGRE_SQL = "select pagetable.* from ( {0}) pagetable limit {2} offset {1}";
    public static final String ORACLE_SQL = "select pagetable.* from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1})  pagetable where rownum_>{2}";
    public static final String SQLSERVER_SQL = "select pagetable.* from ( select row_number() over(order by tempColumn) tempRowNumber, t.* from (select top {1} tempColumn = 0, {0}) t ) pagetable where pagetable.tempRowNumber > {2}";

    public static String createPageSql(String sql, int pageNum, int pageSize) {
        int beginNum = (pageNum - 1) * pageSize;
        Object[] sqlParam = new String[]{sql, Integer.toString(beginNum), Integer.toString(pageSize)};
        String dataSourceType = SqlPageUtils.getDataSourceType();
        if (dataSourceType.contains(DATABSE_TYPE_MYSQL)) {
            sql = MessageFormat.format(MYSQL_SQL, sqlParam);
        } else if (dataSourceType.contains(DATABSE_TYPE_POSTGRE)) {
            sql = MessageFormat.format(POSTGRE_SQL, sqlParam);
        } else {
            int beginIndex = (pageNum - 1) * pageSize;
            int endIndex = beginIndex + pageSize;
            sqlParam[2] = Integer.toString(beginIndex);
            sqlParam[1] = Integer.toString(endIndex);
            if (dataSourceType.contains(DATABSE_TYPE_ORACLE)) {
                sql = MessageFormat.format(ORACLE_SQL, sqlParam);
            } else if (dataSourceType.contains(DATABSE_TYPE_SQLSERVER)) {
                sqlParam[0] = sql.substring(SqlPageUtils.getAfterSelectInsertPoint(sql));
                sql = MessageFormat.format(SQLSERVER_SQL, sqlParam);
            }
        }
        return sql;
    }

    private static int getAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");
        int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
        return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }

    private static String getDataSourceType() {
        DBUtils dbUtils = SpringContextUtils.getBean(DBUtils.class);
        return dbUtils.getDBType();
    }

    public static String buildConditionOfIds(Collection<UUID> ids, String fieldName) {
        if (ObjectUtils.isEmpty(fieldName) || CollectionUtils.isEmpty(ids)) {
            return "1 = 2 \n";
        }
        if (ids.size() == 1) {
            return fieldName + " = '" + ids.iterator().next() + "' \n";
        }
        StringBuilder sql = new StringBuilder();
        sql.append(fieldName).append(" in (");
        ids.forEach(id -> sql.append("'").append(id).append("',"));
        sql.delete(sql.length() - 1, sql.length());
        sql.append(") \n");
        return sql.toString();
    }
}

