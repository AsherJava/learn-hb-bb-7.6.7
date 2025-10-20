/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormulaSearchNode;
import java.util.List;

public class FormulaSearchItem {
    private String key;
    private String code;
    private String title;
    private String formId;
    private List<FormulaSearchNode> groupPath;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FormulaSearchNode> getGroupPath() {
        return this.groupPath;
    }

    public void setGroupPath(List<FormulaSearchNode> groupPath) {
        this.groupPath = groupPath;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

