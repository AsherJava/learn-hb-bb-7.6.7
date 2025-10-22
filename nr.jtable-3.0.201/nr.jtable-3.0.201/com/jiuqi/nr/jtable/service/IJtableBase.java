/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import java.io.Serializable;

public interface IJtableBase
extends Serializable {
    public JtableContext getContext();

    public LogParam getLogParam();
}

