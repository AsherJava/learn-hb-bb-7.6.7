/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting
 */
package com.jiuqi.nr.batch.summary.web.ext.database;

import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting;

public class BeforeViewPageDataHandlerImpl
implements BeforeViewPageDataHandler {
    @Override
    public ActionInteractSetting getOpenAppSetting(BatchSummaryContextData contextData) {
        UITabActionInteractSetting uiAction = new UITabActionInteractSetting();
        uiAction.setAppName("batch-summary-view");
        uiAction.setProdLine("@nr");
        return uiAction;
    }

    @Override
    public IReturnObject<String> beforeViewData(BatchSummaryContextData contextData) {
        return null;
    }
}

