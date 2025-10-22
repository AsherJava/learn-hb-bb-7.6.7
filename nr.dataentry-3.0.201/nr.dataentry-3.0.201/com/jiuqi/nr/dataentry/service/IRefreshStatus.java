/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IRefreshStatus<T> {
    public boolean getEnable(String var1, String var2);

    public String getName();

    public Consts.RefreshStatusType getType();

    public T getStatus(JtableContext var1) throws Exception;
}

