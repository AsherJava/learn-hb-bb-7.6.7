/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition$Level
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
import com.jiuqi.nr.workflow2.events.executor.StepByStepCancelConfirmExecutor;
import org.json.JSONObject;

public class StepByStepCancelConfirmEvent
extends ActionEventRegisteration {
    public static final String ID = "step-by-step-cancel-confirm-event";
    public static final String TITLE = "\u53d6\u6d88\u786e\u8ba4\uff0c\u5c42\u5c42\u68c0\u67e5\u4e8b\u4ef6";

    public StepByStepCancelConfirmEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return super.getDescription();
    }

    public IActionEventDefinition.Level getLevel() {
        return IActionEventDefinition.Level.SYSTEM;
    }

    public short getOrder() {
        return super.getOrder();
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return StepByStepCancelConfirmEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                return new StepByStepCancelConfirmExecutor(eventJsonConfig, helper);
            }
        };
    }
}

