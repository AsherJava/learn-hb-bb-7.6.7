/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.workbench.myanalysis.dataset.action.DataSetNewDataToolbarAction
 */
package com.jiuqi.bi.dataset.report.workbench.myanalysis.actions;

import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.workbench.myanalysis.dataset.action.DataSetNewDataToolbarAction;
import org.springframework.util.StringUtils;

public class ReportDSNewToolbarAction
extends DataSetNewDataToolbarAction {
    private static final String ID = "reportds_new_toolbaraction";

    public ReportDSNewToolbarAction() {
        this("\u62a5\u8868\u6570\u636e\u96c6", false);
    }

    public ReportDSNewToolbarAction(String title, boolean isPrefix) {
        super(title, isPrefix);
    }

    public String getId() {
        return ID;
    }

    public String getIcon() {
        return "#icon-16_SHU_A_NR_baobiaoshujuji";
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        return super.run(actionContext, param);
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting actionInteractSetting = new ModalActionInteractSetting();
        actionInteractSetting.setPluginName("nr-report-dataset");
        actionInteractSetting.setPluginType("data-analysis-plugin");
        actionInteractSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return actionInteractSetting;
    }

    public String postAction() {
        return "reportds_editor_tableaction";
    }
}

