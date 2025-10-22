/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.web.app.action.CopySchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.action.DelSchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.action.EditSchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.action.ShareSchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.action.ShowSchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeTableActionProvider
extends DefaultTableActionProvider {
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;
    private BSShareService bsShareService;

    public SummarySchemeTableActionProvider(BatchSummaryContextData contextData, BSSchemeService schemeService, BeforeViewPageDataHandler pageHandler, BSShareService bsShareService) {
        this.schemeService = schemeService;
        this.contextData = contextData;
        this.pageHandler = pageHandler;
        this.bsShareService = bsShareService;
    }

    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actionBeans = new ArrayList<AbstractTableAction>();
        actionBeans.add(new ShowSchemeTableAction(this.contextData, this.pageHandler));
        actionBeans.add(new EditSchemeTableAction(this.schemeService));
        actionBeans.add((AbstractTableAction)new CopySchemeTableAction(this.schemeService));
        if (!this.contextData.getProductName().equals("dt")) {
            actionBeans.add((AbstractTableAction)new ShareSchemeTableAction(this.bsShareService));
        }
        actionBeans.add((AbstractTableAction)new DelSchemeTableAction(this.schemeService));
        return actionBeans;
    }
}

