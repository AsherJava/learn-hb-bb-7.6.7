/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;

public class ShowSchemeTableAction
extends AbstractTableAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.show.scheme.data";
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;

    public ShowSchemeTableAction(BatchSummaryContextData contextData, BeforeViewPageDataHandler pageHandler) {
        this.contextData = contextData;
        this.pageHandler = pageHandler;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u67e5\u770b";
    }

    public ActionInteractSetting getInteractSetting() {
        return this.pageHandler.getOpenAppSetting(this.contextData);
    }
}

