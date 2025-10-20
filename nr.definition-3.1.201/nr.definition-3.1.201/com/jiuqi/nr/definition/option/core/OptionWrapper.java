/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import java.util.NoSuchElementException;

public class OptionWrapper {
    private final String value;
    private final boolean exit;

    private OptionWrapper() {
        this.value = null;
        this.exit = false;
    }

    private OptionWrapper(String value) {
        this.value = value;
        this.exit = true;
    }

    public String get() {
        if (!this.exit) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public boolean isExit() {
        return this.exit;
    }

    public static OptionWrapper empty() {
        return new OptionWrapper();
    }

    public static OptionWrapper of(String value) {
        return new OptionWrapper(value);
    }
}

