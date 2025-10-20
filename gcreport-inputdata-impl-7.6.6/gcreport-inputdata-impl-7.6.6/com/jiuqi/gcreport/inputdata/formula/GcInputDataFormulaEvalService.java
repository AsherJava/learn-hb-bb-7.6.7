/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.inputdata.formula;

import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GcInputDataFormulaEvalService {
    public boolean checkMxInputData(@NotNull DimensionValueSet var1, String var2, @NotNull InputDataEO var3);

    public boolean checkSumInputData(@NotNull DimensionValueSet var1, String var2, @NotNull List<InputDataEO> var3);

    public double evaluateMxInputData(@NotNull DimensionValueSet var1, String var2, @NotNull InputDataEO var3, List<InputDataEO> var4, List<GcOffSetVchrItemDTO> var5);

    public AbstractData evaluateMxInputAbstractData(@NotNull DimensionValueSet var1, String var2, @NotNull InputDataEO var3, List<InputDataEO> var4);

    public double evaluateSumInputData(@NotNull DimensionValueSet var1, String var2, @NotNull List<InputDataEO> var3, List<GcOffSetVchrItemDTO> var4);

    public AbstractData evaluateSumInputAbstractData(@NotNull DimensionValueSet var1, String var2, @NotNull List<InputDataEO> var3, List<GcOffSetVchrItemDTO> var4);

    public double evaluateInputDataPHS(@NotNull DimensionValueSet var1, String var2, @NotNull List<InputDataEO> var3, @NotNull double var4);

    public String evaluateInputDataSubjectAllocation(@NotNull DimensionValueSet var1, String var2, @NotNull List<InputDataEO> var3);
}

