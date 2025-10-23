/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.factory;

import com.jiuqi.nr.dynamic.temptable.dao.impl.DynamicTempTableDaoImpl;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.framework.pool.IDynamicTempTablePool;
import com.jiuqi.nr.dynamic.temptable.framework.pool.impl.DynamicTempTableDBPool;

public class DynamicTempTablePoolFactory {
    public static IDynamicTempTablePool getDynamicTempTablePool(boolean isUseCache, ConnectionManager connectionManager) {
        if (isUseCache) {
            return null;
        }
        DynamicTempTableDaoImpl dynamicTempTableDao = new DynamicTempTableDaoImpl(connectionManager);
        return DynamicTempTableDBPool.getInstance(dynamicTempTableDao, connectionManager);
    }
}

