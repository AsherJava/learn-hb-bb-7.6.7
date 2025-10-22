/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.io.Serializable;

public class DesignFlowSettingDefine
implements Serializable {
    private static final long serialVersionUID = 2620928509114292720L;
    private boolean setSubTable;
    private boolean dataConfirm;
    private boolean submitExplain;
    private boolean forceSubmitExplain;
    private boolean returnVersion;
    private boolean allowTakeBack;
    private boolean allowModifyData;
    private boolean unitSubmitForCensorship;
    private String selectedRoleKey;
    private boolean unitSubmitForForce;
    private boolean stepByStepReport;
    private boolean stepByStepBack;
    private boolean checkBeforeReporting;
    private ReportAuditType checkBeforeReportingType = ReportAuditType.NONE;
    private String checkBeforeReportingCustom;
    private boolean reportBeforeOperation;
    private String reportBeforeOperationValue;
    private boolean reportBeforeAudit = true;
    private boolean mulCheckBeforeCheck;
    private ReportAuditType reportBeforeAuditType = ReportAuditType.NONE;
    private String reportBeforeAuditCustom;
    private String reportBeforeAuditValue;
    private boolean backDescriptionNeedWrite;
    private boolean allowFormBack;
    private String selectedRoleForceKey;
    public String erroStatus;
    public String promptStatus;
    public boolean defaultButtonName;
    public String defaultButtonNameConfig;
    public boolean defaultNodeName;
    public String defaultNodeNameConfig;
    public String sendMessageMail;
    public String stepReportType = "1";
    public WorkFlowType wordFlowType = WorkFlowType.ENTITY;
    public boolean specialAudit;
    private boolean applyReturn;
    private boolean submitAfterFormula;
    private String submitAfterFormulaValue;
    private String messageTemplate;
    private boolean goBackAllSup;
    private boolean openForceControl;
    private boolean openBackType;
    private String backTypeEntity;
    private boolean returnExplain;
    private boolean allowTakeBackForSubmit;

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

    public boolean isOpenForceControl() {
        return this.openForceControl;
    }

    public void setOpenForceControl(boolean openForceControl) {
        this.openForceControl = openForceControl;
    }

    public String getMessageTemplate() {
        return this.messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public boolean getSubmitAfterFormula() {
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

    public boolean isApplyReturn() {
        return this.applyReturn;
    }

    public void setApplyReturn(boolean applyReturn) {
        this.applyReturn = applyReturn;
    }

    public String getReportBeforeOperationValue() {
        return this.reportBeforeOperationValue;
    }

    public void setReportBeforeOperationValue(String reportBeforeOperationValue) {
        this.reportBeforeOperationValue = reportBeforeOperationValue;
    }

    public boolean getReportBeforeAudit() {
        return this.reportBeforeAudit;
    }

    public void setReportBeforeAudit(boolean reportBeforeAudit) {
        this.reportBeforeAudit = reportBeforeAudit;
    }

    public boolean getMulCheckBeforeCheck() {
        return this.mulCheckBeforeCheck;
    }

    public void setMulCheckBeforeCheck(boolean mulCheckBeforeCheck) {
        this.mulCheckBeforeCheck = mulCheckBeforeCheck;
    }

    public String getReportBeforeAuditValue() {
        return this.reportBeforeAuditValue;
    }

    public void setReportBeforeAuditValue(String reportBeforeAuditValue) {
        this.reportBeforeAuditValue = reportBeforeAuditValue;
    }

    public ReportAuditType getReportBeforeAuditType() {
        return this.reportBeforeAuditType;
    }

    public void setReportBeforeAuditType(ReportAuditType reportBeforeAuditType) {
        this.reportBeforeAuditType = reportBeforeAuditType;
    }

    public boolean getReportBeforeOperation() {
        return this.reportBeforeOperation;
    }

    public void setReportBeforeOperation(boolean reportBeforeOperation) {
        this.reportBeforeOperation = reportBeforeOperation;
    }

    public String getStepReportType() {
        return this.stepReportType;
    }

    public void setStepReportType(String stepReportType) {
        this.stepReportType = stepReportType;
    }

    public WorkFlowType getWordFlowType() {
        return this.wordFlowType;
    }

    public void setWordFlowType(WorkFlowType wordFlowType) {
        this.wordFlowType = wordFlowType;
    }

    public String getSendMessageMail() {
        return this.sendMessageMail;
    }

    public void setSendMessageMail(String sendMessageMail) {
        this.sendMessageMail = sendMessageMail;
    }

    public boolean isDefaultButtonName() {
        return this.defaultButtonName;
    }

    public void setDefaultButtonName(boolean defaultButtonName) {
        this.defaultButtonName = defaultButtonName;
    }

    public String getDefaultButtonNameConfig() {
        return this.defaultButtonNameConfig;
    }

    public void setDefaultButtonNameConfig(String defaultButtonNameConfig) {
        this.defaultButtonNameConfig = defaultButtonNameConfig;
    }

    public boolean isSubTable() {
        return this.setSubTable;
    }

    public void setSubTable(boolean subTable) {
        this.setSubTable = subTable;
    }

    public boolean isDataConfirm() {
        return this.dataConfirm;
    }

    public void setDataConfirm(boolean dataConfirm) {
        this.dataConfirm = dataConfirm;
    }

    public boolean isSubmitExplain() {
        return this.submitExplain;
    }

    public void setSubmitExplain(boolean submitExplain) {
        this.submitExplain = submitExplain;
    }

    public boolean isForceSubmitExplain() {
        return this.forceSubmitExplain;
    }

    public void setForceSubmitExplain(boolean forceSubmitExplain) {
        this.forceSubmitExplain = forceSubmitExplain;
    }

    public boolean isReturnVersion() {
        return this.returnVersion;
    }

    public void setReturnVersion(boolean returnVersion) {
        this.returnVersion = returnVersion;
    }

    public boolean isAllowTakeBack() {
        return this.allowTakeBack;
    }

    public void setAllowTakeBack(boolean allowTakeBack) {
        this.allowTakeBack = allowTakeBack;
    }

    public boolean isAllowModifyData() {
        return this.allowModifyData;
    }

    public void setAllowModifyData(boolean allowModifyData) {
        this.allowModifyData = allowModifyData;
    }

    public boolean isUnitSubmitForCensorship() {
        return this.unitSubmitForCensorship;
    }

    public void setUnitSubmitForCensorship(boolean unitSubmitForCensorship) {
        this.unitSubmitForCensorship = unitSubmitForCensorship;
    }

    public String getSelectedRoleKey() {
        return this.selectedRoleKey;
    }

    public void setSelectedRoleKey(String selectedRoleKey) {
        this.selectedRoleKey = selectedRoleKey;
    }

    public String getErroStatus() {
        return this.erroStatus;
    }

    public void setErroStatus(String erroStatus) {
        this.erroStatus = erroStatus;
    }

    public String getPromptStatus() {
        return this.promptStatus;
    }

    public void setPromptStatus(String promptStatus) {
        this.promptStatus = promptStatus;
    }

    public boolean isUnitSubmitForForce() {
        return this.unitSubmitForForce;
    }

    public void setUnitSubmitForForce(boolean unitSubmitForForce) {
        this.unitSubmitForForce = unitSubmitForForce;
    }

    public String getSelectedRoleForceKey() {
        return this.selectedRoleForceKey;
    }

    public void setSelectedRoleForceKey(String selectedRoleForceKey) {
        this.selectedRoleForceKey = selectedRoleForceKey;
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

    public boolean isCheckBeforeReporting() {
        return this.checkBeforeReporting;
    }

    public void setCheckBeforeReporting(boolean checkBeforeReporting) {
        this.checkBeforeReporting = checkBeforeReporting;
    }

    public boolean isBackDescriptionNeedWrite() {
        return this.backDescriptionNeedWrite;
    }

    public void setBackDescriptionNeedWrite(boolean backDescriptionNeedWrite) {
        this.backDescriptionNeedWrite = backDescriptionNeedWrite;
    }

    public boolean isAllowFormBack() {
        return this.allowFormBack;
    }

    public void setAllowFormBack(boolean allowFormBack) {
        this.allowFormBack = allowFormBack;
    }

    public ReportAuditType getCheckBeforeReportingType() {
        return this.checkBeforeReportingType;
    }

    public void setCheckBeforeReportingType(ReportAuditType checkBeforeReportingType) {
        this.checkBeforeReportingType = checkBeforeReportingType;
    }

    public String getCheckBeforeReportingCustom() {
        return this.checkBeforeReportingCustom;
    }

    public void setCheckBeforeReportingCustom(String checkBeforeReportingCustom) {
        this.checkBeforeReportingCustom = checkBeforeReportingCustom;
    }

    public String getReportBeforeAuditCustom() {
        return this.reportBeforeAuditCustom;
    }

    public void setReportBeforeAuditCustom(String reportBeforeAuditCustom) {
        this.reportBeforeAuditCustom = reportBeforeAuditCustom;
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

    public boolean isSpecialAudit() {
        return this.specialAudit;
    }

    public void setSpecialAudit(boolean specialAudit) {
        this.specialAudit = specialAudit;
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

