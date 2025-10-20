/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 */
package com.jiuqi.gcreport.calculate.env.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.CalcContextExpandVariableCenter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GcCalcEnvContextImpl
extends ProgressDataImpl<List<String>>
implements GcCalcEnvContext {
    private GcCalcArgmentsDTO calcArgments;
    private double ruleStepProgress;
    private final Map<String, GcCalcRuleExecuteStateDTO> ruleStateMap = new ConcurrentHashMap<String, GcCalcRuleExecuteStateDTO>();
    private final CalcContextExpandVariableCenter calcContextExpandVariableCenter = new CalcContextExpandVariableCenter();

    public GcCalcEnvContextImpl() {
        this(UUIDUtils.newUUIDStr());
    }

    public GcCalcEnvContextImpl(String sn) {
        super(sn, new CopyOnWriteArrayList(), "GcCalcEnvContextImpl");
    }

    @Override
    public GcCalcArgmentsDTO getCalcArgments() {
        return this.calcArgments;
    }

    public void setCalcArgments(GcCalcArgmentsDTO calcArgments) {
        this.calcArgments = calcArgments;
    }

    @Override
    public Map<String, GcCalcRuleExecuteStateDTO> getRuleStateMap() {
        return this.ruleStateMap;
    }

    @Override
    public double getRuleStepProgress() {
        return this.ruleStepProgress;
    }

    public void setRuleStepProgress(double ruleStepProgress) {
        this.ruleStepProgress = ruleStepProgress;
    }

    @Override
    public CalcContextExpandVariableCenter getCalcContextExpandVariableCenter() {
        return this.calcContextExpandVariableCenter;
    }

    @Override
    public void addResultItem(String resultItem) {
        if (StringUtils.isEmpty((String)resultItem)) {
            return;
        }
        ((List)this.getResult()).add(resultItem);
    }
}

