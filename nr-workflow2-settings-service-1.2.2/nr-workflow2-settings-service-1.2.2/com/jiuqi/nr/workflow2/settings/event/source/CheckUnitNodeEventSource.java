/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.settings.event.source;

import com.jiuqi.nr.workflow2.settings.event.source.WorkflowEventSource;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsSourceUtil;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckUnitNodeEventSource
implements WorkflowEventSource {
    public static final String KEY_OF_IS_CONTAIN_CURRENCY = "isContainCurrency";
    public static final String KEY_OF_CHECK_CURRENCY = "checkCurrency";
    @Autowired
    private WorkflowSettingsSourceUtil util;

    @Override
    public String getEventId() {
        return "check-unit-node-event";
    }

    @Override
    public Map<String, Object> getSource(String taskKey) {
        JSONObject nodeCheckEventSource = new JSONObject();
        nodeCheckEventSource.put(KEY_OF_IS_CONTAIN_CURRENCY, this.util.isContainCurrency(taskKey));
        nodeCheckEventSource.put(KEY_OF_CHECK_CURRENCY, this.util.buildCurrencySource(taskKey));
        return nodeCheckEventSource.toMap();
    }
}

