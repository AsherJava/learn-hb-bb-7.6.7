/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.cell;

import com.jiuqi.nr.survey.model.cell.ChoicesQuestionCell;

public class RadioGroupQuestionCell
extends ChoicesQuestionCell {
    private boolean showInMultipleColumns;

    public boolean isShowInMultipleColumns() {
        return this.showInMultipleColumns;
    }

    public void setShowInMultipleColumns(boolean showInMultipleColumns) {
        this.showInMultipleColumns = showInMultipleColumns;
    }
}

