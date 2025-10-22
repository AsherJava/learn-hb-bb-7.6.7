/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.cell;

import com.jiuqi.nr.survey.model.cell.CellColumn;
import com.jiuqi.nr.survey.model.define.IPeriodQuestion;

public class PeriodQuestionCell
extends CellColumn
implements IPeriodQuestion {
    private String datetype;
    private String defaultValue;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDatetype() {
        return this.datetype;
    }

    @Override
    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }
}

