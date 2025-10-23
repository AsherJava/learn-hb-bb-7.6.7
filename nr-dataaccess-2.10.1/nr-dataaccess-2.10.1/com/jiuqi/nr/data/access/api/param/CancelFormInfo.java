/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.param;

import java.io.Serializable;
import java.util.List;

public class CancelFormInfo
implements Serializable {
    private List<String> allFormKeys;
    private List<String> doneFormKeys;

    public List<String> getAllFormKeys() {
        return this.allFormKeys;
    }

    public void setAllFormKeys(List<String> allFormKeys) {
        this.allFormKeys = allFormKeys;
    }

    public List<String> getDoneFormKeys() {
        return this.doneFormKeys;
    }

    public void setDoneFormKeys(List<String> doneFormKeys) {
        this.doneFormKeys = doneFormKeys;
    }
}

