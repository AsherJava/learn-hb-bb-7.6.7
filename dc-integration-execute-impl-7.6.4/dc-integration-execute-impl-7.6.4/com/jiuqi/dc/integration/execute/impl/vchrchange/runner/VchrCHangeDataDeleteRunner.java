/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.runner;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.dc.integration.execute.impl.vchrchange.service.VchrChangeHandleService;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PlanTaskRunner(id="3292A32E3A25409DA543926C83E09D8C", name="vchrCHangeDataDeleteRunner", title="\u3010\u51ed\u8bc1\u3011\u51ed\u8bc1\u53d8\u66f4\u6570\u636e\u6e05\u9664", group="\u4e00\u672c\u8d26/\u6570\u636e\u6574\u5408", order=3)
@Component(value="vchrCHangeDataDeleteRunner")
public class VchrCHangeDataDeleteRunner
extends Runner {
    @Autowired
    private VchrChangeHandleService service;
    @Autowired
    private TaskLogService logService;

    public boolean excute(String runnerParameter) {
        Date startTime = DateUtils.now();
        String log = this.service.deleteVchrData();
        String instanceId = UUIDUtils.newUUIDStr();
        this.logService.insertSuccessTaskAndItemLogs(instanceId, "\u3010\u51ed\u8bc1\u3011\u51ed\u8bc1\u53d8\u66f4\u6570\u636e\u6e05\u9664", log, startTime);
        this.appendLog(String.format("\u3010\u51ed\u8bc1\u3011\u51ed\u8bc1\u53d8\u66f4\u6570\u636e\u6e05\u9664taskId = %1$s\n", instanceId));
        this.appendLog(log);
        return true;
    }
}

