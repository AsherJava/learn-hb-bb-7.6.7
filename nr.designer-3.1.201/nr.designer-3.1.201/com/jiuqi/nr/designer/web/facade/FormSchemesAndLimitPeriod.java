/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj;
import java.util.List;

public class FormSchemesAndLimitPeriod {
    private String limitStart;
    private String limitEnd;
    private List<FormSchemeLifeObj> formSchemes;

    public String getLimitStart() {
        return this.limitStart;
    }

    public void setLimitStart(String limitStart) {
        this.limitStart = limitStart;
    }

    public String getLimitEnd() {
        return this.limitEnd;
    }

    public void setLimitEnd(String limitEnd) {
        this.limitEnd = limitEnd;
    }

    public List<FormSchemeLifeObj> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<FormSchemeLifeObj> formSchemes) {
        this.formSchemes = formSchemes;
    }
}

