/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import java.util.List;

public interface GcCalcLogService {
    public GcCalcLogEO queryLatestCalcLogEO(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public GcCalcLogEO insertCalcLogEO(Long var1, GcCalcLogOperateEnum var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public Integer updateCalcLogEO(String var1, String var2, TaskStateEnum var3);

    public Integer updateCalcLog(String var1, TaskStateEnum var2);

    public Integer updateCalcLogEO(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7, TaskStateEnum var8, String var9);

    public List<GcCalcLogEO> queryLatestCalcLogEOs(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5);

    public void unLockTimeOutLogOperate(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, Long var7, String var8);

    public Integer updateLogToUnLatestState(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    public List<GcCalcLogEO> queryLatestCalcLogEOs(GcCalcLogOperateEnum var1, String var2, String var3, String var4);

    public GcCalcLogEO queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum var1, String var2, String var3, String var4, String var5, String var6);

    public void startCalcLog(GcCalcEnvContextImpl var1);
}

