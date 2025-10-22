/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.BatchDelCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferMap;
import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferPar;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CKDSaveResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckData;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CKDValidateCollector;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.obj.CKDValidInfo;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.query.CheckResultSqlBuilder;
import com.jiuqi.nr.data.logic.internal.query.SqlModel;
import com.jiuqi.nr.data.logic.internal.query.UnionModel;
import com.jiuqi.nr.data.logic.internal.service.ICKDChangeNotifier;
import com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService;
import com.jiuqi.nr.data.logic.internal.util.CheckResultDataUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class CheckErrorDescriptionServiceImpl
implements ICheckErrorDescriptionService {
    private static final Logger logger = LoggerFactory.getLogger(CheckErrorDescriptionServiceImpl.class);
    @Autowired
    private SplitCheckTableHelper splitTableHelper;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private ICheckService checkService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IReviseCKDRECIDService reviseCKDRECIDService;
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CheckResultDataUtil checkResultDataUtil;
    @Autowired
    private IRuntimeExpressionService expressionService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityDataQueryAssist entityDataQueryAssist;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    protected NrdbHelper nrdbHelper;
    @Autowired
    private List<ICheckDesValidatorProvider> checkDesValidatorProviders;
    @Autowired
    private ICKDChangeNotifier ckdChangeNotifier;

    @Override
    public List<CheckDesObj> queryFormulaCheckDes(CheckDesQueryParam checkDesQueryParam) {
        DimensionCollection dimensionCollection = checkDesQueryParam.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return Collections.emptyList();
        }
        ArrayList<CheckDesObj> desInfoList = new ArrayList<CheckDesObj>();
        List<String> formulaSchemeKeyList = checkDesQueryParam.getFormulaSchemeKey();
        String formSchemeKey = checkDesQueryParam.getFormSchemeKey();
        FormSchemeDefine formScheme = StringUtils.isNotEmpty((String)formSchemeKey) ? this.runtimeView.getFormScheme(formSchemeKey) : this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeyList);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        Map<String, String> splitKeyValue = this.splitTableHelper.getSplitKeyValue(formScheme, ckdTableName);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(ckdTableName);
        if (tableModel == null) {
            throw new NotFoundTableDefineException(new String[]{ckdTableName});
        }
        String tableId = tableModel.getID();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        HashMap<String, ColumnModelDefine> columnMap = new HashMap<String, ColumnModelDefine>();
        for (ColumnModelDefine c : columns) {
            c.getCode();
            columnMap.put(c.getCode(), c);
            queryModel.getColumns().add(new NvwaQueryColumn(c));
        }
        CheckErrorDescriptionServiceImpl.appendQueryFilters(checkDesQueryParam, queryModel, columnMap, dimensionValueSet, dimensionChanger);
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataRows = readOnlyDataAccess.executeQueryWithRowKey(context);
            List rowKeyColumns = dataRows.getRowKeyColumns();
            for (int i = 0; i < dataRows.size(); ++i) {
                INvwaDataRow row = dataRows.getRow(i);
                DimensionValueSet rowDim = this.getRowDim(row, rowKeyColumns, dimensionChanger);
                if (!CollectionUtils.isEmpty(splitKeyValue)) {
                    for (String dimName : splitKeyValue.keySet()) {
                        rowDim.setValue(dimName, (Object)splitKeyValue.get(dimName));
                    }
                }
                CheckDesObj checkDesObj = this.creatCheckDes(row, columns, rowDim);
                desInfoList.add(checkDesObj);
            }
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        return desInfoList;
    }

    private static void appendQueryFilters(CheckDesQueryParam checkDesQueryParam, NvwaQueryModel queryModel, Map<String, ColumnModelDefine> columnMap, DimensionValueSet dimensionValueSet, DimensionChanger dimensionChanger) {
        List<String> formulaSchemeKeyList = checkDesQueryParam.getFormulaSchemeKey();
        List<String> formKeyList = checkDesQueryParam.getFormKey();
        if (!CollectionUtils.isEmpty(formulaSchemeKeyList)) {
            queryModel.getColumnFilters().put(columnMap.get("CKD_FORMULASCHEMEKEY"), formulaSchemeKeyList);
        }
        if (!CollectionUtils.isEmpty(formKeyList)) {
            queryModel.getColumnFilters().put(columnMap.get("CKD_FORMKEY"), formKeyList);
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getFormulaKey())) {
            queryModel.getColumnFilters().put(columnMap.get("CKD_FORMULACODE"), checkDesQueryParam.getFormulaKey());
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getRecordId())) {
            queryModel.getColumnFilters().put(columnMap.get("CKD_RECID"), checkDesQueryParam.getRecordId());
        }
        if (checkDesQueryParam.getDesCheckPass() != null) {
            if (checkDesQueryParam.getDesCheckPass().booleanValue()) {
                queryModel.getColumnFilters().put(columnMap.get("CKD_STATE"), DesCheckState.PASS.getCode());
            } else {
                queryModel.getColumnFilters().put(columnMap.get("CKD_STATE"), DesCheckState.FAIL.getCode());
            }
        }
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String columnCode = dimensionChanger.getColumnCode(dimensionValueSet.getName(i));
            if (columnCode == null) continue;
            queryModel.getColumnFilters().put(columnMap.get(columnCode), dimensionValueSet.getValue(i));
        }
    }

    private CheckDesObj creatCheckDes(INvwaDataRow row, List<ColumnModelDefine> columns, DimensionValueSet rowDim) {
        CheckDesObj checkDes = new CheckDesObj();
        rowDim.clearValue("CKD_RECID");
        block34: for (int i = 0; i < columns.size(); ++i) {
            String value = "";
            try {
                String str;
                Object obj = row.getValue(i);
                if ((obj instanceof String || obj instanceof Number) && StringUtils.isNotEmpty((String)(str = AbstractData.valueOf((Object)obj, (int)6).getAsString()))) {
                    value = str;
                }
            }
            catch (RuntimeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            switch (columns.get(i).getCode()) {
                case "CKD_RECID": {
                    checkDes.setRecordId(value);
                    continue block34;
                }
                case "CKD_FORMULASCHEMEKEY": {
                    checkDes.setFormulaSchemeKey(value);
                    continue block34;
                }
                case "CKD_FORMKEY": {
                    checkDes.setFormKey(value);
                    continue block34;
                }
                case "CKD_FORMULACODE": {
                    checkDes.setFormulaExpressionKey(value);
                    continue block34;
                }
                case "CKD_GLOBROW": {
                    int globRow = 0;
                    if (StringUtils.isNotEmpty((String)value)) {
                        globRow = new Double(value).intValue();
                    }
                    checkDes.setGlobRow(globRow);
                    continue block34;
                }
                case "CKD_GLOBCOL": {
                    int globCol = 0;
                    if (StringUtils.isNotEmpty((String)value)) {
                        globCol = new Double(value).intValue();
                    }
                    checkDes.setGlobCol(globCol);
                    continue block34;
                }
                case "CKD_DIMSTR": {
                    String[] dims;
                    if (!StringUtils.isNotEmpty((String)value)) continue block34;
                    checkDes.setFloatId(value);
                    for (String dim : dims = value.split(";")) {
                        String[] dimValues = dim.split(":");
                        if (dimValues.length != 2) continue;
                        rowDim.setValue(dimValues[0], (Object)dimValues[1]);
                    }
                    continue block34;
                }
                case "CKD_USERKEY": {
                    checkDes.getCheckDescription().setUserId(value);
                    continue block34;
                }
                case "CKD_UPDATETIME": {
                    try {
                        String formatStr = "yyyy-MM-dd HH:mm:ss";
                        SimpleDateFormat bf = new SimpleDateFormat(formatStr);
                        value = AbstractData.valueOf((Object)row.getValue(i), (int)2).getAsString();
                        Date date = bf.parse(value);
                        checkDes.getCheckDescription().setUpdateTime(date.toInstant());
                    }
                    catch (RuntimeException | ParseException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    continue block34;
                }
                case "CKD_USERNAME": {
                    checkDes.getCheckDescription().setUserNickName(value);
                    continue block34;
                }
                case "CKD_DESCRIPTION": {
                    checkDes.getCheckDescription().setDescription(value);
                    continue block34;
                }
                case "CKD_STATE": {
                    int state = 0;
                    if (StringUtils.isNotEmpty((String)value)) {
                        state = Integer.parseInt(value);
                    }
                    checkDes.getCheckDescription().setState(DesCheckState.getByCode(state));
                    continue block34;
                }
            }
        }
        try {
            FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(checkDes.getFormulaExpressionKey().substring(0, 36));
            if (formulaDefine != null) {
                checkDes.setFormulaCode(formulaDefine.getCode());
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        checkDes.setDimensionSet(DimensionUtil.getDimensionSet(rowDim));
        return checkDes;
    }

    private DimensionValueSet getRowDim(INvwaDataRow row, List<ColumnModelDefine> rowKeyColumns, DimensionChanger dimensionChanger) {
        DimensionValueSet valueSet = new DimensionValueSet();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            String code = rowKeyColumn.getCode();
            String keyValue = (String)row.getKeyValue(rowKeyColumn);
            if ("CKD_RECID".equals(code)) {
                valueSet.setValue("CKD_RECID", (Object)keyValue);
                continue;
            }
            if ("VERSIONID".equals(code)) {
                valueSet.setValue("VERSIONID", (Object)keyValue);
                continue;
            }
            String dimName = dimensionChanger.getDimensionName(code);
            valueSet.setValue(dimName, (Object)keyValue);
        }
        return valueSet;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchSaveFormulaCheckDes(CheckDesBatchSaveObj checkDesBatchSaveObj) {
        this.batchSaveFormulaCheckDes(checkDesBatchSaveObj, false, true);
    }

    private void batchSaveFormulaCheckDes(CheckDesBatchSaveObj checkDesBatchSaveObj, boolean allInsert, boolean needValid) {
        INvwaDataUpdator tableDataUpdator;
        CheckDesQueryParam checkDesQueryParam = checkDesBatchSaveObj.getCheckDesQueryParam();
        DimensionCollection dimensionCollection = checkDesQueryParam.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return;
        }
        List<String> formulaSchemeKeyList = checkDesQueryParam.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeyList);
        List<String> fmSchemeEntityNames = this.entityUtil.getFmSchemeEntities(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        this.checkCKDBeforeBS(checkDesBatchSaveObj, needValid, formScheme);
        this.filterData(checkDesBatchSaveObj);
        if (CollectionUtils.isEmpty(checkDesBatchSaveObj.getCheckDesObjs())) {
            return;
        }
        String tableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        Map<String, String> splitKeyValue = this.splitTableHelper.getSplitKeyValue(formScheme, tableName);
        TableModelDefine checkResultDataTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == checkResultDataTable) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u8868\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        String tableId = checkResultDataTable.getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> columnMap = new HashMap<String, ColumnModelDefine>();
        CheckErrorDescriptionServiceImpl.appendQueryColumns(columns, columnIndexMap, columnMap, queryModel);
        if (!allInsert) {
            this.deleteData(checkDesBatchSaveObj.getCheckDesObjs(), (ColumnModelDefine)columnMap.get("CKD_RECID"));
            this.ckdChangeNotifier.afterDelete(checkDesBatchSaveObj.getCheckDesObjs());
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        try {
            tableDataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u6570\u636e\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        boolean rowKeysContainVer = this.rowKeysContainVer(tableDataUpdator);
        String dw = this.entityUtil.getContextMainDimId(formScheme.getDw());
        EntityData dwEntity = this.entityUtil.getEntity(dw);
        Set<String> unitIdSet = this.getUnitIdSet(dimensionValueSet, dwEntity, formScheme, dw);
        List<CheckDesObj> checkDesObjs = checkDesBatchSaveObj.getCheckDesObjs();
        NpContext context = NpContextHolder.getContext();
        String userId = context.getUserId();
        String userNickname = this.paramUtil.getUserNickNameById(userId);
        Date updateTime = new Date();
        boolean updateCurUsrTime = checkDesBatchSaveObj.isUpdateCurUsrTime();
        Map<String, CheckDesObj> mapByPK = this.validCheckDesObjs(checkDesObjs);
        int saveNum = 0;
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        ArrayList<String> saveDwValues = new ArrayList<String>();
        String savePeriod = String.valueOf(dimensionValueSet.getValue(this.periodEngineService.getPeriodAdapter().getPeriodDimensionName()));
        Map<String, ColumnModelDefine> keyCodeColMap = tableDataUpdator.getRowKeyColumns().stream().collect(Collectors.toMap(IModelDefineItem::getCode, o -> o));
        for (CheckDesObj checkDesObj : mapByPK.values()) {
            String dwId = checkDesObj.getDimensionSet().get(dwEntity.getDimensionName()).getValue();
            if (!unitIdSet.contains(dwId)) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u672a\u627e\u5230\u5355\u4f4d " + dwId);
                continue;
            }
            FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(checkDesObj.getFormulaExpressionKey().substring(0, 36));
            if (formulaDefine == null) {
                logger.debug("\u672a\u627e\u5230\u516c\u5f0f{}", (Object)checkDesObj.getFormulaExpressionKey());
                continue;
            }
            saveDwValues.add(dwId);
            CheckErrorDescriptionServiceImpl.updateUserTime(checkDesObj, updateCurUsrTime, userId, userNickname, updateTime);
            try {
                INvwaDataRow insertRow = tableDataUpdator.addInsertRow();
                ++saveNum;
                CheckErrorDescriptionServiceImpl.setCKDKeyValue(checkDesObj, dimensionChanger, insertRow, keyCodeColMap, rowKeysContainVer, columnMap, splitKeyValue);
                this.setFormulaCheckDes(insertRow, checkDesObj, columnIndexMap, fmSchemeEntityNames, formulaDefine);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        logDimensionCollection.setDw(dwEntity.getKey(), saveDwValues.toArray(new String[0]));
        logDimensionCollection.setPeriod(formScheme.getDateTime(), savePeriod);
        try {
            tableDataUpdator.commitChanges(dataAccessContext);
            this.ckdChangeNotifier.afterInsert(new ArrayList<CheckDesObj>(mapByPK.values()));
            this.logHelper.ckdInfo(formScheme.getTaskKey(), logDimensionCollection, "\u51fa\u9519\u8bf4\u660e\u6279\u91cf\u4fdd\u5b58\u6210\u529f", "\u4fdd\u5b58\u6761\u6570\uff1a" + saveNum);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            this.logHelper.ckdError(formScheme.getTaskKey(), logDimensionCollection, "\u51fa\u9519\u8bf4\u660e\u6279\u91cf\u4fdd\u5b58\u5931\u8d25", "\u4fdd\u5b58\u6761\u6570\uff1a" + saveNum);
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
    }

    private void checkCKDBeforeBS(CheckDesBatchSaveObj checkDesBatchSaveObj, boolean needValid, FormSchemeDefine formScheme) {
        if (needValid) {
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            this.updateCheckState(ckdValCollectorCache, formScheme.getKey(), checkDesBatchSaveObj.getCheckDesObjs());
        }
    }

    private static void appendQueryColumns(List<ColumnModelDefine> columns, Map<String, Integer> columnIndexMap, Map<String, ColumnModelDefine> columnMap, NvwaQueryModel queryModel) {
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = columns.get(i);
            columnIndexMap.put(columnModelDefine.getCode(), i);
            columnMap.put(columnModelDefine.getCode(), columnModelDefine);
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
    }

    private static void setCKDKeyValue(CheckDesObj checkDesObj, DimensionChanger dimensionChanger, INvwaDataRow insertRow, Map<String, ColumnModelDefine> keyCodeColMap, boolean rowKeysContainVer, Map<String, ColumnModelDefine> columnMap, Map<String, String> splitKeyValue) {
        Map<String, DimensionValue> dimensionSet = checkDesObj.getDimensionSet();
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            ColumnModelDefine dimCol = dimensionChanger.getColumn(entry.getKey());
            if (dimCol == null) continue;
            String value = entry.getValue().getValue();
            if (StringUtils.isEmpty((String)value) && "VERSIONID".equals(dimCol.getCode())) {
                insertRow.setKeyValue(dimCol, (Object)"00000000-0000-0000-0000-000000000000");
                continue;
            }
            insertRow.setKeyValue(dimCol, (Object)value);
        }
        insertRow.setKeyValue(keyCodeColMap.get("CKD_RECID"), (Object)checkDesObj.getRecordId());
        if (rowKeysContainVer && !dimensionSet.containsKey("VERSIONID")) {
            insertRow.setKeyValue(columnMap.get("VERSIONID"), (Object)"00000000-0000-0000-0000-000000000000");
        }
        if (!CollectionUtils.isEmpty(splitKeyValue)) {
            for (String dimName : splitKeyValue.keySet()) {
                insertRow.setKeyValue(keyCodeColMap.get(dimName), (Object)splitKeyValue.get(dimName));
            }
        }
    }

    private static void updateUserTime(CheckDesObj checkDesObj, boolean updateCurUsrTime, String userId, String userNickname, Date updateTime) {
        if (updateCurUsrTime || StringUtils.isEmpty((String)checkDesObj.getCheckDescription().getUserId())) {
            checkDesObj.getCheckDescription().setUserId(userId);
        }
        if (updateCurUsrTime || StringUtils.isEmpty((String)checkDesObj.getCheckDescription().getUserNickName())) {
            checkDesObj.getCheckDescription().setUserNickName(userNickname);
        }
        if (updateCurUsrTime || checkDesObj.getCheckDescription().getUpdateTime() == null) {
            checkDesObj.getCheckDescription().setUpdateTime(updateTime.toInstant());
        }
    }

    @NotNull
    private Set<String> getUnitIdSet(DimensionValueSet dimensionValueSet, EntityData dwEntity, FormSchemeDefine formScheme, String dw) {
        List<String> dwKeys = new ArrayList<String>();
        Object dwValue = dimensionValueSet.getValue(dwEntity.getDimensionName());
        if (dwValue instanceof String) {
            dwKeys.add((String)dwValue);
        } else if (dwValue instanceof List) {
            dwKeys.addAll((List)dwValue);
        }
        if (dwKeys.isEmpty()) {
            Date entityQueryVersionDate = this.entityUtil.getEntityQueryVersionDate(formScheme.getDateTime(), dimensionValueSet);
            dwKeys = this.entityUtil.getAllEntityRows(dw, entityQueryVersionDate, null).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        return new HashSet<String>(dwKeys);
    }

    private void filterData(CheckDesBatchSaveObj checkDesBatchSaveObj) {
        CheckDesQueryParam checkDesQueryParam = checkDesBatchSaveObj.getCheckDesQueryParam();
        List<CheckDesObj> checkDesObjs = checkDesBatchSaveObj.getCheckDesObjs();
        Set dimensionValueSets = checkDesQueryParam.getDimensionCollection().getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(dimensionValueSets)) {
            ArrayList<String> filterDimNames = new ArrayList<String>();
            DimensionValueSet dimensionValueSet = (DimensionValueSet)dimensionValueSets.stream().findAny().get();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                filterDimNames.add(dimensionValueSet.getName(i));
            }
            checkDesObjs.removeIf(o -> !dimensionValueSets.contains(o.getDimensionValueSet(filterDimNames)));
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getFormulaSchemeKey())) {
            HashSet<String> formulaSchemeKeys = new HashSet<String>(checkDesQueryParam.getFormulaSchemeKey());
            checkDesObjs.removeIf(o -> !formulaSchemeKeys.contains(o.getFormulaSchemeKey()));
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getFormKey())) {
            HashSet<String> formKeys = new HashSet<String>(checkDesQueryParam.getFormKey());
            checkDesObjs.removeIf(o -> !formKeys.contains(o.getFormKey()));
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getFormulaKey())) {
            HashSet<String> formulaParsedExpressionKeys = new HashSet<String>(checkDesQueryParam.getFormulaKey());
            checkDesObjs.removeIf(o -> !formulaParsedExpressionKeys.contains(o.getFormulaExpressionKey()));
        }
        if (!CollectionUtils.isEmpty(checkDesQueryParam.getRecordId())) {
            HashSet<String> recordIds = new HashSet<String>(checkDesQueryParam.getRecordId());
            checkDesObjs.removeIf(o -> !recordIds.contains(o.getRecordId()));
        }
        if (checkDesQueryParam.getDesCheckPass() != null) {
            if (checkDesQueryParam.getDesCheckPass().booleanValue()) {
                checkDesObjs.removeIf(o -> DesCheckState.PASS != o.getCheckDescription().getState());
            } else {
                checkDesObjs.removeIf(o -> DesCheckState.FAIL != o.getCheckDescription().getState());
            }
        }
    }

    private boolean rowKeysContainVer(INvwaDataUpdator updator) {
        for (ColumnModelDefine keyColumn : updator.getAllKeyColumns()) {
            if (!"VERSIONID".equals(keyColumn.getCode())) continue;
            return true;
        }
        return false;
    }

    private void deleteData(List<CheckDesObj> checkDesObjs, ColumnModelDefine recid) {
        if (!CollectionUtils.isEmpty(checkDesObjs)) {
            NvwaQueryModel queryModel = new NvwaQueryModel();
            queryModel.getColumns().add(new NvwaQueryColumn(recid));
            Set recidSet = checkDesObjs.stream().map(CheckDesObj::getRecordId).collect(Collectors.toSet());
            queryModel.getColumnFilters().put(recid, new ArrayList(recidSet));
            INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            try {
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                INvwaDataUpdator tableDataUpdator = updatableDataAccess.openForUpdate(context);
                tableDataUpdator.deleteAll();
                tableDataUpdator.commitChanges(context);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u6570\u636e\u5220\u9664\u5f02\u5e38");
                throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_DEL_EXC.getCode());
            }
        }
    }

    @Override
    @Deprecated
    public void batchSaveFormulaCheckDes(BatchSaveCheckDesParam batchSaveCheckDesParam) {
        this.batchSaveCKD(batchSaveCheckDesParam);
    }

    private CKDSaveResult executeBatchSave(BatchSaveCheckDesParam batchSaveCheckDesParam, CheckResult checkResult) {
        boolean success = true;
        LinkedHashSet<String> errorMsgSet = new LinkedHashSet<String>();
        if (checkResult != null && !checkResult.getResultData().isEmpty()) {
            HashSet<String> flmKeySet = new HashSet<String>();
            CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
            CheckDesQueryParam queryInfo = new CheckDesQueryParam();
            CheckResultQueryParam checkResultQueryParam = batchSaveCheckDesParam.getCheckResultQueryParam();
            queryInfo.setDimensionCollection(checkResultQueryParam.getDimensionCollection());
            List<String> formulaSchemeKeysList = checkResultQueryParam.getFormulaSchemeKeys();
            queryInfo.setFormulaSchemeKey(formulaSchemeKeysList);
            checkDesBatchSaveObj.setCheckDesQueryParam(queryInfo);
            List<Map<String, DimensionValue>> dimensionList = checkResult.getDimensionList();
            ArrayList<CheckDesObj> desInfos = new ArrayList<CheckDesObj>();
            checkDesBatchSaveObj.setCheckDesObjs(desInfos);
            ArrayList<Integer> checkTypes = new ArrayList<Integer>(checkResultQueryParam.getCheckTypes().keySet());
            List<String> formulaIds = CheckErrorDescriptionServiceImpl.getFormulaIds(checkResultQueryParam);
            NpContext context = NpContextHolder.getContext();
            String userId = context.getUserId();
            String userNickname = this.paramUtil.getUserNickNameById(userId);
            Date updateTime = new Date();
            FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeysList);
            List<String> fmSchemeEntityNames = this.entityUtil.getFmSchemeEntities(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
            boolean coverOriginalDes = batchSaveCheckDesParam.isCoverOriginalDes();
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            for (CheckResultData checkResultData : checkResult.getResultData()) {
                DesCheckState desCheckState;
                FormulaData formula;
                if (CheckErrorDescriptionServiceImpl.filterBeforeBS(checkResultData, success, flmKeySet, formula = checkResultData.getFormulaData(), coverOriginalDes, checkTypes, formulaIds)) continue;
                Map<String, DimensionValue> dimensionValueMap = dimensionList.get(checkResultData.getDimensionIndex());
                CheckDesObj formulaCheckDesInfo = new CheckDesObj();
                formulaCheckDesInfo.setFormulaSchemeKey(formula.getFormulaSchemeKey());
                formulaCheckDesInfo.setFormKey(formula.getFormKey());
                formulaCheckDesInfo.setFormulaExpressionKey(formula.getParsedExpressionKey());
                formulaCheckDesInfo.setFormulaCode(formula.getCode());
                formulaCheckDesInfo.setGlobCol(formula.getGlobCol());
                formulaCheckDesInfo.setGlobRow(formula.getGlobRow());
                formulaCheckDesInfo.setDimensionSet(dimensionValueMap);
                formulaCheckDesInfo.setFloatId(this.getDimStr(dimensionValueMap, fmSchemeEntityNames));
                formulaCheckDesInfo.setRecordId(checkResultData.getRecordId());
                CheckDescription descriptionInfo = new CheckDescription();
                formulaCheckDesInfo.setCheckDescription(descriptionInfo);
                descriptionInfo.setUserId(userId);
                descriptionInfo.setUserNickName(userNickname);
                descriptionInfo.setUpdateTime(updateTime.toInstant());
                descriptionInfo.setDescription(batchSaveCheckDesParam.getDescription());
                CheckDesContext checkDesContext = new CheckDesContext(formScheme.getKey(), formulaCheckDesInfo.getFormulaSchemeKey());
                CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
                CKDValidInfo validInfo = ckdValidateCollector.getValidInfo(formulaCheckDesInfo);
                if (!validInfo.isPass()) {
                    success = false;
                    errorMsgSet.addAll(validInfo.getErrorMsgList());
                    desCheckState = DesCheckState.FAIL;
                } else {
                    desCheckState = DesCheckState.PASS;
                }
                descriptionInfo.setState(desCheckState);
                if (!success) continue;
                desInfos.add(formulaCheckDesInfo);
            }
            if (success) {
                this.batchSaveFormulaCheckDes(checkDesBatchSaveObj, !coverOriginalDes, false);
            }
        }
        CKDSaveResult ckdSaveResult = new CKDSaveResult();
        ckdSaveResult.setSuccess(success);
        ckdSaveResult.setErrorMsgList(new ArrayList<String>(errorMsgSet));
        return ckdSaveResult;
    }

    private static boolean filterBeforeBS(CheckResultData checkResultData, boolean success, Set<String> flmKeySet, FormulaData formula, boolean coverOriginalDes, List<Integer> checkTypes, List<String> formulaIds) {
        if (!success && !flmKeySet.add(formula.getFormulaSchemeKey())) {
            return true;
        }
        if (!coverOriginalDes && checkResultData.getCheckDescription() != null && StringUtils.isNotEmpty((String)checkResultData.getCheckDescription().getDescription())) {
            return true;
        }
        if (!checkTypes.isEmpty() && !checkTypes.contains(formula.getCheckType())) {
            return true;
        }
        return !formulaIds.isEmpty() && !formulaIds.contains(formula.getKey());
    }

    @NotNull
    private static List<String> getFormulaIds(CheckResultQueryParam checkResultQueryParam) {
        ArrayList<String> formulaIds = new ArrayList<String>();
        if (Mode.FORMULA == checkResultQueryParam.getMode()) {
            formulaIds.addAll(checkResultQueryParam.getRangeKeys());
        }
        return formulaIds;
    }

    private CheckResult queryCheckResult(BatchSaveCheckDesParam batchSaveCheckDesParam) {
        CheckResult checkResult = null;
        ActionEnum actionEnum = batchSaveCheckDesParam.getActionEnum();
        String actionId = batchSaveCheckDesParam.getActionId();
        CheckResultQueryParam checkResultQueryParam = batchSaveCheckDesParam.getCheckResultQueryParam();
        if (StringUtils.isNotEmpty((String)actionId)) {
            checkResultQueryParam.setBatchId(actionId);
        }
        if (ActionEnum.CHECK == actionEnum) {
            CheckParam checkParam = new CheckParam();
            checkParam.setDimensionCollection(checkResultQueryParam.getDimensionCollection());
            checkParam.setFormulaSchemeKey(checkResultQueryParam.getFormulaSchemeKeys().get(0));
            checkParam.setMode(checkResultQueryParam.getMode());
            checkParam.setRangeKeys(checkResultQueryParam.getRangeKeys());
            checkResult = this.checkService.check(checkParam);
            if (checkResult != null && !CollectionUtils.isEmpty(checkResultQueryParam.getCheckTypes())) {
                checkResult.getResultData().removeIf(o -> !checkResultQueryParam.getCheckTypes().containsKey(o.getFormulaData().getCheckType()));
            }
        } else if (ActionEnum.ALL_CHECK == actionEnum) {
            checkResult = this.checkResultService.queryAllCheckResult(checkResultQueryParam, actionId);
        } else if (ActionEnum.BATCH_CHECK == actionEnum) {
            checkResult = this.checkResultService.queryBatchCheckResult(checkResultQueryParam);
        }
        ICKDQueryCKRFilter filter = batchSaveCheckDesParam.getFilter();
        if (filter != null) {
            checkResult = filter.filter(checkResult);
        }
        return checkResult;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public CKDSaveResult saveCKD(CheckDesObj checkDesObj) {
        INvwaDataUpdator tableDataUpdator;
        String formulaSchemeKey = checkDesObj.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        String tableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        Map<String, String> splitKeyValue = this.splitTableHelper.getSplitKeyValue(formScheme, tableName);
        TableModelDefine checkResultDataTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == checkResultDataTable) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u8868\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        this.fillInfoOnSave(checkDesObj);
        CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
        CheckDesContext checkDesContext = new CheckDesContext(formScheme.getKey(), formulaSchemeKey);
        CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
        CKDValidInfo validInfo = ckdValidateCollector.getValidInfo(checkDesObj);
        if (!validInfo.isPass()) {
            CKDSaveResult ckdSaveResult = new CKDSaveResult();
            ckdSaveResult.setSuccess(false);
            ckdSaveResult.setErrorMsgList(validInfo.getErrorMsgList());
            return ckdSaveResult;
        }
        checkDesObj.getCheckDescription().setState(DesCheckState.PASS);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        String tableId = checkResultDataTable.getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < columns.size(); ++i) {
            columnIndexMap.put(((ColumnModelDefine)columns.get(i)).getCode(), i);
        }
        for (ColumnModelDefine columnModelDefine : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(tableId, "CKD_FORMULASCHEMEKEY"), formulaSchemeKey);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        try {
            tableDataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u6570\u636e\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(checkDesObj.getDimensionSet());
        LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dimensionValueSet);
        FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(checkDesObj.getFormulaExpressionKey().substring(0, 36));
        if (formulaDefine == null) {
            logger.error("\u672a\u627e\u5230\u516c\u5f0f{}", (Object)checkDesObj.getFormulaExpressionKey());
            this.logHelper.ckdError(formScheme.getTaskKey(), logDimension, "\u51fa\u9519\u8bf4\u660e\u4fdd\u5b58\u5931\u8d25", checkDesObj.getCheckDescription().getUserNickName() + "\uff1a" + checkDesObj.getCheckDescription().getDescription());
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        try {
            INvwaDataRow updateOrInsertRow = tableDataUpdator.addUpdateOrInsertRow();
            Map<String, ColumnModelDefine> keyCodeColMap = tableDataUpdator.getRowKeyColumns().stream().collect(Collectors.toMap(IModelDefineItem::getCode, o -> o));
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                ColumnModelDefine dimCol = dimensionChanger.getColumn(dimensionValueSet.getName(i));
                if (dimCol == null) continue;
                Object value = dimensionValueSet.getValue(i);
                if ((value == null || StringUtils.isEmpty((String)value.toString())) && "VERSIONID".equals(dimCol.getCode())) {
                    updateOrInsertRow.setKeyValue(dimCol, (Object)"00000000-0000-0000-0000-000000000000");
                    continue;
                }
                updateOrInsertRow.setKeyValue(dimCol, value);
            }
            updateOrInsertRow.setKeyValue(keyCodeColMap.get("CKD_RECID"), (Object)checkDesObj.getRecordId());
            if (!CollectionUtils.isEmpty(splitKeyValue)) {
                for (String dimName : splitKeyValue.keySet()) {
                    updateOrInsertRow.setKeyValue(keyCodeColMap.get(dimName), (Object)splitKeyValue.get(dimName));
                }
            }
            List<String> formSchemeEntityNames = this.entityUtil.getFmSchemeEntities(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
            this.setFormulaCheckDes(updateOrInsertRow, checkDesObj, columnIndexMap, formSchemeEntityNames, formulaDefine);
            tableDataUpdator.commitChanges(dataAccessContext);
            this.ckdChangeNotifier.afterUpdateOrInsert(Collections.singletonList(checkDesObj));
            this.logHelper.ckdInfo(formScheme.getTaskKey(), logDimension, "\u51fa\u9519\u8bf4\u660e\u4fdd\u5b58\u6210\u529f", checkDesObj.getCheckDescription().getUserNickName() + "\uff1a" + checkDesObj.getCheckDescription().getDescription());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            this.logHelper.ckdError(formScheme.getTaskKey(), logDimension, "\u51fa\u9519\u8bf4\u660e\u4fdd\u5b58\u5931\u8d25", checkDesObj.getCheckDescription().getUserNickName() + "\uff1a" + checkDesObj.getCheckDescription().getDescription());
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_SAVE_EXC.getCode());
        }
        CKDSaveResult ckdSaveResult = new CKDSaveResult();
        ckdSaveResult.setSuccess(true);
        return ckdSaveResult;
    }

    private void fillInfoOnSave(CheckDesObj checkDesObj) {
        FormulaDefine formulaDefine;
        if (StringUtils.isEmpty((String)checkDesObj.getFormulaCode()) && (formulaDefine = this.formulaRunTimeController.queryFormulaDefine(checkDesObj.getFormulaExpressionKey().substring(0, 36))) != null) {
            checkDesObj.setFormulaCode(formulaDefine.getCode());
        }
    }

    @Override
    @Deprecated
    public CheckDesObj saveFormulaCheckDes(CheckDesObj checkDesObj) {
        this.saveCKD(checkDesObj);
        return checkDesObj;
    }

    @Override
    public CKDSaveResult batchSaveCKD(BatchSaveCheckDesParam batchSaveCheckDesParam) {
        CheckResult checkResult = this.queryCheckResult(batchSaveCheckDesParam);
        return this.executeBatchSave(batchSaveCheckDesParam, checkResult);
    }

    private void setFormulaCheckDes(INvwaDataRow row, CheckDesObj checkDesObj, Map<String, Integer> columnIndexMap, List<String> formSchemeEntityNames, FormulaDefine formulaDefine) {
        row.setValue(columnIndexMap.get("CKD_FORMULASCHEMEKEY").intValue(), (Object)formulaDefine.getFormulaSchemeKey());
        row.setValue(columnIndexMap.get("CKD_FORMKEY").intValue(), (Object)checkDesObj.getFormKey());
        row.setValue(columnIndexMap.get("CKD_FORMULACODE").intValue(), (Object)checkDesObj.getFormulaExpressionKey());
        row.setValue(columnIndexMap.get("CKD_GLOBROW").intValue(), (Object)String.valueOf(checkDesObj.getGlobRow()));
        row.setValue(columnIndexMap.get("CKD_GLOBCOL").intValue(), (Object)String.valueOf(checkDesObj.getGlobCol()));
        String dimStr = this.getDimStr(checkDesObj.getDimensionSet(), formSchemeEntityNames);
        row.setValue(columnIndexMap.get("CKD_DIMSTR").intValue(), (Object)dimStr);
        row.setValue(columnIndexMap.get("CKD_USERKEY").intValue(), (Object)checkDesObj.getCheckDescription().getUserId());
        if (checkDesObj.getCheckDescription().getUpdateTime() != null) {
            row.setValue(columnIndexMap.get("CKD_UPDATETIME").intValue(), (Object)new Time(Date.from(checkDesObj.getCheckDescription().getUpdateTime()).getTime()));
        } else {
            row.setValue(columnIndexMap.get("CKD_UPDATETIME").intValue(), (Object)new Time(new Date().getTime()));
        }
        row.setValue(columnIndexMap.get("CKD_USERNAME").intValue(), (Object)checkDesObj.getCheckDescription().getUserNickName());
        row.setValue(columnIndexMap.get("CKD_DESCRIPTION").intValue(), (Object)checkDesObj.getCheckDescription().getDescription());
        row.setValue(columnIndexMap.get("CKD_STATE").intValue(), (Object)checkDesObj.getCheckDescription().getState().getCode());
    }

    private String getDimStr(Map<String, DimensionValue> dimensionSet, List<String> formSchemeEntityNames) {
        StringBuilder dimStrBuilder = new StringBuilder();
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            String dimensionName = entry.getKey();
            DimensionValue dimensionValue = entry.getValue();
            if ("ALLCKR_ASYNCTASKID".equals(dimensionName) || "CKR_BATCH_ID".equals(dimensionName) || "VERSIONID".equals(dimensionName) || "RECORDKEY".equals(dimensionName) || "ID".equals(dimensionName) || formSchemeEntityNames.contains(dimensionName)) continue;
            dimStrBuilder.append(dimensionName).append(":").append(dimensionValue.getValue()).append(";");
        }
        String id = null;
        if (dimensionSet.containsKey("ID")) {
            id = dimensionSet.get("ID").getValue();
        } else if (dimensionSet.containsKey("RECORDKEY")) {
            id = dimensionSet.get("RECORDKEY").getValue();
        }
        if (StringUtils.isEmpty((String)id)) {
            dimStrBuilder.append("ID:null;");
        } else {
            dimStrBuilder.append("ID:").append(id).append(";");
        }
        return dimStrBuilder.toString();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void removeFormulaCheckDes(CheckDesObj checkDesObj) {
        INvwaDataUpdator updatableDataSet;
        String formulaSchemeKey = checkDesObj.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(checkDesObj.getDimensionSet());
        LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dimensionValueSet);
        String tableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        TableModelDefine checkResultDataTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == checkResultDataTable) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u8868\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_DEL_EXC.getCode());
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        String tableId = checkResultDataTable.getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        for (ColumnModelDefine columnModelDefine : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        queryModel.getColumnFilters().put(this.dataModelService.getColumnModelDefineByCode(tableId, "CKD_FORMULASCHEMEKEY"), formulaSchemeKey);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        try {
            updatableDataSet = updatableDataAccess.openForUpdate(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u5ba1\u6838\u8bf4\u660e\u6570\u636e\u5931\u8d25");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_DEL_EXC.getCode());
        }
        List rowKeyColumns = updatableDataSet.getRowKeyColumns();
        ArrayList<Object> keyValList = new ArrayList<Object>();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            String code = rowKeyColumn.getCode();
            if ("CKD_RECID".equals(code)) {
                keyValList.add(checkDesObj.getRecordId());
                continue;
            }
            Object value = dimensionValueSet.getValue(dimensionChanger.getDimensionName(code));
            if ((value == null || StringUtils.isEmpty((String)value.toString())) && "VERSIONID".equals(code)) {
                keyValList.add("00000000-0000-0000-0000-000000000000");
                continue;
            }
            keyValList.add(value);
        }
        ArrayKey arrayKey = new ArrayKey(keyValList);
        try {
            updatableDataSet.addDeleteRow(arrayKey);
            updatableDataSet.commitChanges(dataAccessContext);
            this.ckdChangeNotifier.afterDelete(Collections.singletonList(checkDesObj));
            this.logHelper.ckdInfo(formScheme.getTaskKey(), logDimension, "\u51fa\u9519\u8bf4\u660e\u5220\u9664\u6210\u529f", "");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            this.logHelper.ckdError(formScheme.getTaskKey(), logDimension, "\u51fa\u9519\u8bf4\u660e\u5220\u9664\u5931\u8d25", "");
            throw new LogicMappingException(ExceptionEnum.CHECK_ERROR_DES_DEL_EXC.getCode());
        }
    }

    @Override
    public void batchDelCheckDes(BatchDelCheckDesParam param) {
        if (!this.validateDelParam(param)) {
            logger.warn("\u6279\u91cf\u5220\u9664\u51fa\u9519\u8bf4\u660e\u6761\u4ef6\u5168\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\uff01");
            return;
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(ckdTableName);
        if (tableModel == null) {
            throw new NotFoundTableDefineException(new String[]{ckdTableName});
        }
        String tableId = tableModel.getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        HashMap<String, ColumnModelDefine> columnMap = new HashMap<String, ColumnModelDefine>();
        for (ColumnModelDefine c : columns) {
            c.getCode();
            columnMap.put(c.getCode(), c);
        }
        this.ckdChangeNotifier.beforeDelete(param);
        if (this.dimEmpty(param.getDimensionCollection())) {
            NvwaQueryModel queryModel = CheckErrorDescriptionServiceImpl.getDelQueryModel(param, columnMap);
            this.delAllByQueryModel(queryModel);
        } else {
            List<String> depDimNames = this.entityUtil.getDepOnDwDims(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckdTableName);
            try {
                List<DimensionValueSet> dimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(param.getDimensionCollection(), depDimNames);
                for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
                    NvwaQueryModel queryModel = CheckErrorDescriptionServiceImpl.getDelQueryModel(param, columnMap);
                    for (int i = 0; i < dimensionValueSet.size(); ++i) {
                        String columnCode = dimensionChanger.getColumnCode(dimensionValueSet.getName(i));
                        if (columnCode == null) continue;
                        ColumnModelDefine column = (ColumnModelDefine)columnMap.get(columnCode);
                        queryModel.getColumns().add(new NvwaQueryColumn(column));
                        queryModel.getColumnFilters().put(column, dimensionValueSet.getValue(i));
                    }
                    this.delAllByQueryModel(queryModel);
                }
            }
            catch (LogicCheckedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void delAllByQueryModel(NvwaQueryModel queryModel) {
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator nvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            nvwaDataUpdator.deleteAll();
            nvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u6279\u91cf\u5220\u9664\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private static NvwaQueryModel getDelQueryModel(BatchDelCheckDesParam param, Map<String, ColumnModelDefine> columnMap) {
        ColumnModelDefine column;
        NvwaQueryModel queryModel = new NvwaQueryModel();
        if (!CollectionUtils.isEmpty(param.getFormulaSchemeKeys())) {
            column = columnMap.get("CKD_FORMULASCHEMEKEY");
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            queryModel.getColumnFilters().put(column, param.getFormulaSchemeKeys());
        }
        if (!CollectionUtils.isEmpty(param.getFormKeys())) {
            column = columnMap.get("CKD_FORMKEY");
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            queryModel.getColumnFilters().put(column, param.getFormKeys());
        }
        if (!CollectionUtils.isEmpty(param.getParsedFormulaKeys())) {
            column = columnMap.get("CKD_FORMULACODE");
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            queryModel.getColumnFilters().put(column, param.getParsedFormulaKeys());
        }
        if (!CollectionUtils.isEmpty(param.getRecordIds())) {
            column = columnMap.get("CKD_RECID");
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            queryModel.getColumnFilters().put(column, param.getRecordIds());
        }
        if (param.getDesCheckPass() != null) {
            column = columnMap.get("CKD_STATE");
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            if (param.getDesCheckPass().booleanValue()) {
                queryModel.getColumnFilters().put(column, DesCheckState.PASS.getCode());
            } else {
                queryModel.getColumnFilters().put(column, DesCheckState.FAIL.getCode());
            }
        }
        return queryModel;
    }

    private boolean validateDelParam(BatchDelCheckDesParam param) {
        return !this.dimEmpty(param.getDimensionCollection()) || !CollectionUtils.isEmpty(param.getRecordIds()) || !CollectionUtils.isEmpty(param.getFormulaSchemeKeys()) || !CollectionUtils.isEmpty(param.getFormKeys()) || !CollectionUtils.isEmpty(param.getParsedFormulaKeys()) || param.getDesCheckPass() != null;
    }

    private boolean dimEmpty(DimensionCollection dimensionCollection) {
        return dimensionCollection == null || CollectionUtils.isEmpty(dimensionCollection.getDimensionCombinations());
    }

    @Override
    public DesCheckResult desCheckResult(CheckResultQueryParam checkResultQueryParam) {
        ArrayList<DesCheckData> checkDataList = new ArrayList<DesCheckData>();
        CheckResult checkResult = this.checkResultService.queryBatchCheckResult(checkResultQueryParam);
        HashSet<String> unitKeys = new HashSet<String>();
        int charNum = this.systemOptionUtil.getCheckCharNum();
        int charMaxNum = this.systemOptionUtil.getCheckCharMaxNum();
        for (CheckResultData checkResultData : checkResult.getResultData()) {
            String description = checkResultData.getCheckDescription().getDescription();
            if (description != null && description.length() >= charNum && description.length() <= charMaxNum || unitKeys.contains(checkResultData.getUnitKey())) continue;
            DesCheckData desCheckData = new DesCheckData();
            desCheckData.setUnitCode(checkResultData.getUnitCode());
            desCheckData.setUnitTitle(checkResultData.getUnitTitle());
            checkDataList.add(desCheckData);
            unitKeys.add(checkResultData.getUnitKey());
        }
        DesCheckResult desCheckResult = new DesCheckResult();
        desCheckResult.setCheckCharMinNum(charNum);
        desCheckResult.setCheckCharMaxNum(charMaxNum);
        desCheckResult.setDesCheckData(checkDataList);
        return desCheckResult;
    }

    @Override
    public List<FormulaData> queryCheckResultFormulas(BatchSaveCheckDesParam batchSaveCheckDesParam) {
        CheckResultQueryParam checkResultQueryParam = batchSaveCheckDesParam.getCheckResultQueryParam();
        String actionId = batchSaveCheckDesParam.getActionId();
        if (StringUtils.isNotEmpty((String)actionId)) {
            checkResultQueryParam.setBatchId(actionId);
        }
        if (ActionEnum.BATCH_CHECK == batchSaveCheckDesParam.getActionEnum()) {
            return this.getBatchCheckFormulaData(checkResultQueryParam);
        }
        return this.getCheckFormulaData(batchSaveCheckDesParam);
    }

    @NotNull
    private ArrayList<FormulaData> getCheckFormulaData(BatchSaveCheckDesParam batchSaveCheckDesParam) {
        LinkedHashMap<String, FormulaData> formulaMap = new LinkedHashMap<String, FormulaData>();
        CheckResult checkResult = this.queryCheckResult(batchSaveCheckDesParam);
        if (checkResult != null) {
            for (CheckResultData checkResultData : checkResult.getResultData()) {
                FormulaData formula = checkResultData.getFormulaData();
                if (formulaMap.containsKey(formula.getKey())) continue;
                formulaMap.put(formula.getKey(), formula);
            }
        }
        return new ArrayList<FormulaData>(formulaMap.values());
    }

    @NotNull
    private List<FormulaData> getBatchCheckFormulaData(CheckResultQueryParam checkResultQueryParam) {
        List<String> formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeys);
        if (this.nrdbHelper.isEnableNrdb()) {
            return this.getNrDBBCFormulaData(checkResultQueryParam, formScheme);
        }
        return this.getNormalBCFormulaData(checkResultQueryParam, formScheme);
    }

    @NotNull
    private List<FormulaData> getNormalBCFormulaData(CheckResultQueryParam checkResultQueryParam, FormSchemeDefine formScheme) {
        SqlModel fmlSqlModel;
        try {
            fmlSqlModel = this.getFmlSqlModel(formScheme, checkResultQueryParam);
        }
        catch (LogicCheckedException e) {
            logger.error(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc(), e);
            return Collections.emptyList();
        }
        try (CheckResultSqlBuilder checkResultSqlBuilder = new CheckResultSqlBuilder(formScheme, fmlSqlModel, this.dataSource);){
            List<Map<String, Object>> maps = checkResultSqlBuilder.listFmlParsedExpressionKey();
            List<FormulaData> list = this.listFmlData(maps);
            return list;
        }
    }

    @NotNull
    private List<FormulaData> getNrDBBCFormulaData(CheckResultQueryParam checkResultQueryParam, FormSchemeDefine formScheme) {
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByName(ckrTableName);
        if (ckrTable == null) {
            throw new NotFoundTableDefineException(new String[]{ckrTableName + "\u672a\u627e\u5230"});
        }
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(ckrTableName);
        DimensionCollection dimensionCollection = checkResultQueryParam.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return Collections.emptyList();
        }
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(ckrTable.getID());
        Map<String, ColumnModelDefine> colMap = columnModelDefinesByTable.stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity()));
        List columns = queryModel.getColumns();
        NvwaQueryColumn fmlSchemeQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKR_FORMULASCHEMEKEY"));
        fmlSchemeQueryCol.setAggrType(AggrType.MIN);
        NvwaQueryColumn formulaQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKR_FORMULACODE"));
        formulaQueryCol.setAggrType(AggrType.MIN);
        columns.add(fmlSchemeQueryCol);
        columns.add(formulaQueryCol);
        List groupByColumns = queryModel.getGroupByColumns();
        groupByColumns.add(0);
        groupByColumns.add(1);
        List orderByItems = queryModel.getOrderByItems();
        ColumnModelDefine formOrderCol = (ColumnModelDefine)colMap.get("CKR_FORMORDER");
        orderByItems.add(new OrderByItem(formOrderCol));
        NvwaQueryColumn formOrderQueryCol = new NvwaQueryColumn(formOrderCol);
        formOrderQueryCol.setAggrType(AggrType.MIN);
        columns.add(formOrderQueryCol);
        ColumnModelDefine formulaOrderCol = (ColumnModelDefine)colMap.get("CKR_FORMULAORDER");
        orderByItems.add(new OrderByItem(formulaOrderCol));
        NvwaQueryColumn formulaOrderQueryCol = new NvwaQueryColumn(formulaOrderCol);
        formulaOrderQueryCol.setAggrType(AggrType.MIN);
        columns.add(formulaOrderQueryCol);
        this.checkResultDataUtil.fillCKRQueryFilter(checkResultQueryParam, ckrTableName, ckdTableName, dimensionValueSet, queryModel, colMap);
        queryModel.setMainTableName(ckrTableName);
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        try {
            MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(dataAccessContext);
            LinkedHashMap<String, FormulaData> result = new LinkedHashMap<String, FormulaData>();
            for (DataRow dataRow : dataRows) {
                String fmlParsedExpressionKey;
                String formulaSchemeKey = dataRow.getString(0);
                FormulaData fmlData = this.getFmlData(formulaSchemeKey, fmlParsedExpressionKey = dataRow.getString(1));
                if (result.containsKey(fmlData.getKey())) continue;
                result.put(fmlData.getKey(), fmlData);
            }
            return new ArrayList<FormulaData>(result.values());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<FormulaData> listFmlData(List<Map<String, Object>> maps) {
        if (CollectionUtils.isEmpty(maps)) {
            return Collections.emptyList();
        }
        LinkedHashMap<String, FormulaData> result = new LinkedHashMap<String, FormulaData>();
        for (Map<String, Object> map : maps) {
            String fmlParsedExpressionKey = (String)map.get("CKR_FORMULACODE");
            String formulaSchemeKey = (String)map.get("CKR_FORMULASCHEMEKEY");
            FormulaData fmlData = this.getFmlData(formulaSchemeKey, fmlParsedExpressionKey);
            if (result.containsKey(fmlData.getKey())) continue;
            result.put(fmlData.getKey(), fmlData);
        }
        return new ArrayList<FormulaData>(result.values());
    }

    private FormulaData getFmlData(String formulaSchemeKey, String fmlParsedExpressionKey) {
        IParsedExpression parsedExpression = this.expressionService.getParsedExpression(formulaSchemeKey, fmlParsedExpressionKey);
        FormulaData formulaData = new FormulaData();
        Formula source = parsedExpression.getSource();
        formulaData.setKey(source.getId());
        formulaData.setCode(source.getCode());
        String formKey = parsedExpression.getFormKey();
        formulaData.setFormKey(StringUtils.isEmpty((String)formKey) ? "00000000-0000-0000-0000-000000000000" : formKey);
        formulaData.setParsedExpressionKey(parsedExpression.getKey());
        formulaData.setMeaning(source.getMeanning());
        formulaData.setCheckType(source.getChecktype());
        return formulaData;
    }

    private SqlModel getFmlSqlModel(FormSchemeDefine formScheme, CheckResultQueryParam checkResultQueryParam) throws LogicCheckedException {
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByName(ckrTableName);
        if (ckrTable == null) {
            throw new NotFoundTableDefineException(new String[]{ckrTableName + "\u672a\u627e\u5230"});
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        List<String> depDimNames = this.entityUtil.getDepOnDwDims(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        SqlModel sqlModel = new SqlModel();
        sqlModel.setMainTableName(ckrTableName);
        sqlModel.setBatchId(checkResultQueryParam.getBatchId());
        this.checkResultDataUtil.appendColumnFilters(checkResultQueryParam, sqlModel);
        Map<Integer, Boolean> mainCheckTypes = checkResultQueryParam.getCheckTypes();
        if (checkResultQueryParam.isQueryByDim()) {
            List<DimensionValueSet> dimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(checkResultQueryParam.getDimensionCollection(), depDimNames);
            this.checkResultDataUtil.appendDimFilters(dimensionChanger, sqlModel.getDimFilters(), dimensionValueSets.get(0));
            List<UnionModel> unionModels = this.checkResultDataUtil.appendUnionModel(mainCheckTypes, dimensionValueSets, checkResultQueryParam.getCustomCondition(), dimensionChanger, depDimNames);
            sqlModel.setUnionModels(unionModels);
        }
        sqlModel.setFilterCondition(checkResultQueryParam.getFilterCondition());
        sqlModel.setCheckTypes(mainCheckTypes);
        sqlModel.setQueryCondition(checkResultQueryParam.getQueryCondition());
        List<String> mainColumns = sqlModel.getMainColumns();
        mainColumns.add("CKR_FORMULASCHEMEKEY");
        mainColumns.add("CKR_FORMULACODE");
        List<String> orderByColumns = sqlModel.getOrderByColumns();
        orderByColumns.add("CKR_FORMORDER");
        orderByColumns.add("CKR_FORMULAORDER");
        return sqlModel;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void transferCheckDes(CKDTransferPar param) throws Exception {
        List<String> formKeys = param.getFormKeys();
        if (CollectionUtils.isEmpty(formKeys)) {
            logger.debug("\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u65f6\u62a5\u8868\u8303\u56f4\u4e0d\u660e\u786e\uff0c\u505c\u6b62\u6267\u884c");
            return;
        }
        CKDTransferMap transferMap = param.getTransferMap();
        if (transferMap == null || CollectionUtils.isEmpty(transferMap.getMainDimValueMap()) || CollectionUtils.isEmpty(transferMap.getBizKeyOrderMap())) {
            logger.debug("\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u65f6\u6620\u5c04\u5173\u7cfb\u4e3a\u7a7a\uff0c\u505c\u6b62\u6267\u884c");
            return;
        }
        String formSchemeKey = param.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(ckdTableName);
        if (table == null) {
            logger.error("\u51fa\u9519\u8bf4\u660e\u8868{}\u4e0d\u5b58\u5728\uff0c\u505c\u6b62\u540c\u6b65\u51fa\u9519\u8bf4\u660e", (Object)ckdTableName);
        } else {
            DimensionCollection srcDimensionCollection = param.getSrcDimensionCollection();
            DimensionValueSet srcDimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(srcDimensionCollection);
            if (srcDimensionValueSet == null) {
                logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
                return;
            }
            String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(formScheme.getDateTime());
            NpContext context = NpContextHolder.getContext();
            String userId = context.getUserId();
            String userNickname = this.paramUtil.getUserNickNameById(userId);
            Instant updateTime = new Date().toInstant();
            CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
            checkDesQueryParam.setDimensionCollection(srcDimensionCollection);
            List<String> formulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formScheme.getKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            checkDesQueryParam.setFormulaSchemeKey(formulaSchemes);
            checkDesQueryParam.setFormKey(formKeys);
            List<CheckDesObj> checkDesObjs = this.queryFormulaCheckDes(checkDesQueryParam);
            EntityData dwEntity = this.entityUtil.getEntity(formScheme.getDw());
            String dwDimName = dwEntity.getDimensionName();
            Map<String, Map<String, String>> dwDimMap = this.getDWDimMap(formScheme, new ArrayList<String>(transferMap.getMainDimValueMap().values()), String.valueOf(srcDimensionValueSet.getValue(periodDimensionName)), param.getIgnDimNames());
            List<CheckDesObj> mappedDesObjs = this.processDataMap(checkDesObjs, dwDimName, transferMap, userId, userNickname, updateTime, param.getIgnDimNames(), dwDimMap);
            List<CheckDesObj> deDupResult = mappedDesObjs.stream().filter(CommonUtils.distinctByKey(CheckDesObj::getRecordId)).collect(Collectors.toList());
            DimensionCollection desDimension = this.getDesDimension(srcDimensionValueSet, param, dwDimName);
            List<DimensionAccessFormInfo.AccessFormInfo> accessForms = this.getAccessFormInfos(formKeys, formScheme, desDimension, AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
            if (CollectionUtils.isEmpty(accessForms)) {
                logger.debug("\u65e0\u6570\u636e\u6743\u9650\uff0c\u505c\u6b62\u6267\u884c");
                return;
            }
            for (DimensionAccessFormInfo.AccessFormInfo accessForm : accessForms) {
                Map dimensions = accessForm.getDimensions();
                List accessFormKeys = accessForm.getFormKeys();
                if (CollectionUtils.isEmpty(dimensions) || CollectionUtils.isEmpty(accessFormKeys)) continue;
                logger.debug("\u5f00\u59cb\u5220\u9664\u76ee\u6807\u7ef4\u5ea6{}-\u62a5\u8868{}\u4e0b\u7684\u51fa\u9519\u8bf4\u660e", (Object)dimensions, (Object)accessFormKeys);
                this.deleteByDwForm(table, dimensions, accessFormKeys);
                FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(formScheme.getDw()), dimensions);
                DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensions, formSchemeKey, (SpecificDimBuilder)fixedDimBuilder);
                logger.debug("\u5f00\u59cb\u5c06\u6765\u6e90\u51fa\u9519\u8bf4\u660e\u540c\u6b65\u5230\u76ee\u6807\u7ef4\u5ea6\u62a5\u8868");
                this.batchSaveCKD(checkDesQueryParam, deDupResult, dimensionCollection, accessFormKeys);
            }
        }
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getAccessFormInfos(List<String> formKeys, FormSchemeDefine formScheme, DimensionCollection desDimension, AccessLevel.FormAccessLevel formAccessLevel) {
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(desDimension);
        accessFormParam.setTaskKey(formScheme.getTaskKey());
        accessFormParam.setFormSchemeKey(formScheme.getKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(formAccessLevel);
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        DimensionAccessFormInfo dimensionAccessFormInfo = dataAccessFormService.getBatchAccessForms(accessFormParam);
        return dimensionAccessFormInfo.getAccessForms();
    }

    private DimensionCollection getDesDimension(DimensionValueSet srcDimensionValueSet, CKDTransferPar transferPar, String dwDimName) {
        HashSet<String> dwSet = new HashSet<String>(transferPar.getTransferMap().getMainDimValueMap().values());
        DimensionValueSet mergeDimensionValueSet = new DimensionValueSet(srcDimensionValueSet);
        mergeDimensionValueSet.clearValue(dwDimName);
        if (dwSet.size() == 1) {
            mergeDimensionValueSet.setValue(dwDimName, dwSet.stream().findFirst().get());
        } else {
            mergeDimensionValueSet.setValue(dwDimName, new ArrayList<String>(dwSet));
        }
        if (!CollectionUtils.isEmpty(transferPar.getIgnDimNames())) {
            for (String ignDimName : transferPar.getIgnDimNames()) {
                mergeDimensionValueSet.clearValue(ignDimName);
                mergeDimensionValueSet.setValue(ignDimName, (Object)"");
            }
        }
        return this.dimensionCollectionUtil.getDimensionCollection(mergeDimensionValueSet, transferPar.getFormSchemeKey());
    }

    private void batchSaveCKD(CheckDesQueryParam checkDesQueryParam, List<CheckDesObj> checkDesObjs, DimensionCollection dimensionCollection, List<String> formKeys) {
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        checkDesQueryParam.setFormKey(formKeys);
        checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
        checkDesBatchSaveObj.setCheckDesObjs(checkDesObjs);
        this.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
    }

    private void deleteByDwForm(TableModelDefine ckdTable, Map<String, DimensionValue> dimensions, List<String> formKeys) throws Exception {
        List cols = this.dataModelService.getColumnModelDefinesByTable(ckdTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        cols.forEach(o -> colMap.put(o.getCode(), (ColumnModelDefine)o));
        ColumnModelDefine recidCol = (ColumnModelDefine)colMap.get("CKD_RECID");
        queryModel.getColumns().add(new NvwaQueryColumn(recidCol));
        int recidIndex = 0;
        queryModel.getColumns().add(new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKD_DIMSTR")));
        int dimStrIndex = 1;
        this.appendDelByDwFormFilters(ckdTable, dimensions, formKeys, queryModel, colMap);
        INvwaDataAccess readOnlyDataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataRows = readOnlyDataAccess.executeQuery(new DataAccessContext(this.dataModelService));
        if (dataRows == null || dataRows.isEmpty()) {
            return;
        }
        ArrayList<String> deleteKeys = new ArrayList<String>();
        for (DataRow dataRow : dataRows) {
            String[] dimKV;
            String[] split;
            String recid = dataRow.getString(recidIndex);
            String dimStr = dataRow.getString(dimStrIndex);
            if (StringUtils.isNotEmpty((String)dimStr) && (split = dimStr.split(";")).length == 1 && (dimKV = split[0].split(":")).length == 2 && "ID".equalsIgnoreCase(dimKV[0]) && "null".equalsIgnoreCase(dimKV[1])) continue;
            deleteKeys.add(recid);
        }
        if (deleteKeys.isEmpty()) {
            return;
        }
        this.deleteByRecid(recidCol, deleteKeys);
    }

    private void appendDelByDwFormFilters(TableModelDefine ckdTable, Map<String, DimensionValue> dimensions, List<String> formKeys, NvwaQueryModel queryModel, Map<String, ColumnModelDefine> colMap) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckdTable.getName());
        queryModel.getColumnFilters().put(colMap.get("CKD_FORMKEY"), formKeys);
        for (Map.Entry<String, DimensionValue> e : dimensions.entrySet()) {
            String value;
            String columnCode = dimensionChanger.getColumnCode(e.getKey());
            if (StringUtils.isEmpty((String)columnCode) || StringUtils.isEmpty((String)(value = e.getValue().getValue()))) continue;
            List<String> listValue = Arrays.asList(value.split(";"));
            if (listValue.size() == 1) {
                queryModel.getColumnFilters().put(colMap.get(columnCode), listValue.get(0));
                continue;
            }
            queryModel.getColumnFilters().put(colMap.get(columnCode), new ArrayList<String>(listValue));
        }
    }

    private void deleteByRecid(ColumnModelDefine recidCol, List<String> deleteKeys) throws Exception {
        NvwaQueryModel delModel = new NvwaQueryModel();
        delModel.getColumns().add(new NvwaQueryColumn(recidCol));
        delModel.getColumnFilters().put(recidCol, deleteKeys);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(delModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
        dataUpdator.deleteAll();
        dataUpdator.commitChanges(context);
    }

    private List<CheckDesObj> processDataMap(List<CheckDesObj> checkDesObjs, String dwDimName, CKDTransferMap transferMap, String userId, String userNickname, Instant updateTime, List<String> ignDimNames, Map<String, Map<String, String>> dwDimMap) {
        ArrayList<CheckDesObj> result = new ArrayList<CheckDesObj>();
        HashMap dwDimCache = new HashMap();
        for (CheckDesObj checkDesObj : checkDesObjs) {
            Map<String, DimensionValue> dimensionSet = checkDesObj.getDimensionSet();
            String oldDw = dimensionSet.get(dwDimName).getValue();
            String newDw = transferMap.getMainDimValueMap().get(oldDw);
            if (!StringUtils.isNotEmpty((String)newDw)) continue;
            DimensionValue dw = new DimensionValue();
            dw.setName(dwDimName);
            dw.setValue(newDw);
            dimensionSet.put(dwDimName, dw);
            if (!CollectionUtils.isEmpty(ignDimNames)) {
                for (String ignDimName : ignDimNames) {
                    DimensionValue dimensionValue = dimensionSet.get(ignDimName);
                    dimensionValue.setValue(dwDimMap.get(newDw).get(ignDimName));
                }
            }
            String oldBiz = dimensionSet.get("ID").getValue();
            String newBiz = transferMap.getBizKeyOrderMap().get(oldBiz);
            if (StringUtils.isNotEmpty((String)newBiz)) {
                DimensionValue biz = new DimensionValue();
                biz.setName("ID");
                biz.setValue(newBiz);
                dimensionSet.put("ID", biz);
            } else if (!"null".equals(oldBiz) || StringUtils.isNotEmpty((String)checkDesObj.getFloatId()) && checkDesObj.getFloatId().split(";").length == 1) continue;
            checkDesObj.getCheckDescription().setUserId(userId);
            checkDesObj.getCheckDescription().setUserNickName(userNickname);
            checkDesObj.getCheckDescription().setUpdateTime(updateTime);
            checkDesObj.setRecordId(null);
            result.add(checkDesObj);
        }
        return result;
    }

    private Map<String, Map<String, String>> getDWDimMap(FormSchemeDefine formSchemeDefine, List<String> dwValues, String period, List<String> dimNames) throws Exception {
        HashMap<String, Map<String, String>> dwDimMap = new HashMap<String, Map<String, String>>();
        if (!CollectionUtils.isEmpty(dimNames)) {
            ReferRelation referRelation;
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
            String dataScheme = taskDefine.getDataScheme();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            String dwName = this.entityMetaService.getDimensionName(formSchemeDefine.getDw());
            dimensionValueSet.setValue(dwName, dwValues);
            dimensionValueSet.setValue(this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(formSchemeDefine.getDateTime()), (Object)period);
            Date entityQueryVersionDate = this.entityUtil.getEntityQueryVersionDate(formSchemeDefine.getDateTime(), dimensionValueSet);
            IEntityQuery entityQuery = this.entityUtil.getEntityQuery(formSchemeDefine.getDw(), entityQueryVersionDate, dimensionValueSet, null);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            IEntityTable entityTable = entityQuery.executeReader((IContext)context);
            List allRows = entityTable.getAllRows();
            String[] split = formSchemeDefine.getDims().split(";");
            HashMap<String, ReferRelation> dimNameRefMap = new HashMap<String, ReferRelation>();
            for (String dimID : split) {
                String dimensionName = this.entityMetaService.getDimensionName(dimID);
                referRelation = this.entityDataQueryAssist.buildReferRelation(dataScheme, dimID);
                dimNameRefMap.put(dimensionName, referRelation);
            }
            for (IEntityRow r : allRows) {
                HashMap<String, String> map = new HashMap<String, String>();
                for (String dimName : dimNames) {
                    referRelation = (ReferRelation)dimNameRefMap.get(dimName);
                    IEntityRefer refer = referRelation.getRefer();
                    map.put(dimName, r.getAsString(refer.getOwnField()));
                }
                dwDimMap.put(r.getEntityKeyData(), map);
            }
        }
        return dwDimMap;
    }

    @Override
    public void reviseCheckDesKey() {
        logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u4e0b\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5f00\u59cb");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            List taskDefines = this.runtimeView.getAllReportTaskDefines();
            for (TaskDefine taskDefine : taskDefines) {
                List formSchemes = this.runtimeView.queryFormSchemeByTask(taskDefine.getKey());
                for (FormSchemeDefine formScheme : formSchemes) {
                    executorService.execute(() -> {
                        logger.info("{}-{}\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5f00\u59cb", (Object)formScheme.getKey(), (Object)formScheme.getFormSchemeCode());
                        try {
                            this.reviseCKDRECIDService.revise(formScheme);
                            logger.info("{}-{}\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5b8c\u6210", (Object)formScheme.getKey(), (Object)formScheme.getFormSchemeCode());
                        }
                        catch (Exception e) {
                            logger.error(String.format("%s-%s\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5f02\u5e38:%s", formScheme.getKey(), formScheme.getFormSchemeCode(), e.getMessage()), e);
                        }
                    });
                }
            }
            executorService.shutdown();
            while (!executorService.isTerminated()) {
            }
            logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u4e0b\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5b8c\u6210");
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u4e0b\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u6267\u884c\u65f6\u5f02\u5e38:" + e.getMessage(), e);
        }
    }

    @Override
    public void checkDes(CheckDesParam param) {
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        checkDesQueryParam.setDimensionCollection(param.getDimensionCollection());
        checkDesQueryParam.setFormulaSchemeKey(param.getFormulaSchemeKey());
        checkDesQueryParam.setFormKey(param.getFormKey());
        checkDesQueryParam.setFormulaKey(param.getFormulaKey());
        List<CheckDesObj> checkDesObjs = this.queryFormulaCheckDes(checkDesQueryParam);
        if (!CollectionUtils.isEmpty(checkDesObjs)) {
            ArrayList<CheckDesObj> updateList = new ArrayList<CheckDesObj>();
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            for (CheckDesObj checkDesObj : checkDesObjs) {
                DesCheckState newState;
                CheckDesContext checkDesContext = new CheckDesContext(param.getFormSchemeKey(), checkDesObj.getFormulaSchemeKey());
                CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
                DesCheckState state = checkDesObj.getCheckDescription().getState();
                if (state == (newState = CheckErrorDescriptionServiceImpl.getDesCheckState(ckdValidateCollector, checkDesObj))) continue;
                checkDesObj.getCheckDescription().setState(newState);
                updateList.add(checkDesObj);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
                checkDesBatchSaveObj.setCheckDesObjs(updateList);
                checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
                this.batchSaveFormulaCheckDes(checkDesBatchSaveObj, false, false);
            }
        }
    }

    private Map<String, CheckDesObj> validCheckDesObjs(List<CheckDesObj> checkDesObjs) {
        if (!CollectionUtils.isEmpty(checkDesObjs)) {
            return checkDesObjs.stream().collect(Collectors.toMap(CheckDesObj::getRecordId, Function.identity(), (o1, o2) -> o2));
        }
        return Collections.emptyMap();
    }

    private void updateCheckState(CKDValCollectorCache ckdValCollectorCache, String formSchemeKey, List<CheckDesObj> checkDesObjs) {
        for (CheckDesObj checkDesObj : checkDesObjs) {
            CheckDesContext checkDesContext = new CheckDesContext(formSchemeKey, checkDesObj.getFormulaSchemeKey());
            CKDValidateCollector ckdValidateCollector = ckdValCollectorCache.getCKDValidateCollector(checkDesContext);
            DesCheckState desCheckState = CheckErrorDescriptionServiceImpl.getDesCheckState(ckdValidateCollector, checkDesObj);
            checkDesObj.getCheckDescription().setState(desCheckState);
        }
    }

    private static DesCheckState getDesCheckState(CKDValidateCollector ckdValidateCollector, CheckDesObj checkDesObj) {
        boolean validPass = ckdValidateCollector.validPass(checkDesObj);
        return validPass ? DesCheckState.PASS : DesCheckState.FAIL;
    }
}

