/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BIDataSetException;

public class BIDataSetNotFoundException
extends BIDataSetException {
    private static final long serialVersionUID = 2836151830892595389L;

    public BIDataSetNotFoundException(String dsName) {
        super("\u672a\u627e\u5230\u6570\u636e\u96c6:" + dsName);
    }
}

