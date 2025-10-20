/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.va.mapper.jdialect.Dialect
 */
package com.jiuqi.bde.bizmodel.define.adaptor.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.va.mapper.jdialect.Dialect;

public class DialectUtil {
    public static Dialect getDialect(String dbType) {
        Assert.isNotEmpty((String)dbType);
        if ("mysql".equalsIgnoreCase(dbType)) {
            return Dialect.MySQL57Dialect;
        }
        if ("oracle".equalsIgnoreCase(dbType)) {
            return Dialect.Oracle12cDialect;
        }
        if ("sqlserver".equalsIgnoreCase(dbType)) {
            return Dialect.SQLServer2012Dialect;
        }
        if ("postgresql".equalsIgnoreCase(dbType)) {
            return Dialect.PostgreSQLDialect;
        }
        if ("gauss".equalsIgnoreCase(dbType)) {
            return Dialect.PostgreSQLDialect;
        }
        if ("uxdb".equalsIgnoreCase(dbType)) {
            return Dialect.PostgreSQLDialect;
        }
        if ("polardb".equalsIgnoreCase(dbType)) {
            return Dialect.PolardbOracleDialect;
        }
        if ("dameng".equalsIgnoreCase(dbType)) {
            return Dialect.Oracle10gDialect;
        }
        if ("hana".equalsIgnoreCase(dbType)) {
            return Dialect.HANAColumnStoreDialect;
        }
        if ("kingbase".equalsIgnoreCase(dbType)) {
            return Dialect.KingbaseDialect;
        }
        if ("oscar".equals(dbType)) {
            return Dialect.OscarDialect;
        }
        throw new BusinessRuntimeException(String.format("\u6570\u636e\u5e93\u7c7b\u578b\u3010%1$s\u3011\u7684\u65b9\u8a00\u5c1a\u672a\u652f\u6301", dbType));
    }
}

