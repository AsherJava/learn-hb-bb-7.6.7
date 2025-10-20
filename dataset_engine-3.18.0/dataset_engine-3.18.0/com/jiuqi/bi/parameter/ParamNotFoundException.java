/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.BIDataSetException;

public class ParamNotFoundException
extends BIDataSetException {
    private static final long serialVersionUID = 160406039120959183L;

    public ParamNotFoundException(String paramName) {
        super("\u65e0\u6cd5\u627e\u5230\u53c2\u6570\uff1a" + paramName);
    }
}

