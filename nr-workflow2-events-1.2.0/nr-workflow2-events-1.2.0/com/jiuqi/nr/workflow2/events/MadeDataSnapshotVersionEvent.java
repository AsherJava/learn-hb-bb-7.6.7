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
import com.jiuqi.nr.workflow2.events.executor.MadeDataSnapshotVersionEventExecutor;
import org.json.JSONObject;

public class MadeDataSnapshotVersionEvent
extends ActionEventRegisteration {
    public static final String ID = "made-data-snapshot-version-event";
    public static final String TITLE = "\u751f\u6210\u6570\u636e\u7248\u672c";

    public MadeDataSnapshotVersionEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return "\u751f\u6210\u8868\u5355\u6570\u636e\u7684\u5feb\u7167\u7248\u672c";
    }

    public short getOrder() {
        return 50;
    }

    public IActionEventDefinition.ExecutionTiming getExecutionTiming() {
        return IActionEventDefinition.ExecutionTiming.POST_ACTION;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return MadeDataSnapshotVersionEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                return new MadeDataSnapshotVersionEventExecutor(eventJsonConfig, helper);
            }
        };
    }
}

