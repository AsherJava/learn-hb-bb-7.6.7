/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.ITableActionProvider
 */
package com.jiuqi.nr.multcheck2.view.table;

import com.jiuqi.nr.multcheck2.view.table.action.MCCopyAction;
import com.jiuqi.nr.multcheck2.view.table.action.MCDelAction;
import com.jiuqi.nr.multcheck2.view.table.action.MCDesignAction;
import com.jiuqi.nr.multcheck2.view.table.action.MCEditAction;
import com.jiuqi.nr.multcheck2.view.table.action.MCMoveDownAction;
import com.jiuqi.nr.multcheck2.view.table.action.MCMoveUpAction;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.ITableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class MCTableActionProvider
implements ITableActionProvider {
    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actions = new ArrayList<AbstractTableAction>();
        actions.add(new MCDesignAction());
        actions.add((AbstractTableAction)new MCEditAction());
        actions.add((AbstractTableAction)new MCCopyAction());
        actions.add((AbstractTableAction)new MCDelAction());
        actions.add((AbstractTableAction)new MCMoveUpAction());
        actions.add((AbstractTableAction)new MCMoveDownAction());
        return actions;
    }
}

