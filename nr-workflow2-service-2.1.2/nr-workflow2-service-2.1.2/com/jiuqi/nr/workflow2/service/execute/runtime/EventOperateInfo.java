/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;

public class EventOperateInfo
implements IEventOperateInfo {
    private String checkResultMessage;
    private Object checkResultDetail;
    private final WFMonitorCheckResult checkResult;

    public EventOperateInfo(WFMonitorCheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public EventOperateInfo(WFMonitorCheckResult checkResult, String checkResultMessage) {
        this(checkResult);
        this.checkResultMessage = checkResultMessage;
    }

    public EventOperateInfo(WFMonitorCheckResult checkResult, String checkResultMessage, Object checkResultDetail) {
        this(checkResult, checkResultMessage);
        this.checkResultDetail = checkResultDetail;
    }

    public WFMonitorCheckResult getCheckResult() {
        return this.checkResult;
    }

    public String getCheckResultMessage() {
        return this.checkResultMessage;
    }

    public Object getCheckResultDetail() {
        return this.checkResultDetail;
    }
}

