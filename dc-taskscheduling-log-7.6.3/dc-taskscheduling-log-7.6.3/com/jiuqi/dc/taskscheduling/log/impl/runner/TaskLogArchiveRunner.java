/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.dc.taskscheduling.log.impl.runner;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.log.impl.data.ArchiveParam;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PlanTaskRunner(id="F1C4783DB3A54C1C9C7EECA3BFFCF59D", name="TaskLogArchiveRunner", title="\u3010\u65e5\u5fd7\u5f52\u6863\u3011\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863", settingPage="customTemplate|archiveType", group="\u4e00\u672c\u8d26/\u65e5\u5fd7\u5f52\u6863")
@Component(value="TaskLogArchiveRunner")
public class TaskLogArchiveRunner
extends Runner {
    private final Logger logger = LogFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private TaskLogService taskLogService;

    public boolean excute(String runnerParameter) {
        String instanceId = UUIDUtils.newUUIDStr();
        String taskType = "\u3010\u65e5\u5fd7\u5f52\u6863\u3011\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863";
        Date startTime = DateUtils.now();
        this.appendLog(String.format("\u3010\u65e5\u5fd7\u5f52\u6863\u3011\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863taskId = %1$s\n", instanceId));
        try {
            ArchiveParam archiveParam = (ArchiveParam)JsonUtils.readValue((String)runnerParameter, ArchiveParam.class);
            String result = this.taskLogService.logArchive(archiveParam);
            this.insertTaskAndItemLogs(instanceId, taskType, result, startTime, DataHandleState.SUCCESS);
            this.appendLog(result);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            this.logger.error("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6267\u884c\u5931\u8d25\u3002\n", (Throwable)e);
            this.insertTaskAndItemLogs(instanceId, taskType, "\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6267\u884c\u5931\u8d25\u3002\n" + LogUtil.getExceptionStackStr((Throwable)e), startTime, DataHandleState.FAILURE);
            this.appendLog("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6267\u884c\u5931\u8d25\u3002\n" + LogUtil.getExceptionStackStr((Throwable)e));
            return Boolean.FALSE;
        }
    }

    private void insertTaskAndItemLogs(String instanceId, String taskType, String result, Date startTime, DataHandleState state) {
        Date endTime = DateUtils.now();
        DcTaskLogEO taskInfo = TaskInfoUtil.createTaskInfo(taskType);
        taskInfo.setId(instanceId);
        taskInfo.setResultLog("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6267\u884c\u4e34\u65f6\u7ed3\u679c");
        taskInfo.setEndTime(endTime);
        ArrayList<DcTaskItemLogEO> taskItemList = new ArrayList<DcTaskItemLogEO>();
        DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo(taskType, UUIDUtils.newHalfGUIDStr(), UUIDUtils.emptyHalfGUIDStr(), DimType.UNITCODE, null, null, instanceId);
        taskItem.setResultLog("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6267\u884c\u4e34\u65f6\u7ed3\u679c");
        taskItem.setExecuteState(state.getState());
        taskItem.setStartTime(startTime);
        taskItem.setEndTime(endTime);
        taskItemList.add(taskItem);
        this.taskLogService.insertTaskLogs(taskInfo, taskItemList);
        this.taskLogService.updateTaskItemResultById(taskItem.getId(), state, result);
    }
}

