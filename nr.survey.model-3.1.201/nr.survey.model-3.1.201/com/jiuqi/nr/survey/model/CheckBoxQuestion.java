/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.ChoicesQuestion;

public class CheckBoxQuestion
extends ChoicesQuestion {
    private boolean showSelectAllItem;
    private int maxSelectedChoices;

    public boolean isShowSelectAllItem() {
        return this.showSelectAllItem;
    }

    public void setShowSelectAllItem(boolean showSelectAllItem) {
        this.showSelectAllItem = showSelectAllItem;
    }

    public int getMaxSelectedChoices() {
        return this.maxSelectedChoices;
    }

    public void setMaxSelectedChoices(int maxSelectedChoices) {
        this.maxSelectedChoices = maxSelectedChoices;
    }
}

