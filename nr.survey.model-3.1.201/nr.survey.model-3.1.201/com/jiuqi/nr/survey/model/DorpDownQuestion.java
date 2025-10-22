/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.ChoicesQuestion;

public class DorpDownQuestion
extends ChoicesQuestion {
    private boolean allowClear = true;

    public boolean isAllowClear() {
        return this.allowClear;
    }

    public void setAllowClear(boolean allowClear) {
        this.allowClear = allowClear;
    }
}

