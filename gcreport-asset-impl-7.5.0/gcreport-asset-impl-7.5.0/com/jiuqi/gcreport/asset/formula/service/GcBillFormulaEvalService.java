/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.asset.formula.service;

import com.jiuqi.gcreport.asset.assetbill.dto.GcAssetBillGroupDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import javax.validation.constraints.NotNull;

public interface GcBillFormulaEvalService {
    public boolean checkAssetBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcAssetBillGroupDTO var4);

    public double evaluateAssetBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcAssetBillGroupDTO var4);

    public AbstractData getAssetBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcAssetBillGroupDTO var4);

    public String evaluateAssetBillDataGetSubject(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcAssetBillGroupDTO var4);
}

