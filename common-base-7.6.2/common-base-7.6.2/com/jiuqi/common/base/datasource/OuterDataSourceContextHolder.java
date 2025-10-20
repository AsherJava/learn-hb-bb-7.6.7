/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.datasource;

public class OuterDataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal();

    public static String getDataSourceCode() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDataSourceCode(String dataSourceCode) {
        CONTEXT_HOLDER.set(dataSourceCode);
    }

    public static void clearDataSourceCode() {
        CONTEXT_HOLDER.remove();
    }
}

