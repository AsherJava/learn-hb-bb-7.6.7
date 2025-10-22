/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

public class FormAccessResult {
    public boolean haveAccess;
    public String message;

    public static FormAccessResult formHaveAccess() {
        FormAccessResult formAccessResult = new FormAccessResult(true, "");
        return formAccessResult;
    }

    public FormAccessResult(boolean haveAccess, String message) {
        this.haveAccess = haveAccess;
        this.message = message;
    }

    public FormAccessResult() {
    }

    public boolean isHaveAccess() {
        return this.haveAccess;
    }

    public void setHaveAccess(boolean haveAccess) {
        this.haveAccess = haveAccess;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

