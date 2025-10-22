/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.dataservice.core.datastatus.monitor;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;

public interface IDataStatusMonitor
extends IMonitor {
    public DataStatusPresetInfo getPresetInfo();

    default public IMonitor getMonitor() {
        return null;
    }
}

