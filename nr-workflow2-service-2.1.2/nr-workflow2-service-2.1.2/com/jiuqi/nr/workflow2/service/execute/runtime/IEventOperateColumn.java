/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateColumn;

public interface IEventOperateColumn {
    public static final IEventOperateColumn DEF_OPT_COLUMN = new EventOperateColumn("default-operate-column", "\u7f3a\u7701\u7684\u64cd\u4f5c\u7ed3\u679c", EventExecutionAffect.IMPACT_REPORTING_CHECK);

    public String getColumnName();

    public String getColumnTitle();

    public String getColumnSettings();

    public EventExecutionAffect getAffectStatus();

    public CompleteDependentType getCompleteDependentType();
}

