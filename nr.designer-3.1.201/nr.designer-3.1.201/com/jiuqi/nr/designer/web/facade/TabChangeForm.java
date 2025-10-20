/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormData;

public class TabChangeForm {
    private boolean success;
    private String message;
    private FormData data;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FormData getData() {
        return this.data;
    }

    public void setData(FormData data) {
        this.data = data;
    }
}

