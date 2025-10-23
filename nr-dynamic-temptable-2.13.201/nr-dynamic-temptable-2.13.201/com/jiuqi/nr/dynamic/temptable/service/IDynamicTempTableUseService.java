/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.service;

import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException;
import java.sql.SQLException;

public interface IDynamicTempTableUseService {
    public DynamicTempTableMeta getTempTable(int var1, String var2) throws IllegalArgumentException, NoAvailableTempTableException, SQLException;

    public DynamicTempTableMeta getTempTable(int var1, boolean var2, String var3) throws IllegalArgumentException, NoAvailableTempTableException, SQLException;

    public DynamicTempTableMeta getTempTable(int var1, int var2, String var3) throws IllegalArgumentException, NoAvailableTempTableException, SQLException;

    public void releaseTempTable(String var1);
}

