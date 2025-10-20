/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.extend.ds.field;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSUtils;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.DataSetManager;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.manager.PageDataSetReader;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.ParameterEnvFactory;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.field.DsFiledQueryBuilder;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.manager.IDataSourceDataQuickProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DsFieldDataSourceDataProvider
implements IDataSourceDataProvider,
IDataSourceDataQuickProvider {
    private DSFieldDataSourceModel dataSourceModel;
    private DsFiledQueryBuilder queryBuilder;
    private Map<String, DSField> fieldNameAndFieldModelMap = new HashMap<String, DSField>();
    private DataSourceMetaInfo metaInfo;

    public DsFieldDataSourceDataProvider(DataSourceModel dataSourceModel) {
        this.dataSourceModel = (DSFieldDataSourceModel)dataSourceModel;
        this.queryBuilder = new DsFiledQueryBuilder();
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesForList(List<DataSourceValueModel> values, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (values == null || values.size() == 0) {
                return new MemoryDataSet();
            }
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            Map<ParameterModel, Object> parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(this.dataSourceModel, values, parameterValueMap);
            BIDataSet resultBiDataSet = dataSet.filter(filterItems);
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBiDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    public MemoryDataSet<ParameterColumnInfo> filterValues(String expression, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (StringUtils.isEmpty((String)expression)) {
                return null;
            }
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            Map<ParameterModel, Object> parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            List<FilterItem> filterItems = this.queryBuilder.buildCascadeFiterItems(this.dataSourceModel, parameterValueMap);
            BIDataSet resultBIDataSet = dataSet.filter(expression).filter(filterItems);
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBIDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        try {
            DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
            if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
                return this.getAllValues(env);
            }
            if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
                Object dataSourceValues = parameterModel.getDataSourceValues();
                if (dataSourceValues == null) {
                    return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
                }
                MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList((List)dataSourceValues, env);
                return DataSourceUtils.sortByAppointValues((List)dataSourceValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
                String dataSourceFilter = parameterModel.getDataSourceFilter();
                if (StringUtils.isEmpty((String)dataSourceFilter)) {
                    return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
                }
                return this.filterValues(dataSourceFilter, env);
            }
            return null;
        }
        catch (Exception e) {
            throw new DataSourceException("\u8fc7\u6ee4\u53c2\u6570\u53ef\u9009\u503c\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> getAllValues(ParameterEngineEnv env) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            Map<ParameterModel, Object> parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(this.dataSourceModel, parameterValueMap);
            BIDataSet resultBIDataSet = dataSet.filter(filterItems);
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBIDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
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
            MemoryDataSet<ParameterColumnInfo> resultDataSet = this.filterValuesForList((List)defaultValues, env);
            resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.filterChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
            return DataSourceUtils.sortByAppointValues((List)defaultValues, resultDataSet, this.getDataSourceMetaInfo());
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            MemoryDataSet<ParameterColumnInfo> dataSet = this.filterChoiceValues(parameterModel, env);
            return DataSourceUtils.getFirstValue(ParameterUtils.reverseDataSet(parameterModel, dataSet));
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.EXPRESSION)) {
            String expression = parameterModel.getDefalutValueFilter();
            if (StringUtils.isEmpty((String)expression)) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            return this.getExpressionDefaultValue(expression, parameterModel, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.NONE)) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> getExpressionDefaultValue(String expression, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (StringUtils.isNotEmpty((String)expression)) {
                FormulaParser parser = FormulaParser.getInstance();
                IContext context = new IContext(){};
                IExpression expressionNode = parser.parseEval(expression, context);
                Object value = expressionNode.evaluate(context);
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
                DataRow row = dataSet.add();
                row.setValue(0, value);
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
            return this.filterDefaultValuesForExpr(expression, env);
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterDefaultValuesForExpr(String expression, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (StringUtils.isEmpty((String)expression)) {
                return null;
            }
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            Map<ParameterModel, Object> parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            List<FilterItem> filterItems = this.queryBuilder.buildCascadeFiterItems(this.dataSourceModel, parameterValueMap);
            MemoryDataSet<ParameterColumnInfo> choiceValues = this.filterChoiceValues(parameterModel, env);
            if (choiceValues.size() == 0) {
                return choiceValues;
            }
            List<FilterItem> choiceValuesFilterItems = this.queryBuilder.buildFilterItems(this.dataSourceModel, DataSourceUtils.dataSet2DataSourceValueModelsForDsField(choiceValues, this.dataSourceModel), parameterValueMap);
            BIDataSet resultBIDataSet = dataSet.filter(choiceValuesFilterItems).filter(expression).filter(filterItems);
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBIDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, choiceValues, this.getDataSourceMetaInfo());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private String getNameColValue(Object keyValue, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        if (keyValue == null) {
            return null;
        }
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsFieldName = this.dataSourceModel.getDsFieldName();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyField = keyAndNameCol[0];
            String nameField = keyAndNameCol[1];
            if (!keyField.equals(nameField) && keyField.equals(dsFieldName)) {
                return this.getNameColValueFromDataSet(keyValue, keyField, nameField, this.filterChoiceValues(parameterModel, env));
            }
            Format format = DSUtils.generateFormat(dsField, env.getI18nLang() == null ? Locale.getDefault() : Locale.forLanguageTag(env.getI18nLang()));
            return format.format(keyValue);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private String getNameColValueFromDataSet(Object keyValue, String keyField, String nameField, MemoryDataSet<ParameterColumnInfo> allValues) {
        Iterator it = allValues.iterator();
        int keyIndex = allValues.getMetadata().indexOf(keyField);
        int nameIndex = allValues.getMetadata().indexOf(nameField);
        while (it.hasNext()) {
            DataRow row = (DataRow)it.next();
            for (int i = 0; i < allValues.getMetadata().size(); ++i) {
                if (!row.getValue(keyIndex).equals(keyValue)) continue;
                return row.getString(nameIndex);
            }
        }
        return null;
    }

    @Override
    public void init(ParameterEngineEnv env) {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> filterValues, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        if (filterValues == null || filterValues.size() == 0) {
            return new MemoryDataSet();
        }
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            Map<ParameterModel, Object> parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            String filterExpr = this.queryBuilder.buildSearchFiterExpr(this.dataSourceModel, this.formatSearchFilterValues(filterValues), parameterValueMap);
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            MemoryDataSet<ParameterColumnInfo> choiceValues = isSearchFromChoiceValues ? this.filterChoiceValues(parameterModel, env) : this.getAllValues(env);
            if (choiceValues.size() == 0) {
                return choiceValues;
            }
            List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(this.dataSourceModel, DataSourceUtils.dataSet2DataSourceValueModelsForDsField(choiceValues, this.dataSourceModel), parameterValueMap);
            BIDataSet tempResultDataSet = dataSet.filter(filterItems);
            BIDataSet resultBIDataSet = tempResultDataSet.filter(filterExpr);
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBIDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            if (isSearchFromChoiceValues) {
                this.formatMemoryDataSet(resultDataSet);
                return DataSourceUtils.sortByAppointValues(choiceValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
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
            String dsFieldName = this.dataSourceModel.getDsFieldName();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            return DataSourceUtils.format(filterValue, DataType.valueOf(dsField.getValType()));
        }
        catch (Exception e) {
            return filterValue;
        }
    }

    private BIDataSet distinctBIDataSet(IDataSetManager dataSetManager, String dsName, BIDataSet resultDataSet) throws BIDataSetNotFoundException, BIDataSetException {
        String dsFieldName = this.dataSourceModel.getDsFieldName();
        DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
        String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
        String keyCol = keyAndNameCol[0];
        String nameCol = keyAndNameCol[1];
        ArrayList<String> cols = new ArrayList<String>();
        cols.add(keyCol);
        cols.add(nameCol);
        return resultDataSet.distinct(cols);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        try {
            BIDataSet resultBIDataSet;
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet dataSet = dataSetManager.open(DataSetManagerUtils.createDSContext(env), dsName, this.dataSourceModel.getDsType());
            if (StringUtils.isNotEmpty((String)filterExpr)) {
                resultBIDataSet = dataSet.filter(filterExpr);
            } else {
                List<FilterItem> filterItems = this.queryBuilder.buildFilterItems(this.dataSourceModel);
                resultBIDataSet = dataSet.filter(filterItems);
            }
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, resultBIDataSet);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private List<DataSourceAttrBean> getAttrBeans() throws DataSourceException {
        try {
            ArrayList<DataSourceAttrBean> attrBeans = new ArrayList<DataSourceAttrBean>();
            DataSourceAttrBean bean = new DataSourceAttrBean();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsFieldName = this.dataSourceModel.getDsFieldName();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            String[] keyAndNameCol = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyCol = keyAndNameCol[0];
            String nameCol = keyAndNameCol[1];
            DSField nameField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), nameCol);
            DSField keyField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), keyCol);
            bean.setCurrAttrName(dsFieldName);
            bean.setKeyColName(keyCol);
            bean.setNameColName(nameCol);
            bean.setTitle(nameField.getTitle() + ";" + keyField.getTitle());
            attrBeans.add(bean);
            return attrBeans;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public synchronized DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException {
        if (this.metaInfo != null) {
            return this.metaInfo;
        }
        this.metaInfo = new DataSourceMetaInfo();
        this.metaInfo.getAttrBeans().addAll(this.getAttrBeans());
        this.metaInfo.setTitle(this.getTitle());
        return this.metaInfo;
    }

    private String getTitle() throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            String dsFieldName = this.dataSourceModel.getDsFieldName();
            DSField dsField = dataSetManager.findField(dsName, this.dataSourceModel.getDsType(), dsFieldName);
            return dsField.getTitle();
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        List<DataSourceValueModel> valuesList = DataSourceUtils.dataSet2DataSourceValueModelsForDsField((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)values), this.dataSourceModel);
        return this.filterValuesForList(valuesList, env);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fuzzySearch(List<String> values, ParameterEngineEnv env) throws DataSourceException {
        return this.searchValues(values, true, env);
    }

    private void formatMemoryDataSet(MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSetException, BIDataSetNotFoundException, BIDataSetException {
        if (dataSet == null || dataSet.size() == 0) {
            return;
        }
        if (!this.dataSourceModel.getDataType().equals((Object)DataType.DATETIME)) {
            return;
        }
        int colIndex = dataSet.getMetadata().size();
        for (int i = 0; i < colIndex; ++i) {
            for (DataRow row : dataSet) {
                Object value = row.getValue(i);
                if (!(value instanceof Calendar)) continue;
                Calendar calendar = (Calendar)value;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String dateValue = dateFormat.format(calendar.getTime());
                row.setValue(i, (Object)dateValue);
            }
        }
    }

    @Override
    public String formatValue(String value, DataSourceAttrBean attrBean) throws DataSourceException {
        try {
            Format format;
            IDataSetManager dataSetMgr = DataSetManagerUtils.createDataSetManager();
            DSField filed = this.fieldNameAndFieldModelMap.get(this.dataSourceModel.getDsFieldName());
            if (filed == null) {
                filed = dataSetMgr.findField(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType(), this.dataSourceModel.getDsFieldName());
                this.fieldNameAndFieldModelMap.put(this.dataSourceModel.getDsFieldName(), filed);
            }
            if ((format = DSUtils.generateFormat(filed, Locale.getDefault())) == null) {
                return value;
            }
            int dataTypeValue = filed.getValType();
            DataType dataType = DataType.valueOf(dataTypeValue);
            return format.format(DataSourceUtils.format(value, dataType));
        }
        catch (Exception e) {
            throw new DataSourceException("\u683c\u5f0f\u5316\u5b57\u7b26\u4e32\u51fa\u9519" + e.getMessage(), e);
        }
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(DataSourceModel dataSourceModel) throws DataSourceException {
        DSFieldDataSourceModel dsFieldDataSourceModel = (DSFieldDataSourceModel)dataSourceModel;
        String dsName = dsFieldDataSourceModel.getDsName();
        String dsType = dsFieldDataSourceModel.getDsType();
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

    private BIDataSet getTopNValues(ParameterEngineEnv env, int n, List<FilterItem> filters) throws DataSourceException {
        DSContext context = new DSContext();
        context.setUserGuid(env.getUserGuid());
        try {
            context.setParameterEnv(ParameterEnvFactory.createParameterEnv(env));
        }
        catch (ParameterException e) {
            throw new DataSourceException(e.getMessage(), e);
        }
        if (filters != null) {
            for (FilterItem filter : filters) {
                context.addFilterItem(filter);
            }
        }
        IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
        try {
            if (n > 0) {
                PageDataSetReader pageReader = dataSetManager.openPageDataSet((IDSContext)context, this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType());
                pageReader.setPageSize(n);
                return pageReader.open(1, context);
            }
            return dataSetManager.open(context, this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> distinctValues(ParameterEngineEnv env, BIDataSet ds) throws DataSourceException {
        try {
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            String dsName = this.dataSourceModel.getDsName();
            BIDataSet distinctedDataSet = this.distinctBIDataSet(dataSetManager, dsName, ds);
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.biDataSet2ParameterMemoryDataSet(distinctedDataSet);
            this.formatMemoryDataSet(resultDataSet);
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private List<FilterItem> buildFilterItem(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        Map<ParameterModel, Object> parameterValueMap;
        try {
            parameterValueMap = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
        }
        catch (ParameterException e) {
            throw new DataSourceException(e.getMessage(), e);
        }
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.queryBuilder.buildFilterItems(this.dataSourceModel, parameterValueMap);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            List dataSourceValues = (List)parameterModel.getDataSourceValues();
            return this.queryBuilder.buildFilterItems(this.dataSourceModel, dataSourceValues, parameterValueMap);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            String dataSourceFilter = parameterModel.getDataSourceFilter();
            List<FilterItem> filterItems = this.queryBuilder.buildCascadeFiterItems(this.dataSourceModel, parameterValueMap);
            filterItems.add(new FilterItem("", dataSourceFilter));
            return filterItems;
        }
        throw new DataSourceException("\u672a\u77e5\u7684\u8fc7\u6ee4\u65b9\u5f0f\uff1a" + (Object)((Object)dataSourceFilterMode));
    }

    private MemoryDataSet<ParameterColumnInfo> distinctValuesByFilterItem(ParameterEngineEnv env, DSModel model, List<FilterItem> filterItems) throws DataSourceException {
        BIDataSet ds = this.getTopNValues(env, -1, filterItems);
        return this.distinctValues(env, ds);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, int maxRecordSize, boolean isFirstLevel) throws DataSourceException {
        DSModel model;
        String dataSourceFilter;
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT) ? this.isEmpty(parameterModel.getDataSourceValues()) : dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION) && StringUtils.isEmpty((String)(dataSourceFilter = parameterModel.getDataSourceFilter()))) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        try {
            IDataSetManager datasetMgr = DataSetManagerFactory.create();
            model = datasetMgr.findModel(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType());
        }
        catch (Exception e1) {
            throw new DataSourceException(e1.getMessage(), e1);
        }
        List<FilterItem> filterItems = this.buildFilterItem(parameterModel, env);
        MemoryDataSet<ParameterColumnInfo> dataSet = this.distinctValuesByFilterItem(env, model, filterItems);
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            List dataSourceValues = (List)parameterModel.getDataSourceValues();
            dataSet = DataSourceUtils.sortByAppointValues(dataSourceValues, dataSet, this.getDataSourceMetaInfo());
        }
        return dataSet;
    }

    private boolean isEmpty(Object obj) {
        if (obj == null || StringUtils.isEmpty((String)obj.toString())) {
            return true;
        }
        return obj instanceof List && ((List)obj).isEmpty();
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fillDatasetByKey(MemoryDataSet<ParameterColumnInfo> dataSet, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        if (dataSet == null || dataSet.isEmpty()) {
            return dataSet;
        }
        List<FilterItem> filterItems = this.buildFilterItem(parameterModel, env);
        ArrayList<Object> keys = new ArrayList<Object>();
        Iterator itor = dataSet.iterator();
        while (itor.hasNext()) {
            keys.add(((DataRow)itor.next()).getValue(0));
        }
        Column keyCol = dataSet.getMetadata().getColumn(0);
        String keyField = keyCol.getName();
        filterItems.add(new FilterItem(keyField, keys));
        BIDataSet ds = this.getTopNValues(env, -1, filterItems);
        int keyIdx = ds.getMetadata().indexOf(keyField);
        BIDataSetFieldInfo fieldInfo = (BIDataSetFieldInfo)ds.getMetadata().getColumn(keyIdx).getInfo();
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(keyField);
        fields.add(fieldInfo.getNameField());
        if (dataSet.getMetadata().getColumnCount() > 2) {
            Column pcol = dataSet.getMetadata().getColumn(2);
            fields.add(pcol.getName());
        }
        try {
            ds = ds.distinct(fields);
        }
        catch (BIDataSetException e) {
            throw new DataSourceException(e.getMessage(), e);
        }
        return DataSourceUtils.biDataSet2ParameterMemoryDataSet(ds);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickSearch(List<String> values, ParameterEngineEnv env, int maxRecordSize, boolean showPath) throws DataSourceException {
        String dataSourceFilter;
        DSModel model;
        String type = this.dataSourceModel.getDsType();
        if (!SQLModel.isSQLModelType(type)) {
            return this.searchValues(values, true, env);
        }
        try {
            IDataSetManager datasetMgr = DataSetManagerFactory.create();
            model = datasetMgr.findModel(this.dataSourceModel.getDsName(), this.dataSourceModel.getDsType());
        }
        catch (Exception e1) {
            throw new DataSourceException(e1.getMessage(), e1);
        }
        DSField dsField = model.findField(this.dataSourceModel.getDsFieldName());
        if (dsField == null) {
            throw new DataSourceException("\u6307\u5b9a\u7684\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + this.dataSourceModel.getDsFieldName());
        }
        DSField keyField = model.findField(dsField.getKeyField());
        DSField nameField = model.findField(dsField.getNameField());
        if (keyField == null) {
            keyField = dsField;
        }
        if (nameField == null) {
            nameField = dsField;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < values.size(); ++i) {
            if (i > 0) {
                buf.append(" and ");
            }
            buf.append("(");
            List<String> conds = ParameterUtils.parseSearchValues(values.get(i));
            for (int j = 0; j < conds.size(); ++j) {
                String cond;
                if (j > 0) {
                    buf.append(" or ");
                }
                if (StringUtils.isNotEmpty((String)(cond = conds.get(j)))) {
                    cond = cond.toUpperCase();
                }
                buf.append("UPPER(").append(keyField.getName()).append(")").append(" like '%").append(cond).append("%' or ");
                buf.append("UPPER(").append(nameField.getName()).append(")").append(" like '%").append(cond).append("%' ");
            }
            buf.append(")");
        }
        String filterExpr = buf.toString();
        ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
        filterItems.add(new FilterItem(keyField.getName(), filterExpr));
        ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            if (parameterModel.getDataSourceValues() != null) {
                filterItems.addAll(this.buildFilterItem(parameterModel, env));
            }
        } else if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION) && StringUtils.isNotEmpty((String)(dataSourceFilter = parameterModel.getDataSourceFilter()))) {
            filterItems.addAll(this.buildFilterItem(parameterModel, env));
        }
        return this.distinctValuesByFilterItem(env, model, filterItems);
    }
}

