/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.readwrite.IAccessResult;

public class AccessResult
implements IAccessResult {
    private boolean access = true;
    private String message = null;

    public AccessResult() {
    }

    public AccessResult(boolean access, String message) {
        this.access = access;
        this.message = message;
    }

    @Override
    public boolean haveAccess() throws Exception {
        return this.access;
    }

    @Override
    public String getMessage() throws Exception {
        return this.message;
    }
}

