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
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectAllParentExecutor;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.StepByStepRejectForceControlExecutor;
import org.json.JSONObject;

public class StepByStepRejectEvent
extends ActionEventRegisteration {
    public static final String ID = "step-by-step-reject-event";
    public static final String TITLE = "\u5c42\u5c42\u9000\u56de";
    protected static final String attr_key_isStepByStepReturn = "StepbystepReturn";
    protected static final String attr_key_isReturnAllSuperior = "ReturnAllSuperior";

    public StepByStepRejectEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return TITLE;
    }

    public short getOrder() {
        return 60;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return StepByStepRejectEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                if (StepByStepRejectEvent.this.isRejectAllParent(eventJsonConfig)) {
                    return new StepByStepRejectAllParentExecutor(eventJsonConfig, helper);
                }
                if (StepByStepRejectEvent.this.isForeControlReject(eventJsonConfig)) {
                    return new StepByStepRejectForceControlExecutor(eventJsonConfig, helper);
                }
                return new StepByStepRejectEventExecutor(eventJsonConfig, helper);
            }
        };
    }

    private boolean isRejectAllParent(JSONObject eventJsonConfig) {
        if (eventJsonConfig.has(attr_key_isStepByStepReturn) && eventJsonConfig.has(attr_key_isReturnAllSuperior)) {
            return eventJsonConfig.getBoolean(attr_key_isStepByStepReturn) && eventJsonConfig.getBoolean(attr_key_isReturnAllSuperior);
        }
        return false;
    }

    private boolean isForeControlReject(JSONObject eventJsonConfig) {
        return false;
    }
}

