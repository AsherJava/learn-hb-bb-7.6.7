/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datasource.database;

import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;

public interface QueryDataBaseHandler {
    public String getTypeName();

    public String getDescription();

    public String getProductName();

    public String getDriverName();

    public String getUrl(DataSourceInfoVO var1);

    public String getCreateTempTableSql();

    public String getPageSql(String var1, int var2, int var3);
}

