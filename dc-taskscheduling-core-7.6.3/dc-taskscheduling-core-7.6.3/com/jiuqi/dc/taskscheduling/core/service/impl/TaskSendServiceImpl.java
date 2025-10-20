/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskParamsEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskMsgParam
 *  com.jiuqi.dc.taskscheduling.core.service.TaskSendService
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.cloud.stream.function.StreamBridge
 */
package com.jiuqi.dc.taskscheduling.core.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.enums.TaskParamsEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMQStateCheckEvent;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.core.msg.TaskMsgParam;
import com.jiuqi.dc.taskscheduling.core.service.TaskSendService;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class TaskSendServiceImpl
implements TaskSendService {
    private Logger logger = LoggerFactory.getLogger(TaskSendServiceImpl.class);
    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private INvwaSystemOptionService sysOptionService;

    public String startTask(String taskName, String taskParam) {
        return this.startTask(taskName, taskParam, null);
    }

    public String startTask(String taskName, String taskParam, Map<String, String> extInfo) {
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(taskName);
        Assert.isNotNull((Object)handler, (String)("\u672a\u627e\u5230\u4efb\u52a1\u4ee3\u7801\u4e3a" + taskName + "\u7684\u4efb\u52a1\u5904\u7406\u5668\uff01\uff01"), (Object[])new Object[0]);
        ApplicationContextRegister.getApplicationContext().publishEvent(new RabbitMQStateCheckEvent((Object)handler, handler.getModule()));
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(handler);
        Object paramObj = taskHandlerClient.getHandleParams(taskName, taskParam).getData();
        HashMap<String, String> convertParam = null;
        if (paramObj instanceof String) {
            List params = (List)JsonUtils.readValue((String)((String)paramObj), (TypeReference)new TypeReference<List<TaskMsgParam>>(){});
            convertParam = new HashMap<String, String>();
            for (TaskMsgParam msgParam : params) {
                convertParam.put(msgParam.getParam(), msgParam.getDimCode());
            }
        } else {
            convertParam = (HashMap<String, String>)paramObj;
        }
        if (Objects.isNull(convertParam) || convertParam.size() == 0) {
            return null;
        }
        DcTaskLogEO taskInfo = TaskInfoUtil.createTaskInfo((String)taskName, (String)taskParam);
        if (Objects.nonNull(extInfo)) {
            taskInfo.setExt_1(extInfo.containsKey("EXT_1") ? extInfo.get("EXT_1") : null);
            taskInfo.setExt_2(extInfo.containsKey("EXT_2") ? extInfo.get("EXT_2") : null);
            taskInfo.setExt_3(extInfo.containsKey("EXT_3") ? extInfo.get("EXT_3") : null);
            taskInfo.setExt_4(extInfo.containsKey("EXT_4") ? extInfo.get("EXT_4") : null);
            taskInfo.setExt_5(extInfo.containsKey("EXT_5") ? extInfo.get("EXT_5") : null);
        }
        ArrayList<DcTaskItemLogEO> taskItemList = new ArrayList<DcTaskItemLogEO>();
        ArrayList<TaskHandleMsg> handleMsgList = new ArrayList<TaskHandleMsg>();
        if (convertParam.containsKey(TaskParamsEnum.PARAM.name())) {
            JsonNode param = JsonUtils.readTree((String)((String)convertParam.get(TaskParamsEnum.PARAM.name())));
            Iterator elements = param.fieldNames();
            if (!elements.hasNext()) {
                return null;
            }
            while (elements.hasNext()) {
                String key = (String)elements.next();
                String value = Optional.ofNullable(param.get(key)).map(JsonNode::textValue).orElse(null);
                String instanceId = UUIDUtils.newHalfGUIDStr();
                TaskHandleMsg handleMsg = new TaskHandleMsg(handler.getName(), value, instanceId, UUIDUtils.emptyHalfGUIDStr(), key, 1, taskInfo.getId());
                DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)taskName, (String)instanceId, (String)handleMsg.getPreNodeId(), (IDimType)handler.getDimType(), (String)handleMsg.getDimCode(), (String)handleMsg.getParam(), (String)handleMsg.getRunnerId());
                handleMsg.setTaskItemId(taskItem.getId());
                handleMsg.setSqlRecordEnable(this.sysOptionService.findValueById("JDBC_SQL_RECORD"));
                handleMsgList.add(handleMsg);
                taskItemList.add(taskItem);
            }
        } else {
            for (Map.Entry entry : convertParam.entrySet()) {
                String instanceId = UUIDUtils.newHalfGUIDStr();
                TaskHandleMsg handleMsg = new TaskHandleMsg(handler.getName(), (String)entry.getValue(), instanceId, UUIDUtils.emptyHalfGUIDStr(), (String)entry.getKey(), 1, taskInfo.getId());
                DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)taskName, (String)instanceId, (String)handleMsg.getPreNodeId(), (IDimType)handler.getDimType(), (String)handleMsg.getDimCode(), (String)handleMsg.getParam(), (String)handleMsg.getRunnerId());
                handleMsg.setTaskItemId(taskItem.getId());
                handleMsg.setSqlRecordEnable(this.sysOptionService.findValueById("JDBC_SQL_RECORD"));
                handleMsgList.add(handleMsg);
                taskItemList.add(taskItem);
            }
        }
        this.taskLogService.insertTaskLogs(taskInfo, taskItemList);
        for (TaskHandleMsg msg : handleMsgList) {
            this.sendMessage(msg);
        }
        return taskInfo.getId();
    }

    public void sendMessage(TaskHandleMsg msg) {
        TaskHandlerVO handler = TaskHandlerManager.getTaskHandlerByCode(msg.getTaskName());
        if (handler == null) {
            throw new RuntimeException(String.format("\u672a\u627e\u5230\u540d\u79f0\u4e3a\u3010%1$s\u3011\u7684\u4efb\u52a1\u5904\u7406\u5668", msg.getTaskName()));
        }
        if (msg.getInstanceId() == null) {
            msg.setInstanceId(UUIDUtils.newHalfGUIDStr());
        }
        int queueNum = TaskHandlerManager.getQueueNum(handler, msg);
        try {
            this.waitQueueBindingStream(handler, queueNum);
            this.streamBridge.send(TaskHandlerManager.getBindingName(handler, queueNum), (Object)JsonUtils.writeValueAsString((Object)msg));
        }
        catch (Exception e) {
            this.logger.error("\u4efb\u52a1\u3010{}\u3011MQ\u6d88\u606f\u53d1\u9001\u5931\u8d25\uff0c\u6d88\u606f\u961f\u5217\u540d\uff1a{}", handler.getTitle(), TaskHandlerManager.getBindingDestination(handler, queueNum), e);
            this.taskLogService.updateTaskItemResultById(msg.getTaskItemId(), DataHandleState.FAILURE, "MQ\u6d88\u606f\u53d1\u9001\u5931\u8d25\n" + LogUtil.getExceptionStackStr((Throwable)e));
        }
    }

    private void waitQueueBindingStream(TaskHandlerVO handler, int queueNum) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(handler);
        String destination = TaskHandlerManager.getBindingDestination(handler, queueNum);
        try {
            int waitMinute = 0;
            while (!taskHandlerClient.isBinding(destination) && waitMinute < 30) {
                Thread.sleep(60000L);
                this.logger.info("\u961f\u5217\u3010{}\u3011\u7b2c{}\u6b21\u7b49\u5f85\u7ed1\u5b9a\u6d88\u8d39\u8005 \n", (Object)destination, (Object)(++waitMinute));
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public List<String> startSubTask(String subTaskName, String preParam, String runnerId, String preNodeId) {
        TaskHandlerVO subTaskHandler = TaskHandlerManager.getTaskHandlerByCode(subTaskName);
        Assert.isNotNull((Object)subTaskHandler, (String)("\u672a\u627e\u5230\u4efb\u52a1\u4ee3\u7801\u4e3a" + subTaskName + "\u7684\u4efb\u52a1\u5904\u7406\u5668\uff01\uff01"), (Object[])new Object[0]);
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getTaskHandlerClientByAppName(subTaskHandler);
        Object paramObj = taskHandlerClient.getHandleParams(subTaskName, preParam).getData();
        HashMap<String, String> subTaskParam = null;
        if (paramObj instanceof String) {
            List params = (List)JsonUtils.readValue((String)((String)paramObj), (TypeReference)new TypeReference<List<TaskMsgParam>>(){});
            subTaskParam = new HashMap<String, String>();
            for (TaskMsgParam msgParam : params) {
                subTaskParam.put(msgParam.getParam(), msgParam.getDimCode());
            }
        } else {
            subTaskParam = (HashMap<String, String>)paramObj;
        }
        if (Objects.isNull(subTaskParam) || subTaskParam.size() == 0) {
            return null;
        }
        int level = 1;
        if (TaskTypeEnum.LEVEL.equals((Object)subTaskHandler.getTaskType())) {
            ++level;
        }
        ArrayList<TaskHandleMsg> handleMsgList = new ArrayList<TaskHandleMsg>();
        ArrayList<DcTaskItemLogEO> taskItemList = new ArrayList<DcTaskItemLogEO>();
        ArrayList<String> instanceIds = new ArrayList<String>();
        for (Map.Entry entry : subTaskParam.entrySet()) {
            String instanceId = UUIDUtils.newHalfGUIDStr();
            TaskHandleMsg handleMsg = new TaskHandleMsg(subTaskName, (String)entry.getValue(), instanceId, preNodeId, (String)entry.getKey(), level, runnerId);
            DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo((String)subTaskName, (String)instanceId, (String)preNodeId, (IDimType)subTaskHandler.getDimType(), (String)handleMsg.getDimCode(), (String)handleMsg.getParam(), (String)runnerId);
            handleMsg.setTaskItemId(taskItem.getId());
            handleMsg.setSqlRecordEnable(this.sysOptionService.findValueById("JDBC_SQL_RECORD"));
            handleMsgList.add(handleMsg);
            taskItemList.add(taskItem);
            instanceIds.add(instanceId);
        }
        this.taskLogService.insertTaskItemLogs(taskItemList);
        for (TaskHandleMsg msg : handleMsgList) {
            this.sendMessage(msg);
        }
        return instanceIds;
    }

    public void waitTaskFinished(String runnerId) {
        Integer unExecuteCount;
        while ((unExecuteCount = this.taskLogService.getUnFinishTaskItemCount(runnerId)) != null && unExecuteCount != 0) {
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                this.logger.error("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}

