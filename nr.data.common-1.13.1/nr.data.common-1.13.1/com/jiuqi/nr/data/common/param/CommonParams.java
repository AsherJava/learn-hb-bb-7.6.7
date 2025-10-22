/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.common.param;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.Map;

public class CommonParams {
    private AsyncTaskMonitor monitor;
    private ParamsMapping mapping;
    private Map<String, DimensionCollection> filter;

    public Map<String, DimensionCollection> getFilter() {
        return this.filter;
    }

    public void setFilter(Map<String, DimensionCollection> filter) {
        this.filter = filter;
    }

    public AsyncTaskMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public ParamsMapping getMapping() {
        return this.mapping;
    }

    public void setMapping(ParamsMapping mapping) {
        this.mapping = mapping;
    }
}

