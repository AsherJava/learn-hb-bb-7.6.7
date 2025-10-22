/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 */
package com.jiuqi.nr.batch.summary.web.ext.database;

import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;

public interface BeforeViewPageDataHandler {
    public ActionInteractSetting getOpenAppSetting(BatchSummaryContextData var1);

    public IReturnObject<String> beforeViewData(BatchSummaryContextData var1);
}

