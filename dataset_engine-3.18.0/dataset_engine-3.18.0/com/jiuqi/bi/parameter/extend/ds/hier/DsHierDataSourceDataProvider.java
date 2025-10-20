/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeNode
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.extend.ds.hier;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.DataSetManager;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterDataRowIterator;
import com.jiuqi.bi.parameter.ParameterObjectVistor;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.ds.hier.DSHierarchyDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.hier.DsHierQueryBuilder;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProviderEx;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DsHierDataSourceDataProvider
implements IDataSourceDataProviderEx {
    private DSHierarchyDataSourceModel dataSourceModel;
    private DsHierQueryBuilder queryBuilder;
    private TreeNode rootNode;
    private Map<String, DSField> fieldNameAndFieldModelMap = new HashMap<String, DSField>();
    private DataSourceMetaInfo metaInfo;

    public DsHierDataSourceDataProvider(DataSourceModel dataSourceModel) {
        this.dataSourceModel = (DSHierarchyDataSourceModel)dataSourceModel;
        this.queryBuilder = new DsHierQueryBuilder();
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.getAllValues("", env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            if (dataSourceValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (dataSourceValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)dataSourceValues, this.getDataSourceMetaInfo());
                MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList(dataSet, env);
                MemoryDataSet<ParameterColumnInfo> parentDataSet = this.buildTreeDataSet(resultDataSet, DataSetManagerUtils.createDataSetManager());
                return DataSourceUtils.sortByAppointValues((List)dataSourceValues, parentDataSet, this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> dataSourceDataSet = DataSourceUtils.getDataSourceValuesDataSet((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), this.getDataSourceMetaInfo());
            MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList(dataSourceDataSet, env);
            return DataSourceUtils.sortByAppointValues(dataSourceDataSet, resultDataSet, this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            return this.filterChoiceValuesForExpr(parameterModel, env, true);
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> getAllValues(ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItemsForAllValues(env, this.dataSourceModel, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
            BIDataSet resultDataSet = dataSet.filter(filterItems);
            resultDataSet = this.distinctBIDataSet(resultDataSet);
            return DataSourceUtils.biDataSet2ParameterMemoryDataSet(resultDataSet);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        ParameterDefaultValueFilterMode defaultValueFilterMode = parameterModel.getDefaultValueFilterMode();
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.APPOINT)) {
            Object defaultValues = parameterModel.getDefaultValues();
            if (defaultValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (defaultValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)defaultValues, this.getDataSourceMetaInfo());
                MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(this.filterValuesForList(dataSet, env), this.filterAllChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
                return DataSourceUtils.sortByAppointValues((List)defaultValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> defaultValuesDataSet = DataSourceUtils.getDataSourceValuesDataSet((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)defaultValues), this.getDataSourceMetaInfo());
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(this.filterValuesForList(defaultValuesDataSet, env), this.filterAllChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
            return DataSourceUtils.sortByAppointValues(defaultValuesDataSet, resultDataSet, this.getDataSourceMetaInfo());
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            MemoryDataSet<ParameterColumnInfo> dataSet = this.filterAllChoiceValues(parameterModel, env);
            dataSet = this.buildTreeDataSet(dataSet, DataSetManagerUtils.createDataSetManager());
            return DataSourceUtils.getFirstValue(ParameterUtils.reverseDataSet(parameterModel, dataSet));
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.EXPRESSION)) {
            String expression = parameterModel.getDefalutValueFilter();
            if (StringUtils.isEmpty((String)expression)) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            return this.filterDefaultValuesForExpr(expression, parameterModel, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.NONE)) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesForList(MemoryDataSet<ParameterColumnInfo> values, ParameterEngineEnv env) throws DataSourceException {
        if (values == null || values.size() == 0) {
            return new MemoryDataSet();
        }
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, values, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
            return DataSourceUtils.biDataSet2ParameterMemoryDataSet(this.distinctBIDataSet(dataSet.filter(filterItems)));
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterDefaultValuesForExpr(String expression, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (StringUtils.isNotEmpty((String)expression)) {
                FormulaParser parser = FormulaParser.getInstance();
                IContext context = new IContext(){};
                IExpression expressionNode = parser.parseEval(expression, context);
                String value = expressionNode.evaluate(context).toString();
                value = this.formatOrgValue(value, this.getDataSourceMetaInfo().getAttrBeans().get(0)).toString();
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
                DataRow row = dataSet.add();
                row.setValue(0, (Object)value);
                String nameValue = this.getNameColValue(value, parameterModel, env);
                if (StringUtils.isEmpty((String)nameValue)) {
                    dataSet.clear();
                    return dataSet;
                }
                if (dataSet.getMetadata().size() > 1) {
                    row.setValue(1, (Object)nameValue);
                }
                row.commit();
                return dataSet;
            }
        }
        catch (Exception e) {
            return this.filterDefalutValuesForExpr(expression, env);
        }
        return null;
    }

    private String getNameColValue(String keyValue, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsHierName = this.dataSourceModel.getDsHierarchyName();
            DSHierarchy dsHierarchy = dataSetManager.findHierarchy(dsName, this.dataSourceModel.getDsType(), dsHierName);
            String dsFieldName = dsHierarchy.getLevels().get(0);
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            String keyField = dsField.getKeyField();
            String nameField = dsField.getNameField();
            if (keyField.equals(nameField)) {
                return keyValue;
            }
            if (keyField.equals(dsFieldName)) {
                return this.getNameColValueFromDataSet(keyValue, keyField, nameField, this.filterAllChoiceValues(parameterModel, env));
            }
            return keyValue;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private String getNameColValueFromDataSet(String keyValue, String keyField, String nameField, MemoryDataSet<ParameterColumnInfo> allValues) {
        Iterator it = allValues.iterator();
        int keyIndex = allValues.getMetadata().indexOf(keyField);
        int nameIndex = allValues.getMetadata().indexOf(nameField);
        while (it.hasNext()) {
            DataRow row = (DataRow)it.next();
            for (int i = 0; i < allValues.getMetadata().size(); ++i) {
                if (!row.getString(keyIndex).equals(keyValue)) continue;
                return row.getString(nameIndex);
            }
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterDefalutValuesForExpr(String expression, ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            MemoryDataSet<ParameterColumnInfo> choiceValues = this.filterAllChoiceValues(parameterModel, env);
            if (choiceValues.size() == 0) {
                return choiceValues;
            }
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, choiceValues, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
            BIDataSet biDataSet = dataSet.filter(expression);
            biDataSet = biDataSet.filter(filterItems);
            biDataSet = this.distinctBIDataSet(biDataSet);
            return DataSourceUtils.removeDataRowsNotInTargetDataSet(DataSourceUtils.biDataSet2ParameterMemoryDataSet(biDataSet), choiceValues, this.getDataSourceMetaInfo());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesForExpr(String expression, ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            ArrayList<FilterItem> cascadeFilterItems = new ArrayList<FilterItem>();
            this.queryBuilder.buildCascadeQueryFilters(env, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env), cascadeFilterItems, this.dataSourceModel);
            BIDataSet resultDataSet = dataSet.filter(cascadeFilterItems).filter(expression);
            resultDataSet = this.distinctBIDataSet(resultDataSet);
            return DataSourceUtils.biDataSet2ParameterMemoryDataSet(resultDataSet);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public void init(ParameterEngineEnv env) throws DataSourceException {
        try {
            MemoryDataSet<ParameterColumnInfo> allValues;
            if (env.getQueryProperty("initAllValues") != null) {
                Object epxr = env.getQueryProperty("initAllValuesWithExpr");
                allValues = epxr == null ? this.getAllValues(env) : this.getAllValuesWithoutTree(epxr.toString(), env);
            } else {
                ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
                allValues = this.filterAllChoiceValues(parameterModel, env);
            }
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.STRUCTURECODE)) {
                IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
                DSHierarchy dsHier = dataSetManager.findHierarchy(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), this.dataSourceModel.getDsHierarchyName());
                TreeBuilder treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new ParameterObjectVistor(), (String)dsHier.getCodePattern());
                treeBuilder.setSortMode(1);
                this.rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
            } else if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
                TreeBuilder treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ParameterObjectVistor());
                treeBuilder.setSortMode(1);
                this.rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
            }
        }
        catch (Exception e) {
            throw new DataSourceException("\u521d\u59cb\u5316\u53c2\u6570\u7f13\u5b58\u4fe1\u606f\u51fa\u9519\uff0c" + e.getMessage(), e.getCause());
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> values, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        if (values == null || values.size() == 0) {
            return new MemoryDataSet();
        }
        try {
            MemoryDataSet<ParameterColumnInfo> resultDataSet;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            String searchExpr = this.queryBuilder.buildSearchFilterExpr(this.dataSourceModel, this.formatSearchFilterValues(values), env);
            MemoryDataSet<ParameterColumnInfo> choiceValues = this.filterAllChoiceValues(parameterModel, env);
            if (choiceValues.size() == 0) {
                return choiceValues;
            }
            if (isSearchFromChoiceValues) {
                List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, choiceValues, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                BIDataSet tempResultDataSet = dataSet.filter(filterItems).filter(searchExpr);
                tempResultDataSet = this.distinctBIDataSet(tempResultDataSet);
                resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(tempResultDataSet);
            } else {
                ArrayList<FilterItem> cascadeFilterItems = new ArrayList<FilterItem>();
                this.queryBuilder.buildCascadeQueryFilters(env, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env), cascadeFilterItems, this.dataSourceModel);
                BIDataSet tempResultDataSet = dataSet.filter(cascadeFilterItems).filter(searchExpr);
                tempResultDataSet = this.distinctBIDataSet(tempResultDataSet);
                resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(tempResultDataSet);
            }
            if (isSearchFromChoiceValues) {
                return DataSourceUtils.sortByAppointValues(choiceValues, this.buildSearchMemoryDataSet(resultDataSet), this.getDataSourceMetaInfo());
            }
            return this.buildSearchMemoryDataSet(resultDataSet);
        }
        catch (Exception e) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
    }

    private List<Object> formatSearchFilterValues(List<String> fiterValues) throws DataSourceException {
        try {
            ArrayList<Object> filterObjValues = new ArrayList<Object>();
            for (String filterValue : fiterValues) {
                filterObjValues.add(this.formatOrgValue(filterValue, this.getDataSourceMetaInfo().getAttrBeans().get(0)));
            }
            return filterObjValues;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private Object formatOrgValue(String filterValue, DataSourceAttrBean dataSourceAttrBean) throws BIDataSetNotFoundException, BIDataSetException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsFieldName = dataSourceAttrBean.getCurrAttrName();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            return DataSourceUtils.format(filterValue, DataType.valueOf(dsField.getValType()));
        }
        catch (Exception e) {
            return filterValue;
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            return this.buildTreeDataSet(this.getAllValuesWithoutTree(filterExpr, env), dataSetManager);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> getAllValuesWithoutTree(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        try {
            BIDataSet resultDataSet;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            if (StringUtils.isNotEmpty((String)filterExpr)) {
                resultDataSet = dataSet.filter(filterExpr);
                resultDataSet = this.distinctBIDataSet(resultDataSet);
            } else {
                List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                resultDataSet = dataSet.filter(filterItems);
                resultDataSet = this.distinctBIDataSet(resultDataSet);
            }
            return DataSourceUtils.biDataSet2ParameterMemoryDataSet(resultDataSet);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> buildTreeDataSet(MemoryDataSet<ParameterColumnInfo> allValues, IDataSetManager dataSetManager) throws DataSourceException {
        try {
            DSHierarchy dsHier = dataSetManager.findHierarchy(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), this.dataSourceModel.getDsHierarchyName());
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.NORMAL)) {
                return allValues;
            }
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
                String parentFieldName = dsHier.getParentFieldName();
                return DataSourceUtils.buildParameter_SonDataSet(allValues, parentFieldName, this.getDataSourceMetaInfo().getAttrBeans().get(0));
            }
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.STRUCTURECODE)) {
                return DataSourceUtils.buildStructureCodeDataSet(allValues, dsHier.getCodePattern(), false);
            }
            return allValues;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u5efa\u7236\u5b50\u5c42\u7ea7\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException {
        try {
            if (this.metaInfo != null) {
                return this.metaInfo;
            }
            this.metaInfo = new DataSourceMetaInfo();
            this.metaInfo.setLevelDepth(this.getLevelDepth());
            this.metaInfo.getAttrBeans().addAll(this.getAttrBeans(this.metaInfo));
            this.metaInfo.setTitle(this.getTitle());
            return this.metaInfo;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private String getTitle() throws DataSourceException {
        try {
            String dsName = this.dataSourceModel.getDsName();
            String dsHierName = this.dataSourceModel.getDsHierarchyName();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSHierarchy dsHier = dataSetManager.findHierarchy(dsName, this.dataSourceModel.getDsType(), dsHierName);
            return dsHier.getTitle();
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private List<DataSourceAttrBean> getAttrBeans(DataSourceMetaInfo metaInfo) throws BIDataSetNotFoundException, BIDataSetException {
        String dsName = this.dataSourceModel.getDsName();
        String dsHierName = this.dataSourceModel.getDsHierarchyName();
        IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
        DSHierarchy dsHier = dataSetManager.findHierarchy(dsName, this.dataSourceModel.getDsType(), dsHierName);
        List<String> levels = dsHier.getLevels();
        ArrayList<DataSourceAttrBean> attrBeans = new ArrayList<DataSourceAttrBean>();
        if (dsHier.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY) || dsHier.getType().equals((Object)DSHierarchyType.CODE_HIERARCHY)) {
            metaInfo.setParentSonMode(true);
            return this.getParameter_SonHierAttrBeans(dsName, dataSetManager, levels, attrBeans);
        }
        return this.getNormalHierAttrBeans(dsName, dataSetManager, levels, attrBeans);
    }

    private List<DataSourceAttrBean> getNormalHierAttrBeans(String dsName, IDataSetManager dataSetManager, List<String> levels, List<DataSourceAttrBean> attrBeans) throws BIDataSetNotFoundException, BIDataSetException {
        for (String dsFieldName : levels) {
            DataSourceAttrBean bean = new DataSourceAttrBean();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            String keyCol = dsField.getKeyField();
            String nameCol = dsField.getNameField();
            bean.setCurrAttrName(dsFieldName);
            bean.setKeyColName(keyCol);
            bean.setNameColName(nameCol);
            bean.setTitle(dsField.getTitle());
            attrBeans.add(bean);
        }
        return attrBeans;
    }

    private List<DataSourceAttrBean> getParameter_SonHierAttrBeans(String dsName, IDataSetManager dataSetManager, List<String> levels, List<DataSourceAttrBean> attrBeans) throws BIDataSetNotFoundException, BIDataSetException {
        String dsFieldName = levels.get(0);
        DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
        String keyCol = dsField.getKeyField();
        String nameCol = dsField.getNameField();
        DSField nameField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), nameCol);
        DataSourceAttrBean keyBean = new DataSourceAttrBean();
        keyBean.setCurrAttrName(keyCol);
        keyBean.setKeyColName(keyCol);
        keyBean.setNameColName(nameCol);
        keyBean.setTitle(nameField.getTitle());
        attrBeans.add(keyBean);
        return attrBeans;
    }

    private int getLevelDepth() throws BIDataSetNotFoundException, BIDataSetException {
        String dsName = this.dataSourceModel.getDsName();
        String dsHierName = this.dataSourceModel.getDsHierarchyName();
        IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
        DSHierarchy dsHier = dataSetManager.findHierarchy(dsName, this.dataSourceModel.getDsType(), dsHierName);
        List<String> levels = dsHier.getLevels();
        if (dsHier.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
            return 3;
        }
        if (dsHier.getType().equals((Object)DSHierarchyType.CODE_HIERARCHY)) {
            return 3;
        }
        return levels.size();
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, boolean isAllSubLevel, String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        Map<String, String> parentValueMap = DataSourceUtils.parseParentValue(parentValue, this.dataSourceModel.getHireachyType());
        parentValue = parentValueMap.get(this.getDataSourceMetaInfo().getAttrBeans().get(0).getKeyColName());
        ParameterHierarchyType hierarchyType = this.dataSourceModel.getHireachyType();
        ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
        try {
            if (hierarchyType.equals((Object)ParameterHierarchyType.STRUCTURECODE) || hierarchyType.equals((Object)ParameterHierarchyType.PARENT_SON)) {
                return this.filterTreeChildrenValue(parentValue, level, isAllSubLevel, env, parameterModel);
            }
            return this.filterNormalHierChidrenValue(parentValue, level, isAllSubLevel, filterExpr, env, parentValueMap, hierarchyType, parameterModel);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterNormalHierChidrenValue(String parentValue, int level, boolean isAllSubLevel, String filterExpr, ParameterEngineEnv env, Map<String, String> parentValueMap, ParameterHierarchyType hierarchyType, ParameterModel parameterModel) throws DataSourceException {
        try {
            BIDataSet resultDataSet;
            List<FilterItem> filterItems;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            if (StringUtils.isNotEmpty((String)filterExpr)) {
                filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, parentValueMap, level, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                resultDataSet = dataSet.filter(filterExpr).filter(filterItems);
                resultDataSet = this.distinctNormalChildrenBIDataSet(resultDataSet, level);
            } else {
                filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, parentValueMap, level, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                resultDataSet = dataSet.filter(filterItems);
                resultDataSet = this.distinctNormalChildrenBIDataSet(resultDataSet, level);
            }
            MemoryDataSet<ParameterColumnInfo> parameterDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(resultDataSet);
            return DataSourceUtils.removeDataRowsNotInTargetDataSet(parameterDataSet, this.filterAllChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterTreeChildrenValue(String parentValue, int level, boolean isAllSubLevel, ParameterEngineEnv env, ParameterModel parameterModel) throws DataSourceException {
        if (isAllSubLevel) {
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getAllSubLevelData(this.rootNode, parentValue, level, this.getDataSourceMetaInfo());
            return DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.filterAllChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
        }
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getDirectSubLevelData(this.rootNode, parentValue, level, this.getDataSourceMetaInfo());
        return DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.filterAllChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, String filterExpr, ParameterEngineEnv env, boolean isAllSubLevel) throws DataSourceException {
        try {
            BIDataSet resultDataSet;
            List<FilterItem> filterItems;
            Map<String, String> parentValueMap = DataSourceUtils.parseParentValue(parentValue, this.dataSourceModel.getHireachyType());
            ParameterHierarchyType hierarchyType = this.dataSourceModel.getHireachyType();
            if (hierarchyType.equals((Object)ParameterHierarchyType.STRUCTURECODE) || hierarchyType.equals((Object)ParameterHierarchyType.PARENT_SON)) {
                parentValue = parentValueMap.get(this.getDataSourceMetaInfo().getAttrBeans().get(0).getKeyColName());
                MemoryDataSet<ParameterColumnInfo> resultDataSet2 = DataSourceUtils.getDirectSubLevelData(this.rootNode, parentValue, level, this.getDataSourceMetaInfo());
                return resultDataSet2;
            }
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            if (StringUtils.isNotEmpty((String)filterExpr)) {
                filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, parentValueMap, level, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                resultDataSet = this.distinctBIDataSet(dataSet.filter(filterExpr));
                resultDataSet = resultDataSet.filter(filterItems);
                resultDataSet = this.distinctNormalChildrenBIDataSet(resultDataSet, level);
            } else {
                filterItems = this.queryBuilder.buildFilterItems(env, this.dataSourceModel, parentValueMap, level, ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env));
                resultDataSet = dataSet.filter(filterItems);
                resultDataSet = this.distinctNormalChildrenBIDataSet(resultDataSet, level);
            }
            MemoryDataSet<ParameterColumnInfo> parameterDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(resultDataSet);
            return parameterDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataSet = this.filterValuesForList((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)values), env);
        MemoryDataSet<ParameterColumnInfo> choiceValues = this.filterAllChoiceValues(parameterModel, env);
        return DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, choiceValues, this.getDataSourceMetaInfo());
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterAllChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.getAllValues(env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            if (dataSourceValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (dataSourceValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)dataSourceValues, this.getDataSourceMetaInfo());
                MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList(dataSet, env);
                return DataSourceUtils.sortByAppointValues((List)dataSourceValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> dataSourceDataSet = DataSourceUtils.getDataSourceValuesDataSet((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), this.getDataSourceMetaInfo());
            MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList(dataSourceDataSet, env);
            return DataSourceUtils.sortByAppointValues(dataSourceDataSet, resultDataSet, this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            return this.filterChoiceValuesForExpr(parameterModel, env, false);
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterChoiceValuesForExpr(ParameterModel parameterModel, ParameterEngineEnv env, boolean isNeedBuildParentTree) throws DataSourceException {
        String dataSourceFilter = parameterModel.getDataSourceFilter();
        if (StringUtils.isEmpty((String)dataSourceFilter)) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        MemoryDataSet<ParameterColumnInfo> dataSet = this.filterValuesForExpr(dataSourceFilter, env);
        if (isNeedBuildParentTree) {
            return this.buildTreeDataSet(dataSet, DataSetManagerUtils.createDataSetManager());
        }
        return dataSet;
    }

    private MemoryDataSet<ParameterColumnInfo> buildSearchMemoryDataSet(MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSourceException {
        try {
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
                dataSet.getMetadata().addColumn(new Column("treeLeaf", DataType.STRING.value()));
                int index = dataSet.getMetadata().indexOf("treeLeaf");
                for (DataRow dataRow : dataSet) {
                    dataRow.setInt(index, 1);
                    dataRow.commit();
                }
            } else {
                return dataSet;
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fuzzySearch(List<String> values, ParameterEngineEnv env) throws DataSourceException {
        return this.searchValues(values, true, env);
    }

    private BIDataSet distinctBIDataSet(BIDataSet resultDataSet) throws BIDataSetNotFoundException, BIDataSetException {
        IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
        String dsHierName = this.dataSourceModel.getDsHierarchyName();
        DSHierarchy dsHier = dataSetManager.findHierarchy(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), dsHierName);
        if (dsHier.getType().equals((Object)DSHierarchyType.PARENT_HIERARCHY)) {
            String fieldName = dsHier.getLevels().get(0);
            DSField dsField = dataSetManager.findField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), fieldName);
            ArrayList<String> cols = new ArrayList<String>();
            cols.add(dsField.getKeyField());
            cols.add(dsField.getNameField());
            cols.add(dsHier.getParentFieldName());
            BIDataSet distinctedDataSet = resultDataSet.distinct(cols);
            return distinctedDataSet;
        }
        if (dsHier.getType().equals((Object)DSHierarchyType.CODE_HIERARCHY)) {
            String fieldName = dsHier.getLevels().get(0);
            DSField dsField = dataSetManager.findField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), fieldName);
            ArrayList<String> cols = new ArrayList<String>();
            cols.add(dsField.getKeyField());
            cols.add(dsField.getNameField());
            BIDataSet distinctedDataSet = resultDataSet.distinct(cols);
            return distinctedDataSet;
        }
        return resultDataSet;
    }

    private BIDataSet distinctNormalChildrenBIDataSet(BIDataSet resultDataSet, int level) throws BIDataSetNotFoundException, BIDataSetException {
        IDataSetManager dataSetMgr = DataSetManagerUtils.createDataSetManager();
        DSHierarchy dsHier = dataSetMgr.findHierarchy(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), this.dataSourceModel.getDsHierarchyName());
        List<String> levels = dsHier.getLevels();
        if (level >= levels.size()) {
            throw new BIDataSetException("\u65e0\u6cd5\u67e5\u8be2\u5c42\u7ea7\u6570\u636e\uff0c\u5f53\u524d\u8bf7\u6c42\u5c42\u7ea7\u8d85\u8fc7\u53c2\u6570\u5b9a\u4e49\u7684\u603b\u5c42\u7ea7");
        }
        String currFieldName = levels.get(level);
        DSField dsField = dataSetMgr.findField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), currFieldName);
        ArrayList<String> cols = new ArrayList<String>();
        cols.add(dsField.getKeyField());
        cols.add(dsField.getNameField());
        return resultDataSet.distinct(cols);
    }

    @Override
    public String formatValue(String value, DataSourceAttrBean attrBean) throws DataSourceException {
        try {
            IDataSetManager dataSetMgr = DataSetManagerUtils.createDataSetManager();
            DSField filed = this.fieldNameAndFieldModelMap.get(attrBean.getCurrAttrName());
            if (filed == null) {
                filed = dataSetMgr.findField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), attrBean.getCurrAttrName());
                this.fieldNameAndFieldModelMap.put(attrBean.getCurrAttrName(), filed);
            }
            if (!filed.getFieldType().equals((Object)FieldType.TIME_DIM)) {
                return value;
            }
            int dataTypeValue = filed.getValType();
            DataType dataType = DataType.valueOf(dataTypeValue);
            Format format = DSUtils.generateFormat(filed, Locale.getDefault());
            if (format == null) {
                return value;
            }
            return format.format(DataSourceUtils.format(value, dataType));
        }
        catch (Exception e) {
            throw new DataSourceException("\u683c\u5f0f\u5316\u5b57\u7b26\u4e32\u51fa\u9519" + e.getMessage(), e);
        }
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(DataSourceModel dataSourceModel) throws DataSourceException {
        DSHierarchyDataSourceModel dsHierDataSourceModel = (DSHierarchyDataSourceModel)dataSourceModel;
        String dsName = dsHierDataSourceModel.getDsName();
        String dsType = dsHierDataSourceModel.getDsType();
        ArrayList<DataSourceCandidateFieldInfo> fieldInfos = new ArrayList<DataSourceCandidateFieldInfo>();
        try {
            DSModel dsModel = new DataSetManager().findModel(dsName, dsType);
            for (DSField field : dsModel.getFields()) {
                fieldInfos.add(new DataSourceCandidateFieldInfo(field.getName(), field.getTitle()));
            }
        }
        catch (BIDataSetException e) {
            new DataSourceException(e);
        }
        return fieldInfos;
    }
}

