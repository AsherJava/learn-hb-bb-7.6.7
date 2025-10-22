/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.param.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CancelGatherInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String targetKey;
    private Set<String> allFormKey = new HashSet<String>();
    private Set<String> doneFormKey = new HashSet<String>();

    public String getTargetKey() {
        return this.targetKey;
    }

    public CancelGatherInfo(String targetKey) {
        this.targetKey = targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public Set<String> getAllFormKey() {
        return this.allFormKey;
    }

    public void setAllFormKey(Set<String> allFormKey) {
        this.allFormKey = allFormKey;
    }

    public Set<String> getDoneFormKey() {
        return this.doneFormKey;
    }

    public void setDoneFormKey(Set<String> doneFormKey) {
        this.doneFormKey = doneFormKey;
    }
}

