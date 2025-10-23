/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.nr.datascheme.api.DataScheme;

public interface IMCTableService {
    public void createResultTable(DataScheme var1) throws Exception;

    public void dropResultTable(DataScheme var1) throws Exception;

    public void dealIndex(DataScheme var1) throws Exception;
}

