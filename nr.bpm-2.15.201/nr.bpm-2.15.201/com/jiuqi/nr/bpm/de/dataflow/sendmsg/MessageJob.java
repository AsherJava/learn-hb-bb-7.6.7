/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageParamEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SimpleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.service.DeployService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="SENDMESSAGE_JOB", groupTitle="\u53d1\u9001\u5f85\u529e\u6d88\u606f\u5373\u65f6\u4efb\u52a1")
public class MessageJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(MessageJob.class);
    private ISendMessage sendMessage;
    private BusinessGenerator businessGenerator;
    private IWorkflow workflow;

    public void execute(JobContext context) throws JobExecutionException {
        try {
            this.initParam();
            String parameterValue = context.getParameterValue("message");
            ObjectMapper mapper = new ObjectMapper();
            this.initMapperObject(mapper);
            List objects = (List)mapper.readValue(parameterValue, (TypeReference)new TypeReference<List<MessageParamEvent>>(){});
            List<SimpleMessageEvent> simpleMessageEvent = this.transformToEvent(context, objects);
            for (SimpleMessageEvent simpleMessageEvent2 : simpleMessageEvent) {
                this.sendMessage(simpleMessageEvent2);
            }
        }
        catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initParam() {
        this.sendMessage = (ISendMessage)SpringBeanUtils.getBean(ISendMessage.class);
        this.businessGenerator = (BusinessGenerator)SpringBeanUtils.getBean(BusinessGenerator.class);
        this.workflow = (IWorkflow)SpringBeanUtils.getBean(IWorkflow.class);
    }

    private List<SimpleMessageEvent> transformToEvent(JobContext context, List<MessageParamEvent> objects) {
        ArrayList<SimpleMessageEvent> simpleMessageEvents = new ArrayList<SimpleMessageEvent>();
        for (MessageParamEvent object : objects) {
            SimpleMessageEvent simpleMessageEvent = new SimpleMessageEvent();
            String dimensionValue = object.getDimensionValue();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.parseString(dimensionValue);
            String formSchemeKey = context.getParameterValue("formSchemeKey");
            String formKey = object.getFormKey();
            BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formKey, formKey);
            simpleMessageEvent.setBusinessKey(buildBusinessKey);
            simpleMessageEvent.setOperator(object.getOperator());
            simpleMessageEvent.setTask(object.getTask());
            simpleMessageEvents.add(simpleMessageEvent);
        }
        return simpleMessageEvents;
    }

    private SimpleMessageEvent transformToEvent(JobContext context, MessageParamEvent object) {
        SimpleMessageEvent simpleMessageEvent = new SimpleMessageEvent();
        String dimensionValue = object.getDimensionValue();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.parseString(dimensionValue);
        String formSchemeKey = context.getParameterValue("formSchemeKey");
        String formKey = object.getFormKey();
        BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formKey, formKey);
        simpleMessageEvent.setBusinessKey(buildBusinessKey);
        simpleMessageEvent.setOperator(object.getOperator());
        simpleMessageEvent.setTask(object.getTask());
        return simpleMessageEvent;
    }

    private void initMapperObject(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void sendMessage(SimpleMessageEvent object) {
        try {
            if (object instanceof SimpleMessageEvent) {
                SimpleMessageEvent message = object;
                String formSchemeKey = object.getBusinessKey().getFormSchemeKey();
                MessageData task = object.getTask();
                Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                Optional<UserTask> userTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), formSchemeKey);
                this.sendMessage.evaluateTodo(message.getTask(), userTask.get(), message.getBusinessKey(), message.getOperator());
            }
        }
        catch (Exception e) {
            logger.error("\u4ee3\u529e\u6d88\u606f\u53d1\u9001\u5f02\u5e38", e);
        }
    }
}

