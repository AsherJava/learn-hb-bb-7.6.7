/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.taskscheduling.api.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.taskschedule.stream.util.EntMqStreamDynamicUtils;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.core.data.SqlExecuteLogDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskEnableQueryDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.msg.TaskMsgParam;
import com.jiuqi.dc.taskscheduling.core.util.ITaskHandlerGather;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TaskHandlerController {
    private static final String API_PATH = "/api/datacenter/v1/taskhandler/anon";
    @Autowired
    private ITaskHandlerGather taskHandlerGather;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private MessagePublisher messagePublisher;
    @Autowired
    private TaskLogService taskLogService;

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/regist"})
    public BusinessResponseEntity<String> regist() {
        this.messagePublisher.publishMessage("TASKSCHEDULING_REGIST_CHANNEL", (Object)"sub");
        return BusinessResponseEntity.ok(null);
    }

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/refresh"})
    public BusinessResponseEntity<String> refresh(RemoteSeviceChangeEvent event) {
        this.messagePublisher.publishMessage("TASKSCHEDULING_REGIST_CHANNEL", (Object)event);
        return BusinessResponseEntity.ok(null);
    }

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/listHandler"})
    public BusinessResponseEntity<List<TaskHandlerVO>> getHandlerList() {
        return BusinessResponseEntity.ok(this.taskHandlerGather.getHandlerList());
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/getHandleParams/{taskName}"})
    public BusinessResponseEntity<String> getHandleParams(@PathVariable(name="taskName", required=true) String taskName, @RequestBody String preParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        Map<String, String> handleParams = taskHandler.getHandleParams(preParam);
        ArrayList<TaskMsgParam> params = new ArrayList<TaskMsgParam>();
        if (handleParams != null && !handleParams.isEmpty()) {
            for (Map.Entry<String, String> handleEntry : handleParams.entrySet()) {
                params.add(new TaskMsgParam(handleEntry.getValue(), handleEntry.getKey()));
            }
        }
        return BusinessResponseEntity.ok((Object)JsonUtils.writeValueAsString(params));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/handleTask/{taskName}"})
    public BusinessResponseEntity<TaskHandleResult> handleTask(@PathVariable(name="taskName", required=true) String taskName, @RequestBody TaskHandleMsg executeMsg) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        BaseTaskMonitor monitor = new BaseTaskMonitor(taskName, executeMsg.getTaskItemId(), this.taskLogService);
        monitor.start();
        ThreadContext.put((Object)"TASKHANDLEMSG_KEY", (Object)executeMsg);
        ThreadContext.put((Object)"TASKHANDLER_INSTANCEID_KEY", (Object)executeMsg.getInstanceId());
        ThreadContext.put((Object)"TASKHANDLER_RUNNERID_KEY", (Object)executeMsg.getRunnerId());
        ThreadContext.put((Object)"SQLLOGID_KEY", (Object)executeMsg.getTaskItemId());
        ThreadContext.put((Object)"SQL_RECORD_ENABLE_KEY", (Object)executeMsg.getSqlRecordEnable());
        try {
            BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.ok((Object)taskHandler.handleTask(executeMsg.getParam(), monitor));
            return businessResponseEntity;
        }
        catch (Exception e) {
            BusinessResponseEntity businessResponseEntity = BusinessResponseEntity.error((String)LogUtil.getExceptionStackStr((Throwable)e));
            return businessResponseEntity;
        }
        finally {
            ThreadContext.remove((Object)"TASKHANDLEMSG_KEY");
            ThreadContext.remove((Object)"TASKHANDLER_INSTANCEID_KEY");
            ThreadContext.remove((Object)"TASKHANDLER_RUNNERID_KEY");
            ThreadContext.remove((Object)"SQLLOGID_KEY");
            ThreadContext.remove((Object)"SQL_RECORD_ENABLE_KEY");
        }
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/afterSubtasksHandle/{taskName}"})
    public BusinessResponseEntity<String> afterSubtasksHandle(@PathVariable(name="taskName", required=true) String taskName, @RequestBody String preParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(taskName);
        taskHandler.afterSubtasksHandle(preParam);
        return BusinessResponseEntity.ok((Object)taskName);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startTask/{taskName}"})
    public BusinessResponseEntity<String> startTask(@PathVariable(name="taskName", required=true) String taskName, @RequestBody String preParam) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.startTask(taskName, preParam);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startTaskWithExtInfo/{taskName}"})
    public BusinessResponseEntity<String> startTaskWithExtInfo(@PathVariable(name="taskName", required=true) String taskName, @RequestBody TaskParamVO preParam) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.startTaskWithExtInfo(taskName, preParam);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startSubTask/{taskName}"})
    public BusinessResponseEntity<List<String>> startSubTask(@PathVariable(name="taskName", required=true) String taskName, @RequestBody TaskHandleMsg executeMsg) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.startSubTask(taskName, executeMsg);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/waitTaskFinished/{runnerId}"})
    public void waitTaskFinished(@PathVariable(name="runnerId", required=true) String runnerId) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        taskHandlerClient.waitTaskFinished(runnerId);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/queryTaskLog"})
    public BusinessResponseEntity<List<LogManagerDetailVO>> queryTaskLog(@RequestBody TaskCountQueryDTO queryDto) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.queryTaskLog(queryDto);
    }

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/getTaskProgress/{runnerId}"})
    public BusinessResponseEntity<Double> getTaskProgress(@PathVariable(name="runnerId", required=true) String runnerId) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.getTaskProgress(runnerId);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/enable"})
    public BusinessResponseEntity<Boolean> enable(@RequestBody TaskEnableQueryDTO queryParam) {
        ITaskHandler taskHandler = this.taskHandlerGather.getITaskHandler(queryParam.getTaskName());
        return BusinessResponseEntity.ok((Object)taskHandler.enable(queryParam.getPreTaskName(), queryParam.getPreParam()));
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/countTask"})
    public BusinessResponseEntity<Integer> countTask(@RequestBody TaskCountQueryDTO queryParam) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        return taskHandlerClient.countTask(queryParam);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/recordSql"})
    void recordSql(@RequestBody SqlExecuteLogDTO sqlExecuteLogDTO) {
        this.taskHandlerFactory.getMainTaskHandlerClient().recordSql(sqlExecuteLogDTO);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/recordSqlEndTime/{sqlLogId}"})
    void recordSqlEndTime(@PathVariable(name="sqlLogId") String sqlLogId, @RequestBody Date endTime) {
        this.taskHandlerFactory.getMainTaskHandlerClient().recordSqlEndTime(sqlLogId, endTime);
    }

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/check/binding"})
    public boolean isBinding(@RequestBody String destination) {
        return EntMqStreamDynamicUtils.getConsumerBeanToQueueNameMap().containsValue(destination);
    }
}

