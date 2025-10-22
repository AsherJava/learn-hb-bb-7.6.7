/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutor;

public interface DBQueryExecutorProvider {
    public DBQueryExecutor getDBQueryExecutor(String var1);
}

