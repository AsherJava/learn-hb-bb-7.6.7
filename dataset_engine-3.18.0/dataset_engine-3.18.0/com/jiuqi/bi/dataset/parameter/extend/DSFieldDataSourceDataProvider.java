/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dataset.DSFieldNode
 *  com.jiuqi.bi.syntax.dynamic.BaseDynamicNodeProvider
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.parameter.extend.BIDataRowObjectVistor;
import com.jiuqi.bi.dataset.parameter.extend.DSFieldDataSourceModel;
import com.jiuqi.bi.dataset.parameter.extend.DSHierarchyDataSourceModel;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.dynamic.BaseDynamicNodeProvider;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DSFieldDataSourceDataProvider
implements IParameterDataSourceProvider {
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        AbstractParameterValue defVal = cfg.getDefaultValue();
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)context.getModel().getDatasource();
        IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
        String defValMode = cfg.getDefaultValueMode();
        if (defValMode.equals("appoint")) {
            if (defVal == null || defVal.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            try {
                BIDataSet dataSet = this.filterCandidateValue(context, null);
                List defVals = cfg.getDefaultValue().toValueList(valueFormat);
                String dsFieldName = datasource.getDsFieldName();
                Column column = dataSet.getMetadata().find(dsFieldName);
                String field = ((BIDataSetFieldInfo)column.getInfo()).getKeyField();
                dataSet = dataSet.filter(new ArrayList<FilterItem>(Collections.singletonList(new FilterItem(field, defVals))));
                ParameterResultset result = this.convertResultset(datasource, dataSet, context);
                result.sortByKeysOrder(defVals);
                return result;
            }
            catch (Exception e) {
                throw new ParameterException(e.getMessage(), (Throwable)e);
            }
        }
        if (defValMode.equals("first")) {
            ParameterResultset result = this.getCandidateValue(context, new ParameterHierarchyFilterItem(null));
            if (result == null || result.isEmpty()) {
                return result;
            }
            if (context.getModel().isOrderReverse()) {
                return new ParameterResultset(result.get(result.size() - 1));
            }
            return new ParameterResultset(result.get(0));
        }
        if (defValMode.equals("expr")) {
            String formula = (String)cfg.getDefaultValue().getValue();
            if (formula == null || formula.isEmpty()) {
                return ParameterResultset.EMPTY_RESULTSET;
            }
            try {
                BIDataSet dataSet = this.filterCandidateValue(context, null);
                DatasetFormulaParser parser = DSFormularManager.getInstance().createParser((BIDataSetImpl)dataSet);
                DSFormulaContext dsCxt = new DSFormulaContext((BIDataSetImpl)dataSet, null);
                DSExpression expr = parser.parseEval(formula, dsCxt);
                if (expr.getType(dsCxt) != 1) {
                    Object v = expr.evaluate(dsCxt);
                    ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
                    ArrayList<Object> vlist = new ArrayList<Object>();
                    vlist.add(v);
                    filterItems.add(new FilterItem(datasource.getDsFieldName(), vlist));
                    dataSet = dataSet.filter(filterItems);
                } else {
                    dataSet = dataSet.filter(formula);
                }
                return this.convertResultset(datasource, dataSet, context);
            }
            catch (Exception e) {
                throw new ParameterException(e.getMessage(), (Throwable)e);
            }
        }
        return ParameterResultset.EMPTY_RESULTSET;
    }

    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)context.getModel().getDatasource();
        try {
            BIDataSet dataSet = this.filterCandidateValue(context, hierarchyFilter);
            ParameterResultset result = this.convertResultset(datasource, dataSet, context);
            AbstractParameterValueConfig valueConfig = context.getModel().getValueConfig();
            if (result.size() > 1 && valueConfig.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
                result.sortByKeysOrder(valueConfig.getCandidateValue());
            }
            return result;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private BIDataSet filterCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws Exception {
        AbstractParameterValueConfig cfg = context.getModel().getValueConfig();
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)context.getModel().getDatasource();
        IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
        ParameterCandidateValueMode candidateMode = cfg.getCandidateMode();
        ParameterEnv env = new ParameterEnv(context.getUserId(), context.getCalculator().getParameterModels());
        List<FilterItem> filters = this.buildDependParameterFilter(context, (IParameterEnv)env);
        DSContext dsCxt = new DSContext(null, context.getUserId(), (IParameterEnv)env);
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        BIDataSet dataSet = dataSetManager.open(dsCxt, datasource.getDsName(), datasource.getDsType());
        if (candidateMode == ParameterCandidateValueMode.APPOINT) {
            List candidateValues = cfg.getCandidateValue();
            ArrayList<Object> realVals = new ArrayList<Object>();
            for (String s : candidateValues) {
                realVals.add(valueFormat.parse(s));
            }
            String dsFieldName = datasource.getDsFieldName();
            Column column = dataSet.getMetadata().find(dsFieldName);
            String field = ((BIDataSetFieldInfo)column.getInfo()).getKeyField();
            filters.add(new FilterItem(field, realVals));
        } else if (candidateMode == ParameterCandidateValueMode.EXPRESSION) {
            String expr = (String)cfg.getCandidateValue().get(0);
            FormulaParser parser = FormulaParser.getInstance();
            final Metadata<BIDataSetFieldInfo> metadata = dataSet.getMetadata();
            parser.registerDynamicNodeProvider((IDynamicNodeProvider)new BaseDynamicNodeProvider(){

                public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
                    Column column = metadata.find(refName);
                    if (column != null) {
                        return new DSFieldNode(token, column);
                    }
                    return null;
                }
            });
            boolean containField = false;
            IExpression expression = parser.parseEval(expr, null);
            for (IASTNode node : expression) {
                if (!(node instanceof DSFieldNode)) continue;
                containField = true;
                break;
            }
            if (containField) {
                filters.add(new FilterItem(datasource.getDsFieldName(), expr));
            } else {
                ArrayList<Object> keys = new ArrayList<Object>();
                keys.add(expression.evaluate(null));
                filters.add(new FilterItem(datasource.getDsFieldName(), keys));
            }
        }
        if (!filters.isEmpty()) {
            dataSet = dataSet.filter(filters);
        }
        if (hierarchyFilter != null) {
            ParameterDataSourceHierarchyType hierType = datasource.getHierarchyType();
            List<DSHierarchy> dsHiers = this.getDSHierarchy(dataSet);
            DSHierarchy dsHierarchy = this.findMatchedHierarchy(dsHiers, datasource);
            if (dsHierarchy != null) {
                ArrayList<Object> keys = new ArrayList<Object>();
                if (hierType == ParameterDataSourceHierarchyType.PARENTMODE || hierType == ParameterDataSourceHierarchyType.STRUCTURECODE) {
                    return this.filterCandidateValueParentSon(dataSet, datasource, dsHierarchy, hierarchyFilter);
                }
                if (hierType == ParameterDataSourceHierarchyType.NORMAL) {
                    filters.add(new FilterItem(dsHierarchy.getLevels().get(1), keys));
                } else {
                    throw new Exception("\u4e0d\u652f\u6301\u7f16\u7801\u5c42\u7ea7\u7684\u53c2\u6570\u6765\u6e90");
                }
            }
        }
        return dataSet;
    }

    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)context.getModel().getDatasource();
        IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
        try {
            BIDataSet dataset = this.filterCandidateValue(context, null);
            String dsFieldName = datasource.getDsFieldName();
            Column column = dataset.getMetadata().find(dsFieldName);
            String field = ((BIDataSetFieldInfo)column.getInfo()).getKeyField();
            dataset = dataset.filter(new ArrayList<FilterItem>(Collections.singletonList(new FilterItem(field, value.toValueList(valueFormat)))));
            return this.convertResultset(datasource, dataset, context);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)context.getModel().getDatasource();
        try {
            BIDataSet dataset = this.filterCandidateValue(context, null);
            ParameterResultset result = this.convertResultset(datasource, dataset, context);
            ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
            for (ParameterResultItem item : result) {
                String key = item.getValue().toString().toUpperCase();
                String title = item.getTitle();
                boolean matched = true;
                for (String sv : searchValues) {
                    if (key.contains(sv.toUpperCase()) || title.contains(sv.toUpperCase())) continue;
                    matched = false;
                    break;
                }
                if (!matched) continue;
                list.add(item);
            }
            return new ParameterResultset(list);
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        DSModel dsModel;
        DSFieldDataSourceModel datasource = (DSFieldDataSourceModel)datasourceModel;
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        try {
            dsModel = dataSetManager.findModel(datasource.getDsName(), datasource.getDsType());
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
        List<DSField> fields = dsModel.getFields();
        ArrayList<DataSourceCandidateFieldInfo> infos = new ArrayList<DataSourceCandidateFieldInfo>();
        for (DSField f : fields) {
            if (!f.isDimention()) continue;
            DataSourceCandidateFieldInfo field = new DataSourceCandidateFieldInfo(f.getName(), f.getTitle());
            infos.add(field);
        }
        return infos;
    }

    private ParameterResultset convertResultset(DSFieldDataSourceModel datasource, BIDataSet dataset, ParameterDataSourceContext context) throws Exception {
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        DSField dsField = dataSetManager.findField(datasource.getDsName(), datasource.getDsType(), datasource.getDsFieldName());
        int keyIdx = dataset.getMetadata().indexOf(dsField.getKeyField());
        int nameIdx = dataset.getMetadata().indexOf(dsField.getNameField());
        if (nameIdx == -1) {
            nameIdx = keyIdx;
        }
        int parentIdx = -1;
        if (datasource.getHierarchyType() == ParameterDataSourceHierarchyType.PARENTMODE && dataset.getMetadata().size() >= 3) {
            parentIdx = dataset.getMetadata().indexOf("parent");
        }
        int treeLeafIdx = dataset.getMetadata().indexOf("treeLeaf");
        IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
        Format dataShowFormat = null;
        if (valueFormat != null && StringUtils.isNotEmpty((String)context.getCalculator().getLanguage())) {
            dataShowFormat = valueFormat.getDataShowFormat(new Locale(context.getCalculator().getLanguage()));
        }
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        int size = dataset.getRecordCount();
        HashSet<Object> set = new HashSet<Object>();
        for (int i = 0; i < size; ++i) {
            BIDataRow row = dataset.get(i);
            if (set.contains(row.getValue(keyIdx))) continue;
            ParameterResultItem item = new ParameterResultItem();
            item.setValue(row.getValue(keyIdx));
            String title = row.getString(nameIdx);
            item.setTitle(dataShowFormat != null ? dataShowFormat.format(title) : title);
            set.add(item.getValue());
            if (parentIdx >= 0) {
                item.setParent(row.getString(parentIdx));
            }
            if (treeLeafIdx >= 0) {
                item.setLeaf(row.getInt(treeLeafIdx) == 1);
            }
            items.add(item);
        }
        return new ParameterResultset(items);
    }

    private List<DSHierarchy> getDSHierarchy(BIDataSet dataSet) {
        return (List)dataSet.getMetadata().getProperties().get("HIERARCHY");
    }

    private DSHierarchy findMatchedHierarchy(List<DSHierarchy> dsHiers, DSFieldDataSourceModel datasource) {
        ParameterDataSourceHierarchyType hierType = datasource.getHierarchyType();
        if (dsHiers != null) {
            for (DSHierarchy hier : dsHiers) {
                if (hier.getType().value() != hierType.value() || !hier.getLevels().get(0).equalsIgnoreCase(datasource.getDsFieldName())) continue;
                return hier;
            }
        }
        return null;
    }

    private List<FilterItem> buildDependParameterFilter(ParameterDataSourceContext context, IParameterEnv env) throws Exception {
        ParameterCalculator calculator = context.getCalculator();
        List depends = calculator.getDependParameters(context.getModel().getName());
        if (depends.isEmpty()) {
            return new ArrayList<FilterItem>();
        }
        ArrayList<FilterItem> filters = new ArrayList<FilterItem>();
        for (ParameterDependMember depend : depends) {
            ParameterModel dependParam = calculator.getParameterModelByName(depend.getParameterName());
            AbstractParameterDataSourceModel datasource = dependParam.getDatasource();
            String fieldName = depend.getDatasourceFieldName();
            if (StringUtils.isEmpty((String)fieldName)) {
                IDataSetManager dataSetManager;
                if (datasource.getType().equals("com.jiuqi.bi.datasource.dsfield")) {
                    DSFieldDataSourceModel dds = (DSFieldDataSourceModel)datasource;
                    dataSetManager = DataSetManagerFactory.create();
                    DSField dsField = dataSetManager.findField(dds.getDsName(), dds.getDsType(), dds.getDsFieldName());
                    if (dsField == null) continue;
                    fieldName = StringUtils.isEmpty((String)dsField.getKeyField()) ? dsField.getName() : dsField.getKeyField();
                } else if (datasource.getType().equals("com.jiuqi.bi.datasource.dshier")) {
                    DSHierarchyDataSourceModel hds = (DSHierarchyDataSourceModel)datasource;
                    dataSetManager = DataSetManagerFactory.create();
                    DSHierarchy dsHier = dataSetManager.findHierarchy(hds.getDsName(), hds.getDsType(), hds.getDsHierarchyName());
                    String dsFieldName = dsHier.getLevels().get(0);
                    DSField dsField = dataSetManager.findField(hds.getDsName(), hds.getDsType(), dsFieldName);
                    if (dsField == null) continue;
                    String string = fieldName = StringUtils.isEmpty((String)dsField.getKeyField()) ? dsField.getName() : dsField.getKeyField();
                }
            }
            if (!StringUtils.isNotEmpty((String)fieldName)) continue;
            ParameterResultset val = calculator.getValue(depend.getParameterName());
            env.setValue(depend.getParameterName(), val.getValueAsList());
            FilterItem filterItem = dependParam.isRangeParameter() ? new FilterItem(fieldName, new RangeValues(val.get(0).getValue(), val.get(1).getValue())) : new FilterItem(fieldName, val.getValueAsList());
            filters.add(filterItem);
        }
        return filters;
    }

    public static String[] getDsFieldKeyAndNameCol(DSField dsField) {
        String keyCol = null;
        String nameCol = null;
        FieldType fieldType = dsField.getFieldType();
        if (fieldType.equals((Object)FieldType.DESCRIPTION) || fieldType.equals((Object)FieldType.MEASURE)) {
            keyCol = dsField.getName();
            nameCol = dsField.getName();
        } else {
            keyCol = dsField.getKeyField();
            nameCol = dsField.getNameField();
        }
        return new String[]{keyCol, nameCol};
    }

    private BIDataSet filterCandidateValueParentSon(BIDataSet dataSet, DSFieldDataSourceModel datasource, DSHierarchy dsHierarchy, ParameterHierarchyFilterItem hierarchyFilter) throws Exception {
        if (hierarchyFilter.isAllSub() && StringUtils.isEmpty((String)hierarchyFilter.getParent())) {
            return dataSet;
        }
        dataSet = this.dataSetDistinct(dataSet, datasource, dsHierarchy);
        TreeNode rootNode = this.getRootNode(dataSet, dsHierarchy);
        ArrayList<TreeNode> parentNodes = new ArrayList<TreeNode>();
        if (StringUtils.isEmpty((String)hierarchyFilter.getParent())) {
            parentNodes.add(rootNode);
        } else {
            this.getChildren(rootNode, hierarchyFilter.getParent(), parentNodes);
        }
        MemoryDataSet<BIDataSetFieldInfo> resultDataSet = this.buildBIDataSet(dataSet);
        if (!parentNodes.isEmpty()) {
            ArrayList<TreeNode> result = new ArrayList<TreeNode>();
            if (hierarchyFilter.isAllSub()) {
                this.fillAllSub((TreeNode)parentNodes.get(0), result);
            } else {
                result.addAll(Arrays.asList(((TreeNode)parentNodes.get(0)).getChildren()));
            }
            int treeLeafIndex = resultDataSet.getMetadata().indexOf("treeLeaf");
            for (TreeNode node : result) {
                BIDataRow dataRow = (BIDataRow)node.getItem();
                dataRow.getBuffer()[treeLeafIndex] = node.getChildren().length == 0 ? 1 : 0;
                resultDataSet.add(dataRow.getBuffer());
            }
        }
        return new BIDataSetImpl(resultDataSet);
    }

    private BIDataSet dataSetDistinct(BIDataSet dataSet, DSFieldDataSourceModel datasource, DSHierarchy dsHierarchy) throws BIDataSetException {
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        DSField dsField = dataSetManager.findField(datasource.getDsName(), datasource.getDsType(), dsHierarchy.getLevels().get(0));
        String[] keyAndNameCol = DSFieldDataSourceDataProvider.getDsFieldKeyAndNameCol(dsField);
        String keyCol = keyAndNameCol[0];
        String nameCol = keyAndNameCol[1];
        ArrayList<String> clos = new ArrayList<String>();
        clos.add(keyCol);
        clos.add(nameCol);
        if (dsHierarchy.getType() == DSHierarchyType.PARENT_HIERARCHY) {
            clos.add(dsHierarchy.getParentFieldName());
        }
        dataSet = dataSet.distinct(clos);
        dataSet.getMetadata().addColumn(new Column("treeLeaf", 3));
        return dataSet;
    }

    private TreeNode getRootNode(BIDataSet dataSet, DSHierarchy dsHierarchy) throws TreeException {
        TreeBuilder treeBuilder = null;
        treeBuilder = dsHierarchy.getType() == DSHierarchyType.PARENT_HIERARCHY ? TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new BIDataRowObjectVistor(dataSet.getMetadata().indexOf(dsHierarchy.getLevels().get(0)), dataSet.getMetadata().indexOf(dsHierarchy.getParentFieldName()))) : TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new BIDataRowObjectVistor(), (String)dsHierarchy.getCodePattern());
        treeBuilder.setSortMode(1);
        return treeBuilder.build(dataSet.iterator());
    }

    private void fillAllSub(TreeNode treeNode, List<TreeNode> children) {
        for (TreeNode node : treeNode.getChildren()) {
            children.add(node);
            this.fillAllSub(node, children);
        }
    }

    private void getChildren(TreeNode treeNode, String parent, List<TreeNode> parentNodes) {
        BIDataRow dataRow;
        if (treeNode.getItem() != null && parent.equals((dataRow = (BIDataRow)treeNode.getItem()).getString(0))) {
            parentNodes.add(treeNode);
            return;
        }
        for (TreeNode node : treeNode.getChildren()) {
            this.getChildren(node, parent, parentNodes);
        }
    }

    private MemoryDataSet<BIDataSetFieldInfo> buildBIDataSet(BIDataSet dataSet) {
        MemoryDataSet memoryDataSet = new MemoryDataSet(dataSet.getMetadata());
        return memoryDataSet;
    }
}

