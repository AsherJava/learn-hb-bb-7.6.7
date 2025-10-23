/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.events.executor.CompleteCalculationEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import org.json.JSONObject;

public class CompleteCalculationEvent
extends ActionEventRegisteration {
    public static final String ID = "complete-calculation-event";
    public static final String TITLE = "\u8fd0\u7b97";

    public CompleteCalculationEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return "\u6267\u884c\u5168\u7b97\uff0c\u6279\u91cf\u5168\u7b97";
    }

    public short getOrder() {
        return 10;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return CompleteCalculationEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                return new CompleteCalculationEventExecutor(eventJsonConfig, helper);
            }
        };
    }
}

