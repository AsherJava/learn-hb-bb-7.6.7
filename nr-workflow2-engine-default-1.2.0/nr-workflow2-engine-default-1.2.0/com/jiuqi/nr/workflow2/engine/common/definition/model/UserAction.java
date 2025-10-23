/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction$ReportDirection
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.List;
import java.util.Map;

public class UserAction
implements IUserAction {
    public static final String P_SUPPORT_COMMENT = "SUPPORT_COMMENT";
    public static final String P_COMMENT_NOTNULL = "COMMENT_NOTNULL";
    public static final String P_SUPPORT_RETURN_TYPE = "SUPPORT_RETURN_TYPE";
    public static final String P_RETURN_TYPE_ENTITY = "RETURN_TYPE_ENTITY";
    public static final String P_ENABLE_FORCE_REPORT = "ENABLE_FORCE_REPORT";
    public static final String A_COMMENT = "COMMENT";
    public static final String A_RETURN_TYPE = "RETURN_TYPE";
    public static final String A_FORCE_REPORT = "FORCE_REPORT";
    UserActionTemplate actionTemplate;
    String alias;
    List<IUserActionEvent> previousEvents;
    List<IUserActionEvent> postEvents;
    Map<String, Object> properties;

    UserAction() {
    }

    public String getCode() {
        return this.actionTemplate.getCode();
    }

    public String getTitle() {
        return this.actionTemplate.getTitle();
    }

    public String getIcon() {
        return this.actionTemplate.getIcon();
    }

    public String getAlias() {
        return this.alias;
    }

    public IUserAction.ReportDirection getReportDirection() {
        return this.actionTemplate.getReportDirection();
    }

    public List<IUserActionEvent> getPreviousEvents() {
        return this.previousEvents;
    }

    public List<IUserActionEvent> getPostEvents() {
        return this.postEvents;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }
}

