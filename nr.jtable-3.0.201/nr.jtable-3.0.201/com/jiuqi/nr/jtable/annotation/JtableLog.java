/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.annotation;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.service.IJtableBase;

public abstract class JtableLog
implements IJtableBase {
    @Override
    public JtableContext getContext() {
        return null;
    }

    @Override
    public LogParam getLogParam() {
        return null;
    }
}

