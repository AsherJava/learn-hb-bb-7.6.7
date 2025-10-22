/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.internal.impl.MouldDefineImpl;
import java.util.List;

public class MouldDataDefineImpl {
    private int type;
    private List<MouldDefineImpl> data;
    private final int MANUAL = 0;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MouldDefineImpl> getData() {
        return this.data;
    }

    public void setData(List<MouldDefineImpl> data) {
        this.data = data;
    }
}

