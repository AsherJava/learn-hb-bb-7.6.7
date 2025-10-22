/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IRegisterDataentryRefreshParams {
    public RefreshType getRefreshType();

    public String getPramaKey(JtableContext var1);

    public Object getPramaValue(JtableContext var1);

    public static enum RefreshType {
        UNIT_REFRESH,
        FORM_REFRESH;

    }
}

