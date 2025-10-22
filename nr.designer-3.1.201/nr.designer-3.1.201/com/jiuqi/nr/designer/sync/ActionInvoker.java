/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.sync;

import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.List;

public class ActionInvoker {
    private boolean reverse;
    private List<IAction> actions;

    public ActionInvoker(List<IAction> actions) {
        this.actions = actions;
    }

    public ActionInvoker(boolean reverse, List<IAction> actions) {
        this.reverse = reverse;
        this.actions = actions;
    }

    public void addAction(IAction action) {
        if (null == this.actions) {
            this.actions = new ArrayList<IAction>();
        }
        this.actions.add(action);
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public Grid2Data run(Grid2Data data) {
        block4: {
            if (null == this.actions || this.actions.isEmpty()) break block4;
            if (this.reverse) {
                for (int i = this.actions.size() - 1; i >= 0; --i) {
                    this.actions.get(i).run(data);
                }
            } else {
                for (int i = 0; i < this.actions.size(); ++i) {
                    this.actions.get(i).run(data);
                }
            }
        }
        return data;
    }
}

