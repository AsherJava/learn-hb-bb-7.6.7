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
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherPluginGZWConst;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;

public class AddGroupToolBarGZWAction
extends AbstractToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.new.group";
    private static final String ICON = "#icon16_GJ_A_NW_xinzeng";
    private BSGroupService groupService;
    private BatchGatherGZWContextData contextData;

    public AddGroupToolBarGZWAction(BatchGatherGZWContextData contextData, BSGroupService groupService) {
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
        return BatchGatherPluginGZWConst.createGroupModal("\u65b0\u589e\u5206\u7ec4");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummaryGroupDefine groupDefine = (SummaryGroupDefine)BatchSummaryUtils.toJavaBean((String)param, SummaryGroupDefine.class);
        if (groupDefine != null) {
            return AddGroupToolBarGZWAction.returnActionResult(this.groupService.saveSchemeGroup(groupDefine));
        }
        return BatchGatherPluginGZWConst.getFailActionResult(GroupServiceState.FAIL.title);
    }

    public static ActionResult returnActionResult(GroupServiceState groupServiceState) {
        switch (groupServiceState) {
            case HAS_CHILD_GROUP: 
            case HAS_CHILD_SCHEME: 
            case INVALID_TASK: 
            case INVALID_GROUP_NAME: {
                return BatchGatherPluginGZWConst.getFailActionResult(groupServiceState.title);
            }
            case SUCCESS: {
                return BatchGatherPluginGZWConst.getSuccessActionResult(groupServiceState.title, BatchGatherPluginGZWConst.getResourceNode());
            }
        }
        return BatchGatherPluginGZWConst.getFailActionResult(GroupServiceState.FAIL.title);
    }
}

