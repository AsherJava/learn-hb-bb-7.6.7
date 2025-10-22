/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.response;

public class EstimationFormInfo {
    public static final String typeOfGroup = "Group";
    public static final String typeOfForm = "Form";
    private String key;
    private String code;
    private String title;
    private String type;
    private boolean isInputForm;
    private boolean isOutputForm;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInputForm() {
        return this.isInputForm;
    }

    public void setInputForm(boolean inputForm) {
        this.isInputForm = inputForm;
    }

    public boolean isOutputForm() {
        return this.isOutputForm;
    }

    public void setOutputForm(boolean outputForm) {
        this.isOutputForm = outputForm;
    }
}

