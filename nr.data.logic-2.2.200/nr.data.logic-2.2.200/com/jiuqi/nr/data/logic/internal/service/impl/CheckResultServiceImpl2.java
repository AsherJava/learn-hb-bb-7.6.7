/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaPageDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CustomQueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CKDValidateCollector;
import com.jiuqi.nr.data.logic.internal.obj.CheckQueryContext;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.obj.QueryConParam;
import com.jiuqi.nr.data.logic.internal.provider.impl.LinkEnvProviderImpl;
import com.jiuqi.nr.data.logic.internal.query.CkrCkdJoinProvider;
import com.jiuqi.nr.data.logic.internal.service.impl.CheckResultServiceImpl;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.QueryConditionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaPageDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CheckResultServiceImpl2
extends CheckResultServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultServiceImpl2.class);
    private static final String MSG_SUCCESS = "success";
    private static final String MSG_UN_SUPPORT_CON = "\u6587\u4ef6\u5b58\u50a8\u4e0d\u652f\u6301customCondition\u6216filterCondition";
    private static final String MSG_TABLE_MODEL_NOT_FOUND_FORMAT = "{}\u8868\u6a21\u578b\u4e0d\u5b58\u5728";
    private static final String MSG_TABLE_MODEL_NOT_FOUND = "\u8868\u6a21\u578b\u4e0d\u5b58\u5728";
    private static final String MSG_RESULT_QUERY_EX_PREFIX = "\u5ba1\u6838\u7ed3\u679c\u67e5\u8be2\u5f02\u5e38\uff1a";
    @Autowired
    protected NrdbHelper nrdbHelper;
    @Autowired
    protected IFormulaRunTimeController formulaRunTimeController;

    @Override
    protected CheckResult getAllCheckResultDB(CheckQueryContext checkQueryContext) {
        if (!this.nrdbHelper.isEnableNrdb()) {
            return super.getAllCheckResultDB(checkQueryContext);
        }
        CheckResult checkResult = new CheckResult();
        checkResult.setMessage(MSG_SUCCESS);
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        TableModelDefine ckrTable = checkQueryContext.getCkrTable();
        TableModelDefine ckdTable = checkQueryContext.getCkdTable();
        QueryConParam queryConParam = this.getQueryConParam(checkResultQueryParam);
        if (this.uselessQueryParam(queryConParam)) {
            return checkResult;
        }
        if (this.checkResultDataUtil.needJoinCKD(queryConParam)) {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            String ckrTableName = ckrTable.getName();
            String tableID = ckrTable.getID();
            Map<String, ColumnModelDefine> codeColumnMap = this.dataModelService.getColumnModelDefinesByTable(tableID).stream().collect(Collectors.toMap(IModelDefineItem::getCode, o -> o));
            String ckdTableID = ckdTable.getID();
            for (Map.Entry<String, ColumnModelDefine> entry : codeColumnMap.entrySet()) {
                ColumnModelDefine columnModelDefine = entry.getValue();
                queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_USERKEY")));
            queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_UPDATETIME")));
            queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_USERNAME")));
            queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_DESCRIPTION")));
            queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_ASYNCTASKID"), checkResultQueryParam.getBatchId());
            if (checkResultQueryParam.isQueryByDim()) {
                DimensionValueSet masterKey = checkQueryContext.getMasterKey();
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
                for (int i = 0; i < masterKey.size(); ++i) {
                    ColumnModelDefine columnModelDefine = dimensionChanger.getColumn(masterKey.getName(i));
                    if (columnModelDefine == null) continue;
                    queryModel.getColumnFilters().put(columnModelDefine, masterKey.getValue(i));
                }
            }
            Map<Integer, Boolean> checkTypes = queryConParam.getCheckTypes();
            ArrayList<Integer> chekTypeList = new ArrayList<Integer>(checkTypes.keySet());
            boolean inCondition = queryConParam.getHaveCKD() == null || queryConParam.getHaveCKD() != false;
            List<String> recidList = this.queryRecid(checkQueryContext, queryConParam);
            if (CollectionUtils.isEmpty(recidList)) {
                if (inCondition) {
                    return checkResult;
                }
            } else if (inCondition) {
                queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_RECID"), recidList);
            } else {
                StringBuilder filter = new StringBuilder();
                filter.append("NOT ").append(ckrTable.getName()).append("[").append("ALLCKR_RECID").append("]");
                String join = String.join((CharSequence)"','", recidList);
                filter.append(" IN{'").append(join).append("'}");
                queryModel.setFilter(filter.toString());
            }
            queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_FORMULACHECKTYPE"), chekTypeList);
            List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
            if (!CollectionUtils.isEmpty(rangeKeys)) {
                if (checkResultQueryParam.getMode() == Mode.FORM) {
                    queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_FORMKEY"), rangeKeys);
                } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                    queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_FORMULAID"), rangeKeys);
                }
            }
            queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_FORMORDER"), false));
            queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_FORMULAORDER"), false));
            queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_GLOBROW"), false));
            queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_GLOBCOL"), false));
            queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_RECID"), false));
            queryModel.setMainTableName(ckrTableName);
            return super.getAllCheckResultDB(checkQueryContext, queryModel);
        }
        return super.getAllCheckResultDB(checkQueryContext);
    }

    @Override
    public CheckResult queryBatchCheckResult(CheckResultQueryParam checkResultQueryParam) {
        NvwaQueryModel ckrQueryModel;
        CheckQueryContext checkQueryContext;
        if (!this.nrdbHelper.isEnableNrdb()) {
            return super.queryBatchCheckResult(checkResultQueryParam);
        }
        CheckResult checkResult = new CheckResult();
        checkResult.setMessage(MSG_SUCCESS);
        QueryConParam queryConParam = this.getQueryConParam(checkResultQueryParam);
        if (this.uselessQueryParam(queryConParam)) {
            return checkResult;
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CustomQueryCondition customCondition = checkResultQueryParam.getCustomCondition();
        String filterCondition = checkResultQueryParam.getFilterCondition();
        if (StringUtils.hasText(filterCondition) || customCondition != null) {
            throw new UnsupportedOperationException(MSG_UN_SUPPORT_CON);
        }
        try {
            checkQueryContext = this.checkResultDataUtil.initCheckQueryContext(checkResultQueryParam);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage(), e);
            checkResult.setMessage(e.getMessage());
            return checkResult;
        }
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByCode(ckrTableName);
        if (ckrTable == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckrTableName);
            checkResult.setMessage(ckrTableName + MSG_TABLE_MODEL_NOT_FOUND);
            return checkResult;
        }
        checkQueryContext.setCkrTable(ckrTable);
        DimensionChanger ckrDimChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        checkQueryContext.setCkrDimChanger(ckrDimChanger);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine ckdTable = this.dataModelService.getTableModelDefineByCode(ckdTableName);
        if (ckdTable == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckdTableName);
            checkResult.setMessage(ckdTableName + MSG_TABLE_MODEL_NOT_FOUND);
            return checkResult;
        }
        checkQueryContext.setCkdTable(ckdTable);
        DimensionChanger ckdDimChanger = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
        checkQueryContext.setCkdDimChanger(ckdDimChanger);
        EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        checkQueryContext.setDwEntity(dwEntity);
        List<EntityData> dwDimEntities = this.entityUtil.getDwDimEntities(formScheme);
        DimensionValueSet masterKey = checkQueryContext.getMasterKey();
        Map<String, IDimDataLoader> entityLoaderMap = this.entityUtil.getDimDataLoaders(formScheme, masterKey, dwDimEntities);
        checkQueryContext.setEntityLoaderMap(entityLoaderMap);
        ExecutorContext executorContext = this.paramUtil.getExecutorContext(formScheme.getKey());
        checkQueryContext.setExecutorContext(executorContext);
        if (this.checkResultDataUtil.needJoinCKD(queryConParam)) {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, false);
            ColumnModelDefine ckrBatchCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_BATCH_ID");
            ckrQueryModel.getColumnFilters().put(ckrBatchCol, checkResultQueryParam.getBatchId());
            if (checkResultQueryParam.isQueryByDim()) {
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
                for (int i = 0; i < masterKey.size(); ++i) {
                    ColumnModelDefine columnModelDefine = dimensionChanger.getColumn(masterKey.getName(i));
                    if (columnModelDefine == null) continue;
                    ckrQueryModel.getColumnFilters().put(columnModelDefine, masterKey.getValue(i));
                }
            }
            Map<Integer, Boolean> checkTypes = queryConParam.getCheckTypes();
            ArrayList<Integer> chekTypeList = new ArrayList<Integer>(checkTypes.keySet());
            boolean inCondition = queryConParam.getHaveCKD() == null || queryConParam.getHaveCKD() != false;
            List<String> recidList = this.queryRecid(checkQueryContext, queryConParam);
            if (CollectionUtils.isEmpty(recidList)) {
                if (inCondition) {
                    return checkResult;
                }
            } else if (inCondition) {
                ColumnModelDefine recidCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_RECID");
                ckrQueryModel.getColumnFilters().put(recidCol, recidList);
            } else {
                StringBuilder filter = new StringBuilder();
                filter.append("NOT ").append(ckrTable.getName()).append("[").append("CKR_RECID").append("]");
                String join = String.join((CharSequence)"','", recidList);
                filter.append(" IN{'").append(join).append("'}");
                ckrQueryModel.setFilter(filter.toString());
            }
            ColumnModelDefine ckrTypeCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULACHECKTYPE");
            ckrQueryModel.getColumnFilters().put(ckrTypeCol, chekTypeList);
            List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
            if (!CollectionUtils.isEmpty(rangeKeys)) {
                if (checkResultQueryParam.getMode() == Mode.FORM) {
                    ColumnModelDefine ckrFormCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMKEY");
                    ckrQueryModel.getColumnFilters().put(ckrFormCol, rangeKeys);
                } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                    ColumnModelDefine ckrFlCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULAID");
                    ckrQueryModel.getColumnFilters().put(ckrFlCol, rangeKeys);
                }
            }
        } else {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, true);
        }
        return this.getCheckResult(checkQueryContext, ckrQueryModel);
    }

    private QueryConParam getQueryConParam(CheckResultQueryParam checkResultQueryParam) {
        QueryCondition queryCondition = checkResultQueryParam.getQueryCondition();
        if (queryCondition != null) {
            return QueryConditionUtil.parse(queryCondition);
        }
        Map<Integer, Boolean> checkTypes = checkResultQueryParam.getCheckTypes();
        return new QueryConParam(checkTypes, null, null);
    }

    private boolean uselessQueryParam(QueryConParam queryConParam) {
        Boolean haveCKD = queryConParam.getHaveCKD();
        String ckdKeyWordPattern = queryConParam.getCkdKeyWordPattern();
        return haveCKD != null && haveCKD == false && ckdKeyWordPattern != null;
    }

    private CheckResult getCheckResult(CheckQueryContext checkQueryContext, NvwaQueryModel ckrQueryModel) {
        int totalCount;
        INvwaDataSet dataRows;
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        ExecutorContext executorContext = checkQueryContext.getExecutorContext();
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        Map<String, IDimDataLoader> entityLoaderMap = checkQueryContext.getEntityLoaderMap();
        String masterDimensionName = checkQueryContext.getDwEntity().getDimensionName();
        DimensionChanger ckrDimChanger = checkQueryContext.getCkrDimChanger();
        int start = -1;
        int end = -1;
        if (checkResultQueryParam.getPagerInfo() != null) {
            int offset = checkResultQueryParam.getPagerInfo().getOffset();
            int limit = checkResultQueryParam.getPagerInfo().getLimit();
            start = offset * limit;
            end = start + limit;
        }
        DataAccessContext context = this.getDataAccessContext(formScheme, checkResultQueryParam.getBatchId());
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
            logger.error(MSG_RESULT_QUERY_EX_PREFIX + e.getMessage(), e);
            checkResult.setMessage(e.getMessage());
            return checkResult;
        }
        checkResult.setMessage(MSG_SUCCESS);
        HashMap<String, Integer> dimensionListIndexMap = new HashMap<String, Integer>();
        HashMap<String, Integer> ckdDescIndexMap = new HashMap<String, Integer>();
        CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
        List rowKeyColumns = dataRows.getRowKeyColumns();
        LinkEnvProviderImpl linkEnvProvider = new LinkEnvProviderImpl(this.runTimeViewController, this.dataDefinitionRuntimeController, this.fmdmAttributeService);
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
                CheckResultData checkResultData = this.checkResultDataUtil.createCheckResultData(ckrQueryModel, dataRow, dataRowDimensionValueSet, executorContext, linkEnvProvider);
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
    public CheckResultGroup queryBatchCheckResultGroup(CheckResultQueryParam checkResultQueryParam) {
        NvwaQueryModel ckrQueryModel;
        CheckQueryContext checkQueryContext;
        if (!this.nrdbHelper.isEnableNrdb()) {
            return super.queryBatchCheckResultGroup(checkResultQueryParam);
        }
        CheckResultGroup checkResultGroup = new CheckResultGroup();
        checkResultGroup.setMessage(MSG_SUCCESS);
        QueryConParam queryConParam = this.getQueryConParam(checkResultQueryParam);
        if (this.uselessQueryParam(queryConParam)) {
            return checkResultGroup;
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CustomQueryCondition customCondition = checkResultQueryParam.getCustomCondition();
        String filterCondition = checkResultQueryParam.getFilterCondition();
        if (StringUtils.hasText(filterCondition) || customCondition != null) {
            throw new UnsupportedOperationException(MSG_UN_SUPPORT_CON);
        }
        try {
            checkQueryContext = this.checkResultDataUtil.initCheckQueryContext(checkResultQueryParam);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage(), e);
            checkResultGroup.setMessage(e.getMessage());
            return checkResultGroup;
        }
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByCode(ckrTableName);
        if (ckrTable == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckrTableName);
            checkResultGroup.setMessage(ckrTableName + MSG_TABLE_MODEL_NOT_FOUND);
            return checkResultGroup;
        }
        checkQueryContext.setCkrTable(ckrTable);
        DimensionChanger ckrDimChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        checkQueryContext.setCkrDimChanger(ckrDimChanger);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine ckdTable = this.dataModelService.getTableModelDefineByCode(ckdTableName);
        if (ckdTable == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckdTableName);
            checkResultGroup.setMessage(ckdTableName + MSG_TABLE_MODEL_NOT_FOUND);
            return checkResultGroup;
        }
        checkQueryContext.setCkdTable(ckdTable);
        DimensionChanger ckdDimChanger = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
        checkQueryContext.setCkdDimChanger(ckdDimChanger);
        EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        checkQueryContext.setDwEntity(dwEntity);
        DimensionValueSet masterKey = checkQueryContext.getMasterKey();
        INvwaDataSet dataRows = null;
        int totalCount = -1;
        int showCount = -1;
        int start = -1;
        int end = -1;
        if (checkResultQueryParam.getPagerInfo() != null) {
            int offset = checkResultQueryParam.getPagerInfo().getOffset();
            int limit = checkResultQueryParam.getPagerInfo().getLimit();
            start = offset * limit;
            end = start + limit;
        }
        Map<String, ColumnModelDefine> codeColumnMap = this.dataModelService.getColumnModelDefinesByTable(ckrTable.getID()).stream().collect(Collectors.toMap(IModelDefineItem::getCode, o -> o));
        if (this.checkResultDataUtil.needJoinCKD(queryConParam)) {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, false);
            ColumnModelDefine ckrBatchCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_BATCH_ID");
            ckrQueryModel.getColumnFilters().put(ckrBatchCol, checkResultQueryParam.getBatchId());
            if (checkResultQueryParam.isQueryByDim()) {
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
                for (int i = 0; i < masterKey.size(); ++i) {
                    ColumnModelDefine columnModelDefine = dimensionChanger.getColumn(masterKey.getName(i));
                    if (columnModelDefine == null) continue;
                    ckrQueryModel.getColumnFilters().put(columnModelDefine, masterKey.getValue(i));
                }
            }
            Map<Integer, Boolean> checkTypes = queryConParam.getCheckTypes();
            ArrayList<Integer> chekTypeList = new ArrayList<Integer>(checkTypes.keySet());
            boolean inCondition = queryConParam.getHaveCKD() == null || queryConParam.getHaveCKD() != false;
            List<String> recidList = this.queryRecid(checkQueryContext, queryConParam);
            if (CollectionUtils.isEmpty(recidList)) {
                if (inCondition) {
                    return checkResultGroup;
                }
            } else if (inCondition) {
                ColumnModelDefine recidCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_RECID");
                ckrQueryModel.getColumnFilters().put(recidCol, recidList);
            } else {
                StringBuilder filter = new StringBuilder();
                filter.append("NOT ").append(ckrTable.getName()).append("[").append("CKR_RECID").append("]");
                String join = String.join((CharSequence)"','", recidList);
                filter.append(" IN{'").append(join).append("'}");
                ckrQueryModel.setFilter(filter.toString());
            }
            ColumnModelDefine ckrTypeCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULACHECKTYPE");
            ckrQueryModel.getColumnFilters().put(ckrTypeCol, chekTypeList);
            List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
            if (!CollectionUtils.isEmpty(rangeKeys)) {
                if (checkResultQueryParam.getMode() == Mode.FORM) {
                    ColumnModelDefine ckrFormCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMKEY");
                    ckrQueryModel.getColumnFilters().put(ckrFormCol, rangeKeys);
                } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                    ColumnModelDefine ckrFlCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULAID");
                    ckrQueryModel.getColumnFilters().put(ckrFlCol, rangeKeys);
                }
            }
        } else {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, true);
        }
        ckrQueryModel.getColumns().clear();
        ckrQueryModel.getOrderByItems().clear();
        ckrQueryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_RECID")));
        HashSet<String> queryColumns = new HashSet<String>();
        queryColumns.add("CKR_RECID");
        for (ColumnModelDefine columnModelDefine : ckrQueryModel.getColumnFilters().keySet()) {
            if (!queryColumns.add(columnModelDefine.getCode())) continue;
            ckrQueryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        NvwaQueryModel groupQueryModel = null;
        DataAccessContext groupContext = new DataAccessContext(this.dataModelService);
        DataAccessContext recidContext = this.getDataAccessContext(formScheme, checkResultQueryParam.getBatchId());
        try {
            INvwaPageDataAccess pageDataAccess;
            if (this.checkResultDataUtil.needJoinCKD(queryConParam)) {
                INvwaDataAccess recidQuery = this.dataAccessProvider.createReadOnlyDataAccess(ckrQueryModel);
                MemoryDataSet recidDataRows = recidQuery.executeQuery(recidContext);
                if (recidDataRows == null || recidDataRows.isEmpty()) {
                    return checkResultGroup;
                }
                showCount = recidDataRows.size();
                List filterRecid = recidDataRows.stream().map(o -> o.getString(0)).collect(Collectors.toList());
                groupQueryModel = this.checkResultDataUtil.getGroupQueryModelWithoutFilter(checkResultQueryParam, formScheme, codeColumnMap);
                groupQueryModel.getColumnFilters().put(codeColumnMap.get("CKR_BATCH_ID"), checkResultQueryParam.getBatchId());
                groupQueryModel.getColumnFilters().put(codeColumnMap.get("CKR_RECID"), filterRecid);
            } else {
                pageDataAccess = this.dataAccessProvider.createPageDataAccess(ckrQueryModel);
                showCount = pageDataAccess.queryTotalCount(recidContext);
                groupQueryModel = this.checkResultDataUtil.getGroupQueryModelWithoutFilter(checkResultQueryParam, formScheme, codeColumnMap);
                this.checkResultDataUtil.fillCKRQueryFilter(checkResultQueryParam, ckrTableName, ckdTableName, masterKey, groupQueryModel, codeColumnMap);
            }
            if (start == -1 || end == -1) {
                INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(groupQueryModel);
                dataRows = readOnlyDataAccess.executeQueryWithRowKey(groupContext);
                totalCount = dataRows.size();
            } else {
                pageDataAccess = this.dataAccessProvider.createPageDataAccess(groupQueryModel);
                dataRows = pageDataAccess.executeQueryWithRowKey(groupContext, start, end);
                totalCount = pageDataAccess.queryTotalCount(groupContext);
            }
        }
        catch (Exception e) {
            logger.error(MSG_RESULT_QUERY_EX_PREFIX + e.getMessage(), e);
        }
        if (dataRows != null && dataRows.size() > 0) {
            GroupType groupType = checkResultQueryParam.getGroupType();
            checkResultGroup.setMessage(MSG_SUCCESS);
            LinkedHashMap<String, CheckResultGroupData> groupDataMap = new LinkedHashMap<String, CheckResultGroupData>();
            Map<Integer, String> codeTitleMap = this.paramUtil.getCheckTypeCodeTitleMap();
            int formIndex = -1;
            int formulaIndex = -1;
            int unitIndex = -1;
            int checkTypeIndex = -1;
            int countIndex = -1;
            block31: for (int i = 0; i < groupQueryModel.getColumns().size(); ++i) {
                NvwaQueryColumn nvwaQueryColumn = (NvwaQueryColumn)groupQueryModel.getColumns().get(i);
                ColumnModelDefine columnModel = nvwaQueryColumn.getColumnModel();
                switch (columnModel.getCode()) {
                    case "CKR_FORMKEY": {
                        formIndex = i;
                        continue block31;
                    }
                    case "CKR_FORMULAID": {
                        formulaIndex = i;
                        continue block31;
                    }
                    case "MDCODE": {
                        unitIndex = i;
                        continue block31;
                    }
                    case "CKR_FORMULACHECKTYPE": {
                        checkTypeIndex = i;
                        continue block31;
                    }
                    case "CKR_RECID": {
                        countIndex = i;
                    }
                }
            }
            EntityDataLoader dwEntityDataLoader = this.entityUtil.getEntityDataLoader(this.entityUtil.getContextMainDimId(formScheme.getDw()), formScheme.getDateTime(), masterKey, null);
            for (INvwaDataRow dataRow : dataRows) {
                int groupCount;
                String checkTypeStr;
                String unitKey;
                String formula;
                String formKey;
                CheckResultGroupData groupData = new CheckResultGroupData();
                try {
                    formKey = AbstractData.valueOf((Object)dataRow.getValue(formIndex), (int)6).getAsString();
                    formula = AbstractData.valueOf((Object)dataRow.getValue(formulaIndex), (int)6).getAsString();
                    unitKey = AbstractData.valueOf((Object)dataRow.getValue(unitIndex), (int)6).getAsString();
                    checkTypeStr = AbstractData.valueOf((Object)dataRow.getValue(checkTypeIndex), (int)6).getAsString();
                    String asString = AbstractData.valueOf((Object)dataRow.getValue(countIndex), (int)6).getAsString();
                    groupCount = new Double(asString).intValue();
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    continue;
                }
                FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(formula);
                int checkTypeByFml = Integer.parseInt(checkTypeStr);
                String checkTypeTitle = codeTitleMap.get(checkTypeByFml);
                IEntityRow entityRow = dwEntityDataLoader.getRowByEntityDataKey(unitKey);
                switch (groupType) {
                    case formula: {
                        if (groupDataMap.containsKey(formula)) {
                            groupData = (CheckResultGroupData)groupDataMap.get(formula);
                        } else {
                            groupDataMap.put(formula, groupData);
                        }
                        groupData.setKey(formulaDefine.getKey());
                        groupData.setCode(formulaDefine.getCode());
                        groupData.setTitle(formulaDefine.getCode());
                        groupData.setCount(groupCount);
                        break;
                    }
                    case form_formula: {
                        if (groupDataMap.containsKey(formKey)) {
                            groupData = (CheckResultGroupData)groupDataMap.get(formKey);
                        } else {
                            groupDataMap.put(formKey, groupData);
                            if (this.checkResultDataUtil.fillGroupDataByForm(groupData, formKey)) break;
                        }
                        CheckResultGroupData formulaGroupInfo = new CheckResultGroupData();
                        formulaGroupInfo.setKey(formulaDefine.getKey());
                        formulaGroupInfo.setCode(formulaDefine.getCode());
                        formulaGroupInfo.setTitle(formulaDefine.getCode());
                        formulaGroupInfo.setCount(groupCount);
                        groupData.getChildren().add(formulaGroupInfo);
                        groupData.setCount(groupData.getCount() + groupCount);
                        break;
                    }
                    case unit: {
                        if (groupDataMap.containsKey(unitKey)) {
                            groupData = (CheckResultGroupData)groupDataMap.get(unitKey);
                        } else {
                            groupDataMap.put(unitKey, groupData);
                        }
                        groupData.setKey(entityRow.getEntityKeyData());
                        groupData.setCode(entityRow.getCode());
                        groupData.setTitle(entityRow.getTitle());
                        groupData.setCount(groupCount);
                        break;
                    }
                    case UNIT_FORM: {
                        if (groupDataMap.containsKey(unitKey)) {
                            groupData = (CheckResultGroupData)groupDataMap.get(unitKey);
                        } else {
                            groupDataMap.put(unitKey, groupData);
                            groupData.setKey(entityRow.getEntityKeyData());
                            groupData.setCode(entityRow.getCode());
                            groupData.setTitle(entityRow.getTitle());
                        }
                        CheckResultGroupData formGroupInfo = new CheckResultGroupData();
                        this.checkResultDataUtil.fillGroupDataByForm(formGroupInfo, formKey);
                        formGroupInfo.setCount(groupCount);
                        groupData.getChildren().add(formGroupInfo);
                        groupData.setCount(groupData.getCount() + groupCount);
                        break;
                    }
                    case checktype_form: {
                        groupData = this.checkResultDataUtil.fillGroupDataByCheckType(checkResultGroup, groupDataMap, groupData, checkTypeStr, checkTypeTitle);
                        CheckResultGroupData formGroup = new CheckResultGroupData();
                        if (this.checkResultDataUtil.fillGroupDataByForm(formGroup, formKey)) break;
                        formGroup.setCount(groupCount);
                        groupData.getChildren().add(formGroup);
                        groupData.setCount(groupData.getCount() + groupCount);
                        break;
                    }
                    case checktype_unit: {
                        groupData = this.checkResultDataUtil.fillGroupDataByCheckType(checkResultGroup, groupDataMap, groupData, checkTypeStr, checkTypeTitle);
                        CheckResultGroupData unitGroupInfo = new CheckResultGroupData();
                        unitGroupInfo.setKey(entityRow.getEntityKeyData());
                        unitGroupInfo.setCode(entityRow.getCode());
                        unitGroupInfo.setTitle(entityRow.getTitle());
                        unitGroupInfo.setCount(groupCount);
                        groupData.getChildren().add(unitGroupInfo);
                        groupData.setCount(groupData.getCount() + groupCount);
                        break;
                    }
                    case UNIT_CHECKTYPE: {
                        if (groupDataMap.containsKey(unitKey)) {
                            groupData = (CheckResultGroupData)groupDataMap.get(unitKey);
                        } else {
                            groupDataMap.put(unitKey, groupData);
                            groupData.setKey(entityRow.getEntityKeyData());
                            groupData.setCode(entityRow.getCode());
                            groupData.setTitle(entityRow.getTitle());
                        }
                        CheckResultGroupData checkTypeGroup = new CheckResultGroupData();
                        checkTypeGroup.setKey(checkTypeStr);
                        checkTypeGroup.setCode(checkTypeStr);
                        checkTypeGroup.setTitle(checkTypeTitle);
                        checkTypeGroup.setCount(groupCount);
                        groupData.getChildren().add(checkTypeGroup);
                        groupData.setCount(groupData.getCount() + groupCount);
                        break;
                    }
                }
            }
            checkResultGroup.getGroupData().addAll(groupDataMap.values());
            checkResultGroup.setTotalCount(totalCount);
            checkResultGroup.setShowCount(showCount);
        }
        return checkResultGroup;
    }

    private DataAccessContext getDataAccessContext(FormSchemeDefine formScheme, String batchId) {
        DataAccessContext countContext = new DataAccessContext(this.dataModelService);
        CkrCkdJoinProvider joinProvider = new CkrCkdJoinProvider(this.dataModelService, this.splitTableHelper, formScheme, batchId);
        countContext.setSqlJoinProvider((ISqlJoinProvider)joinProvider);
        return countContext;
    }

    @Override
    public boolean existError(CheckResultQueryParam checkResultQueryParam) {
        NvwaQueryModel ckrQueryModel;
        CheckQueryContext checkQueryContext;
        if (!this.nrdbHelper.isEnableNrdb()) {
            return super.existError(checkResultQueryParam);
        }
        QueryConParam queryConParam = this.getQueryConParam(checkResultQueryParam);
        if (this.uselessQueryParam(queryConParam)) {
            return false;
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)checkResultQueryParam.getBatchId())) {
            throw new LogicMappingException(ExceptionEnum.CHECK_QUERY_PARAM_EXC.getCode());
        }
        CustomQueryCondition customCondition = checkResultQueryParam.getCustomCondition();
        String filterCondition = checkResultQueryParam.getFilterCondition();
        if (StringUtils.hasText(filterCondition) || customCondition != null) {
            throw new UnsupportedOperationException(MSG_UN_SUPPORT_CON);
        }
        try {
            checkQueryContext = this.checkResultDataUtil.initCheckQueryContext(checkResultQueryParam);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        FormSchemeDefine formScheme = checkQueryContext.getFormSchemeDefine();
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByCode(ckrTableName);
        if (ckrTable == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckrTableName);
            return false;
        }
        checkQueryContext.setCkrTable(ckrTable);
        DimensionChanger ckrDimChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        checkQueryContext.setCkrDimChanger(ckrDimChanger);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine ckdTable = this.dataModelService.getTableModelDefineByCode(ckdTableName);
        if (ckdTableName == null) {
            logger.error(MSG_TABLE_MODEL_NOT_FOUND_FORMAT, (Object)ckdTableName);
            return false;
        }
        checkQueryContext.setCkdTable(ckdTable);
        DimensionChanger ckdDimChanger = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
        checkQueryContext.setCkdDimChanger(ckdDimChanger);
        EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        checkQueryContext.setDwEntity(dwEntity);
        List<EntityData> dwDimEntities = this.entityUtil.getDwDimEntities(formScheme);
        DimensionValueSet masterKey = checkQueryContext.getMasterKey();
        Map<String, IDimDataLoader> entityLoaderMap = this.entityUtil.getDimDataLoaders(formScheme, masterKey, dwDimEntities);
        checkQueryContext.setEntityLoaderMap(entityLoaderMap);
        ExecutorContext executorContext = this.paramUtil.getExecutorContext(formScheme.getKey());
        checkQueryContext.setExecutorContext(executorContext);
        if (this.checkResultDataUtil.needJoinCKD(queryConParam)) {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, false);
            ColumnModelDefine ckrBatchCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_BATCH_ID");
            ckrQueryModel.getColumnFilters().put(ckrBatchCol, checkResultQueryParam.getBatchId());
            if (checkResultQueryParam.isQueryByDim()) {
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
                for (int i = 0; i < masterKey.size(); ++i) {
                    ColumnModelDefine columnModelDefine = dimensionChanger.getColumn(masterKey.getName(i));
                    if (columnModelDefine == null) continue;
                    ckrQueryModel.getColumnFilters().put(columnModelDefine, masterKey.getValue(i));
                }
            }
            Map<Integer, Boolean> checkTypes = queryConParam.getCheckTypes();
            ArrayList<Integer> chekTypeList = new ArrayList<Integer>(checkTypes.keySet());
            boolean inCondition = queryConParam.getHaveCKD() == null || queryConParam.getHaveCKD() != false;
            List<String> recidList = this.queryRecid(checkQueryContext, queryConParam);
            if (CollectionUtils.isEmpty(recidList)) {
                if (inCondition) {
                    CheckResult checkResult = new CheckResult();
                    checkResult.setMessage("\u5ba1\u6838\u7ed3\u679c\u4e3a\u7a7a");
                    return false;
                }
            } else if (inCondition) {
                ColumnModelDefine recidCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_RECID");
                ckrQueryModel.getColumnFilters().put(recidCol, recidList);
            } else {
                StringBuilder filter = new StringBuilder();
                filter.append("NOT ").append(ckrTable.getName()).append("[").append("CKR_RECID").append("]");
                String join = String.join((CharSequence)"','", recidList);
                filter.append(" IN{'").append(join).append("'}");
                ckrQueryModel.setFilter(filter.toString());
            }
            ColumnModelDefine ckrTypeCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULACHECKTYPE");
            ckrQueryModel.getColumnFilters().put(ckrTypeCol, chekTypeList);
            List<String> rangeKeys = checkResultQueryParam.getRangeKeys();
            if (!CollectionUtils.isEmpty(rangeKeys)) {
                if (checkResultQueryParam.getMode() == Mode.FORM) {
                    ColumnModelDefine ckrFormCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMKEY");
                    ckrQueryModel.getColumnFilters().put(ckrFormCol, rangeKeys);
                } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                    ColumnModelDefine ckrFlCol = this.dataModelService.getColumnModelDefineByCode(ckrTable.getID(), "CKR_FORMULAID");
                    ckrQueryModel.getColumnFilters().put(ckrFlCol, rangeKeys);
                }
            }
        } else {
            ckrQueryModel = this.checkResultDataUtil.getCKRQueryModel(checkQueryContext, true);
        }
        ckrQueryModel.getOrderByItems().clear();
        Set collect = ckrQueryModel.getColumnFilters().keySet().stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        collect.add("CKR_RECID");
        ckrQueryModel.getColumns().removeIf(o -> !collect.contains(o.getColumnModel().getCode()));
        DataAccessContext context = this.getDataAccessContext(formScheme, checkResultQueryParam.getBatchId());
        try {
            INvwaPageDataAccess pageDataAccess = this.dataAccessProvider.createPageDataAccess(ckrQueryModel);
            MemoryDataSet dataRows = pageDataAccess.executeQuery(context, 0, 1);
            return dataRows != null && !dataRows.isEmpty();
        }
        catch (Exception e) {
            logger.error(MSG_RESULT_QUERY_EX_PREFIX + e.getMessage(), e);
            return false;
        }
    }

    private List<String> queryRecid(CheckQueryContext checkQueryContext, QueryConParam queryConParam) {
        String filter;
        List<String> rangeKeys;
        List<String> formulaSchemeKeys;
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        TableModelDefine ckdTable = checkQueryContext.getCkdTable();
        NvwaQueryModel ckdQueryModel = new NvwaQueryModel();
        ColumnModelDefine ckdRecidCol = this.dataModelService.getColumnModelDefineByCode(ckdTable.getID(), "CKD_RECID");
        ckdQueryModel.getColumns().add(new NvwaQueryColumn(ckdRecidCol));
        if (checkResultQueryParam.isQueryByDim()) {
            DimensionChanger ckdDimChanger = checkQueryContext.getCkdDimChanger();
            DimensionValueSet masterKey = checkQueryContext.getMasterKey();
            for (int i = 0; i < masterKey.size(); ++i) {
                ColumnModelDefine column = ckdDimChanger.getColumn(masterKey.getName(i));
                if (column == null) continue;
                ckdQueryModel.getColumnFilters().put(column, masterKey.getValue(i));
                ckdQueryModel.getColumns().add(new NvwaQueryColumn(column));
            }
        }
        if (!CollectionUtils.isEmpty(formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys())) {
            ColumnModelDefine ckdFsCol = this.dataModelService.getColumnModelDefineByCode(ckdTable.getID(), "CKD_FORMULASCHEMEKEY");
            ckdQueryModel.getColumnFilters().put(ckdFsCol, formulaSchemeKeys);
            ckdQueryModel.getColumns().add(new NvwaQueryColumn(ckdFsCol));
        }
        if (!CollectionUtils.isEmpty(rangeKeys = checkResultQueryParam.getRangeKeys()) && checkResultQueryParam.getMode() == Mode.FORM) {
            ColumnModelDefine ckdFmCol = this.dataModelService.getColumnModelDefineByCode(ckdTable.getID(), "CKD_FORMKEY");
            ckdQueryModel.getColumnFilters().put(ckdFmCol, rangeKeys);
            ckdQueryModel.getColumns().add(new NvwaQueryColumn(ckdFmCol));
        }
        if (queryConParam.getDesCheckStateCode() != null) {
            ColumnModelDefine ckdStateCol = this.dataModelService.getColumnModelDefineByCode(ckdTable.getID(), "CKD_STATE");
            ckdQueryModel.getColumnFilters().put(ckdStateCol, queryConParam.getDesCheckStateCode());
            ckdQueryModel.getColumns().add(new NvwaQueryColumn(ckdStateCol));
        } else {
            filter = "isnotnull(" + ckdTable.getName() + "[" + "CKD_DESCRIPTION" + "] )";
            ckdQueryModel.setFilter(filter);
        }
        if (queryConParam.getCkdKeyWordPattern() != null) {
            filter = ckdQueryModel.getFilter();
            String keyFilter = ckdTable.getName() + "[" + "CKD_DESCRIPTION" + "] LIKE '" + queryConParam.getCkdKeyWordPattern() + "'";
            if (StringUtils.hasText(filter)) {
                ckdQueryModel.setFilter(filter + " AND " + keyFilter);
            } else {
                ckdQueryModel.setFilter(keyFilter);
            }
        }
        ColumnModelDefine ckdDesCol = this.dataModelService.getColumnModelDefineByCode(ckdTable.getID(), "CKD_DESCRIPTION");
        ckdQueryModel.getColumns().add(new NvwaQueryColumn(ckdDesCol));
        ckdQueryModel.setMainTableName(ckdTable.getName());
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(ckdQueryModel);
        try {
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
            if (dataRows == null || dataRows.isEmpty()) {
                return Collections.emptyList();
            }
            return dataRows.stream().map(o -> o.getString(0)).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}

