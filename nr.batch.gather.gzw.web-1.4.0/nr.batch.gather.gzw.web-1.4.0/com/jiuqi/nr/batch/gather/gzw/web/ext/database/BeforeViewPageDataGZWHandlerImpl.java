/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.web.ext.database;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting;
import javax.annotation.Resource;

public class BeforeViewPageDataGZWHandlerImpl
implements BeforeViewPageDataGZWHandler {
    @Resource
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public ActionInteractSetting getOpenAppSetting(BatchGatherGZWContextData contextData) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(contextData.getTaskId());
        UITabActionInteractSetting uiAction = new UITabActionInteractSetting();
        uiAction.setAppName("batch-gather-loading-dataentry-plugin-gzw");
        uiAction.setProdLine("@nr");
        uiAction.setTabTitle("\u6570\u636e\u5f55\u5165-" + taskDefine.getTitle());
        return uiAction;
    }

    @Override
    public IReturnObject<String> beforeViewData(BatchGatherGZWContextData contextData) {
        return null;
    }
}

