/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.INormalAction
 */
package com.jiuqi.nr.zbquery.workbench.share.action;

import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.INormalAction;
import org.springframework.stereotype.Component;

@Component
public class ZBQueryShowTableAction
implements INormalAction {
    public static final String ID = "myshare_zbquery_table_show";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return null;
    }

    public ActionInteractSetting getInteractSetting() {
        return this.getEditInteractSetting();
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return null;
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        return null;
    }

    public ActionInteractSetting getEditInteractSetting() {
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setPluginName("nr-zbquery-myanalysis");
        interactSetting.setPluginType("nr-zbquery-myanalysis-plugin");
        interactSetting.setExpose("ZBQueryShareShowData");
        return interactSetting;
    }
}

