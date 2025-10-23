/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.framework.pool;

import com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import java.sql.SQLException;
import java.util.List;

public interface IDynamicTempTablePool {
    public boolean isPoolUseCache();

    public void setCheckInterval(int var1);

    public void setLeakDetectionThreshold(int var1);

    public List<String> addTempTable(List<CreatTableRule> var1) throws SQLException;

    public List<DynamicTempTableType> getTempTableTypes() throws SQLException;

    public List<DynamicTempTableInfo> getTempTableInfo(ITableQueryInfo var1) throws SQLException;

    public DynamicTempTableMeta getAvailableTempTableMeta(int var1, int var2, String var3) throws NoAvailableTempTableException, SQLException;

    public void releaseTempTable(String var1);
}

