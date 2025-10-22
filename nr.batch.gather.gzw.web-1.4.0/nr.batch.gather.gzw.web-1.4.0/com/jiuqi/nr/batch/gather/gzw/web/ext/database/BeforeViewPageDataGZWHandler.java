/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 */
package com.jiuqi.nr.batch.gather.gzw.web.ext.database;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;

public interface BeforeViewPageDataGZWHandler {
    public ActionInteractSetting getOpenAppSetting(BatchGatherGZWContextData var1);

    public IReturnObject<String> beforeViewData(BatchGatherGZWContextData var1);
}

