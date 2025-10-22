/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.web.vo;

import com.jiuqi.nr.midstore2.dataentry.web.vo.ErrorType;
import com.jiuqi.nr.midstore2.dataentry.web.vo.FailedForm;
import java.util.ArrayList;
import java.util.List;

public class FailedUnit {
    private String code;
    private String title;
    private ErrorType type;
    private String message;
    private List<FailedForm> forms;

    public FailedUnit() {
    }

    public FailedUnit(String code, String title) {
        this.code = code;
        this.title = title;
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

    public ErrorType getType() {
        return this.type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FailedForm> getForms() {
        return this.forms;
    }

    public void setForms(List<FailedForm> forms) {
        this.forms = forms;
    }

    public void addForms(String formCode, String formTitle, String message) {
        if (this.forms == null) {
            this.forms = new ArrayList<FailedForm>();
        }
        this.forms.add(new FailedForm(formCode, formTitle, message));
    }
}

