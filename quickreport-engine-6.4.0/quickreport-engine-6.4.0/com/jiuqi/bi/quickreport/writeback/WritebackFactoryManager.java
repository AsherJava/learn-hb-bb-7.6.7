/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.writeback.IWritebackExecutor;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WritebackFactoryManager {
    private static Map<String, WritebackFactory> factoryMap = new HashMap<String, WritebackFactory>();

    private WritebackFactoryManager() {
    }

    public static synchronized void registerFactory(String type, WritebackFactory factory) throws WritebackException {
        if (factoryMap.containsKey(type)) {
            throw new WritebackException("\u56de\u5199\u63a5\u53e3" + type + "\u5df2\u7ecf\u6ce8\u518c\uff0c\u4e0d\u53ef\u91cd\u590d\u6ce8\u518c\uff01");
        }
        factoryMap.put(type, factory);
    }

    public static synchronized void removeFactory(String type) throws WritebackException {
        if (!factoryMap.containsKey(type)) {
            throw new WritebackException("\u56de\u5199\u63a5\u53e3" + type + "\u672a\u6ce8\u518c\uff01");
        }
        factoryMap.remove(type);
    }

    public static synchronized WritebackFactory getFactory(String type) throws WritebackException {
        if (!factoryMap.containsKey(type)) {
            throw new WritebackException("\u56de\u5199\u63a5\u53e3" + type + "\u672a\u6ce8\u518c\uff01");
        }
        return factoryMap.get(type);
    }

    public static Collection<WritebackFactory> getAllFactory() {
        return factoryMap.values();
    }

    public static IWritebackExecutor createExecutror(String type, String tableName) throws WritebackException {
        WritebackFactory factory = WritebackFactoryManager.getFactory(type);
        return factory.createExecutor(tableName);
    }
}

