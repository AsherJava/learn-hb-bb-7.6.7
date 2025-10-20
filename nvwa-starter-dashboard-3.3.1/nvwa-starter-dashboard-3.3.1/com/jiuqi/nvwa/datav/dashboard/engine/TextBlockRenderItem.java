/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel
 *  com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.textblock.domain.TextBlockConfig
 *  com.jiuqi.nvwa.datav.dashboard.textblock.engine.impl.TextBlockEngine
 *  com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException
 *  com.jiuqi.nvwa.datav.dashboard.textblock.util.TextBlockUtil
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel;
import com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderItem;
import com.jiuqi.nvwa.datav.dashboard.textblock.domain.TextBlockConfig;
import com.jiuqi.nvwa.datav.dashboard.textblock.engine.impl.TextBlockEngine;
import com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException;
import com.jiuqi.nvwa.datav.dashboard.textblock.util.TextBlockUtil;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class TextBlockRenderItem
extends WidgetRenderItem {
    private TextBlockEngine textBlockEngine;
    private TextBlockConfig config;
    private String result;

    public TextBlockRenderItem(DashboardRenderContext context, Widget widget, LinkMsg linkMsg) {
        super(context, widget);
        this.setLinkMsg(linkMsg);
    }

    @Override
    public void buildWidgetRender(LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        this.config = new TextBlockConfig();
        this.config.load(this.getWidget().getConfig().getData());
        this.textBlockEngine = new TextBlockEngine();
        this.textBlockEngine.setSessionId(this.getContext().getSessionId());
        this.textBlockEngine.init(this.config, this.getContext().getUserGuid(), linkMsg);
        this.linkView(linkMsg, widgetRenderContext);
    }

    @Override
    public void linkView(LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        if (this.textBlockEngine == null) {
            throw new Exception("\u6e32\u67d3\u5931\u8d25");
        }
        this.textBlockEngine.setUseDatasetCache(!widgetRenderContext.isForceUpdateDataset());
        if (widgetRenderContext.isForceUpdateDataset()) {
            this.textBlockEngine.resetDataSet();
        }
        this.textBlockEngine.linkView(linkMsg);
        this.result = this.textBlockEngine.parseContent(this.config.getContent());
    }

    @Override
    public BIDataSet getDataSet(DashboardDimDatasourceModel datasource, ParameterDataSourceTaskContext context) throws Exception {
        return this.textBlockEngine == null ? null : this.textBlockEngine.getDataset(datasource, context);
    }

    @Override
    public JSONObject getResult(String sessionId, boolean renderParam) throws Exception {
        if (this.result != null) {
            JSONObject result = new JSONObject();
            result.put("content", (Object)this.result);
            if (renderParam) {
                List parameterModels = TextBlockUtil.buildParameterModels((Widget)this.getWidget(), (TextBlockConfig)this.config, (String)sessionId);
                JSONArray paramJSONArray = TextBlockUtil.buildParameterModelsJSONArray((List)parameterModels);
                result.put("parameterModels", (Object)paramJSONArray);
            } else {
                result.put("parameterModels", Collections.emptyList());
            }
            return result;
        }
        if (StringUtils.isNotEmpty((String)this.getErrorMsg())) {
            throw new TextBlockException(this.getErrorMsg());
        }
        throw new Exception("\u6e32\u67d3\u5931\u8d25");
    }

    @Override
    public IParameterEnv getParameterEnv() throws Exception {
        return this.textBlockEngine == null ? null : this.textBlockEngine.getParamEnv();
    }

    @Override
    public Map<String, MemoryDataSet<BIDataSetFieldInfo>> getDataSets() throws Exception {
        if (this.textBlockEngine == null) {
            return null;
        }
        HashMap<String, MemoryDataSet<BIDataSetFieldInfo>> result = new HashMap<String, MemoryDataSet<BIDataSetFieldInfo>>();
        for (Map.Entry entry : this.textBlockEngine.getDatasetMap().entrySet()) {
            MemoryDataSet memory = new MemoryDataSet();
            Metadata metadata = memory.getMetadata();
            ((BIDataSet)entry.getValue()).getMetadata().getColumns().forEach(column -> {
                Column newColumn = new Column(column.getName(), column.getDataType());
                newColumn.setTitle(column.getTitle());
                newColumn.setInfo(column.getInfo());
                metadata.getColumns().add(newColumn);
            });
            for (int i = 0; i < ((BIDataSet)entry.getValue()).getRecordCount(); ++i) {
                BIDataRow biDataRow = ((BIDataSet)entry.getValue()).get(i);
                memory.add(biDataRow.getBuffer());
            }
            result.put((String)entry.getKey(), (MemoryDataSet<BIDataSetFieldInfo>)memory);
        }
        return result;
    }
}

