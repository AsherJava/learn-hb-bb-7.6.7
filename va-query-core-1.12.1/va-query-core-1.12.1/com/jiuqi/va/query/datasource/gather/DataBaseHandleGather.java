/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 */
package com.jiuqi.va.query.datasource.gather;

import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import java.util.List;

public interface DataBaseHandleGather {
    public QueryDataBaseHandler getHandleServiceByTypeName(String var1);

    public QueryDataBaseHandler getHandleServiceByProductName(String var1);

    public List<QueryDataBaseHandler> getAllHandle();

    public String getTypeNameByProductName(String var1);
}

