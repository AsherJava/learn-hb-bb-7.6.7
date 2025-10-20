/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import com.jiuqi.np.core.context.ContextExtension;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpContextExtension
implements ContextExtension {
    public static final Logger LOGGER = LoggerFactory.getLogger(NpContextExtension.class);
    private static final long serialVersionUID = -4191654614773342112L;
    private HashMap<String, Object> extensionMap;

    @Override
    public void put(String key, Serializable value) {
        if (this.extensionMap == null) {
            this.extensionMap = new HashMap();
        }
        if (null == key) {
            LOGGER.error("NpContext\u4e2d\u7981\u6b62key\u4e3anull\uff01value:" + null == value ? "" : value.toString());
            Stream.of(Thread.currentThread().getStackTrace()).forEach(k -> LOGGER.error(k.toString()));
            return;
        }
        this.extensionMap.put(key, value);
    }

    @Override
    public void remove(String key) {
        if (null != this.extensionMap) {
            this.extensionMap.remove(key);
        }
    }

    @Override
    public Object get(String key) {
        if (this.extensionMap == null) {
            return null;
        }
        return this.extensionMap.get(key);
    }

    @Override
    public void apply(Consumer<Map.Entry<String, Object>> consumer) {
        if (this.extensionMap == null) {
            this.extensionMap = new HashMap();
        }
        for (Map.Entry<String, Object> entry : this.extensionMap.entrySet()) {
            String key = entry.getKey();
            if (null == key) {
                Object value = entry.getValue();
                LOGGER.error("NpContext\u4e2d\u7981\u6b62key\u4e3anull\uff01value:" + null == value ? "" : value.toString());
                Stream.of(Thread.currentThread().getStackTrace()).forEach(k -> LOGGER.error(k.toString()));
                continue;
            }
            consumer.accept(entry);
        }
    }
}

