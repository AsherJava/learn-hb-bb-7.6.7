/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition$ExecutionTiming
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.SendMessageNoticeEventExecutor;
import org.json.JSONObject;

public class SendMessageNoticeEvent
extends ActionEventRegisteration {
    public static final String ID = "send-message-notice-event";
    public static final String TITLE = "\u901a\u77e5";
    public static final String KEY_OF_USER_SELECTABLE = "userSelectable";

    public SendMessageNoticeEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return "\u6d41\u7a0b\u6d41\u8f6c\u5b8c\u6210\u540e\uff0c\u53d1\u5e03\u6d88\u606f\u901a\u77e5\uff01";
    }

    public short getOrder() {
        return 1000;
    }

    public IActionEventDefinition.ExecutionTiming getExecutionTiming() {
        return IActionEventDefinition.ExecutionTiming.POST_ACTION;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return SendMessageNoticeEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                return new SendMessageNoticeEventExecutor(eventJsonConfig);
            }
        };
    }
}

