/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.action.DelGroupTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.EditGroupTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class BatchGatherGroupTableActionGZWProvider
extends DefaultTableActionProvider {
    private final BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;

    public BatchGatherGroupTableActionGZWProvider(BatchGatherGZWContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
    }

    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actionBeans = new ArrayList<AbstractTableAction>();
        actionBeans.add((AbstractTableAction)new EditGroupTableGZWAction(this.groupService));
        actionBeans.add((AbstractTableAction)new DelGroupTableGZWAction(this.contextData, this.groupService, this.schemeService));
        return actionBeans;
    }
}

