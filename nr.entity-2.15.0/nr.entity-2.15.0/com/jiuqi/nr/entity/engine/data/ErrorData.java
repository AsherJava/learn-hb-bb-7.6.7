/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.nr.entity.engine.data.AbstractData;

public final class ErrorData
extends AbstractData {
    private static final long serialVersionUID = -370547710315016217L;
    public static final ErrorData ERROR = new ErrorData();

    public ErrorData() {
        super(-1, false);
    }

    @Override
    public String getAsString() {
        return "ERROR";
    }

    public int hashCode() {
        return this.isNull ? 8 : 16;
    }
}

