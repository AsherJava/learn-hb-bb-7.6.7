/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.context.cxt;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.cxt.Extension;
import java.io.Serializable;
import java.util.List;

public interface DsContext
extends Serializable {
    public String getContextEntityId();

    public String getContextFilterExpression();

    public String getTaskKey();

    public List<Variable> getVariables();

    public Extension getExtension();
}

