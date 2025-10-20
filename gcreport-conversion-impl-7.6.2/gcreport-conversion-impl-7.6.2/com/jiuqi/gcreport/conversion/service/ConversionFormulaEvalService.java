/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.conversion.service;

import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import javax.validation.constraints.NotNull;

public interface ConversionFormulaEvalService {
    public Double evaluateConversionRate(@NotNull DimensionValueSet var1, String var2, GcConversionOrgAndFormContextEnv var3, IDataRow var4);
}

