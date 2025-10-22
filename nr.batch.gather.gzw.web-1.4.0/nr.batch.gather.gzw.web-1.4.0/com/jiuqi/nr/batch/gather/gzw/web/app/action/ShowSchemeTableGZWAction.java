/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;

public class ShowSchemeTableGZWAction
extends AbstractTableAction {
    public static final String ID = "com.jiuqi.nr.batch.gather.gzw.web.app.action.show.scheme.data";
    private BatchGatherGZWContextData contextData;
    private BeforeViewPageDataGZWHandler pageHandler;

    public ShowSchemeTableGZWAction(BatchGatherGZWContextData contextData, BeforeViewPageDataGZWHandler pageHandler) {
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

