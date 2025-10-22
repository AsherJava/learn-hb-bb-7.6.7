/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.out;

import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import java.util.Collections;
import java.util.List;

public class CrudSaveException
extends CrudOperateException {
    private List<SaveResItem> items;

    public List<SaveResItem> getItems() {
        if (this.items == null) {
            return Collections.emptyList();
        }
        return this.items;
    }

    public void setItems(List<SaveResItem> items) {
        this.items = items;
    }

    public CrudSaveException(List<SaveResItem> items) {
        this.items = items;
    }

    public CrudSaveException(int code, List<SaveResItem> items) {
        super(code);
        this.items = items;
    }

    public CrudSaveException(int code, String message, List<SaveResItem> items) {
        super(code, message);
        this.items = items;
    }

    public CrudSaveException(int code, String message, Throwable cause, List<SaveResItem> items) {
        super(code, message, cause);
        this.items = items;
    }

    public CrudSaveException(String message, List<SaveResItem> items) {
        super(message);
        this.items = items;
    }
}

