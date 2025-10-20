/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActionRequest {
    private Map<String, Object> params;
    private Map<String, Object> context = new HashMap<String, Object>();

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = Collections.unmodifiableMap(params);
    }

    public Map<String, Object> getContext() {
        return this.context;
    }
}

