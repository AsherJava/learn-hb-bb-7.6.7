/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.define.IPeriodQuestion;

public class PeriodQuestion
extends Element
implements IPeriodQuestion {
    private String datetype;
    private String defaultValue;

    @Override
    public String getDatetype() {
        return this.datetype;
    }

    @Override
    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

