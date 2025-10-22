/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.survey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.survey.model.ChoicesQuestion;

public class TagboxQuestion
extends ChoicesQuestion {
    private boolean showSelectAllItem;
    private int maxSelectedChoices;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean allowClear = true;
    @JsonInclude(value=JsonInclude.Include.ALWAYS)
    private boolean searchEnabled = true;

    public boolean isShowSelectAllItem() {
        return this.showSelectAllItem;
    }

    public void setShowSelectAllItem(boolean showSelectAllItem) {
        this.showSelectAllItem = showSelectAllItem;
    }

    public boolean isAllowClear() {
        return this.allowClear;
    }

    public void setAllowClear(boolean allowClear) {
        this.allowClear = allowClear;
    }

    public boolean isSearchEnabled() {
        return this.searchEnabled;
    }

    public void setSearchEnabled(boolean searchEnabled) {
        this.searchEnabled = searchEnabled;
    }

    public int getMaxSelectedChoices() {
        return this.maxSelectedChoices;
    }

    public void setMaxSelectedChoices(int maxSelectedChoices) {
        this.maxSelectedChoices = maxSelectedChoices;
    }
}

