/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.datav.chart.controller.R
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardDataCacheManager
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.datav.dashboard.monitor;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.datav.chart.controller.R;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardDataCacheManager;
import com.jiuqi.nvwa.datav.dashboard.monitor.DashboardCacheCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/nvwa/datav/dashboard/montior"})
public class DashboardDataMonitorController {
    @Autowired
    private DashboardCacheCollector collector;

    @GetMapping(value={"/cache"})
    public String collectDashboardCacheInfo() throws DashboardCacheException {
        String user = NpContextHolder.getContext().getUserName();
        if (!user.equals("admin")) {
            throw new DashboardCacheException("\u7f13\u5b58\u8be6\u60c5\u4ec5\u5141\u8bb8\u8d85\u7ea7\u7ba1\u7406\u5458\u8bbf\u95ee");
        }
        try {
            return this.collector.collect();
        }
        catch (Exception e) {
            throw new DashboardCacheException("\u6536\u96c6\u7f13\u5b58\u6570\u636e\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/destroy"})
    public String destroyTaskDispatcherService(@RequestParam(required=true) String sessionId) throws DashboardCacheException {
        DashboardDataCacheManager.getInstance().removeDatasetCache(sessionId);
        return R.ok().jsonString();
    }
}

