/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.service;

import com.jiuqi.common.financialcubes.common.FinancialCubesRebuildScopeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;

public interface FinancialCubesRebuildPlugin {
    public String pluginName();

    public FinancialCubesRebuildScopeEnum rebuildScope();

    public String rebuild(FinancialCubesRebuildDTO var1);
}

