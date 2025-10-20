/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.invest.formula.service;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import javax.validation.constraints.NotNull;

public interface GcBillFormulaEvalService {
    public boolean checkInvestBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcInvestBillGroupDTO var4);

    public boolean checkFvchBillData(GcCalcArgmentsDTO var1, @NotNull DimensionValueSet var2, String var3, @NotNull DefaultTableEntity var4);

    public double evaluateInvestBillData(@NotNull GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull GcInvestBillGroupDTO var4, Integer var5);

    public AbstractData getInvestBillData(GcCalcEnvContext var1, DimensionValueSet var2, String var3, GcInvestBillGroupDTO var4);

    public double evaluateFvchBillData(GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull DefaultTableEntity var4);

    public AbstractData evaluateFvchBillAbstractData(GcCalcEnvContext var1, @NotNull DimensionValueSet var2, String var3, @NotNull DefaultTableEntity var4);
}

