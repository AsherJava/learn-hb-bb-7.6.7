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
package com.jiuqi.nr.dafafill.owner.provider;

import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.INormalAction;
import org.springframework.stereotype.Component;

@Component
public class DataFillPrivateShareAction
implements INormalAction {
    private static final String ID = "myshare_datafill_private_table_show";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "";
    }

    public ActionInteractSetting getInteractSetting() {
        return this.getEditInteractSetting();
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return null;
    }

    public ActionResult run(ActionContext actionContext, String s) throws Exception {
        return null;
    }

    public ActionInteractSetting getEditInteractSetting() {
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setProdLine("@nr");
        interactSetting.setAppName("datafill-functions");
        interactSetting.setExpose("SHARE");
        return interactSetting;
    }
}

