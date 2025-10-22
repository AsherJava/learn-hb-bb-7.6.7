/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationFormImpl
 *  com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.nr.data.estimation.common.exception.EstimationRuntimeException;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationFormImpl;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeSet;
import com.jiuqi.nr.data.estimation.web.response.EstimationSchemeInfo;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EstimationSchemeTemplateBuilder {
    private IRunTimeViewController runTimeViewController;
    private IFormulaRunTimeController formulaRunTimeController;

    public EstimationSchemeTemplateBuilder(IRunTimeViewController runTimeViewController, IFormulaRunTimeController formulaRunTimeController) {
        this.runTimeViewController = runTimeViewController;
        this.formulaRunTimeController = formulaRunTimeController;
    }

    public IEstimationSchemeTemplate build(EstimationSchemeInfo schemeInfo) {
        IEstimationSchemeTemplateImpl impl = new IEstimationSchemeTemplateImpl();
        impl.setKey(schemeInfo.getKey());
        impl.setCode(schemeInfo.getCode());
        impl.setTitle(schemeInfo.getTitle());
        impl.setTaskDefine(this.getTaskDefine(schemeInfo.getTaskId()));
        impl.setFormSchemeDefine(this.getFormSchemeDefine(schemeInfo.getFormSchemeId()));
        impl.setEstimationForms(this.getEstimationForms(schemeInfo.getFormInfos()));
        impl.setAccessFormulaSchemes(this.getAccessFormulaSchemes(schemeInfo.getAccessFormulaSchemes()));
        impl.setCalcFormulaSchemes(this.getCalcFormulaSchemes(schemeInfo.getCalcFormulaSchemes()));
        return impl;
    }

    private TaskDefine getTaskDefine(String taskId) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
        if (taskDefine == null) {
            throw new EstimationRuntimeException("\u4fdd\u5b58\u5931\u8d25\uff1a\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        return taskDefine;
    }

    private FormSchemeDefine getFormSchemeDefine(String formSchemeId) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        if (formScheme == null) {
            throw new EstimationRuntimeException("\u4fdd\u5b58\u5931\u8d25\uff1a\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        return formScheme;
    }

    private List<IEstimationForm> getEstimationForms(List<EstimationFormInfo> formInfos) {
        HashMap formTypeMap = new HashMap();
        formInfos.forEach(e -> formTypeMap.put(e.getKey(), this.getFormType((EstimationFormInfo)e)));
        List formDefines = this.runTimeViewController.queryFormsById(formInfos.stream().filter(e -> "Form".equals(e.getType())).map(EstimationFormInfo::getKey).collect(Collectors.toList()));
        List impls = formDefines.stream().map(formDefine -> {
            IEstimationFormImpl impl = new IEstimationFormImpl();
            impl.setFormDefine(formDefine);
            impl.setFormType((EstimationFormType)formTypeMap.get(formDefine.getKey()));
            return impl;
        }).collect(Collectors.toList());
        return impls.stream().filter(formImpl -> formImpl.getFormType() != null).collect(Collectors.toList());
    }

    private List<FormulaSchemeDefine> getAccessFormulaSchemes(EstimationFormulaSchemeSet formulaSchemeSet) {
        List<String> checkFormulaSchemes = formulaSchemeSet.getCheckFormulaSchemes();
        if (checkFormulaSchemes != null && !checkFormulaSchemes.isEmpty()) {
            return checkFormulaSchemes.stream().map(formulaSchemeKey -> this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey)).collect(Collectors.toList());
        }
        return new ArrayList<FormulaSchemeDefine>();
    }

    private List<FormulaSchemeDefine> getCalcFormulaSchemes(EstimationFormulaSchemeSet formulaSchemeSet) {
        List<String> checkFormulaSchemes = formulaSchemeSet.getCheckFormulaSchemes();
        if (checkFormulaSchemes != null && !checkFormulaSchemes.isEmpty()) {
            return checkFormulaSchemes.stream().map(formulaSchemeKey -> this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey)).collect(Collectors.toList());
        }
        return new ArrayList<FormulaSchemeDefine>();
    }

    private EstimationFormType getFormType(EstimationFormInfo formInfo) {
        if (formInfo.isInputForm() && formInfo.isOutputForm()) {
            throw new EstimationRuntimeException("[" + formInfo.getTitle() + "]\u4e0d\u80fd\u65e2\u662f\u8f93\u5165\u8868\uff0c\u53c8\u662f\u8f93\u51fa\u8868");
        }
        if (formInfo.isInputForm() && !formInfo.isOutputForm()) {
            return EstimationFormType.inputForm;
        }
        if (!formInfo.isInputForm() && formInfo.isOutputForm()) {
            return EstimationFormType.outputForm;
        }
        return null;
    }
}

