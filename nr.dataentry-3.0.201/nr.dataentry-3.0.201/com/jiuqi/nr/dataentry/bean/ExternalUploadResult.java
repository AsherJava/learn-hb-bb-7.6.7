/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.io.Serializable;

public class ExternalUploadResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String msg;
    private String modalTitle;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getModalTitle() {
        return this.modalTitle;
    }

    public void setModalTitle(String modalTitle) {
        this.modalTitle = modalTitle;
    }
}

