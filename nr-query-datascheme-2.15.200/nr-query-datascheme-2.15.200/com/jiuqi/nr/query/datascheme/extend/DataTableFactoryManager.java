/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataTableFactoryManager {
    private static final DataTableFactoryManager _instance = new DataTableFactoryManager();
    private Map<String, IDataTableFactory> factories = new HashMap<String, IDataTableFactory>();

    private DataTableFactoryManager() {
    }

    public static DataTableFactoryManager getInstance() {
        return _instance;
    }

    public void registerFactory(IDataTableFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    public void unregisterFactory(IDataTableFactory factory) {
        this.factories.remove(factory.getType());
    }

    public IDataTableFactory getFactory(String type) {
        return this.factories.get(type);
    }

    public Iterator<IDataTableFactory> iterator() {
        return this.factories.values().iterator();
    }
}

