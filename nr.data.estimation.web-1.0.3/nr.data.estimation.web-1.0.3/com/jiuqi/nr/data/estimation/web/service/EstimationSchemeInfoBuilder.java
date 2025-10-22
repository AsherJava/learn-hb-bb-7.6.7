/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeSet;
import com.jiuqi.nr.data.estimation.web.response.EstimationSchemeInfo;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EstimationSchemeInfoBuilder {
    private EstimationSchemeInfo schemeData;
    private IRunTimeViewController runTimeViewController;
    private IFormulaRunTimeController formulaRunTimeController;

    public EstimationSchemeInfoBuilder(IRunTimeViewController runTimeViewController, IFormulaRunTimeController formulaRunTimeController) {
        this.runTimeViewController = runTimeViewController;
        this.formulaRunTimeController = formulaRunTimeController;
    }

    public EstimationSchemeInfo build(IEstimationSchemeTemplate estScheme) {
        this.schemeData = new EstimationSchemeInfo();
        this.schemeData.setKey(estScheme.getKey());
        this.schemeData.setCode(estScheme.getCode());
        this.schemeData.setTitle(estScheme.getTitle());
        this.schemeData.setTaskId(estScheme.getTaskDefine().getKey());
        this.schemeData.setFormSchemeId(estScheme.getFormSchemeDefine().getKey());
        this.schemeData.setFormInfos(this.buildFormInfos(estScheme.getFormSchemeDefine(), estScheme.getEstimationForms()));
        this.schemeData.setAccessFormulaSchemes(this.buildAccessFormulaSchemes(estScheme.getFormSchemeDefine(), estScheme.getAccessFormulaSchemes()));
        this.schemeData.setCalcFormulaSchemes(this.buildCalcFormulaSchemes(estScheme.getFormSchemeDefine(), estScheme.getCalcFormulaSchemes()));
        return this.schemeData;
    }

    public EstimationSchemeInfo build(FormSchemeDefine formScheme) {
        this.schemeData = new EstimationSchemeInfo();
        this.schemeData.setTaskId(formScheme.getTaskKey());
        this.schemeData.setFormSchemeId(formScheme.getKey());
        this.schemeData.setFormInfos(this.buildFormInfos(formScheme));
        this.schemeData.setAccessFormulaSchemes(this.buildAccessFormulaSchemes(formScheme));
        this.schemeData.setCalcFormulaSchemes(this.buildCalcFormulaSchemes(formScheme));
        return this.schemeData;
    }

    private List<EstimationFormInfo> buildFormInfos(FormSchemeDefine formScheme) {
        ArrayList<EstimationFormInfo> eForms = new ArrayList<EstimationFormInfo>();
        List groups = this.runTimeViewController.getAllFormGroupsInFormScheme(formScheme.getKey());
        for (FormGroupDefine groupDefine : groups) {
            eForms.add(this.newEstimationFormGroup(groupDefine));
            eForms.addAll(this.newEstimationFormSet(groupDefine));
        }
        return eForms;
    }

    private EstimationFormInfo newEstimationFormGroup(FormGroupDefine groupDefine) {
        EstimationFormInfo groupInfo = new EstimationFormInfo();
        groupInfo.setKey(groupDefine.getKey());
        groupInfo.setCode(groupDefine.getCode());
        groupInfo.setTitle(groupDefine.getTitle());
        groupInfo.setType("Group");
        return groupInfo;
    }

    private List<EstimationFormInfo> newEstimationFormSet(FormGroupDefine groupDefine) {
        try {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(groupDefine.getKey());
            if (allFormsInGroup != null) {
                return allFormsInGroup.stream().map(formDefine -> {
                    EstimationFormInfo formInfo = new EstimationFormInfo();
                    formInfo.setKey(formDefine.getKey());
                    formInfo.setCode(formDefine.getFormCode());
                    formInfo.setTitle(formDefine.getTitle());
                    formInfo.setType("Form");
                    return formInfo;
                }).collect(Collectors.toList());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return new ArrayList<EstimationFormInfo>();
    }

    private List<EstimationFormInfo> buildFormInfos(FormSchemeDefine formScheme, List<IEstimationForm> checkForms) {
        List<EstimationFormInfo> eForms = this.buildFormInfos(formScheme);
        HashMap formTypeMap = new HashMap();
        checkForms.forEach(form -> formTypeMap.put(form.getFormDefine().getKey(), form.getFormType()));
        return eForms.stream().peek(estimationFormInfo -> {
            estimationFormInfo.setInputForm(EstimationFormType.inputForm == formTypeMap.get(estimationFormInfo.getKey()));
            estimationFormInfo.setOutputForm(EstimationFormType.outputForm == formTypeMap.get(estimationFormInfo.getKey()));
        }).collect(Collectors.toList());
    }

    private EstimationFormulaSchemeSet buildAccessFormulaSchemes(FormSchemeDefine formScheme) {
        EstimationFormulaSchemeSet formulaSchemeSet = new EstimationFormulaSchemeSet();
        List formulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formScheme.getKey());
        formulaSchemeSet.setAllFormulaSchemes(formulaSchemes.stream().map(this::newEstimationFormulaSchemeInfo).collect(Collectors.toList()));
        return formulaSchemeSet;
    }

    private EstimationFormulaSchemeSet buildAccessFormulaSchemes(FormSchemeDefine formScheme, List<FormulaSchemeDefine> checkFormulaSchemes) {
        EstimationFormulaSchemeSet formulaSchemeSet = this.buildAccessFormulaSchemes(formScheme);
        List usedFormulaSchemes = checkFormulaSchemes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        formulaSchemeSet.setCheckFormulaSchemes(usedFormulaSchemes.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        return formulaSchemeSet;
    }

    private EstimationFormulaSchemeSet buildCalcFormulaSchemes(FormSchemeDefine formScheme) {
        EstimationFormulaSchemeSet formulaSchemeSet = new EstimationFormulaSchemeSet();
        List formulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formScheme.getKey());
        formulaSchemeSet.setAllFormulaSchemes(formulaSchemes.stream().map(this::newEstimationFormulaSchemeInfo).collect(Collectors.toList()));
        return formulaSchemeSet;
    }

    private EstimationFormulaSchemeSet buildCalcFormulaSchemes(FormSchemeDefine formScheme, List<FormulaSchemeDefine> checkFormulaSchemes) {
        EstimationFormulaSchemeSet formulaSchemeSet = this.buildCalcFormulaSchemes(formScheme);
        List usedFormulaSchemes = checkFormulaSchemes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        formulaSchemeSet.setCheckFormulaSchemes(usedFormulaSchemes.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        return formulaSchemeSet;
    }

    private EstimationFormulaSchemeInfo newEstimationFormulaSchemeInfo(FormulaSchemeDefine formulaScheme) {
        EstimationFormulaSchemeInfo eFormulaSchemeInfo = new EstimationFormulaSchemeInfo();
        eFormulaSchemeInfo.setFormulaSchemeId(formulaScheme.getKey());
        eFormulaSchemeInfo.setFormulaSchemeTitle(formulaScheme.getTitle());
        return eFormulaSchemeInfo;
    }
}

