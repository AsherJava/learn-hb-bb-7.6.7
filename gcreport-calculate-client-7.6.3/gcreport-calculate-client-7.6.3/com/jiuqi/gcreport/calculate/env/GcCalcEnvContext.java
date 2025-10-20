/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 */
package com.jiuqi.gcreport.calculate.env;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.impl.CalcContextExpandVariableCenter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GcCalcEnvContext
extends ProgressData<List<String>>,
Serializable {
    public GcCalcArgmentsDTO getCalcArgments();

    public List<String> getResult();

    public Map<String, GcCalcRuleExecuteStateDTO> getRuleStateMap();

    public double getRuleStepProgress();

    public CalcContextExpandVariableCenter getCalcContextExpandVariableCenter();

    public void addResultItem(String var1);
}

