/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.DelTableAction
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.web.app.action.AddSchemeToolBarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;

public class DelSchemeTableAction
extends DelTableAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.DelSchemeTableAction";
    private final BSSchemeService schemeService;

    public DelSchemeTableAction(BSSchemeService schemeService) {
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getInteractSetting() {
        return new NormalActionInteractSetting("\u662f\u5426\u786e\u5b9a\u8981\u5220\u9664\u65b9\u6848\uff1f\u5220\u9664\u65b9\u6848\u7684\u65f6\u5019\u4f1a\u540c\u65f6\u5220\u9664\u6c47\u603b\u6570\u636e\uff01");
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        String key = actionContext.getCurrOperTableNode().getId();
        return AddSchemeToolBarAction.returnActionResult(this.schemeService.removeSummaryScheme(key));
    }
}

