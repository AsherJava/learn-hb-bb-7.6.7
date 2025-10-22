/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.dataservice.core.datastatus.monitor;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.DefaultMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;

public class DataStatusMonitorFactory {
    private DataStatusMonitorFactory() {
    }

    public static IDataStatusMonitor getMonitor(DimensionCombination dimensionCombination, String formSchemeKey, Collection<String> formKeys) {
        return new DefaultMonitor(dimensionCombination, formSchemeKey, formKeys);
    }

    public static IDataStatusMonitor getMonitor(DimensionCombination dimensionCombination, String formSchemeKey, Collection<String> formKeys, IMonitor monitor) {
        DefaultMonitor defaultMonitor = new DefaultMonitor(dimensionCombination, formSchemeKey, formKeys);
        defaultMonitor.setMonitor(monitor);
        return defaultMonitor;
    }
}

