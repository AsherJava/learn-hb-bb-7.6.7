/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

public class PartEtag {
    private int partNum;

    public PartEtag() {
    }

    public PartEtag(int partNum) {
        this.partNum = partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public int getPartNum() {
        return this.partNum;
    }
}

