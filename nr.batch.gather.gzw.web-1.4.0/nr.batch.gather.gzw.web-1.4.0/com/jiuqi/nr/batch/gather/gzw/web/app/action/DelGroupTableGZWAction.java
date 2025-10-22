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
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.nr.batch.gather.gzw.web.app.action.AddGroupToolBarGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;

public class DelGroupTableGZWAction
extends DelTableAction {
    public static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.DelGroupTableAction";
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;

    public DelGroupTableGZWAction(BatchGatherGZWContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
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
        return AddGroupToolBarGZWAction.returnActionResult(this.groupService.removeSchemeGroup(this.contextData.getTaskId(), groupKey));
    }
}

