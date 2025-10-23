/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public class OptionConfig {
    private int synRange;
    private boolean synCommitMemo;
    private boolean createXM;
    private boolean synCheckErrorDesc;

    public int getSynRange() {
        return this.synRange;
    }

    public void setSynRange(int synRange) {
        this.synRange = synRange;
    }

    public boolean isSynCommitMemo() {
        return this.synCommitMemo;
    }

    public void setSynCommitMemo(boolean synCommitMemo) {
        this.synCommitMemo = synCommitMemo;
    }

    public boolean isCreateXM() {
        return this.createXM;
    }

    public void setCreateXM(boolean createXM) {
        this.createXM = createXM;
    }

    public boolean isSynCheckErrorDesc() {
        return this.synCheckErrorDesc;
    }

    public void setSynCheckErrorDesc(boolean synCheckErrorDesc) {
        this.synCheckErrorDesc = synCheckErrorDesc;
    }
}

