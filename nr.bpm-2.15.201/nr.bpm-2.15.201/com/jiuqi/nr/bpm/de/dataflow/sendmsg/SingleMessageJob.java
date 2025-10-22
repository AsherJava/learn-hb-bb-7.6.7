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
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageParamEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SingleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.impl.countersign.CountSignStartMode;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="SINGLEMESSAGE_JOB", groupTitle="\u53d1\u9001\u5f85\u529e\u7b80\u5355\u6d88\u606f\u5373\u65f6\u4efb\u52a1")
public class SingleMessageJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(SingleMessageJob.class);
    private ISendMessage sendMessage;
    private BusinessGenerator businessGenerator;

    public void execute(JobContext context) throws JobExecutionException {
        try {
            this.initParam();
            String parameterValue = context.getParameterValue("message");
            ObjectMapper mapper = new ObjectMapper();
            this.initMapperObject(mapper);
            List objects = (List)mapper.readValue(parameterValue, (TypeReference)new TypeReference<List<MessageParamEvent>>(){});
            List<SingleMessageEvent> singleMessageEvent = this.transformToEvent(context, objects);
            for (SingleMessageEvent singleMessageEvent2 : singleMessageEvent) {
                this.sendMessage(singleMessageEvent2);
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

    private SingleMessageEvent transformToEvent(JobContext context, MessageParamEvent object) {
        SingleMessageEvent singleMessageEvent = new SingleMessageEvent();
        String dimensionValue = object.getDimensionValue();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.parseString(dimensionValue);
        String formSchemeKey = context.getParameterValue("formSchemeKey");
        String formKey = object.getFormKey();
        BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formKey, formKey);
        singleMessageEvent.setBusinessKey(buildBusinessKey);
        singleMessageEvent.setOperator(object.getOperator());
        singleMessageEvent.setTask(object.getTask());
        singleMessageEvent.setActionCode(object.getActionCode());
        singleMessageEvent.setContent(object.getContent());
        singleMessageEvent.setSendMail(object.isSendMail());
        singleMessageEvent.setSignRoles(object.getSignRoles());
        return singleMessageEvent;
    }

    private List<SingleMessageEvent> transformToEvent(JobContext context, List<MessageParamEvent> objects) {
        ArrayList<SingleMessageEvent> singleMessageEvents = new ArrayList<SingleMessageEvent>();
        for (MessageParamEvent object : objects) {
            SingleMessageEvent singleMessageEvent = new SingleMessageEvent();
            String dimensionValue = object.getDimensionValue();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.parseString(dimensionValue);
            String formSchemeKey = context.getParameterValue("formSchemeKey");
            String formKey = object.getFormKey();
            BusinessKey buildBusinessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, formKey, formKey);
            singleMessageEvent.setBusinessKey(buildBusinessKey);
            singleMessageEvent.setOperator(object.getOperator());
            singleMessageEvent.setTask(object.getTask());
            singleMessageEvent.setActionCode(object.getActionCode());
            singleMessageEvent.setContent(object.getContent());
            singleMessageEvent.setSendMail(object.isSendMail());
            singleMessageEvent.setSignRoles(object.getSignRoles());
            singleMessageEvents.add(singleMessageEvent);
        }
        return singleMessageEvents;
    }

    private void initMapperObject(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void sendMessage(SingleMessageEvent object) {
        try {
            if (object instanceof SingleMessageEvent) {
                SingleMessageEvent message = object;
                MessageData task = message.getTask();
                NpContext context = NpContextHolder.getContext();
                ContextExtension signExtension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI);
                CountSignStartMode countSignStartMode = new CountSignStartMode();
                Set<String> signRoles = message.getSignRoles();
                if (signRoles != null && signRoles.size() > 0) {
                    countSignStartMode.setActors(signRoles);
                    signExtension.put(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI_VALUE, (Serializable)countSignStartMode);
                }
                this.sendMessage.send(task, message.getBusinessKey(), message.getActionCode(), message.getContent(), message.isSendMail(), message.getOperator());
            }
        }
        catch (Exception e) {
            logger.error("\u4ee3\u529e\u6d88\u606f\u53d1\u9001\u5f02\u5e38", e);
        }
    }
}

