/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IUnitAuthorityServive {
    public String getAccessCode();

    public boolean canRead(JtableContext var1);
}

