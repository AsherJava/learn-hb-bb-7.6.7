/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadCheckFliterUtil {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;

    public void setCheckConditions(String taskKey, Map<String, DimensionValue> dimensionSet, int currencyType, String currencyVlaue) {
        if (ReportAuditType.CUSTOM.getValue() == currencyType) {
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            dimensionValue.setValue(currencyVlaue);
        } else {
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            if (dimensionValue != null) {
                if (ReportAuditType.ESCALATION.getValue() == currencyType) {
                    dimensionValue.setValue("PROVIDER_BASECURRENCY");
                } else if (ReportAuditType.CONVERSION.getValue() == currencyType) {
                    dimensionValue.setValue("PROVIDER_PBASECURRENCY");
                } else {
                    dimensionValue.setValue("");
                }
            }
        }
    }

    public void setCheckConditions(String taskKey, Map<String, DimensionValue> dimensionSet) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        ReportAuditType reportBeforeAuditType = flowsSetting.getReportBeforeAuditType();
        if (ReportAuditType.CUSTOM.equals((Object)reportBeforeAuditType)) {
            String reportBeforeAuditCustom = flowsSetting.getReportBeforeAuditCustom();
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            dimensionValue.setValue(reportBeforeAuditCustom);
        } else {
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            if (dimensionValue != null) {
                if (ReportAuditType.ESCALATION.equals((Object)reportBeforeAuditType)) {
                    dimensionValue.setValue("PROVIDER_BASECURRENCY");
                } else if (ReportAuditType.CONVERSION.equals((Object)reportBeforeAuditType)) {
                    dimensionValue.setValue("PROVIDER_PBASECURRENCY");
                } else {
                    dimensionValue.setValue("");
                }
            }
        }
    }

    public void setNodecheckConditions(String taskKey, Map<String, DimensionValue> dimensionSet, int currencyType, String currencyVlaue) {
        if (ReportAuditType.CUSTOM.getValue() == currencyType) {
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            dimensionValue.setValue(currencyVlaue);
        } else {
            DimensionValue dimensionValue = dimensionSet.get("MD_CURRENCY");
            if (dimensionValue != null) {
                if (ReportAuditType.ESCALATION.getValue() == currencyType) {
                    dimensionValue.setValue("PROVIDER_BASECURRENCY");
                } else if (ReportAuditType.CONVERSION.getValue() == currencyType) {
                    dimensionValue.setValue("PROVIDER_PBASECURRENCY");
                } else {
                    dimensionValue.setValue("");
                }
            }
        }
    }

    public boolean judgeCurrentcyType(String formSchemeKey, Map<String, DimensionValue> dimensionSet) {
        boolean isReportDim = true;
        String currency = "MD_CURRENCY";
        for (Map.Entry<String, DimensionValue> dimension : dimensionSet.entrySet()) {
            String key = dimension.getKey();
            if (!currency.equals(key) || this.isCurrency(formSchemeKey, key)) continue;
            isReportDim = false;
            return isReportDim;
        }
        return isReportDim;
    }

    private boolean isCurrency(String formSchemeKey, String currency) {
        boolean curr = false;
        List reportEntityKeys = this.formSchemeService.getReportEntityKeys(formSchemeKey);
        if (reportEntityKeys.contains(currency)) {
            curr = true;
        }
        return curr;
    }
}

