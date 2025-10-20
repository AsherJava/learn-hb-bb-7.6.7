/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.designer.web.rest.vo;

import org.json.JSONObject;

public class DesignRestVO {
    private String taskKey;
    private String formSchemeKey;
    private String formGroupKey;
    private String formKey;
    private int languageType;
    private String viewKey;

    public DesignRestVO(String voStr) {
        JSONObject json = new JSONObject(voStr);
        if (json.has("formId")) {
            this.formKey = json.getString("formId");
        }
        if (json.has("taskId")) {
            this.taskKey = json.getString("taskId");
        }
        if (json.has("ownGroupId")) {
            this.formGroupKey = json.getString("ownGroupId");
        }
        if (json.has("languageType")) {
            this.languageType = json.getInt("languageType");
        }
        if (json.has("activedSchemeId")) {
            this.formSchemeKey = json.getString("activedSchemeId");
        }
        if (json.has("viewKey")) {
            this.viewKey = json.getString("viewKey");
        }
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public int getLanguageType() {
        return this.languageType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getViewKey() {
        return this.viewKey;
    }
}

