/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction
 */
package com.jiuqi.nr.mapping.view.toolbar;

import com.jiuqi.nr.mapping.view.toolbar.action.MSDelToolbarAction;
import com.jiuqi.nr.mapping.view.toolbar.action.MSNewSchemeToolbarAction;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.toolbar.inner.RefreshToolbarAction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MSToolbarActionProvider
implements IToolbarActionProvider {
    @Autowired
    private MSDelToolbarAction delete;
    @Autowired
    private MSNewSchemeToolbarAction newScheme;

    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> toolbarActions = new ArrayList<AbstractToolbarAction>();
        toolbarActions.add(this.newScheme);
        toolbarActions.add((AbstractToolbarAction)this.delete);
        toolbarActions.add((AbstractToolbarAction)new RefreshToolbarAction());
        return toolbarActions;
    }
}

