/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractTableAction
 *  com.jiuqi.nvwa.resourceview.table.ITableActionProvider
 */
package com.jiuqi.nr.mapping.view.table;

import com.jiuqi.nr.mapping.view.table.action.MSCopyTableAction;
import com.jiuqi.nr.mapping.view.table.action.MSDelTableAction;
import com.jiuqi.nr.mapping.view.table.action.MSMappingAction;
import com.jiuqi.nr.mapping.view.table.action.MSOptionAction;
import com.jiuqi.nr.mapping.view.table.action.MSRenameAction;
import com.jiuqi.nvwa.resourceview.action.AbstractTableAction;
import com.jiuqi.nvwa.resourceview.table.ITableActionProvider;
import java.util.ArrayList;
import java.util.List;

public class MSTableActionProvider
implements ITableActionProvider {
    public List<AbstractTableAction> getTableActions() {
        ArrayList<AbstractTableAction> actions = new ArrayList<AbstractTableAction>();
        actions.add((AbstractTableAction)new MSRenameAction());
        actions.add((AbstractTableAction)new MSCopyTableAction());
        actions.add((AbstractTableAction)new MSDelTableAction());
        actions.add(new MSOptionAction());
        actions.add(new MSMappingAction());
        return actions;
    }
}

