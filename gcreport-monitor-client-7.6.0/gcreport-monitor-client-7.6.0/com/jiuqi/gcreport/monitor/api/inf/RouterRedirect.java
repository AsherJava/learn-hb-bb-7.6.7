/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.inf;

import java.util.Map;

public class RouterRedirect {
    private String routerName;
    private Map<String, String> params;

    public RouterRedirect(String routerName, Map<String, String> params) {
        this.routerName = routerName;
        this.params = params;
    }

    public String getRouterName() {
        return this.routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}

