/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.action;

import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction;

public class ZBQueryPreviewTableAction
extends MyAnalysisPreviewTableAction {
    public static final String ID = "myanalysis_zbquery_table_preview";

    public ZBQueryPreviewTableAction() {
        this.interactSetting = this.getPreviewInteractSetting();
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getPreviewInteractSetting() {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-zbquery-myanalysis");
        interactSetting.setPluginType("nr-zbquery-myanalysis-plugin");
        interactSetting.setExpose("ZBQueryMyAnalysisEditor");
        interactSetting.setModalTitle("\u67e5\u8be2" + this.getTitle());
        interactSetting.setFullScreen(true);
        interactSetting.setHiddenFooter(true);
        return interactSetting;
    }
}

