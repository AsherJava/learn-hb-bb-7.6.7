/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam
 *  com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel
 *  com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam;
import com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel;
import com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.Map;
import org.json.JSONObject;

public abstract class WidgetRenderItem {
    private Widget widget;
    private LinkMsg linkMsg;
    private DashboardRenderContext context;
    private String errorMsg;
    private boolean start = false;
    private boolean init = false;

    public WidgetRenderItem(DashboardRenderContext context, Widget widget) {
        this.context = context;
        this.widget = widget;
    }

    public DashboardRenderContext getContext() {
        return this.context;
    }

    public void setContext(DashboardRenderContext context) {
        this.context = context;
    }

    public Widget getWidget() {
        return this.widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public LinkMsg getLinkMsg() {
        return this.linkMsg;
    }

    public void setLinkMsg(LinkMsg linkMsg) {
        this.linkMsg = linkMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isStart() {
        return this.start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isInit() {
        return this.init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public String processDrill(DrillInfoParam drillInfoParam) throws Exception {
        return "";
    }

    public abstract void buildWidgetRender(LinkMsg var1, WidgetRenderContext var2) throws Exception;

    public abstract void linkView(LinkMsg var1, WidgetRenderContext var2) throws Exception;

    public abstract BIDataSet getDataSet(DashboardDimDatasourceModel var1, ParameterDataSourceTaskContext var2) throws Exception;

    public abstract JSONObject getResult(String var1, boolean var2) throws Exception;

    public abstract IParameterEnv getParameterEnv() throws Exception;

    public abstract Map<String, MemoryDataSet<BIDataSetFieldInfo>> getDataSets() throws Exception;
}

