/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util;

import org.springframework.core.NamedInheritableThreadLocal;

public class DynamicDataSourceContextHolder {
    private static final NamedInheritableThreadLocal<String> DYNAMIC_DATA_SOURCE_KEY = new NamedInheritableThreadLocal("dynamic-data-source-key");

    public static String getDataSourceKey() {
        return (String)DYNAMIC_DATA_SOURCE_KEY.get();
    }

    public static void setDataSourceKey(String dataSourceKey) {
        DYNAMIC_DATA_SOURCE_KEY.set(dataSourceKey);
    }

    public static void clear() {
        DYNAMIC_DATA_SOURCE_KEY.remove();
    }
}

