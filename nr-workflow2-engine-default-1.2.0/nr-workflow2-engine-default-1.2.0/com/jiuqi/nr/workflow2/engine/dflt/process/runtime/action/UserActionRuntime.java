/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction$ReportDirection
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActionRuntime
implements IUserAction {
    private final IUserAction action;
    private final Map<String, Object> runtimeProperties;

    UserActionRuntime(IUserAction action, Map<String, Object> runtimeProperties) {
        this.action = action;
        if (runtimeProperties == null || runtimeProperties.isEmpty()) {
            this.runtimeProperties = action.getProperties();
        } else {
            this.runtimeProperties = new HashMap<String, Object>(action.getProperties());
            this.runtimeProperties.putAll(action.getProperties());
            this.runtimeProperties.putAll(runtimeProperties);
        }
    }

    public String getCode() {
        return this.action.getCode();
    }

    public String getTitle() {
        return this.action.getTitle();
    }

    public String getAlias() {
        return this.action.getAlias();
    }

    public IUserAction.ReportDirection getReportDirection() {
        return this.action.getReportDirection();
    }

    public List<IUserActionEvent> getPreviousEvents() {
        return this.action.getPreviousEvents();
    }

    public List<IUserActionEvent> getPostEvents() {
        return this.action.getPostEvents();
    }

    public Map<String, Object> getProperties() {
        return this.runtimeProperties;
    }

    public String getIcon() {
        return this.action.getIcon();
    }
}

