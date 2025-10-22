/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import java.io.Serializable;

public class ResponseSurveySaveVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

