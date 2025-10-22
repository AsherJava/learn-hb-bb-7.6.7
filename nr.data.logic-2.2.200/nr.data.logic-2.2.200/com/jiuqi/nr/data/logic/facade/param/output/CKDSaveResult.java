/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import java.util.Collections;
import java.util.List;

public class CKDSaveResult {
    private boolean success;
    private List<String> errorMsgList = Collections.emptyList();

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrorMsgList() {
        return this.errorMsgList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }
}

