/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.inf;

import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.RouterRedirect;
import java.util.Map;

public interface MonitorScene {
    public MonitorSceneEnum getMonitorScene();

    public MonitorStateEnum getState(MonitorArgument var1);

    public Map<String, MonitorStateEnum> getStates(MonitorArgument var1);

    public RouterRedirect getURL(MonitorArgument var1);

    public int getOrder();
}

