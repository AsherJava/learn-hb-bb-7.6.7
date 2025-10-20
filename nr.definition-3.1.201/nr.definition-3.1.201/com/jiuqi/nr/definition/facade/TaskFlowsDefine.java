/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;

public interface TaskFlowsDefine {
    public FlowsType getFlowsType();

    public String getDesignTableDefines();

    public boolean isSubTable();

    public boolean isDataConfirm();

    public boolean isSubmitExplain();

    public boolean isForceSubmitExplain();

    public boolean isReturnVersion();

    public boolean isAllowTakeBack();

    public boolean isAllowModifyData();

    public boolean isUnitSubmitForCensorship();

    public String getSelectedRoleKey();

    public boolean isUnitSubmitForForce();

    public boolean getStepByStepReport();

    public boolean getStepByStepBack();

    public boolean isCheckBeforeReporting();

    public ReportAuditType getCheckBeforeReportingType();

    public String getCheckBeforeReportingCustom();

    public boolean isBackDescriptionNeedWrite();

    public boolean isAllowFormBack();

    public String getSelectedRoleForceKey();

    public String getFilterCondition();

    public String getErroStatus();

    public String getPromptStatus();

    public DesignFlowSettingDefine getDesignFlowSettingDefine();

    public boolean getDefaultButtonName();

    public String getDefaultButtonNameConfig();

    public boolean getDefaultNodeName();

    public String getDefaultNodeNameConfig();

    public String getSendMessageMail();

    public WorkFlowType getWordFlowType();

    public String getStepReportType();

    public boolean isApplyReturn();

    public boolean getReportBeforeOperation();

    public String getReportBeforeOperationValue();

    public boolean getReportBeforeAudit();

    public boolean getMulCheckBeforeCheck();

    public boolean getRealMulCheckBeforeCheck();

    public ReportAuditType getReportBeforeAuditType();

    public String getReportBeforeAuditCustom();

    public String getReportBeforeAuditValue();

    public boolean getSpecialAudit();

    public boolean getSubmitAfterFormula();

    public String getSubmitAfterFormulaValue();

    public String getMessageTemplate();

    public boolean getGoBackAllSup();

    public boolean isOpenBackType();

    public String getBackTypeEntity();

    public boolean isReturnExplain();

    public boolean isAllowTakeBackForSubmit();

    public boolean isOpenForceControl();
}

