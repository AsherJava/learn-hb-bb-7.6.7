/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.samecontrol.action.impl;

import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcExtractReportDataAction
extends AbstractGcActionItem {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    protected GcExtractReportDataAction() {
        super("gcExtractReportData", "\u540c\u63a7\u6570\u636e\u63d0\u53d6", "\u63d0\u53d6\u53d8\u52a8\u5355\u4f4d\u62a5\u8868\u6570\u636e", "#icon-16_GJ_A_GC_zidongduizhang");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        String paramJson = String.valueOf(actionItemEnv.getActionParam());
        String asynTaskID = this.asyncTaskManager.publishTask((Object)paramJson, GcAsyncTaskPoolType.ASYNCTASK_SAMECTRL_EXTRACTREPORTINFO.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

