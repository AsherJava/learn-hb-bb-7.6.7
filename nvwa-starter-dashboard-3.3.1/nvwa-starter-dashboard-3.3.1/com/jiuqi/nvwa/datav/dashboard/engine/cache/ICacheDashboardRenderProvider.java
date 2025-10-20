/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef
 */
package com.jiuqi.nvwa.datav.dashboard.engine.cache;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef;
import java.util.List;
import java.util.Map;

public interface ICacheDashboardRenderProvider {
    public void init(String var1, LinkMsg var2, DashboardRenderContext var3) throws DashboardCacheException;

    public void updateWidget(String var1) throws DashboardCacheException;

    public String processDrill(String var1, DrillInfoParam var2) throws DashboardCacheException;

    public void updateTheme(String var1) throws DashboardCacheException;

    public String getQueryResult(String var1, LinkMsg var2, WidgetRenderContext var3) throws Exception;

    public boolean isDatasetCached(DSRef var1) throws DashboardCacheException;

    public MemoryDataSet<BIDataSetFieldInfo> getDataSet(DSRef var1) throws DashboardCacheException;

    public Map<String, MemoryDataSet<BIDataSetFieldInfo>> getDataSets(String var1) throws DashboardCacheException;

    public void setParamValue(DSRef var1, Map<String, List<Object>> var2) throws DashboardCacheException;

    public void destory() throws DashboardCacheException;

    public void removeWidget(String var1) throws DashboardCacheException;

    public GridData getGridData(String var1, LinkMsg var2) throws Exception;
}

