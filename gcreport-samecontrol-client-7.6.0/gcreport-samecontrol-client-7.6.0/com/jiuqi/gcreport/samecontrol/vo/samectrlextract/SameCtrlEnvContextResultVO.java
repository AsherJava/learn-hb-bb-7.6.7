/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.List;

public class SameCtrlEnvContextResultVO {
    private List<String> result;
    private AtomicDouble progress;
    private boolean successFlag = true;

    public List<String> getResult() {
        return this.result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public AtomicDouble getProgress() {
        return this.progress;
    }

    public void setProgress(AtomicDouble progress) {
        this.progress = progress;
    }

    public boolean isSuccessFlag() {
        return this.successFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }
}

