/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain
 *  com.jiuqi.nvwa.springadapter.filter.NvwaOncePerRequestFilter
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.filter;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorSystemOptionValueManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorTraceRecordManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorTraceSegmentIdUtil;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.OperationSpan;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.RecordTag;
import com.jiuqi.nvwa.springadapter.filter.NvwaFilterChain;
import com.jiuqi.nvwa.springadapter.filter.NvwaOncePerRequestFilter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=100)
public class WebMvcMetricsFilter
extends NvwaOncePerRequestFilter {
    private static final Log logger = LogFactory.getLog(WebMvcMetricsFilter.class);
    private static final Logger ACCESS_LOGGER = LoggerFactory.getLogger("MonitorAccessLog");
    @Autowired
    private MonitorTraceRecordManager traceRecordManager;
    @Autowired
    private MonitorSystemOptionValueManager systemOptionValueManager;
    private static final String HTTP_ENTRY = "HTTP";
    private static final int COMPONENT_ID = 6000;
    private static final String SPAN_LAYER_HTTP = "Http";
    private static final String PEER_SERVICE = "service";

    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    protected void doFilterInternal(NvwaFilterChain nvwaFilterChain) throws Exception {
        if (!ServiceNodeStateHolder.getState().equals((Object)ServiceNodeState.RUNNING) || !this.systemOptionValueManager.getEnable().booleanValue()) {
            nvwaFilterChain.doFilter();
            return;
        }
        boolean enableApi = this.systemOptionValueManager.getEnableApi();
        boolean enableLog = this.systemOptionValueManager.useLogRecord();
        if (!enableApi && !enableLog) {
            nvwaFilterChain.doFilter();
            return;
        }
        long startTime = System.currentTimeMillis();
        String segmentId = String.valueOf(MonitorTraceSegmentIdUtil.getUUID());
        if (enableApi) {
            this.preAddRecord(nvwaFilterChain, startTime, segmentId);
        }
        nvwaFilterChain.doFilter();
        try {
            int status = nvwaFilterChain.getResponseStatus();
            if (enableApi) {
                this.record(startTime, nvwaFilterChain, status, segmentId);
            }
            if (enableLog) {
                ACCESS_LOGGER.info(this.formAccessLog(nvwaFilterChain, startTime));
            }
        }
        catch (Exception e) {
            logger.error("\u76d1\u63a7\u8fc7\u6ee4\u5668\u5904\u7406\u5f02\u5e38", e);
            throw e;
        }
    }

    private String formAccessLog(NvwaFilterChain nvwaFilterChain, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        String timestamp = Instant.ofEpochMilli(startTime).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        return String.format("%s - - [%s] \"%s %s %s\" %d \"%s\" %dms", this.getClientIp(nvwaFilterChain), timestamp, nvwaFilterChain.getMethod(), nvwaFilterChain.getRequestURI(), nvwaFilterChain.getProtocol(), nvwaFilterChain.getResponseStatus(), NpContextHolder.getContext().getUserId(), duration);
    }

    private String getClientIp(NvwaFilterChain nvwaFilterChain) {
        String ip = nvwaFilterChain.getIpAddr();
        return ip;
    }

    private void buildCommonSpan(OperationSpan span, String spanType, boolean isError) {
        span.setSpanId(0);
        span.setParentSpanId(-1);
        span.setComponentId(6000);
        span.setPeer(PEER_SERVICE);
        span.setSpanLayer(SPAN_LAYER_HTTP);
        span.setSpanType(spanType);
        span.setError(isError);
    }

    private boolean isMonitoringEnabled() {
        return this.systemOptionValueManager.getEnable() != false && (this.systemOptionValueManager.getEnableApi() != false || this.systemOptionValueManager.useLogRecord() != false);
    }

    private void preAddRecord(NvwaFilterChain nvwaFilterChain, long startTime, String segmentId) {
        try {
            OperationSpan span = new OperationSpan();
            span.setOperationName("NOT FINISH:" + this.buildOperationName(nvwaFilterChain));
            span.setStartTime(startTime);
            span.setEndTime(startTime);
            this.buildCommonSpan(span, "Entry", true);
            span.setSpanLayer("Local");
            span.setError(false);
            ArrayList<RecordTag> tags = new ArrayList<RecordTag>();
            tags.add(new RecordTag("http.status_code", "-1"));
            span.setTags(tags);
            this.traceRecordManager.publish(span, null, segmentId);
        }
        catch (Exception e) {
            logger.error("\u521b\u5efaEntry\u76d1\u63a7\u8bb0\u5f55\u5931\u8d25", e);
        }
    }

    private String buildOperationName(NvwaFilterChain nvwaFilterChain) {
        return nvwaFilterChain.getMethod() + ":" + nvwaFilterChain.getRequestURI();
    }

    private void record(long startTime, NvwaFilterChain nvwaFilterChain, int status, String segmentId) {
        OperationSpan apiRecord = this.formatOperationSpan(nvwaFilterChain, status, startTime, System.currentTimeMillis());
        this.traceRecordManager.publish(apiRecord, null, segmentId);
    }

    private OperationSpan formatOperationSpan(NvwaFilterChain nvwaFilterChain, int status, long startTime, long endTime) {
        OperationSpan span = new OperationSpan();
        span.setStartTime(startTime);
        span.setEndTime(endTime);
        span.setOperationName(this.buildOperationName(nvwaFilterChain));
        this.buildCommonSpan(span, "Entry", status < 200 || status >= 300);
        ArrayList<RecordTag> tags = new ArrayList<RecordTag>();
        tags.add(new RecordTag("http.method", nvwaFilterChain.getMethod()));
        tags.add(new RecordTag("http.status_code", String.valueOf(status)));
        tags.add(new RecordTag("url", nvwaFilterChain.getRequestURL().toString()));
        span.setTags(tags);
        return span;
    }
}

