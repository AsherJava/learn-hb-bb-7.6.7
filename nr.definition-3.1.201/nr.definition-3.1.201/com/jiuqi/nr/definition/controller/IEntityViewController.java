/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.common.exception.JQException;

public interface IEntityViewController {
    public String getDimensionNameByViewKey(String var1) throws JQException;

    public String getBizFieldKeyByViewKey(String var1) throws JQException;

    public String getTableModelKey(String var1) throws JQException;
}

