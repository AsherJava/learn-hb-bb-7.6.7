/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;

public class AddGroupToolBarAction
extends AbstractToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.new.group";
    private static final String ICON = "#icon16_GJ_A_NW_xinzeng";
    private BSGroupService groupService;
    private BatchSummaryContextData contextData;

    public AddGroupToolBarAction(BatchSummaryContextData contextData, BSGroupService groupService) {
        this.contextData = contextData;
        this.groupService = groupService;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u65b0\u589e\u5206\u7ec4";
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        return this.contextData.isValidContext();
    }

    public ActionInteractSetting getInteractSetting() {
        return BatchSummaryPluginConst.createGroupModal("\u65b0\u589e\u5206\u7ec4");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummaryGroupDefine groupDefine = (SummaryGroupDefine)BatchSummaryUtils.toJavaBean((String)param, SummaryGroupDefine.class);
        if (groupDefine != null) {
            return AddGroupToolBarAction.returnActionResult(this.groupService.saveSchemeGroup(groupDefine));
        }
        return BatchSummaryPluginConst.getFailActionResult(GroupServiceState.FAIL.title);
    }

    public static ActionResult returnActionResult(GroupServiceState groupServiceState) {
        switch (groupServiceState) {
            case HAS_CHILD_GROUP: 
            case HAS_CHILD_SCHEME: 
            case INVALID_TASK: 
            case INVALID_GROUP_NAME: {
                return BatchSummaryPluginConst.getFailActionResult(groupServiceState.title);
            }
            case SUCCESS: {
                return BatchSummaryPluginConst.getSuccessActionResult(groupServiceState.title, BatchSummaryPluginConst.getResourceNode());
            }
        }
        return BatchSummaryPluginConst.getFailActionResult(GroupServiceState.FAIL.title);
    }
}

