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
public class CompleteCalculationEventSource
implements WorkflowEventSource {
    public static final String KEY_OF_FORMULA_SCHEME_SOURCE = "formulaSchemeSource";
    @Autowired
    private WorkflowSettingsSourceUtil util;

    @Override
    public String getEventId() {
        return "complete-calculation-event";
    }

    @Override
    public Map<String, Object> getSource(String taskKey) {
        JSONObject calculateEventSource = new JSONObject();
        calculateEventSource.put(KEY_OF_FORMULA_SCHEME_SOURCE, this.util.buildFormulaSchemeSource(taskKey));
        return calculateEventSource.toMap();
    }
}

