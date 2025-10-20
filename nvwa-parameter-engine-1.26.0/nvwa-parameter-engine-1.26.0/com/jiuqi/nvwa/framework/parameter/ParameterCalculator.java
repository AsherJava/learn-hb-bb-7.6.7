/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.digraph.DGLink
 *  com.jiuqi.bi.util.digraph.DGNode
 *  com.jiuqi.bi.util.digraph.Digraph
 *  com.jiuqi.bi.util.digraph.EDigraph
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.digraph.DGLink;
import com.jiuqi.bi.util.digraph.DGNode;
import com.jiuqi.bi.util.digraph.Digraph;
import com.jiuqi.bi.util.digraph.EDigraph;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.DimTreeHierarchy;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParameterCalculator {
    private final String userId;
    private String language;
    private String sessionId;
    private final List<ParameterModel> parameterModels;
    private final Map<String, ParameterModel> pmodelMap = new HashMap<String, ParameterModel>();
    private final Map<String, AbstractParameterValue> values = new HashMap<String, AbstractParameterValue>();
    private final Map<String, List<ParameterDependMember>> autoDependsMap = new HashMap<String, List<ParameterDependMember>>();
    private final Map<String, String> paramUnittreeMap = new HashMap<String, String>();
    private final Map<String, String> extras = new HashMap<String, String>();
    private transient Digraph<ParameterModel> graph;

    public ParameterCalculator(String userId, List<ParameterModel> models) {
        this.userId = userId;
        this.parameterModels = new ArrayList<ParameterModel>(models);
        for (ParameterModel pm : this.parameterModels) {
            this.pmodelMap.put(pm.getName().toUpperCase(), pm);
        }
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public boolean addParameterModel(ParameterModel model) {
        if (this.pmodelMap.containsKey(model.getName().toUpperCase())) {
            return false;
        }
        this.parameterModels.add(model);
        this.pmodelMap.put(model.getName().toUpperCase(), model);
        this.resetGraph();
        return true;
    }

    public void initValue(Map<String, AbstractParameterValue> valueMap) throws ParameterException {
        HashSet<String> names = new HashSet<String>();
        names.addAll(this.values.keySet());
        HashMap anlisValueMap = new HashMap();
        for (Map.Entry<String, AbstractParameterValue> entry : valueMap.entrySet()) {
            if (this.values.containsKey(entry.getKey().toUpperCase())) continue;
            List<ParameterModel> pms = this.findParameterModelByAlias(entry.getKey());
            if (pms.isEmpty()) {
                names.add(entry.getKey());
                continue;
            }
            pms.forEach(p -> {
                names.add(p.getName());
                anlisValueMap.put(p.getName(), entry.getValue());
            });
        }
        Digraph<ParameterModel> graph = this.buildGraph(names);
        try {
            graph.orderNodes(true);
        }
        catch (EDigraph e) {
            throw new ParameterException("\u6267\u884c\u53c2\u6570\u4f9d\u8d56\u62d3\u6251\u6392\u5e8f\u51fa\u9519", e);
        }
        List models = graph.getRawNodes();
        for (ParameterModel model : models) {
            AbstractParameterValue v = valueMap.get(model.getName());
            if (v == null) {
                v = (AbstractParameterValue)anlisValueMap.get(model.getName());
            }
            if (v == null) continue;
            this.setValue(model.getName(), v);
        }
        this.resetGraph();
    }

    public void setValue(String parameterName, AbstractParameterValue value) throws ParameterException {
        FixedMemberParameterValue f;
        String pname;
        ParameterModel model;
        int pos = parameterName.lastIndexOf(46);
        String range = null;
        if (pos > 0) {
            String realName = parameterName.substring(0, pos);
            String next = parameterName.substring(pos + 1).toUpperCase();
            if (next.equals("MAX") || next.equals("MIN")) {
                parameterName = realName;
                range = next;
            }
        }
        if ((model = this.getParameterModelByName(pname = parameterName.toUpperCase())) == null) {
            throw new ParameterException("\u53c2\u6570\u6a21\u578b\u4e0d\u5728\u5f53\u524d\u6267\u884c\u73af\u5883\uff1a" + pname);
        }
        if (pname.equalsIgnoreCase(model.getMessageAlias())) {
            pname = model.getName().toUpperCase();
        }
        if (model.getSelectMode() == ParameterSelectMode.SINGLE && value instanceof FixedMemberParameterValue) {
            List<Object> items;
            AbstractParameterDataSourceFactory factory;
            f = (FixedMemberParameterValue)value;
            int widgetType = model.getWidgetType();
            if (widgetType == ParameterWidgetType.DEFAULT.value() && (factory = ParameterDataSourceManager.getInstance().getFactory(model.getDatasource().getType())) != null) {
                IParameterRenderer renderer = factory.createParameterRenderer();
                widgetType = renderer.getDefaultWidgetType(model.getDatasource(), model.getSelectMode());
            }
            if ((items = f.getItems()).size() > 1) {
                if (widgetType == ParameterWidgetType.DATEPICKER.value() || model.getDatasource().getBusinessType() == DataBusinessType.TIME_DIM) {
                    for (int i = items.size() - 1; i > -1; --i) {
                        if (items.get(i) == null || !StringUtils.isNotEmpty((String)items.get(i).toString())) continue;
                        value = new FixedMemberParameterValue(items.get(i));
                        break;
                    }
                } else {
                    for (Object item : items) {
                        if (item == null || !StringUtils.isNotEmpty((String)item.toString())) continue;
                        value = new FixedMemberParameterValue(item);
                        break;
                    }
                }
            }
        }
        if (range == null) {
            if (model.getSelectMode() == ParameterSelectMode.RANGE && value instanceof FixedMemberParameterValue && (f = (FixedMemberParameterValue)value).getItems().size() == 1) {
                f.getItems().add(f.getItems().get(0));
            }
            this.values.put(pname, value);
        } else {
            FixedMemberParameterValue val = (FixedMemberParameterValue)this.values.get(pname);
            if (val == null) {
                val = new FixedMemberParameterValue();
                if (range.equals("MIN")) {
                    val.getItems().add(value.getValue());
                    val.getItems().add(null);
                } else {
                    val.getItems().add(null);
                    val.getItems().add(value.getValue());
                }
                this.values.put(pname, val);
            } else if (range.equals("MIN")) {
                val.getItems().set(0, value.getValue());
            } else {
                val.getItems().set(1, value.getValue());
            }
        }
    }

    public void setValue(String parameterName, List<String> value) throws ParameterException {
        String pname = parameterName.toUpperCase();
        ParameterModel model = this.getParameterModelByName(pname);
        if (model == null) {
            throw new ParameterException("\u53c2\u6570\u73af\u5883\u4e2d\u7f3a\u5c11\u6a21\u578b\u4fe1\u606f\uff1a" + pname);
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(model.getDatasource());
        ArrayList<Object> list = new ArrayList<Object>();
        for (String v : value) {
            list.add(format.parse(v));
        }
        this.setValue(parameterName, new FixedMemberParameterValue(list));
    }

    public AbstractParameterValue getOriginalValue(String parameterName) throws ParameterException {
        int pos = parameterName.lastIndexOf(46);
        String range = null;
        if (pos > 0) {
            String realName = parameterName.substring(0, pos);
            String next = parameterName.substring(pos + 1).toUpperCase();
            if (next.equals("MAX") || next.equals("MIN")) {
                parameterName = realName;
                range = next;
            }
        }
        AbstractParameterValue parameterVal = this.values.get(parameterName.toUpperCase());
        ParameterModel pmodel = this.findParameterModelByName(parameterName, true);
        if (pmodel == null) {
            throw new ParameterException("\u53c2\u6570\u6267\u884c\u73af\u5883\u7f3a\u5c11\u53c2\u6570\u4fe1\u606f\uff1a" + parameterName);
        }
        if (pmodel.isRangeParameter() && parameterVal != null) {
            IParameterValueFormat valueFormat = ParameterUtils.createValueFormat(pmodel.getDatasource());
            if (range != null) {
                if (range.equals("MIN")) {
                    Object v = parameterVal.toValueList(valueFormat).get(0);
                    parameterVal = v == null ? null : new FixedMemberParameterValue(v);
                } else if (range.equals("MAX")) {
                    Object v = parameterVal.toValueList(valueFormat).get(1);
                    parameterVal = v == null ? null : new FixedMemberParameterValue(v);
                }
            }
        }
        return parameterVal;
    }

    public ParameterResultset getValue(String parameterName) throws ParameterException {
        return this.getValue(parameterName, true);
    }

    public ParameterResultset getValue(String parameterName, boolean useCandidateValueWhenEmpty) throws ParameterException {
        AbstractParameterValue valueModel = this.getOriginalValue(parameterName);
        ParameterResultset result = this._computeParameterValue(parameterName, valueModel, useCandidateValueWhenEmpty);
        ParameterModel pmodel = this.findParameterModelByName(parameterName, false);
        if (pmodel.getSelectMode() == ParameterSelectMode.SINGLE && result.size() > 1) {
            result = new ParameterResultset(result.get(0));
        }
        return result;
    }

    public List<String> getParameterTopoList() throws ParameterException {
        this.initParameterGraph();
        List nodes = this.graph.getNodes();
        return nodes.stream().map(n -> ((ParameterModel)n.get()).getName()).collect(Collectors.toList());
    }

    public ParameterResultset computeParameterValue(String parameterName, AbstractParameterValue valueModel) throws ParameterException {
        return this._computeParameterValue(parameterName, valueModel, false);
    }

    private ParameterResultset _computeParameterValue(String parameterName, AbstractParameterValue valueModel, boolean useCandidateValueWhenEmpty) throws ParameterException {
        ParameterResultset result;
        ParameterModel pmodel;
        String pname = parameterName;
        int pos = parameterName.lastIndexOf(46);
        String range = null;
        if (pos > 0) {
            String realName = parameterName.substring(0, pos);
            String next = parameterName.substring(pos + 1).toUpperCase();
            if (next.equals("MAX") || next.equals("MIN")) {
                pname = realName.toUpperCase();
                range = next;
            }
        }
        if ((pmodel = this.findParameterModelByName(pname, true)) == null) {
            throw new ParameterException("\u53c2\u6570\u6267\u884c\u73af\u5883\u7f3a\u5c11\u53c2\u6570\u4fe1\u606f\uff1a" + pname);
        }
        if (pmodel.isRangeParameter() && range == null) {
            return this._computeRangeParameterValue(pname, valueModel);
        }
        if (pmodel.isRangeParameter()) {
            ParameterRangeValueConfig cfg = (ParameterRangeValueConfig)pmodel.getValueConfig();
            pmodel = pmodel.clone();
            AbstractParameterValueConfig clonedCfg = pmodel.getValueConfig();
            if (!range.equals("MIN")) {
                clonedCfg.setDefaultValueMode(cfg.getDefaultMaxValueMode());
                clonedCfg.setDefaultValue(cfg.getDefaultMaxValue());
            }
        }
        AbstractParameterValueConfig valueCfg = pmodel.getValueConfig();
        ParameterDataSourceContext context = new ParameterDataSourceContext(pmodel, this);
        IParameterDataSourceProvider dataProvider = this.getDataSourceDataProvider(pmodel);
        boolean orderByDefVal = false;
        if (valueModel == null) {
            String dvm = valueCfg.getDefaultValueMode();
            AbstractParameterValue dfv = valueCfg.getDefaultValue();
            if (dvm != null && (dvm.equals("appoint") || dvm.equals("expr"))) {
                if (dfv == null || dfv.isEmpty()) {
                    result = ParameterResultset.EMPTY_RESULTSET;
                } else {
                    result = dataProvider.getDefaultValue(context);
                    if (dvm.equals("appoint")) {
                        orderByDefVal = true;
                    }
                }
            } else {
                result = dataProvider.getDefaultValue(context);
            }
        } else if (valueModel.isEmpty()) {
            result = ParameterResultset.EMPTY_RESULTSET;
        } else {
            SmartSelectorParameterValue ssv;
            AbstractParameterValue val = valueModel;
            if (valueModel instanceof SmartSelectorParameterValue && !dataProvider.supportOptimizeSmartSelector() && !(ssv = (SmartSelectorParameterValue)valueModel).getValue().isFixedValueMode()) {
                ParameterResultset candidateVals = dataProvider.getCandidateValue(context, null);
                List<Object> fixed = ssv.filterValue(candidateVals.getValueAsList());
                if (ssv.getValue().getSelectMode().equals("fuzzy")) {
                    List<Object> filter_n = ssv.filterValue(candidateVals.getNameValueAsList());
                    filter_n.forEach(v -> {
                        List<ParameterResultItem> matchs = candidateVals.findByTitle((String)v);
                        matchs.stream().forEach(m -> fixed.add(m.getValue()));
                    });
                }
                val = new FixedMemberParameterValue(fixed);
            }
            result = dataProvider.compute(context, val);
        }
        if (useCandidateValueWhenEmpty && result.isEmpty() && pmodel.getSelectMode() != ParameterSelectMode.SINGLE) {
            ParameterCandidateValueMode candidateValMode = valueCfg.getCandidateMode();
            List<String> candidateVal = valueCfg.getCandidateValue();
            if (!(candidateValMode != ParameterCandidateValueMode.APPOINT && candidateValMode != ParameterCandidateValueMode.EXPRESSION || candidateVal.isEmpty())) {
                result = dataProvider.getCandidateValue(context, null);
                orderByDefVal = false;
            }
        }
        if (!orderByDefVal && valueCfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            List<String> candidateVal = valueCfg.getCandidateValue();
            result.sortByKeysOrder(candidateVal);
        }
        if (pmodel.isOrderReverse()) {
            result.reverse();
        }
        return result;
    }

    private ParameterResultset _computeRangeParameterValue(String pname, AbstractParameterValue valueModel) throws ParameterException {
        FixedMemberParameterValue maxV;
        FixedMemberParameterValue minV;
        if (valueModel == null) {
            minV = null;
            maxV = null;
        } else if (valueModel instanceof FixedMemberParameterValue) {
            FixedMemberParameterValue fixed = (FixedMemberParameterValue)valueModel;
            List<Object> items = fixed.getItems();
            minV = new FixedMemberParameterValue(items.size() > 0 ? items.get(0) : null);
            maxV = new FixedMemberParameterValue(items.size() > 1 ? items.get(1) : null);
        } else {
            throw new ParameterException("\u4f20\u5165\u7684\u53c2\u6570\u53d6\u503c\u4fe1\u606f\u9519\u8bef");
        }
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        if (minV != null && minV.getItems().get(0) == null) {
            items.add(new ParameterResultItem(null));
        } else {
            ParameterResultset min = this.computeParameterValue(pname + ".MIN", minV);
            if (min.isEmpty()) {
                items.add(new ParameterResultItem(null));
            } else {
                items.add(min.get(0));
            }
        }
        if (maxV != null && maxV.getItems().get(0) == null) {
            items.add(new ParameterResultItem(null));
        } else {
            ParameterResultset max = this.computeParameterValue(pname + ".MAX", maxV);
            if (max.isEmpty()) {
                items.add(new ParameterResultItem(null));
            } else {
                items.add(max.get(0));
            }
        }
        return new ParameterResultset(items);
    }

    public int getCandidateValueCount(String parameterName) throws ParameterException {
        ParameterModel pmodel = this.getParameterModelByName(parameterName);
        IParameterDataSourceProvider dataProvider = this.getDataSourceDataProvider(pmodel);
        ParameterDataSourceContext context = new ParameterDataSourceContext(pmodel, this);
        return dataProvider.getCandidateValueCount(context);
    }

    public ParameterResultset getCandidateValue(String parameterName) throws ParameterException {
        return this.getCandidateValue(parameterName, null);
    }

    public ParameterResultset getCandidateValue(String parameterName, ParameterDataSourceContext.PageInfo pageInfo) throws ParameterException {
        ParameterModel pmodel = this.getParameterModelByName(parameterName);
        IParameterDataSourceProvider dataProvider = this.getDataSourceDataProvider(pmodel);
        ParameterDataSourceContext context = new ParameterDataSourceContext(pmodel, this);
        context.setPageInfo(pageInfo);
        ParameterResultset result = dataProvider.getCandidateValue(context, null);
        AbstractParameterValueConfig valueCfg = pmodel.getValueConfig();
        if (valueCfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            List<String> candidateVal = valueCfg.getCandidateValue();
            result.sortByKeysOrder(candidateVal);
        }
        if (pmodel.isOrderReverse()) {
            result.reverse();
        }
        return result;
    }

    public ParameterResultset getChildValue(String parameterName, String parent) throws ParameterException {
        return this.getChildValue(parameterName, new ParameterHierarchyFilterItem(parent));
    }

    public ParameterResultset getChildValue(String parameterName, ParameterHierarchyFilterItem filterItem) throws ParameterException {
        ParameterModel pmodel = this.getParameterModelByName(parameterName);
        IParameterDataSourceProvider dataProvider = this.getDataSourceDataProvider(pmodel);
        ParameterDataSourceContext context = new ParameterDataSourceContext(pmodel, this);
        ParameterResultset result = dataProvider.getCandidateValue(context, filterItem);
        AbstractParameterValueConfig valueCfg = pmodel.getValueConfig();
        if (valueCfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            List<String> candidateVal = valueCfg.getCandidateValue();
            result.sortByKeysOrder(candidateVal);
        }
        if (pmodel.isOrderReverse()) {
            result.reverse();
        }
        return result;
    }

    public ParameterResultset search(String parameterName, List<String> searchValues) throws ParameterException {
        return this.search(parameterName, searchValues, new ParameterDataSourceContext.PageInfo(1, 50));
    }

    public ParameterResultset search(String parameterName, List<String> searchValues, ParameterDataSourceContext.PageInfo pageInfo) throws ParameterException {
        ParameterModel pmodel = this.getParameterModelByName(parameterName);
        IParameterDataSourceProvider dataProvider = this.getDataSourceDataProvider(pmodel);
        ParameterDataSourceContext context = new ParameterDataSourceContext(pmodel, this);
        context.setPageInfo(pageInfo);
        return dataProvider.search(context, searchValues);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }

    public List<ParameterModel> getParameterModels() {
        return new ArrayList<ParameterModel>(this.parameterModels);
    }

    public ParameterModel getParameterModelByName(String name) {
        return this.findParameterModelByName(name, false);
    }

    public ParameterModel findParameterModelByName(String name, boolean appendWhenNotExist) {
        ParameterModel model;
        int pos = name.lastIndexOf(46);
        if (pos > 0) {
            String realName = name.substring(0, pos);
            String next = name.substring(pos + 1).toUpperCase();
            if (next.equals("MAX") || next.equals("MIN")) {
                name = realName;
            }
        }
        if ((model = this.pmodelMap.get(name.toUpperCase())) == null) {
            for (ParameterModel m : this.parameterModels) {
                if (m.getMessageAlias() == null || !m.getMessageAlias().equalsIgnoreCase(name)) continue;
                model = m;
                break;
            }
        }
        if (model == null && appendWhenNotExist) {
            try {
                model = ParameterStorageManager.getInstance().findModel(name);
            }
            catch (ParameterStorageException e) {
                e.printStackTrace();
            }
            if (model != null) {
                this.addParameterModel(model);
            }
        }
        return model;
    }

    public List<ParameterModel> findParameterModelByAlias(String alias) {
        return this.parameterModels.stream().filter(p -> alias.equalsIgnoreCase(p.getMessageAlias())).collect(Collectors.toList());
    }

    public IParameterDataSourceProvider getDataSourceDataProvider(ParameterModel pmodel) throws ParameterException {
        String dstype = pmodel.getDatasource().getType();
        ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
        AbstractParameterDataSourceFactory factory = mgr.getFactory(dstype);
        if (factory == null) {
            throw new ParameterException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u6570\u636e\u6765\u6e90\u63d0\u4f9b\u5668", null);
        }
        return factory.create(pmodel.getDatasource());
    }

    public List<DimTreeHierarchy> getCandidateDimTree(String parameterName) throws ParameterException {
        ParameterModel pmodel = this.getParameterModelByName(parameterName);
        if (pmodel == null) {
            return null;
        }
        IParameterDataSourceProvider provider = this.getDataSourceDataProvider(pmodel);
        return provider.getCandidateTreeHierarchies(new ParameterDataSourceContext(pmodel, this));
    }

    public String getDimTree(String parameterName) throws ParameterException {
        if (this.paramUnittreeMap.containsKey(parameterName = parameterName.toUpperCase())) {
            return this.paramUnittreeMap.get(parameterName);
        }
        List<DimTreeHierarchy> hierarchies = this.getCandidateDimTree(parameterName);
        String defaultUnittreeId = null;
        if (hierarchies != null && !hierarchies.isEmpty()) {
            defaultUnittreeId = hierarchies.get(0).getCode();
        }
        this.paramUnittreeMap.put(parameterName, defaultUnittreeId);
        return defaultUnittreeId;
    }

    public void setDimTree(String parameterName, String dimTreeCode) throws ParameterException {
        boolean valid = false;
        List<DimTreeHierarchy> hierarchies = this.getCandidateDimTree(parameterName = parameterName.toUpperCase());
        if (hierarchies != null && !hierarchies.isEmpty()) {
            for (DimTreeHierarchy hier : hierarchies) {
                if (!hier.getCode().equals(dimTreeCode)) continue;
                valid = true;
                break;
            }
        }
        if (valid) {
            this.paramUnittreeMap.put(parameterName, dimTreeCode);
            ParameterResultset value = this.getValue(parameterName);
            FixedMemberParameterValue fixedValue = new FixedMemberParameterValue(value.getValueAsList());
            this.values.put(parameterName, fixedValue);
            this.updateCascadedParameterDimTree(parameterName, dimTreeCode);
        }
    }

    public List<ParameterDependMember> getDependParameters(String parameterName) throws ParameterException {
        this.initParameterGraph();
        List nodes = this.graph.getNodes();
        for (DGNode node : nodes) {
            if (!((ParameterModel)node.get()).getName().equals(parameterName)) continue;
            List<ParameterDependMember> modelDepends = ((ParameterModel)node.get()).getValueConfig().getDepends();
            List<ParameterDependMember> autoDepends = this.autoDependsMap.get(parameterName);
            HashMap map = new HashMap();
            if (modelDepends != null) {
                modelDepends.forEach(d -> map.put(d.getParameterName(), d));
            }
            if (autoDepends != null) {
                autoDepends.forEach(d -> map.put(d.getParameterName(), d));
            }
            ArrayList<ParameterDependMember> list = new ArrayList<ParameterDependMember>();
            node.getDepends().forEach(dn -> {
                String pm = ((ParameterModel)dn.getInitial().get()).getName();
                ParameterDependMember m = (ParameterDependMember)map.get(pm);
                if (m == null) {
                    System.out.println("\u53c2\u6570\u3010" + parameterName + "\u3011\u4f9d\u8d56\u53c2\u6570\u3010" + pm + "\u3011\uff0c\u4f46\u662f\u4ece\u6a21\u578b\u4e0a\u6ca1\u6709\u53d6\u5230\u8fd9\u4e2a\u4f9d\u8d56\u5173\u7cfb");
                } else {
                    list.add(m);
                }
            });
            return list;
        }
        StringBuilder b = new StringBuilder();
        b.append("\u627e\u4e0d\u5230\u8be5\u53c2\u6570\u6a21\u578b\u5bf9\u8c61\uff1a").append(parameterName).append("[");
        nodes.forEach(c -> b.append(((ParameterModel)c.get()).getName()).append(", "));
        b.append("]");
        throw new ParameterException(b.toString());
    }

    public List<String> getAffectedParameters(String parameterName) throws ParameterException {
        this.initParameterGraph();
        List nodes = this.graph.getNodes();
        for (DGNode node : nodes) {
            if (!((ParameterModel)node.get()).getName().equals(parameterName)) continue;
            ArrayList<String> list = new ArrayList<String>();
            node.getAffects().forEach(dn -> list.add(((ParameterModel)dn.getTerminal().get()).getName()));
            return list;
        }
        StringBuilder b = new StringBuilder();
        b.append("\u627e\u4e0d\u5230\u8be5\u53c2\u6570\u6a21\u578b\u5bf9\u8c61\uff1a").append(parameterName).append("[");
        nodes.forEach(c -> b.append(((ParameterModel)c.get()).getName()).append(", "));
        b.append("]");
        throw new ParameterException(b.toString());
    }

    private void initParameterGraph() throws ParameterException {
        if (this.graph == null) {
            ArrayList<String> pnames = new ArrayList<String>();
            for (ParameterModel pm : this.parameterModels) {
                pnames.add(pm.getName());
            }
            this.graph = this.buildGraph(pnames);
            try {
                this.graph.orderNodes(true);
            }
            catch (EDigraph e) {
                throw new ParameterException("\u6267\u884c\u53c2\u6570\u4f9d\u8d56\u62d3\u6251\u6392\u5e8f\u51fa\u9519", e);
            }
        }
    }

    private void resetGraph() {
        this.graph = null;
    }

    public String getExtraValue(String key) {
        return this.extras.get(key);
    }

    public void setExtraValue(String key, String value) {
        this.extras.put(key, value);
    }

    public void updateCascadedParameterDimTree(String parameterName, String dimTreeCode) throws ParameterException {
        int pos = dimTreeCode.indexOf(46);
        if (pos < 0) {
            return;
        }
        String dim = dimTreeCode.substring(0, pos);
        Set<Map.Entry<String, String>> entries = this.paramUnittreeMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            boolean sameDim;
            int p;
            if (entry.getKey().equalsIgnoreCase(parameterName) || entry.getValue() == null || (p = entry.getValue().indexOf(46)) < 0 || !(sameDim = entry.getValue().substring(0, p).equalsIgnoreCase(dim))) continue;
            this.paramUnittreeMap.put(entry.getKey(), dimTreeCode);
        }
    }

    private Digraph<ParameterModel> buildGraph(Collection<String> paramNames) throws ParameterException {
        Digraph<ParameterModel> graph = new Digraph<ParameterModel>(){

            protected DGNode<ParameterModel> processDeadRoute(List<DGNode<ParameterModel>> route) throws EDigraph {
                return route.get(0);
            }
        };
        HashMap<String, DGNode> map = new HashMap<String, DGNode>();
        for (String paramName : paramNames) {
            ParameterModel model = this.getParameterModelByName(paramName);
            if (model == null) continue;
            DGNode node = graph.add((Object)model);
            map.put(paramName.toUpperCase(), node);
        }
        for (DGNode node : map.values()) {
            List<ParameterDependMember> depends = ((ParameterModel)node.get()).getValueConfig().getDepends();
            for (ParameterDependMember m : depends) {
                DGNode initial = (DGNode)map.get(m.getParameterName());
                if (initial == null) continue;
                graph.link(initial, node);
            }
        }
        ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
        for (String paramName : paramNames) {
            List<String> affects;
            ParameterModel model = this.getParameterModelByName(paramName);
            if (model == null) continue;
            AbstractParameterDataSourceFactory factory = mgr.getFactory(model.getDatasource().getType());
            if (factory == null) {
                throw new ParameterException("\u672a\u77e5\u7684\u53c2\u6570\u6765\u6e90\u7c7b\u578b\uff1a" + model.getDatasource().getType() + "[" + model.getName() + "]");
            }
            IParameterDependAnalyzer analyzer = factory.createDependAnalyzer();
            if (analyzer == null) continue;
            DGNode target = (DGNode)map.get(paramName);
            List<ParameterDependMember> depends = analyzer.findDepends(this, model);
            if (depends != null && !depends.isEmpty()) {
                this.autoDependsMap.put(paramName, depends);
                for (ParameterDependMember depend : depends) {
                    DGNode initial = (DGNode)map.get(depend.getParameterName());
                    if (initial == null) continue;
                    this.addGraphLink(graph, (DGNode<ParameterModel>)initial, (DGNode<ParameterModel>)target);
                }
            }
            if ((affects = analyzer.findAffects(this, model)) == null || affects.isEmpty()) continue;
            for (String affect : affects) {
                DGNode initial = (DGNode)map.get(affect);
                if (initial == null) continue;
                this.addGraphLink(graph, (DGNode<ParameterModel>)target, (DGNode<ParameterModel>)initial);
            }
        }
        return graph;
    }

    private boolean addGraphLink(Digraph<ParameterModel> graph, DGNode<ParameterModel> initial, DGNode<ParameterModel> target) {
        List links = graph.getLinks();
        for (DGLink link : links) {
            if (link.getInitial() != initial || link.getTerminal() != target) continue;
            return false;
        }
        graph.link(initial, target);
        return true;
    }

    public ParameterCalculator clone() {
        ParameterCalculator calculator = new ParameterCalculator(this.userId, this.parameterModels);
        calculator.language = this.language;
        calculator.sessionId = this.sessionId;
        for (Map.Entry<String, AbstractParameterValue> entry : this.values.entrySet()) {
            calculator.values.put(entry.getKey(), entry.getValue().clone());
        }
        for (Map.Entry<String, Object> entry : this.autoDependsMap.entrySet()) {
            calculator.autoDependsMap.put(entry.getKey(), new ArrayList((Collection)entry.getValue()));
        }
        for (Map.Entry<String, Object> entry : this.paramUnittreeMap.entrySet()) {
            calculator.paramUnittreeMap.put(entry.getKey(), (String)entry.getValue());
        }
        for (Map.Entry<String, Object> entry : this.extras.entrySet()) {
            calculator.extras.put(entry.getKey(), (String)entry.getValue());
        }
        return calculator;
    }
}

