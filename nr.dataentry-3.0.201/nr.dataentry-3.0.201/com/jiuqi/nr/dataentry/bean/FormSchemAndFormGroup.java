/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import java.util.List;

public class FormSchemAndFormGroup {
    private FormSchemeData scheme;
    private List<FormGroupData> formGroups;

    public FormSchemeData getScheme() {
        return this.scheme;
    }

    public void setScheme(FormSchemeData scheme) {
        this.scheme = scheme;
    }

    public List<FormGroupData> getFormGroups() {
        return this.formGroups;
    }

    public void setFormGroups(List<FormGroupData> formGroups) {
        this.formGroups = formGroups;
    }
}

