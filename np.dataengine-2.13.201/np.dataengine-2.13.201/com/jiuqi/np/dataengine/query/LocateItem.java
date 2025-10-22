/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

public class LocateItem {
    private boolean hasLocate;
    private int position;

    public LocateItem(boolean hasLocate, int position) {
        this.setHasLocate(hasLocate);
        this.setPosition(position);
    }

    public boolean getHasLocate() {
        return this.hasLocate;
    }

    public void setHasLocate(boolean hasLocate) {
        this.hasLocate = hasLocate;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

