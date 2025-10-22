/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.service;

import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GcFinancialCheckFormulaEvalService {
    public boolean checkMxUnOffsetData(@NotNull DimensionValueSet var1, String var2, @NotNull GcRelatedItemEO var3, String var4, String var5);

    public boolean checkSumUnOffsetData(@NotNull DimensionValueSet var1, String var2, @NotNull List<GcFcRuleUnOffsetDataDTO> var3);

    public double evaluateMxUnOffsetData(@NotNull DimensionValueSet var1, String var2, @NotNull GcFcRuleUnOffsetDataDTO var3, List<GcFcRuleUnOffsetDataDTO> var4);

    public AbstractData evaluateMxUnOffsetAbstractData(@NotNull DimensionValueSet var1, String var2, @NotNull GcFcRuleUnOffsetDataDTO var3, List<GcFcRuleUnOffsetDataDTO> var4);

    public double evaluateSumUnOffsetData(@NotNull DimensionValueSet var1, String var2, List<GcFcRuleUnOffsetDataDTO> var3);

    public AbstractData evaluateSumUnOffsetAbstractData(@NotNull DimensionValueSet var1, String var2, List<GcFcRuleUnOffsetDataDTO> var3);

    public double evaluateUnOffsetDataPHS(@NotNull DimensionValueSet var1, String var2, @NotNull List<GcFcRuleUnOffsetDataDTO> var3, @NotNull double var4);
}

