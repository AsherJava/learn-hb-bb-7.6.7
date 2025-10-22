/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.ReportTagDefine
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel
 */
package com.jiuqi.nr.datareport.obj;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NrDocumentParam {
    private Map<String, ReportTagDefine> tagMap;
    private ReportEnv reportEnv;
    private DimensionCombination dimensionCombination;
    private ExecutorContext executorContext;
    private Map<String, List<Object>> quickReportParam;
    private Map<String, String[]> chartParam;
    private final Map<String, DashboardModel> dashboardModelMap = new HashMap<String, DashboardModel>();

    public Map<String, ReportTagDefine> getTagMap() {
        return this.tagMap;
    }

    public void setTagMap(Map<String, ReportTagDefine> tagMap) {
        this.tagMap = tagMap;
    }

    public ReportEnv getReportEnv() {
        return this.reportEnv;
    }

    public void setReportEnv(ReportEnv reportEnv) {
        this.reportEnv = reportEnv;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public Map<String, List<Object>> getQuickReportParam() {
        return this.quickReportParam;
    }

    public void setQuickReportParam(Map<String, List<Object>> quickReportParam) {
        this.quickReportParam = quickReportParam;
    }

    public Map<String, String[]> getChartParam() {
        return this.chartParam;
    }

    public void setChartParam(Map<String, String[]> chartParam) {
        this.chartParam = chartParam;
    }

    public Map<String, DashboardModel> getDashboardModelMap() {
        return this.dashboardModelMap;
    }
}

