/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.utils.AppConditionUtil
 */
package com.jiuqi.nr.multcheck2.view.table.action;

import com.jiuqi.nr.multcheck2.common.GlobalType;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import com.jiuqi.nvwa.resourceview.utils.AppConditionUtil;

public class MCDesignAction
extends AbstractTableAction {
    public static final String ID = "multcheck2_table_design";
    public static final String TITLE = "\u8bbe\u8ba1";
    private String type = null;

    public MCDesignAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        return super.run(actionContext, param);
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        if (AppConditionUtil.getConitionValue().equals(GlobalType.EXIST.value())) {
            CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
            interactSetting.setProdLine("@nr");
            interactSetting.setPluginName("nr-multcheck2-plugin");
            interactSetting.setPluginType("nr-multcheck2-plugin");
            interactSetting.setExpose("Design");
            return interactSetting;
        }
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setProdLine("@nr");
        interactSetting.setPluginName("nr-multcheck2-plugin");
        interactSetting.setPluginType("nr-multcheck2-plugin");
        interactSetting.setExpose("Design");
        return interactSetting;
    }
}

