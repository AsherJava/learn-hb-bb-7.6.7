/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.dataanalysis.dataset.remote.IRemoteDataSetStorageProvider
 *  com.jiuqi.nvwa.dataanalysis.dataset.remote.RemoteDSModel
 *  com.jiuqi.nvwa.datav.chart.ChartException
 *  com.jiuqi.nvwa.datav.chart.bean.DSModelResourceNode
 *  com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager
 *  com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.domain.WidgetConfig
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.json.JSONObject
 */
package com.jiuqi.gc.financialcubes.query.plugin.parselayerpluginimpl;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import com.jiuqi.gc.financialcubes.query.plugin.ParseLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationTaskUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.dataanalysis.dataset.remote.IRemoteDataSetStorageProvider;
import com.jiuqi.nvwa.dataanalysis.dataset.remote.RemoteDSModel;
import com.jiuqi.nvwa.datav.chart.ChartException;
import com.jiuqi.nvwa.datav.chart.bean.DSModelResourceNode;
import com.jiuqi.nvwa.datav.chart.manager.DashboardChartManager;
import com.jiuqi.nvwa.datav.dashboard.domain.DashboardModel;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.domain.WidgetConfig;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashBoardHandler
implements ParseLayerPenetrationPlugin {
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IRemoteDataSetStorageProvider dataSetStorageProvider;
    @Autowired
    private DashboardManager dashboardManager;
    @Autowired
    private DashboardChartManager dashboardChartManager;
    @Autowired
    private PenetrationTaskUtils penetrationTaskUtils;

    @Override
    public PenetrationType getType() {
        return PenetrationType.DASH_BOARD;
    }

    @Override
    public Map<String, String> handle(String linkMsg, PenetrationContextInfo context) {
        return this.preProcess(context, linkMsg);
    }

    private Map<String, String> preProcess(PenetrationContextInfo context, String linkMsg) {
        HashMap<String, String> msgMap = new HashMap<String, String>();
        Map linkmsgMap = JSONUtil.parseMap((String)linkMsg);
        Pair<String, String> guidAndWidId = this.extractGuidAndWidId(linkmsgMap);
        Map<String, String> dashBoardDataSourse = this.getDashBoardDsByGuidAndWidId((String)guidAndWidId.getFirst(), (String)guidAndWidId.getSecond());
        String dataSetGuid = this.getDataSetGuid(dashBoardDataSourse);
        RemoteDSModel model = null;
        try {
            model = this.dataSetStorageProvider.getModel(dataSetGuid);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6839\u636eguid\u67e5\u627e\u6570\u636e\u96c6\u51fa\u9519");
        }
        List parameterModels = model.getParameterModels();
        this.setupPenetrationContext(model, context);
        Map<String, String> dataSetConditions = this.getDataSetConditions(parameterModels, context);
        for (Map.Entry entry : linkmsgMap.entrySet()) {
            ArrayList valueList;
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            String targetValue = "";
            if (key.equals("__widgetId") || key.equals("__dhbGuid") || key.startsWith("NR_PERIOD") || key.endsWith(".MAX") || key.endsWith(".MIN") || key.equals("MD_ORG") || key.equals("C_ZBNAME") || key.equals("MD_ACCTSUBJECT.CODE") || key.equals("MD_ACCTSUBJECT.NAME") || key.startsWith("MD_ORG_")) continue;
            if (value instanceof ArrayList && !CollectionUtils.isEmpty((Collection)(valueList = (ArrayList)value))) {
                targetValue = String.join((CharSequence)",", (CharSequence[])valueList.stream().map(Object::toString).toArray(String[]::new));
            }
            msgMap.put(key, targetValue);
        }
        dataSetConditions.forEach(msgMap::putIfAbsent);
        return msgMap;
    }

    private void setupPenetrationContext(RemoteDSModel model, PenetrationContextInfo context) {
        String tableName = model.getFields().stream().filter(field -> field.getFieldType() == FieldType.MEASURE && !StringUtils.isEmpty((String)field.getName())).map(DSField::getName).findFirst().orElse(null);
        Assert.isNotNull((Object)tableName, (String)"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u8868\u540d\u4e3a\u7a7a", (Object[])new Object[0]);
        if (tableName.contains("_")) {
            String targetName = tableName.substring(0, tableName.lastIndexOf("_"));
            context.setDataSchemeTableCode(targetName);
            context.setDataSchemeKey(this.iRuntimeDataSchemeService.getDataTableByCode(targetName).getDataSchemeKey());
            String expression = this.iRuntimeDataSchemeService.getDataTableByCode(targetName).getExpression();
            Assert.isNotNull((Object)expression, (String)"\u76ee\u524d\u4e0d\u652f\u6301\u6b64\u60c5\u5f62\u7684\u7a7f\u900f\uff0c\u7f3a\u5c11\u53e3\u5f84", (Object[])new Object[0]);
        }
    }

    public Map<String, String> getDashBoardDsByGuidAndWidId(String guid, String widId) {
        DashboardModel dashboardModel = null;
        try {
            dashboardModel = this.dashboardManager.getDashboardModel(guid, false);
        }
        catch (DashboardException e) {
            throw new RuntimeException(e);
        }
        Assert.isNotNull((Object)dashboardModel, (String)"\u4eea\u8868\u76d8\u6570\u636e\u6a21\u578b\u4e3anull", (Object[])new Object[0]);
        List widgets = dashboardModel.getWidgets();
        Widget targetDashBoard = null;
        for (Widget widget : widgets) {
            if (!widId.equals(widget.getId())) continue;
            targetDashBoard = widget;
            break;
        }
        Assert.isNotNull(targetDashBoard, (String)"\u6ca1\u627e\u5230\u5bf9\u5e94\u7684\u4eea\u8868\u76d8", (Object[])new Object[0]);
        WidgetConfig config = targetDashBoard.getConfig();
        Assert.isNotNull((Object)config, (String)"\u4eea\u8868\u76d8\u914d\u7f6e\u4e3a\u7a7a", (Object[])new Object[0]);
        JSONObject configData = config.getData();
        Assert.isNotNull((Object)configData, (String)"\u4eea\u8868\u76d8\u5bf9\u8c61\u4e3aNull", (Object[])new Object[0]);
        Map dataMap = JSONUtil.parseMap((String)StringUtils.toViewString((Object)configData));
        Object chartModel = dataMap.get("chartModel");
        Assert.isInstanceOf(Map.class, chartModel, (String)"\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d", (Object[])new Object[0]);
        Map dashMap = (Map)chartModel;
        Object dashBoardDS = dashMap.get("dsRef");
        Assert.isInstanceOf(Map.class, dashBoardDS, (String)"\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d", (Object[])new Object[0]);
        return (Map)dashBoardDS;
    }

    public Pair<String, String> extractGuidAndWidId(Map<String, Object> linkmsgMap) {
        String guid = "";
        String widId = "";
        if (linkmsgMap.containsKey("__dhbGuid")) {
            guid = ConverterUtils.getAsString((Object)linkmsgMap.get("__dhbGuid"));
        }
        if (linkmsgMap.containsKey("__widgetId")) {
            widId = ConverterUtils.getAsString((Object)linkmsgMap.get("__widgetId"));
        }
        return new Pair((Object)guid, (Object)widId);
    }

    public String getDataSetGuid(Map<String, String> dashBoardDataSource) {
        String dsGuid = "";
        try {
            DSModelResourceNode dataSetResourceNode = this.dashboardChartManager.getDataSetResourceNodeByNameAndType(dashBoardDataSource.get("storageType"), dashBoardDataSource.get("dsName"), dashBoardDataSource.get("type"));
            dsGuid = dataSetResourceNode.getGuid();
        }
        catch (ChartException e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6e90\u6570\u636e\u96c6\u5931\u8d25");
        }
        return dsGuid;
    }

    public Map<String, String> getDataSetConditions(List<ParameterModel> parameterModels, PenetrationContextInfo context) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        for (ParameterModel param : parameterModels) {
            String key = StringUtils.isEmpty((String)param.getMessageAlias()) ? param.getName() : param.getMessageAlias();
            Object value = param.getValueConfig().getDefaultValue().getValue();
            String targetValue = "";
            if (!(value instanceof List)) continue;
            ArrayList valueList = (ArrayList)value;
            if (!valueList.isEmpty()) {
                targetValue = String.join((CharSequence)",", (CharSequence[])valueList.stream().map(Object::toString).toArray(String[]::new));
            }
            if (key.startsWith(this.penetrationTaskUtils.getSchemeNameById(context.getDataSchemeKey()))) {
                key = key.substring(key.indexOf(46) + 1);
            }
            resultMap.put(key, targetValue);
        }
        return resultMap;
    }
}

