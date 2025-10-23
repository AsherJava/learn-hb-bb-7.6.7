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
import com.jiuqi.nr.workflow2.events.executor.CheckUnitNodeEventExecutorConverter;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import org.json.JSONObject;

public class CheckUnitNodeEvent
extends ActionEventRegisteration {
    public static final String ID = "check-unit-node-event";
    public static final String TITLE = "\u8282\u70b9\u68c0\u67e5";

    public CheckUnitNodeEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return "\u6267\u884c\u5355\u4f4d\u8282\u70b9\u68c0\u67e5";
    }

    public short getOrder() {
        return 20;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return CheckUnitNodeEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                return new CheckUnitNodeEventExecutorConverter(eventJsonConfig, helper);
            }
        };
    }
}

