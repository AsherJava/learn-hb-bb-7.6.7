/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.init;

import java.util.List;

public interface FTableDefineModel {
    public void init();

    public List<String> depandentTable();

    public boolean hasTable(String var1);

    public List<String> getTableNames();

    public boolean isReservedWord(String var1);
}

