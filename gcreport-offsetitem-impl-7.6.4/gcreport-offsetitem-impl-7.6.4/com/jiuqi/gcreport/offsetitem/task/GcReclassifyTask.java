/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.List;

public interface GcReclassifyTask {
    public String name();

    public int doTask(GcActionParamsVO var1, List<String> var2, TaskLog var3);

    public boolean allow(GcActionParamsVO var1);

    public void queryReclassifyData(QueryParamsVO var1, LossGainOffsetVO var2);
}

