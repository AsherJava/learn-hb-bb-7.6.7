/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IDimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IDimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfo;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInstanceParser;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageRelevantInfo;
import com.jiuqi.nr.workflow2.events.executor.util.NotificationSendUtil;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NOTIFICATION_ASYNC_TASK", groupTitle="\u6d41\u7a0b2.0 \u901a\u77e5\u4e8b\u4ef6\u5355\u4e2a\u53d1\u9001\u6d88\u606f\u6267\u884c\u4efb\u52a1", subject="\u62a5\u8868")
public class NotificationSingleTaskExecutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        VaMessageClient vaMessageClient = (VaMessageClient)SpringBeanUtils.getBean(VaMessageClient.class);
        MessageInstanceParser messageInstanceParser = (MessageInstanceParser)SpringBeanUtils.getBean(MessageInstanceParser.class);
        NotificationSendUtil notificationSendUtil = (NotificationSendUtil)SpringBeanUtils.getBean(NotificationSendUtil.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNC_TASK_SINGLE_NOTIFICATION", jobContext);
        if (Objects.nonNull(params) && Objects.nonNull(this.getArgs())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map execParam = (Map)objectMapper.readValue(this.getArgs(), (TypeReference)new TypeReference<Map<String, Object>>(){});
                ProcessExecuteEnv envParam = (ProcessExecuteEnv)objectMapper.readValue(execParam.get("envParam").toString(), ProcessExecuteEnv.class);
                boolean isSendMail = (Boolean)execParam.get("isSendMail");
                boolean isTodoEnabled = (Boolean)execParam.get("isTodoEnabled");
                JSONObject eventJsonConfig = new JSONObject(execParam.get("eventConfig").toString());
                WorkflowObjectType workflowObjectType = workflowSettingsService.queryTaskWorkflowObjectType(envParam.getTaskKey());
                IBusinessObject businessObject = workflowObjectType.equals((Object)WorkflowObjectType.FORM) ? (IBusinessObject)objectMapper.readValue(execParam.get("businessObject").toString(), IFormObject.class) : (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? (IBusinessObject)objectMapper.readValue(execParam.get("businessObject").toString(), IFormGroupObject.class) : (IBusinessObject)objectMapper.readValue(execParam.get("businessObject").toString(), IDimensionObject.class));
                BusinessKey businessKey = new BusinessKey(envParam.getTaskKey(), businessObject);
                double progress = 0.0;
                double increaseProgress = 1.0 / (double)eventJsonConfig.keySet().size();
                for (String messageType : eventJsonConfig.keySet()) {
                    JSONObject messageConfig = eventJsonConfig.getJSONObject(messageType);
                    boolean isUserSelectable = messageConfig.getBoolean("userSelectable");
                    if (messageType.equals(MessageType.EMAIL.code) && isUserSelectable && !isSendMail) continue;
                    Set<String> receiveUsers = notificationSendUtil.getReceiverSet(envParam, messageConfig, (IBusinessKey)businessKey, isTodoEnabled);
                    String receiver = receiveUsers.isEmpty() ? null : receiveUsers.iterator().next();
                    MessageRelevantInfo relevantInfo = notificationSendUtil.buildVaribleReplaceMap(envParam, (IBusinessKey)businessKey, receiver);
                    MessageInfo messageInfo = messageInstanceParser.parseToMessageInfo(messageConfig.toString(), relevantInfo.getVaribleReplaceMap());
                    VaMessageSendDTO dto = new VaMessageSendDTO();
                    dto.setGrouptype("\u62a5\u8868\u6d41\u7a0b\u901a\u77e5");
                    dto.setMsgtype(envParam.getActionTitle() + "\u901a\u77e5");
                    dto.setReceiveUserIds(new ArrayList<String>(receiveUsers));
                    dto.setTitle(messageInfo.getTitle());
                    dto.setContent(notificationSendUtil.postProcess(messageType, messageInfo.getContent(), relevantInfo));
                    if (messageType.equals(MessageType.MESSAGE.code)) {
                        dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
                    } else if (messageType.equals(MessageType.EMAIL.code)) {
                        dto.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
                    } else if (messageType.equals(MessageType.SHORT_MESSAGE.code)) {
                        dto.setMsgChannel(VaMessageOption.MsgChannel.SMS);
                    }
                    R response = vaMessageClient.addMsg(dto);
                    asyncTaskMonitor.progressAndMessage(progress += increaseProgress, response.getMsg());
                }
                asyncTaskMonitor.finish("success", null);
            }
            catch (Exception e) {
                LoggerFactory.getLogger(((Object)((Object)this)).getClass()).error(e.getMessage(), e);
                throw new JobExecutionException((Throwable)e);
            }
        }
    }
}

