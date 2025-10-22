/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.snapshot.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.snapshot.asynctask.SnapshotUpgradeTaskExecutor;
import com.jiuqi.nr.snapshot.input.UpgradeContext;
import com.jiuqi.nr.snapshot.output.TaskObj;
import com.jiuqi.nr.snapshot.upgrade.SnapshotUpgradeService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/snapshot"})
public class SnapshotController {
    @Autowired
    private SnapshotUpgradeService snapshotUpgradeService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @GetMapping(value={"/get-task"})
    @ApiOperation(value="\u83b7\u53d6\u53ef\u5347\u7ea7\u4efb\u52a1")
    public List<TaskObj> getUpgradeTasks() {
        return this.snapshotUpgradeService.getUpgradeTasks();
    }

    @PostMapping(value={"/upgrade"})
    public AsyncTaskInfo upgrade(@Valid @RequestBody UpgradeContext upgradeContext) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)upgradeContext));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new SnapshotUpgradeTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_SNAPSHOTUPGRADE");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

