/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import java.util.HashMap;
import java.util.Map;

public class SimpleJobAnnotationHolder {
    private static SimpleJobAnnotationHolder instance;
    private Map<String, String> map = new HashMap<String, String>();

    private SimpleJobAnnotationHolder() {
    }

    public static SimpleJobAnnotationHolder getInstance() {
        if (instance == null) {
            instance = new SimpleJobAnnotationHolder();
        }
        return instance;
    }

    public void register(String className) {
        this.map.put(className, className);
    }

    public boolean exist(String className) {
        return this.map.containsKey(className);
    }
}

