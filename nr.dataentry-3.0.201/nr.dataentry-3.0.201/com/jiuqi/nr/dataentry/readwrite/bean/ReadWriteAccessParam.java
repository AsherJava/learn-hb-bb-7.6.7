/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class ReadWriteAccessParam
extends NRContext {
    private List<ReadWriteAccessItem> items;
    private JtableContext context;

    public List<ReadWriteAccessItem> getItems() {
        return this.items;
    }

    public void setItems(List<ReadWriteAccessItem> items) {
        this.items = items;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }
}

