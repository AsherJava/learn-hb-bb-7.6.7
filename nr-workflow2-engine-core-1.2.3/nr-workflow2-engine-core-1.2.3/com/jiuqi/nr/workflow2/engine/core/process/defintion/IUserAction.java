/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.defintion;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import java.util.List;
import java.util.Map;

public interface IUserAction {
    public static final String START_ACTION = "start";

    public String getCode();

    public String getTitle();

    public String getAlias();

    public ReportDirection getReportDirection();

    public List<IUserActionEvent> getPreviousEvents();

    public List<IUserActionEvent> getPostEvents();

    public Map<String, Object> getProperties();

    default public String getIcon() {
        return null;
    }

    public static enum ReportDirection {
        REPORT_UPWARD,
        REJECT_DOWNWARD,
        OTHER;

    }
}

