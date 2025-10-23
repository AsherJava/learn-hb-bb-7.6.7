/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn
 */
package com.jiuqi.nr.workflow2.schedule.bi.jobs.monitor;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import java.util.List;

public class ProcessStartupOperateResult
extends EventOperateResult {
    public void toResultMessage(IProcessAsyncMonitor monitor) {
        int successCount = 0;
        int failCount = 0;
        int otherCount = 0;
        List allBusinessObject = this.getAllBusinessObject();
        IOperateResultSet operateResultSet = this.getOperateResultSet(IEventOperateColumn.DEF_OPT_COLUMN);
        for (IBusinessObject businessObject : allBusinessObject) {
            WFMonitorCheckResult checkStatus = operateResultSet.getCheckStatus(businessObject);
            if (WFMonitorCheckResult.CHECK_PASS == checkStatus) {
                ++successCount;
                continue;
            }
            if (WFMonitorCheckResult.CHECK_UN_PASS == checkStatus) {
                ++failCount;
                continue;
            }
            ++otherCount;
        }
        monitor.info("\u4e00\u5171\uff1a\u300c" + allBusinessObject.size() + "\u300d\u4e2a\u5b9e\u4f8b\uff01");
        monitor.info("\u542f\u52a8\u6210\u529f\uff1a\u300c" + successCount + "\u300d\u4e2a\u5b9e\u4f8b\uff01");
        monitor.info("\u542f\u52a8\u5931\u8d25\uff1a\u300c" + failCount + "\u300d\u4e2a\u5b9e\u4f8b\uff01");
        if (otherCount > 0) {
            monitor.info("\u5176\u4ed6\u539f\u56e0\uff1a\u300c" + otherCount + "\u300d\u4e2a\u5b9e\u4f8b\uff01");
        }
    }
}

