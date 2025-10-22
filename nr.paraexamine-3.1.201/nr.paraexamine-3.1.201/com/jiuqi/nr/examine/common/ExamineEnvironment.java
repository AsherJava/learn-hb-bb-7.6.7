/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.controller.IDesignParaCheckController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.examine.common;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.controller.IDesignParaCheckController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.examine.service.ParamExamineInfoService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamineEnvironment {
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private IRunTimeViewController nrRuntimeController;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    private IFormulaDesignTimeController designFormulaController;
    @Autowired
    private IPrintDesignTimeController designPrintController;
    @Autowired
    private IDesignParaCheckController designParaCheckController;
    @Autowired
    private ParamExamineInfoService service;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public ExamineEnvironment() {
    }

    public ExamineEnvironment(IDesignTimeViewController nrDesignController, IRunTimeViewController nrRuntimeController, IDataDefinitionDesignTimeController npDesignController, IFormulaDesignTimeController designFormulaController, IPrintDesignTimeController designPrintController, IDesignParaCheckController designParaCheckController, ParamExamineInfoService service) {
        this.nrDesignController = nrDesignController;
        this.nrRuntimeController = nrRuntimeController;
        this.npDesignController = npDesignController;
        this.designFormulaController = designFormulaController;
        this.designPrintController = designPrintController;
        this.designParaCheckController = designParaCheckController;
        this.service = service;
    }

    public IDesignTimeViewController getNrDesignController() {
        return this.nrDesignController;
    }

    public void setNrDesignController(IDesignTimeViewController nrDesignController) {
        this.nrDesignController = nrDesignController;
    }

    public IRunTimeViewController getNrRuntimeController() {
        return this.nrRuntimeController;
    }

    public void setNrRuntimeController(IRunTimeViewController nrRuntimeController) {
        this.nrRuntimeController = nrRuntimeController;
    }

    public IDataDefinitionDesignTimeController getNpDesignController() {
        return this.npDesignController;
    }

    public void setNpDesignController(IDataDefinitionDesignTimeController npDesignController) {
        this.npDesignController = npDesignController;
    }

    public IFormulaDesignTimeController getDesignFormulaController() {
        return this.designFormulaController;
    }

    public void setDesignFormulaController(IFormulaDesignTimeController designFormulaController) {
        this.designFormulaController = designFormulaController;
    }

    public IPrintDesignTimeController getDesignPrintController() {
        return this.designPrintController;
    }

    public void setDesignPrintController(IPrintDesignTimeController designPrintController) {
        this.designPrintController = designPrintController;
    }

    public IDesignParaCheckController getDesignParaCheckController() {
        return this.designParaCheckController;
    }

    public void setDesignParaCheckController(IDesignParaCheckController designParaCheckController) {
        this.designParaCheckController = designParaCheckController;
    }

    public ParamExamineInfoService getService() {
        return this.service;
    }

    public void setService(ParamExamineInfoService service) {
        this.service = service;
    }

    public IPeriodEntityAdapter getPeriodEntityAdapter() {
        return this.periodEntityAdapter;
    }

    public void setPeriodEntityAdapter(IPeriodEntityAdapter periodEntityAdapter) {
        this.periodEntityAdapter = periodEntityAdapter;
    }
}

