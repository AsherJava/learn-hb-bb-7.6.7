/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import java.util.List;

public class FormSyncPushResult {
    boolean hasError;
    String desTaskTitle;
    String desFormSchemeKey;
    String desFormSchemeTitle;
    String oneDesFormKey;
    String oneDesFormGroupKey;
    List<FormulaSyncResult> formulaSyncResults;

    public boolean isHasError() {
        return this.hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getDesTaskTitle() {
        return this.desTaskTitle;
    }

    public void setDesTaskTitle(String desTaskTitle) {
        this.desTaskTitle = desTaskTitle;
    }

    public String getDesFormSchemeKey() {
        return this.desFormSchemeKey;
    }

    public void setDesFormSchemeKey(String desFormSchemeKey) {
        this.desFormSchemeKey = desFormSchemeKey;
    }

    public String getDesFormSchemeTitle() {
        return this.desFormSchemeTitle;
    }

    public void setDesFormSchemeTitle(String desFormSchemeTitle) {
        this.desFormSchemeTitle = desFormSchemeTitle;
    }

    public String getOneDesFormKey() {
        return this.oneDesFormKey;
    }

    public void setOneDesFormKey(String oneDesFormKey) {
        this.oneDesFormKey = oneDesFormKey;
    }

    public String getOneDesFormGroupKey() {
        return this.oneDesFormGroupKey;
    }

    public void setOneDesFormGroupKey(String oneDesFormGroupKey) {
        this.oneDesFormGroupKey = oneDesFormGroupKey;
    }

    public List<FormulaSyncResult> getFormulaSyncResults() {
        return this.formulaSyncResults;
    }

    public void setFormulaSyncResults(List<FormulaSyncResult> formulaSyncResults) {
        this.formulaSyncResults = formulaSyncResults;
    }
}

