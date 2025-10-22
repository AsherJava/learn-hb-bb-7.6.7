/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.data.engine.version;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import java.util.List;

public interface DataVersionOpt {
    public String createDataVersion(List<String> var1, DimensionValueSet var2, String var3, String var4, IMonitor var5, boolean var6, String var7) throws Exception;

    public void overwriteDefaultVersion(List<String> var1, DimensionValueSet var2, String var3, String var4, IMonitor var5, boolean var6, String var7) throws Exception;

    public void overwriteDefaultVersionOfFormList(List<String> var1, DimensionValueSet var2, String var3, String var4, IMonitor var5, boolean var6, List<String> var7, String var8) throws Exception;
}

