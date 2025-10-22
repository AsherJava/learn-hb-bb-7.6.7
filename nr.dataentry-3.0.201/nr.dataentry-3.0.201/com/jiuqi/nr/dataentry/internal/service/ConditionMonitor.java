/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import java.util.HashSet;
import java.util.Set;

public class ConditionMonitor
extends AbstractMonitor {
    private Set<String> conditionResultList = new HashSet<String>();

    public ConditionMonitor() {
        super(DataEngineConsts.DataEngineRunType.JUDGE);
    }

    public void error(FormulaCheckEventImpl event) {
        this.conditionResultList.add(event.getFormulaObj().getFormKey());
    }

    public Set<String> getConditionResultList() {
        return this.conditionResultList;
    }

    public void message(String msg, Object sender) {
    }

    public void onProgress(double progress) {
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }
}

