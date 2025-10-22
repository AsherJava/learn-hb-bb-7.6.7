/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.UITabActionInteractSetting;

public class BatchShowSchemesAction
extends AbstractToolbarAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.show.schemes";
    private static final String ICON = "#icon-_GJZchakanshangbao";
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;

    public BatchShowSchemesAction(BatchSummaryContextData contextData, BeforeViewPageDataHandler pageHandler) {
        this.contextData = contextData;
        this.pageHandler = pageHandler;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u6279\u91cf\u67e5\u770b";
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        return this.contextData.isValidContext();
    }

    public ActionInteractSetting getInteractSetting() {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        ActionInteractSetting openAppSetting = this.pageHandler.getOpenAppSetting(this.contextData);
        if (openAppSetting instanceof UITabActionInteractSetting) {
            UITabActionInteractSetting uiAction = (UITabActionInteractSetting)openAppSetting;
            TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(this.contextData.getTaskId());
            if (taskDefine != null) {
                uiAction.setTabTitleProvider(actionContext -> taskDefine.getTitle());
            }
            return uiAction;
        }
        return openAppSetting;
    }
}

