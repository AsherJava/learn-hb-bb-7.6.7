/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.build.hyperlink;

import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.DimNameParameterFinder;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkContext;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.IDimParameterProvider;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.cache.DSFilterKey;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parameter.ParameterEnvHelper;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.hyperlink.HyperlinkManager;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkContext;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkEnv;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkExecutor;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;
import com.jiuqi.bi.quickreport.model.HyperlinkWindowInfo;
import com.jiuqi.bi.quickreport.model.ParameterInfo;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public final class HyperlinkEnv
implements IHyperlinkEnv {
    private final Map<String, Object> paramValues = new HashMap<String, Object>();
    private final List<DSFilterKey> restrictions = new ArrayList<DSFilterKey>();
    private final List<CellBindingInfo> linkInfos = new ArrayList<CellBindingInfo>();
    private Map<DSFilterKey, Integer> restrictionFinder;
    private static IDimParameterProvider dimParamProvider;

    public static void setDimParameterProvider(IDimParameterProvider provider) {
        dimParamProvider = provider;
    }

    @Deprecated
    public Map<String, Object> getParamValues() {
        return this.paramValues;
    }

    @Deprecated
    public List<DSFilterKey> getRestrictions() {
        return this.restrictions;
    }

    @Deprecated
    public List<CellLinkInfo> getLinkInfos() {
        return this.linkInfos.stream().map(CellLinkInfo::new).collect(Collectors.toList());
    }

    public void beginBuiding(ReportContext context) throws ReportBuildException {
        this.restrictionFinder = new HashMap<DSFilterKey, Integer>();
        if (!HyperlinkManager.isEnabled()) {
            return;
        }
        this.buildParamValues(context.getReport(), context.getParamEnv());
    }

    private void buildParamValues(QuickReport report, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) throws ReportBuildException {
        this.paramValues.clear();
        for (ParameterInfo paramInfo : report.getParameters()) {
            ParameterModel paramModel = paramInfo.getParamModel();
            if (!this.isMessageParam(paramModel)) continue;
            this.putParam(paramModel, paramEnv);
            this.putParamDimTree(paramInfo, paramEnv);
        }
    }

    private void putParamDimTree(ParameterInfo paramInfo, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) throws ReportBuildException {
        ParameterModel paramModel = paramInfo.getParamModel();
        if (paramModel.getDatasource() == null) {
            return;
        }
        try {
            String dimTreeCode = paramEnv.getDimTree(paramModel.getName());
            if (StringUtils.isEmpty((String)dimTreeCode)) {
                return;
            }
            String dimName = null;
            IParameterEnv rawEnv = ParameterEnvHelper.unwrap(paramEnv);
            if (rawEnv != null) {
                DimNameParameterFinder finder = new DimNameParameterFinder();
                dimName = finder.getDimName(paramInfo.getModel());
            } else if (dimParamProvider != null) {
                dimName = dimParamProvider.getDimName(paramEnv, paramModel);
            }
            if (StringUtils.isNotEmpty(dimName)) {
                String key = dimName + ".SYS_DIMTREE";
                this.paramValues.put(StringUtils.upperCase((String)key), dimTreeCode);
            }
        }
        catch (ParameterException e) {
            throw new ReportBuildException("\u83b7\u53d6\u53c2\u6570\u9009\u62e9\u7684\u7ef4\u5ea6\u6811\u5f62\u51fa\u73b0\u5f02\u5e38", e);
        }
    }

    private boolean isMessageParam(ParameterModel paramModel) {
        return paramModel != null;
    }

    private void putParam(ParameterModel paramModel, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) throws ReportBuildException {
        List values;
        try {
            values = paramEnv.getValueAsList(paramModel.getName());
        }
        catch (ParameterException e) {
            throw new ReportBuildException(e);
        }
        if (paramModel.getDataType() == 2) {
            ListIterator<String> i = values.listIterator();
            while (i.hasNext()) {
                Object value = i.next();
                if (!(value instanceof Calendar)) continue;
                String date = DataType.formatValue((int)2, value);
                i.set(date);
            }
        }
        String paramName = StringUtils.isEmpty((String)paramModel.getMessageAlias()) ? paramModel.getName() : paramModel.getMessageAlias();
        this.putParam(paramModel, paramName, values);
    }

    private void putParam(ParameterModel paramModel, String msgName, List<?> values) {
        if (paramModel.isRangeParameter()) {
            this.paramValues.put(StringUtils.upperCase((String)msgName), values);
        } else {
            List<Object> value = paramModel.getSelectMode() == ParameterSelectMode.SINGLE ? (values.isEmpty() ? null : values.get(0)) : (values.isEmpty() ? null : values);
            this.paramValues.put(StringUtils.upperCase((String)msgName), value);
        }
    }

    public void endBuilding() {
        this.restrictionFinder = null;
    }

    public int addRestrictions(List<IFilterDescriptor> filters) {
        DSFilterKey key = this.keyOf(filters);
        Integer index = this.restrictionFinder.get(key);
        if (index == null) {
            this.restrictions.add(key);
            index = this.restrictions.size() - 1;
            this.restrictionFinder.put(key, index);
        }
        return index;
    }

    private DSFilterKey keyOf(List<IFilterDescriptor> filters) {
        HashMap<String, IFilterDescriptor> msgFilters = new HashMap<String, IFilterDescriptor>();
        for (IFilterDescriptor filter : filters) {
            if (!this.isMessageFilter(filter)) continue;
            String msgName = StringUtils.isEmpty((String)filter.getField().getMessageAlias()) ? filter.getField().getName() : filter.getField().getMessageAlias();
            msgFilters.put(msgName, filter);
        }
        return new DSFilterKey("", msgFilters.values());
    }

    private boolean isMessageFilter(IFilterDescriptor filter) {
        if (filter.getField() == null) {
            return false;
        }
        DSField field = filter.getField();
        if (!field.isDimention() || field instanceof DSCalcField) {
            return false;
        }
        return filter instanceof ValueFilterDescriptor || filter instanceof ValuesFilterDescriptor;
    }

    public int addCell(CellBindingInfo cellInfo) {
        int index = this.linkInfos.indexOf(cellInfo);
        if (index >= 0) {
            return index;
        }
        this.linkInfos.add(cellInfo);
        return this.linkInfos.size() - 1;
    }

    @Override
    public void linkTo(Object httpRequest, Object httpResponse, int cellID, int filterID) throws ReportHyperlinkException {
        IHyperlinkContext linkContext = this.createContext(cellID, filterID);
        IHyperlinkExecutor executor = HyperlinkManager.createExecutor();
        executor.doLink(httpRequest, httpResponse, linkContext);
    }

    @Override
    public IHyperlinkContext createContext(int cellID, int filterID) {
        CellBindingInfo linkInfo = this.linkInfos.get(cellID);
        HyperlinkContext context = new HyperlinkContext(linkInfo.getCellMap().getHyperlink().getData());
        this.fillRestrictions(context, filterID);
        this.fillMeasures(context.getMeasures(), linkInfo.getDisplay() == null ? linkInfo.getValue() : linkInfo.getDisplay());
        return context;
    }

    private void fillRestrictions(HyperlinkContext context, int filterID) {
        DSFilterKey filters = this.restrictions.get(filterID);
        this.buildRestrictions(context.getRestrictions(), filters);
    }

    private void buildRestrictions(Map<String, Object> linkRestrictions, DSFilterKey filters) {
        linkRestrictions.putAll(this.paramValues);
        for (IFilterDescriptor filter : filters) {
            List<Object> value;
            DSField field = filter.getField();
            if (filter instanceof ValueFilterDescriptor) {
                value = ((ValueFilterDescriptor)filter).getValue();
            } else {
                if (!(filter instanceof ValuesFilterDescriptor)) continue;
                value = ((ValuesFilterDescriptor)filter).getValues();
            }
            String msgName = StringUtils.isEmpty((String)field.getMessageAlias()) ? field.getName() : field.getMessageAlias();
            linkRestrictions.put(StringUtils.upperCase((String)msgName), value);
        }
    }

    private void fillMeasures(List<String> measures, IReportExpression expression) {
        for (IASTNode node : expression) {
            DSFieldNode fieldNode;
            if (!(node instanceof DSFieldNode) || (fieldNode = (DSFieldNode)node).getField().getFieldType() != FieldType.MEASURE) continue;
            if (StringUtils.isEmpty((String)fieldNode.getField().getMessageAlias())) {
                measures.add(fieldNode.getField().getName());
                continue;
            }
            measures.add(fieldNode.getField().getMessageAlias());
        }
    }

    @Override
    public boolean resExisted(Object httpRequest, Object httpResponse, int cellID, int filterID) throws ReportHyperlinkException {
        IHyperlinkContext linkContext = this.createContext(cellID, filterID);
        IHyperlinkExecutor executor = HyperlinkManager.createExecutor();
        return executor.resExisted(httpRequest, httpResponse, linkContext);
    }

    @Override
    @Deprecated
    public HyperlinkWindowInfo resWindowInfo(Object httpRequest, Object httpResponse, int cellID, int filterID) {
        CellBindingInfo cellInfo = this.linkInfos.get(cellID);
        return cellInfo.getCellMap().getHyperlinkWindowInfo();
    }

    @Override
    public String resTitle(Object httpRequest, Object httpResponse, int cellID, int filterID) {
        CellBindingInfo cellInfo = this.linkInfos.get(cellID);
        if (cellInfo.getCellMap().getHyperlink().getData() == null) {
            return null;
        }
        return cellInfo.getCellMap().getHyperlink().getData().optString("resourceTitle");
    }

    public boolean isEmpty() {
        return this.linkInfos.isEmpty();
    }

    public List<com.jiuqi.bi.quickreport.engine.result.CellLinkInfo> toCellLinkInfos() {
        return this.linkInfos.stream().map(cellInfo -> {
            com.jiuqi.bi.quickreport.engine.result.CellLinkInfo linkInfo = new com.jiuqi.bi.quickreport.engine.result.CellLinkInfo();
            linkInfo.setData(cellInfo.getCellMap().getHyperlink().getData());
            linkInfo.setTargetMode(cellInfo.getCellMap().getHyperlink().getTargetMode());
            linkInfo.setTargetWidth(cellInfo.getCellMap().getHyperlink().getTargetWidth());
            linkInfo.setTargetHeight(cellInfo.getCellMap().getHyperlink().getTargetHeight());
            this.fillMeasures(linkInfo.getMeasures(), cellInfo.getDisplay() == null ? cellInfo.getValue() : cellInfo.getDisplay());
            return linkInfo;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> toCellLinkParams() {
        return this.restrictions.stream().map(filter -> {
            HashMap<String, Object> params = new HashMap<String, Object>();
            this.buildRestrictions((Map<String, Object>)params, (DSFilterKey)filter);
            return Collections.unmodifiableMap(params);
        }).collect(Collectors.toList());
    }

    @Deprecated
    public static final class CellLinkInfo {
        public final JSONObject target;
        public final HyperlinkWindowInfo wInfo;
        public final IReportExpression expression;

        public CellLinkInfo(CellBindingInfo cellInfo) {
            this.target = cellInfo.getCellMap().getHyperlink().getData();
            this.wInfo = cellInfo.getCellMap().getHyperlinkWindowInfo();
            this.expression = cellInfo.getDisplay() == null ? cellInfo.getValue() : cellInfo.getDisplay();
        }

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();
            json.put("target", (Object)this.target);
            if (this.wInfo != null) {
                json.put("wInfo", (Object)this.wInfo.toJSON());
            }
            JSONArray jsonExpr = new JSONArray();
            this.expression.forEach(node -> {
                if (!(node instanceof DSFieldNode)) {
                    return;
                }
                DSField dsField = ((DSFieldNode)((Object)node)).getField();
                if (dsField.getFieldType() != FieldType.MEASURE) {
                    return;
                }
                jsonExpr.put((Object)(StringUtils.isEmpty((String)dsField.getMessageAlias()) ? dsField.getName() : dsField.getMessageAlias()));
            });
            json.put("expression", (Object)jsonExpr);
            return json;
        }
    }
}

