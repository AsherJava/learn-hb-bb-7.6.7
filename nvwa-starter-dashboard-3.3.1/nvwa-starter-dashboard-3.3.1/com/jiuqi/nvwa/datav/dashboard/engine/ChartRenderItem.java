/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.datav.chart.ChartException
 *  com.jiuqi.nvwa.datav.chart.ChartType
 *  com.jiuqi.nvwa.datav.chart.context.ChartContextFactory
 *  com.jiuqi.nvwa.datav.chart.context.IChartContext
 *  com.jiuqi.nvwa.datav.chart.dto.ChartRenderDTO
 *  com.jiuqi.nvwa.datav.chart.engine.BaseChartBuilder
 *  com.jiuqi.nvwa.datav.chart.engine.ChartFactory
 *  com.jiuqi.nvwa.datav.chart.engine.ChartModel
 *  com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam
 *  com.jiuqi.nvwa.datav.chart.engine.IChartProvider
 *  com.jiuqi.nvwa.datav.chart.engine.ModelValidateException
 *  com.jiuqi.nvwa.datav.chart.engine.common.ColumnInfo
 *  com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel
 *  com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext
 *  com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager
 *  com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.theme.manager.DashboardThemeManager
 *  com.jiuqi.nvwa.datav.dashboard.theme.model.Theme
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.datav.chart.ChartException;
import com.jiuqi.nvwa.datav.chart.ChartType;
import com.jiuqi.nvwa.datav.chart.context.ChartContextFactory;
import com.jiuqi.nvwa.datav.chart.context.IChartContext;
import com.jiuqi.nvwa.datav.chart.dto.ChartRenderDTO;
import com.jiuqi.nvwa.datav.chart.engine.BaseChartBuilder;
import com.jiuqi.nvwa.datav.chart.engine.ChartFactory;
import com.jiuqi.nvwa.datav.chart.engine.ChartModel;
import com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam;
import com.jiuqi.nvwa.datav.chart.engine.IChartProvider;
import com.jiuqi.nvwa.datav.chart.engine.ModelValidateException;
import com.jiuqi.nvwa.datav.chart.engine.common.ColumnInfo;
import com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel;
import com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext;
import com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager;
import com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderItem;
import com.jiuqi.nvwa.datav.dashboard.theme.manager.DashboardThemeManager;
import com.jiuqi.nvwa.datav.dashboard.theme.model.Theme;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ChartRenderItem
extends WidgetRenderItem {
    private BaseChartBuilder chartBuilder;

    public ChartRenderItem(DashboardRenderContext context, Widget widget, LinkMsg linkMsg) {
        super(context, widget);
        this.setLinkMsg(linkMsg);
    }

    @Override
    public String processDrill(DrillInfoParam drillInfoParam) throws Exception {
        if (this.chartBuilder == null) {
            throw new Exception("\u56fe\u8868\u6e32\u67d3\u5931\u8d25");
        }
        JSONObject result = new JSONObject();
        if (drillInfoParam.isDrillUp()) {
            this.chartBuilder.processDrillUp();
            result.put("canDrillUp", this.chartBuilder.isCanDrillUp());
        } else {
            this.chartBuilder.processDrillDown(drillInfoParam);
        }
        return result.toString();
    }

    @Override
    public void buildWidgetRender(LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        Widget widget = this.getWidget();
        JSONObject chartModelJSON = widget.getConfig().getData().optJSONObject("chartModel");
        IChartContext chartContext = ChartContextFactory.getInstance().buildChartContext("com.jiuqi.nvwa.datav.dashboard.chart", this.getContext().getUserGuid());
        String chartType = chartModelJSON.optString("chartType", "");
        IChartProvider chartProvider = ChartFactory.getInstance().getChartProvider(chartType);
        if (chartProvider == null) {
            chartModelJSON.put("chartType", (Object)ChartType.Extend.getType());
            chartProvider = ChartFactory.getInstance().getChartProvider(ChartType.Extend.getType());
        }
        ChartModel chartModel = DashboardChartUtils.parseChartModel((JSONObject)chartModelJSON, (IChartProvider)chartProvider);
        DashboardThemeManager dashboardThemeManager = (DashboardThemeManager)SpringBeanUtils.getBean(DashboardThemeManager.class);
        Theme theme = dashboardThemeManager.getThemeById(this.getContext().getThemeId(), true);
        BaseChartBuilder chartBuilder = chartProvider.createChartBuilder(chartModel, chartContext, theme, this.getContext().getUserGuid());
        chartBuilder.setSessionId(this.getContext().getSessionId());
        chartBuilder.setUseDatasetCache(!widgetRenderContext.isForceUpdateDataset());
        if (widgetRenderContext.isDrill()) {
            chartBuilder.linkView(linkMsg);
        } else {
            chartBuilder.init(chartModel, this.getContext().getUserGuid(), theme);
            chartBuilder.linkView(linkMsg);
        }
        this.chartBuilder = chartBuilder;
    }

    @Override
    public void linkView(LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        if (this.chartBuilder == null) {
            throw new ChartException("\u56fe\u8868\u6e32\u67d3\u5931\u8d25");
        }
        if (!widgetRenderContext.isDrill()) {
            while (this.chartBuilder.isCanDrillUp()) {
                this.chartBuilder.processDrillUp();
            }
        }
        this.chartBuilder.setUseDatasetCache(!widgetRenderContext.isForceUpdateDataset());
        if (widgetRenderContext.isForceUpdateDataset()) {
            this.chartBuilder.resetDataSet();
        }
        this.chartBuilder.linkView(linkMsg);
    }

    @Override
    public BIDataSet getDataSet(DashboardDimDatasourceModel datasource, ParameterDataSourceTaskContext context) {
        return this.chartBuilder != null ? this.chartBuilder.getBaseDataSet() : null;
    }

    public BaseChartBuilder getChartBuilder() {
        return this.chartBuilder;
    }

    public void setChartBuilder(BaseChartBuilder chartBuilder) {
        this.chartBuilder = chartBuilder;
    }

    @Override
    public JSONObject getResult(String sessionId, boolean renderParam) throws Exception {
        if (this.chartBuilder != null) {
            ChartRenderDTO chartRenderDTO = new ChartRenderDTO();
            try {
                String option = this.chartBuilder.buildChart();
                chartRenderDTO.setChartOption(option);
                Map<String, String> aliasMapping = this.getAliasMapping(this.chartBuilder.getBaseDataSet());
                chartRenderDTO.setAliasMap(aliasMapping);
                chartRenderDTO.setFilterMsg(this.chartBuilder.getFilterMsg());
                chartRenderDTO.setCanDrillUp(this.chartBuilder.isCanDrillUp());
            }
            catch (ModelValidateException e) {
                chartRenderDTO.setRenderErrorMessage(e.getMessage());
            }
            String taskId = sessionId + "$" + this.getWidget().getId();
            chartRenderDTO.setTaskId(taskId);
            if (renderParam) {
                DashboardChartManager dashboardChartManager = (DashboardChartManager)SpringBeanUtils.getBean(DashboardChartManager.class);
                dashboardChartManager.renderChartParams(chartRenderDTO, sessionId + "$" + this.getWidget().getId(), this.chartBuilder.getChartModel());
            }
            return chartRenderDTO.toJSON();
        }
        if (StringUtils.isNotEmpty((String)this.getErrorMsg())) {
            ChartRenderDTO chartRenderDTO = new ChartRenderDTO();
            chartRenderDTO.setRenderErrorMessage(this.getErrorMsg());
            return chartRenderDTO.toJSON();
        }
        throw new ChartException("\u56fe\u8868\u6e32\u67d3\u5931\u8d25");
    }

    @Override
    public IParameterEnv getParameterEnv() throws Exception {
        return this.chartBuilder != null ? this.chartBuilder.getDataSetParamEnv() : null;
    }

    @Override
    public Map<String, MemoryDataSet<BIDataSetFieldInfo>> getDataSets() throws Exception {
        if (this.chartBuilder == null || this.chartBuilder.getChartDataSet() == null || this.chartBuilder.getChartModel().getDsRef() == null || StringUtils.isEmpty((String)this.chartBuilder.getChartModel().getDsRef().getDsName())) {
            return null;
        }
        HashMap<String, MemoryDataSet<BIDataSetFieldInfo>> result = new HashMap<String, MemoryDataSet<BIDataSetFieldInfo>>();
        MemoryDataSet chartDataSet = this.chartBuilder.getChartDataSet();
        MemoryDataSet memory = new MemoryDataSet();
        Metadata metadata = memory.getMetadata();
        chartDataSet.getMetadata().getColumns().forEach(column -> {
            Column newColumn = new Column(column.getName(), column.getDataType());
            newColumn.setTitle(column.getTitle());
            newColumn.setInfo((Object)((ColumnInfo)column.getInfo()).getBaseInfo());
            metadata.getColumns().add(newColumn);
        });
        memory.add((DataSet)chartDataSet);
        result.put(this.chartBuilder.getChartModel().getDsRef().getDsName(), memory);
        return result;
    }

    private Map<String, String> getAliasMapping(BIDataSet baseDataSet) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (baseDataSet == null) {
            return map;
        }
        for (int i = 0; i < baseDataSet.getMetadata().getColumnCount(); ++i) {
            Column column = baseDataSet.getMetadata().getColumn(i);
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.MEASURE || StringUtils.isEmpty((String)((BIDataSetFieldInfo)column.getInfo()).getMessageAlias())) continue;
            map.put(column.getName(), ((BIDataSetFieldInfo)column.getInfo()).getMessageAlias());
        }
        return map;
    }
}

