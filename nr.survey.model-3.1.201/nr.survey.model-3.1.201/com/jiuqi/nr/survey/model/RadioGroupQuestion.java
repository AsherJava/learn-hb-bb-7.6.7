/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.ChoicesQuestion;

public class RadioGroupQuestion
extends ChoicesQuestion {
    private boolean showClearButton;

    public boolean isShowClearButton() {
        return this.showClearButton;
    }

    public void setShowClearButton(boolean showClearButton) {
        this.showClearButton = showClearButton;
    }
}

