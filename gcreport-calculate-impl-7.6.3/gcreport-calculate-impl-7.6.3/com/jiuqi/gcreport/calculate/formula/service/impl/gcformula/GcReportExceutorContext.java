/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.gcformula;

import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.List;

public class GcReportExceutorContext
extends GcReportSimpleExecutorContext {
    private List<? extends AbstractFieldDynamicDeclarator> inputDatas = null;
    private GcReportDataSet dataSet;

    public GcReportExceutorContext() {
    }

    public GcReportExceutorContext(IDataDefinitionRuntimeController runtimeController) {
    }

    public List<? extends AbstractFieldDynamicDeclarator> getInputDatas() {
        return this.inputDatas;
    }

    public void setInputDatas(List<? extends AbstractFieldDynamicDeclarator> inputDatas) {
        this.inputDatas = inputDatas;
    }

    public void reset() {
        super.reset();
        this.inputDatas = null;
        if (null != this.dataSet) {
            this.dataSet.clear();
        }
    }

    public void setDataSet(GcReportDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public GcReportDataSet getDataSet() {
        return this.dataSet;
    }
}

