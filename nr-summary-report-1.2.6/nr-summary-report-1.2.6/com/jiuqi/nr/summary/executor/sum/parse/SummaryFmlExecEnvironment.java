/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.summary.executor.sum.parse;

import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.summary.executor.sum.SumBeanSet;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryParam;
import com.jiuqi.nr.summary.executor.sum.parse.SummaryDatalLinkFinder;

public class SummaryFmlExecEnvironment
extends ReportFmlExecEnvironment
implements IFmlExecEnvironment {
    private RuntimeSummaryParam param;
    private SumBeanSet beanSet;
    private SummaryDatalLinkFinder datalLinkFinder;

    public SummaryFmlExecEnvironment(RuntimeSummaryParam param, SumBeanSet beanSet) {
        super(beanSet.runTimeViewController, beanSet.dataDefinitionController, beanSet.entityViewRunTimeController, null);
        this.param = param;
        this.beanSet = beanSet;
        this.dataScehmeKey = param.getParamDefine().getSummaryDataScheme().getKey();
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        if (this.datalLinkFinder == null) {
            this.datalLinkFinder = new SummaryDatalLinkFinder(this.param, this.beanSet);
        }
        return this.datalLinkFinder;
    }
}

