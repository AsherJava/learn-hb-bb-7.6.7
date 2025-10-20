/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.model.DSModelException;

public class DataSetTypeNotFoundException
extends DSModelException {
    private static final long serialVersionUID = -9092062948804138386L;

    public DataSetTypeNotFoundException(String type) {
        super("\u65e0\u6cd5\u627e\u5230\u6570\u636e\u96c6\u7c7b\u578b\uff1a" + type);
    }
}

