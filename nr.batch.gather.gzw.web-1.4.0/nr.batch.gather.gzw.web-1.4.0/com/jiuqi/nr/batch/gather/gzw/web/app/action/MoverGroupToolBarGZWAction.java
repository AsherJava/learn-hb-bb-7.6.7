/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.MoveToolbarAction
 *  com.jiuqi.nvwa.workbench.common.IErrorMessageEnum
 *  com.jiuqi.nvwa.workbench.common.WorkbenchError
 *  com.jiuqi.nvwa.workbench.myanalysis.common.ErrorMessageEnum
 *  org.json.JSONObject
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherPluginGZWConst;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import com.jiuqi.nvwa.resourceview.toolbar.inner.MoveToolbarAction;
import com.jiuqi.nvwa.workbench.common.IErrorMessageEnum;
import com.jiuqi.nvwa.workbench.common.WorkbenchError;
import com.jiuqi.nvwa.workbench.myanalysis.common.ErrorMessageEnum;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class MoverGroupToolBarGZWAction
extends MoveToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.MoverGroupToolBarAction";
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;

    public MoverGroupToolBarGZWAction(BatchGatherGZWContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public Boolean actionState(ActionContext actionContext) {
        return this.contextData.isValidContext();
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return ActionResult.error(null, (String)WorkbenchError.getInstance((IErrorMessageEnum)ErrorMessageEnum.MA_MOVE_NODE_ERROR, (Object[])new Object[0]).getMessage());
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        String parentGroupKey = this.getParentGroupNodeKey(param);
        List checkTableNodes = actionContext.getCheckTableNodes();
        if (StringUtils.isNotEmpty((String)parentGroupKey) && checkTableNodes != null && !checkTableNodes.isEmpty()) {
            List groupList = checkTableNodes.stream().filter(this::isGroupNode).collect(Collectors.toList());
            List schemeList = checkTableNodes.stream().filter(this::isSchemeNode).collect(Collectors.toList());
            List groupIds = groupList.stream().map(ResourceNode::getId).collect(Collectors.toList());
            this.groupService.moveGroups2Group(parentGroupKey, groupIds);
            List schemeIds = schemeList.stream().map(ResourceNode::getId).collect(Collectors.toList());
            this.schemeService.moveScheme2Group(parentGroupKey, schemeIds);
            ActionResult actionResult = ActionResult.success(null, (String)"\u79fb\u52a8\u6210\u529f\uff01");
            actionResult.setRefresh(true);
            return actionResult;
        }
        return BatchGatherPluginGZWConst.getFailActionResult("\u6ca1\u6709\u53ef\u79fb\u52a8\u7684\u5206\u7ec4\u6216\u8005\u65b9\u6848\uff01");
    }

    private String getParentGroupNodeKey(String param) {
        JSONObject jsonObject = new JSONObject(param);
        String parentGroupKey = jsonObject.optString("id", null);
        if ("batch-gather-GZW-scheme-resource-view-id".equals(parentGroupKey)) {
            return "00000000-0000-0000-0000-000000000000";
        }
        return parentGroupKey;
    }

    private boolean isGroupNode(ResourceNode node) {
        return NodeType.NODE_GROUP == node.getType();
    }

    private boolean isSchemeNode(ResourceNode node) {
        return NodeType.NODE_DATA == node.getType();
    }
}

