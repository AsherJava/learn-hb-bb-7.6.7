/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisRenameTableAction
 */
package com.jiuqi.bi.dataset.report.workbench.myanalysis.actions;

import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisRenameTableAction;
import org.springframework.util.StringUtils;

public class ReportDSRenameTableAction
extends MyAnalysisRenameTableAction {
    private static final String ID = "reportds_rename_tableaction";

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting actionInteractSetting = new ModalActionInteractSetting();
        actionInteractSetting.setPluginName("nr-report-dataset");
        actionInteractSetting.setPluginType("data-analysis-plugin");
        actionInteractSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return actionInteractSetting;
    }
}

