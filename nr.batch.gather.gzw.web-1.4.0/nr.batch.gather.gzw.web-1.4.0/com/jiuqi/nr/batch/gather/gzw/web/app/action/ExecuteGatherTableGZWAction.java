/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherPluginGZWConst;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;

public class ExecuteGatherTableGZWAction
extends AbstractTableAction {
    public static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.ExecuteGather";
    public static final String TITLE = "\u6267\u884c\u6c47\u603b";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public ActionInteractSetting getInteractSetting() {
        ModalActionInteractSetting modal = new ModalActionInteractSetting();
        modal.setWidth(520);
        modal.setModalTitle(TITLE);
        modal.setProdLine("@nr");
        modal.setPluginName("batch-gather-plugin-gzw");
        modal.setPluginType("batch-gather-plugin-gzw");
        modal.setExpose("executeGather");
        return modal;
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        return BatchGatherPluginGZWConst.getSuccessActionResult("\u6267\u884c\u5b8c\u6bd5", (ResourceNode)new ResourceData());
    }
}

