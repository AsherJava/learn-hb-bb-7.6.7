/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.DSContextFactory
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.IDSFilter
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.manager.PageDataSetReader
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException
 *  com.jiuqi.bi.dataset.model.field.CalcMode
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.sql.SQLModel
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.engine.context.cache;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DSContextFactory;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.manager.PageDataSetReader;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.context.PagingUtils;
import com.jiuqi.bi.quickreport.engine.context.ParamSnapshot;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.ReportNotFoundException;
import com.jiuqi.bi.quickreport.engine.context.cache.DSFilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.cache.DSFilterKey;
import com.jiuqi.bi.quickreport.engine.context.cache.DSPagingInfo;
import com.jiuqi.bi.quickreport.engine.context.cache.DataSetCache;
import com.jiuqi.bi.quickreport.engine.context.filter.ExpressionFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.IDataSetModelProvider;
import com.jiuqi.bi.quickreport.engine.parser.QParserHelper;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.model.DSPageInfo;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DataSetCachePool {
    private QuickReport report;
    private IParameterEnv paramEnv;
    private String userID;
    private String language;
    private IDataSetModelProvider dataSetProvider;
    private IDataSetManager dataSetManager = DataSetManagerFactory.create();
    private IReportListener listener;
    private Map<String, String> dataSrcMap;
    private Function<String, List<IFilterDescriptor>> dataSetFilter;
    private Map<String, Set<String>> refFields;
    private Map<String, Optional<DSModel>> models;
    private Map<String, BIDataSet> cache0 = new TreeMap<String, BIDataSet>();
    private DataSetCache cache1 = new DataSetCache(512);
    private DataSetCache cacheN = new DataSetCache(128);
    private DataSetCache cacheA = new DataSetCache(16);
    private Map<String, BIDataSet> cacheNPT = new HashMap<String, BIDataSet>();
    private Map<ArrayKey, BIDataSet> cacheABT = new HashMap<ArrayKey, BIDataSet>();
    private Set<ArrayKey> primaryKeys = new HashSet<ArrayKey>();
    private Map<String, DSPagingInfo> pagingInfos;
    private List<ParamSnapshot> snapshot;
    private ILogger log;

    public DataSetCachePool() {
        this.dataSrcMap = new HashMap<String, String>();
        this.models = new HashMap<String, Optional<DSModel>>();
        this.pagingInfos = new ArrayMap();
        this.log = ReportLog.openLogger();
    }

    public QuickReport getReport() {
        return this.report;
    }

    public void setReport(QuickReport report) {
        this.report = report;
    }

    public IParameterEnv getParamEnv() {
        return this.paramEnv;
    }

    public void setParamEnv(IParameterEnv paramEnv) {
        this.paramEnv = paramEnv;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public IReportListener getListener() {
        return this.listener;
    }

    public void setListener(IReportListener listener) {
        this.listener = listener;
    }

    public IDataSetModelProvider getDataSetProvider() {
        return this.dataSetProvider;
    }

    public void setDataSetProvider(IDataSetModelProvider dataSetProvider) {
        this.dataSetProvider = dataSetProvider;
    }

    public Map<String, Set<String>> getRefFields() {
        return this.refFields;
    }

    public void setRefFields(Map<String, Set<String>> refFields) {
        this.refFields = refFields;
    }

    public Function<String, List<IFilterDescriptor>> getDataSetFilter() {
        return this.dataSetFilter;
    }

    public void setDataSetFilter(Function<String, List<IFilterDescriptor>> dataSetFilter) {
        this.dataSetFilter = dataSetFilter;
    }

    @Deprecated
    public int getPageNum() {
        if (this.report.getPageInfo().getDSPageInfos().isEmpty()) {
            return -1;
        }
        if (this.report.getPageInfo().getDSPageInfos().size() == 1) {
            DSPagingInfo pi = this.pagingInfos.get(this.report.getPageInfo().getDSPageInfos().get(0).getDatasetName());
            return pi == null ? -1 : pi.getPageNum();
        }
        throw new UnsupportedOperationException("\u5206\u6790\u8868\u542f\u7528\u4e86\u591a\u6570\u636e\u96c6\u5206\u9875\uff0c\u65e0\u6cd5\u83b7\u53d6\u5168\u5c40\u5206\u9875\u9875\u7801");
    }

    @Deprecated
    public void setPageNum(int pageNum) {
        this.setPageNums(pageNum >= 1 ? Collections.singletonMap("@DEFAULT", pageNum) : Collections.emptyMap());
    }

    @Deprecated
    public int getPageSize() throws ReportContextException {
        if (this.report.getPageInfo().getPageMode() != PageMode.DATASET) {
            return 1;
        }
        Map<String, DSPagingInfo> dsPagingInfo = this.getPagingInfos();
        if (dsPagingInfo.isEmpty()) {
            return 1;
        }
        if (dsPagingInfo.size() == 1) {
            DSPagingInfo pi = dsPagingInfo.values().iterator().next();
            return pi.getPageSize();
        }
        throw new ReportContextException("\u5206\u6790\u8868\u542f\u7528\u4e86\u591a\u6570\u636e\u96c6\u5206\u9875\uff0c\u65e0\u6cd5\u83b7\u53d6\u5168\u5c40\u5206\u9875\u9875\u6570");
    }

    @Deprecated
    public int getRecordSize() throws ReportContextException {
        if (this.report.getPageInfo().getPageMode() != PageMode.DATASET) {
            return -1;
        }
        Map<String, DSPagingInfo> dsPagingInfos = this.getPagingInfos();
        if (dsPagingInfos.isEmpty()) {
            return -1;
        }
        if (dsPagingInfos.size() == 1) {
            DSPagingInfo pi = dsPagingInfos.values().iterator().next();
            return pi.getRecordSize();
        }
        throw new ReportContextException("\u5206\u6790\u8868\u542f\u7528\u4e86\u591a\u6570\u636e\u96c6\u5206\u9875\uff0c\u65e0\u6cd5\u83b7\u53d6\u5168\u5c40\u5206\u9875\u8bb0\u5f55\u6570");
    }

    public Map<String, DSPagingInfo> getPagingInfos() throws ReportContextException {
        this.initPageInfos();
        return this.pagingInfos;
    }

    private void initPageInfos() throws ReportContextException {
        if (this.report.getPageInfo().getPageMode() != PageMode.DATASET) {
            return;
        }
        for (DSPageInfo pageInfo : this.report.getPageInfo().getDSPageInfos()) {
            DSPagingInfo pagingInfo = this.openDSPagingInfo(pageInfo);
            if (pagingInfo.getRecordSize() > 0 && pagingInfo.getPageCount() > 0) continue;
            DSModel dsModel = this.openModel(pageInfo.getDatasetName());
            DSContext dsContext = DSContextFactory.create((DSModel)dsModel, (String)this.userID, (IParameterEnv)this.paramEnv);
            dsContext.setI18nLang(this.language);
            PageDataSetReader reader = pagingInfo.openReader(dsModel, dsContext, this.dataSetManager);
            try {
                if (pagingInfo.getPageCount() <= 0) {
                    pagingInfo.setPageCount(reader.getPageCount((IDSContext)dsContext));
                }
                if (pagingInfo.getRecordSize() > 0) continue;
                pagingInfo.setRecordSize(reader.getRecordCount((IDSContext)dsContext));
            }
            catch (BIDataSetException e) {
                throw new ReportContextException(e.getMessage(), e);
            }
        }
    }

    public void setPageNums(Map<String, Integer> pageNums) {
        if (this.report.getPageInfo().getPageMode() != PageMode.DATASET) {
            this.pagingInfos.clear();
            return;
        }
        for (DSPageInfo pageInfo : this.report.getPageInfo().getDSPageInfos()) {
            DSPagingInfo pagingInfo = this.openDSPagingInfo(pageInfo);
            int pageNum = PagingUtils.numOfPage(pageNums, pageInfo.getDatasetName());
            boolean invalided = false;
            if (this.report.getPageInfo().getRowCount() != pagingInfo.getPageSize()) {
                pagingInfo.setPageSize(this.report.getPageInfo().getRowCount());
                pagingInfo.setPageCount(-1);
                invalided = true;
            }
            if (pageNum != pagingInfo.getPageNum()) {
                pagingInfo.setPageNum(pageNum);
                invalided = true;
            }
            if (!invalided) continue;
            this.clearCache(pageInfo.getDatasetName());
        }
    }

    private DSPagingInfo openDSPagingInfo(DSPageInfo pageInfo) {
        return this.pagingInfos.computeIfAbsent(pageInfo.getDatasetName().toUpperCase(), dsName -> {
            DSPagingInfo pagingInfo = new DSPagingInfo((String)dsName, pageInfo.getGroupField());
            pagingInfo.setPageSize(this.report.getPageInfo().getRowCount());
            return pagingInfo;
        });
    }

    public DSPagingInfo findPagingInfo(String dsName) {
        DSPagingInfo dsPagingInfo = this.pagingInfos.get(dsName.toUpperCase());
        if (dsPagingInfo == null) {
            return null;
        }
        return dsPagingInfo.getPageNum() >= 1 ? dsPagingInfo : null;
    }

    public void invalidatePagingInfos(Collection<String> pagingDSNames) {
        Set unpagedDSNames = this.report.getPageInfo().getDSPageInfos().stream().filter(pi -> !pagingDSNames.contains(pi.getDatasetName())).map(DSPageInfo::getDatasetName).collect(Collectors.toSet());
        if (!unpagedDSNames.isEmpty()) {
            this.pagingInfos.keySet().removeAll(unpagedDSNames);
            this.log.warn("\u5206\u6790\u8868" + this.report.getName() + "\u4e2d\u914d\u7f6e\u7684\u5206\u9875\u6570\u636e\u96c6" + unpagedDSNames + "\u672a\u5728\u4e3b\u9875\u7b7e\u7684\u6d6e\u52a8\u533a\u57df\u4e2d\u4f7f\u7528\uff0c\u65e0\u6cd5\u53c2\u4e0e\u5206\u9875");
        }
    }

    public Set<ArrayKey> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public DSModel openModel(String dsName) throws ReportContextException, ReportNotFoundException {
        Optional<DSModel> option = this.models.get(dsName.toUpperCase());
        if (option == null) {
            DSModel model;
            try {
                model = this.dataSetProvider.findModel(dsName);
            }
            catch (ReportExpressionException e) {
                throw new ReportContextException(e);
            }
            option = Optional.ofNullable(model);
            this.models.put(dsName.toUpperCase(), option);
        }
        if (!option.isPresent()) {
            throw new ReportNotFoundException("\u67e5\u627e\u6570\u636e\u96c6\u4e0d\u5b58\u5728\uff1a" + dsName);
        }
        return option.get();
    }

    public BIDataSet open(String dsName) throws ReportContextException {
        return this.openCache0(dsName);
    }

    public BIDataSet open(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        switch (filters.size()) {
            case 0: {
                return this.openCache0(dsName);
            }
            case 1: {
                return this.openCache1(dsName, filters);
            }
        }
        return this.openCacheN(dsName, filters);
    }

    public BIDataSet openCacheA(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        DSFilterKey key = new DSFilterKey(dsName, filters);
        BIDataSet dataset = this.cacheA.get(key);
        if (dataset == null) {
            dataset = this.createDataSetA(dsName, filters);
            this.cacheA.put(key, dataset);
        }
        return dataset;
    }

    public BIDataSet openNonParent(String dsName) throws ReportContextException {
        String dataSetName = dsName.toUpperCase();
        BIDataSet dataSet = this.cacheNPT.get(dataSetName);
        if (dataSet != null) {
            return dataSet;
        }
        dataSet = this.createDataSetNPT(dataSetName);
        this.cacheNPT.put(dataSetName, dataSet);
        return dataSet;
    }

    public BIDataSet aggregateByTree(String dataDSName, List<String> dimDSNames) throws ReportContextException {
        ArrayList<String> keyList = new ArrayList<String>(1 + dimDSNames.size());
        keyList.add(dataDSName);
        keyList.addAll(dimDSNames);
        ArrayKey key = ArrayKey.of(keyList);
        BIDataSet dataset = this.cacheABT.get(key);
        if (dataset != null) {
            return dataset;
        }
        dataset = this.open(dataDSName);
        for (String dimDSName : dimDSNames) {
            BIDataSet dimDS = this.open(dimDSName);
            try {
                dataset = dataset.aggregateByTree(dimDS, null);
            }
            catch (BIDataSetException e) {
                throw new ReportContextException(e);
            }
        }
        this.cacheABT.put(key, dataset);
        return dataset;
    }

    public MemoryDataSet<BIDataSetFieldInfo> distinct(DSModel dsModel, List<String> fieldNames, List<IFilterDescriptor> filters) throws ReportContextException {
        if (fieldNames.isEmpty()) {
            throw new ReportContextException("\u672a\u6307\u5b9a\u6570\u636e\u96c6\u7684\u53bb\u91cd\u5b57\u6bb5");
        }
        DSContext dsCntx = this.createDSContext(dsModel);
        for (IFilterDescriptor filter : filters) {
            dsCntx.addFilterItem(filter.toFilter());
        }
        if (fieldNames.size() == 1) {
            try {
                return this.dataSetManager.distinct((IDSContext)dsCntx, dsModel, fieldNames.get(0));
            }
            catch (BIDataSetException | DataSetTypeNotFoundException e) {
                throw new ReportContextException(e);
            }
        }
        try {
            BIDataSet dataSet = this.dataSetManager.open((IDSContext)dsCntx, dsModel);
            return this.distinctBy(dataSet, fieldNames);
        }
        catch (BIDataSetException | DataSetTypeNotFoundException e) {
            throw new ReportContextException(e);
        }
    }

    private MemoryDataSet<BIDataSetFieldInfo> distinctBy(BIDataSet dataSet, List<String> fieldNames) throws BIDataSetException, ReportContextException {
        final int keyIndex = dataSet.getMetadata().indexOf(fieldNames.get(0));
        BIDataSet filterDS = dataSet.filter(new IDSFilter(){
            private Set<Object> keys = new HashSet<Object>();

            public boolean judge(DataRow row) throws BIDataSetException {
                Object value = row.getValue(keyIndex);
                return this.keys.add(value);
            }
        });
        MemoryDataSet result = new MemoryDataSet();
        ArrayList<Function<BIDataRow, Object>> readers = new ArrayList<Function<BIDataRow, Object>>(fieldNames.size());
        for (String fieldName : fieldNames) {
            Column column = filterDS.getMetadata().find(fieldName);
            if (column == null) {
                throw new ReportContextException("\u67e5\u8be2\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
            }
            result.getMetadata().addColumn(column.clone());
            int index = column.getIndex();
            readers.add(row -> row.getValue(index));
        }
        for (BIDataRow dataRow : filterDS) {
            DataRow row2 = result.add();
            for (int i = 0; i < readers.size(); ++i) {
                Object value = ((Function)readers.get(i)).apply(dataRow);
                row2.setValue(i, value);
            }
            try {
                row2.commit();
            }
            catch (DataSetException e) {
                throw new ReportContextException(e);
            }
        }
        return result;
    }

    public void clearCache(String dataSetName) {
        String dsName = dataSetName.toUpperCase();
        this.cache0.remove(dsName);
        this.cache1.remove(dsName);
        this.cacheN.remove(dsName);
        this.cacheA.remove(dsName);
        this.cacheNPT.remove(dsName);
        this.dataSrcMap.remove(dsName);
        DSPagingInfo pagingInfo = this.pagingInfos.get(dsName);
        if (pagingInfo != null) {
            pagingInfo.clear();
        }
    }

    public void clearCache() {
        this.cache0.clear();
        this.cache1.clear();
        this.cacheN.clear();
        this.cacheA.clear();
        this.cacheNPT.clear();
        this.dataSrcMap.clear();
        this.pagingInfos.values().forEach(DSPagingInfo::clear);
    }

    public void snapshot() throws ReportContextException {
        HashMap<String, ParamSnapshot> finder = new HashMap<String, ParamSnapshot>();
        for (String dataSetName : this.cache0.keySet()) {
            this.snapshotDataSet(dataSetName, finder);
        }
        this.snapshot = new ArrayList(finder.values());
    }

    public boolean refresh() throws ReportContextException {
        if (this.snapshot == null) {
            this.clearCache();
            return true;
        }
        return this.invalidateCache();
    }

    public void resetDataSource(String dsName, String dataSrcName) {
        this.dataSrcMap.put(dsName.toUpperCase(), dataSrcName);
    }

    private BIDataSet openCache0(String dsName) throws ReportContextException {
        BIDataSet dataset = this.cache0.get(dsName = dsName.toUpperCase());
        if (dataset == null) {
            this.log.debug("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\u96c6" + dsName + "...");
            dataset = this.createDataSet0(dsName);
            this.cache0.put(dsName.toUpperCase(), dataset);
            this.log.debug("\u6570\u636e\u96c6" + dsName + "\u52a0\u8f7d\u5b8c\u6210\u3002");
            this.dumpDataSet(dataset, dsName);
        }
        return dataset;
    }

    private BIDataSet openCache1(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        DSFilterKey key = new DSFilterKey(dsName, filters);
        BIDataSet dataset = this.cache1.get(key);
        if (dataset == null) {
            dataset = this.createDataSet1(dsName, filters);
            this.cache1.put(key, dataset);
        }
        return dataset;
    }

    private BIDataSet openCacheN(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        DSFilterKey key = new DSFilterKey(dsName, filters);
        BIDataSet dataset = this.cacheN.get(key);
        if (dataset == null) {
            dataset = this.createDataSetN(dsName, filters);
            this.cacheN.put(key, dataset);
        }
        return dataset;
    }

    private BIDataSet createDataSet0(String dsName) throws ReportContextException {
        BIDataSet dataSet = null;
        if (this.listener != null) {
            try {
                dataSet = this.listener.openDataSet(dsName);
            }
            catch (ReportEngineException e) {
                throw new ReportContextException(e);
            }
        }
        if (dataSet == null) {
            DSModel dsModel = this.openModel(dsName);
            this.resetDataSource(dsModel);
            DSContext dsContext = this.createDSContext(dsModel);
            try {
                DSPagingInfo pagingInfo = this.findPagingInfo(dsName);
                if (pagingInfo == null) {
                    dataSet = this.dataSetManager.open((IDSContext)dsContext, dsModel);
                } else {
                    PageDataSetReader reader = pagingInfo.openReader(dsModel, dsContext, this.dataSetManager);
                    if (pagingInfo.getPageCount() <= 0) {
                        pagingInfo.setPageCount(reader.getPageCount((IDSContext)dsContext));
                    }
                    if (pagingInfo.getRecordSize() <= 0) {
                        pagingInfo.setRecordSize(reader.getRecordCount((IDSContext)dsContext));
                    }
                    dataSet = reader.open(pagingInfo.getPageNum(), (IDSContext)dsContext);
                }
            }
            catch (BIDataSetException e) {
                throw new ReportContextException(e.getMessage(), e);
            }
            catch (DataSetTypeNotFoundException e) {
                throw new ReportContextException(e.getMessage(), e);
            }
        }
        return this.calcDataSet(dsName, dataSet);
    }

    private DSContext createDSContext(DSModel dsModel) throws ReportContextException {
        List<IFilterDescriptor> filters;
        DSContext dsContext = DSContextFactory.create((DSModel)dsModel, (String)this.userID, (IParameterEnv)this.paramEnv);
        dsContext.setI18nLang(this.language);
        if (this.dataSetFilter != null && (filters = this.dataSetFilter.apply(dsModel.getName())) != null) {
            for (IFilterDescriptor filter : filters) {
                dsContext.addFilterItem(filter.toFilter());
            }
        }
        return dsContext;
    }

    private BIDataSet createDataSet1(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        BIDataSet dataset = this.openCache0(dsName);
        return this.filterDataSet(dsName, dataset, filters);
    }

    private BIDataSet createDataSetN(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        IFilterDescriptor primaryFilter = null;
        BIDataSet dataset = null;
        boolean timeRelated = this.isTimeRelated(filters);
        for (IFilterDescriptor filter : filters) {
            if (!dsName.equalsIgnoreCase(filter.getDataSetName()) || filter instanceof MappingFilterDescriptor || timeRelated && filter.getField() != null && filter.getField().getFieldType() == FieldType.TIME_DIM) continue;
            if (this.primaryKeys.isEmpty()) {
                DSFilterKey subKey = new DSFilterKey(dsName, filter);
                dataset = this.cache1.get(subKey);
                if (dataset == null) continue;
                primaryFilter = filter;
                break;
            }
            if (!this.isPrimaryFilter(filter)) continue;
            primaryFilter = filter;
            break;
        }
        if (dataset == null) {
            if (primaryFilter == null) {
                dataset = this.openCache0(dsName);
            } else {
                ArrayList<IFilterDescriptor> subFilters = new ArrayList<IFilterDescriptor>(1);
                subFilters.add(primaryFilter);
                dataset = this.openCache1(dsName, subFilters);
            }
        }
        List<IFilterDescriptor> newFilters = this.eliminateFilter(filters, primaryFilter);
        return this.filterDataSet(dsName, dataset, newFilters);
    }

    private List<IFilterDescriptor> eliminateFilter(List<IFilterDescriptor> filters, IFilterDescriptor filter) {
        ArrayList<IFilterDescriptor> newFilters;
        if (filter == null) {
            newFilters = new ArrayList<IFilterDescriptor>(filters);
        } else {
            newFilters = new ArrayList(filters.size() - 1);
            for (IFilterDescriptor curFilter : filters) {
                if (curFilter == filter) continue;
                newFilters.add(curFilter);
            }
        }
        return newFilters;
    }

    private boolean isPrimaryFilter(IFilterDescriptor filter) {
        return this.primaryKeys.contains(ArrayKey.of((Object[])new Object[]{filter.getDataSetName(), filter.getFieldName()}));
    }

    private BIDataSet createDataSetA(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
        BIDataSet rawDataSet = this.openCache0(dsName);
        BIDataSet aggrDataSet = this.aggrDataSet(dsName, rawDataSet, filters);
        return this.filterDataSet(dsName, aggrDataSet, filters);
    }

    private BIDataSet createDataSetNPT(String dataSetName) throws ReportContextException {
        BIDataSet dataSet = this.openCache0(dataSetName);
        DSModel model = this.openModel(dataSetName);
        try {
            return QParserHelper.removeParentFields(dataSet, model);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
    }

    private BIDataSet filterDataSet(String dsName, BIDataSet dataset, List<IFilterDescriptor> filters) throws ReportContextException {
        BIDataSet filterDS;
        DSFilterAnalyzer analyser = new DSFilterAnalyzer(dsName){

            @Override
            protected BIDataSet openDataSet(String dsName, List<IFilterDescriptor> filters) throws ReportContextException {
                return DataSetCachePool.this.open(dsName, filters);
            }
        };
        analyser.getFilters().addAll(filters);
        List<FilterItem> dsFilters = analyser.analyse();
        try {
            filterDS = dataset.filter(dsFilters);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
        IDSFilter filter = analyser.createFilter(filterDS);
        if (filter == null) {
            return filterDS;
        }
        try {
            return filterDS.filter(filter);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
    }

    private BIDataSet calcDataSet(String dsName, BIDataSet dataSet) throws ReportContextException {
        Set<String> fields = this.refFields.get(dsName.toUpperCase());
        if (fields == null) {
            return dataSet;
        }
        ArrayList<Integer> colIndexes = new ArrayList<Integer>();
        for (String fieldName : fields) {
            Column col = dataSet.getMetadata().find(fieldName);
            if (col == null) {
                throw new ReportContextException("\u67e5\u627e\u6570\u636e\u96c6\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + dsName + "." + fieldName);
            }
            if (!((BIDataSetFieldInfo)col.getInfo()).isCalcField() || ((BIDataSetFieldInfo)col.getInfo()).getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
            colIndexes.add(col.getIndex());
        }
        if (colIndexes.isEmpty()) {
            return dataSet;
        }
        try {
            dataSet.compute(colIndexes);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
        return dataSet;
    }

    private BIDataSet aggrDataSet(String dsName, BIDataSet dataSet, List<IFilterDescriptor> filters) throws ReportContextException {
        BIDataSet aggrDataSet;
        ArrayList<String> dimFields = new ArrayList<String>();
        ArrayList<String> measureFields = new ArrayList<String>();
        this.scanFilters(dataSet, filters, dimFields, measureFields);
        this.scanMeasures(dsName, dataSet, measureFields);
        try {
            aggrDataSet = dataSet.aggregate(dimFields, measureFields, true);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
        try {
            aggrDataSet.compute(null);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e);
        }
        return aggrDataSet;
    }

    private void resetDataSource(DSModel dsModel) {
        if (!(dsModel instanceof SQLModel)) {
            return;
        }
        String dataSource = this.dataSrcMap.get(dsModel.getName().toUpperCase());
        if (!StringUtils.isEmpty((String)dataSource)) {
            ((SQLModel)dsModel).setDataSourceId(dataSource);
        }
    }

    private void scanFilters(BIDataSet dataSet, List<IFilterDescriptor> filters, List<String> dimFields, List<String> measureFields) {
        HashSet<String> filterFields = new HashSet<String>();
        for (IFilterDescriptor filter : filters) {
            filter.getRefFields(filterFields);
        }
        for (String fieldName : filterFields) {
            Column column = dataSet.getMetadata().find(fieldName);
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.MEASURE) {
                measureFields.add(column.getName());
                continue;
            }
            dimFields.add(column.getName());
        }
    }

    private void scanMeasures(String dsName, BIDataSet dataSet, List<String> measureFields) {
        Set<String> fields = this.refFields.get(dsName.toUpperCase());
        for (String fieldName : fields) {
            Column column = dataSet.getMetadata().find(fieldName);
            if (!((BIDataSetFieldInfo)column.getInfo()).isCalcField() || ((BIDataSetFieldInfo)column.getInfo()).getCalcMode() != CalcMode.AGGR_THEN_CALC || measureFields.contains(column.getName())) continue;
            measureFields.add(column.getName());
        }
    }

    private boolean isTimeRelated(List<IFilterDescriptor> filters) {
        for (IFilterDescriptor filter : filters) {
            if (filter.getField() == null) {
                return true;
            }
            if (filter.getField() instanceof DSCalcField && ((DSCalcField)filter.getField()).getCalcMode() == CalcMode.AGGR_THEN_CALC) {
                return true;
            }
            if (!(filter instanceof ExpressionFilterDescriptor)) continue;
            return true;
        }
        return false;
    }

    private void snapshotDataSet(String dataSetName, Map<String, ParamSnapshot> finder) throws ReportContextException {
        DSModel dataSet = this.openModel(dataSetName);
        for (ParameterModel param : dataSet.getParameterModels()) {
            if (this.paramEnv.getParameterModelByName(param.getName()) == null) continue;
            ParamSnapshot paramSnap = finder.get(param.getName());
            if (paramSnap == null) {
                String dimTree;
                List value;
                try {
                    value = this.paramEnv.getValueAsList(param.getName());
                    dimTree = this.paramEnv.getDimTree(param.getName());
                }
                catch (ParameterException e) {
                    throw new ReportContextException(e);
                }
                paramSnap = new ParamSnapshot(param.getName(), value, dimTree);
                finder.put(param.getName(), paramSnap);
            }
            paramSnap.getAffects().add(dataSetName);
        }
    }

    private boolean invalidateCache() throws ReportContextException {
        boolean changed = false;
        for (ParamSnapshot paramSnap : this.snapshot) {
            String dimTree;
            List value;
            try {
                value = this.paramEnv.getValueAsList(paramSnap.getParamName());
                dimTree = this.paramEnv.getDimTree(paramSnap.getParamName());
            }
            catch (ParameterException e) {
                throw new ReportContextException(e);
            }
            if (paramSnap.equalsValue(value, dimTree)) continue;
            for (String dataSetName : paramSnap.getAffects()) {
                this.clearCache(dataSetName);
            }
            changed = true;
        }
        return changed;
    }

    private void dumpDataSet(BIDataSet dataset, String dsName) {
        if (!this.report.getDebugConfig().isEnabled() || StringUtils.isEmpty((String)this.report.getDebugConfig().getDumpDir())) {
            return;
        }
        String fileName = this.report.getDebugConfig().getDumpDir() + "/" + this.report.getName() + "." + dsName + ".CSV";
        try {
            dataset.dump(fileName);
            this.log.info("\u5df2\u7ecf\u5c06\u6570\u636e\u96c6\u8f93\u51fa\u5230\u65e5\u5fd7\u6587\u4ef6\uff1a" + fileName);
        }
        catch (IOException e) {
            this.log.error("\u5c06\u6570\u636e\u96c6\u8f93\u51fa\u5230\u65e5\u5fd7\u6587\u4ef6[" + fileName + "]\u65f6\u51fa\u9519\u3002", (Throwable)e);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Cache-0: ");
        this.toString(buffer, this.cache0).append(StringUtils.LINE_SEPARATOR).append("Cache-1: ").append(this.cache1).append(StringUtils.LINE_SEPARATOR).append("Cache-N: ").append(this.cacheN).append(StringUtils.LINE_SEPARATOR).append("Cache-A: ").append(this.cacheA).append(StringUtils.LINE_SEPARATOR);
        if (!this.cacheNPT.isEmpty()) {
            buffer.append("Cache-NPT: ");
            this.toString(buffer, this.cacheNPT);
        }
        return buffer.toString();
    }

    private StringBuilder toString(StringBuilder buffer, Map<String, BIDataSet> dataSets) {
        buffer.append('[');
        Iterator<Map.Entry<String, BIDataSet>> i = dataSets.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, BIDataSet> entry = i.next();
            buffer.append(entry.getKey()).append('=').append(entry.getValue().getRecordCount());
            if (!i.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append(']');
        return buffer;
    }
}

