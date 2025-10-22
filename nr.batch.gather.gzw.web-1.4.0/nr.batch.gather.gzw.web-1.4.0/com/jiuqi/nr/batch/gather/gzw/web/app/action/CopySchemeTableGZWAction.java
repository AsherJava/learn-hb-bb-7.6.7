/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherPluginGZWConst;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.AddSchemeToolBarGZWAction;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction;

public class CopySchemeTableGZWAction
extends CopyTableAction {
    public static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.copy.scheme.data";
    private BSSchemeService schemeService;

    public CopySchemeTableGZWAction(BSSchemeService schemeService) {
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getInteractSetting() {
        ModalActionInteractSetting modal = new ModalActionInteractSetting();
        modal.setWidth(960);
        modal.setModalTitle("\u590d\u5236\u6c47\u603b\u65b9\u6848");
        modal.setProdLine("@nr");
        modal.setPluginName("batch-gather-plugin-gzw");
        modal.setPluginType("batch-gather-plugin-gzw");
        modal.setExpose("schemeView");
        return modal;
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummarySchemeDefine schemeDefine = (SummarySchemeDefine)BatchSummaryUtils.toJavaBean((String)param, SummarySchemeDefine.class);
        if (schemeDefine != null) {
            return AddSchemeToolBarGZWAction.returnActionResult(this.schemeService.saveSummaryScheme(schemeDefine));
        }
        return BatchGatherPluginGZWConst.getFailActionResult(SchemeServiceState.FAIL.title);
    }
}

