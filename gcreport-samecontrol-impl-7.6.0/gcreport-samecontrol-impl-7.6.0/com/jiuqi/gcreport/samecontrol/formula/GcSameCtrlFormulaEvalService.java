/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.gcreport.samecontrol.formula;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.np.dataengine.common.DimensionValueSet;

public interface GcSameCtrlFormulaEvalService {
    public double evaluateSameCtrlInvestData(SameCtrlOffsetCond var1, DimensionValueSet var2, String var3, Double var4, DefaultTableEntity var5);

    public boolean checkSameCtrlInvestData(SameCtrlOffsetCond var1, DimensionValueSet var2, String var3, DefaultTableEntity var4);
}

