/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.formula.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import javax.validation.constraints.NotNull;

public interface GcFormulaEvalService {
    public boolean checkUnitData(GcCalcArgmentsDTO var1, @NotNull DimensionValueSet var2, String var3, String var4);

    public double evaluateUnitData(@NotNull DimensionValueSet var1, String var2, String var3);

    public AbstractData evaluateUnitDataFormula(@NotNull DimensionValueSet var1, String var2, String var3);

    public double evaluate(@NotNull DimensionValueSet var1, String var2, String var3);

    public AbstractData ordinaryFormulaEvaluate(DimensionValueSet var1, String var2, String var3);
}

