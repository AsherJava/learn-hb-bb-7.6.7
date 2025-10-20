/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import java.text.FieldPosition;

final class DontCareFieldPosition
extends FieldPosition {
    static final FieldPosition INSTANCE = new DontCareFieldPosition();

    private DontCareFieldPosition() {
        super(0);
    }
}

