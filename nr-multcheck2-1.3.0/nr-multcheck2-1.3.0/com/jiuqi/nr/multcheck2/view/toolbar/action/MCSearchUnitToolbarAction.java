/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.NewResourceToolbarAction
 */
package com.jiuqi.nr.multcheck2.view.toolbar.action;

import com.jiuqi.nr.multcheck2.common.MultcheckUtil;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import com.jiuqi.nvwa.resourceview.toolbar.inner.NewResourceToolbarAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MCSearchUnitToolbarAction
extends NewResourceToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(MCSearchUnitToolbarAction.class);
    private static final String ID = "multcheck2_toolbar_search-unit";
    private static final String TITLE = "\u5355\u4f4d\u641c\u7d22";
    private static final String ICON = "#icon16_GJ_A_NW_chakanhuizongxinxi";

    public MCSearchUnitToolbarAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        ResourceNode treeNode = actionContext.getSelectTreeNode();
        if (treeNode == null) {
            return false;
        }
        return treeNode.getId().indexOf("F@") > -1;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        ActionResult actionResult = ActionResult.success(null, (String)(this.getTitle() + "\u6210\u529f\uff01"));
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting interactSetting = MultcheckUtil.getModalActionInteractSetting();
        interactSetting.setExpose("NewScheme");
        interactSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return interactSetting;
    }
}

