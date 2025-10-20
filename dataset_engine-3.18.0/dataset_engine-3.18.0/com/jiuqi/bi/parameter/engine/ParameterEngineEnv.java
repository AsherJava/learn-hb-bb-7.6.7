/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.context.IUserBindingContext
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterEnvSnapShot;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterScopeType;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParamCacheInfo;
import com.jiuqi.bi.parameter.engine.cascade.ParameterCascadeEngine;
import com.jiuqi.bi.parameter.engine.cascade.ParameterCascadeRelation;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceDataFactory;
import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProviderEx;
import com.jiuqi.bi.parameter.manager.IDataSourceDataQuickProvider;
import com.jiuqi.bi.parameter.manager.IDataSourceTreeHierarchyProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterDimType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.parameter.storage.ParameterStorageException;
import com.jiuqi.bi.parameter.storage.ParameterStorageManager;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.context.IUserBindingContext;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParameterEngineEnv {
    private List<ParameterModel> parameterModels;
    private String userGuid;
    private String i18nLang;
    private Map<String, Object> valuesMap = new HashMap<String, Object>();
    private boolean orderValuesEnable = false;
    private Map<String, Boolean> orderValuesMap = new HashMap<String, Boolean>();
    private ParameterCascadeRelation cascadeRelation;
    private ParameterCascadeEngine cascadeEngine;
    private Map<String, IDataSourceDataProvider> dataSourceProviderMap = new HashMap<String, IDataSourceDataProvider>();
    private List<String> orgRangeParameterNames = new ArrayList<String>();
    private Map<String, ParameterModel> orgRangeParameterModels = new HashMap<String, ParameterModel>();
    private String owner_Name;
    private String owner_Type;
    private Map<String, Object> queryProperties = new HashMap<String, Object>();
    private Map<ParameterScopeType, List<String>> scopes = new HashMap<ParameterScopeType, List<String>>();
    private Map<String, DataSourceMetaInfo> metaInfoBuffer = new ConcurrentHashMap<String, DataSourceMetaInfo>();
    private Map<String, String> paramUnittreeMap = new HashMap<String, String>();
    private Map<String, ParamCacheInfo> cacheInfoMap = new HashMap<String, ParamCacheInfo>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = this.lock.readLock();

    public ParameterEngineEnv(List<ParameterModel> parameterModels, String userGuid) throws ParameterException {
        this.parameterModels = ParameterUtils.getNewParameterList(parameterModels);
        for (ParameterModel pm : this.parameterModels) {
            this.cacheInfoMap.put(pm.getName().toUpperCase(), new ParamCacheInfo(pm));
        }
        this.userGuid = userGuid;
        this.buildRangeParameter(this.parameterModels);
        this.buldRangeCascadeParameter(this.parameterModels);
        this.cascadeEngine = new ParameterCascadeEngine(this);
        this.cascadeRelation = this.cascadeEngine.buildParameterCascadeRelation();
        this.buildNoneDataSourceParameterDefaultValues(this.parameterModels);
        this.cascadeRelation.testLoop();
        this.cascadeRelation.optimize();
    }

    public ParameterEngineEnv(List<ParameterModel> parameterModels, String userGuid, boolean withOutCascadeRelation) throws ParameterException {
        this.cascadeEngine = new ParameterCascadeEngine(this);
        this.parameterModels = ParameterUtils.getNewParameterList(parameterModels);
        for (ParameterModel pm : this.parameterModels) {
            this.cacheInfoMap.put(pm.getName().toUpperCase(), new ParamCacheInfo(pm));
        }
        this.userGuid = userGuid;
        this.buildRangeParameter(this.parameterModels);
        this.buildNoneDataSourceParameterDefaultValues(this.parameterModels);
    }

    private ParameterEngineEnv(List<ParameterModel> parameterModels, String userGuid, ParameterCascadeRelation cascadeRelation, Map<String, Object> valuesMap, List<String> orgRangeParameterNames, Map<String, ParameterModel> orgRangeParameterModels) {
        this.cascadeEngine = new ParameterCascadeEngine(this);
        this.parameterModels = ParameterUtils.getNewParameterList(parameterModels);
        this.userGuid = userGuid;
        this.cascadeRelation = cascadeRelation;
        this.valuesMap = valuesMap;
        ArrayList<String> rangePararNames = new ArrayList<String>();
        rangePararNames.addAll(orgRangeParameterNames);
        this.orgRangeParameterNames = rangePararNames;
        HashMap<String, ParameterModel> rangeParameterModels = new HashMap<String, ParameterModel>();
        rangeParameterModels.putAll(orgRangeParameterModels);
        this.orgRangeParameterModels = rangeParameterModels;
    }

    public void setI18nLang(String i18nLang) {
        this.i18nLang = i18nLang;
    }

    public String getI18nLang() {
        return this.i18nLang;
    }

    public void initParameterValue(Map<String, Object> paramValueMap, boolean ignoreExternalParam) throws ParameterException {
        if (paramValueMap == null || paramValueMap.isEmpty()) {
            return;
        }
        HashSet<String> keys = new HashSet<String>(paramValueMap.keySet());
        for (String s : keys) {
            if (s.equals(s.toUpperCase())) continue;
            Object v = paramValueMap.get(s);
            paramValueMap.remove(s);
            paramValueMap.put(s.toUpperCase(), v);
        }
        HashMap<String, Object> tmp = new HashMap<String, Object>(paramValueMap);
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<ParameterModel> cascadeModels = new ArrayList<ParameterModel>();
        for (ParameterModel pm : this.parameterModels) {
            Object v = tmp.get(pm.getName());
            if (v != null) {
                map.put(pm.getName(), v);
                tmp.remove(pm.getName());
            }
            List<String> cascades = pm.getCascadeParameters();
            for (String s : cascades) {
                v = tmp.get(s);
                if (v == null) continue;
                map.put(s, v);
                tmp.remove(s);
                if (this.getParameterModel(s) != null) continue;
                try {
                    ParameterModel parameterModel = ParameterStorageManager.getInstance().findModel(s, null, null, null);
                    if (parameterModel == null) continue;
                    cascadeModels.add(parameterModel);
                }
                catch (ParameterStorageException e) {
                    throw new ParameterException(e);
                }
            }
        }
        if (!cascadeModels.isEmpty()) {
            this.parameterModels.addAll(cascadeModels);
        }
        if (!ignoreExternalParam) {
            map.putAll(tmp);
        }
        List<String> gopology = this.cascadeEngine.computeParameterGopology();
        ArrayList<String> l = new ArrayList<String>();
        for (String s : gopology) {
            if (!map.containsKey(s.toUpperCase())) continue;
            l.add(s);
            map.remove(s.toUpperCase());
        }
        if (l.size() < map.size()) {
            l.addAll(map.keySet());
        }
        for (String parameterName : l) {
            parameterName = parameterName.toUpperCase();
            Object value = paramValueMap.get(parameterName);
            try {
                if (value instanceof SmartSelector) {
                    this.valuesMap.put(parameterName, value);
                    continue;
                }
                if (value instanceof List) {
                    MemoryDataSet<ParameterColumnInfo> result;
                    List values = (List)value;
                    DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                    if (dataSourceMetaInfo == null) {
                        result = values.size() > 0 ? this.formateSubmitValue(parameterName, values.get(0)) : null;
                    } else {
                        MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.getMemoryDataSet(dataSourceMetaInfo);
                        ParameterModel parameterModel = this.getParameterModel(parameterName);
                        int colCount = dataSet.getMetadata().size();
                        if (values != null && values.size() != 0) {
                            for (Object keyValue : values) {
                                DataRow row = dataSet.add();
                                for (int i = 0; i < colCount; ++i) {
                                    row.setValue(i, keyValue);
                                }
                            }
                        }
                        IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
                        dataProvider.init(this);
                        result = dataProvider instanceof IDataSourceDataQuickProvider ? ((IDataSourceDataQuickProvider)dataProvider).fillDatasetByKey(dataSet, parameterModel, this) : dataProvider.filterValues(dataSet, parameterModel, this);
                        result = this.sortByAppointValues(dataSet, result, dataSourceMetaInfo);
                    }
                    Object v = this.valuesMap.get(parameterName);
                    if (v instanceof SmartSelector) {
                        ((SmartSelector)v).setDefaultValue(result);
                        continue;
                    }
                    this.valuesMap.put(parameterName, result);
                    continue;
                }
                ParameterModel parameterModel = this.getParameterModel(parameterName);
                if (parameterModel.getDataSourceModel() == null && parameterModel.getDataType().equals((Object)DataType.DATETIME)) {
                    this.valuesMap.put(parameterName, this.formatKeyValue2String(parameterModel, value));
                    continue;
                }
                Object v = this.valuesMap.get(parameterName);
                if (v instanceof SmartSelector) {
                    ((SmartSelector)v).setDefaultValue(value);
                    continue;
                }
                this.valuesMap.put(parameterName, value);
            }
            catch (Exception e) {
                throw new ParameterException("\u8bbe\u7f6e\u53c2\u6570\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage(), e.getCause());
            }
        }
    }

    public Object getValue(String parameterName) throws ParameterException {
        List dvs;
        MemoryDataSet memory;
        ParameterModel m;
        Object result;
        try {
            parameterName = parameterName.toUpperCase();
            result = this.isRangeParameter(parameterName) ? this.getValueForRange(parameterName) : (this.isDateParameter(parameterName) ? this.getValueForDate(parameterName) : this.getValueForOuter(parameterName));
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u53d6\u503c\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        if (result instanceof MemoryDataSet) {
            result = this.tryGetValue(parameterName, (MemoryDataSet)result);
        }
        if ((m = this.getParameterModel(parameterName)) != null && m.getSelectMode() == ParameterSelectMode.SINGLE && m.getDefaultValueFilterMode() == ParameterDefaultValueFilterMode.EXPRESSION && result instanceof MemoryDataSet && !(memory = (MemoryDataSet)result).isEmpty()) {
            Object[] first = memory.getBuffer(0);
            memory.clear();
            try {
                memory.add(first);
            }
            catch (DataSetException dataSetException) {
                // empty catch block
            }
        }
        if (m.getDataSourceFilterMode() == DataSourceFilterMode.APPOINT && (dvs = (List)m.getDataSourceValues()) != null && dvs.size() > 0 && result instanceof MemoryDataSet) {
            MemoryDataSet rs = (MemoryDataSet)result;
            try {
                result = this.sortByAppointValues(dvs, (MemoryDataSet<ParameterColumnInfo>)rs, this.getDataSourceMetaInfo(parameterName));
            }
            catch (DataSourceException e) {
                throw new ParameterException(e);
            }
        }
        return result;
    }

    public Object tryGetValue(String parameterName, MemoryDataSet<?> result) throws ParameterException {
        ParameterModel pmodel;
        MemoryDataSet<?> ds = result;
        if (ds.isEmpty() && (pmodel = this.getParameterModel(parameterName)) != null) {
            if (pmodel.getSelectMode() == ParameterSelectMode.SINGLE) {
                return result;
            }
            DataSourceFilterMode filterMode = pmodel.getDataSourceFilterMode();
            boolean needQuery = false;
            if (filterMode == DataSourceFilterMode.APPOINT && pmodel.getDataSourceValues() != null) {
                needQuery = true;
            } else if (filterMode == DataSourceFilterMode.EXPRESSION && StringUtils.isNotEmpty((String)pmodel.getDataSourceFilter())) {
                needQuery = true;
            } else if (filterMode == DataSourceFilterMode.ALL) {
                // empty if block
            }
            if (needQuery) {
                IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
                try {
                    if (dataProvider instanceof IDataSourceDataProviderEx) {
                        return ((IDataSourceDataProviderEx)dataProvider).filterAllChoiceValues(pmodel, this);
                    }
                    return dataProvider.filterChoiceValues(pmodel, this);
                }
                catch (DataSourceException e) {
                    throw new ParameterException(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    public Object getValueForUI(String parameterName) throws ParameterException {
        try {
            parameterName = parameterName.toUpperCase();
            if (this.isRangeParameter(parameterName)) {
                return this.getValueForRange(parameterName);
            }
            if (this.isDateParameter(parameterName)) {
                return this.getValueForDate(parameterName);
            }
            return this.getValueForOuter(parameterName);
        }
        catch (Exception e) {
            String errorMessage = "\u83b7\u53d6\u53c2\u6570\u53d6\u503c\u65f6\u51fa\u9519";
            ParameterModel p = this.getParameterModel(parameterName);
            if (p != null) {
                errorMessage = "\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u53d6\u503c\u65f6\u51fa\u9519";
            }
            throw new ParameterException(errorMessage + "\uff0c" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<?> getValueAsList(String parameterName) throws ParameterException {
        this.readLock.lock();
        try {
            Cloneable ss;
            parameterName = parameterName.toUpperCase();
            if (this.isNormalHierParameter(parameterName)) {
                throw new ParameterException("\u5c42\u6b21\u7c7b\u578b\u7684\u53c2\u6570\u6682\u4e0d\u652f\u6301\uff01");
            }
            ArrayList<Object> valueList = new ArrayList<Object>();
            Object value = this.getValue(parameterName);
            if (value instanceof SmartSelector) {
                ss = (SmartSelector)value;
                ParamCacheInfo pInfo = this.getParamInfo(parameterName);
                value = ((SmartSelector)ss).getFilterValueInMemory(this, parameterName, !pInfo.modify);
                if (value instanceof MemoryDataSet) {
                    value = this.tryGetValue(parameterName, (MemoryDataSet)value);
                }
            }
            if (value == null) {
                ss = valueList;
                return ss;
            }
            if (value instanceof MemoryDataSet) {
                this.formatMemoryDataSet(parameterName, valueList, value);
            } else if (value.getClass().isArray()) {
                Object[] valueArray;
                for (Object object : valueArray = (Object[])value) {
                    valueList.add(object);
                }
            } else {
                valueList.add(value);
            }
            ArrayList<Object> arrayList = valueList;
            return arrayList;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<?> getValueAsListForUI(String parameterName) throws ParameterException {
        this.readLock.lock();
        try {
            Cloneable ss;
            parameterName = parameterName.toUpperCase();
            if (this.isNormalHierParameter(parameterName)) {
                throw new ParameterException("\u5c42\u6b21\u7c7b\u578b\u7684\u53c2\u6570\u6682\u4e0d\u652f\u6301\uff01");
            }
            ArrayList<Object> valueList = new ArrayList<Object>();
            Object value = this.getValueForUI(parameterName);
            if (value instanceof SmartSelector) {
                ss = (SmartSelector)value;
                value = ss.getFilterValueInMemory(this, parameterName);
            }
            if (value == null) {
                ss = valueList;
                return ss;
            }
            if (value instanceof MemoryDataSet) {
                this.formatMemoryDataSet(parameterName, valueList, value);
                ParameterModel pmodel = this.getParameterModel(parameterName);
                if (pmodel.isRangeParameter()) {
                    this.formatMemoryDataSet(parameterName, valueList, value);
                }
            } else if (value.getClass().isArray()) {
                Object[] valueArray;
                for (Object object : valueArray = (Object[])value) {
                    valueList.add(object);
                }
            } else {
                valueList.add(value);
            }
            ArrayList<Object> arrayList = valueList;
            return arrayList;
        }
        finally {
            this.readLock.unlock();
        }
    }

    private void formatMemoryDataSet(String parameterName, List<Object> valueList, Object value) throws ParameterException {
        block3: {
            DataSourceModel dataSourceModel;
            DataSourceMetaInfo dataSourceMetaInfo;
            MemoryDataSet dataSet;
            block2: {
                dataSet = (MemoryDataSet)value;
                dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                dataSourceModel = this.getParameterModel(parameterName).getDataSourceModel();
                if (dataSourceModel != null && dataSourceMetaInfo != null) break block2;
                for (DataRow row : dataSet) {
                    valueList.add(row.getValue(0));
                }
                break block3;
            }
            if (dataSourceMetaInfo.getAttrBeans().size() <= 0) break block3;
            DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
            int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
            for (DataRow row : dataSet) {
                valueList.add(this.formatResultValue(dataSourceModel, row.getValue(keyIndex)));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<?> getKeyValueAsList(String parameterName) throws ParameterException {
        this.readLock.lock();
        try {
            Cloneable ss;
            parameterName = parameterName.toUpperCase();
            if (this.isNormalHierParameter(parameterName)) {
                throw new ParameterException("\u5c42\u6b21\u7c7b\u578b\u7684\u53c2\u6570\u6682\u4e0d\u652f\u6301\uff01");
            }
            ArrayList<Object> valueList = new ArrayList<Object>();
            Object value = this.getValue(parameterName);
            if (value instanceof SmartSelector && (value = (ss = (SmartSelector)value).getFilterValueInMemory(this, parameterName)) instanceof MemoryDataSet) {
                value = this.tryGetValue(parameterName, (MemoryDataSet)value);
            }
            if (value == null) {
                ss = valueList;
                return ss;
            }
            if (value instanceof MemoryDataSet) {
                MemoryDataSet dataSet = (MemoryDataSet)value;
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                DataSourceModel dataSourceModel = this.getParameterModel(parameterName).getDataSourceModel();
                if (dataSourceMetaInfo == null || dataSourceModel == null) {
                    for (DataRow row : dataSet) {
                        valueList.add(row.getValue(0));
                    }
                } else if (dataSourceMetaInfo.getAttrBeans().size() > 0) {
                    DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                    int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
                    if (keyIndex == -1) {
                        keyIndex = 0;
                    }
                    for (DataRow row : dataSet) {
                        valueList.add(this.formatResultValue(dataSourceModel, row.getValue(keyIndex)));
                    }
                }
            } else if (value.getClass().isArray()) {
                Object[] valueArray;
                for (Object object : valueArray = (Object[])value) {
                    valueList.add(object);
                }
            } else {
                valueList.add(value);
            }
            ArrayList<Object> arrayList = valueList;
            return arrayList;
        }
        finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> getKeyValueAsString(String parameterName) throws ParameterException {
        this.readLock.lock();
        try {
            Cloneable ss;
            parameterName = parameterName.toUpperCase();
            if (this.isNormalHierParameter(parameterName)) {
                throw new ParameterException("\u5c42\u6b21\u7c7b\u578b\u7684\u53c2\u6570\u6682\u4e0d\u652f\u6301\uff01");
            }
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            ArrayList<String> valueList = new ArrayList<String>();
            Object value = this.getValue(parameterName);
            if (value instanceof SmartSelector && (value = (ss = (SmartSelector)value).getFilterValueInMemory(this, parameterName)) instanceof MemoryDataSet) {
                value = this.tryGetValue(parameterName, (MemoryDataSet)value);
            }
            if (value == null) {
                ss = valueList;
                return ss;
            }
            if (value instanceof MemoryDataSet) {
                MemoryDataSet dataSet = (MemoryDataSet)value;
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
                if (keyIndex == -1) {
                    keyIndex = 0;
                }
                for (DataRow row : dataSet) {
                    valueList.add(row.getString(keyIndex));
                }
            } else if (value.getClass().isArray()) {
                Object[] valueArray;
                for (Object object : valueArray = (Object[])value) {
                    valueList.add(this.formatKeyValue2String(parameterModel, object));
                }
            } else {
                valueList.add(this.formatKeyValue2String(parameterModel, value));
            }
            ArrayList<String> arrayList = valueList;
            return arrayList;
        }
        finally {
            this.readLock.unlock();
        }
    }

    private String formatKeyValue2String(ParameterModel parameterModel, Object value) {
        if (value == null) {
            return null;
        }
        if (parameterModel.getDataType().equals((Object)DataType.DATETIME)) {
            if (value instanceof Calendar) {
                Calendar calendar = (Calendar)value;
                StringBuilder sb = new StringBuilder();
                sb.append(calendar.get(1));
                sb.append(";");
                sb.append(calendar.get(2) + 1);
                sb.append(";");
                sb.append(calendar.get(5));
                return sb.toString();
            }
            return value.toString();
        }
        return value.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> getNameValueAsString(String parameterName) throws ParameterException {
        this.readLock.lock();
        try {
            Cloneable ss;
            parameterName = parameterName.toUpperCase();
            if (this.isNormalHierParameter(parameterName)) {
                throw new ParameterException("\u5c42\u6b21\u7c7b\u578b\u7684\u53c2\u6570\u6682\u4e0d\u652f\u6301\uff01");
            }
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            ArrayList<String> valueList = new ArrayList<String>();
            Object value = this.getValue(parameterName);
            if (value instanceof SmartSelector && (value = (ss = (SmartSelector)value).getFilterValueInMemory(this, parameterName)) instanceof MemoryDataSet) {
                value = this.tryGetValue(parameterName, (MemoryDataSet)value);
            }
            if (value == null) {
                ss = valueList;
                return ss;
            }
            if (value instanceof MemoryDataSet) {
                MemoryDataSet dataSet = (MemoryDataSet)value;
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                int nameIndex = dataSet.getMetadata().indexOf(bean.getNameColName());
                for (DataRow row : dataSet) {
                    String keyValue = row.getString(nameIndex);
                    valueList.add(this.formatValue(keyValue, bean, parameterName));
                }
            } else if (value.getClass().isArray()) {
                Object[] valueArray;
                for (Object object : valueArray = (Object[])value) {
                    valueList.add(this.formatNameValue2String(parameterModel, object));
                }
            } else {
                valueList.add(this.formatNameValue2String(parameterModel, value));
            }
            ArrayList<String> arrayList = valueList;
            return arrayList;
        }
        finally {
            this.readLock.unlock();
        }
    }

    private String formatNameValue2String(ParameterModel parameterModel, Object value) {
        if (value == null) {
            return null;
        }
        if (parameterModel.getDataType().equals((Object)DataType.DATETIME)) {
            if (value instanceof Calendar) {
                Calendar calendar = (Calendar)value;
                StringBuilder sb = new StringBuilder();
                sb.append(calendar.get(1));
                sb.append("\u5e74");
                sb.append(calendar.get(2) + 1);
                sb.append("\u6708");
                sb.append(calendar.get(5));
                sb.append("\u65e5");
                return sb.toString();
            }
            return value.toString();
        }
        return value.toString();
    }

    private Object formatResultValue(DataSourceModel dataSourceModel, Object value) {
        if (value == null) {
            return null;
        }
        if (dataSourceModel.getDataType().equals((Object)DataType.DATETIME) && value instanceof String) {
            String valueStr = value.toString();
            int year = Integer.valueOf(valueStr.substring(0, 4));
            int month = Integer.valueOf(valueStr.substring(5, 6)) - 1;
            int day = Integer.valueOf(valueStr.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            return calendar;
        }
        return value;
    }

    private boolean isNormalHierParameter(String parameterName) throws ParameterException {
        if (!this.containsParameter(parameterName)) {
            throw new ParameterException("\u672a\u77e5\u7684\u53c2\u6570[" + parameterName + "]\uff01");
        }
        ParameterModel parameterModel = this.getParameterModel(parameterName);
        return parameterModel.isNormalHierParameter();
    }

    private Object getValueForOuter(String parameterName) throws ParameterException {
        ParameterModel parameterModel = this.getParameterModel(parameterName);
        DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
        if (dataSourceModel == null) {
            if (parameterModel.getDataType().equals((Object)DataType.DOUBLE) || parameterModel.getDataType().equals((Object)DataType.INTEGER)) {
                Object value = this.getValueForInner(parameterName);
                if (value == null || StringUtils.isEmpty((String)value.toString())) {
                    return null;
                }
                return value;
            }
            return this.getValueForInner(parameterName);
        }
        return this.getValueForInner(parameterName);
    }

    private Calendar getValueForDate(String parameterName) throws ParameterException {
        Object innerValue = this.getValueForInner(parameterName);
        if (innerValue == null || StringUtils.isEmpty((String)innerValue.toString())) {
            return null;
        }
        if (innerValue instanceof Calendar) {
            return (Calendar)innerValue;
        }
        if (innerValue instanceof String) {
            String value = (String)this.getValueForInner(parameterName);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = value.contains(";") ? new SimpleDateFormat("yyyy;MM;dd") : (value.contains("-") ? new SimpleDateFormat("yyyy-MM-dd") : new SimpleDateFormat("yyyyMMdd"));
            try {
                calendar.setTime(df.parse(value));
            }
            catch (ParseException e) {
                throw new ParameterException(e);
            }
            return calendar;
        }
        return null;
    }

    private boolean isDateParameter(String parameterName) {
        ParameterModel parameterModel = this.getParameterModel(parameterName);
        return parameterModel.getDataSourceModel() == null && parameterModel.getDataType().equals((Object)DataType.DATETIME);
    }

    private Object getValueForRange(String parameterName) throws ParameterException, DataSetException {
        String parameterName_Min = parameterName + ".MIN";
        String parameterName_Max = parameterName + ".MAX";
        if (this.isDateParameter(parameterName_Min) && this.isDateParameter(parameterName_Max)) {
            Calendar minValue = this.getValueForDate(parameterName_Min);
            Calendar maxValue = this.getValueForDate(parameterName_Max);
            Object[] values = new Object[]{minValue, maxValue};
            return values;
        }
        Object minValue = this.getValueForOuter(parameterName_Min);
        Object maxValue = this.getValueForOuter(parameterName_Max);
        if (minValue instanceof MemoryDataSet) {
            MemoryDataSet _minValue = (MemoryDataSet)minValue;
            MemoryDataSet _maxValue = (MemoryDataSet)maxValue;
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)_minValue.getMetadata());
            if (_minValue.isEmpty()) {
                this.fillNullValue((MemoryDataSet<ParameterColumnInfo>)_minValue);
            }
            if (_maxValue.isEmpty()) {
                this.fillNullValue((MemoryDataSet<ParameterColumnInfo>)_maxValue);
            }
            resultDataSet.add((DataSet)_minValue);
            if (_minValue.getMetadata().getColumnCount() == _maxValue.getMetadata().getColumnCount()) {
                resultDataSet.add((DataSet)_maxValue);
            } else {
                int size = Math.min(_minValue.getMetadata().getColumnCount(), _maxValue.getMetadata().getColumnCount());
                for (DataRow maxRow : _maxValue) {
                    DataRow dataRow = resultDataSet.add();
                    for (int i = 0; i < size; ++i) {
                        dataRow.setValue(i, maxRow.getValue(i));
                    }
                }
            }
            return resultDataSet;
        }
        Object[] values = new Object[]{minValue, maxValue};
        return values;
    }

    private void fillNullValue(MemoryDataSet<ParameterColumnInfo> dataset) {
        int size = dataset.getMetadata().size();
        Object[] nullRow = new Object[size];
        try {
            dataset.add(nullRow);
        }
        catch (DataSetException dataSetException) {
            // empty catch block
        }
    }

    public synchronized Object getValueForInner(String parameterName) throws ParameterException {
        if (this.valuesMap.containsKey(parameterName = parameterName.toUpperCase())) {
            return this.valuesMap.get(parameterName);
        }
        try {
            MemoryDataSet<ParameterColumnInfo> defaultValues;
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                defaultValues = this.getNoneDataSourceDefalutValue(parameterModel);
            } else {
                IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
                defaultValues = dataProvider.filterDefaultValues(parameterModel, this);
            }
            if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                SmartSelector ss = new SmartSelector();
                this.initDefaultValueForSmartSelector(parameterName, ss, defaultValues);
                this.valuesMap.put(parameterName, ss);
                return ss;
            }
            this.valuesMap.put(parameterName, defaultValues);
            return defaultValues;
        }
        catch (Exception e) {
            throw new ParameterException(e);
        }
    }

    private void initDefaultValueForSmartSelector(String parameterName, SmartSelector selector, Object defaultVal) throws ParameterException {
        DataType dataType;
        ParameterModel pmodel = this.getParameterModel(parameterName);
        if (pmodel.getDataSourceModel() == null) {
            dataType = pmodel.getDataType();
            if (dataType == DataType.STRING) {
                selector.setType(SmartSelector.SelectType.FUZZY);
            } else if (dataType != DataType.BOOLEAN) {
                selector.setType(SmartSelector.SelectType.RANGE);
            }
        } else {
            dataType = pmodel.getDataType();
            if (dataType == DataType.STRING || pmodel.getDataSourceModel().getDimType().isTimeDim() || pmodel.getDataSourceModel().getDimType() != null && dataType == DataType.INTEGER) {
                selector.setType(SmartSelector.SelectType.FIXED);
            } else if (dataType != DataType.BOOLEAN) {
                selector.setType(SmartSelector.SelectType.RANGE);
            }
        }
        if (defaultVal == null) {
            return;
        }
        selector.setDefaultValue(defaultVal);
        if (defaultVal instanceof List) {
            List valList = (List)defaultVal;
            for (Object val : valList) {
                SmartSelector.SelectedValue sv = new SmartSelector.SelectedValue(val);
                selector.getSelectedValues().add(sv);
            }
        } else if (defaultVal instanceof MemoryDataSet) {
            MemoryDataSet ds = (MemoryDataSet)defaultVal;
            DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
            DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
            int keyIndex = ds.getMetadata().indexOf(bean.getKeyColName());
            int nameIndex = ds.getMetadata().indexOf(bean.getNameColName());
            for (DataRow row : ds) {
                SmartSelector.SelectedValue sv = new SmartSelector.SelectedValue(row.getValue(keyIndex), nameIndex >= 0 ? row.getString(nameIndex) : null);
                selector.getSelectedValues().add(sv);
            }
        } else {
            SmartSelector.SelectedValue sv = new SmartSelector.SelectedValue(defaultVal);
            selector.getSelectedValues().add(sv);
        }
    }

    private Object getNoneDataSourceDefalutValue(ParameterModel parameterModel) throws SyntaxException {
        String defaultValueFilter = parameterModel.getDefalutValueFilter();
        if (StringUtils.isNotEmpty((String)defaultValueFilter)) {
            FormulaParser parser = FormulaParser.getInstance();
            IUserBindingContext context = new IUserBindingContext(){

                public String getUserID() {
                    return ParameterEngineEnv.this.userGuid;
                }
            };
            IExpression expression = parser.parseEval(defaultValueFilter, (IContext)context);
            return this.parseDateDefaultValue(expression.evaluate((IContext)context), parameterModel);
        }
        return parameterModel.getDefaultValues();
    }

    private Object getNoneDataSourceDefalutMaxValue(ParameterModel parameterModel) throws SyntaxException {
        String defaultMaxValueFilter = parameterModel.getDefaultMaxValueFilter();
        if (StringUtils.isNotEmpty((String)defaultMaxValueFilter)) {
            FormulaParser parser = FormulaParser.getInstance();
            IUserBindingContext context = new IUserBindingContext(){

                public String getUserID() {
                    return ParameterEngineEnv.this.userGuid;
                }
            };
            IExpression expression = parser.parseEval(defaultMaxValueFilter, (IContext)context);
            return this.parseDateDefaultValue(expression.evaluate((IContext)context), parameterModel);
        }
        return parameterModel.getDefaultMaxValues();
    }

    private Object parseDateDefaultValue(Object value, ParameterModel parameterModel) {
        DataType dataType = parameterModel.getDataType();
        if (dataType.equals((Object)DataType.DATETIME)) {
            if (value instanceof String) {
                String valueStr = (String)value;
                return valueStr.replaceAll("-", ";");
            }
            if (value instanceof Calendar) {
                Calendar calendar = (Calendar)value;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd");
                String dateStr = sdf.format(calendar.getTime());
                return dateStr;
            }
            return value;
        }
        return value;
    }

    public Object getChoiceValue(String parameterName, boolean isFirstLevel) throws ParameterException {
        this.readLock.lock();
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                Object var5_6 = null;
                return var5_6;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            if (dataProvider instanceof IDataSourceDataQuickProvider) {
                List dvs;
                MemoryDataSet<ParameterColumnInfo> rs = ((IDataSourceDataQuickProvider)dataProvider).quickGetChoiceValues(parameterModel, this, 501, isFirstLevel);
                if (dataSourceModel.getDimType() != ParameterDimType.TIME_DIM && rs.size() > 500) {
                    rs.remove(500);
                    rs.getMetadata().getProperties().put("hasMore", "true");
                }
                if (parameterModel.getDataSourceFilterMode() == DataSourceFilterMode.APPOINT && (dvs = (List)parameterModel.getDataSourceValues()) != null && dvs.size() > 0) {
                    rs = this.sortByAppointValues(dvs, rs, this.getDataSourceMetaInfo(parameterName));
                }
                MemoryDataSet<ParameterColumnInfo> memoryDataSet = rs;
                return memoryDataSet;
            }
            if (isFirstLevel) {
                MemoryDataSet<ParameterColumnInfo> memoryDataSet = dataProvider.filterChoiceValues(parameterModel, this);
                return memoryDataSet;
            }
            if (dataProvider instanceof IDataSourceDataProviderEx) {
                MemoryDataSet<ParameterColumnInfo> memoryDataSet = ((IDataSourceDataProviderEx)dataProvider).filterAllChoiceValues(parameterModel, this);
                return memoryDataSet;
            }
            MemoryDataSet<ParameterColumnInfo> memoryDataSet = dataProvider.filterChoiceValues(parameterModel, this);
            return memoryDataSet;
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u53ef\u9009\u53d6\u503c\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        finally {
            this.readLock.unlock();
        }
    }

    public Object getAllChoiceValue(String parameterName, boolean isFirstLevel) throws ParameterException {
        this.readLock.lock();
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                Object var5_6 = null;
                return var5_6;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            if (isFirstLevel) {
                MemoryDataSet<ParameterColumnInfo> memoryDataSet = dataProvider.filterChoiceValues(parameterModel, this);
                return memoryDataSet;
            }
            if (dataProvider instanceof IDataSourceDataProviderEx) {
                MemoryDataSet<ParameterColumnInfo> memoryDataSet = ((IDataSourceDataProviderEx)dataProvider).filterAllChoiceValues(parameterModel, this);
                return memoryDataSet;
            }
            MemoryDataSet<ParameterColumnInfo> memoryDataSet = dataProvider.filterChoiceValues(parameterModel, this);
            return memoryDataSet;
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u53ef\u9009\u53d6\u503c\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        finally {
            this.readLock.unlock();
        }
    }

    public String formatValue(String value, DataSourceAttrBean attrBean, String parameterName) throws ParameterException {
        if (!attrBean.getKeyColName().equals(attrBean.getNameColName())) {
            return value;
        }
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return value;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            return dataProvider.formatValue(value, attrBean);
        }
        catch (Exception e) {
            throw new ParameterException("\u683c\u5f0f\u53c2\u6570[" + parameterName + "\u3011\u7684\u53d6\u503c\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public Object fuzzySearch(String parameterName, List<String> values) throws ParameterException {
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return null;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            if (dataProvider instanceof IDataSourceDataQuickProvider) {
                MemoryDataSet<ParameterColumnInfo> rs = ((IDataSourceDataQuickProvider)dataProvider).quickSearch(values, this, 501, false);
                if (dataSourceModel.getDimType() != ParameterDimType.TIME_DIM && rs.size() > 500) {
                    rs.remove(500);
                    rs.getMetadata().getProperties().put("hasMore", "true");
                }
                return rs;
            }
            return dataProvider.fuzzySearch(values, this);
        }
        catch (Exception e) {
            throw new ParameterException(e);
        }
    }

    public Object getChildrenValue(String parameterName, String parentValue, boolean isAllSubLevel, int level) throws ParameterException {
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return null;
            }
            IDataSourceDataProviderEx dataProvider = (IDataSourceDataProviderEx)this.getDataSourceDataProvider(parameterName);
            return dataProvider.filterChildrenValue(parentValue, level, isAllSubLevel, null, this);
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u53ef\u9009\u53d6\u503c\u65f6\u51fa\u9519\uff0c", e);
        }
    }

    public DataSourceMetaInfo getDataSourceMetaInfo(String parameterName) throws ParameterException {
        DataSourceMetaInfo metaInfo = this.metaInfoBuffer.get(parameterName);
        if (metaInfo != null) {
            return metaInfo;
        }
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return null;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            metaInfo = dataProvider.getDataSourceMetaInfo();
            this.metaInfoBuffer.put(parameterName, metaInfo);
            return metaInfo;
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u6570\u636e\u6765\u6e90\u5143\u4fe1\u606f\u65f6\u51fa\u9519\uff0c", e);
        }
    }

    private void reInitDataSourceProvider(String parameterName) throws ParameterException {
        try {
            IDataSourceDataProvider dataProvider = this.dataSourceProviderMap.get(parameterName);
            if (dataProvider == null) {
                return;
            }
            dataProvider.init(this);
        }
        catch (Exception e) {
            throw new ParameterException("\u91cd\u7f6e\u53c2\u6570[" + parameterName + "\u3011\u7684\u6570\u636e\u6765\u6e90\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public IDataSourceDataProvider getDataSourceDataProvider(String parameterName) throws ParameterException {
        IDataSourceDataProvider cachedDataProvider = this.dataSourceProviderMap.get(parameterName);
        if (cachedDataProvider != null) {
            return cachedDataProvider;
        }
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            DataSourceDataFactory dataSourceDataFactory = DataSourceFactoryManager.getDataSourceDataFactory(dataSourceModel.getType());
            IDataSourceDataProvider dataProvider = dataSourceDataFactory.createDataProvider(dataSourceModel);
            dataProvider.init(this);
            this.dataSourceProviderMap.put(parameterName, dataProvider);
            return dataProvider;
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u6570\u636e\u63d0\u4f9b\u5668\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public DataSourceModelFactory getDataSourceModelFactory(ParameterModel parameterModel) throws ParameterException {
        try {
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            return DataSourceFactoryManager.getDataSourceModelFactory(dataSourceModel.getType());
        }
        catch (Exception e) {
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + parameterModel.getTitle() + "\u3011\u7684\u6a21\u578b\u63d0\u4f9b\u5668\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public Object searchValues(String parameterName, List<String> values) throws ParameterException {
        return this.searchValues(parameterName, values, false);
    }

    public Object searchValues(String parameterName, List<String> values, boolean showPath) throws ParameterException {
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return null;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            if (dataProvider instanceof IDataSourceDataQuickProvider) {
                MemoryDataSet<ParameterColumnInfo> rs = ((IDataSourceDataQuickProvider)dataProvider).quickSearch(values, this, 501, showPath);
                if (dataSourceModel.getDimType() != ParameterDimType.TIME_DIM && rs.size() > 500) {
                    rs.remove(500);
                    rs.getMetadata().getProperties().put("hasMore", "true");
                }
                return rs;
            }
            return dataProvider.searchValues(values, true, this);
        }
        catch (Exception e) {
            throw new ParameterException(e);
        }
    }

    public Map<String, Object> submitValue(String parameterName, Object value) throws ParameterException {
        parameterName = parameterName.toUpperCase();
        this.valuesMap.put(parameterName, this.formateSubmitValue(parameterName, value));
        ParamCacheInfo info = this.getParamInfo(parameterName);
        info.modify = true;
        return this.updateCascade(parameterName);
    }

    private Map<String, Object> updateCascade(String parameterName) throws ParameterException {
        HashMap<String, Object> cascadedParameterValue = new HashMap<String, Object>();
        List<String> cascadedParameters = this.cascadeRelation.getCascadedParameters(parameterName);
        if (cascadedParameters == null || cascadedParameters.size() == 0) {
            return cascadedParameterValue;
        }
        List<String> gopology = this.cascadeEngine.computeParameterGopology();
        ArrayList<String> l = new ArrayList<String>();
        for (String s : gopology) {
            if (!cascadedParameters.contains(s)) continue;
            l.add(s);
        }
        for (String cascadedParameter : l) {
            this.reInitDataSourceProvider(cascadedParameter);
            Object cascadedValue = this.getCascadedParameterValues(cascadedParameter);
            cascadedParameterValue.put(cascadedParameter, cascadedValue);
            Map<String, Object> map = this.updateCascade(cascadedParameter);
            if (map.size() <= 0) continue;
            cascadedParameterValue.putAll(map);
        }
        return cascadedParameterValue;
    }

    private Object formateSubmitValue(String parameterName, Object value) throws ParameterException {
        if (value == null) {
            return value;
        }
        ParameterModel parameterModel = this.getParameterModel(parameterName);
        DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
        try {
            if (dataSourceModel != null) {
                if (value instanceof MemoryDataSet) {
                    DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                    DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                    MemoryDataSet dataSet = (MemoryDataSet)value;
                    int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
                    if (keyIndex == -1) {
                        return dataSet;
                    }
                    for (DataRow dataRow : dataSet) {
                        Object keyValue = dataRow.getValue(keyIndex);
                        if (keyValue == null) continue;
                        Object formatKeyValue = DataSourceUtils.format(keyValue.toString(), dataSourceModel.getDataType());
                        dataRow.setValue(keyIndex, formatKeyValue);
                    }
                    return dataSet;
                }
                if (value instanceof SmartSelector) {
                    SmartSelector ss = (SmartSelector)value;
                    Object pval = this.getValue(parameterName);
                    Object val = ((SmartSelector)pval).getDefaultValue();
                    if (val != null && val instanceof MemoryDataSet) {
                        ss.setDefaultValue(val);
                        DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                        DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                        MemoryDataSet dataSet = (MemoryDataSet)val;
                        int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
                        if (keyIndex == -1) {
                            return ss;
                        }
                        for (DataRow dataRow : dataSet) {
                            Object keyValue = dataRow.getValue(keyIndex);
                            if (keyValue == null) continue;
                            Object formatKeyValue = DataSourceUtils.format(keyValue.toString(), dataSourceModel.getDataType());
                            dataRow.setValue(keyIndex, formatKeyValue);
                        }
                    }
                    return value;
                }
                return this.formatValueByDataType(value, dataSourceModel.getDataType());
            }
            if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                return value;
            }
            return this.formatValueByDataType(value, parameterModel.getDataType());
        }
        catch (Exception e) {
            throw new ParameterException(e);
        }
    }

    private Object formatValueByDataType(Object value, DataType dtype) {
        if (dtype == DataType.BOOLEAN && !(value instanceof Boolean)) {
            if (value.toString().equalsIgnoreCase("true")) {
                return true;
            }
            if (value.toString().equalsIgnoreCase("false")) {
                return false;
            }
        } else {
            if (dtype == DataType.INTEGER && !(value instanceof Number)) {
                if (StringUtils.isEmpty((String)value.toString())) {
                    return null;
                }
                Integer v = null;
                try {
                    v = Integer.valueOf(value.toString());
                }
                catch (NumberFormatException e) {
                    v = Double.valueOf(value.toString()).intValue();
                }
                return v;
            }
            if (dtype == DataType.DOUBLE && !(value instanceof Number)) {
                if (StringUtils.isEmpty((String)value.toString())) {
                    return null;
                }
                return Double.valueOf(value.toString());
            }
        }
        return value;
    }

    private Object getCascadedParameterValues(String parameterName) throws ParameterException {
        parameterName = parameterName.toUpperCase();
        ParameterModel parameterModel = this.getParameterModel(parameterName);
        try {
            MemoryDataSet<ParameterColumnInfo> value;
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                Object value2 = parameterModel.getDefaultValues();
                this.valuesMap.put(parameterName, value2);
                return value2;
            }
            IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
            dataProvider.init(this);
            ParamCacheInfo info = this.getParamInfo(parameterName);
            Object pv = this.valuesMap.get(parameterName);
            if (pv != null) {
                if (pv instanceof SmartSelector) {
                    Object v = ((SmartSelector)pv).getFilterValueInMemory(this, parameterName, !info.modify);
                    value = dataProvider.filterValues(v, parameterModel, this);
                } else {
                    value = dataProvider.filterValues(pv, parameterModel, this);
                }
                if (!(info.modify || value != null && value.size() != 0)) {
                    value = dataProvider.filterDefaultValues(parameterModel, this);
                }
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                if (pv instanceof SmartSelector) {
                    SmartSelector ss = (SmartSelector)pv;
                    value = this.sortByAppointValues(ss, value, dataSourceMetaInfo);
                    ss.setDefaultValue(value);
                    ss.updateSelectedValueByParamValue(value);
                    return pv;
                }
                if (dataSourceMetaInfo != null && pv instanceof MemoryDataSet) {
                    value = this.sortByAppointValues((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)pv), value, dataSourceMetaInfo);
                }
                this.valuesMap.put(parameterName, value);
            } else {
                value = dataProvider.filterDefaultValues(parameterModel, this);
                this.valuesMap.put(parameterName, value);
            }
            return value;
        }
        catch (Exception e) {
            throw new ParameterException("\u83b7\u5f97\u53c2\u6570\u7ea7\u8054\u3010" + parameterModel.getTitle() + "\u3011\u53d6\u503c\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    public ParameterModel getParameterModel(String parameterName) {
        if (StringUtils.isEmpty((String)parameterName)) {
            return null;
        }
        if (this.parameterModels == null || this.parameterModels.size() == 0) {
            return null;
        }
        for (ParameterModel parameterModel : this.parameterModels) {
            if (!parameterModel.getName().equalsIgnoreCase(parameterName)) continue;
            return parameterModel;
        }
        return this.orgRangeParameterModels.get(parameterName);
    }

    public List<ParameterModel> getParameterModelsByName(String parameterName) {
        if ((parameterName = parameterName.toUpperCase()).indexOf(";") != -1) {
            String[] paraNames;
            ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
            for (String name : paraNames = parameterName.split(";")) {
                parameterModels.addAll(this.getParaModelsByName(name));
            }
            return parameterModels;
        }
        return this.getParaModelsByName(parameterName);
    }

    private List<ParameterModel> getParaModelsByName(String parameterName) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        if (this.isRangeParameter(parameterName)) {
            parameterModels.add(this.getParameterModel(parameterName + ".MIN"));
            parameterModels.add(this.getParameterModel(parameterName + ".MAX"));
        } else {
            parameterModels.add(this.getParameterModel(parameterName));
        }
        return parameterModels;
    }

    public List<String> getCascadedParameters(String parameterName) {
        return this.cascadeRelation.getCascadedParameters(parameterName);
    }

    public List<String> getCascadeParameters(String parameterName) {
        return this.cascadeRelation.getCascadeParameters(parameterName);
    }

    public ParameterCascadeRelation getCascadeRelation() {
        return this.cascadeRelation;
    }

    public void restore(ParameterEnvSnapShot snapShot) throws ParameterException {
        Map<String, ParamCacheInfo> snapShotCacheInfoMap = snapShot.getCacheInfoMap();
        this.cacheInfoMap.clear();
        this.cacheInfoMap.putAll(snapShotCacheInfoMap);
        Map<String, Object> parameterValueMap = snapShot.getValueMap();
        this.valuesMap.clear();
        List<ParameterModel> pmodels = this.getParameterModels();
        for (ParameterModel pm : pmodels) {
            if (pm.isNeedCascade()) continue;
            Object value = parameterValueMap.get(pm.getName().toUpperCase());
            this.valuesMap.put(pm.getName().toUpperCase(), value);
        }
        Map<String, String> map = snapShot.getParamUnittreeMap();
        this.paramUnittreeMap.clear();
        this.paramUnittreeMap.putAll(map);
        List<String> gopology = this.cascadeEngine.computeParameterGopology();
        for (String parameterName : gopology) {
            DataSourceModel dataSourceModel;
            if (!parameterValueMap.containsKey(parameterName)) continue;
            Object value = parameterValueMap.get(parameterName);
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            if (parameterModel != null && value instanceof MemoryDataSet && ((MemoryDataSet)value).size() > 0 && (dataSourceModel = parameterModel.getDataSourceModel()) != null) {
                MemoryDataSet<ParameterColumnInfo> defaultValues;
                IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
                try {
                    defaultValues = dataProvider.filterValues(value, parameterModel, this);
                }
                catch (DataSourceException e) {
                    throw new ParameterException(e);
                }
                try {
                    defaultValues = this.sortByAppointValues((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)value), defaultValues, this.getDataSourceMetaInfo(parameterName));
                }
                catch (DataSourceException e) {
                    throw new ParameterException(e);
                }
                this.valuesMap.put(parameterName, defaultValues);
                continue;
            }
            this.valuesMap.put(parameterName, value);
        }
    }

    public ParameterEnvSnapShot snapShot() throws ParameterException {
        ParameterEnvSnapShot shot = new ParameterEnvSnapShot(this.valuesMap, this.cacheInfoMap, this.parameterModels);
        shot.getParamUnittreeMap().putAll(this.paramUnittreeMap);
        return shot;
    }

    public List<ParameterModel> getParameterModels() {
        return this.parameterModels;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    private void buildRangeParameter(List<ParameterModel> parameterModels) throws ParameterException {
        ArrayList<ParameterModel> newParameterModels = new ArrayList<ParameterModel>();
        for (ParameterModel parameterModel : parameterModels) {
            if (parameterModel.isRangeParameter() && !this.isClonedParameter(parameterModel.getName())) {
                String paramName = parameterModel.getName().toUpperCase();
                if (!this.orgRangeParameterNames.contains(paramName)) {
                    this.orgRangeParameterNames.add(paramName);
                }
                if (!this.orgRangeParameterModels.containsKey(paramName)) {
                    this.orgRangeParameterModels.put(paramName, parameterModel);
                }
                ParameterModel minParameterModel = parameterModel.clone();
                minParameterModel.setName(parameterModel.getName() + ".MIN");
                minParameterModel.setNeedCascade(true);
                minParameterModel._setRangeCloned(true);
                String maxParameterName = parameterModel.getName() + ".MAX";
                ParameterModel maxParameterModel = parameterModel.simpleClone();
                maxParameterModel.setDataSourceModel(parameterModel.getDataSourceModel());
                maxParameterModel.setName(maxParameterName);
                maxParameterModel.setTitle("\u5230");
                maxParameterModel._setRangeCloned(true);
                maxParameterModel.setDefaultValues(maxParameterModel.getDefaultMaxValues());
                maxParameterModel.setDefalutValueFilter(maxParameterModel.getDefaultMaxValueFilter());
                maxParameterModel.setDefaultValueFilterMode(maxParameterModel.getDefaultMaxValueFilterMode());
                maxParameterModel.setDefaultValueFilterModeExtend(maxParameterModel.getDefaultMaxValueFilterModeExtend());
                maxParameterModel.setNoneDsDefValueFilterMode(maxParameterModel.getNoneDsDefMaxValueFilterMode());
                if (maxParameterModel.getDefaultValues() == null) {
                    Object values;
                    Object defaultValue = parameterModel.getDefaultValues();
                    if (defaultValue instanceof MemoryDataSet) {
                        values = (MemoryDataSet)defaultValue;
                        if (values.size() == 2) {
                            maxParameterModel.setDefalutValueFilter(parameterModel.getDefalutValueFilter());
                            maxParameterModel.setDefaultValueFilterMode(parameterModel.getDefaultValueFilterMode());
                            maxParameterModel.setDefaultValueFilterModeExtend(parameterModel.getDefaultValueFilterModeExtend());
                            maxParameterModel.setNoneDsDefValueFilterMode(parameterModel.getNoneDsDefValueFilterMode());
                            values = (MemoryDataSet)values.clone();
                            values.remove(0);
                            maxParameterModel.setDefaultValues(values.clone());
                        }
                    } else if (defaultValue instanceof List && (values = (List)defaultValue).size() == 2) {
                        maxParameterModel.setDefalutValueFilter(parameterModel.getDefalutValueFilter());
                        maxParameterModel.setDefaultValueFilterMode(parameterModel.getDefaultValueFilterMode());
                        maxParameterModel.setDefaultValueFilterModeExtend(parameterModel.getDefaultValueFilterModeExtend());
                        maxParameterModel.setNoneDsDefValueFilterMode(parameterModel.getNoneDsDefValueFilterMode());
                        ArrayList maxVls = new ArrayList();
                        maxVls.add(values.get(1));
                        maxParameterModel.setDefaultValues(maxVls);
                    }
                }
                parameterModel.setHidden(true);
                parameterModel.setNeedCascade(false);
                newParameterModels.add(parameterModel);
                newParameterModels.add(minParameterModel);
                newParameterModels.add(maxParameterModel);
                continue;
            }
            newParameterModels.add(parameterModel);
        }
        parameterModels.clear();
        parameterModels.addAll(newParameterModels);
    }

    private void buldRangeCascadeParameter(List<ParameterModel> parameterModels) {
        for (ParameterModel parameterModel : parameterModels) {
            if (!parameterModel.isNeedCascade()) continue;
            List<String> cascadeParameters = parameterModel.getCascadeParameters();
            ArrayList<String> cascadeMaxAndMinParameters = new ArrayList<String>();
            ArrayList<String> removeParameters = new ArrayList<String>();
            if (cascadeParameters == null || cascadeParameters.size() == 0) continue;
            for (String cascadeParameter : cascadeParameters) {
                if (!this.isRangeParameter(cascadeParameter)) continue;
                removeParameters.add(cascadeParameter);
                cascadeMaxAndMinParameters.add(cascadeParameter + ".MAX");
                cascadeMaxAndMinParameters.add(cascadeParameter + ".MIN");
            }
            cascadeParameters.removeAll(removeParameters);
            cascadeParameters.addAll(cascadeMaxAndMinParameters);
        }
    }

    private void buildNoneDataSourceParameterDefaultValues(List<ParameterModel> parameterModels) throws ParameterException {
        try {
            for (ParameterModel parameterModel : parameterModels) {
                if (parameterModel.getDataSourceModel() != null) continue;
                parameterModel.setDefaultValues(this.getNoneDataSourceDefalutValue(parameterModel));
                if (!parameterModel.isRangeParameter()) continue;
                parameterModel.setDefaultMaxValues(this.getNoneDataSourceDefalutMaxValue(parameterModel));
            }
        }
        catch (Exception e) {
            throw new ParameterException("\u6784\u9020\u53c2\u6570\u9ed8\u8ba4\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public synchronized String getDimTree(String parameterName) throws ParameterException {
        if (this.paramUnittreeMap.containsKey(parameterName)) {
            return this.paramUnittreeMap.get(parameterName);
        }
        MemoryDataSet<String> hierarchies = this.getChoiceDimTree(parameterName);
        if (hierarchies != null && !hierarchies.isEmpty()) {
            if (this.paramUnittreeMap.get(parameterName) == null) {
                String defaultUnittreeId = hierarchies.get(0).getString(0);
                this.paramUnittreeMap.put(parameterName, defaultUnittreeId);
                return defaultUnittreeId;
            }
        } else {
            this.paramUnittreeMap.put(parameterName, null);
        }
        return null;
    }

    public void setDimTree(String parameterName, String dimTreeCode) throws ParameterException {
        String oldDimTreeCode = this.getDimTree(parameterName = parameterName.toUpperCase());
        if (StringUtils.equals((String)oldDimTreeCode, (String)dimTreeCode)) {
            return;
        }
        MemoryDataSet<String> hierarchies = this.getChoiceDimTree(parameterName);
        if (hierarchies != null && !hierarchies.isEmpty()) {
            Iterator itor = hierarchies.iterator();
            while (itor.hasNext()) {
                String code = ((DataRow)itor.next()).getString(0);
                if (!code.equalsIgnoreCase(dimTreeCode)) continue;
                this.paramUnittreeMap.put(parameterName, dimTreeCode);
                List<?> v = this.getValueAsList(parameterName);
                this.setValue(parameterName, v);
                this.updateCascadedParameterDimTree(parameterName, dimTreeCode);
                return;
            }
        }
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

    public MemoryDataSet<String> getChoiceDimTree(String parameterName) throws ParameterException {
        ParameterModel parameterModel = this.getParameterModel(parameterName = parameterName.toUpperCase());
        if (parameterModel == null) {
            return null;
        }
        DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
        if (dataSourceModel == null) {
            return null;
        }
        IDataSourceDataProvider provider = this.getDataSourceDataProvider(parameterName);
        if (provider instanceof IDataSourceTreeHierarchyProvider) {
            ParameterModel model = this.getParameterModel(parameterName);
            IDataSourceTreeHierarchyProvider thProvider = (IDataSourceTreeHierarchyProvider)((Object)provider);
            try {
                return thProvider.getChoiceTreeHierarchies(model, this);
            }
            catch (DataSourceException e) {
                throw new ParameterException(e);
            }
        }
        return null;
    }

    public void setValue(String parameterName, Object value) throws ParameterException {
        if (value == null || StringUtils.isEmpty((String)parameterName)) {
            return;
        }
        parameterName = parameterName.toUpperCase();
        ParamCacheInfo info = this.getParamInfo(parameterName);
        info.modify = true;
        try {
            if (value instanceof SmartSelector) {
                this.valuesMap.put(parameterName, value);
            } else if (value instanceof List) {
                ParameterModel parameterModel = this.getParameterModel(parameterName);
                List values = (List)value;
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                MemoryDataSet<ParameterColumnInfo> dataSourceValues = null;
                if (dataSourceMetaInfo == null) {
                    dataSourceValues = new MemoryDataSet<ParameterColumnInfo>();
                    Metadata metaData = dataSourceValues.getMetadata();
                    metaData.addColumn(new Column("code", parameterModel.getDataType().value()));
                } else {
                    dataSourceValues = DataSourceUtils.getMemoryDataSet(dataSourceMetaInfo);
                }
                for (Object keyValue : values) {
                    DataRow row = dataSourceValues.add();
                    row.setValue(0, keyValue);
                }
                MemoryDataSet<ParameterColumnInfo> dataSet = null;
                if (dataSourceMetaInfo != null) {
                    IDataSourceDataProvider dataProvider = this.getDataSourceDataProvider(parameterName);
                    dataSet = dataProvider instanceof IDataSourceDataQuickProvider ? ((IDataSourceDataQuickProvider)dataProvider).fillDatasetByKey(dataSourceValues, parameterModel, this) : dataProvider.filterValues(dataSourceValues, parameterModel, this);
                    dataSet = this.sortByAppointValues(dataSourceValues, dataSet, dataSourceMetaInfo);
                } else {
                    dataSet = dataSourceValues;
                }
                Object v = this.valuesMap.get(parameterName);
                if (v instanceof SmartSelector) {
                    ((SmartSelector)v).setDefaultValue(dataSet);
                } else {
                    this.valuesMap.put(parameterName, dataSet);
                }
            } else {
                ParameterModel parameterModel = this.getParameterModel(parameterName);
                if (parameterModel.getDataSourceModel() == null && parameterModel.getDataType().equals((Object)DataType.DATETIME)) {
                    this.valuesMap.put(parameterName, this.formatKeyValue2String(parameterModel, value));
                    return;
                }
                Object v = this.valuesMap.get(parameterName);
                if (v instanceof SmartSelector) {
                    ((SmartSelector)v).setDefaultValue(value);
                } else {
                    this.valuesMap.put(parameterName, value);
                }
            }
        }
        catch (Exception e) {
            throw new ParameterException("\u8bbe\u7f6e\u53c2\u6570\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage(), e.getCause());
        }
    }

    public void setValueAsString(String parameterName, List<String> values) throws ParameterException {
        if (StringUtils.isEmpty((String)parameterName)) {
            return;
        }
        parameterName = parameterName.toUpperCase();
        if (values == null || values.size() == 0) {
            this.setEmptyValue(parameterName);
            return;
        }
        try {
            if (values instanceof List) {
                ParameterModel parameterModel = this.getParameterModel(parameterName);
                if (parameterModel == null) {
                    return;
                }
                if (parameterModel.getDataSourceModel() == null) {
                    this.valuesMap.put(parameterName, this.formateSubmitValue(parameterName, values.get(0)));
                    return;
                }
                if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                    SmartSelector ss = new SmartSelector();
                    this.initDefaultValueForSmartSelector(parameterName, ss, DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfoWithoutInitDataProvider(parameterName)));
                    for (String val : values) {
                        SmartSelector.SelectedValue selectedValue = new SmartSelector.SelectedValue(val);
                        ss.getSelectedValues().add(selectedValue);
                    }
                    this.valuesMap.put(parameterName, ss);
                    return;
                }
                DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfo(parameterName);
                MemoryDataSet dataSourceValues = null;
                if (dataSourceMetaInfo == null) {
                    dataSourceValues = new MemoryDataSet();
                    Metadata metaData = dataSourceValues.getMetadata();
                    metaData.addColumn(new Column("code", parameterModel.getDataType().value()));
                } else {
                    dataSourceValues = DataSourceUtils.getMemoryDataSet(dataSourceMetaInfo);
                }
                if (values != null && values.size() != 0) {
                    for (Object e : values) {
                        DataRow row = dataSourceValues.add();
                        row.setValue(0, this.formateSubmitValue(parameterName, e));
                    }
                }
                MemoryDataSet<ParameterColumnInfo> dataSet = null;
                IDataSourceDataProvider iDataSourceDataProvider = this.getDataSourceDataProvider(parameterName);
                dataSet = iDataSourceDataProvider instanceof IDataSourceDataQuickProvider ? ((IDataSourceDataQuickProvider)iDataSourceDataProvider).fillDatasetByKey((MemoryDataSet<ParameterColumnInfo>)dataSourceValues, parameterModel, this) : iDataSourceDataProvider.filterValues(dataSourceValues, parameterModel, this);
                if (dataSourceMetaInfo != null) {
                    dataSet = this.sortByAppointValues((MemoryDataSet<ParameterColumnInfo>)dataSourceValues, dataSet, dataSourceMetaInfo);
                }
                this.valuesMap.put(parameterName, dataSet);
            } else {
                this.valuesMap.put(parameterName, values);
            }
        }
        catch (Exception e) {
            throw new ParameterException("\u8bbe\u7f6e\u53c2\u6570\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public Map<String, Object> setKeyValueAsStringAndUpdateCascade(String parameterName, List<String> value) throws ParameterException {
        if (this.hasChange(parameterName, value)) {
            this.setValueAsString(parameterName, value);
            ParamCacheInfo info = this.getParamInfo(parameterName);
            info.modify = true;
        }
        return this.updateCascade(parameterName);
    }

    private boolean hasChange(String parameterName, List<String> value) throws ParameterException {
        List<String> oldValue = this.getKeyValueAsString(parameterName);
        if (oldValue.isEmpty() && (value == null || value.isEmpty())) {
            return false;
        }
        if (oldValue.size() == value.size()) {
            for (int i = 0; i < oldValue.size(); ++i) {
                if (StringUtils.equals((String)oldValue.get(i), (String)value.get(i))) continue;
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private void setEmptyValue(String parameterName) throws ParameterException {
        ParameterModel parameterModel = this.getParameterModel(parameterName = parameterName.toUpperCase());
        DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
        if (dataSourceModel == null) {
            this.valuesMap.put(parameterName, null);
        } else if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
            SmartSelector ss = new SmartSelector();
            this.initDefaultValueForSmartSelector(parameterName, ss, DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfoWithoutInitDataProvider(parameterName)));
            this.valuesMap.put(parameterName, ss);
        } else {
            this.valuesMap.put(parameterName, DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfoWithoutInitDataProvider(parameterName)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ParameterEngineEnv clone() {
        this.readLock.lock();
        try {
            HashMap<String, Object> valuesMap = new HashMap<String, Object>();
            valuesMap.putAll(this.valuesMap);
            ParameterEngineEnv cloned = new ParameterEngineEnv(this.parameterModels, this.userGuid, this.cascadeRelation, valuesMap, this.orgRangeParameterNames, this.orgRangeParameterModels);
            cloned.paramUnittreeMap.clear();
            cloned.paramUnittreeMap.putAll(this.paramUnittreeMap);
            cloned.scopes.clear();
            cloned.scopes.putAll(this.scopes);
            cloned.i18nLang = this.i18nLang;
            ParameterEngineEnv parameterEngineEnv = cloned;
            return parameterEngineEnv;
        }
        finally {
            this.readLock.unlock();
        }
    }

    public void addParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        ArrayList<ParameterModel> addedParameterModels = new ArrayList<ParameterModel>();
        for (ParameterModel parameterModel : parameterModels) {
            if (this.containsParameter(parameterModel.getName())) continue;
            addedParameterModels.add(parameterModel);
        }
        this.parameterModels.addAll(addedParameterModels);
        this.buildRangeParameter(this.parameterModels);
        this.cascadeRelation = this.cascadeEngine.buildParameterCascadeRelation();
        this.buildNoneDataSourceParameterDefaultValues(this.parameterModels);
    }

    public void removeParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        ArrayList<ParameterModel> removedParameterModels = new ArrayList<ParameterModel>();
        for (ParameterModel parameterModel : parameterModels) {
            if (!this.containsParameter(parameterModel.getName())) continue;
            removedParameterModels.add(parameterModel);
            String parameterName = parameterModel.getName().toUpperCase();
            this.valuesMap.remove(parameterName);
            this.dataSourceProviderMap.remove(parameterName);
        }
        this.parameterModels.removeAll(removedParameterModels);
        this.removeRangeParameters(removedParameterModels);
        this.buildRangeParameter(this.parameterModels);
        this.cascadeRelation = this.cascadeEngine.buildParameterCascadeRelation();
        this.buildNoneDataSourceParameterDefaultValues(this.parameterModels);
    }

    private void removeRangeParameters(List<ParameterModel> removedParameterModels) {
        for (ParameterModel parameterModel : removedParameterModels) {
            if (!this.orgRangeParameterNames.contains(parameterModel.getName())) continue;
            this.orgRangeParameterNames.remove(parameterModel.getName());
        }
    }

    public boolean containsParameter(String parameterName) {
        if (this.orgRangeParameterNames.contains(parameterName)) {
            return true;
        }
        for (ParameterModel parameterModel : this.parameterModels) {
            if (!parameterModel.getName().equalsIgnoreCase(parameterName)) continue;
            return true;
        }
        return false;
    }

    public List<String> getKeyAndNameCol(String parameterName) throws ParameterException {
        DataSourceMetaInfo dataSourceMetaInfo = this.getDataSourceMetaInfoWithoutInitDataProvider(parameterName);
        ArrayList<String> keyAndNameCol = new ArrayList<String>();
        if (dataSourceMetaInfo != null) {
            List<DataSourceAttrBean> attrBeans = dataSourceMetaInfo.getAttrBeans();
            DataSourceAttrBean attrBean = attrBeans.get(attrBeans.size() - 1);
            keyAndNameCol.add(attrBean.getKeyColName());
            keyAndNameCol.add(attrBean.getNameColName());
        }
        return keyAndNameCol;
    }

    private DataSourceMetaInfo getDataSourceMetaInfoWithoutInitDataProvider(String parameterName) throws ParameterException {
        try {
            ParameterModel parameterModel = this.getParameterModel(parameterName);
            DataSourceModel dataSourceModel = parameterModel.getDataSourceModel();
            if (dataSourceModel == null) {
                return null;
            }
            IDataSourceDataProvider cachedDataProvider = this.dataSourceProviderMap.get(parameterName);
            if (cachedDataProvider != null) {
                return cachedDataProvider.getDataSourceMetaInfo();
            }
            DataSourceDataFactory dataSourceDataFactory = DataSourceFactoryManager.getDataSourceDataFactory(dataSourceModel.getType());
            IDataSourceDataProvider dataProvider = dataSourceDataFactory.createDataProvider(dataSourceModel);
            return dataProvider.getDataSourceMetaInfo();
        }
        catch (Exception e) {
            ParameterModel p = this.getParameterModel(parameterName);
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u3010" + p.getTitle() + "\u3011\u7684\u6570\u636e\u6765\u6e90\u5143\u4fe1\u606f\u65f6\u51fa\u9519\uff0c", e);
        }
    }

    private ParamCacheInfo getParamInfo(String paramName) {
        ParamCacheInfo v = this.cacheInfoMap.get(paramName);
        if (v == null) {
            v = new ParamCacheInfo(null);
            this.cacheInfoMap.put(paramName, v);
        }
        return v;
    }

    public boolean isRangeParameter(String parameterName) {
        parameterName = parameterName.toUpperCase();
        return this.orgRangeParameterNames.contains(parameterName);
    }

    public String getOwner_Name() {
        return this.owner_Name;
    }

    public void setOwner_Name(String ownerName) {
        this.owner_Name = ownerName;
    }

    public String getOwner_Type() {
        return this.owner_Type;
    }

    public void setOwner_Type(String ownerType) {
        this.owner_Type = ownerType;
    }

    private boolean isClonedParameter(String parameterName) {
        if (parameterName.endsWith(".MAX") || parameterName.endsWith(".MIN")) {
            return true;
        }
        for (ParameterModel paraModel : this.parameterModels) {
            if (!paraModel.getName().equalsIgnoreCase(parameterName + ".MAX") && !paraModel.getName().equalsIgnoreCase(parameterName + ".MIN")) continue;
            return true;
        }
        return false;
    }

    public void putQueryProperty(String key, Object value) {
        this.queryProperties.put(key, value);
    }

    public Object getQueryProperty(String key) {
        return this.queryProperties.get(key);
    }

    public void clearQueryProperties() {
        this.queryProperties.clear();
    }

    public void addQueryScope(ParameterScopeType type, String id) {
        List<String> scopeValues = this.scopes.get((Object)type);
        if (scopeValues == null) {
            scopeValues = new ArrayList<String>();
            this.scopes.put(type, scopeValues);
        }
        if (!scopeValues.contains(id)) {
            scopeValues.add(id);
        }
    }

    public Map<ParameterScopeType, List<String>> getQueryScope() {
        return this.scopes;
    }

    public boolean valueCached(String parameterName) {
        if (StringUtils.isEmpty((String)parameterName)) {
            return false;
        }
        ParameterModel parameterModel = this.getParameterModel(parameterName = parameterName.toUpperCase());
        if (parameterModel == null) {
            return false;
        }
        if (parameterModel.isRangeParameter()) {
            boolean hasMax = this.valuesMap.containsKey(parameterName + ".MAX");
            boolean hasMin = this.valuesMap.containsKey(parameterName + ".MIN");
            return hasMax || hasMin;
        }
        return this.valuesMap.containsKey(parameterName);
    }

    public boolean valueModified(String parameterName) {
        if (StringUtils.isEmpty((String)parameterName)) {
            return false;
        }
        ParameterModel parameterModel = this.getParameterModel(parameterName = parameterName.toUpperCase());
        if (parameterModel == null) {
            return false;
        }
        ParamCacheInfo cacheInfo = this.cacheInfoMap.get(parameterName);
        if (cacheInfo == null) {
            return false;
        }
        return cacheInfo.modify;
    }

    public boolean isOrderValues(String parameterName) throws ParameterException {
        boolean orderValues;
        boolean bl = orderValues = this.orderValuesEnable && this.orderValuesMap.containsKey(parameterName) && this.orderValuesMap.get(parameterName) != false;
        if (orderValues) {
            ParameterModel model = this.getParameterModel(parameterName);
            if (model == null) {
                return false;
            }
            DataSourceModel dataSourceModel = model.getDataSourceModel();
            if (dataSourceModel == null) {
                return false;
            }
            return model.getSelectMode() == ParameterSelectMode.MUTIPLE && model.getDataSourceModel().isOrderEnable();
        }
        return false;
    }

    public void setOrderValues(String parameterName, boolean orderValues) throws ParameterException {
        this.orderValuesMap.put(parameterName, orderValues);
    }

    public boolean isOrderValuesEnable() {
        return this.orderValuesEnable;
    }

    public void setOrderValuesEnable(boolean orderValuesEnable) {
        this.orderValuesEnable = orderValuesEnable;
    }

    private MemoryDataSet<ParameterColumnInfo> sortByAppointValues(List<DataSourceValueModel> dataSourceValues, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        if (this.orderValuesEnable) {
            return DataSourceUtils.sortByAppointValues(dataSourceValues, dataSet, dataSourceMetaInfo);
        }
        return dataSet;
    }

    private MemoryDataSet<ParameterColumnInfo> sortByAppointValues(SmartSelector smartSelector, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        if (this.orderValuesEnable) {
            return DataSourceUtils.sortByAppointValues(smartSelector, dataSet, dataSourceMetaInfo);
        }
        return dataSet;
    }

    private MemoryDataSet<ParameterColumnInfo> sortByAppointValues(MemoryDataSet<ParameterColumnInfo> dataSourceValues, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        if (this.orderValuesEnable) {
            return DataSourceUtils.sortByAppointValues(dataSourceValues, dataSet, dataSourceMetaInfo);
        }
        return dataSet;
    }
}

