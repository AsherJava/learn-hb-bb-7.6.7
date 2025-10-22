/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 *  com.jiuqi.nr.jtable.params.base.FromGridData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.JtableData
 *  com.jiuqi.nr.jtable.service.IExtractFormulaService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.impl.JtableContextServiceImpl
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.gcreport.bde.fetchsetting.impl.service.IGetReportFormDataHook;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.FromGridData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;
import com.jiuqi.nr.jtable.service.IExtractFormulaService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.impl.JtableContextServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BDEJtableContextServiceImpl
extends JtableContextServiceImpl {
    @Autowired
    private IJtableParamService jtableParamService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IExtractFormulaService formulaService;
    @Autowired(required=false)
    private IGetReportFormDataHook getReportFormDataHook;

    public JtableData getReportFormData(JtableContext jtableContext) {
        JtableData jtableData = super.getReportFormData(jtableContext);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        if (taskDefine == null || !taskDefine.getEfdcSwitch()) {
            return jtableData;
        }
        if (jtableData.getFormulaSchemeData() != null) {
            return jtableData;
        }
        if (this.getReportFormDataHook != null && this.getReportFormDataHook.enable(jtableContext)) {
            return this.getReportFormDataHook.execute(jtableData, jtableContext);
        }
        FormulaSchemeData formulaSchemeData = this.jtableParamService.getSoluctionByDimensions(jtableContext);
        if (formulaSchemeData == null || formulaSchemeData.getKey() == null || formulaSchemeData.getKey().length() > 16) {
            return jtableData;
        }
        jtableData.setFormulaSchemeData(formulaSchemeData);
        FromGridData structure = (FromGridData)jtableData.getStructure();
        List extractDataLinkList = this.formulaService.getExtractDataLinkList(jtableContext, formulaSchemeData.getKey());
        structure.setExtractDataLinks(extractDataLinkList);
        return jtableData;
    }
}

