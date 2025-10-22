/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import java.util.Collections;
import java.util.List;

public class CKDValidInfo {
    private boolean pass;
    private List<String> errorMsgList = Collections.emptyList();

    public boolean isPass() {
        return this.pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public List<String> getErrorMsgList() {
        return this.errorMsgList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }
}

