/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.jiuqi.nr.designer.paramlanguage.vo.ResultFormGroupObject;
import java.util.List;

public class ResultReportObject {
    private List<ResultFormGroupObject> forms;
    private List<ResultFormGroupObject> tree;
    private String language;

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ResultFormGroupObject> getForms() {
        return this.forms;
    }

    public void setForms(List<ResultFormGroupObject> forms) {
        this.forms = forms;
    }

    public List<ResultFormGroupObject> getTree() {
        return this.tree;
    }

    public void setTree(List<ResultFormGroupObject> tree) {
        this.tree = tree;
    }
}

