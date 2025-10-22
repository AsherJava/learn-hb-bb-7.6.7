/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction
 */
package com.jiuqi.bi.dataset.report.workbench.myanalysis.actions;

import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction;

public class ReportDSPreviewTableAction
extends MyAnalysisPreviewTableAction {
    private static final String ID = "reportds_preview_tableaction";

    public ReportDSPreviewTableAction() {
        this.interactSetting = this.getPreviewInteractSetting();
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getPreviewInteractSetting() {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-report-dataset");
        interactSetting.setPluginType("data-analysis-plugin");
        interactSetting.setModalTitle("\u62a5\u8868\u6570\u636e\u96c6" + this.getTitle());
        interactSetting.setFullScreen(true);
        interactSetting.setHiddenFooter(true);
        return interactSetting;
    }
}

