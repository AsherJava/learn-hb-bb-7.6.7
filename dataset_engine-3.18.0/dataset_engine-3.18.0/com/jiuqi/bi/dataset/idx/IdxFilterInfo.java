/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.idx;

import java.util.List;

public final class IdxFilterInfo {
    public final int colIdx;
    public final List<Object> value;

    public IdxFilterInfo(int colIdx, List<Object> value) {
        this.colIdx = colIdx;
        this.value = value;
    }
}

