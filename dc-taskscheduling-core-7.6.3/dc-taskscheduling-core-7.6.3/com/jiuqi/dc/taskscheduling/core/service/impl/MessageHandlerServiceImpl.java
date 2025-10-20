/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskMsgParam
 *  com.jiuqi.dc.taskscheduling.core.service.MessageHandleService
 *  com.jiuqi.dc.taskscheduling.core.service.TaskSendService
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.dc.taskscheduling.core.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.impl.BaseTaskMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.msg.TaskMsgParam;
import com.jiuqi.dc.taskscheduling.core.service.MessageHandleService;
import com.jiuqi.dc.taskscheduling.core.service.TaskSendService;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerServiceImpl
implements MessageHandleService {
    private Logger logger = LoggerFactory.getLogger(MessageHandlerServiceImpl.class);
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private TaskSendService taskSendService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private INvwaSystemOptionService sysOptionService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handlerMsg(TaskHandleMsg msg) {
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(msg.getTaskName());
        Assert.isNotNull((Object)handler, (String)String.format("\u672a\u627e\u5230\u540d\u79f0\u4e3a\u3010%1$s\u3011\u7684\u6570\u636e\u5904\u7406\u4efb\u52a1", msg.getTaskName()), (Object[])new Object[0]);
        String logId = msg.getTaskItemId();
        Assert.isNotEmpty((String)logId, (String)String.format("\u6570\u636e\u5904\u7406\u4efb\u52a1\u3010%1$s\u3011\u6ca1\u6709\u65e5\u5fd7ID", msg.getTaskName()), (Object[])new Object[0]);
        BaseTaskMonitor monitor = new BaseTaskMonitor(msg.getTaskName(), logId, this.taskLogService);
        if (!monitor.beforeStart()) {
            return;
        }
        TaskHandleResult result = null;
        try {
            if (handler.isDimSerialExecute() && this.existExecuting(handler, msg)) {
                TaskHandleMsg reMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
                Thread.sleep(100L);
                this.taskSendService.sendMessage(reMsg);
                return;
            }
            monitor.start();
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(handler);
            BusinessResponseEntity handlerResp = taskHandlerClient.handleTask(handler.getName(), msg);
            if (!handlerResp.isSuccess()) {
                throw new BusinessRuntimeException(handlerResp.getErrorMessage());
            }
            if (handlerResp.getData() == null) {
                throw new BusinessRuntimeException("\u5904\u7406\u4efb\u52a1\u8c03\u7528\u8fd4\u56de\u7ed3\u679c\u5bf9\u8c61\u4e3anull\u3002");
            }
            result = (TaskHandleResult)handlerResp.getData();
            if (result.getRejoin().booleanValue()) {
                TaskHandleMsg reMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
                this.taskSendService.sendMessage(reMsg);
                return;
            }
        }
        catch (Exception e) {
            StringBuilder errorLog = new StringBuilder();
            if (result != null) {
                errorLog.append(result.getLog()).append("\n");
            }
            errorLog.append("\u5904\u7406\u4efb\u52a1\u65f6\u53d1\u751f\u5f02\u5e38\uff1a").append(LogUtil.getExceptionStackStr((Throwable)e));
            monitor.error(errorLog.toString(), (Throwable)e);
            return;
        }
        try {
            if (Objects.nonNull(result) && result.isSuccess().booleanValue() && !result.getSendPostTaskMsgWhileHandleTask().booleanValue()) {
                this.handlePostTask(msg, result);
            }
        }
        finally {
            monitor.finish(result);
        }
    }

    private boolean existExecuting(TaskHandlerVO handler, TaskHandleMsg msg) {
        String runnerId = msg.getRunnerId();
        String dimCode = msg.getDimCode();
        String handlerName = handler.getName();
        Integer stateCountByRunnerIdAndDimCode = this.taskLogService.getStateCountByRunnerIdAndDimCode(runnerId, handlerName, dimCode, DataHandleState.EXECUTING);
        return stateCountByRunnerIdAndDimCode != null && stateCountByRunnerIdAndDimCode > 0;
    }

    public void sendPostTaskMsg(TaskHandleMsg msg, String preParam) {
        this.insertPostTaskLogAndSendMsg(msg, preParam);
    }

    private void handlePostTask(TaskHandleMsg msg, TaskHandleResult result) {
        Map<TaskHandlerVO, String> levelHandlerMap = this.insertPostTaskLogAndSendMsg(msg, result.getPreParam());
        if (levelHandlerMap.isEmpty()) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        block2: for (TaskHandlerVO levelHandler : levelHandlerMap.keySet()) {
            while (true) {
                Integer unExecuteCount;
                if ((unExecuteCount = this.taskLogService.getUnFinishTaskItemCount(levelHandlerMap.get(levelHandler))) == null || unExecuteCount == 0) {
                    result.appendLog(String.format("%1$s\u5b50\u4efb\u52a1\u3010%2$s\u3011\u5904\u7406\u5b8c\u6210", sdf.format(new Date()), levelHandler.getTitle()));
                    continue block2;
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e) {
                    this.logger.error("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25", e);
                    Thread.currentThread().interrupt();
                }
            }
        }
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(msg.getTaskName());
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(handler);
        taskHandlerClient.afterSubtasksHandle(msg.getTaskName(), msg.getParam());
    }

    private Map<TaskHandlerVO, String> insertPostTaskLogAndSendMsg(TaskHandleMsg msg, String preParam) {
        HashMap<TaskHandlerVO, String> levelHandlerMap = new HashMap<TaskHandlerVO, String>();
        List<TaskHandlerVO> postHandlers = TaskHandlerManager.getChildrenTaskHandlerByCode(msg.getTaskName());
        String sqlRecordEnable = this.sysOptionService.findValueById("JDBC_SQL_RECORD");
        for (TaskHandlerVO postHandler : postHandlers) {
            HashMap<String, String> paramimMap = null;
            try {
                if (!this.taskHandlerFactory.isEnable(postHandler, msg.getTaskName(), preParam)) continue;
                TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(postHandler);
                Object paramObj = taskHandlerClient.getHandleParams(postHandler.getName(), preParam).getData();
                if (paramObj instanceof String) {
                    List params = (List)JsonUtils.readValue((String)((String)paramObj), (TypeReference)new TypeReference<List<TaskMsgParam>>(){});
                    paramimMap = new HashMap<String, String>();
                    for (TaskMsgParam msgParam : params) {
                        paramimMap.put(msgParam.getParam(), msgParam.getDimCode());
                    }
                } else if (Objects.nonNull(paramObj)) {
                    paramimMap = (HashMap<String, String>)paramObj;
                }
            }
            catch (Exception e) {
                this.logger.error("\u53d1\u8d77\u540e\u7f6e\u4efb\u52a1\u3010{}\u3011\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u5f02\u5e38", (Object)postHandler.getTitle(), (Object)e);
                DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)postHandler.getName(), (String)msg.getInstanceId(), (String)msg.getTaskItemId(), (IDimType)postHandler.getDimType(), (String)msg.getDimCode(), (String)preParam, (String)msg.getRunnerId());
                taskItem.setExecuteState(Integer.valueOf(DataHandleState.FAILURE.getState()));
                taskItem.setResultLog(String.format("\u53d1\u8d77\u540e\u7f6e\u4efb\u52a1\u3010%1$s\u3011\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u5f02\u5e38\uff0c\u5f02\u5e38\u539f\u56e0\uff1a%2$s\n%3$s", postHandler.getTitle(), e.getMessage(), LogUtil.getExceptionStackStr((Throwable)e)));
                Date time = new Date();
                taskItem.setStartTime(time);
                taskItem.setEndTime(time);
                this.taskLogService.insertTaskItemLogs((List)CollectionUtils.newArrayList((Object[])new DcTaskItemLogEO[]{taskItem}));
            }
            if (Objects.isNull(paramimMap) || paramimMap.isEmpty()) continue;
            String instanceId = InstanceTypeEnum.FOLLOW.equals((Object)postHandler.getInstanceType()) ? msg.getInstanceId() : UUIDUtils.newUUIDStr();
            String preNodeId = msg.getTaskItemId();
            int level = 1;
            if (TaskTypeEnum.LEVEL.equals((Object)postHandler.getTaskType())) {
                instanceId = msg.getTaskItemId();
                levelHandlerMap.put(postHandler, instanceId);
                level = msg.getLevel() + 1;
            }
            ArrayList<TaskHandleMsg> postMsgList = new ArrayList<TaskHandleMsg>(paramimMap.size());
            for (String param : paramimMap.keySet()) {
                TaskHandleMsg taskHandleMsg = new TaskHandleMsg(postHandler.getName(), (String)paramimMap.get(param), instanceId, preNodeId, param, level, msg.getRunnerId());
                taskHandleMsg.setSqlRecordEnable(sqlRecordEnable);
                postMsgList.add(taskHandleMsg);
            }
            this.sendMessage(postMsgList, postHandler);
        }
        return levelHandlerMap;
    }

    private void sendMessage(List<TaskHandleMsg> msgList, TaskHandlerVO handler) {
        if (msgList == null || msgList.isEmpty()) {
            return;
        }
        CommonUtil.waitServerStartUp();
        List<DcTaskItemLogEO> taskItemList = this.getTaskItemLogs(msgList, handler.getName(), (IDimType)handler.getDimType());
        this.taskLogService.insertTaskItemLogs(taskItemList);
        for (TaskHandleMsg msg : msgList) {
            this.taskSendService.sendMessage(msg);
        }
    }

    private List<DcTaskItemLogEO> getTaskItemLogs(List<TaskHandleMsg> msgList, String taskName, IDimType dimType) {
        ArrayList<DcTaskItemLogEO> taskItemList = new ArrayList<DcTaskItemLogEO>();
        for (TaskHandleMsg msg : msgList) {
            DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)taskName, (String)msg.getInstanceId(), (String)msg.getPreNodeId(), (IDimType)dimType, (String)msg.getDimCode(), (String)msg.getParam(), (String)msg.getRunnerId());
            taskItemList.add(taskItem);
            msg.setTaskItemId(taskItem.getId());
        }
        return taskItemList;
    }
}

