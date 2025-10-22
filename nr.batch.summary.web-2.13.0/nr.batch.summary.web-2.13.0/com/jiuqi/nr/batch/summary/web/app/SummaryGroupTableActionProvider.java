/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.web.app.action.DelGroupTableAction;
import com.jiuqi.nr.batch.summary.web.app.action.EditGroupTableAction;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class SummaryGroupTableActionProvider
extends DefaultTableActionProvider {
    private final BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;

    public SummaryGroupTableActionProvider(BatchSummaryContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
    }

    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actionBeans = new ArrayList<AbstractTableAction>();
        actionBeans.add((AbstractTableAction)new EditGroupTableAction(this.groupService));
        actionBeans.add((AbstractTableAction)new DelGroupTableAction(this.contextData, this.groupService, this.schemeService));
        return actionBeans;
    }
}

