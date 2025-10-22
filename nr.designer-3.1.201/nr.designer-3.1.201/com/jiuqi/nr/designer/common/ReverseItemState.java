/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReverseItemState {
    DEFAULT(1),
    NEW(2),
    UPDATE(3),
    DELETE(4);

    private int state;

    private ReverseItemState(int state) {
        this.state = state;
    }

    @JsonValue
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

