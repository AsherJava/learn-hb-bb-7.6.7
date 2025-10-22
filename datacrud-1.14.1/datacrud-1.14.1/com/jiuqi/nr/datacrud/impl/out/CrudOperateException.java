/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.out;

import com.jiuqi.nr.datacrud.SaveResItem;
import java.util.List;

public class CrudOperateException
extends Exception {
    private int code;
    private List<SaveResItem> saveResItems;

    public CrudOperateException() {
    }

    public CrudOperateException(int code) {
        this.code = code;
    }

    public CrudOperateException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CrudOperateException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CrudOperateException(String message) {
        super(message);
    }
}

