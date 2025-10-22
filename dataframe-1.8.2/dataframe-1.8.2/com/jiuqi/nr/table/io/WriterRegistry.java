/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.io.DataWriter;
import com.jiuqi.nr.table.io.WriteOptions;
import java.util.HashMap;
import java.util.Map;

public class WriterRegistry {
    private final Map<String, DataWriter<?>> optionTypesRegistry = new HashMap();
    private final Map<String, DataWriter<?>> extensionsRegistry = new HashMap();

    public void registerOptions(Class<? extends WriteOptions> optionsType, DataWriter<?> writer) {
        this.optionTypesRegistry.put(optionsType.getCanonicalName(), writer);
    }

    public void registerExtension(String extension, DataWriter<?> writer) {
        this.extensionsRegistry.put(extension, writer);
    }

    public <T extends WriteOptions> DataWriter<T> getWriterForOptions(T options) {
        return this.optionTypesRegistry.get(options.getClass().getCanonicalName());
    }

    public DataWriter<?> getWriterForExtension(String extension) {
        return this.extensionsRegistry.get(extension);
    }
}

