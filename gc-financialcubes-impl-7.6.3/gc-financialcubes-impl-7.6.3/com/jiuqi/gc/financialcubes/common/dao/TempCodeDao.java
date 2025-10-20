/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.common.dao;

import java.util.List;

public interface TempCodeDao {
    public void batchInsert(List<String> var1);

    public void cleanTemp();
}

