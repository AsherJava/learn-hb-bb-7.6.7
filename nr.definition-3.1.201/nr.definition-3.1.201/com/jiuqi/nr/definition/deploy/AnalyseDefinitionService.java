/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DataRegionItem;
import com.jiuqi.nr.definition.deploy.DeployDefinitionService;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.FCConditionDeployParam;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyseDefinitionService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    private DeployDefinitionService deployDefinitionService;
    @Autowired
    private IRunTimeFormulaVariableService runTimeFormulaVariableService;

    public DeployParams getFullDeployParamsByTask(String taskKey) throws Exception {
        DeployParams deployParams = new DeployParams();
        HashSet<String> taskKeys = new HashSet<String>();
        taskKeys.add(taskKey);
        DeployItem taskDefine = new DeployItem(taskKeys, taskKeys);
        deployParams.setTaskDefine(taskDefine);
        DeployItem formScheme = new DeployItem();
        List<DesignFormSchemeDefine> designFormSchemes = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        List<FormSchemeDefine> runTimeFormSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        if (designFormSchemes != null && designFormSchemes.size() > 0) {
            formScheme.setDesignTimeKeys(designFormSchemes.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (runTimeFormSchemes != null && runTimeFormSchemes.size() > 0) {
            formScheme.setRunTimeKeys(runTimeFormSchemes.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setFormScheme(formScheme);
        this.getDeployParams(deployParams);
        this.getFormulaConditionByTask(taskKey, deployParams);
        return deployParams;
    }

    private void getFormulaConditionByTask(String taskKey, DeployParams deployParams) {
        List<DesignFormulaCondition> designFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByTask(taskKey);
        List<FormulaCondition> formulaConditions = this.formulaRunTimeController.getFormulaConditions(taskKey);
        DeployItem condition = deployParams.getFormulaCondition();
        condition.setDesignTimeKeys(designFormulaConditions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
        condition.setRunTimeKeys(formulaConditions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
    }

    public void getDeployParams(DeployParams deployParams) throws Exception {
        DeployItem formScheme = deployParams.getFormScheme();
        this.getFormGroup(formScheme, deployParams);
        this.getFormulaVariable(formScheme, deployParams);
        DeployItem formGroup = deployParams.getFormGroup();
        this.getForm(formGroup, deployParams);
        DeployItem form = deployParams.getForm();
        this.getDataLinkMapping(form, deployParams);
        this.getDataRegion(form, deployParams);
        DeployItem dataRegion = deployParams.getDataRegion();
        this.getDataLink(dataRegion, deployParams);
        this.getFormulaScheme(formScheme, deployParams);
        DeployItem formulaScheme = deployParams.getFormulaScheme();
        this.getFormula(formulaScheme, deployParams);
        this.getFormulaConditionLink(formulaScheme, deployParams);
        this.getPrintScheme(formScheme, deployParams);
        DeployItem printScheme = deployParams.getPrintScheme();
        this.getPrintTemplate(printScheme, deployParams);
        this.getTaskLink(formScheme, deployParams);
    }

    private void getFormulaConditionLink(DeployItem formulaScheme, DeployParams deployParams) {
        if (this.itemIsNull(formulaScheme)) {
            return;
        }
        DeployItem formula = new DeployItem();
        if (formulaScheme.getDesignTimeKeys() != null) {
            HashSet designFormulaKeys = new HashSet();
            for (String formulaSchemeKey : formulaScheme.getDesignTimeKeys()) {
                designFormulaKeys.addAll(this.formulaDesignTimeController.listConditionLinkByScheme(formulaSchemeKey).stream().map(FormulaConditionLink::getFormulaKey).collect(Collectors.toList()));
            }
            formula.setDesignTimeKeys(designFormulaKeys);
        }
        if (formulaScheme.getRunTimeKeys() != null) {
            HashSet runKeys = new HashSet();
            for (String formulaSchemeKey : formulaScheme.getRunTimeKeys()) {
                runKeys.addAll(this.formulaRunTimeController.getFormulaConditionLinks(formulaSchemeKey).stream().map(FormulaConditionLink::getFormulaKey).collect(Collectors.toList()));
            }
            formula.setRunTimeKeys(runKeys);
        }
        deployParams.setFormulaConditionLink(formula);
    }

    private DeployItem getFormulaConditionLink(String formulaScheme) {
        DeployItem formula = new DeployItem();
        Set designFormulaKeys = this.formulaDesignTimeController.listConditionLinkByScheme(formulaScheme).stream().map(FormulaConditionLink::getFormulaKey).collect(Collectors.toSet());
        formula.setDesignTimeKeys(designFormulaKeys);
        Set runKeys = this.formulaRunTimeController.getFormulaConditionLinks(formulaScheme).stream().map(FormulaConditionLink::getFormulaKey).collect(Collectors.toSet());
        formula.setRunTimeKeys(runKeys);
        return formula;
    }

    private boolean itemIsNull(DeployItem item) {
        if (item == null) {
            return true;
        }
        if (item.getDesignTimeKeys() == null || item.getDesignTimeKeys().size() == 0) {
            return item.getRunTimeKeys() == null || item.getRunTimeKeys().size() == 0;
        }
        return false;
    }

    private void resetNullValue(DeployParams deployParams) {
        if (deployParams.getDataLink() == null) {
            deployParams.setDataLink(new DeployItem());
        }
        if (deployParams.getDataRegion() == null) {
            deployParams.setDataRegion(new DeployItem());
        }
        if (deployParams.getForm() == null) {
            deployParams.setForm(new DeployItem());
        }
        if (deployParams.getFormGroup() == null) {
            deployParams.setFormGroup(new DeployItem());
        }
        if (deployParams.getFormScheme() == null) {
            deployParams.setFormScheme(new DeployItem());
        }
        if (deployParams.getFormToGroupLink() == null) {
            deployParams.setFormToGroupLink(new DeployItem());
        }
        if (deployParams.getFormula() == null) {
            deployParams.setFormula(new DeployItem());
        }
        if (deployParams.getFormulaVariable() == null) {
            deployParams.setFormulaVariable(new DeployItem());
        }
        if (deployParams.getFormulaScheme() == null) {
            deployParams.setFormulaScheme(new DeployItem());
        }
        if (deployParams.getGroupToFormLink() == null) {
            deployParams.setGroupToFormLink(new DeployItem());
        }
        if (deployParams.getPrintScheme() == null) {
            deployParams.setPrintScheme(new DeployItem());
        }
        if (deployParams.getPrintTemplate() == null) {
            deployParams.setPrintTemplate(new DeployItem());
        }
        if (deployParams.getRegionSetting() == null) {
            deployParams.setRegionSetting(new DeployItem());
        }
        if (deployParams.getTaskDefine() == null) {
            deployParams.setTaskDefine(new DeployItem());
        }
        if (deployParams.getTaskLink() == null) {
            deployParams.setTaskLink(new DeployItem());
        }
    }

    private DeployItem mergeEntityView(DeployItem taskView, DeployItem formSchemeView) {
        if (taskView == null && formSchemeView == null) {
            return null;
        }
        DeployItem entityView = new DeployItem();
        HashSet designViewKeys = new HashSet();
        HashSet runTimeViewKeys = new HashSet();
        if (taskView != null) {
            designViewKeys.addAll(taskView.getDesignTimeKeys());
            runTimeViewKeys.addAll(taskView.getRunTimeKeys());
        }
        if (formSchemeView != null) {
            designViewKeys.addAll(formSchemeView.getDesignTimeKeys());
            runTimeViewKeys.addAll(formSchemeView.getRunTimeKeys());
        }
        entityView.setDesignTimeKeys(designViewKeys);
        entityView.setRunTimeKeys(runTimeViewKeys);
        return entityView;
    }

    private void appendMasterKeys(String masterKey, StringBuilder masterKeys) {
        masterKeys.append(masterKey);
        if (masterKey.charAt(masterKey.length() - 1) == ";".charAt(";".length() - 1)) {
            return;
        }
        masterKeys.append(";");
    }

    private void getTaskLink(DeployItem formScheme, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(formScheme)) {
            return;
        }
        DeployItem taskLink = new DeployItem();
        if (formScheme.getDesignTimeKeys() != null) {
            ArrayList<DesignTaskLinkDefine> designTaskLinks = new ArrayList<DesignTaskLinkDefine>();
            for (String formSchemeKey : formScheme.getDesignTimeKeys()) {
                designTaskLinks.addAll(this.designTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey));
            }
            taskLink.setDesignTimeKeys(designTaskLinks.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formScheme.getRunTimeKeys() != null) {
            ArrayList<TaskLinkDefine> runTimeTaskLinks = new ArrayList<TaskLinkDefine>();
            for (String formSchemeKey : formScheme.getRunTimeKeys()) {
                runTimeTaskLinks.addAll(this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey));
            }
            taskLink.setRunTimeKeys(runTimeTaskLinks.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setTaskLink(taskLink);
    }

    private void getPrintTemplate(DeployItem printScheme, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(printScheme)) {
            return;
        }
        DeployItem printTemplate = new DeployItem();
        if (printScheme.getDesignTimeKeys() != null) {
            ArrayList<DesignPrintTemplateDefine> designPrintTemplateDefines = new ArrayList<DesignPrintTemplateDefine>();
            for (String printSchemeKey : printScheme.getDesignTimeKeys()) {
                designPrintTemplateDefines.addAll(this.printDesignTimeController.getAllPrintTemplateInScheme(printSchemeKey));
            }
            printTemplate.setDesignTimeKeys(designPrintTemplateDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (printScheme.getRunTimeKeys() != null) {
            ArrayList<PrintTemplateDefine> runTimePrintTemplateDefines = new ArrayList<PrintTemplateDefine>();
            for (String printSchemeKey : printScheme.getRunTimeKeys()) {
                runTimePrintTemplateDefines.addAll(this.printRunTimeController.getAllPrintTemplateInScheme(printSchemeKey));
            }
            printTemplate.setRunTimeKeys(runTimePrintTemplateDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setPrintTemplate(printTemplate);
    }

    private void getPrintScheme(DeployItem formScheme, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(formScheme)) {
            return;
        }
        DeployItem printScheme = new DeployItem();
        if (formScheme.getDesignTimeKeys() != null) {
            ArrayList<DesignPrintTemplateSchemeDefine> designPrintSchemeDefines = new ArrayList<DesignPrintTemplateSchemeDefine>();
            for (String formSchemeKey : formScheme.getDesignTimeKeys()) {
                designPrintSchemeDefines.addAll(this.printDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey));
            }
            printScheme.setDesignTimeKeys(designPrintSchemeDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formScheme.getRunTimeKeys() != null) {
            ArrayList<PrintTemplateSchemeDefine> runTimePrintSchemeDefines = new ArrayList<PrintTemplateSchemeDefine>();
            for (String formSchemeKey : formScheme.getRunTimeKeys()) {
                runTimePrintSchemeDefines.addAll(this.printRunTimeController.getAllPrintSchemeByFormScheme(formSchemeKey));
            }
            printScheme.setRunTimeKeys(runTimePrintSchemeDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setPrintScheme(printScheme);
    }

    private void getFormula(DeployItem formulaScheme, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(formulaScheme)) {
            return;
        }
        DeployItem formula = new DeployItem();
        if (formulaScheme.getDesignTimeKeys() != null) {
            HashSet<String> designFormulaKeys = new HashSet<String>();
            for (String formulaSchemeKey : formulaScheme.getDesignTimeKeys()) {
                designFormulaKeys.addAll(this.deployDefinitionService.getFormulaKeysByFormulaScheme(formulaSchemeKey));
            }
            formula.setDesignTimeKeys(designFormulaKeys);
        }
        if (formulaScheme.getRunTimeKeys() != null) {
            ArrayList<FormulaDefine> runTimeFormulaDefines = new ArrayList<FormulaDefine>();
            for (String formulaSchemeKey : formulaScheme.getRunTimeKeys()) {
                runTimeFormulaDefines.addAll(this.formulaRunTimeController.getAllFormulasInScheme(formulaSchemeKey));
            }
            formula.setRunTimeKeys(runTimeFormulaDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setFormula(formula);
    }

    public void getFormulaParams(String formulaSchemeKey, String formKey, DeployParams deployParams) throws Exception {
        DeployItem formula = new DeployItem();
        if (formulaSchemeKey != null) {
            List<DesignFormulaDefine> designFormulaDefines = this.formulaDesignTimeController.getAllSoftFormulasInForm(formulaSchemeKey, formKey);
            formula.setDesignTimeKeys(designFormulaDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            List<FormulaDefine> runTimeFormulaDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
            formula.setRunTimeKeys(runTimeFormulaDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            deployParams.setFormula(formula);
            deployParams.setFormulaConditionLink(this.getFormulaConditionLink(formulaSchemeKey));
        }
    }

    private void getFormulaVariable(DeployItem formScheme, DeployParams deployParams) {
        DeployItem formulaVariable = new DeployItem();
        if (formScheme != null) {
            ArrayList<FormulaVariDefine> designFormulaVariables = new ArrayList<FormulaVariDefine>();
            for (String designKey : formScheme.getDesignTimeKeys()) {
                List<FormulaVariDefine> desFormulaVariable = this.formulaDesignTimeController.queryAllFormulaVariable(designKey);
                if (desFormulaVariable == null) continue;
                designFormulaVariables.addAll(desFormulaVariable);
            }
            formulaVariable.setDesignTimeKeys(designFormulaVariables.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            ArrayList<FormulaVariDefine> runFormulaVariables = new ArrayList<FormulaVariDefine>();
            for (String runKey : formScheme.getRunTimeKeys()) {
                List<FormulaVariDefine> runFormulaVariable = this.runTimeFormulaVariableService.queryAllFormulaVariable(runKey);
                if (runFormulaVariable == null) continue;
                runFormulaVariables.addAll(runFormulaVariable);
            }
            formulaVariable.setRunTimeKeys(runFormulaVariables.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            deployParams.setFormulaVariable(formulaVariable);
        }
    }

    private void getFormulaScheme(DeployItem formScheme, DeployParams deployParams) {
        if (this.itemIsNull(formScheme)) {
            return;
        }
        DeployItem formulaScheme = new DeployItem();
        if (formScheme.getDesignTimeKeys() != null) {
            ArrayList<DesignFormulaSchemeDefine> designFormulaSchemeDefines = new ArrayList<DesignFormulaSchemeDefine>();
            for (String formSchemeKey : formScheme.getDesignTimeKeys()) {
                designFormulaSchemeDefines.addAll(this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey));
            }
            formulaScheme.setDesignTimeKeys(designFormulaSchemeDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formScheme.getRunTimeKeys() != null) {
            ArrayList<FormulaSchemeDefine> runTimeFormulaSchemeDefines = new ArrayList<FormulaSchemeDefine>();
            for (String formSchemeKey : formScheme.getRunTimeKeys()) {
                runTimeFormulaSchemeDefines.addAll(this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey));
            }
            formulaScheme.setRunTimeKeys(runTimeFormulaSchemeDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setFormulaScheme(formulaScheme);
    }

    private void getDataLink(DeployItem dataRegion, DeployParams deployParams) {
        if (this.itemIsNull(dataRegion)) {
            return;
        }
        DeployItem dataLink = new DeployItem();
        if (dataRegion.getDesignTimeKeys() != null) {
            HashSet<String> designLinkKeys = new HashSet<String>();
            for (String dataRegionKey : dataRegion.getDesignTimeKeys()) {
                designLinkKeys.addAll(this.deployDefinitionService.getLinkKeysByRegion(dataRegionKey));
            }
            dataLink.setDesignTimeKeys(designLinkKeys);
        }
        if (dataRegion.getRunTimeKeys() != null) {
            ArrayList<DataLinkDefine> runTimeDataLinkDefines = new ArrayList<DataLinkDefine>();
            for (String dataRegionKey : dataRegion.getRunTimeKeys()) {
                runTimeDataLinkDefines.addAll(this.runTimeViewController.getAllLinksInRegion(dataRegionKey));
            }
            dataLink.setRunTimeKeys(runTimeDataLinkDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setDataLink(dataLink);
    }

    private void getDataLinkMapping(DeployItem form, DeployParams deployParams) {
        ArrayList<Object> dataLinkMappings;
        if (this.itemIsNull(form)) {
            return;
        }
        DeployItem dataLinkMapping = new DeployItem();
        if (form.getDesignTimeKeys() != null) {
            dataLinkMappings = new ArrayList<Object>();
            for (String formkey : form.getDesignTimeKeys()) {
                dataLinkMappings.addAll(this.designTimeViewController.queryDataLinkMappingByFormKey(formkey));
            }
            dataLinkMapping.setDesignTimeKeys(dataLinkMappings.stream().map(o -> o.getId()).collect(Collectors.toSet()));
        }
        if (form.getRunTimeKeys() != null) {
            dataLinkMappings = new ArrayList();
            for (String formkey : form.getDesignTimeKeys()) {
                dataLinkMappings.addAll(this.runTimeViewController.queryDataLinkMapping(formkey));
            }
            dataLinkMapping.setRunTimeKeys(dataLinkMappings.stream().map(o -> o.getId()).collect(Collectors.toSet()));
        }
        deployParams.setDataLinkMapping(dataLinkMapping);
    }

    private void getDataRegion(DeployItem form, DeployParams deployParams) {
        if (this.itemIsNull(form)) {
            return;
        }
        DeployItem dataRegion = new DeployItem();
        DeployItem regionSetting = new DeployItem();
        if (form.getDesignTimeKeys() != null) {
            ArrayList<DataRegionItem> designRegionItems = new ArrayList<DataRegionItem>();
            for (String formKey : form.getDesignTimeKeys()) {
                designRegionItems.addAll(this.deployDefinitionService.getRegionKeysByForm(formKey));
            }
            dataRegion.setDesignTimeKeys(designRegionItems.stream().map(o -> o.getRegionKey()).collect(Collectors.toSet()));
            regionSetting.setDesignTimeKeys(designRegionItems.stream().filter(o -> o.getRegionSettingKey() != null).map(o -> o.getRegionSettingKey()).collect(Collectors.toSet()));
        }
        if (form.getRunTimeKeys() != null) {
            ArrayList<DataRegionDefine> runTimeRegionDefines = new ArrayList<DataRegionDefine>();
            for (String formKey : form.getRunTimeKeys()) {
                runTimeRegionDefines.addAll(this.runTimeViewController.getAllRegionsInForm(formKey));
            }
            dataRegion.setRunTimeKeys(runTimeRegionDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            regionSetting.setRunTimeKeys(runTimeRegionDefines.stream().filter(o -> o.getRegionSettingKey() != null).map(o -> o.getRegionSettingKey()).collect(Collectors.toSet()));
        }
        deployParams.setDataRegion(dataRegion);
        deployParams.setRegionSetting(regionSetting);
    }

    private void getFormGroup(DeployItem formScheme, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(formScheme)) {
            return;
        }
        DeployItem formGroup = new DeployItem();
        if (formScheme.getDesignTimeKeys() != null) {
            ArrayList<DesignFormGroupDefine> defFormGroupDefines = new ArrayList<DesignFormGroupDefine>();
            for (String formSchemeKey : formScheme.getDesignTimeKeys()) {
                defFormGroupDefines.addAll(this.designTimeViewController.queryAllGroupsByFormScheme(formSchemeKey));
            }
            formGroup.setDesignTimeKeys(defFormGroupDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formScheme.getRunTimeKeys() != null) {
            ArrayList<FormGroupDefine> runTimeFormGroupDefines = new ArrayList<FormGroupDefine>();
            for (String formSchemeKey : formScheme.getRunTimeKeys()) {
                runTimeFormGroupDefines.addAll(this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey));
            }
            formGroup.setRunTimeKeys(runTimeFormGroupDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setFormGroup(formGroup);
        deployParams.setGroupToFormLink(formGroup);
    }

    private DeployItem getForm(DeployItem formGroup, DeployParams deployParams) throws Exception {
        if (this.itemIsNull(formGroup)) {
            return deployParams.getForm();
        }
        DeployItem form = new DeployItem();
        ArrayList<DesignFormDefine> defFormDefines = new ArrayList<DesignFormDefine>();
        ArrayList<FormDefine> runTimeFormDefines = new ArrayList<FormDefine>();
        if (formGroup.getDesignTimeKeys() != null) {
            for (String formGroupKey : formGroup.getDesignTimeKeys()) {
                defFormDefines.addAll(this.designTimeViewController.getAllFormsInGroupWithoutBinaryData(formGroupKey, true));
            }
            form.setDesignTimeKeys(defFormDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formGroup.getRunTimeKeys() != null) {
            for (String formGroupKey : formGroup.getRunTimeKeys()) {
                runTimeFormDefines.addAll(this.runTimeViewController.getAllFormsInGroupWithoutOrder(formGroupKey, true));
            }
            form.setRunTimeKeys(runTimeFormDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        deployParams.setForm(form);
        deployParams.setFormToGroupLink(form);
        return form;
    }

    private void addEntityForms(List<TableDefine> runTimeEntityTables, DeployItem form, DeployItem dataRegion, DeployItem dataLink) {
        HashSet<String> formKeys = new HashSet<String>();
        HashSet regionKeys = new HashSet();
        HashSet linkKeys = new HashSet();
        for (TableDefine runTimeTable : runTimeEntityTables) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(runTimeTable.getKey());
            if (formDefine == null) continue;
            formKeys.add(formDefine.getKey());
            List<DataRegionDefine> regionDefines = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            regionKeys.addAll(regionDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            ArrayList<DataLinkDefine> linkDefines = new ArrayList<DataLinkDefine>();
            for (DataRegionDefine regionDefine : regionDefines) {
                linkDefines.addAll(this.runTimeViewController.getAllLinksInRegion(regionDefine.getKey()));
            }
            linkKeys.addAll(linkDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formKeys.size() <= 0) {
            return;
        }
        form.mergeRunTimeKeys(formKeys);
        dataRegion.mergeRunTimeKeys(regionKeys);
        dataLink.mergeRunTimeKeys(linkKeys);
    }

    private void addDesignEntityForms(List<DesignTableDefine> designEntityTables, DeployItem form, DeployItem dataRegion, DeployItem dataLink) {
        HashSet<String> formKeys = new HashSet<String>();
        HashSet regionKeys = new HashSet();
        HashSet linkKeys = new HashSet();
        for (DesignTableDefine designTableDefine : designEntityTables) {
            DesignFormDefine formDefine = this.designTimeViewController.queryFormById(designTableDefine.getKey());
            if (formDefine == null) continue;
            formKeys.add(formDefine.getKey());
            List<DesignDataRegionDefine> regionDefines = this.designTimeViewController.getAllRegionsInForm(formDefine.getKey());
            regionKeys.addAll(regionDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
            ArrayList<DesignDataLinkDefine> linkDefines = new ArrayList<DesignDataLinkDefine>();
            for (DesignDataRegionDefine regionDefine : regionDefines) {
                linkDefines.addAll(this.designTimeViewController.getAllLinksInRegion(regionDefine.getKey()));
            }
            linkKeys.addAll(linkDefines.stream().map(o -> o.getKey()).collect(Collectors.toSet()));
        }
        if (formKeys.size() <= 0) {
            return;
        }
        form.mergeDesignKeys(formKeys);
        dataRegion.mergeDesignKeys(regionKeys);
        dataLink.mergeDesignKeys(linkKeys);
    }

    public DeployParams getIncrementalDeployParams() {
        return new DeployParams();
    }

    public void getFormulaConditionDeployParam(FCConditionDeployParam deployParam) {
        this.getFormulaConditionDeployParam(deployParam, null);
    }

    public void getFormulaConditionDeployParam(FCConditionDeployParam deployParam, String formKey) {
        String formulaSchemeKey = deployParam.getFormulaScheme();
        if (formulaSchemeKey == null) {
            return;
        }
        HashSet<String> dKeys = new HashSet<String>();
        HashSet<String> rKeys = new HashSet<String>();
        this.getFormulaConditionInForm(formulaSchemeKey, formKey, dKeys, rKeys);
        deployParam.setDesignKeys(dKeys);
        deployParam.setRunKeys(rKeys);
        HashSet<String> keys = new HashSet<String>(dKeys);
        keys.addAll(rKeys);
        this.analyzeItem(keys, dKeys, rKeys);
        List<FormulaConditionLink> links = this.getNeedReloadFormulaConditionLink(dKeys, rKeys);
        deployParam.setRefreshSchemeKeys(links.stream().map(FormulaConditionLink::getFormulaSchemeKey).collect(Collectors.toSet()));
    }

    private List<FormulaConditionLink> getNeedReloadFormulaConditionLink(Set<String> dKeys, Set<String> rKeys) {
        ArrayList<FormulaConditionLink> links = new ArrayList<FormulaConditionLink>();
        links.addAll(this.formulaDesignTimeController.listConditionLinksByCondition(new ArrayList<String>(dKeys)));
        links.addAll(this.formulaRunTimeController.getFormulaConditionLinksByCondition(new ArrayList<String>(rKeys)));
        return links;
    }

    private void getFormulaConditionInForm(String formulaSchemeKey, String formKey, Set<String> dKeys, Set<String> rKeys) {
        List<DesignFormulaConditionLink> designFormulaConditionLinks = this.formulaDesignTimeController.listConditionLinkByScheme(formulaSchemeKey);
        List<FormulaConditionLink> formulaConditionLinks = this.formulaRunTimeController.getFormulaConditionLinks(formulaSchemeKey);
        HashSet dFormulaKeySet = new HashSet();
        HashSet rFormulaKeySet = new HashSet();
        if (formKey != null) {
            try {
                List<DesignFormulaDefine> designFormulaDefines = this.formulaDesignTimeController.getAllSoftFormulasInForm(formulaSchemeKey, formKey);
                designFormulaDefines.forEach(d -> dFormulaKeySet.add(d.getKey()));
                List<FormulaDefine> list = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
                list.forEach(d -> rFormulaKeySet.add(d.getKey()));
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
        }
        for (DesignFormulaConditionLink designFormulaConditionLink : designFormulaConditionLinks) {
            if (formKey != null && !dFormulaKeySet.contains(designFormulaConditionLink.getFormulaKey())) continue;
            dKeys.add(designFormulaConditionLink.getConditionKey());
        }
        for (FormulaConditionLink formulaConditionLink : formulaConditionLinks) {
            if (formKey != null && !rFormulaKeySet.contains(formulaConditionLink.getFormulaKey())) continue;
            rKeys.add(formulaConditionLink.getConditionKey());
        }
    }

    private void analyzeItem(Set<String> keys, Set<String> dKeys, Set<String> rKeys) {
        List<DesignFormulaCondition> designFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByKey(new ArrayList<String>(keys));
        List<FormulaCondition> formulaConditions = this.formulaRunTimeController.getFormulaConditions(new ArrayList<String>(keys));
        this.analyzeItem(designFormulaConditions, formulaConditions, dKeys, rKeys);
    }

    public void getFormulaConditionDeployParamByTask(FCConditionDeployParam deployParam, String task, String ... conditionKeys) {
        List<FormulaCondition> formulaConditions;
        List<DesignFormulaCondition> designFormulaConditions;
        if (conditionKeys == null || conditionKeys.length == 0) {
            designFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByTask(task);
            formulaConditions = this.formulaRunTimeController.getFormulaConditions(task);
        } else {
            List<String> collect = Arrays.stream(conditionKeys).distinct().collect(Collectors.toList());
            designFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByKey(collect);
            formulaConditions = this.formulaRunTimeController.getFormulaConditions(collect);
        }
        Set<String> dKeys = designFormulaConditions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        Set<String> rKeys = formulaConditions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        deployParam.setDesignKeys(dKeys);
        deployParam.setRunKeys(rKeys);
        this.analyzeItem(designFormulaConditions, formulaConditions, dKeys, rKeys);
        this.solutionFccParam(deployParam);
    }

    private void solutionFccParam(FCConditionDeployParam deployParam) {
        List<DesignFormulaConditionLink> designFormulaConditionLinks = this.formulaDesignTimeController.listConditionLinksByCondition(new ArrayList<String>(deployParam.getDesignKeys()));
        List<FormulaConditionLink> formulaConditionLinks = this.formulaRunTimeController.getFormulaConditionLinksByCondition(new ArrayList<String>(deployParam.getRunKeys()));
        Set<DesignFormulaConditionLink> designLinks = deployParam.getDesignLinks();
        Set<FormulaConditionLink> runLinks = deployParam.getRunLinks();
        Set<String> refreshSchemeKeys = deployParam.getRefreshSchemeKeys();
        for (DesignFormulaConditionLink designFormulaConditionLink : designFormulaConditionLinks) {
            designLinks.add(designFormulaConditionLink);
            refreshSchemeKeys.add(designFormulaConditionLink.getFormulaSchemeKey());
        }
        for (FormulaConditionLink formulaConditionLink : formulaConditionLinks) {
            runLinks.add(formulaConditionLink);
            refreshSchemeKeys.add(formulaConditionLink.getFormulaSchemeKey());
        }
    }

    private void analyzeItem(List<DesignFormulaCondition> designFormulaConditions, List<FormulaCondition> formulaConditions, Set<String> dKeys, Set<String> rKeys) {
        Map<String, Date> dateMap = formulaConditions.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IMetaItem::getUpdateTime));
        for (DesignFormulaCondition condition : designFormulaConditions) {
            Date date = dateMap.get(condition.getKey());
            if (date == null || !date.equals(condition.getUpdateTime())) continue;
            dKeys.remove(condition.getKey());
            rKeys.remove(condition.getKey());
        }
    }
}

