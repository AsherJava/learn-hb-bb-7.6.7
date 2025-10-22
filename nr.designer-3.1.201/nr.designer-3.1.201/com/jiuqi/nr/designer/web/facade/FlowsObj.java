/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowsObj {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="TaskId")
    private String taskId;
    @JsonProperty(value="FlowType")
    private int flowType;
    @JsonProperty(value="SubmitEntityTables")
    private String submitEntityTables;
    @JsonProperty(value="SetSubTable")
    private boolean subTable;
    @JsonProperty(value="SetDataConfirm")
    private boolean dataConfirm;
    @JsonProperty(value="SetSubmitExplain")
    private boolean submitExplain;
    @JsonProperty(value="ForceSubmitExplain")
    private boolean forceSubmitExplain;
    @JsonProperty(value="SetReturnVersion")
    private boolean returnVersion;
    @JsonProperty(value="SetAllowTakeBack")
    private boolean allowTakeBack;
    @JsonProperty(value="SetAllowModifyData")
    private boolean allowModifyData;
    @JsonProperty(value="SetUnitSubmitForCensorship")
    private boolean unitSubmitForCensorship;
    @JsonProperty(value="SelectedRoleId")
    private String selectedRoleId;
    @JsonProperty(value="UnitSubmitForForce")
    private boolean unitSubmitForForce;
    @JsonProperty(value="SelectedRoleForceId")
    private String selectedRoleForceId;
    private boolean checkBeforeReporting;
    private int checkBeforeReportingType;
    private String checkBeforeReportingCustom;
    private boolean backDescriptionNeedWrite;
    private boolean allowFormBack;
    private boolean stepByStepReport;
    private boolean stepByStepBack;
    @JsonProperty(value="FilterCondition")
    private String filterCondition;
    @JsonProperty(value="ErroStatus")
    private String erroStatus;
    @JsonProperty(value="PromptStatus")
    private String promptStatus;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="ApplyReturn")
    private boolean applyReturn;
    private boolean checkBeforeFormula;
    private String checkBeforeFormulaValue;
    private boolean checkBeforeCheck = true;
    private boolean mulCheckBeforeCheck = false;
    private String checkBeforeCheckValue;
    private int checkBeforeCheckType;
    private String checkBeforeCheckCustom;
    private boolean submitAfterFormula;
    private String submitAfterFormulaValue;
    @JsonProperty(value="MessageTemplate")
    private String messageTemplate;
    private boolean goBackAllSup;
    private boolean openForceControl;
    @JsonProperty(value="SpecialAudit")
    private boolean specialAudit = false;
    @JsonProperty(value="OpenBackType")
    private boolean openBackType = false;
    @JsonProperty(value="BackTypeEntity")
    private String backTypeEntity;
    @JsonProperty(value="ReturnExplain")
    private boolean returnExplain = false;
    @JsonProperty(value="AllowTakeBackForSubmit")
    private boolean allowTakeBackForSubmit = false;
    @JsonProperty(value="FlowsSettingIsExtend")
    private boolean flowsSettingIsExtend = true;
    @JsonProperty(value="EntitiesIsExtend")
    private boolean entitiesIsExtend = true;
    @JsonProperty(value="DefaultButtonName")
    private boolean defaultButtonName;
    @JsonProperty(value="DefaultButtonNameConfig")
    private String defaultButtonNameConfig;
    @JsonProperty(value="DefaultNodeName")
    private boolean defaultNodeName;
    @JsonProperty(value="DefaultNodeNameConfig")
    private String defaultNodeNameConfig;
    @JsonProperty(value="SendMessageMail")
    private String sendMessageMail;
    @JsonProperty(value="WorkFlowType")
    private String WorkFlowType;
    @JsonProperty(value="StepReportType")
    private String StepReportType;

    public void setOpenForceControl(boolean openForceControl) {
        this.openForceControl = openForceControl;
    }

    public boolean isOpenForceControl() {
        return this.openForceControl;
    }

    public boolean isReturnExplain() {
        return this.returnExplain;
    }

    public void setReturnExplain(boolean returnExplain) {
        this.returnExplain = returnExplain;
    }

    public boolean isAllowTakeBackForSubmit() {
        return this.allowTakeBackForSubmit;
    }

    public void setAllowTakeBackForSubmit(boolean allowTakeBackForSubmit) {
        this.allowTakeBackForSubmit = allowTakeBackForSubmit;
    }

    public boolean getGoBackAllSup() {
        return this.goBackAllSup;
    }

    public void setGoBackAllSup(boolean goBackAllSup) {
        this.goBackAllSup = goBackAllSup;
    }

    public boolean isSpecialAudit() {
        return this.specialAudit;
    }

    public void setSpecialAudit(boolean specialAudit) {
        this.specialAudit = specialAudit;
    }

    public int getCheckBeforeReportingType() {
        return this.checkBeforeReportingType;
    }

    public void setCheckBeforeReportingType(int checkBeforeReportingType) {
        this.checkBeforeReportingType = checkBeforeReportingType;
    }

    public String getCheckBeforeReportingCustom() {
        return this.checkBeforeReportingCustom;
    }

    public void setCheckBeforeReportingCustom(String checkBeforeReportingCustom) {
        this.checkBeforeReportingCustom = checkBeforeReportingCustom;
    }

    public String getCheckBeforeCheckCustom() {
        return this.checkBeforeCheckCustom;
    }

    public void setCheckBeforeCheckCustom(String checkBeforeCheckCustom) {
        this.checkBeforeCheckCustom = checkBeforeCheckCustom;
    }

    public boolean isSubmitAfterFormula() {
        return this.submitAfterFormula;
    }

    public void setSubmitAfterFormula(boolean submitAfterFormula) {
        this.submitAfterFormula = submitAfterFormula;
    }

    public String getSubmitAfterFormulaValue() {
        return this.submitAfterFormulaValue;
    }

    public void setSubmitAfterFormulaValue(String submitAfterFormulaValue) {
        this.submitAfterFormulaValue = submitAfterFormulaValue;
    }

    public boolean getCheckBeforeFormula() {
        return this.checkBeforeFormula;
    }

    public void setCheckBeforeFormula(boolean checkBeforeFormula) {
        this.checkBeforeFormula = checkBeforeFormula;
    }

    public String getCheckBeforeFormulaValue() {
        return this.checkBeforeFormulaValue;
    }

    public void setCheckBeforeFormulaValue(String checkBeforeFormulaValue) {
        this.checkBeforeFormulaValue = checkBeforeFormulaValue;
    }

    public boolean getCheckBeforeCheck() {
        return this.checkBeforeCheck;
    }

    public void setCheckBeforeCheck(boolean checkBeforeCheck) {
        this.checkBeforeCheck = checkBeforeCheck;
    }

    public boolean getMulCheckBeforeCheck() {
        return this.mulCheckBeforeCheck;
    }

    public void setMulCheckBeforeCheck(boolean mulCheckBeforeCheck) {
        this.mulCheckBeforeCheck = mulCheckBeforeCheck;
    }

    public String getCheckBeforeCheckValue() {
        return this.checkBeforeCheckValue;
    }

    public void setCheckBeforeCheckValue(String checkBeforeCheckValue) {
        this.checkBeforeCheckValue = checkBeforeCheckValue;
    }

    public int getCheckBeforeCheckType() {
        return this.checkBeforeCheckType;
    }

    public void setCheckBeforeCheckType(int checkBeforeCheckType) {
        this.checkBeforeCheckType = checkBeforeCheckType;
    }

    public boolean isApplyReturn() {
        return this.applyReturn;
    }

    public void setApplyReturn(boolean applyReturn) {
        this.applyReturn = applyReturn;
    }

    public String getStepReportType() {
        return this.StepReportType;
    }

    public void setStepReportType(String stepReportType) {
        this.StepReportType = stepReportType;
    }

    public String getWorkFlowType() {
        return this.WorkFlowType;
    }

    public void setWorkFlowType(String workFlowType) {
        this.WorkFlowType = workFlowType;
    }

    public String getSendMessageMail() {
        return this.sendMessageMail;
    }

    public void setSendMessageMail(String sendMessageMail) {
        this.sendMessageMail = sendMessageMail;
    }

    public String getDefaultButtonNameConfig() {
        return this.defaultButtonNameConfig;
    }

    public void setDefaultButtonNameConfig(String defaultButtonNameConfig) {
        this.defaultButtonNameConfig = defaultButtonNameConfig;
    }

    public boolean isDefaultButtonName() {
        return this.defaultButtonName;
    }

    public void setDefaultButtonName(boolean defaultButtonName) {
        this.defaultButtonName = defaultButtonName;
    }

    @JsonIgnore
    public boolean getFlowsSettingIsExtend() {
        return this.flowsSettingIsExtend;
    }

    @JsonIgnore
    public void setFlowsSettingIsExtend(boolean flowsSettingIsExtend) {
        this.flowsSettingIsExtend = flowsSettingIsExtend;
    }

    @JsonIgnore
    public boolean getEntitiesIsExtend() {
        return this.entitiesIsExtend;
    }

    @JsonIgnore
    public void setEntitiesIsExtend(boolean entitiesIsExtend) {
        this.entitiesIsExtend = entitiesIsExtend;
    }

    @JsonIgnore
    public String getID() {
        return this.id;
    }

    @JsonIgnore
    public void setID(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @JsonIgnore
    public String getTaskId() {
        return this.taskId;
    }

    @JsonIgnore
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonIgnore
    public int getFlowType() {
        return this.flowType;
    }

    @JsonIgnore
    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    @JsonIgnore
    public boolean isSubTable() {
        return this.subTable;
    }

    @JsonIgnore
    public void setSubTable(boolean subTable) {
        this.subTable = subTable;
    }

    @JsonIgnore
    public boolean isDataConfirm() {
        return this.dataConfirm;
    }

    @JsonIgnore
    public void setDataConfirm(boolean dataConfirm) {
        this.dataConfirm = dataConfirm;
    }

    @JsonIgnore
    public boolean isSubmitExplain() {
        return this.submitExplain;
    }

    @JsonIgnore
    public void setSubmitExplain(boolean submitExplain) {
        this.submitExplain = submitExplain;
    }

    @JsonIgnore
    public boolean isReturnVersion() {
        return this.returnVersion;
    }

    @JsonIgnore
    public void setReturnVersion(boolean returnVersion) {
        this.returnVersion = returnVersion;
    }

    @JsonIgnore
    public boolean isAllowTakeBack() {
        return this.allowTakeBack;
    }

    @JsonIgnore
    public void setAllowTakeBack(boolean allowTakeBack) {
        this.allowTakeBack = allowTakeBack;
    }

    @JsonIgnore
    public boolean isAllowModifyData() {
        return this.allowModifyData;
    }

    @JsonIgnore
    public void setAllowModifyData(boolean allowModifyData) {
        this.allowModifyData = allowModifyData;
    }

    @JsonIgnore
    public boolean isUnitSubmitForCensorship() {
        return this.unitSubmitForCensorship;
    }

    @JsonIgnore
    public void setUnitSubmitForCensorship(boolean unitSubmitForCensorship) {
        this.unitSubmitForCensorship = unitSubmitForCensorship;
    }

    @JsonIgnore
    public String getSelectedRoleId() {
        return this.selectedRoleId;
    }

    @JsonIgnore
    public void setSelectedRoleId(String selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    @JsonIgnore
    public boolean isUnitSubmitForForce() {
        return this.unitSubmitForForce;
    }

    @JsonIgnore
    public void setUnitSubmitForForce(boolean unitSubmitForForce) {
        this.unitSubmitForForce = unitSubmitForForce;
    }

    public String getSelectedRoleForceId() {
        return this.selectedRoleForceId;
    }

    public void setSelectedRoleForceId(String selectedRoleForceId) {
        this.selectedRoleForceId = selectedRoleForceId;
    }

    @JsonIgnore
    public String getSubmitEntityTables() {
        return this.submitEntityTables;
    }

    @JsonIgnore
    public void setSubmitEntityTables(String submitEntityTables) {
        this.submitEntityTables = submitEntityTables;
    }

    @JsonIgnore
    public String getFilterCondition() {
        return this.filterCondition;
    }

    @JsonIgnore
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @JsonIgnore
    public String getErroStatus() {
        return this.erroStatus;
    }

    @JsonIgnore
    public void setErroStatus(String erroStatus) {
        this.erroStatus = erroStatus;
    }

    @JsonIgnore
    public String getPromptStatus() {
        return this.promptStatus;
    }

    @JsonIgnore
    public void setPromptStatus(String promptStatus) {
        this.promptStatus = promptStatus;
    }

    public boolean getCheckBeforeReporting() {
        return this.checkBeforeReporting;
    }

    public void setCheckBeforeReporting(boolean checkBeforeReporting) {
        this.checkBeforeReporting = checkBeforeReporting;
    }

    public boolean getBackDescriptionNeedWrite() {
        return this.backDescriptionNeedWrite;
    }

    public void setBackDescriptionNeedWrite(boolean backDescriptionNeedWrite) {
        this.backDescriptionNeedWrite = backDescriptionNeedWrite;
    }

    public boolean getAllowFormBack() {
        return this.allowFormBack;
    }

    public void setAllowFormBack(boolean allowFormBack) {
        this.allowFormBack = allowFormBack;
    }

    public boolean getStepByStepReport() {
        return this.stepByStepReport;
    }

    public void setStepByStepReport(boolean stepByStepReport) {
        this.stepByStepReport = stepByStepReport;
    }

    public boolean getStepByStepBack() {
        return this.stepByStepBack;
    }

    public void setStepByStepBack(boolean stepByStepBack) {
        this.stepByStepBack = stepByStepBack;
    }

    public String getMessageTemplate() {
        return this.messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public boolean isForceSubmitExplain() {
        return this.forceSubmitExplain;
    }

    public void setForceSubmitExplain(boolean forceSubmitExplain) {
        this.forceSubmitExplain = forceSubmitExplain;
    }

    public boolean isDefaultNodeName() {
        return this.defaultNodeName;
    }

    public void setDefaultNodeName(boolean defaultNodeName) {
        this.defaultNodeName = defaultNodeName;
    }

    public String getDefaultNodeNameConfig() {
        return this.defaultNodeNameConfig;
    }

    public void setDefaultNodeNameConfig(String defaultNodeNameConfig) {
        this.defaultNodeNameConfig = defaultNodeNameConfig;
    }

    public boolean isOpenBackType() {
        return this.openBackType;
    }

    public void setOpenBackType(boolean openBackType) {
        this.openBackType = openBackType;
    }

    public String getBackTypeEntity() {
        return this.backTypeEntity;
    }

    public void setBackTypeEntity(String backTypeEntity) {
        this.backTypeEntity = backTypeEntity;
    }
}

