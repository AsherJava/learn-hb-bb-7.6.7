/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

import java.util.HashSet;
import java.util.Set;

public class ExcelWriteInfo {
    private double progress;
    private boolean haveData;
    private final Set<String> expDws = new HashSet<String>();
    private int state = 1;

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isHaveData() {
        return this.haveData;
    }

    public void setHaveData(boolean haveData) {
        this.haveData = haveData;
    }

    public Set<String> getExpDws() {
        return this.expDws;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

