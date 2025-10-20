/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.executor.BatchExecutorException
 *  org.springframework.dao.DuplicateKeyException
 */
package com.jiuqi.dc.base.common.utils;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.executor.BatchExecutorException;
import org.springframework.dao.DuplicateKeyException;

public class SqlUtil {
    private SqlUtil() {
        throw new IllegalStateException();
    }

    public static String concatCondi(List<String> list, boolean isAnd) {
        return SqlUtil.concatCondi(list, isAnd, true);
    }

    public static String concatCondiNoAnd(List<String> list, boolean isAnd) {
        return SqlUtil.concatCondi(list, isAnd, false);
    }

    private static String concatCondi(List<String> list, boolean isAnd, boolean includePreAnd) {
        if (list == null || list.size() == 0) {
            return includePreAnd ? " and 1 = 1 " : " 1=1 ";
        }
        int count = list.size();
        if (count == 1) {
            return includePreAnd ? " and " + list.get(0) : list.get(0);
        }
        StringBuilder result = new StringBuilder("");
        if (includePreAnd) {
            result.append(" and (");
        } else {
            result.append(" (");
        }
        for (int i = 0; i < count; ++i) {
            if (i > 0) {
                result.append(isAnd ? " and " : " or ");
            }
            result.append(list.get(i));
        }
        result.append(") \n");
        return result.toString();
    }

    public static boolean isSQLUniqueException(String dbType, Exception ee) {
        if (ee == null || ee.getMessage() == null) {
            return false;
        }
        if (ee instanceof DuplicateKeyException) {
            return true;
        }
        SQLException e = null;
        BatchExecutorException be = null;
        if (ee instanceof SQLException) {
            e = (SQLException)ee;
        } else if (ee.getCause() instanceof SQLException) {
            e = (SQLException)ee.getCause();
        } else if (ee instanceof BatchExecutorException) {
            be = (BatchExecutorException)ee.getCause();
        } else if (ee.getCause() instanceof BatchExecutorException) {
            be = (BatchExecutorException)ee.getCause();
        } else {
            return false;
        }
        if ("mysql".equalsIgnoreCase(dbType)) {
            if (e != null && (e.getErrorCode() == 1062 || e.getMessage().contains("Duplicate"))) {
                return true;
            }
            if (be != null && be.getMessage().contains("Duplicate")) {
                return true;
            }
        } else if ("oracle".equalsIgnoreCase(dbType)) {
            if (e != null && (e.getErrorCode() == 1 || e.getMessage().contains("ORA-00001"))) {
                return true;
            }
            if (be != null && be.getMessage().contains("ORA-00001")) {
                return true;
            }
        } else if ("sqlserver".equalsIgnoreCase(dbType)) {
            if (e != null && (e.getErrorCode() == 2601 || e.getErrorCode() == 2627)) {
                return true;
            }
            if (be != null && be.getMessage().contains("Duplicate")) {
                return true;
            }
        } else if ("dameng".equalsIgnoreCase(dbType)) {
            if (e != null && (e.getErrorCode() == -6602 || e.getMessage().contains("\u552f\u4e00\u6027\u7ea6\u675f"))) {
                return true;
            }
            if (be != null && be.getMessage().contains("\u552f\u4e00\u6027\u7ea6\u675f")) {
                return true;
            }
        } else if ("hana".equalsIgnoreCase(dbType)) {
            if (e != null && e.getMessage().contains("unique constraint violated")) {
                return true;
            }
            if (be != null && be.getMessage().contains("unique constraint violated")) {
                return true;
            }
        } else if ("kingbase".equalsIgnoreCase(dbType)) {
            if (e != null && (e.getErrorCode() == 0 || e.getMessage().contains("\u8fdd\u53cd\u552f\u4e00\u7ea6\u675f") || e.getMessage().contains("duplicate"))) {
                return true;
            }
            if (be != null && (be.getMessage().contains("duplicate") || be.getMessage().contains("\u8fdd\u53cd\u552f\u4e00\u7ea6\u675f"))) {
                return true;
            }
        }
        return false;
    }
}

