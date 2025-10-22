/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.context.infc;

import com.jiuqi.np.dataengine.var.Variable;
import java.io.Serializable;
import java.util.List;

public interface INRContext
extends Serializable {
    public String getContextEntityId();

    public String getContextFilterExpression();

    default public List<Variable> getVariables() {
        return null;
    }
}

