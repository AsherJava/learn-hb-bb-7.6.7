/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.BatchMessageJob;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageJob;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SingleMessageJob;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.BatchMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageParamEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SimpleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SingleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.TodoMsgException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MessageEventListener {
    private final Logger logger = LoggerFactory.getLogger(MessageEventListener.class);
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Deprecated
    public void onTodoComplete(SimpleMessageEvent event) throws TodoMsgException {
        BusinessKey businessKey;
        MessageJob job = new MessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            MessageParamEvent messageParamEvent = new MessageParamEvent();
            messageParamEvent.setTask(event.getTask());
            messageParamEvent.setOperator(event.getOperator());
            businessKey = event.getBusinessKey();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            messageParamEvent.setDimensionValue(buildDimension.toString());
            messageParamEvent.setFormKey(businessKey.getFormKey());
            writeValueAsString = mapper.writeValueAsString((Object)messageParamEvent);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        businessKey = event.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @EventListener
    public void onTodoComplete(SingleMessageEvent event) throws TodoMsgException {
        SingleMessageJob job = new SingleMessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            ArrayList<MessageParamEvent> messageParamEvents = new ArrayList<MessageParamEvent>();
            MessageParamEvent messageParamEvent = new MessageParamEvent();
            messageParamEvent.setTask(event.getTask());
            messageParamEvent.setOperator(event.getOperator());
            BusinessKey businessKey = event.getBusinessKey();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            messageParamEvent.setDimensionValue(buildDimension.toString());
            messageParamEvent.setFormKey(businessKey.getFormKey());
            messageParamEvent.setActionCode(event.getActionCode());
            messageParamEvent.setContent(event.getContent());
            messageParamEvent.setSendMail(event.isSendMail());
            Set<String> signRoles = event.getSignRoles();
            messageParamEvent.setSignRoles(signRoles);
            messageParamEvents.add(messageParamEvent);
            writeValueAsString = mapper.writeValueAsString(messageParamEvents);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        BusinessKey businessKey = event.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @Deprecated
    public void onTodoComplete(BatchMessageEvent event) throws TodoMsgException {
        BusinessKey businessKey;
        BatchMessageJob job = new BatchMessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            MessageParamEvent messageParamEvent = new MessageParamEvent();
            messageParamEvent.setTask(event.getTask());
            messageParamEvent.setOperator(event.getOperator());
            businessKey = event.getBusinessKey();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            messageParamEvent.setDimensionValue(buildDimension.toString());
            messageParamEvent.setActionCode(event.getActionCode());
            messageParamEvent.setContent(event.getContent());
            messageParamEvent.setSendMail(event.isSendMail());
            messageParamEvent.setCanUploadUnitSize(event.getCanUploadUnitSize());
            messageParamEvent.setFromOrGroupKeys(event.getFormOrGroupKeys());
            messageParamEvent.setSignRoles(event.getSignRoles());
            writeValueAsString = mapper.writeValueAsString((Object)messageParamEvent);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        businessKey = event.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        map.put("formKey", businessKey.getFormKey());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void sendSimpleMessage(List<SimpleMessageEvent> events) throws TodoMsgException {
        if (events.isEmpty()) {
            return;
        }
        SimpleMessageEvent simpleMessageEvent = (SimpleMessageEvent)events.stream().findAny().get();
        MessageJob job = new MessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            ArrayList<MessageParamEvent> messageParamEvents = new ArrayList<MessageParamEvent>();
            for (SimpleMessageEvent event : events) {
                MessageParamEvent messageParamEvent = new MessageParamEvent();
                messageParamEvent.setTask(event.getTask());
                messageParamEvent.setOperator(event.getOperator());
                BusinessKey businessKey = event.getBusinessKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
                messageParamEvent.setDimensionValue(buildDimension.toString());
                messageParamEvent.setFormKey(businessKey.getFormKey());
                messageParamEvents.add(messageParamEvent);
            }
            writeValueAsString = mapper.writeValueAsString(messageParamEvents);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        BusinessKey businessKey = simpleMessageEvent.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void sendSingleMessage(List<SingleMessageEvent> events) throws TodoMsgException {
        if (events.isEmpty()) {
            return;
        }
        SingleMessageEvent singleMessageEvent = (SingleMessageEvent)events.stream().findAny().get();
        SingleMessageJob job = new SingleMessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            ArrayList<MessageParamEvent> messageParamEvents = new ArrayList<MessageParamEvent>();
            for (SingleMessageEvent event : events) {
                MessageParamEvent messageParamEvent = new MessageParamEvent();
                messageParamEvent.setTask(event.getTask());
                messageParamEvent.setOperator(event.getOperator());
                BusinessKey businessKey = event.getBusinessKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
                messageParamEvent.setDimensionValue(buildDimension.toString());
                messageParamEvent.setFormKey(businessKey.getFormKey());
                messageParamEvent.setActionCode(event.getActionCode());
                messageParamEvent.setContent(event.getContent());
                messageParamEvent.setSendMail(event.isSendMail());
                messageParamEvents.add(messageParamEvent);
            }
            writeValueAsString = mapper.writeValueAsString(messageParamEvents);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        BusinessKey businessKey = singleMessageEvent.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void sendBatchMessage(List<BatchMessageEvent> events) throws TodoMsgException {
        if (events.isEmpty()) {
            return;
        }
        BatchMessageEvent batchMessageEvent = (BatchMessageEvent)events.stream().findAny().get();
        BatchMessageJob job = new BatchMessageJob();
        job.setTitle("\u5f85\u529e\u6d88\u606f\u53d1\u9001");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        job.setUserGuid(user.getId());
        job.setUserName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        String writeValueAsString = null;
        try {
            ArrayList<MessageParamEvent> messageParamEvents = new ArrayList<MessageParamEvent>();
            for (BatchMessageEvent event : events) {
                MessageParamEvent messageParamEvent = new MessageParamEvent();
                messageParamEvent.setTask(event.getTask());
                messageParamEvent.setOperator(event.getOperator());
                BusinessKey businessKey = event.getBusinessKey();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
                messageParamEvent.setDimensionValue(buildDimension.toString());
                messageParamEvent.setFormKey(businessKey.getFormKey());
                messageParamEvent.setActionCode(event.getActionCode());
                messageParamEvent.setContent(event.getContent());
                messageParamEvent.setSendMail(event.isSendMail());
                messageParamEvent.setSignRoles(event.getSignRoles());
                ArrayList dims = new ArrayList();
                messageParamEvent.setCanUploadUnitSize(event.getCanUploadUnitSize());
                messageParamEvent.setFromOrGroupKeys(event.getFormOrGroupKeys());
                messageParamEvents.add(messageParamEvent);
            }
            writeValueAsString = mapper.writeValueAsString(messageParamEvents);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", writeValueAsString);
        BusinessKey businessKey = batchMessageEvent.getBusinessKey();
        map.put("formSchemeKey", businessKey.getFormSchemeKey());
        map.put("period", businessKey.getPeriod());
        job.setParams(map);
        try {
            RealTimeJobManager.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}

