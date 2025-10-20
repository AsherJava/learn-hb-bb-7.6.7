/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy.listener;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DsProxyConfiguration;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorSystemOptionValueManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorTraceRecordManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorTraceSegmentIdUtil;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.OperationSpan;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.RecordTag;
import java.util.ArrayList;
import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class NvwaSqlExecutionListener
implements QueryExecutionListener {
    @Autowired
    private DsProxyConfiguration configuration;
    @Autowired
    @Lazy
    private MonitorTraceRecordManager traceRecordManager;
    @Autowired
    @Lazy
    MonitorSystemOptionValueManager systemOptionValueManager;
    private static final String SQL_ENTRY = "DATABASE";
    private static final String PREFIX = "/*NO_TRIGGER_SQL_MONITOR*/";

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        if (!ServiceNodeStateHolder.getState().equals((Object)ServiceNodeState.RUNNING)) {
            return;
        }
        for (QueryInfo queryInfo : queryInfoList) {
            if (!queryInfo.getQuery().startsWith(PREFIX)) continue;
            return;
        }
        if (!this.configuration.isBeforeQuery() || !this.systemOptionValueManager.getEnableSql().booleanValue()) {
            return;
        }
        int id = execInfo.hashCode();
        long startTime = System.currentTimeMillis();
        String traceId = LogTraceIDUtil.getLogTraceId();
        if (traceId == null) {
            traceId = OrderGenerator.newOrder();
            LogTraceIDUtil.setLogTraceId((String)traceId);
        }
        String segmentId = String.valueOf(MonitorTraceSegmentIdUtil.getUUID());
        execInfo.addCustomValue("TRACE_ID", traceId);
        execInfo.addCustomValue("START_TIME", startTime);
        execInfo.addCustomValue("SEGMENT_ID", segmentId);
        this.preRecord(execInfo, queryInfoList, startTime, traceId, segmentId);
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        if (!ServiceNodeStateHolder.getState().equals((Object)ServiceNodeState.RUNNING)) {
            return;
        }
        for (QueryInfo queryInfo : queryInfoList) {
            if (!queryInfo.getQuery().startsWith(PREFIX)) continue;
            return;
        }
        if (!this.configuration.isAfterQuery() || !this.systemOptionValueManager.getEnableSql().booleanValue()) {
            return;
        }
        String traceId = execInfo.getCustomValue("TRACE_ID", String.class);
        Long startTime = execInfo.getCustomValue("START_TIME", Long.class);
        String segmentId = execInfo.getCustomValue("SEGMENT_ID", String.class);
        Long endTime = System.currentTimeMillis();
        this.record(execInfo, queryInfoList, startTime, endTime, traceId, segmentId);
    }

    private void record(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, Long startTime, Long endTime, String traceId, String segmentId) {
        for (int i = 0; i < queryInfoList.size(); ++i) {
            QueryInfo queryInfo = queryInfoList.get(i);
            OperationSpan sqlRecord = this.recordFormer(execInfo, queryInfo, startTime, endTime, false);
            this.traceRecordManager.publish(sqlRecord, traceId, segmentId);
        }
    }

    private void preRecord(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, Long startTime, String traceId, String segmentId) {
        for (int i = 0; i < queryInfoList.size(); ++i) {
            QueryInfo queryInfo = queryInfoList.get(i);
            OperationSpan sqlRecord = this.recordFormer(execInfo, queryInfo, startTime, startTime, true);
            sqlRecord.setError(false);
            sqlRecord.setSpanLayer("Local");
            ArrayList<RecordTag> tags = new ArrayList<RecordTag>();
            tags.add(new RecordTag("db.type", "-1"));
            sqlRecord.setTags(tags);
            this.traceRecordManager.publish(sqlRecord, traceId, segmentId);
        }
    }

    private OperationSpan recordFormer(ExecutionInfo execInfo, QueryInfo queryInfo, Long startTime, Long endTime, boolean isBefore) {
        OperationSpan operationRecord = new OperationSpan();
        operationRecord.setStartTime(startTime);
        operationRecord.setEndTime(endTime);
        if (isBefore) {
            operationRecord.setOperationName("NOT FINISH:" + queryInfo.getQuery());
        } else {
            operationRecord.setOperationName(queryInfo.getQuery());
        }
        operationRecord.setSpanType("Exit");
        operationRecord.setError(!execInfo.isSuccess());
        operationRecord.setComponentId(6000);
        operationRecord.setPeer("service");
        operationRecord.setSpanLayer("Database");
        ArrayList<RecordTag> tags = new ArrayList<RecordTag>();
        tags.add(new RecordTag("db.statement", queryInfo.getQuery()));
        tags.add(new RecordTag("db.type", execInfo.getStatementType().name()));
        tags.add(new RecordTag("db.instance", execInfo.getDataSourceName()));
        StringBuilder stringBuffer = new StringBuilder();
        for (List<ParameterSetOperation> parameterSetOperations : queryInfo.getParametersList()) {
            for (ParameterSetOperation parameterSetOperation : parameterSetOperations) {
                for (Object arg : parameterSetOperation.getArgs()) {
                    stringBuffer.append(arg).append(";");
                }
            }
        }
        tags.add(new RecordTag("db.params", stringBuffer.toString()));
        operationRecord.setTags(tags);
        return operationRecord;
    }
}

