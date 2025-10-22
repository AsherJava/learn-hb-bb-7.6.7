/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.TableInnerActionType
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction
 */
package com.jiuqi.bi.dataset.report.workbench.myanalysis.actions;

import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.TableInnerActionType;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction;

public class ReportDSEditorTableAction
extends MyAnalysisEditTableAction {
    public static final String ID = "reportds_editor_tableaction";

    public String getId() {
        return ID;
    }

    public ReportDSEditorTableAction() {
        this.interactSetting = this.getEditInteractSetting();
        this.setInnerType(TableInnerActionType.TABLE_EDIT);
    }

    public ActionInteractSetting getEditInteractSetting() {
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setPluginName("nr-report-dataset");
        interactSetting.setPluginType("data-analysis-plugin");
        return interactSetting;
    }
}

