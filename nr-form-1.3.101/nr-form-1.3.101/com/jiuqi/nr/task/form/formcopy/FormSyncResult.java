/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import java.io.Serializable;
import java.util.List;

public class FormSyncResult
implements Serializable {
    private String srcFormSchemeKey;
    private String srcFormSchemeTitle;
    private String desFormSchemeKey;
    private String desFormSchemeTitle;
    List<FormulaSyncResult> formulaSyncResultList;

    public FormSyncResult() {
    }

    public FormSyncResult(String srcFormSchemeKey, String srcFormSchemeTitle, String desFormSchemeKey, String desFormSchemeTitle, List<FormulaSyncResult> formulaSyncResultList) {
        this.srcFormSchemeKey = srcFormSchemeKey;
        this.srcFormSchemeTitle = srcFormSchemeTitle;
        this.desFormSchemeKey = desFormSchemeKey;
        this.desFormSchemeTitle = desFormSchemeTitle;
        this.formulaSyncResultList = formulaSyncResultList;
    }

    public FormSyncResult(String srcFormSchemeKey, String desFormSchemeKey, List<FormulaSyncResult> formulaSyncResultList) {
        this.srcFormSchemeKey = srcFormSchemeKey;
        this.desFormSchemeKey = desFormSchemeKey;
        this.formulaSyncResultList = formulaSyncResultList;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public String getSrcFormSchemeTitle() {
        return this.srcFormSchemeTitle;
    }

    public void setSrcFormSchemeTitle(String srcFormSchemeTitle) {
        this.srcFormSchemeTitle = srcFormSchemeTitle;
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

    public List<FormulaSyncResult> getFormulaSyncResultList() {
        return this.formulaSyncResultList;
    }

    public void setFormulaSyncResultList(List<FormulaSyncResult> formulaSyncResultList) {
        this.formulaSyncResultList = formulaSyncResultList;
    }
}

