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
 *  com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nr.batch.summary.web.app.action.AddSchemeToolBarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction;

public class CopySchemeTableAction
extends CopyTableAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.copy.scheme.data";
    private BSSchemeService schemeService;

    public CopySchemeTableAction(BSSchemeService schemeService) {
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getInteractSetting() {
        return BatchSummaryPluginConst.createSchemeModal("\u590d\u5236\u6c47\u603b\u65b9\u6848");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummarySchemeDefine schemeDefine = (SummarySchemeDefine)BatchSummaryUtils.toJavaBean((String)param, SummarySchemeDefine.class);
        if (schemeDefine != null) {
            return AddSchemeToolBarAction.returnActionResult(this.schemeService.saveSummaryScheme(schemeDefine));
        }
        return BatchSummaryPluginConst.getFailActionResult(SchemeServiceState.FAIL.title);
    }
}

