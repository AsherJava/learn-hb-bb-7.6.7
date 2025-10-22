/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import java.util.List;

public interface DataVersionService {
    public void createDataVersion(List<String> var1, DimensionValueSet var2, String var3, String var4, IMonitor var5, boolean var6) throws Exception;
}

