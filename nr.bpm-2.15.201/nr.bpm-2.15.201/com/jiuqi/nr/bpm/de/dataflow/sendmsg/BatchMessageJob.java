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
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
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
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.BatchMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageParamEvent;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.impl.countersign.CountSignStartMode;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@RealTimeJob(group="BATCHSENDMESSAGE_JOB", groupTitle="\u53d1\u9001\u5f85\u529e\u6d88\u606f\u5373\u65f6\u4efb\u52a1")
public class BatchMessageJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BatchMessageJob.class);
    private ISendMessage sendMessage;
    private BusinessGenerator businessGenerator;

    public void execute(JobContext context) throws JobExecutionException {
        try {
            this.initParam();
            String parameterValue = context.getParameterValue("message");
            ObjectMapper mapper = new ObjectMapper();
            this.initMapperObject(mapper);
            List objects = (List)mapper.readValue(parameterValue, (TypeReference)new TypeReference<List<MessageParamEvent>>(){});
            List<BatchMessageEvent> batchMessageEvents = this.transformToEvent(context, objects);
            Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
            String todoVersion = environment.getProperty("jiuqi.nr.todo.version", "2.0");
            if (todoVersion.equals("1.0")) {
                HashMap<String, BatchMessageEvent> distinctMap = new HashMap<String, BatchMessageEvent>();
                for (BatchMessageEvent batchMessageEvent : batchMessageEvents) {
                    BusinessKey businessKey = batchMessageEvent.getBusinessKey();
                    String dimensionName = batchMessageEvent.getBusinessKey().getMasterEntity().getDimessionNames().iterator().next();
                    String unitId = businessKey.getMasterEntity().getMasterEntityKey(dimensionName);
                    if (distinctMap.containsKey(unitId)) {
                        String formOrGroupKey = batchMessageEvent.getFormOrGroupKeys().iterator().next();
                        ((BatchMessageEvent)distinctMap.get(unitId)).getFormOrGroupKeys().add(formOrGroupKey);
                        continue;
                    }
                    LinkedHashSet<String> newFormOrGroupKeys = new LinkedHashSet<String>();
                    if (batchMessageEvent.getFormOrGroupKeys() != null && batchMessageEvent.getFormOrGroupKeys().size() > 0) {
                        newFormOrGroupKeys.add(batchMessageEvent.getFormOrGroupKeys().iterator().next());
                    }
                    batchMessageEvent.setFormOrGroupKeys(newFormOrGroupKeys);
                    distinctMap.put(unitId, batchMessageEvent);
                }
                for (Map.Entry entry : distinctMap.entrySet()) {
                    this.sendMessage((BatchMessageEvent)entry.getValue());
                }
            } else {
                for (BatchMessageEvent batchMessageEvent : batchMessageEvents) {
                    this.sendMessage(batchMessageEvent);
                }
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
    }

    private BatchMessageEvent transformToEvent(JobContext context, MessageParamEvent object) {
        BatchMessageEvent batchMessageEvent = new BatchMessageEvent();
        String dimensionValue = object.getDimensionValue();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.parseString(dimensionValue);
        String formSchemeKey = context.getParameterValue("formSchemeKey");
        String formKey = object.getFormKey();
        BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formKey, formKey);
        batchMessageEvent.setBusinessKey(buildBusinessKey);
        batchMessageEvent.setOperator(object.getOperator());
        batchMessageEvent.setTask(object.getTask());
        batchMessageEvent.setActionCode(object.getActionCode());
        batchMessageEvent.setCanUploadUnitSize(object.getCanUploadUnitSize());
        batchMessageEvent.setContent(object.getContent());
        batchMessageEvent.setFormOrGroupKeys(object.getFromOrGroupKeys());
        batchMessageEvent.setSendMail(object.isSendMail());
        batchMessageEvent.setSignRoles(object.getSignRoles());
        return batchMessageEvent;
    }

    private List<BatchMessageEvent> transformToEvent(JobContext context, List<MessageParamEvent> objects) {
        ArrayList<BatchMessageEvent> batchMessageEvents = new ArrayList<BatchMessageEvent>();
        for (MessageParamEvent object : objects) {
            String dimensionValue = object.getDimensionValue();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.parseString(dimensionValue);
            String formSchemeKey = context.getParameterValue("formSchemeKey");
            Set<String> formOrGroupKeys = object.getFromOrGroupKeys();
            if (formOrGroupKeys == null || formOrGroupKeys.isEmpty()) {
                BatchMessageEvent batchMessageEvent = new BatchMessageEvent();
                BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, null, null);
                batchMessageEvent.setBusinessKey(buildBusinessKey);
                batchMessageEvent.setOperator(object.getOperator());
                batchMessageEvent.setTask(object.getTask());
                batchMessageEvent.setActionCode(object.getActionCode());
                batchMessageEvent.setCanUploadUnitSize(object.getCanUploadUnitSize());
                batchMessageEvent.setContent(object.getContent());
                batchMessageEvent.setFormOrGroupKeys(null);
                batchMessageEvent.setSendMail(object.isSendMail());
                batchMessageEvents.add(batchMessageEvent);
                continue;
            }
            for (String formOrGroupKey : formOrGroupKeys) {
                BatchMessageEvent batchMessageEvent = new BatchMessageEvent();
                BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formOrGroupKey, formOrGroupKey);
                batchMessageEvent.setBusinessKey(buildBusinessKey);
                batchMessageEvent.setOperator(object.getOperator());
                batchMessageEvent.setTask(object.getTask());
                batchMessageEvent.setActionCode(object.getActionCode());
                batchMessageEvent.setCanUploadUnitSize(object.getCanUploadUnitSize());
                batchMessageEvent.setContent(object.getContent());
                batchMessageEvent.setFormOrGroupKeys(Collections.singleton(formOrGroupKey));
                batchMessageEvent.setSendMail(object.isSendMail());
                batchMessageEvent.setSignRoles(object.getSignRoles());
                batchMessageEvents.add(batchMessageEvent);
            }
        }
        return batchMessageEvents;
    }

    private void initMapperObject(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void sendMessage(BatchMessageEvent object) {
        try {
            if (object instanceof BatchMessageEvent) {
                BatchMessageEvent message = object;
                NpContext context = NpContextHolder.getContext();
                ContextExtension signExtension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI);
                CountSignStartMode countSignStartMode = new CountSignStartMode();
                Set<String> signRoles = message.getSignRoles();
                if (signRoles != null && signRoles.size() > 0) {
                    countSignStartMode.setActors(signRoles);
                    signExtension.put(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI_VALUE, (Serializable)countSignStartMode);
                }
                this.sendMessage.send(message.getTask(), message.getBusinessKey(), message.getActionCode(), message.getContent(), message.isSendMail(), message.getCanUploadUnitSize(), message.getFormOrGroupKeys(), message.getOperator());
            }
        }
        catch (Exception e) {
            logger.error("\u4ee3\u529e\u6d88\u606f\u53d1\u9001\u5f02\u5e38", e);
        }
    }
}

