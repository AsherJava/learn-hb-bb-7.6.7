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
public class CompleteReviewEventSource
implements WorkflowEventSource {
    public static final String KEY_OF_FORMULA_SCHEME_SOURCE = "formulaSchemeSource";
    public static final String KEY_OF_CURRENCY_SOURCE = "currencySource";
    public static final String KEY_OF_ERROR_HANDLE_SOURCE = "errorHandleSource";
    public static final String KEY_OF_IS_CONTAIN_CURRENCY = "isContainCurrency";
    @Autowired
    private WorkflowSettingsSourceUtil util;

    @Override
    public String getEventId() {
        return "complete-review-event";
    }

    @Override
    public Map<String, Object> getSource(String taskKey) {
        JSONObject reviewEventSource = new JSONObject();
        reviewEventSource.put(KEY_OF_FORMULA_SCHEME_SOURCE, this.util.buildFormulaSchemeSource(taskKey));
        reviewEventSource.put(KEY_OF_CURRENCY_SOURCE, this.util.buildCurrencySource(taskKey));
        reviewEventSource.put(KEY_OF_ERROR_HANDLE_SOURCE, this.util.buildErrorHandleSource());
        reviewEventSource.put(KEY_OF_IS_CONTAIN_CURRENCY, this.util.isContainCurrency(taskKey));
        return reviewEventSource.toMap();
    }
}

