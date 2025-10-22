/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class FormsParam
extends NRContext {
    private JtableContext context;
    private boolean fmdm;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public boolean isFmdm() {
        return this.fmdm;
    }

    public void setFmdm(boolean fmdm) {
        this.fmdm = fmdm;
    }

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }
}

