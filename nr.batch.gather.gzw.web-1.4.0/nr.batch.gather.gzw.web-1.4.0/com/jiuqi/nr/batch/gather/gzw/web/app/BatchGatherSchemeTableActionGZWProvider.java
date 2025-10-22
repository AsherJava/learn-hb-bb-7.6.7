/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.action.CopySchemeTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.DelSchemeTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.EditSchemeTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.ExecuteGatherTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.action.ShowSchemeTableGZWAction;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class BatchGatherSchemeTableActionGZWProvider
extends DefaultTableActionProvider {
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;
    private BeforeViewPageDataGZWHandler pageHandler;
    private BSShareService bsShareService;

    public BatchGatherSchemeTableActionGZWProvider(BatchGatherGZWContextData contextData, BSSchemeService schemeService, BeforeViewPageDataGZWHandler pageHandler, BSShareService bsShareService) {
        this.schemeService = schemeService;
        this.contextData = contextData;
        this.pageHandler = pageHandler;
        this.bsShareService = bsShareService;
    }

    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actionBeans = new ArrayList<AbstractTableAction>();
        actionBeans.add(new ShowSchemeTableGZWAction(this.contextData, this.pageHandler));
        actionBeans.add(new EditSchemeTableGZWAction(this.schemeService));
        actionBeans.add((AbstractTableAction)new CopySchemeTableGZWAction(this.schemeService));
        actionBeans.add(new ExecuteGatherTableGZWAction());
        actionBeans.add((AbstractTableAction)new DelSchemeTableGZWAction(this.schemeService));
        return actionBeans;
    }
}

