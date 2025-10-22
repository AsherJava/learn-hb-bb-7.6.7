/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;

public interface ParamRelation {
    public String getDefaultGroupName();

    public String getFormSchemeKey();

    public String getTaskKey();

    public String getDwDimName();

    public IFmlExecEnvironment getReportFmlExecEnvironment();
}

