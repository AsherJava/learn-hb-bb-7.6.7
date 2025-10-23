/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.service.Impl;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.core.DeployFailFixHelper;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDsAndNvwaCheckResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTmCheckSummary;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFailAnalysis;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=2)
public class DeployFixFieldsAnalysis
implements IDataSchemeDeployFailAnalysis {
    @Autowired
    private DeployFailFixHelper fixHelper;

    @Override
    public DeployExType doAnalysis(DeployFixDataTable dataTableCheckResult, List<DeployFixTableModel> tableModelCheckResult, boolean checkData) {
        DeployExType exType = null;
        DeployFixTmCheckSummary checkSummary = this.fixHelper.getCheckSummary(tableModelCheckResult);
        DeployFixDsAndNvwaCheckResult checkResult = this.fixHelper.getCheckResult(dataTableCheckResult, tableModelCheckResult);
        if (!dataTableCheckResult.getCheckResult().isDsField() && !checkResult.isDsAndNvwaField() && checkSummary.isAllNvwaColumns() && checkSummary.isAllNvwaColumnsAndLogicFields()) {
            exType = dataTableCheckResult.getCheckResult().isDsFieldAndDfdi() ? (checkData ? DeployExType.HAS_DATA_MISS_DRF_DFDI : DeployExType.MISS_DRF_DFDI) : DeployExType.MISS_DRF;
        } else if (!(dataTableCheckResult.getCheckResult().isDsField() || checkResult.isDsAndNvwaField() || checkSummary.isAllNvwaColumns() || checkSummary.isAllNvwaColumnsAndLogicFields())) {
            exType = dataTableCheckResult.getCheckResult().isDsFieldAndDfdi() ? (checkData ? DeployExType.HAS_DATA_MISS_DRF_NRC_DFDI : DeployExType.MISS_DRF_NRC_DFDI) : (checkData ? DeployExType.HAS_DATA_MISS_DRF_NRC : DeployExType.MISS_DRF_NRC);
        } else if (!dataTableCheckResult.getCheckResult().isDsField() && !checkResult.isDsAndNvwaField() && checkSummary.isAllNvwaColumns() && !checkSummary.isAllNvwaColumnsAndLogicFields()) {
            exType = dataTableCheckResult.getCheckResult().isDsFieldAndDfdi() ? (checkData ? DeployExType.HAS_DATA_MISS_DRF_PT_DFDI : DeployExType.MISS_DRF_PT_DFDI) : (checkData ? DeployExType.HAS_DATA_MISS_DRF_PT : DeployExType.MISS_DRF_PT);
        } else if (!dataTableCheckResult.getCheckResult().isDsField() && checkResult.isDsAndNvwaField() && checkSummary.isAllNvwaColumns() && !checkSummary.isAllNvwaColumnsAndLogicFields()) {
            exType = dataTableCheckResult.getCheckResult().isDsFieldAndDfdi() ? (checkData ? DeployExType.HAS_DATA_MISS_DRF_NDC_NRC_DFDI : DeployExType.MISS_DRF_NDC_NRC_DFDI) : (checkData ? DeployExType.HAS_DATA_MISS_DRF_NDC_NRC : DeployExType.MISS_DRF_NDC_NRC);
        } else if (!dataTableCheckResult.getCheckResult().isDsField() && !checkResult.isDsAndNvwaField() && !checkSummary.isAllNvwaColumns() && checkSummary.isAllNvwaColumnsAndLogicFields()) {
            exType = dataTableCheckResult.getCheckResult().isDsFieldAndDfdi() ? (checkData ? DeployExType.HAS_DATA_MISS_DRF_NRC_PT_DFDI : DeployExType.MISS_DRF_NRC_PT_DFDI) : (checkData ? DeployExType.HAS_DATA_MISS_DRF_NRC_PT : DeployExType.MISS_DRF_NRC_PT);
        } else {
            return exType;
        }
        return exType;
    }
}

