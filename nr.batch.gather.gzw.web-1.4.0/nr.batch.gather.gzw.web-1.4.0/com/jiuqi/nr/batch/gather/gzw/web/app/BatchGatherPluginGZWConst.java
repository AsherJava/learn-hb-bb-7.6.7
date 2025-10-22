/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;

public class BatchGatherPluginGZWConst {
    public static final String DATA_PAGE_APP_NAME = "batch-gather-loading-dataentry-plugin-gzw";
    public static final String PLUGIN_NAME = "batch-gather-plugin-gzw";
    public static final String PLUGIN_TYPE = "batch-gather-plugin-gzw";
    public static final String PROD_LINE = "@nr";
    public static final String COMPONENT_OF_TASK_NAME = "customCondition";
    public static final String COMPONENT_OF_GROUP_NAME = "groupView";
    public static final String COMPONENT_OF_SCHEME_NAME = "schemeView";
    public static final String COMPONENT_OF_EXECUTE_NAME = "executeGather";
    public static final String ROOT_GROUP_TITLE = "\u6211\u7684\u5206\u7c7b\u6c47\u603b\u65b9\u6848";
    public static final String ADD_GROUP_TITLE = "\u65b0\u589e\u5206\u7ec4";
    public static final String EDIT_GROUP_TITLE = "\u7f16\u8f91\u5206\u7ec4";
    public static final String ADD_SCHEME_TITLE = "\u65b0\u589e\u6c47\u603b\u65b9\u6848";
    public static final String EDIT_SCHEME_TITLE = "\u7f16\u8f91\u6c47\u603b\u65b9\u6848";
    public static final String COPY_SCHEMES_TITLE = "\u590d\u5236\u6c47\u603b\u65b9\u6848";

    private BatchGatherPluginGZWConst() {
    }

    public static ModalActionInteractSetting createSchemeModal(String title) {
        ModalActionInteractSetting modal = new ModalActionInteractSetting();
        modal.setModalTitle(title);
        modal.setProdLine(PROD_LINE);
        modal.setPluginName("batch-gather-plugin-gzw");
        modal.setPluginType("batch-gather-plugin-gzw");
        modal.setExpose(COMPONENT_OF_SCHEME_NAME);
        return modal;
    }

    public static ModalActionInteractSetting createGroupModal(String title) {
        ModalActionInteractSetting modal = new ModalActionInteractSetting();
        modal.setModalTitle(title);
        modal.setProdLine(PROD_LINE);
        modal.setPluginName("batch-gather-plugin-gzw");
        modal.setPluginType("batch-gather-plugin-gzw");
        modal.setExpose(COMPONENT_OF_GROUP_NAME);
        return modal;
    }

    public static ResourceNode getResourceNode() {
        ResourceNode resourceNode = new ResourceNode();
        return resourceNode;
    }

    public static ActionResult getFailActionResult(String message) {
        ActionResult actionResult = new ActionResult(false, null, message);
        actionResult.setRefresh(false);
        return actionResult;
    }

    public static ActionResult getSuccessActionResult(String message, ResourceNode resourceNode) {
        ActionResult actionResult = new ActionResult(true, resourceNode, message);
        actionResult.setRefresh(true);
        return actionResult;
    }
}

