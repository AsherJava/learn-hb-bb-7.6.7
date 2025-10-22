/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean.impl;

import com.jiuqi.nr.dataentry.bean.facade.FDimensionState;

public class DimensionStateImpl
implements FDimensionState {
    private String name;
    private boolean write;

    public DimensionStateImpl() {
    }

    public DimensionStateImpl(String name, boolean write) {
        this.name = name;
        this.write = write;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isWrite() {
        return this.write;
    }

    @Override
    public void setWrite(boolean write) {
        this.write = write;
    }
}

