/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.job.NotificationBatchTaskExecutor;
import com.jiuqi.nr.workflow2.events.executor.job.NotificationSingleTaskExecutor;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class SendMessageNoticeEventExecutor
extends AbstractActionEventExecutor {
    public static final String KEY_OF_ENV_PARAM = "envParam";
    public static final String KEY_OF_EVENT_CONFIG = "eventConfig";
    public static final String KEY_OF_BUSINESS_OBJECT = "businessObject";
    public static final String KEY_OF_BUSINESS_OBJECT_LIST = "businessObjectList";
    public static final String KEY_OF_IS_SEND_MAIL = "isSendMail";
    public static final String KEY_OF_IS_TODO_ENABLED = "isTodoEnabled";
    private final AsyncTaskManager asyncTaskManager = (AsyncTaskManager)SpringBeanUtils.getBean(AsyncTaskManager.class);
    private final AsyncThreadExecutor asyncThreadExecutor = (AsyncThreadExecutor)SpringBeanUtils.getBean(AsyncThreadExecutor.class);

    public SendMessageNoticeEventExecutor(JSONObject eventJsonConfig) {
        super(eventJsonConfig);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) {
        boolean isSendMail = (Boolean)actionArgs.get(IProcessEventExecuteAttrKeys.SEND_MAIL.attrKey);
        boolean isTodoEnabled = (Boolean)actionArgs.get(IProcessEventExecuteAttrKeys.IS_TODO_ENABLED.attrKey);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap<String, Object> execParam = new HashMap<String, Object>();
            execParam.put(KEY_OF_ENV_PARAM, objectMapper.writeValueAsString((Object)this.getEnvParam(actionArgs)));
            execParam.put(KEY_OF_EVENT_CONFIG, this.eventJsonConfig.toString());
            execParam.put(KEY_OF_BUSINESS_OBJECT, objectMapper.writeValueAsString((Object)businessKey.getBusinessObject()));
            execParam.put(KEY_OF_IS_SEND_MAIL, isSendMail);
            execParam.put(KEY_OF_IS_TODO_ENABLED, isTodoEnabled);
            String args = objectMapper.writeValueAsString(execParam);
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(args);
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new NotificationSingleTaskExecutor());
            this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        }
        catch (JsonProcessingException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) {
        boolean isSendMail = (Boolean)actionArgs.get(IProcessEventExecuteAttrKeys.SEND_MAIL.attrKey);
        boolean isTodoEnabled = (Boolean)actionArgs.get(IProcessEventExecuteAttrKeys.IS_TODO_ENABLED.attrKey);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap<String, Object> execParam = new HashMap<String, Object>();
            execParam.put(KEY_OF_ENV_PARAM, objectMapper.writeValueAsString((Object)this.getEnvParam(actionArgs)));
            execParam.put(KEY_OF_EVENT_CONFIG, this.eventJsonConfig.toString());
            IBusinessObjectCollection businessObjectCollection = businessKeyCollection.getBusinessObjects();
            ArrayList<IBusinessObject> businessObjectList = new ArrayList<IBusinessObject>();
            for (IBusinessObject businessObject : businessObjectCollection) {
                businessObjectList.add(businessObject);
            }
            execParam.put(KEY_OF_BUSINESS_OBJECT_LIST, objectMapper.writeValueAsString(businessObjectList));
            execParam.put(KEY_OF_IS_SEND_MAIL, isSendMail);
            execParam.put(KEY_OF_IS_TODO_ENABLED, isTodoEnabled);
            String args = objectMapper.writeValueAsString(execParam);
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(args);
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new NotificationBatchTaskExecutor());
            this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        catch (JsonProcessingException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}

