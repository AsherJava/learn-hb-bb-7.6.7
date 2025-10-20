/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.authority;

public interface IReportAuthority {
    public boolean canAccess(String var1, String var2);

    public boolean canExport(String var1, String var2);

    public boolean canPrint(String var1, String var2);
}

