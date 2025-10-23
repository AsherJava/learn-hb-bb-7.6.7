/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.factory;

import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import com.jiuqi.nr.dynamic.temptable.service.impl.DynamicTempTableManageServiceImpl;
import com.jiuqi.nr.dynamic.temptable.service.impl.DynamicTempTableUseServiceImpl;

public class DynamicTempTableServiceFactory {
    public static IDynamicTempTableManageService getDynamicTempTableManageService(ConnectionManager connectionManager) {
        return DynamicTempTableManageServiceImpl.getInstance(false, connectionManager);
    }

    public static IDynamicTempTableManageService getDynamicTempTableManageService(boolean isPoolUseCache, int checkInterval, int leakDetectionThreshold, ConnectionManager connectionManager) {
        return DynamicTempTableManageServiceImpl.getInstance(isPoolUseCache, connectionManager);
    }

    public static IDynamicTempTableUseService getDynamicTempTableUseService(IDynamicTempTableManageService dynamicTempTableManageService, ConnectionManager connectionManager) {
        return DynamicTempTableUseServiceImpl.getInstance(dynamicTempTableManageService.isPoolUseCache(), connectionManager);
    }
}

