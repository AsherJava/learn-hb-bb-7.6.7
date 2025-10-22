/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.out;

import com.jiuqi.nr.datacrud.ReturnRes;

public class ReturnResInstance
extends ReturnRes {
    public static final ReturnRes OK_INSTANCE = new ReturnResInstance(0);
    public static final ReturnRes ERR_INSTANCE = new ReturnResInstance(1900);

    public ReturnResInstance(int code) {
        super(code, null);
    }

    @Override
    public void setCode(int code) {
    }

    @Override
    public void setMessage(String message) {
    }
}

