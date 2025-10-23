/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import java.util.List;

public class TransWorkFlow {
    private String state;
    private String commitType;
    private List<TransMemo> memos;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCommitType() {
        return this.commitType;
    }

    public void setCommitType(String commitType) {
        this.commitType = commitType;
    }

    public List<TransMemo> getMemos() {
        return this.memos;
    }

    public void setMemos(List<TransMemo> memos) {
        this.memos = memos;
    }
}

