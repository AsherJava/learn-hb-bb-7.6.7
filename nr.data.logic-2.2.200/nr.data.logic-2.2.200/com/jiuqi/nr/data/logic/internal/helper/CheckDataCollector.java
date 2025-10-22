/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.nr.data.logic.internal.obj.CheckData;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CheckDataCollector {
    private final Map<String, CheckData> cache = new ConcurrentHashMap<String, CheckData>();

    public static CheckDataCollector getInstance() {
        return CheckResultInstance.INSTANCE;
    }

    public CheckData get(String executeId) {
        if (executeId == null) {
            return null;
        }
        return this.cache.get(executeId);
    }

    public void put(String executeId, CheckData checkData) {
        this.cache.put(executeId, checkData);
    }

    public void remove(String executeId) {
        this.cache.remove(executeId);
    }

    private static class CheckResultInstance {
        private static final CheckDataCollector INSTANCE = new CheckDataCollector();

        private CheckResultInstance() {
        }
    }
}

