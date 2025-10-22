/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataContext;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataRowFilter;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataSetHelper;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CKDValidateCollector;
import com.jiuqi.nr.data.logic.internal.obj.CKRPackageInfo;
import com.jiuqi.nr.data.logic.internal.obj.CheckQueryContext;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.provider.impl.LinkEnvProviderImpl;
import com.jiuqi.nr.data.logic.internal.query.AllCheckResultDesJoinProvider;
import com.jiuqi.nr.data.logic.internal.query.CheckResultSqlBuilder;
import com.jiuqi.nr.data.logic.internal.query.SqlModel;
import com.jiuqi.nr.data.logic.internal.query.parse.CheckFilterNodeFinder;
import com.jiuqi.nr.data.logic.internal.util.CheckResultDataUtil;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class CheckResultServiceImpl
implements ICheckResultService {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultServiceImpl.class);
    private static final String MSG_SUCCESS = "success";
    @Autowired
    protected ParamUtil paramUtil;
    @Autowired
    protected FormulaParseUtil formulaParseUtil;
    @Autowired
    protected SplitCheckTableHelper splitTableHelper;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    protected INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    protected DataEngineAdapter dataEngineAdapter;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected CheckResultDataUtil checkResultDataUtil;
    @Autowired
    protected MemoryDataSetHelper memoryDataSetHelper;
    @Autowired
    protected ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    protected EntityUtil entityUtil;
    @Autowired
    protected DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IFMDMAttributeService fmdmAttributeService;
    @Autowired
    protected List<ICheckDesValidatorProvider> checkDesValidatorProviders;

    @Override
    public CheckResult queryAllCheckResult(CheckResultQueryParam checkResultQueryParam, String operationId) {
        CheckQueryContext checkQueryContext;
        if (StringUtils.isNotEmpty((String)operationId)) {
            checkResultQueryParam.setBatchId(operationId);
        }
        if (StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CheckResult checkResult = new CheckResult();
        checkResult.setMessage(MSG_SUCCESS);
        try {
            checkQueryContext = this.checkResultDataUtil.initCheckQueryContext(checkResultQueryParam);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage(), e);
            checkResult.setMessage(e.getMessage());
            return checkResult;
        }
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        String ckrTableName = this.splitTableHelper.getSplitAllCKRTableName(formScheme);
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(ckrTableName);
        if (table == null) {
            logger.error(ckrTableName + ExceptionEnum.TABLE_MODEL_EXC.getDesc());
            checkResult.setMessage(ckrTableName + ExceptionEnum.TABLE_MODEL_EXC.getDesc());
            return checkResult;
        }
        checkQueryContext.setCkrTable(table);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        checkQueryContext.setCkrDimChanger(dimensionChanger);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine ckdTable = this.dataModelService.getTableModelDefineByCode(ckdTableName);
        if (ckdTable == null) {
            logger.error(ckdTableName + ExceptionEnum.TABLE_MODEL_EXC.getDesc());
            checkResult.setMessage(ckdTableName + ExceptionEnum.TABLE_MODEL_EXC.getDesc());
            return checkResult;
        }
        checkQueryContext.setCkdTable(ckdTable);
        DimensionChanger dimensionChanger2 = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
        checkQueryContext.setCkdDimChanger(dimensionChanger2);
        EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        checkQueryContext.setDwEntity(dwEntity);
        List<EntityData> dwDimEntities = this.entityUtil.getDwDimEntities(formScheme);
        Map<String, IDimDataLoader> entityLoaderMap = this.entityUtil.getDimDataLoaders(formScheme, checkQueryContext.getMasterKey(), dwDimEntities);
        checkQueryContext.setEntityLoaderMap(entityLoaderMap);
        ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(formScheme.getKey());
        checkQueryContext.setExecutorContext(executorContext);
        CheckResult allCheckResultMeo = this.getAllCheckResultMeo(checkQueryContext);
        return allCheckResultMeo == null ? this.getAllCheckResultDB(checkQueryContext) : allCheckResultMeo;
    }

    protected CheckResult getAllCheckResultMeo(CheckQueryContext checkQueryContext) {
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
        DimensionCollection dimensionCollection = checkResultQueryParam.getDimensionCollection();
        List<String> cacheKeyList = this.memoryDataSetHelper.listCacheKey(dimensionCollection, formulaSchemeKeys);
        if (CollectionUtils.isEmpty(cacheKeyList)) {
            CheckResult checkResult = new CheckResult();
            checkResult.setMessage("\u7ef4\u5ea6\u7ec4\u5408\u548c\u516c\u5f0f\u65b9\u6848\u5b58\u5728\u7a7a\u503c\uff0c\u65e0\u6cd5\u83b7\u53d6\u5ba1\u6838\u7ed3\u679c\u7f13\u5b58\u7d22\u5f15");
            return checkResult;
        }
        Metadata metadata = this.memoryDataSetHelper.findAnyDataSet(cacheKeyList).getMetadata();
        TableModelDefine ckrTable = checkQueryContext.getCkrTable();
        EntityData dwEntity = checkQueryContext.getDwEntity();
        MemoryDataContext memoryDataContext = new MemoryDataContext((Metadata<ColumnInfo>)metadata, ckrTable.getName(), dwEntity.getTableName());
        MemoryDataRowFilter dataRowFilter = CheckResultServiceImpl.getMemoryDataRowFilter(memoryDataContext, checkResultQueryParam, (Metadata<ColumnInfo>)metadata);
        DataSet<ColumnInfo> dataSet = null;
        for (String cacheKey : cacheKeyList) {
            DataSet<ColumnInfo> dataRows = this.memoryDataSetHelper.readData(cacheKey, dataRowFilter);
            try {
                if (dataSet == null) {
                    dataSet = dataRows;
                    continue;
                }
                if (dataRows == null) continue;
                dataSet.add(dataRows);
            }
            catch (DataSetException e) {
                logger.error("\u5168\u5ba1\u7ed3\u679c\u5185\u5b58\u6570\u636e\u5408\u5e76\u5f02\u5e38:" + e.getMessage(), e);
            }
        }
        List<String> dimColNames = this.getDimColNames(ckrTable);
        List<FmlCheckResultEntity> result = this.memoryDataSetHelper.getCheckResultEntity(dataSet, dimColNames);
        if (result != null && !result.isEmpty()) {
            QueryContext queryContext = null;
            try {
                queryContext = new QueryContext(checkQueryContext.getExecutorContext(), null);
            }
            catch (ParseException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
            CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
            checkDesQueryParam.setFormulaSchemeKey(formulaSchemeKeys);
            checkDesQueryParam.setDimensionCollection(dimensionCollection);
            List<CheckDesObj> checkDesObjs = this.checkErrorDescriptionService.queryFormulaCheckDes(checkDesQueryParam);
            result = CheckResultUtil.orderCheckResult(result);
            HashMap<String, String> colDimNameMap = new HashMap<String, String>();
            FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
            colDimNameMap.put("MDCODE", dwEntity.getDimensionName());
            colDimNameMap.put("PERIOD", this.entityUtil.getPeriodEntity(formScheme.getDateTime()).getDimensionName());
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            CKRPackageInfo ckrPackageInfo = new CKRPackageInfo(queryContext, formulaShowInfo, checkQueryContext.getEntityLoaderMap(), colDimNameMap, checkResultQueryParam.getCheckTypes(), checkResultQueryParam.getQueryCondition(), formScheme.getKey(), ckdValCollectorCache);
            return CheckResultUtil.packageCheckResult(result, checkDesObjs, ckrPackageInfo);
        }
        return null;
    }

    @NotNull
    private List<String> getDimColNames(TableModelDefine ckrTable) {
        String[] bizKeys = ckrTable.getBizKeys().split(";");
        ArrayList<String> dimColNames = new ArrayList<String>();
        for (String bizKey : bizKeys) {
            String bizKeyCode = this.dataModelService.getColumnModelDefineByID(bizKey).getCode();
            if ("ALLCKR_ASYNCTASKID".equals(bizKeyCode)) continue;
            dimColNames.add(bizKeyCode);
        }
        return dimColNames;
    }

    @NotNull
    private static MemoryDataRowFilter getMemoryDataRowFilter(MemoryDataContext memoryDataContext, CheckResultQueryParam checkResultQueryParam, Metadata<ColumnInfo> metadata) {
        MemoryDataRowFilter dataRowFilter = new MemoryDataRowFilter(memoryDataContext);
        String condition = checkResultQueryParam.getFilterCondition();
        List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
        if (!CollectionUtils.isEmpty(rangeKeys)) {
            int index = -1;
            if (checkResultQueryParam.getMode() == Mode.FORM) {
                index = metadata.indexOf("ALLCKR_FORMKEY");
            } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                index = metadata.indexOf("ALLCKR_FORMULAID");
            }
            if (index != -1) {
                dataRowFilter.addValuesFilter(index, rangeKeys);
            }
        }
        if (StringUtils.isNotEmpty((String)condition)) {
            dataRowFilter.appendFilter(condition);
        }
        return dataRowFilter;
    }

    protected CheckResult getAllCheckResultDB(CheckQueryContext checkQueryContext) {
        NvwaQueryModel ckrQueryModel = this.checkResultDataUtil.getAllCKRQueryModel(checkQueryContext);
        return this.getAllCheckResultDB(checkQueryContext, ckrQueryModel);
    }

    protected CheckResult getAllCheckResultDB(CheckQueryContext checkQueryContext, NvwaQueryModel ckrQueryModel) {
        int totalCount;
        INvwaDataSet dataRows;
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        int start = -1;
        int end = -1;
        if (checkResultQueryParam.getPagerInfo() != null) {
            int offset = checkResultQueryParam.getPagerInfo().getOffset();
            int limit = checkResultQueryParam.getPagerInfo().getLimit();
            start = offset * limit;
            end = start + limit;
        }
        EntityData dwEntity = checkQueryContext.getDwEntity();
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        AllCheckResultDesJoinProvider sqlJoinProvider = new AllCheckResultDesJoinProvider(this.dataModelService, formScheme, this.splitTableHelper);
        String dwEntityTableName = dwEntity.getTableName();
        sqlJoinProvider.setDwTableName(dwEntityTableName);
        context.setSqlJoinProvider((ISqlJoinProvider)sqlJoinProvider);
        CheckFilterNodeFinder provider = new CheckFilterNodeFinder();
        provider.setDwGroupName(dwEntityTableName);
        context.getParser().registerDynamicNodeProvider((IReportDynamicNodeProvider)provider);
        CheckResult checkResult = new CheckResult();
        try {
            if (start == -1 || end == -1) {
                INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(ckrQueryModel);
                dataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
                totalCount = dataRows.size();
            } else {
                INvwaPageDataAccess pageDataAccess = this.dataAccessProvider.createPageDataAccess(ckrQueryModel);
                dataRows = pageDataAccess.executeQueryWithRowKey(context, start, end);
                totalCount = pageDataAccess.queryTotalCount(context);
            }
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u7ed3\u679c\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), e);
            return checkResult;
        }
        checkResult.setMessage(MSG_SUCCESS);
        HashMap<String, Integer> dimensionListIndexMap = new HashMap<String, Integer>();
        HashMap<String, Integer> ckdDescIndexMap = new HashMap<String, Integer>();
        CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
        List rowKeyColumns = dataRows.getRowKeyColumns();
        LinkEnvProviderImpl linkEnvProvider = new LinkEnvProviderImpl(this.runTimeViewController, this.dataDefinitionRuntimeController, this.fmdmAttributeService);
        DimensionChanger ckrDimChanger = checkQueryContext.getCkrDimChanger();
        Map<String, IDimDataLoader> entityLoaderMap = checkQueryContext.getEntityLoaderMap();
        ExecutorContext executorContext = checkQueryContext.getExecutorContext();
        String masterDimensionName = dwEntity.getDimensionName();
        for (int i = 0; i < dataRows.size(); ++i) {
            INvwaDataRow dataRow = dataRows.getRow(i);
            ArrayKey rowKey = dataRows.getRow(i).getRowKey();
            DimensionValueSet dataRowDimensionValueSet = new DimensionValueSet();
            for (int i1 = 0; i1 < rowKeyColumns.size(); ++i1) {
                String name = ckrDimChanger.getDimensionName(((ColumnModelDefine)rowKeyColumns.get(i1)).getCode());
                if (name == null) continue;
                dataRowDimensionValueSet.setValue(name, rowKey.get(i1));
            }
            try {
                CheckResultData checkResultData = this.checkResultDataUtil.createAllCheckResultData(ckrQueryModel, dataRow, dataRowDimensionValueSet, executorContext, linkEnvProvider);
                CheckResultUtil.fillCheckResultDimIndex(checkResult, dimensionListIndexMap, dataRowDimensionValueSet, checkResultData);
                CheckDesContext checkDesContext = new CheckDesContext(formScheme.getKey(), checkResultData.getFormulaData().getFormulaSchemeKey());
                CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
                CheckResultUtil.fillCKDDescIndex(checkResult, ckdDescIndexMap, checkResultData, ckdValidateCollector);
                checkResultData.setDimensionTitle(DimensionUtil.getDimensionTitle(dataRowDimensionValueSet, entityLoaderMap));
                checkResult.getResultData().add(checkResultData);
                continue;
            }
            catch (Exception e) {
                logger.error("\u5ba1\u6838\u7ed3\u679c\u7ec4\u88c5\u5f02\u5e38" + e.getMessage(), e);
            }
        }
        this.checkResultDataUtil.setOtherResultData(checkResult, entityLoaderMap.get(masterDimensionName), masterDimensionName);
        checkResult.setTotalCount(totalCount);
        return checkResult;
    }

    @Override
    public CheckResult queryBatchCheckResult(CheckResultQueryParam checkResultQueryParam) {
        SqlModel sqlModel;
        if (StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CheckResult checkResult = new CheckResult();
        checkResult.setMessage(MSG_SUCCESS);
        List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeys);
        DimensionCollection dimensionCollection = checkResultQueryParam.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            checkResult.setMessage(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return checkResult;
        }
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(formScheme.getKey());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        String[] bizKeys = this.dataModelService.getTableModelDefineByName(ckrTableName).getBizKeys().split(";");
        ArrayList<String> bizKeyColumnName = new ArrayList<String>();
        for (String bizKey : bizKeys) {
            bizKeyColumnName.add(this.dataModelService.getColumnModelDefineByID(bizKey).getName());
        }
        List<String> depDimNames = this.entityUtil.getDepOnDwDims(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        String masterDimensionName = this.entityUtil.getEntity(formScheme.getDw()).getDimensionName();
        try {
            sqlModel = this.checkResultDataUtil.getSqlModel(checkResultQueryParam, ckrTableName, ckdTableName, bizKeyColumnName, dimensionChanger, depDimNames, this.entityUtil.getEntityQueryVersionDate(formScheme.getDateTime(), dimensionValueSet));
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc(), e);
            checkResult.setMessage(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return checkResult;
        }
        try (CheckResultSqlBuilder checkResultSqlBuilder = new CheckResultSqlBuilder(formScheme, sqlModel, this.dataSource);){
            HashMap<String, Integer> dimensionListIndexMap = new HashMap<String, Integer>();
            HashMap<String, Integer> ckdDescIndexMap = new HashMap<String, Integer>();
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            List<EntityData> dwDimEntities = this.entityUtil.getDwDimEntities(formScheme);
            Map<String, IDimDataLoader> entityLoaderMap = this.entityUtil.getDimDataLoaders(formScheme, dimensionValueSet, dwDimEntities);
            LinkEnvProviderImpl linkEnvProvider = new LinkEnvProviderImpl(this.runTimeViewController, this.dataDefinitionRuntimeController, this.fmdmAttributeService);
            for (Map<String, Object> row : checkResultSqlBuilder.items()) {
                DimensionValueSet dataRowDimensionValueSet = new DimensionValueSet();
                for (String bizKey : bizKeys) {
                    String name = this.dataModelService.getColumnModelDefineByID(bizKey).getName();
                    Object o = row.get(name);
                    String asString = AbstractData.valueOf((Object)o, (int)6).getAsString();
                    String dimName = dimensionChanger.getDimensionName(name);
                    if (!StringUtils.isNotEmpty((String)dimName)) continue;
                    dataRowDimensionValueSet.setValue(dimName, (Object)asString);
                }
                try {
                    CheckResultData checkResultData = this.checkResultDataUtil.createCheckResultData(row, dataRowDimensionValueSet, executorContext, linkEnvProvider);
                    CheckResultUtil.fillCheckResultDimIndex(checkResult, dimensionListIndexMap, dataRowDimensionValueSet, checkResultData);
                    CheckDesContext checkDesContext = new CheckDesContext(formScheme.getKey(), checkResultData.getFormulaData().getFormulaSchemeKey());
                    CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
                    CheckResultUtil.fillCKDDescIndex(checkResult, ckdDescIndexMap, checkResultData, ckdValidateCollector);
                    checkResultData.setDimensionTitle(DimensionUtil.getDimensionTitle(dataRowDimensionValueSet, entityLoaderMap));
                    checkResult.getResultData().add(checkResultData);
                }
                catch (Exception e) {
                    logger.error("\u5ba1\u6838\u7ed3\u679c\u7ec4\u88c5\u5f02\u5e38:" + e.getMessage(), e);
                }
            }
            this.checkResultDataUtil.setOtherResultData(checkResult, entityLoaderMap.get(masterDimensionName), masterDimensionName);
            checkResult.setTotalCount(checkResultSqlBuilder.itemsCount());
            CheckResult checkResult2 = checkResult;
            return checkResult2;
        }
    }

    @Override
    public CheckResultGroup queryBatchCheckResultGroup(CheckResultQueryParam checkResultQueryParam) {
        SqlModel groupSqlModel;
        if (StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CheckResultGroup checkResultGroup = new CheckResultGroup();
        checkResultGroup.setMessage(MSG_SUCCESS);
        List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeys);
        DimensionCollection dimensionCollection = checkResultQueryParam.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            checkResultGroup.setMessage(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return checkResultGroup;
        }
        try {
            groupSqlModel = this.checkResultDataUtil.getGroupSqlModel(checkResultQueryParam, formScheme, this.entityUtil.getEntityQueryVersionDate(formScheme.getDateTime(), dimensionValueSet));
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc(), e);
            checkResultGroup.setMessage(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return checkResultGroup;
        }
        try (CheckResultSqlBuilder checkResultSqlBuilder = new CheckResultSqlBuilder(formScheme, groupSqlModel, this.dataSource);){
            EntityDataLoader dwEntityDataLoader = this.entityUtil.getEntityDataLoader(this.entityUtil.getContextMainDimId(formScheme.getDw()), formScheme.getDateTime(), dimensionValueSet, null);
            CheckResultGroup checkResultGroup2 = this.checkResultDataUtil.getCheckResultGroup(checkResultQueryParam, checkResultSqlBuilder, dwEntityDataLoader);
            return checkResultGroup2;
        }
    }

    @Override
    public boolean existError(CheckResultQueryParam checkResultQueryParam) {
        SqlModel sqlModel;
        if (StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        List dimensionCombinations = checkResultQueryParam.getDimensionCollection().getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            logger.warn(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return false;
        }
        List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeys);
        try {
            Date date = this.entityUtil.getEntityQueryVersionDate(formScheme.getDateTime(), ((DimensionCombination)dimensionCombinations.get(0)).toDimensionValueSet());
            sqlModel = this.checkResultDataUtil.getExistSqlModel(checkResultQueryParam, formScheme, date);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        try (CheckResultSqlBuilder checkResultSqlBuilder = new CheckResultSqlBuilder(formScheme, sqlModel, this.dataSource);){
            boolean bl = checkResultSqlBuilder.exist();
            return bl;
        }
    }
}

