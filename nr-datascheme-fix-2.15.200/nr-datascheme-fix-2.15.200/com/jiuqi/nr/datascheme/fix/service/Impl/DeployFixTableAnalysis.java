/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.service.Impl;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFailAnalysis;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=1)
public class DeployFixTableAnalysis
implements IDataSchemeDeployFailAnalysis {
    @Override
    public DeployExType doAnalysis(DeployFixDataTable dataTableCheckResult, List<DeployFixTableModel> tableModelCheckResult, boolean checkData) {
        DeployExType exType = null;
        for (DeployFixTableModel fixTableModel : tableModelCheckResult) {
            if (!dataTableCheckResult.getCheckResult().isDsTable() && !fixTableModel.getCheckResult().isNvwaTable() && fixTableModel.getCheckResult().isNvwaTableAndPt()) {
                if (checkData) {
                    exType = DeployExType.HAS_DATA_MISS_DRT_NDT;
                    continue;
                }
                exType = DeployExType.MISS_DRT_NDT;
                continue;
            }
            if (dataTableCheckResult.getCheckResult().isDsTable() && fixTableModel.getCheckResult().isNvwaTable() && !fixTableModel.getCheckResult().isNvwaTableAndPt()) {
                if (fixTableModel.getLogicTable() != null) {
                    if (checkData) {
                        exType = DeployExType.HAS_DATA_MISS_NDT_NRT;
                        continue;
                    }
                    exType = DeployExType.MISS_NDT_NRT;
                    continue;
                }
                exType = DeployExType.MISS_PT;
                continue;
            }
            if (dataTableCheckResult.getCheckResult().isDsTable() && !fixTableModel.getCheckResult().isNvwaTable() && fixTableModel.getCheckResult().isNvwaTableAndPt()) {
                if (checkData) {
                    exType = DeployExType.HAS_DATA_MISS_NDT;
                    continue;
                }
                exType = DeployExType.MISS_NDT;
                continue;
            }
            if (dataTableCheckResult.getCheckResult().isDsTable() && !fixTableModel.getCheckResult().isNvwaTable() && !fixTableModel.getCheckResult().isNvwaTableAndPt()) {
                if (fixTableModel.getLogicTable() != null) {
                    if (checkData) {
                        exType = DeployExType.HAS_DATA_MISS_NDT_NRT;
                        continue;
                    }
                    exType = DeployExType.MISS_NDT_NRT;
                    continue;
                }
                if (fixTableModel.getTableModelDefine() != null) {
                    exType = DeployExType.MISS_NDT_PT;
                    continue;
                }
                exType = DeployExType.MISS_NDT_NRT_PT;
                continue;
            }
            if (!dataTableCheckResult.getCheckResult().isDsTable() && !fixTableModel.getCheckResult().isNvwaTableAndPt()) {
                if (dataTableCheckResult.getDesDataTable() != null) continue;
                exType = DeployExType.GARBAGE_DATA;
                continue;
            }
            return exType;
        }
        return exType;
    }
}

