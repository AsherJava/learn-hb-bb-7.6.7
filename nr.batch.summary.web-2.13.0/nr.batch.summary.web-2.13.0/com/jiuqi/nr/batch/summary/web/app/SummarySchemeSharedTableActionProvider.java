/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.web.app.action.ShowSchemeTableAction;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.DefaultTableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeSharedTableActionProvider
extends DefaultTableActionProvider {
    private BatchSummaryContextData contextData;
    private BeforeViewPageDataHandler pageHandler;

    public SummarySchemeSharedTableActionProvider(BatchSummaryContextData contextData, BeforeViewPageDataHandler pageHandler) {
        this.contextData = contextData;
        this.pageHandler = pageHandler;
    }

    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actionBeans = new ArrayList<AbstractTableAction>();
        actionBeans.add(new ShowSchemeTableAction(this.contextData, this.pageHandler));
        return actionBeans;
    }
}

