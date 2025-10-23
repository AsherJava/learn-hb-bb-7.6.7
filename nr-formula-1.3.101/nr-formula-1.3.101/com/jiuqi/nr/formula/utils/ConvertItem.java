/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils;

import java.util.function.Function;

public class ConvertItem<K, V> {
    private final String source;
    private final Function<K, V> sourceToTargetFunction;

    public ConvertItem(String source) {
        this.source = source;
        this.sourceToTargetFunction = null;
    }

    public ConvertItem(String source, Function<K, V> sourceToTargetFunction) {
        this.source = source;
        this.sourceToTargetFunction = sourceToTargetFunction;
    }

    public String getSource() {
        return this.source;
    }

    public Function<K, V> getSourceToTargetFunction() {
        return this.sourceToTargetFunction;
    }
}

