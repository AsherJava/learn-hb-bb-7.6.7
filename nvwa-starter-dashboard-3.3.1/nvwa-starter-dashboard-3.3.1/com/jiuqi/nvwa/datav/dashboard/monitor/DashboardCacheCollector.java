/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.datav.chart.engine.ChartModel
 *  com.jiuqi.nvwa.datav.chart.engine.IChartBuilderCache
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DSResultItem
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDatasetProvider
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef
 *  com.jiuqi.nvwa.datav.dashboard.textblock.engine.ITextBlockEngine
 *  com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException
 *  com.jiuqi.nvwa.datav.dashboard.trace.TraceLogInfo
 *  com.jiuqi.nvwa.datav.dashboard.trace.TraceLogInfo$TraceStage
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskContextBuilder
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.strategy.NodeTaskInstance
 *  com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.datav.dashboard.monitor;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.datav.chart.engine.ChartModel;
import com.jiuqi.nvwa.datav.chart.engine.IChartBuilderCache;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DSResultItem;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDatasetProvider;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef;
import com.jiuqi.nvwa.datav.dashboard.monitor.table.DashboardCacheStatInfo;
import com.jiuqi.nvwa.datav.dashboard.monitor.table.DashboardExecTraceInfo;
import com.jiuqi.nvwa.datav.dashboard.monitor.table.MonitorTableDao;
import com.jiuqi.nvwa.datav.dashboard.textblock.engine.ITextBlockEngine;
import com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException;
import com.jiuqi.nvwa.datav.dashboard.trace.TraceLogInfo;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskContextBuilder;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.strategy.NodeTaskInstance;
import com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardCacheCollector {
    @Autowired
    private MonitorTableDao dao;
    @Autowired
    private ITaskDispatcher dispatcher;
    private static Logger logger = LoggerFactory.getLogger(DashboardCacheCollector.class);

    public String collect() throws DashboardCacheException, TaskException, IOException {
        try {
            this.dao.createStatTableIfNotExists();
        }
        catch (Exception e) {
            throw new DashboardCacheException("\u521b\u5efa\u6570\u636e\u5e93\u8868\u5931\u8d25", (Throwable)e);
        }
        ArrayList<DashboardCacheStatInfo> cacheStats = new ArrayList<DashboardCacheStatInfo>();
        ArrayList<DashboardExecTraceInfo> execTraces = new ArrayList<DashboardExecTraceInfo>();
        this.collectDatasetCacheInfo(cacheStats);
        this.collectChartCacheInfo(cacheStats, execTraces);
        this.collectTextBlockCacheInfo(cacheStats, execTraces);
        try {
            this.dao.clearHistory();
            for (DashboardCacheStatInfo statInfo : cacheStats) {
                this.dao.insertCacheInfo(statInfo);
            }
            for (DashboardExecTraceInfo traceInfo : execTraces) {
                this.dao.insertTraceInfo(traceInfo);
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DashboardCacheException("\u6267\u884c\u6570\u636e\u5e93\u67e5\u8be2\u51fa\u9519", (Throwable)e);
        }
        JSONObject json = new JSONObject();
        JSONArray statJson = new JSONArray();
        for (DashboardCacheStatInfo stat : cacheStats) {
            JSONObject j = new JSONObject();
            j.put("taskId", (Object)stat.getTaskId());
            j.put("sessionId", (Object)stat.getSessionId());
            j.put("nodeIp", (Object)stat.getNodeIp());
            j.put("type", (Object)stat.getType());
            j.put("dsName", (Object)stat.getDsName());
            statJson.put((Object)j);
        }
        json.put("cacheInfos", (Object)statJson);
        return json.toString();
    }

    private void collectTextBlockCacheInfo(List<DashboardCacheStatInfo> cacheStats, List<DashboardExecTraceInfo> execTraces) throws TaskException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
        List textBlockCacheList = this.dispatcher.collectService("datav-textblock-engine");
        for (NodeTaskInstance item : textBlockCacheList) {
            ITaskContext taskContext = new TaskContextBuilder().taskId(item.getTaskId()).build();
            ITextBlockEngine textBlockCache = (ITextBlockEngine)this.dispatcher.findService(taskContext, ITextBlockEngine.class);
            if (textBlockCache == null) continue;
            if (!textBlockCache.isInited()) {
                logger.warn("\u670d\u52a1\u53d1\u751f\u4e86\u91cd\u542f\uff0c\u6570\u636e\u7f13\u5b58\u5df2\u7ecf\u88ab\u6e05\u7a7a");
                continue;
            }
            String sid = textBlockCache.getSessionId();
            DashboardCacheStatInfo stat = new DashboardCacheStatInfo();
            stat.setType("textblock");
            stat.setSessionId(sid);
            stat.setCreatetime(dateFormat.format(new Date(item.getCreateTime())));
            stat.setNodeIp(item.getNode().getIp());
            stat.setNodeName(item.getNode().getName());
            stat.setTaskId(item.getTaskId());
            Integer v = (Integer)nameMap.get(sid);
            if (v == null) {
                nameMap.put(sid, 1);
                stat.setChartTitle("\u6587\u672c");
            } else {
                nameMap.put(sid, v + 1);
                stat.setChartTitle("\u6587\u672c_" + v);
            }
            List dsRefs = textBlockCache.getTextBlockConfig().getDsRefs();
            ArrayList<String> nameBuf = new ArrayList<String>();
            int dsCount = 0;
            for (DSRef dsRef : dsRefs) {
                nameBuf.add(dsRef.getDsName());
                try {
                    dsCount += textBlockCache.getDataSet(dsRef).size();
                }
                catch (TextBlockException e) {
                    throw new TaskException((Throwable)e);
                }
            }
            stat.setDsName(StringUtils.join(nameBuf.iterator(), (String)";"));
            stat.setDsTitle(stat.getDsName());
            stat.setDsCount(dsCount);
            TraceLogInfo trace = textBlockCache.getTraceInfo();
            if (trace != null) {
                List stages = trace.getStages();
                long estimateTime = ((TraceLogInfo.TraceStage)stages.get((int)(stages.size() - 1))).timestamp - ((TraceLogInfo.TraceStage)stages.get((int)0)).timestamp;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                for (TraceLogInfo.TraceStage stage : stages) {
                    DashboardExecTraceInfo traceInfo = new DashboardExecTraceInfo();
                    traceInfo.setMsg(stage.msg);
                    traceInfo.setTaskId(item.getTaskId());
                    traceInfo.setTime(format.format(new Date(stage.timestamp)));
                    execTraces.add(traceInfo);
                }
                stat.setEstimateTime(estimateTime);
            }
            cacheStats.add(stat);
        }
    }

    private void collectChartCacheInfo(List<DashboardCacheStatInfo> cacheStats, List<DashboardExecTraceInfo> execTraces) throws TaskException, DashboardCacheException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
        List chartCacheList = this.dispatcher.collectService("chart-builder");
        for (NodeTaskInstance item : chartCacheList) {
            List stages;
            Integer v;
            ITaskContext taskContext = new TaskContextBuilder().taskId(item.getTaskId()).build();
            IChartBuilderCache chartBuilderCache = (IChartBuilderCache)this.dispatcher.findService(taskContext, IChartBuilderCache.class);
            if (chartBuilderCache == null) continue;
            if (!chartBuilderCache.isInited()) {
                logger.warn("\u670d\u52a1\u53d1\u751f\u4e86\u91cd\u542f\uff0c\u6570\u636e\u7f13\u5b58\u5df2\u7ecf\u88ab\u6e05\u7a7a\uff1a" + item.getTaskId());
                continue;
            }
            ChartModel chartModel = chartBuilderCache.getChartModel();
            String sid = chartBuilderCache.getSessionId();
            MemoryDataSet baseds = chartBuilderCache.getBaseDataSet();
            DashboardCacheStatInfo stat = new DashboardCacheStatInfo();
            stat.setType("chart");
            stat.setSessionId(sid);
            stat.setCreatetime(dateFormat.format(new Date(item.getCreateTime())));
            stat.setNodeIp(item.getNode().getIp());
            stat.setNodeName(item.getNode().getName());
            stat.setTaskId(item.getTaskId());
            String title = chartModel.getTitle().getText();
            if (StringUtils.isEmpty((String)title)) {
                title = chartModel.getChartType().getTitle();
            }
            if ((v = (Integer)nameMap.get(sid + title)) == null) {
                nameMap.put(sid + title, 1);
                stat.setChartTitle(title);
            } else {
                nameMap.put(sid + title, v + 1);
                stat.setChartTitle(title + "_" + v);
            }
            stat.setChartType(chartModel.getChartType().getTitle());
            stat.setDsName(chartModel.getDsRef().getDsName());
            stat.setDsTitle(chartModel.getDsRef().getDsName());
            stat.setDsCount(baseds == null ? 0 : baseds.size());
            TraceLogInfo trace = chartBuilderCache.getTraceInfo();
            if (trace != null && !(stages = trace.getStages()).isEmpty()) {
                long estimateTime = ((TraceLogInfo.TraceStage)stages.get((int)(stages.size() - 1))).timestamp - ((TraceLogInfo.TraceStage)stages.get((int)0)).timestamp;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                for (TraceLogInfo.TraceStage stage : stages) {
                    DashboardExecTraceInfo traceInfo = new DashboardExecTraceInfo();
                    traceInfo.setMsg(stage.msg);
                    traceInfo.setTaskId(item.getTaskId());
                    traceInfo.setTime(format.format(new Date(stage.timestamp)));
                    execTraces.add(traceInfo);
                }
                stat.setEstimateTime(estimateTime);
            }
            cacheStats.add(stat);
        }
    }

    private void collectDatasetCacheInfo(List<DashboardCacheStatInfo> cacheStats) throws TaskException, DashboardCacheException, IOException {
        List dsCacheList = this.dispatcher.collectService("chart-builder-dataset-cache");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        for (NodeTaskInstance item : dsCacheList) {
            ITaskContext taskContext = new TaskContextBuilder().taskId(item.getTaskId()).build();
            ICacheDatasetProvider dsCache = (ICacheDatasetProvider)this.dispatcher.findService(taskContext, ICacheDatasetProvider.class);
            if (dsCache == null) continue;
            List refs = dsCache.getCachedDsRef();
            for (DSRef ref : refs) {
                DashboardCacheStatInfo stat = new DashboardCacheStatInfo();
                stat.setType("dataset");
                stat.setCreatetime(dateFormat.format(new Date(item.getCreateTime())));
                stat.setNodeIp(item.getNode().getIp());
                stat.setNodeName(item.getNode().getName());
                stat.setTaskId(item.getTaskId() + "_" + ref.getDsName());
                stat.setSessionId(item.getTaskId());
                DSResultItem cacheItem = dsCache.getDSCache(ref);
                stat.setDsName(cacheItem.getDsModel().getName());
                stat.setDsTitle(cacheItem.getDsModel().getTitle());
                stat.setDsCount(cacheItem.getDataset().size());
                stat.setDsPvalues(new JSONObject(cacheItem.getParams()).toString());
                cacheStats.add(stat);
            }
        }
    }
}

