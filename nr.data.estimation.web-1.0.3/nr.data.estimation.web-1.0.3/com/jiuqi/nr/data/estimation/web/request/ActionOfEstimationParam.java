/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.data.estimation.web.request.ActionOfSaveFormDataParam;

public class ActionOfEstimationParam
extends ActionOfSaveFormDataParam {
    private boolean isChange;

    public boolean isChange() {
        return this.isChange;
    }

    public void setChange(boolean change) {
        this.isChange = change;
    }
}

