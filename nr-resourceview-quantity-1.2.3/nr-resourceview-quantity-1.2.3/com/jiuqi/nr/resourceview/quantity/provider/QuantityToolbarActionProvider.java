/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 */
package com.jiuqi.nr.resourceview.quantity.provider;

import com.jiuqi.nr.resourceview.quantity.action.DeleteQuantityAction;
import com.jiuqi.nr.resourceview.quantity.action.ModifyQuantityAction;
import com.jiuqi.nr.resourceview.quantity.action.NewQuantityMainAction;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityToolbarActionProvider
implements IToolbarActionProvider {
    @Autowired
    private DeleteQuantityAction deleteQuantityAction;

    public List<AbstractToolbarAction> getToolbarActions() {
        ArrayList<AbstractToolbarAction> toolbarActions = new ArrayList<AbstractToolbarAction>();
        toolbarActions.add(new NewQuantityMainAction());
        toolbarActions.add(new ModifyQuantityAction());
        toolbarActions.add(this.deleteQuantityAction);
        return toolbarActions;
    }
}

