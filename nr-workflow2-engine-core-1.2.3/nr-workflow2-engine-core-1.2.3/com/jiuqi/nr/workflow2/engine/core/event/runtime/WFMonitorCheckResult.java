/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

public enum WFMonitorCheckResult {
    UN_CHECK,
    CHECK_PASS,
    CHECK_UN_PASS;


    public static WFMonitorCheckResult valueOfName(String name) {
        for (WFMonitorCheckResult value : WFMonitorCheckResult.values()) {
            if (!value.name().equals(name)) continue;
            return value;
        }
        return null;
    }
}

