/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AssTaskToFormSchemes;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.SelectStructure;
import java.util.List;

public class AssTaskFormSchemeInfo {
    private int assTaskCount;
    private List<SelectStructure> assTasks;
    private List<AssTaskToFormSchemes> assTasksToForms;

    public int getAssTaskCount() {
        return this.assTaskCount;
    }

    public void setAssTaskCount(int assTaskCount) {
        this.assTaskCount = assTaskCount;
    }

    public List<SelectStructure> getAssTasks() {
        return this.assTasks;
    }

    public void setAssTasks(List<SelectStructure> assTasks) {
        this.assTasks = assTasks;
    }

    public List<AssTaskToFormSchemes> getAssTasksToForms() {
        return this.assTasksToForms;
    }

    public void setAssTasksToForms(List<AssTaskToFormSchemes> assTasksToForms) {
        this.assTasksToForms = assTasksToForms;
    }
}

