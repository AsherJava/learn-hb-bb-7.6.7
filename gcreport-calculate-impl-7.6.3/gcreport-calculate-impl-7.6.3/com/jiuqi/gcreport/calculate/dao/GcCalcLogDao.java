/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 */
package com.jiuqi.gcreport.calculate.dao;

import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import java.util.List;

public interface GcCalcLogDao
extends IDbSqlGenericDAO<GcCalcLogEO, String> {
    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum var1, String var2, String var3, String var4, TaskStateEnum var5, String var6);

    public GcCalcLogEO queryLatestLogs(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public Long queryLockLogBeginTimeByDim(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public Integer unLockLogByDim(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public GcCalcLogEO queryLogById(String var1);

    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5);

    public List<GcCalcLogEO> queryLatestLogs(GcCalcLogOperateEnum var1, String var2, String var3, String var4);

    public Integer updateLogToUnLatestState(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public Integer updateCalcLog(String var1, String var2, TaskStateEnum var3);

    public Integer updateCalcLog(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7, TaskStateEnum var8, String var9);

    public Integer updateCalcLog(String var1, TaskStateEnum var2);

    public GcCalcLogEO queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum var1, List<String> var2, String var3, String var4, String var5, String var6);
}

