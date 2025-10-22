/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param.cksr;

import java.util.Arrays;
import java.util.Optional;

public enum CheckStatus {
    SUCCESS(1),
    FAIL(2);

    private final int value;

    private CheckStatus(int value) {
        this.value = value;
    }

    public static CheckStatus getByValue(int value) {
        Optional<CheckStatus> any = Arrays.stream(CheckStatus.class.getEnumConstants()).filter(e -> e.getValue() == value).findAny();
        return any.orElse(null);
    }

    public int getValue() {
        return this.value;
    }
}

