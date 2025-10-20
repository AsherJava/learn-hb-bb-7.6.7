/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain
 *  com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain;
import com.jiuqi.nvwa.springadapter.filter.NvwaGenericFilterBean;
import java.util.Optional;
import org.slf4j.MDC;

public class MDCFilter
extends NvwaGenericFilterBean {
    private static final String TRACEID_KEY = "X-NVWA-TRACE-ID";

    public void doFilter(NvwaFilterChain nvwaFilterChain) throws Exception {
        Optional<String> traceid = Optional.ofNullable(nvwaFilterChain.getHeader(TRACEID_KEY)).filter(s -> !s.isEmpty());
        traceid = traceid.isPresent() ? traceid : Optional.of(OrderGenerator.newOrder());
        traceid.ifPresent(id -> MDC.put("traceid", id));
        Node self = DistributionManager.getInstance().self();
        if (self != null) {
            MDC.put("IP", self.getIp());
        }
        nvwaFilterChain.doFilter();
    }
}

