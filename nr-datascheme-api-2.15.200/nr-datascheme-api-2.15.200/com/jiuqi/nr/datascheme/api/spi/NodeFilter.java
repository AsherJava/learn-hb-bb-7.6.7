/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.datascheme.api.spi;

import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import java.util.Objects;
import java.util.function.Predicate;
import javax.validation.constraints.NotNull;

public interface NodeFilter
extends Predicate<ISchemeNode> {
    @NotNull
    default public NodeFilter and(@NotNull Predicate<? super ISchemeNode> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) && other.test((ISchemeNode)t);
    }

    @NotNull
    default public NodeFilter negate() {
        return t -> !this.test(t);
    }

    @NotNull
    default public NodeFilter or(@NotNull Predicate<? super ISchemeNode> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) || other.test((ISchemeNode)t);
    }
}

