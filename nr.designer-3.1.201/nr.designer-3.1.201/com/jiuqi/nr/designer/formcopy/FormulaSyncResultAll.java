/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy;

import com.jiuqi.nr.designer.formcopy.FormulaSyncResult;
import java.util.List;
import java.util.Map;

public class FormulaSyncResultAll {
    List<FormulaSyncResult> formulaSyncResult;
    Map<String, String> newFormLink;

    public FormulaSyncResultAll() {
    }

    public FormulaSyncResultAll(List<FormulaSyncResult> formulaSyncResult, Map<String, String> newFormLink) {
        this.formulaSyncResult = formulaSyncResult;
        this.newFormLink = newFormLink;
    }

    public List<FormulaSyncResult> getFormulaSyncResult() {
        return this.formulaSyncResult;
    }

    public void setFormulaSyncResult(List<FormulaSyncResult> formulaSyncResult) {
        this.formulaSyncResult = formulaSyncResult;
    }

    public Map<String, String> getNewFormLink() {
        return this.newFormLink;
    }

    public void setNewFormLink(Map<String, String> newFormLink) {
        this.newFormLink = newFormLink;
    }
}

