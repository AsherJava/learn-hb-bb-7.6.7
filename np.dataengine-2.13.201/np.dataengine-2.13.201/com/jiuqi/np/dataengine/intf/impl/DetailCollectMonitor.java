/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.ArrayList;
import java.util.List;

public class DetailCollectMonitor
extends AbstractMonitor {
    private List<String> messages = new ArrayList<String>();

    @Override
    public void message(String msg, Object sender) {
        if (sender instanceof IReportFunction) {
            this.messages.add(msg);
        }
    }

    @Override
    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }

    @Override
    public boolean isOutFMLPlan() {
        return false;
    }

    @Override
    public boolean isDebug() {
        return true;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        this.messages.forEach(msg -> buff.append((String)msg).append("\n"));
        return buff.toString();
    }
}

