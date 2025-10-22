/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.print;

public class FileDataBlockPtr {
    private int privateBlockStartPos;
    private int privateBlockStartPosHigh;

    public final int getBlockStartPos() {
        return this.privateBlockStartPos;
    }

    public final void setBlockStartPos(int value) {
        this.privateBlockStartPos = value;
    }

    public final int getBlockStartPosHigh() {
        return this.privateBlockStartPosHigh;
    }

    public final void setBlockStartPosHigh(int value) {
        this.privateBlockStartPosHigh = value;
    }
}

