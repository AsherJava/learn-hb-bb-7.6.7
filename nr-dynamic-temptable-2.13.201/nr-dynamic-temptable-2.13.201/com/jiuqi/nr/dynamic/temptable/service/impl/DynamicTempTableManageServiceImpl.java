/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.service.impl;

import com.jiuqi.nr.dynamic.temptable.common.ArgumentCheckUtils;
import com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.factory.DynamicTempTablePoolFactory;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.framework.pool.IDynamicTempTablePool;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DynamicTempTableManageServiceImpl
implements IDynamicTempTableManageService {
    private static DynamicTempTableManageServiceImpl instance;
    private IDynamicTempTablePool dynamicTempTablePool;

    public static synchronized IDynamicTempTableManageService getInstance(boolean isUseCache, ConnectionManager connectionManager) {
        if (instance == null) {
            instance = new DynamicTempTableManageServiceImpl();
            DynamicTempTableManageServiceImpl.instance.dynamicTempTablePool = DynamicTempTablePoolFactory.getDynamicTempTablePool(isUseCache, connectionManager);
        }
        return instance;
    }

    @Override
    public boolean isPoolUseCache() {
        return this.dynamicTempTablePool.isPoolUseCache();
    }

    @Override
    public IDynamicTempTableManageService setCheckInterval(int checkInterval) throws IllegalArgumentException {
        if (checkInterval <= 0) {
            throw new IllegalArgumentException("checkInterval must be greater than 0!");
        }
        this.dynamicTempTablePool.setCheckInterval(checkInterval);
        return this;
    }

    @Override
    public IDynamicTempTableManageService setLeakDetectionThreshold(int leakDetectionThreshold) throws IllegalArgumentException {
        if (leakDetectionThreshold <= 0) {
            throw new IllegalArgumentException("leakDetectionThreshold must be greater than 0!");
        }
        this.dynamicTempTablePool.setLeakDetectionThreshold(leakDetectionThreshold);
        return this;
    }

    @Override
    public List<String> getCreateTableDDLs(CreatTableRule creatTableRule) throws IllegalArgumentException, SQLException {
        return this.getCreateTableDDLs(Collections.singletonList(creatTableRule));
    }

    @Override
    public List<String> getCreateTableDDLs(List<CreatTableRule> creatTableRules) throws IllegalArgumentException, SQLException {
        creatTableRules.forEach(this::checkCreateTableRuleLegal);
        return this.dynamicTempTablePool.addTempTable(creatTableRules);
    }

    @Override
    public List<DynamicTempTableType> getAllDynamicTempTableTypes() throws SQLException {
        return this.dynamicTempTablePool.getTempTableTypes();
    }

    @Override
    public List<DynamicTempTableInfo> getDynamicTempTables(ITableQueryInfo queryInfo) throws SQLException {
        return this.dynamicTempTablePool.getTempTableInfo(queryInfo);
    }

    private void checkCreateTableRuleLegal(CreatTableRule creatTableRule) throws IllegalArgumentException {
        if (creatTableRule != null) {
            ArgumentCheckUtils.checkDynamicTempTableColumnCount(creatTableRule.getColumnCount(), "creatTableRule's columnCount");
            if (creatTableRule.getTableCount() < 1) {
                throw new IllegalArgumentException("creatTableRule's tableCount must have at least 1 table!");
            }
        } else {
            throw new IllegalArgumentException("creatTableRule must not be null!");
        }
    }
}

