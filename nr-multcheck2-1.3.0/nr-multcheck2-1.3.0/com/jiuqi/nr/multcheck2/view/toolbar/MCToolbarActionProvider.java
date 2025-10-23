/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 */
package com.jiuqi.nr.multcheck2.view.toolbar;

import com.jiuqi.nr.multcheck2.view.toolbar.action.MCNewSchemeToolbarAction;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MCToolbarActionProvider
implements IToolbarActionProvider {
    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> toolbarActions = new ArrayList<AbstractToolbarAction>();
        toolbarActions.add((AbstractToolbarAction)new MCNewSchemeToolbarAction());
        return toolbarActions;
    }
}

