/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.action.env;

import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import java.util.HashMap;
import java.util.Map;

public class GcActionItemEnv {
    private Map<String, Object> envItems;
    private AbstractGcActionItem action;
    private String actionParam;

    public GcActionItemEnv(AbstractGcActionItem action, String actionParamJson) {
        this.action = action;
        this.actionParam = actionParamJson;
    }

    public Map<String, Object> getEnvItems() {
        return this.envItems;
    }

    public Object getInfo(String envItemKey) {
        if (this.envItems == null) {
            return null;
        }
        return this.envItems.get(envItemKey);
    }

    public Object putInfo(String envItemKey, Object envItemValue) {
        if (this.envItems == null) {
            this.envItems = new HashMap<String, Object>();
        }
        return this.envItems.put(envItemKey, envItemValue);
    }

    public AbstractGcActionItem getAction() {
        return this.action;
    }

    public String getActionParam() {
        return this.actionParam;
    }
}

