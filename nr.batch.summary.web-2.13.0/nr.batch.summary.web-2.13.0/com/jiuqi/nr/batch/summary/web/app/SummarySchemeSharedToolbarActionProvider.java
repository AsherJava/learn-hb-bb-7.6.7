/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeSharedToolbarActionProvider
implements IToolbarActionProvider {
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;

    public SummarySchemeSharedToolbarActionProvider(BatchSummaryContextData contextData, BSGroupService groupService, BSSchemeService schemeService, BeforeViewPageDataHandler pageHandler) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
        this.pageHandler = pageHandler;
    }

    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> actions = new ArrayList<AbstractToolbarAction>();
        actions.add((AbstractToolbarAction)new RefreshToolbarAction());
        return actions;
    }
}

