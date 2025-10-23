/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.action;

import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction;

public class ZBQueryNewDataToolbarAction
extends MyAnalysisNewDataToolbarAction {
    public static final String TITLE = "\u67e5\u8be2";

    public ZBQueryNewDataToolbarAction() {
        super(TITLE);
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        return super.run(actionContext, param);
    }

    public String postAction() {
        return "myanalysis_zbquery_table_edit";
    }
}

