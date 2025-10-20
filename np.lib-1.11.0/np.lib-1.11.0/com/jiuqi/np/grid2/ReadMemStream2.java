/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.StreamException2;

public class ReadMemStream2
extends MemStream2 {
    private long sizeCache = 0L;

    @Override
    public long getSize() throws StreamException2 {
        return this.sizeCache > 0L ? this.sizeCache : (this.sizeCache = super.getSize());
    }
}

