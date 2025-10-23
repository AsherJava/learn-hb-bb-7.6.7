/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.service;

import com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import java.sql.SQLException;
import java.util.List;

public interface IDynamicTempTableManageService {
    public boolean isPoolUseCache();

    public IDynamicTempTableManageService setCheckInterval(int var1) throws IllegalArgumentException;

    public IDynamicTempTableManageService setLeakDetectionThreshold(int var1) throws IllegalArgumentException;

    public List<String> getCreateTableDDLs(CreatTableRule var1) throws IllegalArgumentException, SQLException;

    public List<String> getCreateTableDDLs(List<CreatTableRule> var1) throws IllegalArgumentException, SQLException;

    public List<DynamicTempTableType> getAllDynamicTempTableTypes() throws SQLException;

    public List<DynamicTempTableInfo> getDynamicTempTables(ITableQueryInfo var1) throws SQLException;
}

