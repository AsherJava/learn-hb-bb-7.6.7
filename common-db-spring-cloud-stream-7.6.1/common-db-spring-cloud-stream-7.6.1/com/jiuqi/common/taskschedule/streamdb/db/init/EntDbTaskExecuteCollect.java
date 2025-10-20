/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.init;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class EntDbTaskExecuteCollect {
    private static Map<String, Function> listenerNameToFunctionMap = new ConcurrentHashMap<String, Function>();

    public static Map<String, Function> getListenerNameToFunctionMap() {
        return listenerNameToFunctionMap;
    }

    public static void addListenerNameToFunctionMap(String name, Function function) {
        listenerNameToFunctionMap.put(name, function);
    }
}

