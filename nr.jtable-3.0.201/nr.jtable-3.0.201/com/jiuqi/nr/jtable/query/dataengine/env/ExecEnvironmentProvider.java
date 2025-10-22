/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 */
package com.jiuqi.nr.jtable.query.dataengine.env;

import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface ExecEnvironmentProvider {
    public static final String execEvnVariableMapKey = "execEnvironmentProvider";

    public String getInstanceId();

    public IFmlExecEnvironment getExecEnvironment(JtableContext var1, IFmlExecEnvironment var2);
}

