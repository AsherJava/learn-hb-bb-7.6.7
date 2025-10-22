/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.grid2.MemStream2
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.np.grid2.StreamException2
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.config.MulCheckConfiguration;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignTaskFlowsDefine
implements Serializable,
TaskFlowsDefine {
    private static final Logger logger = LoggerFactory.getLogger(DesignTaskFlowsDefine.class);
    private static final long serialVersionUID = 3861634015617010416L;
    private FlowsType flowsType;
    private String designTableDefines;
    private String filterCondition;
    private DesignFlowSettingDefine designFlowSettingDefine = new DesignFlowSettingDefine();

    @Override
    public boolean getSubmitAfterFormula() {
        return this.designFlowSettingDefine.getSubmitAfterFormula();
    }

    public void setSubmitAfterFormula(boolean submitAfterFormula) {
        this.designFlowSettingDefine.setSubmitAfterFormula(submitAfterFormula);
    }

    @Override
    public String getSubmitAfterFormulaValue() {
        return this.designFlowSettingDefine.getSubmitAfterFormulaValue();
    }

    public void setSubmitAfterFormulaValue(String submitAfterFormulaValue) {
        this.designFlowSettingDefine.setSubmitAfterFormulaValue(submitAfterFormulaValue);
    }

    @Override
    public FlowsType getFlowsType() {
        return this.flowsType;
    }

    public void setFlowsType(FlowsType flowsType) {
        this.flowsType = flowsType;
    }

    @Override
    public String getDesignTableDefines() {
        return this.designTableDefines;
    }

    public void setDesignTableDefines(String designTableDefines) {
        this.designTableDefines = designTableDefines;
    }

    @Override
    public boolean isSubTable() {
        return this.designFlowSettingDefine.isSubTable();
    }

    public void setSubTable(boolean subTable) {
        this.designFlowSettingDefine.setSubTable(subTable);
    }

    @Override
    public boolean isDataConfirm() {
        return this.designFlowSettingDefine.isDataConfirm();
    }

    public void setDataConfirm(boolean dataConfirm) {
        this.designFlowSettingDefine.setDataConfirm(dataConfirm);
    }

    @Override
    public boolean isSubmitExplain() {
        return this.designFlowSettingDefine.isSubmitExplain();
    }

    @Override
    public boolean isForceSubmitExplain() {
        return this.designFlowSettingDefine.isForceSubmitExplain();
    }

    public void setSubmitExplain(boolean submitExplain) {
        this.designFlowSettingDefine.setSubmitExplain(submitExplain);
    }

    public void setForceSubmitExplain(boolean forceSubmitExplain) {
        this.designFlowSettingDefine.setForceSubmitExplain(forceSubmitExplain);
    }

    @Override
    public boolean isReturnVersion() {
        return this.designFlowSettingDefine.isReturnVersion();
    }

    public void setReturnVersion(boolean returnVersion) {
        this.designFlowSettingDefine.setReturnVersion(returnVersion);
    }

    @Override
    public boolean isAllowTakeBack() {
        return this.designFlowSettingDefine.isAllowTakeBack();
    }

    public void setAllowTakeBack(boolean allowTakeBack) {
        this.designFlowSettingDefine.setAllowTakeBack(allowTakeBack);
    }

    @Override
    public boolean isApplyReturn() {
        return this.designFlowSettingDefine.isApplyReturn();
    }

    public void setApplyReturn(boolean applyReturn) {
        this.designFlowSettingDefine.setApplyReturn(applyReturn);
    }

    @Override
    public boolean isAllowModifyData() {
        return this.designFlowSettingDefine.isAllowModifyData();
    }

    public void setAllowModifyData(boolean allowModifyData) {
        this.designFlowSettingDefine.setAllowModifyData(allowModifyData);
    }

    @Override
    public boolean isUnitSubmitForCensorship() {
        return this.designFlowSettingDefine.isUnitSubmitForCensorship();
    }

    public void setUnitSubmitForCensorship(boolean unitSubmitForCensorship) {
        this.designFlowSettingDefine.setUnitSubmitForCensorship(unitSubmitForCensorship);
    }

    @Override
    public String getSelectedRoleKey() {
        return this.designFlowSettingDefine.getSelectedRoleKey();
    }

    public void setSelectedRoleKey(String selectedRoleKey) {
        this.designFlowSettingDefine.setSelectedRoleKey(selectedRoleKey);
    }

    @Override
    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public String getErroStatus() {
        return this.designFlowSettingDefine.getErroStatus();
    }

    public void setErroStatus(String erroStatus) {
        this.designFlowSettingDefine.setErroStatus(erroStatus);
    }

    @Override
    public String getPromptStatus() {
        return this.designFlowSettingDefine.getPromptStatus();
    }

    public void setPromptStatus(String promptStatus) {
        this.designFlowSettingDefine.setPromptStatus(promptStatus);
    }

    @Override
    public boolean isUnitSubmitForForce() {
        return this.designFlowSettingDefine.isUnitSubmitForForce();
    }

    public void setUnitSubmitForForce(boolean unitSubmitForForce) {
        this.designFlowSettingDefine.setUnitSubmitForForce(unitSubmitForForce);
    }

    @Override
    public boolean getStepByStepReport() {
        return this.designFlowSettingDefine.getStepByStepReport();
    }

    public void setStepByStepReport(boolean stepByStepReport) {
        this.designFlowSettingDefine.setStepByStepReport(stepByStepReport);
    }

    @Override
    public boolean getStepByStepBack() {
        return this.designFlowSettingDefine.getStepByStepBack();
    }

    public void setStepByStepBack(boolean stepByStepBack) {
        this.designFlowSettingDefine.setStepByStepBack(stepByStepBack);
    }

    @Override
    public boolean isCheckBeforeReporting() {
        return this.designFlowSettingDefine.isCheckBeforeReporting();
    }

    public void setCheckBeforeReporting(boolean checkBeforeReporting) {
        this.designFlowSettingDefine.setCheckBeforeReporting(checkBeforeReporting);
    }

    @Override
    public boolean isBackDescriptionNeedWrite() {
        return this.designFlowSettingDefine.isBackDescriptionNeedWrite();
    }

    public void setBackDescriptionNeedWrite(boolean backDescriptionNeedWrite) {
        this.designFlowSettingDefine.setBackDescriptionNeedWrite(backDescriptionNeedWrite);
    }

    @Override
    public boolean isAllowFormBack() {
        return this.designFlowSettingDefine.isAllowFormBack();
    }

    public void setAllowFormBack(boolean allowFormBack) {
        this.designFlowSettingDefine.setAllowFormBack(allowFormBack);
    }

    @Override
    public String getSelectedRoleForceKey() {
        return this.designFlowSettingDefine.getSelectedRoleForceKey();
    }

    public void setSelectedRoleForceKey(String selectedRoleForceKey) {
        this.designFlowSettingDefine.setSelectedRoleForceKey(selectedRoleForceKey);
    }

    @Override
    public DesignFlowSettingDefine getDesignFlowSettingDefine() {
        return this.designFlowSettingDefine;
    }

    public void setDesignFlowSettingDefine(DesignFlowSettingDefine designFlowSettingDefine) {
        this.designFlowSettingDefine = designFlowSettingDefine;
    }

    public static byte[] designTaskFlowsDefineToBytes(TaskFlowsDefine data) {
        if (data == null) {
            return null;
        }
        MemStream2 store = new MemStream2();
        try {
            DesignTaskFlowsDefine.saveToStream((Stream2)store, data);
            return store.getBytes();
        }
        catch (StreamException2 ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static DesignTaskFlowsDefine bytesToTaskFlowsData(byte[] data) {
        if (data == null) {
            return null;
        }
        DesignTaskFlowsDefine designTaskFlowsDefine = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            designTaskFlowsDefine = DesignTaskFlowsDefine.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            designTaskFlowsDefine = null;
        }
        return designTaskFlowsDefine;
    }

    public static void saveToStream(Stream2 stream, TaskFlowsDefine designTaskFlowsDefine) throws StreamException2 {
        stream.writeInt(designTaskFlowsDefine.getFlowsType() == null ? 3 : designTaskFlowsDefine.getFlowsType().getValue());
        byte[] data = stream.encodeString(designTaskFlowsDefine.getDesignTableDefines() == null ? "" : designTaskFlowsDefine.getDesignTableDefines());
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
        data = stream.encodeString(designTaskFlowsDefine.getDesignFlowSettingDefine() == null ? "" : "1");
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
        int flowSettingLength = data.length;
        if (flowSettingLength == 1) {
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isSubTable()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isDataConfirm()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isSubmitExplain()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isReturnVersion()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isAllowTakeBack()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isAllowModifyData()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isUnitSubmitForCensorship()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getSelectedRoleKey() == null ? "" : designTaskFlowsDefine.getSelectedRoleKey());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getErroStatus() == null ? "" : designTaskFlowsDefine.getErroStatus());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getPromptStatus() == null ? "" : designTaskFlowsDefine.getPromptStatus());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isUnitSubmitForForce()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getSelectedRoleForceKey() == null ? "" : designTaskFlowsDefine.getSelectedRoleForceKey());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
        }
        data = stream.encodeString(designTaskFlowsDefine.getFilterCondition() == null ? "" : designTaskFlowsDefine.getFilterCondition());
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
        if (flowSettingLength == 1) {
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isCheckBeforeReporting()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isBackDescriptionNeedWrite()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getStepByStepReport()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getStepByStepBack()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isAllowFormBack()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getDefaultButtonName()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getDefaultButtonNameConfig() == null ? "" : designTaskFlowsDefine.getDefaultButtonNameConfig());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getSendMessageMail() == null ? "" : designTaskFlowsDefine.getSendMessageMail());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getWordFlowType() == null ? "" : designTaskFlowsDefine.getWordFlowType().getValue() + "");
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getStepReportType() == null ? "" : designTaskFlowsDefine.getStepReportType() + "");
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isApplyReturn()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getReportBeforeOperation()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getReportBeforeOperationValue() == null ? "" : designTaskFlowsDefine.getReportBeforeOperationValue());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getReportBeforeAudit()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getReportBeforeAuditValue() == null ? "" : designTaskFlowsDefine.getReportBeforeAuditValue());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getSubmitAfterFormula()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getSubmitAfterFormulaValue() == null ? "" : designTaskFlowsDefine.getSubmitAfterFormulaValue());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getReportBeforeAuditType() == null ? "" : designTaskFlowsDefine.getReportBeforeAuditType().getValue() + "");
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getReportBeforeAuditCustom() == null ? "" : designTaskFlowsDefine.getReportBeforeAuditCustom());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getCheckBeforeReportingType() == null ? "" : designTaskFlowsDefine.getCheckBeforeReportingType().getValue() + "");
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getCheckBeforeReportingCustom() == null ? "" : designTaskFlowsDefine.getCheckBeforeReportingCustom());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(designTaskFlowsDefine.getMessageTemplate() == null ? "" : designTaskFlowsDefine.getMessageTemplate());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isForceSubmitExplain()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getRealMulCheckBeforeCheck()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getGoBackAllSup()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getDefaultNodeName()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(StringUtils.isEmpty((String)designTaskFlowsDefine.getDefaultNodeNameConfig()) ? "" : designTaskFlowsDefine.getDefaultNodeNameConfig());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getSpecialAudit()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isOpenBackType()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.getBackTypeEntity()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isReturnExplain()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isAllowTakeBackForSubmit()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(String.valueOf(designTaskFlowsDefine.isOpenForceControl()));
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
        }
    }

    public static DesignTaskFlowsDefine loadFromStream(Stream2 stream) throws StreamException2 {
        DesignTaskFlowsDefine designTaskFlowsDefine = new DesignTaskFlowsDefine();
        int flowsType = stream.readInt();
        designTaskFlowsDefine.setFlowsType(FlowsType.fromType(flowsType));
        int length = stream.readInt();
        if (length >= 0) {
            designTaskFlowsDefine.setDesignTableDefines(stream.readString(length));
        }
        if ((length = stream.readInt()) == 1) {
            int reportBeforeAuditTypeValue;
            stream.readString(length);
            length = stream.readInt();
            if (length >= 0) {
                designTaskFlowsDefine.setSubTable(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDataConfirm(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSubmitExplain(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReturnVersion(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowTakeBack(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowModifyData(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setUnitSubmitForCensorship(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSelectedRoleKey(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setErroStatus(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setPromptStatus(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setUnitSubmitForForce(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSelectedRoleForceKey(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setFilterCondition(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setCheckBeforeReporting(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setBackDescriptionNeedWrite(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setStepByStepReport(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setStepByStepBack(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowFormBack(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDefaultButtonName(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDefaultButtonNameConfig(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSendMessageMail(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setWordFlowType(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setStepReportType(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setApplyReturn(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReportBeforeOperation(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReportBeforeOperationValue(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReportBeforeAudit(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReportBeforeAuditValue(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSubmitAfterFormula(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSubmitAfterFormulaValue(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                reportBeforeAuditTypeValue = ReportAuditType.NONE.getValue();
                try {
                    reportBeforeAuditTypeValue = Integer.parseInt(stream.readString(length));
                }
                catch (Exception exception) {
                    // empty catch block
                }
                designTaskFlowsDefine.setReportBeforeAuditType(ReportAuditType.forValue(reportBeforeAuditTypeValue));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReportBeforeAuditCustom(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                reportBeforeAuditTypeValue = ReportAuditType.NONE.getValue();
                try {
                    reportBeforeAuditTypeValue = Integer.parseInt(stream.readString(length));
                }
                catch (Exception exception) {
                    // empty catch block
                }
                designTaskFlowsDefine.setCheckBeforeReportingType(ReportAuditType.forValue(reportBeforeAuditTypeValue));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setCheckBeforeReportingCustom(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setMessageTemplate(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setForceSubmitExplain(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setRealMulCheckBeforeCheck(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setGoBackAllSup(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDefaultNodeName(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDefaultNodeNameConfig(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSpecialAudit(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setOpenBackType(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setBackTypeEntity(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReturnExplain(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowTakeBackForSubmit(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setOpenForceControl(Boolean.parseBoolean(stream.readString(length)));
            }
        } else if (length == 0) {
            stream.readString(length);
            length = stream.readInt();
            if (length >= 0) {
                designTaskFlowsDefine.setFilterCondition(stream.readString(length));
            }
        } else {
            if (length >= 0) {
                designTaskFlowsDefine.setSubTable(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setDataConfirm(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSubmitExplain(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setReturnVersion(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowTakeBack(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setAllowModifyData(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setUnitSubmitForCensorship(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSelectedRoleKey(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setFilterCondition(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setErroStatus(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setPromptStatus(stream.readString(length));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setUnitSubmitForForce(Boolean.parseBoolean(stream.readString(length)));
            }
            if ((length = stream.readInt()) >= 0) {
                designTaskFlowsDefine.setSelectedRoleForceKey(stream.readString(length));
            }
        }
        return designTaskFlowsDefine;
    }

    @Override
    @JsonIgnore
    public boolean getDefaultButtonName() {
        return this.designFlowSettingDefine.isDefaultButtonName();
    }

    @JsonIgnore
    public void setDefaultButtonName(boolean defaultButtonName) {
        this.designFlowSettingDefine.setDefaultButtonName(defaultButtonName);
    }

    @Override
    @JsonIgnore
    public String getDefaultButtonNameConfig() {
        return this.designFlowSettingDefine.getDefaultButtonNameConfig();
    }

    @JsonIgnore
    public void setDefaultNodeName(boolean defaultNodeName) {
        this.designFlowSettingDefine.setDefaultNodeName(defaultNodeName);
    }

    @Override
    @JsonIgnore
    public boolean getDefaultNodeName() {
        return this.designFlowSettingDefine.isDefaultNodeName();
    }

    @JsonIgnore
    public void setDefaultNodeNameConfig(String defaultNodeNameConfig) {
        this.designFlowSettingDefine.setDefaultNodeNameConfig(defaultNodeNameConfig);
    }

    @Override
    @JsonIgnore
    public String getDefaultNodeNameConfig() {
        return this.designFlowSettingDefine.getDefaultNodeNameConfig();
    }

    @JsonIgnore
    public void setDefaultButtonNameConfig(String defaultButtonNameConfig) {
        this.designFlowSettingDefine.setDefaultButtonNameConfig(defaultButtonNameConfig);
    }

    @Override
    @JsonIgnore
    public String getSendMessageMail() {
        return this.designFlowSettingDefine.getSendMessageMail();
    }

    @JsonIgnore
    public void setSendMessageMail(String sendMessageMail) {
        this.designFlowSettingDefine.setSendMessageMail(sendMessageMail);
    }

    @Override
    @JsonIgnore
    public WorkFlowType getWordFlowType() {
        return this.designFlowSettingDefine.getWordFlowType();
    }

    @JsonIgnore
    public void setWordFlowType(String workFlowType) {
        if (StringUtils.isNotEmpty((String)workFlowType)) {
            this.designFlowSettingDefine.setWordFlowType(WorkFlowType.fromType(Integer.parseInt(workFlowType)));
        } else {
            this.designFlowSettingDefine.setWordFlowType(WorkFlowType.ENTITY);
        }
    }

    @Override
    @JsonIgnore
    public String getStepReportType() {
        return this.designFlowSettingDefine.getStepReportType();
    }

    @JsonIgnore
    public void setStepReportType(String stepReportType) {
        this.designFlowSettingDefine.setStepReportType("".equals(stepReportType) ? "1" : stepReportType);
    }

    @Override
    @JsonIgnore
    public boolean getReportBeforeOperation() {
        return this.designFlowSettingDefine.getReportBeforeOperation();
    }

    @JsonIgnore
    public void setReportBeforeOperation(boolean checkBeforeFormula) {
        this.designFlowSettingDefine.setReportBeforeOperation(checkBeforeFormula);
    }

    @Override
    @JsonIgnore
    public String getReportBeforeOperationValue() {
        return this.designFlowSettingDefine.getReportBeforeOperationValue();
    }

    @JsonIgnore
    public void setReportBeforeOperationValue(String reportBeforeOperationValue) {
        this.designFlowSettingDefine.setReportBeforeOperationValue(reportBeforeOperationValue);
    }

    @Override
    @JsonIgnore
    public boolean getReportBeforeAudit() {
        return this.designFlowSettingDefine.getReportBeforeAudit();
    }

    @Override
    @JsonIgnore
    public boolean getMulCheckBeforeCheck() {
        return BeanUtil.getBean(MulCheckConfiguration.class).isOpenMulCheckBeforeCheck() && this.designFlowSettingDefine.getMulCheckBeforeCheck() && this.designFlowSettingDefine.getWordFlowType() == WorkFlowType.ENTITY;
    }

    @Override
    public boolean getRealMulCheckBeforeCheck() {
        return this.designFlowSettingDefine.getMulCheckBeforeCheck();
    }

    @JsonIgnore
    public void setRealMulCheckBeforeCheck(boolean mulCheckBeforeCheck) {
        this.designFlowSettingDefine.setMulCheckBeforeCheck(mulCheckBeforeCheck);
    }

    @JsonIgnore
    public void setReportBeforeAudit(boolean reportBeforeAudit) {
        this.designFlowSettingDefine.setReportBeforeAudit(reportBeforeAudit);
    }

    @Override
    @JsonIgnore
    public String getReportBeforeAuditValue() {
        return this.designFlowSettingDefine.getReportBeforeAuditValue();
    }

    @Override
    public boolean getSpecialAudit() {
        return this.designFlowSettingDefine.isSpecialAudit();
    }

    public void setSpecialAudit(boolean specialAudit) {
        this.designFlowSettingDefine.setSpecialAudit(specialAudit);
    }

    @JsonIgnore
    public void setReportBeforeAuditValue(String reportBeforeAuditValue) {
        this.designFlowSettingDefine.setReportBeforeAuditValue(reportBeforeAuditValue);
    }

    @Override
    public ReportAuditType getReportBeforeAuditType() {
        return this.designFlowSettingDefine.getReportBeforeAuditType();
    }

    public void setReportBeforeAuditType(ReportAuditType reportBeforeAuditValue) {
        this.designFlowSettingDefine.setReportBeforeAuditType(reportBeforeAuditValue);
    }

    @Override
    public ReportAuditType getCheckBeforeReportingType() {
        return this.designFlowSettingDefine.getCheckBeforeReportingType();
    }

    public void setCheckBeforeReportingType(ReportAuditType checkBeforeReportingType) {
        this.designFlowSettingDefine.setCheckBeforeReportingType(checkBeforeReportingType);
    }

    @Override
    public String getCheckBeforeReportingCustom() {
        return this.designFlowSettingDefine.getCheckBeforeReportingCustom();
    }

    public void setCheckBeforeReportingCustom(String value) {
        this.designFlowSettingDefine.setCheckBeforeReportingCustom(value);
    }

    @Override
    public String getReportBeforeAuditCustom() {
        return this.designFlowSettingDefine.getReportBeforeAuditCustom();
    }

    public void setReportBeforeAuditCustom(String value) {
        this.designFlowSettingDefine.setReportBeforeAuditCustom(value);
    }

    @Override
    @JsonIgnore
    public String getMessageTemplate() {
        return this.designFlowSettingDefine.getMessageTemplate();
    }

    @JsonIgnore
    public void setMessageTemplate(String messageTemplate) {
        this.designFlowSettingDefine.setMessageTemplate(messageTemplate);
    }

    @Override
    @JsonIgnore
    public boolean getGoBackAllSup() {
        return this.designFlowSettingDefine.getGoBackAllSup();
    }

    @Override
    public boolean isOpenBackType() {
        return this.designFlowSettingDefine.isOpenBackType();
    }

    public void setOpenBackType(boolean openBackType) {
        this.designFlowSettingDefine.setOpenBackType(openBackType);
    }

    @Override
    public String getBackTypeEntity() {
        return this.designFlowSettingDefine.getBackTypeEntity();
    }

    @Override
    public boolean isReturnExplain() {
        return this.designFlowSettingDefine.isReturnExplain();
    }

    public void setReturnExplain(boolean returnExplain) {
        this.designFlowSettingDefine.setReturnExplain(returnExplain);
    }

    @Override
    public boolean isAllowTakeBackForSubmit() {
        return this.designFlowSettingDefine.isAllowTakeBackForSubmit();
    }

    @Override
    public boolean isOpenForceControl() {
        return this.designFlowSettingDefine.isOpenForceControl();
    }

    public void setOpenForceControl(boolean openForceControl) {
        this.designFlowSettingDefine.setOpenForceControl(openForceControl);
    }

    public void setAllowTakeBackForSubmit(boolean allowTakeBackForSubmit) {
        this.designFlowSettingDefine.setAllowTakeBackForSubmit(allowTakeBackForSubmit);
    }

    public void setBackTypeEntity(String backTypeEntity) {
        this.designFlowSettingDefine.setBackTypeEntity(backTypeEntity);
    }

    @JsonIgnore
    public void setGoBackAllSup(boolean goBackAllSup) {
        this.designFlowSettingDefine.setGoBackAllSup(goBackAllSup);
    }
}

