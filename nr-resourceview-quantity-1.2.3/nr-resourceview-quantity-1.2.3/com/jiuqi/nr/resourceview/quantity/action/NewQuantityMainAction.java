/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 */
package com.jiuqi.nr.resourceview.quantity.action;

import com.jiuqi.nr.resourceview.quantity.action.NewQuantityAction;
import com.jiuqi.nr.resourceview.quantity.action.NewQuantityCategoryAction;
import com.jiuqi.nr.resourceview.quantity.action.NewQuantityUnitAction;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import java.util.ArrayList;
import java.util.List;

public class NewQuantityMainAction
extends AbstractToolbarAction {
    public String getId() {
        return "com.jiuqi.nr.resourceview.quantity.action.NewQuantityMainAction";
    }

    public String getTitle() {
        return "\u65b0\u5efa";
    }

    public String getIcon() {
        return "#icon16_GJ_A_NW_xinzeng";
    }

    public boolean enabled() {
        return true;
    }

    public Boolean actionState(ActionContext actionContext) {
        return true;
    }

    public List<AbstractToolbarAction> getChildActions() {
        ArrayList<AbstractToolbarAction> childActions = new ArrayList<AbstractToolbarAction>();
        childActions.add(new NewQuantityAction());
        childActions.add(new NewQuantityCategoryAction());
        childActions.add(new NewQuantityUnitAction());
        return childActions;
    }
}

