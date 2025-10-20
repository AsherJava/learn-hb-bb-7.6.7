/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.util;

import java.util.Objects;

@FunctionalInterface
public interface NoParametersConsumer {
    public void accept();

    default public NoParametersConsumer andThen(NoParametersConsumer after) {
        Objects.requireNonNull(after);
        return () -> {
            this.accept();
            after.accept();
        };
    }
}

