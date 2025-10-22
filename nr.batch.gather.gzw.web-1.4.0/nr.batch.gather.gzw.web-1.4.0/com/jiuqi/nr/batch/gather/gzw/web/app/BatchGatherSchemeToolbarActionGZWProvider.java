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
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.action.AddGroupToolBarGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.AddSchemeToolBarGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.MoverGroupToolBarGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction;
import java.util.ArrayList;
import java.util.List;

public class BatchGatherSchemeToolbarActionGZWProvider
implements IToolbarActionProvider {
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;
    private BeforeViewPageDataGZWHandler pageHandler;
    private BSShareService bsShareService;

    public BatchGatherSchemeToolbarActionGZWProvider(BatchGatherGZWContextData contextData, BSGroupService groupService, BSSchemeService schemeService, BeforeViewPageDataGZWHandler pageHandler, BSShareService bsShareService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
        this.pageHandler = pageHandler;
        this.bsShareService = bsShareService;
    }

    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> actions = new ArrayList<AbstractToolbarAction>();
        actions.add(new AddGroupToolBarGZWAction(this.contextData, this.groupService));
        actions.add(new AddSchemeToolBarGZWAction(this.contextData, this.schemeService));
        actions.add((AbstractToolbarAction)new RefreshToolbarAction());
        actions.add((AbstractToolbarAction)new MoverGroupToolBarGZWAction(this.contextData, this.groupService, this.schemeService));
        return actions;
    }
}

