/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.collector;

import java.util.ArrayList;
import java.util.List;

public class FmlExecuteCollectConfig {
    private List<String> focusZbExps = new ArrayList<String>();
    private boolean canCommit = true;

    public boolean isCanCommit() {
        return this.canCommit;
    }

    public void setCanCommit(boolean canCommit) {
        this.canCommit = canCommit;
    }

    public List<String> getFocusZbExps() {
        return this.focusZbExps;
    }
}

