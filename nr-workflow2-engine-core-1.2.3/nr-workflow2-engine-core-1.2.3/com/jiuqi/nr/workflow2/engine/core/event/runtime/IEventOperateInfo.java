/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;

public interface IEventOperateInfo {
    public WFMonitorCheckResult getCheckResult();

    public String getCheckResultMessage();

    public Object getCheckResultDetail();
}

