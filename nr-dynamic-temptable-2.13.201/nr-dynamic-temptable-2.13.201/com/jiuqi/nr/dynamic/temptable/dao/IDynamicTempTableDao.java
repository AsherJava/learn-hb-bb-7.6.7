/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.dao;

import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.domain.MonitorConfigInfo;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDynamicTempTableDao {
    public MonitorConfigInfo getMonitorConfig() throws SQLException;

    public int updateMonitorConfig(MonitorConfigInfo var1) throws SQLException;

    public Map<Integer, Integer> getMaxTableOrderNumber(Set<Integer> var1) throws SQLException;

    public List<DynamicTempTableType> getTempTableTypes() throws SQLException;

    public List<DynamicTempTableInfo> getTempTableInfos(ITableQueryInfo var1) throws SQLException;

    public DynamicTempTableInfo getAndAcquireTempTale(Set<Integer> var1, String var2) throws SQLException;

    public int truncateAndReleaseTempTable(String var1) throws SQLException;
}

