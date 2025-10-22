/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.table.inner.RenameTableAction
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nr.batch.summary.web.app.action.AddGroupToolBarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.table.inner.RenameTableAction;

public class EditGroupTableAction
extends RenameTableAction {
    private BSGroupService groupService;
    private static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.edit.group";

    public EditGroupTableAction(BSGroupService groupService) {
        this.groupService = groupService;
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getInteractSetting() {
        return BatchSummaryPluginConst.createGroupModal("\u7f16\u8f91\u5206\u7ec4");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummaryGroupDefine groupDefine = (SummaryGroupDefine)BatchSummaryUtils.toJavaBean((String)param, SummaryGroupDefine.class);
        if (groupDefine != null) {
            return AddGroupToolBarAction.returnActionResult(this.groupService.renameSchemeGroup(groupDefine.getKey(), groupDefine.getTitle()));
        }
        return BatchSummaryPluginConst.getFailActionResult(GroupServiceState.FAIL.code);
    }
}

