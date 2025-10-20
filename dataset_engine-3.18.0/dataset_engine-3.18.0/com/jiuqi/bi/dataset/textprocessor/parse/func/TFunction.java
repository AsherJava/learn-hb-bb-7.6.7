/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.format.IFormatable
 *  com.jiuqi.bi.syntax.function.Function
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func;

import com.jiuqi.bi.syntax.format.IFormatable;
import com.jiuqi.bi.syntax.function.Function;

public abstract class TFunction
extends Function
implements IFormatable {
    private static final long serialVersionUID = 7271879527962826285L;

    public abstract boolean isDatasetNodeParam(int var1);

    public String category() {
        return "\u6570\u636e\u96c6\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return true;
    }
}

