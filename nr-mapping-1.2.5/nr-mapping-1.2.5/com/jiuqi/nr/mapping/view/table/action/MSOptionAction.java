/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting
 */
package com.jiuqi.nr.mapping.view.table.action;

import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSOptionAction
extends AbstractTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MSOptionAction.class);
    public static final String ID = "mapping_table_config";
    public static final String TITLE = "\u914d\u7f6e";

    public MSOptionAction() {
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
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setPluginName("nr-mapping-scheme-plugin");
        interactSetting.setPluginType("nr-mapping-scheme-plugin");
        interactSetting.setExpose("ConfigScheme");
        return interactSetting;
    }
}

