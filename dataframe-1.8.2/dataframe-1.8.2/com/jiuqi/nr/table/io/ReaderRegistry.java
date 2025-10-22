/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.io.DataReader;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReaderRegistry {
    private final Map<String, DataReader<?>> optionTypesRegistry = new HashMap();
    private final Map<String, DataReader<?>> extensionsRegistry = new HashMap();
    private final Map<String, DataReader<?>> mimeTypesRegistry = new HashMap();

    public void registerOptions(Class<? extends ReadOptions> optionsType, DataReader<?> reader) {
        this.optionTypesRegistry.put(optionsType.getCanonicalName(), reader);
    }

    public void registerExtension(String extension, DataReader<?> reader) {
        this.extensionsRegistry.put(extension, reader);
    }

    public void registerMimeType(String mimeType, DataReader<?> reader) {
        this.mimeTypesRegistry.put(mimeType, reader);
    }

    public <T extends ReadOptions> DataReader<T> getReaderForOptions(T options) {
        String clazz = options.getClass().getCanonicalName();
        DataReader<?> reader = this.optionTypesRegistry.get(clazz);
        if (reader == null) {
            throw new IllegalArgumentException("No reader registered for class " + clazz);
        }
        return reader;
    }

    public Optional<DataReader<?>> getReaderForExtension(String extension) {
        return Optional.ofNullable(this.extensionsRegistry.get(extension));
    }

    public Optional<DataReader<?>> getReaderForMimeType(String mimeType) {
        return Optional.ofNullable(this.mimeTypesRegistry.get(mimeType));
    }
}

