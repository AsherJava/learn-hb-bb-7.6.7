/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.interceptor.QuerySqlInterceptor
 */
package com.jiuqi.va.query.common.service;

import com.jiuqi.va.query.sql.interceptor.QuerySqlInterceptor;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class QuerySqlInterceptorUtil {
    private QuerySqlInterceptorUtil() {
    }

    public static String getInterceptorSqlString(String sql) {
        try {
            QuerySqlInterceptor querySqlInterceptor = DCQuerySpringContextUtils.getBean(QuerySqlInterceptor.class);
            sql = querySqlInterceptor.intercept(sql);
        }
        catch (NoSuchBeanDefinitionException e) {
            return sql;
        }
        return sql;
    }
}

