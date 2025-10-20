/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.BIDataSetImpl
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.datav.chart.ChartException
 *  com.jiuqi.nvwa.datav.chart.engine.BaseChartBuilder
 *  com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam
 *  com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel
 *  com.jiuqi.nvwa.datav.chart.engine.param.IDimParameterDataSourceProviderTask
 *  com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext
 *  com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel
 *  com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DSResultItem
 *  com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.LocalDSRef
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory
 *  com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager
 *  com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider
 *  com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException
 *  com.jiuqi.nvwa.datav.dashboard.theme.model.Theme
 *  com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.datav.dashboard.engine.cache;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.datav.chart.ChartException;
import com.jiuqi.nvwa.datav.chart.engine.BaseChartBuilder;
import com.jiuqi.nvwa.datav.chart.engine.DrillInfoParam;
import com.jiuqi.nvwa.datav.chart.engine.param.DashboardDimDatasourceModel;
import com.jiuqi.nvwa.datav.chart.engine.param.IDimParameterDataSourceProviderTask;
import com.jiuqi.nvwa.datav.chart.engine.param.ParameterDataSourceTaskContext;
import com.jiuqi.nvwa.datav.chart.util.DashboardChartUtils;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel;
import com.jiuqi.nvwa.datav.dashboard.domain.LinkMsg;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.engine.ChartRenderItem;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.TextBlockRenderItem;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderContext;
import com.jiuqi.nvwa.datav.dashboard.engine.WidgetRenderItem;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DSResultItem;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardCacheException;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDashboardRenderProvider;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.LocalDSRef;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.factory.AbstractResourceWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.manager.ResourceWidgetConfigFactoryManager;
import com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider;
import com.jiuqi.nvwa.datav.dashboard.textblock.exception.TextBlockException;
import com.jiuqi.nvwa.datav.dashboard.theme.model.Theme;
import com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dispatchable(name="dashboard-render-cache", title="\u4eea\u8868\u76d8\u6e32\u67d3\u7ed3\u679c\u7f13\u5b58", expireTime=3600000L)
public class CacheDashboardRenderProvider
implements ICacheDashboardRenderProvider,
IDimParameterDataSourceProviderTask {
    private static final Logger logger = LoggerFactory.getLogger(CacheDashboardRenderProvider.class);
    private static final String WIDGET_TYPE_PARAMETER = "ParameterWidget";
    private static final String WIDGET_TYPE_TEXT = "TextWidget";
    private static final String WIDGET_TYPE_CHART = "ChartWidget";
    private static ExecutorService threadPool;
    private DashboardModel dashboardModel;
    private DashboardRenderContext context;
    private IParameterEnv publicParameterEnv;
    private String publicParameterError;
    private LinkMsg initLinkMsg;
    private final Map<String, WidgetRenderItem> widgetRenderMap = new LinkedHashMap<String, WidgetRenderItem>();
    private final Map<DSRef, DSResultItem> dataSetMap = new HashMap<DSRef, DSResultItem>();

    @Override
    public void init(String dashboardModel, LinkMsg linkMsg, DashboardRenderContext context) throws DashboardCacheException {
        this.dashboardModel = new DashboardModel();
        this.dashboardModel.fromJSON(new JSONObject(dashboardModel));
        this.context = context;
        this.buildDatasetCacheIfPossible();
        this.initRender(linkMsg);
    }

    private void initRender(LinkMsg linkMsg) {
        List widgets = this.dashboardModel.getWidgets();
        widgets.forEach(widget -> {
            logger.debug("widget:" + widget.getId() + ",type:" + widget.getType());
            switch (widget.getType()) {
                case "ParameterWidget": {
                    this.initPublicParameterWidget((Widget)widget, linkMsg);
                    break;
                }
                case "ChartWidget": {
                    this.widgetRenderMap.put(widget.getId(), new ChartRenderItem(this.context, (Widget)widget, linkMsg));
                    break;
                }
                case "TextWidget": {
                    this.widgetRenderMap.put(widget.getId(), new TextBlockRenderItem(this.context, (Widget)widget, linkMsg));
                    break;
                }
            }
        });
        NpContext npContext = NpContextHolder.getContext();
        new Thread(() -> {
            NpContextHolder.setContext((NpContext)npContext);
            if (this.publicParameterError == null) {
                try {
                    this.buildInitLinkMsg(linkMsg);
                }
                catch (DashboardCacheException e) {
                    throw new RuntimeException(e);
                }
                if (this.publicParameterError == null) {
                    this.buildWidgetRender();
                }
            }
        }).start();
    }

    private void buildWidgetRender() {
        NpContext npContext = NpContextHolder.getContext();
        threadPool = Executors.newFixedThreadPool(5);
        AtomicInteger counter = new AtomicInteger(0);
        for (Map.Entry<String, WidgetRenderItem> entry : this.widgetRenderMap.entrySet()) {
            if (threadPool.isShutdown()) break;
            WidgetRenderItem widgetRenderItem = entry.getValue();
            if (widgetRenderItem.isStart()) continue;
            threadPool.execute(() -> {
                NpContextHolder.setContext((NpContext)npContext);
                WidgetRenderItem widgetRenderItem2 = widgetRenderItem;
                synchronized (widgetRenderItem2) {
                    if (widgetRenderItem.isStart()) {
                        counter.incrementAndGet();
                        return;
                    }
                    widgetRenderItem.setStart(true);
                }
                try {
                    WidgetRenderContext widgetRenderContext = new WidgetRenderContext();
                    this.buildWidgetRender(widgetRenderItem, this.initLinkMsg, widgetRenderContext);
                }
                catch (Exception e) {
                    logger.error("\u7ec4\u4ef6[" + widgetRenderItem.getWidget().getId() + "]\u6e32\u67d3\u5931\u8d25\uff0c", (Object)e.getMessage(), (Object)e);
                }
                counter.incrementAndGet();
            });
        }
        while (counter.get() != this.widgetRenderMap.size()) {
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!threadPool.isShutdown()) {
            threadPool.shutdown();
        }
    }

    private void initPublicParameterWidget(Widget widget, LinkMsg linkMsg) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        if (widget.getConfig() != null && widget.getConfig().getData() != null) {
            JSONArray paramJsons = widget.getConfig().getData().optJSONArray("selected");
            for (int i = 0; i < paramJsons.length(); ++i) {
                JSONObject paramJson = paramJsons.getJSONObject(i);
                if (paramJson.optBoolean("isMemory")) {
                    ParameterModel parameterModel = new ParameterModel();
                    parameterModel.fromJson(paramJson.optJSONObject("jsonModel"));
                    parameterModels.add(parameterModel);
                    continue;
                }
                String paramName = paramJson.optString("name");
                try {
                    ParameterModel parameterModel = ParameterStorageManager.getInstance().findModel(new ParameterResourceIdentify(paramName, null, null), "com.jiuqi.nvwa.parameter.storage");
                    if (parameterModel == null) {
                        logger.error("\u53c2\u6570\u6a21\u578b\u3010" + paramName + "\u3011\u4e0d\u5b58\u5728");
                        continue;
                    }
                    parameterModels.add(parameterModel);
                    continue;
                }
                catch (ParameterStorageException e) {
                    this.publicParameterError = e.getMessage();
                }
            }
        }
        if (this.publicParameterError == null && !parameterModels.isEmpty()) {
            this.publicParameterEnv = new ParameterEnv(this.context.getUserGuid(), parameterModels);
            this.publicParameterEnv.setLanguage(this.context.getLanguage());
            try {
                this.receiveMsg(this.publicParameterEnv, linkMsg);
            }
            catch (ParameterException e) {
                this.publicParameterError = e.getMessage();
            }
        }
    }

    private void buildInitLinkMsg(LinkMsg linkMsg) throws DashboardCacheException {
        this.initLinkMsg = new LinkMsg();
        for (Map.Entry entry : linkMsg.getMessages().entrySet()) {
            this.initLinkMsg.getMessages().put(entry.getKey(), entry.getValue());
        }
        if (this.publicParameterEnv != null) {
            for (ParameterModel model : this.publicParameterEnv.getParameterModels()) {
                String key = StringUtils.isNotEmpty((String)model.getMessageAlias()) ? model.getMessageAlias().toUpperCase() : model.getName().toUpperCase();
                try {
                    IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)model.getDatasource());
                    List valueAsList = this.publicParameterEnv.getValueAsList(model.getName());
                    String[] values = new String[valueAsList.size()];
                    for (int i = 0; i < valueAsList.size(); ++i) {
                        values[i] = valueAsList.get(i) != null ? valueFormat.format(valueAsList.get(i)) : null;
                    }
                    this.initLinkMsg.getMessages().put(key, values);
                }
                catch (ParameterException e) {
                    this.publicParameterError = e.getMessage();
                    throw new DashboardCacheException((Throwable)e);
                }
            }
        }
    }

    @Override
    public void updateWidget(String widgetJson) throws DashboardCacheException {
        Widget widget = new Widget();
        widget.fromJSON(new JSONObject(widgetJson));
        WidgetRenderItem oldWidgetRenderItem = this.widgetRenderMap.get(widget.getId());
        logger.debug("widget:" + widget.getId() + ",type:" + widget.getType());
        if (widget.getType().equals(WIDGET_TYPE_CHART)) {
            this.widgetRenderMap.put(widget.getId(), new ChartRenderItem(this.context, widget, oldWidgetRenderItem != null ? oldWidgetRenderItem.getLinkMsg() : null));
        } else if (widget.getType().equals(WIDGET_TYPE_TEXT)) {
            this.widgetRenderMap.put(widget.getId(), new TextBlockRenderItem(this.context, widget, oldWidgetRenderItem != null ? oldWidgetRenderItem.getLinkMsg() : null));
        }
    }

    @Override
    public String processDrill(String widgetId, DrillInfoParam drillInfoParam) throws DashboardCacheException {
        WidgetRenderItem widgetRenderItem = this.widgetRenderMap.get(widgetId);
        if (widgetRenderItem == null) {
            throw new DashboardCacheException("\u7ec4\u4ef6[" + widgetId + "]\u4e0d\u5b58\u5728");
        }
        try {
            return widgetRenderItem.processDrill(drillInfoParam);
        }
        catch (Exception e) {
            throw new DashboardCacheException((Throwable)e);
        }
    }

    @Override
    public void updateTheme(String themeId) throws DashboardCacheException {
        try {
            Theme theme = DashboardChartUtils.parseTheme((String)themeId);
            if (theme == null) {
                throw new DashboardCacheException("\u4e3b\u9898\u4e0d\u5b58\u5728[" + themeId + "]");
            }
            this.context.setThemeId(theme.getItem().getGuid());
        }
        catch (ChartException e) {
            throw new DashboardCacheException((Throwable)e);
        }
    }

    @Override
    public String getQueryResult(String widgetId, LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        WidgetRenderItem widgetRenderItem = this.getWidgetRenderItem(widgetId, linkMsg, widgetRenderContext);
        if (widgetRenderItem == null) {
            return null;
        }
        return widgetRenderItem.getResult(widgetRenderItem.getContext().getSessionId(), widgetRenderContext.isRenderParam()).toString();
    }

    @Override
    public boolean isDatasetCached(DSRef dsRef) throws DashboardCacheException {
        return this.dataSetMap.containsKey(dsRef);
    }

    @Override
    public void destory() throws DashboardCacheException {
        if (!threadPool.isShutdown()) {
            threadPool.shutdown();
        }
    }

    @Override
    public void removeWidget(String widgetId) throws DashboardCacheException {
        this.widgetRenderMap.remove(widgetId);
    }

    @Override
    public GridData getGridData(String widgetId, LinkMsg linkMsg) throws Exception {
        WidgetRenderContext widgetRenderContext = new WidgetRenderContext();
        WidgetRenderItem widgetRenderItem = this.getWidgetRenderItem(widgetId, linkMsg, widgetRenderContext);
        if (widgetRenderItem instanceof ChartRenderItem) {
            ChartRenderItem chartRenderItem = (ChartRenderItem)widgetRenderItem;
            BaseChartBuilder chartBuilder = chartRenderItem.getChartBuilder();
            return chartBuilder.buildTable(true);
        }
        throw new Exception("\u4e0d\u652f\u6301\u7684\u6e32\u67d3\u7c7b\u578b");
    }

    public BIDataSet getDataset(DashboardDimDatasourceModel datasource, ParameterDataSourceTaskContext context) throws ParameterException {
        WidgetRenderItem widgetRenderItem;
        String[] split;
        String dsCacheGuid = datasource.getDsCacheGuid();
        BIDataSet dataset = null;
        if (StringUtils.isNotEmpty((String)dsCacheGuid) && (split = dsCacheGuid.split("\\$")).length >= 2 && (widgetRenderItem = this.widgetRenderMap.get(split[1])) != null) {
            if (widgetRenderItem.isStart() && !widgetRenderItem.isInit()) {
                long waitTime = 0L;
                while (!widgetRenderItem.isInit()) {
                    if (waitTime > 300000L) {
                        throw new ParameterException("\u67e5\u8be2\u8d85\u65f6");
                    }
                    try {
                        Thread.sleep(100L);
                        waitTime += 100L;
                    }
                    catch (InterruptedException e) {
                        throw new ParameterException((Throwable)e);
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)widgetRenderItem.getErrorMsg())) {
                logger.error(widgetRenderItem.getErrorMsg());
                throw new ParameterException(widgetRenderItem.getErrorMsg());
            }
            try {
                if (context.getLinkMsg() != null && !context.getLinkMsg().getMessages().isEmpty()) {
                    boolean dataChanged = false;
                    IParameterEnv parameterEnv = widgetRenderItem.getParameterEnv();
                    for (Map.Entry entry : context.getLinkMsg().getMessages().entrySet()) {
                        ParameterModel model = parameterEnv.getParameterModelByName((String)entry.getKey());
                        if (model == null) continue;
                        AbstractParameterValue v = parameterEnv.getOriginalValue((String)entry.getKey());
                        List<String> values = this.getKeysAsString(v, model);
                        if (values != null) {
                            if (this.equals(values.toArray(new String[0]), (String[])entry.getValue())) continue;
                            dataChanged = true;
                            break;
                        }
                        dataChanged = true;
                        break;
                    }
                    if (dataChanged) {
                        widgetRenderItem.linkView(context.getLinkMsg(), new WidgetRenderContext());
                    }
                }
                dataset = widgetRenderItem.getDataSet(datasource, context);
            }
            catch (Exception e) {
                throw new ParameterException(e.getMessage(), (Throwable)e);
            }
        }
        if (dataset == null) {
            return new BIDataSetImpl(new MemoryDataSet());
        }
        MemoryDataSet memory = new MemoryDataSet(dataset.getMetadata());
        dataset.iterator().forEachRemaining(row -> {
            try {
                memory.add(row.getBuffer());
            }
            catch (DataSetException e) {
                logger.error(e.getMessage(), e);
            }
        });
        return new BIDataSetImpl(memory);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MemoryDataSet<BIDataSetFieldInfo> getDataSet(DSRef dsRef) throws DashboardCacheException {
        DSResultItem item = this.dataSetMap.get(dsRef);
        if (item == null) {
            return null;
        }
        if (!item.isInit()) {
            DSResultItem dSResultItem = item;
            synchronized (dSResultItem) {
                try {
                    this.initDataset(item, null);
                }
                catch (Exception e) {
                    throw new DashboardCacheException(e.getMessage(), (Throwable)e);
                }
            }
        }
        return item.getDataset();
    }

    @Override
    public Map<String, MemoryDataSet<BIDataSetFieldInfo>> getDataSets(String widgetId) throws DashboardCacheException {
        WidgetRenderItem widgetRenderItem = this.widgetRenderMap.get(widgetId);
        if (widgetRenderItem == null) {
            return null;
        }
        try {
            return widgetRenderItem.getDataSets();
        }
        catch (Exception e) {
            throw new DashboardCacheException((Throwable)e);
        }
    }

    private List<String> getKeysAsString(AbstractParameterValue parameterValue, ParameterModel parameterModel) throws ParameterException {
        if (parameterValue == null) {
            return null;
        }
        IParameterValueFormat format = this.getParameterValueFormat(parameterModel);
        return parameterValue.getKeysAsString(null, format);
    }

    private IParameterValueFormat getParameterValueFormat(ParameterModel parameterModel) {
        ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
        AbstractParameterDataSourceFactory factory = mgr.getFactory(parameterModel.getDatasource().getType());
        Object format = factory != null ? factory.createValueFormat(parameterModel.getDatasource()) : new DefaultParameterValueFormat(parameterModel.getDatasource());
        return format;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setParamValue(DSRef dsRef, Map<String, List<Object>> parameterValueMap) throws DashboardCacheException {
        block12: {
            if (parameterValueMap == null || parameterValueMap.isEmpty()) {
                return;
            }
            DSResultItem item = this.dataSetMap.get(dsRef);
            if (item == null) {
                return;
            }
            try {
                if (!item.isInit()) {
                    DSResultItem dSResultItem = item;
                    synchronized (dSResultItem) {
                        this.initDataset(item, parameterValueMap);
                        break block12;
                    }
                }
                Map orgParams = item.getParams();
                if (this.equal(orgParams, parameterValueMap)) {
                    return;
                }
                DSResultItem dSResultItem = item;
                synchronized (dSResultItem) {
                    this.updateDataset(item, parameterValueMap);
                }
            }
            catch (Exception e) {
                throw new DashboardCacheException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private boolean equal(Map<String, List<Object>> v1, Map<String, List<Object>> v2) {
        if (v1.size() != v2.size()) {
            return false;
        }
        for (Map.Entry<String, List<Object>> entry : v1.entrySet()) {
            List<Object> l1 = entry.getValue();
            List<Object> l2 = v2.get(entry.getKey());
            if (l1 == null || l2 == null) {
                return false;
            }
            if (l1.size() != l2.size()) {
                return false;
            }
            HashSet<Object> s1 = new HashSet<Object>(l1);
            for (Object v : l2) {
                if (s1.contains(v)) continue;
                return false;
            }
        }
        return true;
    }

    private BIDataSet openDataset(DSModel dsModel, IParameterEnv env) throws DataSetTypeNotFoundException, BIDataSetException {
        DSContext context = new DSContext(dsModel, this.context.getUserGuid(), env);
        context.setI18nLang(this.context.getLanguage());
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        return dataSetManager.open((IDSContext)context, dsModel);
    }

    private MemoryDataSet<BIDataSetFieldInfo> convert(BIDataSet dataset) {
        MemoryDataSet rs = new MemoryDataSet(dataset.getMetadata());
        Iterator itor = dataset.iterator();
        while (itor.hasNext()) {
            try {
                rs.add(((BIDataRow)itor.next()).getBuffer());
            }
            catch (DataSetException dataSetException) {}
        }
        return rs;
    }

    private void initDataset(DSResultItem item, Map<String, List<Object>> parameterValueMap) throws Exception {
        if (item.isInit()) {
            return;
        }
        DSModel dsModel = DataSetManagerFactory.create().findModel(item.getDsRef().getDsName(), item.getDsRef().getType());
        ArrayList<ParameterModel> pms = new ArrayList<ParameterModel>();
        if (item.getParamRef() == null) {
            pms.addAll(dsModel.getParameterModels());
        } else {
            for (String param : item.getParamRef()) {
                ParameterModel pm = dsModel.findParamemterModel(param);
                if (pm == null) continue;
                pms.add(pm);
            }
        }
        ParameterEnv env = new ParameterEnv(this.context.getUserGuid(), pms);
        if (parameterValueMap != null) {
            env.initValue(parameterValueMap);
            item.getParams().clear();
            item.getParams().putAll(parameterValueMap);
        }
        item.setDsModel(dsModel);
        item.setEnv((IParameterEnv)env);
        BIDataSet dataset = this.openDataset(dsModel, (IParameterEnv)env);
        item.setDataset(this.convert(dataset));
        item.markInited();
    }

    private void updateDataset(DSResultItem item, Map<String, List<Object>> parameterValueMap) throws Exception {
        Map orgParams = item.getParams();
        if (this.equal(orgParams, parameterValueMap)) {
            return;
        }
        item.getEnv().initValue(parameterValueMap);
        BIDataSet dataset = this.openDataset(item.getDsModel(), item.getEnv());
        item.setDataset(this.convert(dataset));
        item.getParams().clear();
        item.getParams().putAll(parameterValueMap);
    }

    private void buildWidgetRender(WidgetRenderItem widgetRenderItem, LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) throws Exception {
        Widget widget = widgetRenderItem.getWidget();
        if (widget.getConfig() == null || widget.getConfig().getData() == null) {
            widgetRenderItem.setErrorMsg("\u7ec4\u4ef6\u914d\u7f6e\u4e3a\u7a7a");
            widgetRenderItem.setInit(true);
            return;
        }
        try {
            widgetRenderItem.buildWidgetRender(linkMsg, widgetRenderContext);
        }
        catch (Exception e) {
            if (e instanceof ChartException || e instanceof TextBlockException) {
                widgetRenderItem.setErrorMsg(e.getMessage());
                widgetRenderItem.setInit(true);
                return;
            }
            widgetRenderItem.setErrorMsg("\u7ec4\u4ef6\u6e32\u67d3\u5931\u8d25");
            widgetRenderItem.setInit(true);
            throw new Exception(e.getMessage(), e);
        }
        widgetRenderItem.setInit(true);
    }

    private void receiveMsg(IParameterEnv parameterEnv, LinkMsg linkMsg) throws ParameterException {
        HashMap<String, ParameterModel> aliasParamModelMap = new HashMap<String, ParameterModel>();
        for (ParameterModel parameterModel : parameterEnv.getParameterModels()) {
            String messageAlias = parameterModel.getMessageAlias();
            if (!StringUtils.isNotEmpty((String)messageAlias)) continue;
            aliasParamModelMap.put(messageAlias, parameterModel);
        }
        Map messages = linkMsg.getMessages();
        HashMap<String, List<String>> paramMessageMap = new HashMap<String, List<String>>();
        for (Map.Entry messageEntry : messages.entrySet()) {
            String linkMessageKey = (String)messageEntry.getKey();
            String[] paramValues = (String[])messageEntry.getValue();
            String paramName = aliasParamModelMap.containsKey(linkMessageKey) ? ((ParameterModel)aliasParamModelMap.get(linkMessageKey)).getName() : linkMessageKey;
            if (paramMessageMap.containsKey(paramName)) continue;
            paramMessageMap.put(paramName, Arrays.asList(paramValues));
        }
        parameterEnv.initValue(paramMessageMap);
    }

    private void buildDatasetCacheIfPossible() throws DashboardCacheException {
        HashMap<DSRef, List<Widget>> ds2WidgetMap = new HashMap<DSRef, List<Widget>>();
        ResourceWidgetConfigFactoryManager factoryMgr = ResourceWidgetConfigFactoryManager.getInstance();
        List widgets = this.dashboardModel.getWidgets();
        for (Object widget : widgets) {
            AbstractResourceWidgetConfigFactory abstractResourceWidgetConfigFactory = factoryMgr.getFactory(widget.getType());
            if (abstractResourceWidgetConfigFactory == null) continue;
            List refs = null;
            try {
                IWidgetConfigProvider configProvider = abstractResourceWidgetConfigFactory.createConfigProvider((Widget)widget);
                refs = configProvider == null ? null : configProvider.getRelatedDataset((Widget)widget);
            }
            catch (DashboardException e) {
                throw new DashboardCacheException((Throwable)e);
            }
            catch (UnsupportedOperationException e) {
                logger.warn(e.getMessage(), e);
            }
            if (refs == null) continue;
            refs.forEach(arg_0 -> this.lambda$buildDatasetCacheIfPossible$4((Widget)widget, ds2WidgetMap, arg_0));
        }
        Map<DSRef, List<Widget>> needCached = this.getAvailableCacheWidget(ds2WidgetMap);
        if (!needCached.isEmpty()) {
            try {
                for (Map.Entry entry : needCached.entrySet()) {
                    this.dataSetMap.put((DSRef)entry.getKey(), new DSResultItem((DSRef)entry.getKey(), null));
                }
            }
            catch (Exception e) {
                throw new DashboardCacheException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private void putIfAbsent(Widget widget, DSRef dsRef, Map<DSRef, List<Widget>> ds2WidgetMap) {
        List v = ds2WidgetMap.computeIfAbsent(dsRef, k -> new ArrayList());
        v.add(widget);
    }

    private Map<DSRef, List<Widget>> getAvailableCacheWidget(Map<DSRef, List<Widget>> ds2WidgetMap) {
        HashMap<DSRef, List<Widget>> map = new HashMap<DSRef, List<Widget>>();
        for (Map.Entry<DSRef, List<Widget>> entry : ds2WidgetMap.entrySet()) {
            List<Widget> widgets = entry.getValue();
            if (widgets.size() <= 1 || entry.getKey() instanceof LocalDSRef) continue;
            map.put(entry.getKey(), widgets);
        }
        return map;
    }

    private boolean equals(String[] list1, String[] list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.length != list2.length) {
            return false;
        }
        for (int i = 0; i < list1.length; ++i) {
            if (list1[i].equals(list2[i])) continue;
            return false;
        }
        return true;
    }

    private WidgetRenderItem getWidgetRenderItem(String widgetId, LinkMsg linkMsg, WidgetRenderContext widgetRenderContext) {
        WidgetRenderItem widgetRenderItem = this.widgetRenderMap.get(widgetId);
        if (widgetRenderItem == null) {
            return null;
        }
        try {
            if (widgetRenderItem.isInit()) {
                if (StringUtils.isNotEmpty((String)widgetRenderItem.getErrorMsg())) {
                    widgetRenderItem.setErrorMsg(null);
                    widgetRenderItem.setInit(false);
                    this.buildWidgetRender(widgetRenderItem, linkMsg, widgetRenderContext);
                } else {
                    widgetRenderItem.linkView(linkMsg, widgetRenderContext);
                }
            } else if (widgetRenderItem.isStart()) {
                while (!widgetRenderItem.isInit()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        throw new DashboardCacheException((Throwable)e);
                    }
                }
                widgetRenderItem.linkView(linkMsg, widgetRenderContext);
            } else {
                widgetRenderItem.setStart(true);
                this.buildWidgetRender(widgetRenderItem, linkMsg, widgetRenderContext);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            widgetRenderItem.setErrorMsg(e.getMessage());
        }
        return widgetRenderItem;
    }

    private /* synthetic */ void lambda$buildDatasetCacheIfPossible$4(Widget widget, Map ds2WidgetMap, DSRef c) {
        this.putIfAbsent(widget, c, ds2WidgetMap);
    }
}

