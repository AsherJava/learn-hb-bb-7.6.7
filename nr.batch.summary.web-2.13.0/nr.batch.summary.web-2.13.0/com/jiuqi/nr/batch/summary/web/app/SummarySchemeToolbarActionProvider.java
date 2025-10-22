/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.web.app.action.AddGroupToolBarAction;
import com.jiuqi.nr.batch.summary.web.app.action.AddSchemeToolBarAction;
import com.jiuqi.nr.batch.summary.web.app.action.BatchShowSchemesAction;
import com.jiuqi.nr.batch.summary.web.app.action.MoverGroupToolBarAction;
import com.jiuqi.nr.batch.summary.web.app.action.ShareSchemeToolBarAction;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeToolbarActionProvider
implements IToolbarActionProvider {
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;
    private BSShareService bsShareService;

    public SummarySchemeToolbarActionProvider(BatchSummaryContextData contextData, BSGroupService groupService, BSSchemeService schemeService, BeforeViewPageDataHandler pageHandler, BSShareService bsShareService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
        this.pageHandler = pageHandler;
        this.bsShareService = bsShareService;
    }

    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> actions = new ArrayList<AbstractToolbarAction>();
        actions.add(new AddGroupToolBarAction(this.contextData, this.groupService));
        actions.add(new AddSchemeToolBarAction(this.contextData, this.schemeService));
        actions.add((AbstractToolbarAction)new RefreshToolbarAction());
        actions.add((AbstractToolbarAction)new MoverGroupToolBarAction(this.contextData, this.groupService, this.schemeService));
        actions.add(new BatchShowSchemesAction(this.contextData, this.pageHandler));
        if (!this.contextData.getProductName().equals("dt")) {
            actions.add(new ShareSchemeToolBarAction(this.bsShareService));
        }
        return actions;
    }
}

