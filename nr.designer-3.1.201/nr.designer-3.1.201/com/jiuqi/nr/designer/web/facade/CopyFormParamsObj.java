/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.designer.common.CopyFormType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyFormParamsObj {
    private String taskKey;
    private String schemeKey;
    private String[] sourceFormKey;
    private String[] newFormTitle;
    private String targetGroupKey;
    private Map<String, String> formAndTitle;
    private Map<String, List<String>> schemeAndFormKey;
    private CopyFormType params;

    @JsonIgnore
    public String getFormTitle(String formKey) {
        if (null == this.formAndTitle) {
            this.formAndTitle = new HashMap<String, String>();
            for (int i = 0; i < this.sourceFormKey.length; ++i) {
                this.formAndTitle.put(this.sourceFormKey[i], this.newFormTitle[i]);
            }
        }
        return this.formAndTitle.get(formKey);
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String[] getSourceFormKey() {
        return this.sourceFormKey;
    }

    public void setSourceFormKey(String[] sourceFormKey) {
        this.sourceFormKey = sourceFormKey;
    }

    public String[] getNewFormTitle() {
        return this.newFormTitle;
    }

    public void setNewFormTitle(String[] newFormTitle) {
        this.newFormTitle = newFormTitle;
    }

    public String getTargetGroupKey() {
        return this.targetGroupKey;
    }

    public void setTargetGroupKey(String targetGroupKey) {
        this.targetGroupKey = targetGroupKey;
    }

    public CopyFormType getParams() {
        return this.params;
    }

    public void setParams(CopyFormType params) {
        this.params = params;
    }

    public Map<String, List<String>> getSchemeAndFormKey() {
        return this.schemeAndFormKey;
    }

    public void setSchemeAndFormKey(Map<String, List<String>> schemeAndFormKey) {
        this.schemeAndFormKey = schemeAndFormKey;
    }
}

