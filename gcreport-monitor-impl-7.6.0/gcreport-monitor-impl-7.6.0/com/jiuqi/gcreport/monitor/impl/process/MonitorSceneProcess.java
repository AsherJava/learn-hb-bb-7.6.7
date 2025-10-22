/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.monitor.impl.process;

import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.impl.config.MonitorSceneCollectUtils;
import com.jiuqi.gcreport.monitor.impl.entity.MonitorConfigDetailEO;
import com.jiuqi.gcreport.monitor.impl.util.MonitorUtil;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import org.apache.commons.lang3.StringUtils;

public class MonitorSceneProcess
extends RecursiveTask<Map<String, Map<String, MonitorStateEnum>>> {
    private final transient Map<String, MonitorConfigDetailEO> detailEOMap;
    private final List<String> tableNodes;
    private final List<String> list;
    private final MonitorArgument argument;
    private final NpContext context;

    public MonitorSceneProcess(List<String> tableNodes, Map<String, MonitorConfigDetailEO> detailEOMap, MonitorArgument argument, List<String> list, NpContext context) {
        this.detailEOMap = detailEOMap;
        this.tableNodes = tableNodes;
        this.argument = argument;
        this.list = list;
        this.context = context;
    }

    @Override
    protected Map<String, Map<String, MonitorStateEnum>> compute() {
        ConcurrentHashMap<String, Map<String, MonitorStateEnum>> ret = new ConcurrentHashMap<String, Map<String, MonitorStateEnum>>();
        NpContextHolder.setContext((NpContext)this.context);
        int maxNum = 2;
        if (this.tableNodes.size() >= maxNum) {
            List<List<String>> averageAssign = MonitorUtil.averageAssign(this.tableNodes, 2);
            MonitorSceneProcess subtask1 = new MonitorSceneProcess(averageAssign.get(0), this.detailEOMap, this.argument, this.list, this.context);
            MonitorSceneProcess subtask2 = new MonitorSceneProcess(averageAssign.get(1), this.detailEOMap, this.argument, this.list, this.context);
            MonitorSceneProcess.invokeAll(subtask1, subtask2);
            ret.putAll((Map)subtask1.join());
            ret.putAll((Map)subtask2.join());
            return ret;
        }
        this.tableNodes.forEach(s -> {
            MonitorScene monitorScene = MonitorSceneCollectUtils.getMonitorScene(s);
            MonitorConfigDetailEO configDetailEO = this.detailEOMap.get(s);
            String unitIds = configDetailEO.getUnitIds();
            if (StringUtils.isEmpty((CharSequence)unitIds)) {
                this.argument.setOrgIds(this.list);
            } else {
                this.argument.setOrgIds(Arrays.asList(unitIds.split(";")));
            }
            assert (monitorScene != null);
            Map states = monitorScene.getStates(this.argument);
            ret.put((String)s, states);
        });
        return ret;
    }
}

