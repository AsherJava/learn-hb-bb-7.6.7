/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import java.util.Objects;

public class EventOperateColumn
implements IEventOperateColumn {
    public static final String DEFAULT_OPERATE_COLUMN_NAME = "default-operate-column";
    public static final String DEFAULT_OPERATE_COLUMN_TITLE = "\u7f3a\u7701\u7684\u64cd\u4f5c\u7ed3\u679c";
    private final String columnName;
    private final String columnTitle;
    private String columnSettings;
    private EventExecutionAffect affectStatus = EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK;
    private CompleteDependentType completeDependentType = CompleteDependentType.NONE;

    public EventOperateColumn(String columnName, String columnTitle) {
        this.columnName = columnName;
        this.columnTitle = columnTitle;
    }

    public EventOperateColumn(String columnName, String columnTitle, EventExecutionAffect affectStatus) {
        this(columnName, columnTitle);
        this.affectStatus = affectStatus;
    }

    public EventOperateColumn(IUserActionEvent event) {
        this(event.getDefinitionId(), event.getAlias());
        this.columnSettings = event.getSettings();
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public String getColumnTitle() {
        return this.columnTitle;
    }

    @Override
    public String getColumnSettings() {
        return this.columnSettings;
    }

    public void setColumnSettings(String columnSettings) {
        this.columnSettings = columnSettings;
    }

    @Override
    public EventExecutionAffect getAffectStatus() {
        return this.affectStatus;
    }

    public void setAffectStatus(EventExecutionAffect affectStatus) {
        this.affectStatus = affectStatus;
    }

    @Override
    public CompleteDependentType getCompleteDependentType() {
        return this.completeDependentType;
    }

    public void setCompleteDependentType(CompleteDependentType completeDependentType) {
        this.completeDependentType = completeDependentType;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EventOperateColumn that = (EventOperateColumn)o;
        return Objects.equals(this.columnName, that.columnName);
    }

    public int hashCode() {
        return Objects.hashCode(this.columnName);
    }
}

