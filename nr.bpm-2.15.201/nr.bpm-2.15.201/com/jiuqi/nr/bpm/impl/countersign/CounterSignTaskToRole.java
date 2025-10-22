/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.countersign;

import java.io.Serializable;
import java.util.Map;

public class CounterSignTaskToRole
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, String> taskToRoleKey;

    public Map<String, String> getTaskToRoleKey() {
        return this.taskToRoleKey;
    }

    public void setTaskToRoleKey(Map<String, String> taskToRoleKey) {
        this.taskToRoleKey = taskToRoleKey;
    }
}

