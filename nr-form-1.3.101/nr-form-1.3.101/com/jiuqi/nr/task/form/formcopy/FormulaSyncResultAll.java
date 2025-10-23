/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import java.util.List;
import java.util.Map;

public class FormulaSyncResultAll {
    List<FormulaSyncResult> formulaSyncResult;
    Map<String, String> newFormLink;
    StringBuilder formCopyMessage;

    public FormulaSyncResultAll() {
    }

    public FormulaSyncResultAll(List<FormulaSyncResult> formulaSyncResult, Map<String, String> newFormLink) {
        this.formulaSyncResult = formulaSyncResult;
        this.newFormLink = newFormLink;
        this.formCopyMessage = new StringBuilder();
    }

    public FormulaSyncResultAll(List<FormulaSyncResult> formulaSyncResult, Map<String, String> newFormLink, StringBuilder formCopyMessage) {
        this.formulaSyncResult = formulaSyncResult;
        this.newFormLink = newFormLink;
        this.formCopyMessage = formCopyMessage;
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

    public StringBuilder getFormCopyMessage() {
        return this.formCopyMessage;
    }

    public void setFormCopyMessage(StringBuilder formCopyMessage) {
        this.formCopyMessage = formCopyMessage;
    }
}

