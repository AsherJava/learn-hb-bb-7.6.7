/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorRecord;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorSystemOptionValueManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.OperationSpan;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEventProducer;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorTraceRecordManager {
    @Autowired
    private MonitorEventProducer monitorEventProducer;
    @Autowired
    private MonitorSystemOptionValueManager systemOptionValueManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorTraceRecordManager.class);
    private volatile String machineName;
    private volatile String serviceName;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void publish(OperationSpan span, String traceId, String segmentId) {
        MonitorTraceRecordManager monitorTraceRecordManager;
        MonitorRecord record = new MonitorRecord();
        String traceId2 = LogTraceIDUtil.getLogTraceId();
        if (StringUtils.isEmpty((String)traceId)) {
            traceId = traceId2;
        }
        if (traceId == null) {
            LOGGER.error("\u76d1\u63a7\u5e73\u53f0\uff1a\u65e5\u5fd7\u94fe\u8def\u8ffd\u8e2a\u4e8b\u52a1ID\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u4e0a\u62a5\uff01");
            return;
        }
        if (this.machineName == null) {
            monitorTraceRecordManager = this;
            synchronized (monitorTraceRecordManager) {
                if (this.machineName == null) {
                    this.machineName = DistributionManager.getInstance().getMachineName();
                }
            }
        }
        if (this.serviceName == null) {
            monitorTraceRecordManager = this;
            synchronized (monitorTraceRecordManager) {
                if (this.serviceName == null) {
                    this.serviceName = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name");
                }
            }
        }
        record.setTraceId(traceId);
        record.setTraceSegmentId(traceId + segmentId);
        record.setSpans(new ArrayList<OperationSpan>());
        record.setServiceInstance(this.machineName);
        record.setService(this.serviceName);
        span.setSpanId(0);
        span.setParentSpanId(-1);
        record.getSpans().add(span);
        this.publish(record);
    }

    public void publish(MonitorRecord record) {
        this.monitorEventProducer.publish(record);
    }
}

