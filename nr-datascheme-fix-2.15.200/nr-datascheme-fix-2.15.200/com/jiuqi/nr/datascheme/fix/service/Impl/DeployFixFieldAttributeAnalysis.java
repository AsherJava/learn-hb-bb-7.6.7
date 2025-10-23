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
@Order(value=3)
public class DeployFixFieldAttributeAnalysis
implements IDataSchemeDeployFailAnalysis {
    @Autowired
    private DeployFailFixHelper fixHelper;

    @Override
    public DeployExType doAnalysis(DeployFixDataTable dataTableCheckResult, List<DeployFixTableModel> tableModelCheckResult, boolean checkData) {
        DeployExType exType = null;
        DeployFixTmCheckSummary checkSummary = this.fixHelper.getCheckSummary(tableModelCheckResult);
        DeployFixDsAndNvwaCheckResult checkResult = this.fixHelper.getCheckResult(dataTableCheckResult, tableModelCheckResult);
        if (checkData) {
            if (dataTableCheckResult.getCheckResult().isDsFieldAttribute() && checkResult.isDsAndNvwaFieldAttribute() && checkSummary.isAllNvwaColumnsAttribute() && checkSummary.isAllNvwaColumnsAndLogicFieldsAttribute()) {
                return exType;
            }
        } else {
            return exType;
        }
        exType = DeployExType.FIELD_ATTRIBUTE_SITUATION;
        return exType;
    }
}

