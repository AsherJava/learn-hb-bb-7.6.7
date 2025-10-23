/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.service.impl;

import com.jiuqi.nr.dynamic.temptable.common.ArgumentCheckUtils;
import com.jiuqi.nr.dynamic.temptable.common.PropertiesUtils;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException;
import com.jiuqi.nr.dynamic.temptable.factory.DynamicTempTablePoolFactory;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.framework.pool.IDynamicTempTablePool;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import java.sql.SQLException;

public class DynamicTempTableUseServiceImpl
implements IDynamicTempTableUseService {
    private static DynamicTempTableUseServiceImpl instance;
    private IDynamicTempTablePool dynamicTempTablePool;

    public static synchronized IDynamicTempTableUseService getInstance(boolean isPoolUseCache, ConnectionManager connectionManager) {
        if (instance == null) {
            instance = new DynamicTempTableUseServiceImpl();
            DynamicTempTableUseServiceImpl.instance.dynamicTempTablePool = DynamicTempTablePoolFactory.getDynamicTempTablePool(isPoolUseCache, connectionManager);
        }
        return instance;
    }

    @Override
    public DynamicTempTableMeta getTempTable(int columnCount, String currentUseId) throws IllegalArgumentException, NoAvailableTempTableException, SQLException {
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(columnCount, "columnCount");
        return this.dynamicTempTablePool.getAvailableTempTableMeta(columnCount, columnCount, currentUseId);
    }

    @Override
    public DynamicTempTableMeta getTempTable(int columnCount, boolean allowMoreColumnTable, String currentUseId) throws IllegalArgumentException, NoAvailableTempTableException, SQLException {
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(columnCount, "columnCount");
        int maxColumnCount = PropertiesUtils.getIntPropertyValue("max-column-number");
        return this.dynamicTempTablePool.getAvailableTempTableMeta(columnCount, maxColumnCount, currentUseId);
    }

    @Override
    public DynamicTempTableMeta getTempTable(int columnCount, int acceptableMaxColumnCount, String currentUseId) throws IllegalArgumentException, NoAvailableTempTableException, SQLException {
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(columnCount, "columnCount");
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(acceptableMaxColumnCount, "acceptableMaxColumnCount");
        int startColumnCount = Math.min(columnCount, acceptableMaxColumnCount);
        int endColumnCount = Math.max(columnCount, acceptableMaxColumnCount);
        return this.dynamicTempTablePool.getAvailableTempTableMeta(startColumnCount, endColumnCount, currentUseId);
    }

    @Override
    public void releaseTempTable(String tempTableName) {
        if (this.dynamicTempTablePool != null) {
            this.dynamicTempTablePool.releaseTempTable(tempTableName);
        }
    }
}

