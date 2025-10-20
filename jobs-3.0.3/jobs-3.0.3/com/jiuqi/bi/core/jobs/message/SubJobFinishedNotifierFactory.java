/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.core.ISubJobFinishedNotifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubJobFinishedNotifierFactory {
    private static SubJobFinishedNotifierFactory instance = new SubJobFinishedNotifierFactory();
    private Map<String, ISubJobFinishedNotifier> subjobNotifiers = new ConcurrentHashMap<String, ISubJobFinishedNotifier>();

    public static SubJobFinishedNotifierFactory getInstance() {
        return instance;
    }

    public void putSubjobNotifier(String instanceId, ISubJobFinishedNotifier notifier) {
        this.subjobNotifiers.put(instanceId, notifier);
    }

    public void removeSubjobNotifier(String instanceId) {
        this.subjobNotifiers.remove(instanceId);
    }

    public ISubJobFinishedNotifier getSubjobNotifier(String instanceId) {
        return this.subjobNotifiers.get(instanceId);
    }
}

