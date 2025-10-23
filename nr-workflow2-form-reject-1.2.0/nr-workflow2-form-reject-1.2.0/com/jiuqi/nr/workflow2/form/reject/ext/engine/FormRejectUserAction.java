/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction$ReportDirection
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.form.reject.ext.engine;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormRejectUserAction
implements IUserAction {
    private final IUserAction userRejectAction;
    private final Map<String, Object> properties = new HashMap<String, Object>();

    public FormRejectUserAction(IUserAction userRejectAction) {
        this.userRejectAction = userRejectAction;
        this.properties.putAll(userRejectAction.getProperties());
    }

    public String getCode() {
        return this.userRejectAction.getCode();
    }

    public String getTitle() {
        return this.userRejectAction.getTitle();
    }

    public String getAlias() {
        return this.userRejectAction.getAlias();
    }

    public IUserAction.ReportDirection getReportDirection() {
        return this.userRejectAction.getReportDirection();
    }

    public List<IUserActionEvent> getPreviousEvents() {
        return this.userRejectAction.getPreviousEvents();
    }

    public List<IUserActionEvent> getPostEvents() {
        return this.userRejectAction.getPostEvents();
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public String getIcon() {
        return this.userRejectAction.getIcon();
    }
}

