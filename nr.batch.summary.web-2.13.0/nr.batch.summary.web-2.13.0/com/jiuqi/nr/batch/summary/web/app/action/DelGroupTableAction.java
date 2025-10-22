/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.DelTableAction
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.web.app.action.AddGroupToolBarAction;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;

public class DelGroupTableAction
extends DelTableAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.DelGroupTableAction";
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;

    public DelGroupTableAction(BatchSummaryContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getInteractSetting() {
        return new NormalActionInteractSetting();
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        String groupKey = actionContext.getCurrOperTableNode().getId();
        return AddGroupToolBarAction.returnActionResult(this.groupService.removeSchemeGroup(this.contextData.getTaskId(), groupKey));
    }
}

