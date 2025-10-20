/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.datav.chart.context.ChartContextFactory
 *  com.jiuqi.nvwa.datav.chart.context.IChartContextProvider
 *  com.jiuqi.nvwa.datav.chart.context.impl.DashboardChartContextProvider
 *  com.jiuqi.nvwa.datav.chart.engine.dispatch.ChartBuilderCache
 *  com.jiuqi.nvwa.datav.chart.engine.param.DimParameterDataSourceProviderTaskFactory
 *  com.jiuqi.nvwa.datav.chart.manager.MapManager
 *  com.jiuqi.nvwa.datav.dashboard.textblock.engine.impl.TextBlockEngine
 *  com.jiuqi.nvwa.datav.dashboard.theme.model.ThemeFactory
 *  com.jiuqi.nvwa.datav.dashboard.util.ExtSVGLoader
 */
package com.jiuqi.nvwa.datav.dashboard.launch;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.datav.chart.context.ChartContextFactory;
import com.jiuqi.nvwa.datav.chart.context.IChartContextProvider;
import com.jiuqi.nvwa.datav.chart.context.impl.DashboardChartContextProvider;
import com.jiuqi.nvwa.datav.chart.engine.dispatch.ChartBuilderCache;
import com.jiuqi.nvwa.datav.chart.engine.param.DimParameterDataSourceProviderTaskFactory;
import com.jiuqi.nvwa.datav.chart.manager.MapManager;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.CacheDashboardRenderProvider;
import com.jiuqi.nvwa.datav.dashboard.launch.BucketDescriptorInfoUpdator;
import com.jiuqi.nvwa.datav.dashboard.textblock.engine.impl.TextBlockEngine;
import com.jiuqi.nvwa.datav.dashboard.theme.model.ThemeFactory;
import com.jiuqi.nvwa.datav.dashboard.util.ExtSVGLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Startup {
    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    public void init() {
        ExtSVGLoader.load();
        try {
            ThemeFactory.getInstance().init();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("\u4eea\u8868\u76d8\u4e3b\u9898\u8d44\u6e90\u52a0\u8f7d\u5931\u8d25", e);
        }
        ChartContextFactory.getInstance().registerChartContextProvider((IChartContextProvider)new DashboardChartContextProvider());
        try {
            MapManager mapManager = (MapManager)SpringBeanUtils.getBean(MapManager.class);
            mapManager.loadMapsByZip();
            mapManager.loadRegionMapsByZip();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("\u6ce8\u518c\u5730\u56fe\u6570\u636e\u5931\u8d25", e);
        }
        DimParameterDataSourceProviderTaskFactory.getInstance().register(ChartBuilderCache.class);
        DimParameterDataSourceProviderTaskFactory.getInstance().register(TextBlockEngine.class);
        DimParameterDataSourceProviderTaskFactory.getInstance().register(CacheDashboardRenderProvider.class);
    }

    public void initWhenStarted() {
        try {
            this.initBucket();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("\u5973\u5a32\u4eea\u8868\u76d8\u521b\u5efaOSS-bucket\u5931\u8d25", e);
        }
    }

    private void initBucket() throws Exception {
        ObjectStorageEngine engine = ObjectStorageEngine.newInstance();
        Bucket bucket = engine.getBucket("DASHBOARD");
        if (bucket == null) {
            bucket = new Bucket("DASHBOARD");
            bucket.setDesc("\u5b58\u653e\u4eea\u8868\u76d8\u76f8\u5173\u7684\u56fe\u7247\u3001\u9644\u4ef6\u7b49\u8d44\u6e90");
            engine.createBucket(bucket);
        } else if (StringUtils.isEmpty((String)bucket.getDesc())) {
            new BucketDescriptorInfoUpdator().updateBucketDescriptor(bucket.getName());
        }
    }
}

