/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.QueryTable;

public interface IQuerySqlUpdater {
    public String updateQuerySql(QueryTable var1, String var2, String var3);

    public boolean supportSoftParse(QueryTable var1, String var2);
}

