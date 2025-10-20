/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.lease.formula.service;

import com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GcBillFormulaEvalService {
    public AbstractData evaluateBillAbstractData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcBillGroupDTO var4, @NotNull List<String> var5);

    public double evaluateBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcBillGroupDTO var4, List<String> var5);
}

