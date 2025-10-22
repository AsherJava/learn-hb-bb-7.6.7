/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BatchTaskExecuteFactoryMgr {
    private static final BatchTaskExecuteFactoryMgr instance = new BatchTaskExecuteFactoryMgr();
    private Map<String, IBatchTaskExecuteFactory> map = new HashMap<String, IBatchTaskExecuteFactory>();

    public static final BatchTaskExecuteFactoryMgr getInstance() {
        return instance;
    }

    public Iterator<IBatchTaskExecuteFactory> getAllFacories() {
        return this.map.values().iterator();
    }

    public void regFactory(IBatchTaskExecuteFactory factory) {
        if (factory == null || factory.getType() == null) {
            throw new NullPointerException();
        }
        this.map.put(factory.getType(), factory);
    }

    public IBatchTaskExecuteFactory findFactory(String type) {
        if (type == null) {
            throw new NullPointerException();
        }
        IBatchTaskExecuteFactory factory = this.map.get(type);
        return factory;
    }
}

