/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils
 *  com.jiuqi.dc.base.common.jdbc.service.SqlRecordService
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.dc.taskscheduling.core.data.SqlExecuteLogDTO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskEnableQueryDTO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.core.service.TaskSendService
 *  com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent
 */
package com.jiuqi.dc.taskscheduling.core.service.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.core.data.SqlExecuteLogDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskEnableQueryDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.service.TaskSendService;
import com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class TaskHandlerService
implements TaskHandlerClient {
    @Autowired
    private ITaskHandlerGather taskHandlerGather;
    @Autowired
    private TaskSendService taskSendService;
    @Autowired
    private MessagePublisher messagePublisher;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private SqlRecordService sqlRecordService;

    public BusinessResponseEntity<String> regist() {
        this.messagePublisher.publishMessage("TASKSCHEDULING_REGIST_CHANNEL", (Object)"main");
        return BusinessResponseEntity.ok(null);
    }

    public BusinessResponseEntity<String> refresh(RemoteSeviceChangeEvent event) {
        this.messagePublisher.publishMessage("TASKSCHEDULING_REGIST_CHANNEL", (Object)event);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<TaskHandlerVO>> getHandlerList() {
        return BusinessResponseEntity.ok((Object)this.taskHandlerGather.getHandlerList());
    }

    public BusinessResponseEntity<Object> getHandleParams(String taskName, String preParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        return BusinessResponseEntity.ok((Object)taskHandler.getHandleParams(preParam));
    }

    public BusinessResponseEntity<TaskHandleResult> handleTask(String taskName, TaskHandleMsg executeMsg) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        BaseTaskMonitor monitor = new BaseTaskMonitor(taskName, executeMsg.getTaskItemId(), this.taskLogService);
        monitor.start();
        return BusinessResponseEntity.ok((Object)taskHandler.handleTask(executeMsg.getParam(), (ITaskProgressMonitor)monitor));
    }

    public BusinessResponseEntity<String> afterSubtasksHandle(String taskName, String preParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        taskHandler.afterSubtasksHandle(preParam);
        return BusinessResponseEntity.ok((Object)taskName);
    }

    public BusinessResponseEntity<String> startTask(String taskName, String preParam) {
        String taskId = this.taskSendService.startTask(taskName, preParam);
        return BusinessResponseEntity.ok((Object)taskId);
    }

    public BusinessResponseEntity<String> startTaskWithExtInfo(String taskName, TaskParamVO preParam) {
        HashMap extInfo = CollectionUtils.newHashMap();
        extInfo.put("EXT_1", preParam.getExt_1());
        extInfo.put("EXT_2", preParam.getExt_2());
        extInfo.put("EXT_3", preParam.getExt_3());
        extInfo.put("EXT_4", preParam.getExt_4());
        extInfo.put("EXT_5", preParam.getExt_5());
        String taskId = this.taskSendService.startTask(taskName, preParam.getPreParam(), (Map)extInfo);
        return BusinessResponseEntity.ok((Object)taskId);
    }

    public BusinessResponseEntity<List<String>> startSubTask(String taskName, TaskHandleMsg executeMsg) {
        List instanceIds = this.taskSendService.startSubTask(taskName, Boolean.TRUE.equals(executeMsg.getParamCompress()) ? CompressUtil.deCompress((String)executeMsg.getParam()) : executeMsg.getParam(), executeMsg.getRunnerId(), executeMsg.getPreNodeId());
        return BusinessResponseEntity.ok((Object)instanceIds);
    }

    public void waitTaskFinished(String runnerId) {
        this.taskSendService.waitTaskFinished(runnerId);
    }

    public BusinessResponseEntity<Double> getTaskProgress(String runnerId) {
        return BusinessResponseEntity.ok((Object)this.taskLogService.getTaskProgressByRunnerId(runnerId));
    }

    public BusinessResponseEntity<List<LogManagerDetailVO>> queryTaskLog(TaskCountQueryDTO queryDto) {
        return BusinessResponseEntity.ok((Object)this.taskLogService.queryTaskLog(queryDto));
    }

    public BusinessResponseEntity<Boolean> enable(TaskEnableQueryDTO queryParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(queryParam.getTaskName());
        return BusinessResponseEntity.ok((Object)taskHandler.enable(queryParam.getPreTaskName(), queryParam.getPreParam()));
    }

    public BusinessResponseEntity<Integer> countTask(TaskCountQueryDTO queryParam) {
        return BusinessResponseEntity.ok((Object)this.taskLogService.countTask(queryParam));
    }

    public void recordSql(SqlExecuteLogDTO sqlExecuteLogDTO) {
        this.sqlRecordService.recordSql(sqlExecuteLogDTO.getId(), sqlExecuteLogDTO.getLogId(), sqlExecuteLogDTO.getSqlString(), sqlExecuteLogDTO.getExecuteParam(), sqlExecuteLogDTO.getStartTime());
    }

    public void recordSqlEndTime(String sqlLogId, Date endTime) {
        this.sqlRecordService.recordEndTime(sqlLogId, endTime);
    }

    public boolean isBinding(String destination) {
        return EntMqStreamDynamicUtils.getConsumerBeanToQueueNameMap().containsValue(destination);
    }
}

