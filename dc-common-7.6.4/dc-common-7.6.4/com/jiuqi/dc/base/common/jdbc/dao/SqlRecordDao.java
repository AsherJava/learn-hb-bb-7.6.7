/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.dao;

import com.jiuqi.dc.base.common.jdbc.domain.SqlExecuteLogDO;
import java.util.Date;

public interface SqlRecordDao {
    public boolean existsSqlInfo(String var1);

    public int insertSqlInfo(String var1, String var2);

    public int insertSqlExecuteLog(SqlExecuteLogDO var1);

    public void recordEndTime(String var1, Date var2);
}

