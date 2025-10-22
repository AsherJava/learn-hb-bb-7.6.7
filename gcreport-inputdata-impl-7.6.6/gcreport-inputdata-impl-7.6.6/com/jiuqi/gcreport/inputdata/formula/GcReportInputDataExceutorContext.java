/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.inputdata.formula;

import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.List;

public class GcReportInputDataExceutorContext
extends GcReportExceutorContext {
    private List<? extends AbstractFieldDynamicDeclarator> inputDatas = null;
    private List<? extends AbstractFieldDynamicDeclarator> offSetVchrItems = null;
    private GcReportInputDataSet dataSet;

    public GcReportInputDataExceutorContext() {
    }

    public GcReportInputDataExceutorContext(IDataDefinitionRuntimeController runtimeController) {
    }

    public List<? extends AbstractFieldDynamicDeclarator> getInputDatas() {
        return this.inputDatas;
    }

    public void setInputDatas(List<? extends AbstractFieldDynamicDeclarator> inputDatas) {
        this.inputDatas = inputDatas;
    }

    public List<? extends AbstractFieldDynamicDeclarator> getOffSetVchrItems() {
        return this.offSetVchrItems;
    }

    public void setOffSetVchrItems(List<? extends AbstractFieldDynamicDeclarator> offSetVchrItems) {
        this.offSetVchrItems = offSetVchrItems;
    }

    public void reset() {
        super.reset();
        this.inputDatas = null;
        if (null != this.dataSet) {
            this.dataSet.clear();
        }
    }

    public void setDataSet(GcReportInputDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public GcReportInputDataSet getDataSet() {
        return this.dataSet;
    }
}

