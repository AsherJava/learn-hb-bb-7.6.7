/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CustomQueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaNode;
import com.jiuqi.nr.data.logic.internal.obj.CheckQueryContext;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.obj.QueryConParam;
import com.jiuqi.nr.data.logic.internal.provider.LinkEnvProvider;
import com.jiuqi.nr.data.logic.internal.query.CheckResultSqlBuilder;
import com.jiuqi.nr.data.logic.internal.query.SqlModel;
import com.jiuqi.nr.data.logic.internal.query.UnionModel;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckResultDataUtil {
    private static final String MSG_RESULT_TRANSFER_ERROR_PREFIX = "\u5ba1\u6838\u7ed3\u679c\u6570\u636e\u8f6c\u6362\u5f02\u5e38\uff0c\u51fa\u9519\u539f\u56e0\uff1a";
    private static final Logger logger = LoggerFactory.getLogger(CheckResultDataUtil.class);
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private SplitCheckTableHelper splitTableHelper;
    @Autowired
    private EntityUtil entityUtil;

    public SqlModel getSqlModel(CheckResultQueryParam checkResultQueryParam, String ckrTableName, String ckdTableName, List<String> bizKeyColumnNames, DimensionChanger dimensionChanger, List<String> depDimNames, Date queryDate) throws LogicCheckedException {
        SqlModel sqlModel = new SqlModel();
        sqlModel.setBatchId(checkResultQueryParam.getBatchId());
        sqlModel.setQueryDate(queryDate);
        sqlModel.setMainTableName(ckrTableName);
        sqlModel.setJoinTableName(ckdTableName);
        this.appendPageInfo(sqlModel, checkResultQueryParam.getPagerInfo());
        this.appendColumnFilters(checkResultQueryParam, sqlModel);
        Map<Integer, Boolean> mainCheckTypes = checkResultQueryParam.getCheckTypes();
        if (checkResultQueryParam.isQueryByDim()) {
            List<DimensionValueSet> dimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(checkResultQueryParam.getDimensionCollection(), depDimNames);
            this.appendDimFilters(dimensionChanger, sqlModel.getDimFilters(), dimensionValueSets.get(0));
            List<UnionModel> unionModels = this.appendUnionModel(mainCheckTypes, dimensionValueSets, checkResultQueryParam.getCustomCondition(), dimensionChanger, depDimNames);
            sqlModel.setUnionModels(unionModels);
        }
        sqlModel.setFilterCondition(checkResultQueryParam.getFilterCondition());
        sqlModel.setCheckTypes(mainCheckTypes);
        sqlModel.setQueryCondition(checkResultQueryParam.getQueryCondition());
        List<String> mainColumns = sqlModel.getMainColumns();
        mainColumns.addAll(bizKeyColumnNames);
        mainColumns.add("CKR_FORMULASCHEMEKEY");
        mainColumns.add("CKR_FORMKEY");
        mainColumns.add("CKR_FORMULACODE");
        mainColumns.add("CKR_GLOBROW");
        mainColumns.add("CKR_GLOBCOL");
        mainColumns.add("CKR_DIMSTR");
        mainColumns.add("CKR_ERRORDESC");
        mainColumns.add("CKR_LEFT");
        mainColumns.add("CKR_RIGHT");
        mainColumns.add("CKR_BALANCE");
        List<String> joinColumns = sqlModel.getJoinColumns();
        joinColumns.add("CKD_USERKEY");
        joinColumns.add("CKD_UPDATETIME");
        joinColumns.add("CKD_USERNAME");
        joinColumns.add("CKD_DESCRIPTION");
        joinColumns.add("CKD_STATE");
        List<String> orderByColumns = sqlModel.getOrderByColumns();
        switch (checkResultQueryParam.getGroupType()) {
            case formula: 
            case form_formula: {
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                orderByColumns.add("CKR_UNITORDER");
                break;
            }
            case checktype_form: {
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                orderByColumns.add("CKR_UNITORDER");
                break;
            }
            case checktype_unit: {
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                break;
            }
            case UNIT_CHECKTYPE: {
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                break;
            }
            default: {
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
            }
        }
        orderByColumns.add("CKR_GLOBROW");
        orderByColumns.add("CKR_GLOBCOL");
        this.appendDimOrderColumns(checkResultQueryParam, dimensionChanger, orderByColumns);
        orderByColumns.add("CKR_DIMSTR");
        return sqlModel;
    }

    private void appendDimOrderColumns(CheckResultQueryParam checkResultQueryParam, DimensionChanger dimensionChanger, List<String> orderByColumns) {
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(checkResultQueryParam.getFormulaSchemeKeys());
        List<String> orderedDimColCodes = this.getOrderedDimColCodes(formScheme, dimensionChanger);
        if (CollectionUtils.isEmpty(orderedDimColCodes)) {
            return;
        }
        orderByColumns.addAll(orderedDimColCodes);
    }

    public NvwaQueryModel getCKRQueryModel(CheckQueryContext checkQueryContext, boolean filter) {
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine ckrTable = checkQueryContext.getCkrTable();
        TableModelDefine ckdTable = checkQueryContext.getCkdTable();
        String ckrTableName = ckrTable.getName();
        String ckdTableName = ckdTable.getName();
        DimensionValueSet masterKey = checkQueryContext.getMasterKey();
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
        queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_STATE")));
        if (filter) {
            this.fillCKRQueryFilter(checkResultQueryParam, ckrTableName, ckdTableName, masterKey, queryModel, codeColumnMap);
        }
        List orderByItems = queryModel.getOrderByItems();
        switch (checkResultQueryParam.getGroupType()) {
            case formula: 
            case form_formula: {
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULAORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_UNITORDER")));
                break;
            }
            case checktype_form: {
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULACHECKTYPE")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULAORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_UNITORDER")));
                break;
            }
            case checktype_unit: {
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULACHECKTYPE")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_UNITORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULAORDER")));
                break;
            }
            case UNIT_CHECKTYPE: {
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_UNITORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULACHECKTYPE")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULAORDER")));
                break;
            }
            default: {
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_UNITORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMORDER")));
                orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_FORMULAORDER")));
            }
        }
        orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_GLOBROW")));
        orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_GLOBCOL")));
        this.appendDimOrderItems(checkQueryContext, codeColumnMap, queryModel);
        orderByItems.add(new OrderByItem(codeColumnMap.get("CKR_DIMSTR")));
        queryModel.setMainTableName(ckrTableName);
        return queryModel;
    }

    public void fillCKRQueryFilter(CheckResultQueryParam checkResultQueryParam, String ckrTableName, String ckdTableName, DimensionValueSet masterKey, NvwaQueryModel queryModel, Map<String, ColumnModelDefine> codeColumnMap) {
        QueryCondition queryCondition;
        Map<Integer, Boolean> checkTypes;
        List<String> rangeKeys;
        List<String> formulaSchemeKeys;
        queryModel.getColumnFilters().put(codeColumnMap.get("CKR_BATCH_ID"), checkResultQueryParam.getBatchId());
        if (checkResultQueryParam.isQueryByDim()) {
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
            for (int i = 0; i < masterKey.size(); ++i) {
                String columnCode = dimensionChanger.getColumnCode(masterKey.getName(i));
                queryModel.getColumnFilters().put(codeColumnMap.get(columnCode), masterKey.getValue(i));
            }
        }
        if (!CollectionUtils.isEmpty(formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys())) {
            queryModel.getColumnFilters().put(codeColumnMap.get("CKR_FORMULASCHEMEKEY"), formulaSchemeKeys);
        }
        if (!CollectionUtils.isEmpty(rangeKeys = checkResultQueryParam.getRangeKeys())) {
            if (checkResultQueryParam.getMode() == Mode.FORM) {
                queryModel.getColumnFilters().put(codeColumnMap.get("CKR_FORMKEY"), rangeKeys);
            } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                queryModel.getColumnFilters().put(codeColumnMap.get("CKR_FORMULAID"), rangeKeys);
            }
        }
        if (!CollectionUtils.isEmpty(checkTypes = checkResultQueryParam.getCheckTypes())) {
            ArrayList<Integer> checkTypeList = new ArrayList<Integer>(checkTypes.keySet());
            queryModel.getColumnFilters().put(codeColumnMap.get("CKR_FORMULACHECKTYPE"), checkTypeList);
        }
        if ((queryCondition = checkResultQueryParam.getQueryCondition()) != null) {
            com.jiuqi.nr.data.logic.facade.param.input.QueryContext queryContext = new com.jiuqi.nr.data.logic.facade.param.input.QueryContext();
            queryContext.setCkrTableName(ckrTableName);
            queryContext.setCkdTableName(ckdTableName);
            String fml = queryCondition.buildFml(queryContext);
            queryModel.setFilter(fml);
        }
    }

    public boolean needJoinCKD(QueryConParam queryConParam) {
        if (queryConParam.getDesCheckStateCode() != null || queryConParam.getCkdKeyWordPattern() != null) {
            return true;
        }
        Map<Integer, Boolean> checkTypes = queryConParam.getCheckTypes();
        if (!CollectionUtils.isEmpty(checkTypes)) {
            for (Boolean value : checkTypes.values()) {
                if (value == null) continue;
                return true;
            }
        }
        return false;
    }

    public SqlModel getGroupSqlModel(CheckResultQueryParam checkResultQueryParam, FormSchemeDefine formScheme, Date queryDate) throws LogicCheckedException {
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByName(ckrTableName);
        if (ckrTable == null) {
            throw new NotFoundTableDefineException(new String[]{ckrTableName + "\u672a\u627e\u5230"});
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        List<String> depDimNames = this.entityUtil.getDepOnDwDims(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        SqlModel sqlModel = new SqlModel();
        sqlModel.setBatchId(checkResultQueryParam.getBatchId());
        sqlModel.setQueryDate(queryDate);
        sqlModel.setMainTableName(ckrTableName);
        sqlModel.setJoinTableName(ckdTableName);
        this.appendPageInfo(sqlModel, checkResultQueryParam.getPagerInfo());
        this.appendColumnFilters(checkResultQueryParam, sqlModel);
        Map<Integer, Boolean> mainCheckTypes = checkResultQueryParam.getCheckTypes();
        if (checkResultQueryParam.isQueryByDim()) {
            List<DimensionValueSet> dimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(checkResultQueryParam.getDimensionCollection(), depDimNames);
            this.appendDimFilters(dimensionChanger, sqlModel.getDimFilters(), dimensionValueSets.get(0));
            List<UnionModel> unionModels = this.appendUnionModel(mainCheckTypes, dimensionValueSets, checkResultQueryParam.getCustomCondition(), dimensionChanger, depDimNames);
            sqlModel.setUnionModels(unionModels);
        }
        sqlModel.setFilterCondition(checkResultQueryParam.getFilterCondition());
        sqlModel.setCheckTypes(mainCheckTypes);
        sqlModel.setQueryCondition(checkResultQueryParam.getQueryCondition());
        List<String> mainColumns = sqlModel.getMainColumns();
        mainColumns.add("MDCODE");
        mainColumns.add("CKR_RECID");
        mainColumns.add("CKR_FORMULACHECKTYPE");
        mainColumns.add("CKR_FORMKEY");
        mainColumns.add("CKR_FORMULAID");
        List<String> orderByColumns = sqlModel.getOrderByColumns();
        Map<String, AggrType> groupByColumns = sqlModel.getGroupByColumns();
        switch (checkResultQueryParam.getGroupType()) {
            case formula: {
                groupByColumns.put("CKR_FORMULAID", AggrType.NONE);
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                orderByColumns.add("CKR_UNITORDER");
                break;
            }
            case form_formula: {
                groupByColumns.put("CKR_FORMKEY", AggrType.NONE);
                groupByColumns.put("CKR_FORMULAID", AggrType.NONE);
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                orderByColumns.add("CKR_UNITORDER");
                break;
            }
            case checktype_form: {
                groupByColumns.put("CKR_FORMULACHECKTYPE", AggrType.NONE);
                groupByColumns.put("CKR_FORMKEY", AggrType.NONE);
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                orderByColumns.add("CKR_UNITORDER");
                break;
            }
            case checktype_unit: {
                groupByColumns.put("CKR_FORMULACHECKTYPE", AggrType.NONE);
                groupByColumns.put("MDCODE", AggrType.NONE);
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                break;
            }
            case UNIT_CHECKTYPE: {
                groupByColumns.put("MDCODE", AggrType.NONE);
                groupByColumns.put("CKR_FORMULACHECKTYPE", AggrType.NONE);
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMULACHECKTYPE");
                break;
            }
            case UNIT_FORM: {
                groupByColumns.put("MDCODE", AggrType.NONE);
                groupByColumns.put("CKR_FORMKEY", AggrType.NONE);
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
                break;
            }
            default: {
                groupByColumns.put("MDCODE", AggrType.NONE);
                orderByColumns.add("CKR_UNITORDER");
                orderByColumns.add("CKR_FORMORDER");
                orderByColumns.add("CKR_FORMULAORDER");
            }
        }
        orderByColumns.add("CKR_GLOBROW");
        orderByColumns.add("CKR_GLOBCOL");
        orderByColumns.add("CKR_RECID");
        return sqlModel;
    }

    public NvwaQueryModel getGroupQueryModelWithoutFilter(CheckResultQueryParam checkResultQueryParam, FormSchemeDefine formScheme, Map<String, ColumnModelDefine> codeColumnMap) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        List columns = queryModel.getColumns();
        NvwaQueryColumn column0 = new NvwaQueryColumn(codeColumnMap.get("MDCODE"));
        column0.setAggrType(AggrType.MIN);
        NvwaQueryColumn column1 = new NvwaQueryColumn(codeColumnMap.get("CKR_RECID"));
        column1.setAggrType(AggrType.COUNT);
        ColumnModelDefine formulaCheckTypeCol = codeColumnMap.get("CKR_FORMULACHECKTYPE");
        NvwaQueryColumn column2 = new NvwaQueryColumn(formulaCheckTypeCol);
        column2.setAggrType(AggrType.MIN);
        NvwaQueryColumn column3 = new NvwaQueryColumn(codeColumnMap.get("CKR_FORMKEY"));
        column3.setAggrType(AggrType.MIN);
        NvwaQueryColumn column4 = new NvwaQueryColumn(codeColumnMap.get("CKR_FORMULAID"));
        column4.setAggrType(AggrType.MIN);
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        columns.add(column4);
        List orderByItems = queryModel.getOrderByItems();
        List groupByColumns = queryModel.getGroupByColumns();
        ColumnModelDefine formulaOrderCol = codeColumnMap.get("CKR_FORMULAORDER");
        ColumnModelDefine formOrderCol = codeColumnMap.get("CKR_FORMORDER");
        ColumnModelDefine unitOrderCol = codeColumnMap.get("CKR_UNITORDER");
        switch (checkResultQueryParam.getGroupType()) {
            case formula: {
                ((NvwaQueryColumn)columns.get(4)).setAggrType(AggrType.NONE);
                groupByColumns.add(4);
                orderByItems.add(new OrderByItem(formulaOrderCol));
                NvwaQueryColumn queryColumn = new NvwaQueryColumn(formulaOrderCol);
                queryColumn.setAggrType(AggrType.MIN);
                columns.add(queryColumn);
                break;
            }
            case form_formula: {
                ((NvwaQueryColumn)columns.get(3)).setAggrType(AggrType.NONE);
                groupByColumns.add(3);
                ((NvwaQueryColumn)columns.get(4)).setAggrType(AggrType.NONE);
                groupByColumns.add(4);
                orderByItems.add(new OrderByItem(formOrderCol));
                NvwaQueryColumn queryColumn1 = new NvwaQueryColumn(formOrderCol);
                queryColumn1.setAggrType(AggrType.MIN);
                columns.add(queryColumn1);
                orderByItems.add(new OrderByItem(formulaOrderCol));
                NvwaQueryColumn queryColumn2 = new NvwaQueryColumn(formulaOrderCol);
                queryColumn2.setAggrType(AggrType.MIN);
                columns.add(queryColumn2);
                break;
            }
            case checktype_form: {
                ((NvwaQueryColumn)columns.get(2)).setAggrType(AggrType.NONE);
                groupByColumns.add(2);
                ((NvwaQueryColumn)columns.get(3)).setAggrType(AggrType.NONE);
                groupByColumns.add(3);
                orderByItems.add(new OrderByItem(formulaCheckTypeCol));
                NvwaQueryColumn queryColumn3 = new NvwaQueryColumn(formulaCheckTypeCol);
                queryColumn3.setAggrType(AggrType.MIN);
                columns.add(queryColumn3);
                orderByItems.add(new OrderByItem(formOrderCol));
                NvwaQueryColumn queryColumn4 = new NvwaQueryColumn(formOrderCol);
                queryColumn4.setAggrType(AggrType.MIN);
                columns.add(queryColumn4);
                break;
            }
            case checktype_unit: {
                ((NvwaQueryColumn)columns.get(2)).setAggrType(AggrType.NONE);
                groupByColumns.add(2);
                ((NvwaQueryColumn)columns.get(0)).setAggrType(AggrType.NONE);
                groupByColumns.add(0);
                orderByItems.add(new OrderByItem(formulaCheckTypeCol));
                NvwaQueryColumn queryColumn5 = new NvwaQueryColumn(formulaCheckTypeCol);
                queryColumn5.setAggrType(AggrType.MIN);
                columns.add(queryColumn5);
                orderByItems.add(new OrderByItem(unitOrderCol));
                NvwaQueryColumn queryColumn6 = new NvwaQueryColumn(unitOrderCol);
                queryColumn6.setAggrType(AggrType.MIN);
                columns.add(queryColumn6);
                break;
            }
            case UNIT_CHECKTYPE: {
                ((NvwaQueryColumn)columns.get(0)).setAggrType(AggrType.NONE);
                groupByColumns.add(0);
                ((NvwaQueryColumn)columns.get(2)).setAggrType(AggrType.NONE);
                groupByColumns.add(2);
                orderByItems.add(new OrderByItem(unitOrderCol));
                NvwaQueryColumn unitOrderQueryCol1 = new NvwaQueryColumn(unitOrderCol);
                unitOrderQueryCol1.setAggrType(AggrType.MIN);
                columns.add(unitOrderQueryCol1);
                orderByItems.add(new OrderByItem(formulaCheckTypeCol));
                break;
            }
            case UNIT_FORM: {
                ((NvwaQueryColumn)columns.get(0)).setAggrType(AggrType.NONE);
                groupByColumns.add(0);
                ((NvwaQueryColumn)columns.get(3)).setAggrType(AggrType.NONE);
                groupByColumns.add(3);
                orderByItems.add(new OrderByItem(unitOrderCol));
                NvwaQueryColumn unitOrderQueryCol = new NvwaQueryColumn(unitOrderCol);
                unitOrderQueryCol.setAggrType(AggrType.MIN);
                columns.add(unitOrderQueryCol);
                orderByItems.add(new OrderByItem(formOrderCol));
                NvwaQueryColumn formOrderQueryCol = new NvwaQueryColumn(formOrderCol);
                formOrderQueryCol.setAggrType(AggrType.MIN);
                columns.add(formOrderQueryCol);
                break;
            }
            default: {
                ((NvwaQueryColumn)columns.get(0)).setAggrType(AggrType.NONE);
                groupByColumns.add(0);
                orderByItems.add(new OrderByItem(unitOrderCol));
                NvwaQueryColumn queryColumn7 = new NvwaQueryColumn(unitOrderCol);
                queryColumn7.setAggrType(AggrType.MIN);
                columns.add(queryColumn7);
            }
        }
        queryModel.setMainTableName(ckrTableName);
        return queryModel;
    }

    public SqlModel getExistSqlModel(CheckResultQueryParam checkResultQueryParam, FormSchemeDefine formScheme, Date queryDate) throws LogicCheckedException {
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, checkResultQueryParam.getBatchId());
        String ckdTableName = this.splitTableHelper.getSplitCKDTableName(formScheme);
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(ckrTableName);
        List<String> depDimNames = this.entityUtil.getDepOnDwDims(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        SqlModel sqlModel = new SqlModel();
        sqlModel.setBatchId(checkResultQueryParam.getBatchId());
        sqlModel.setQueryDate(queryDate);
        sqlModel.setMainTableName(ckrTableName);
        sqlModel.setJoinTableName(ckdTableName);
        this.appendColumnFilters(checkResultQueryParam, sqlModel);
        Map<Integer, Boolean> mainCheckTypes = checkResultQueryParam.getCheckTypes();
        if (checkResultQueryParam.isQueryByDim()) {
            List<DimensionValueSet> dimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(checkResultQueryParam.getDimensionCollection(), depDimNames);
            this.appendDimFilters(dimensionChanger, sqlModel.getDimFilters(), dimensionValueSets.get(0));
            List<UnionModel> unionModels = this.appendUnionModel(mainCheckTypes, dimensionValueSets, checkResultQueryParam.getCustomCondition(), dimensionChanger, depDimNames);
            sqlModel.setUnionModels(unionModels);
        }
        sqlModel.setFilterCondition(checkResultQueryParam.getFilterCondition());
        sqlModel.setCheckTypes(mainCheckTypes);
        sqlModel.setQueryCondition(checkResultQueryParam.getQueryCondition());
        return sqlModel;
    }

    public CheckResultData createCheckResultData(Map<String, Object> dataRow, DimensionValueSet dimensionValueSet, ExecutorContext executorContext, LinkEnvProvider linkEnvProvider) throws Exception {
        CheckResultData checkResultData = new CheckResultData();
        FormulaData formula = new FormulaData();
        String formulaSchemeKey = null;
        QueryContext queryContext = null;
        try {
            queryContext = new QueryContext(executorContext, null);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        for (Map.Entry<String, Object> entry : dataRow.entrySet()) {
            String columnName = entry.getKey();
            String fieldValue = "";
            Object value = entry.getValue();
            try {
                String str;
                if ((value instanceof String || value instanceof Number) && StringUtils.isNotEmpty((String)(str = AbstractData.valueOf((Object)value, (int)6).getAsString()))) {
                    fieldValue = str;
                }
            }
            catch (RuntimeException e) {
                logger.error(MSG_RESULT_TRANSFER_ERROR_PREFIX + e.getMessage(), e);
            }
            switch (columnName) {
                case "CKR_RECID": {
                    dimensionValueSet.clearValue("CKR_RECID");
                    checkResultData.setRecordId(fieldValue);
                    break;
                }
                case "CKR_FORMULASCHEMEKEY": {
                    formulaSchemeKey = fieldValue;
                    formula.setFormulaSchemeKey(formulaSchemeKey);
                    break;
                }
                case "CKR_FORMKEY": {
                    formula.setFormKey(fieldValue);
                    FormDefine formDefine = this.runTimeViewController.queryFormById(fieldValue);
                    String formTitle = formDefine == null ? "\u8868\u95f4" : formDefine.getTitle();
                    formula.setFormTitle(formTitle);
                    break;
                }
                case "CKR_FORMULACODE": {
                    formula.setParsedExpressionKey(fieldValue);
                    break;
                }
                case "CKR_GLOBROW": {
                    int globRow = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globRow = new Double(fieldValue).intValue();
                    }
                    formula.setGlobRow(globRow);
                    break;
                }
                case "CKR_GLOBCOL": {
                    int globCol = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globCol = new Double(fieldValue).intValue();
                    }
                    formula.setGlobCol(globCol);
                    break;
                }
                case "CKR_DIMSTR": {
                    CheckResultUtil.fillDimStr(dimensionValueSet, fieldValue);
                    break;
                }
                case "CKR_ERRORDESC": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) break;
                    List<FormulaNode> formulaNodes = CheckResultUtil.getFormulaNodes(fieldValue, linkEnvProvider);
                    checkResultData.setFormulaNodeList(formulaNodes);
                    break;
                }
                case "CKR_LEFT": {
                    checkResultData.setLeft(fieldValue);
                    break;
                }
                case "CKR_RIGHT": {
                    checkResultData.setRight(fieldValue);
                    break;
                }
                case "CKR_BALANCE": {
                    checkResultData.setDifference(fieldValue);
                    break;
                }
                case "CKD_USERKEY": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) break;
                    checkResultData.getCheckDescription().setUserId(fieldValue);
                    break;
                }
                case "CKD_UPDATETIME": {
                    this.fillCkdWriteTime(checkResultData, value, true);
                    break;
                }
                case "CKD_USERNAME": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) break;
                    checkResultData.getCheckDescription().setUserNickName(fieldValue);
                    break;
                }
                case "CKD_DESCRIPTION": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) break;
                    checkResultData.getCheckDescription().setDescription(fieldValue);
                    break;
                }
                case "CKD_STATE": {
                    int state = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        state = Integer.parseInt(fieldValue);
                    }
                    checkResultData.getCheckDescription().setState(DesCheckState.getByCode(state));
                    break;
                }
            }
        }
        this.fillFormulaDataByParsed(formula, formulaSchemeKey, queryContext, formulaShowInfo);
        checkResultData.setFormulaData(formula);
        return checkResultData;
    }

    public CheckResultData createCheckResultData(NvwaQueryModel queryModel, INvwaDataRow dataRow, DimensionValueSet dimensionValueSet, ExecutorContext executorContext, LinkEnvProvider linkEnvProvider) throws Exception {
        CheckResultData checkResultData = new CheckResultData();
        FormulaData formula = new FormulaData();
        String formulaSchemeKey = null;
        QueryContext queryContext = null;
        try {
            queryContext = new QueryContext(executorContext, null);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        block40: for (int i = 0; i < queryModel.getColumns().size(); ++i) {
            String columnName = ((NvwaQueryColumn)queryModel.getColumns().get(i)).getColumnModel().getCode();
            String fieldValue = "";
            Object value = dataRow.getValue(i);
            try {
                String str;
                if ((value instanceof String || value instanceof Number) && StringUtils.isNotEmpty((String)(str = AbstractData.valueOf((Object)value, (int)6).getAsString()))) {
                    fieldValue = str;
                }
            }
            catch (RuntimeException e) {
                logger.error(MSG_RESULT_TRANSFER_ERROR_PREFIX + e.getMessage(), e);
            }
            switch (columnName) {
                case "CKR_RECID": {
                    dimensionValueSet.clearValue("CKR_RECID");
                    checkResultData.setRecordId(fieldValue);
                    continue block40;
                }
                case "CKR_FORMULASCHEMEKEY": {
                    formulaSchemeKey = fieldValue;
                    formula.setFormulaSchemeKey(formulaSchemeKey);
                    continue block40;
                }
                case "CKR_FORMKEY": {
                    formula.setFormKey(fieldValue);
                    FormDefine formDefine = this.runTimeViewController.queryFormById(fieldValue);
                    String formTitle = formDefine == null ? "\u8868\u95f4" : formDefine.getTitle();
                    formula.setFormTitle(formTitle);
                    continue block40;
                }
                case "CKR_FORMULACODE": {
                    formula.setParsedExpressionKey(fieldValue);
                    continue block40;
                }
                case "CKR_GLOBROW": {
                    int globRow = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globRow = new Double(fieldValue).intValue();
                    }
                    formula.setGlobRow(globRow);
                    continue block40;
                }
                case "CKR_GLOBCOL": {
                    int globCol = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globCol = new Double(fieldValue).intValue();
                    }
                    formula.setGlobCol(globCol);
                    continue block40;
                }
                case "CKR_DIMSTR": {
                    CheckResultUtil.fillDimStr(dimensionValueSet, fieldValue);
                    continue block40;
                }
                case "CKR_ERRORDESC": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    List<FormulaNode> formulaNodes = CheckResultUtil.getFormulaNodes(fieldValue, linkEnvProvider);
                    checkResultData.setFormulaNodeList(formulaNodes);
                    continue block40;
                }
                case "CKR_LEFT": {
                    checkResultData.setLeft(fieldValue);
                    continue block40;
                }
                case "CKR_RIGHT": {
                    checkResultData.setRight(fieldValue);
                    continue block40;
                }
                case "CKR_BALANCE": {
                    checkResultData.setDifference(fieldValue);
                    continue block40;
                }
                case "CKD_USERKEY": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setUserId(fieldValue);
                    continue block40;
                }
                case "CKD_UPDATETIME": {
                    this.fillCkdWriteTime(checkResultData, value, false);
                    continue block40;
                }
                case "CKD_USERNAME": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setUserNickName(fieldValue);
                    continue block40;
                }
                case "CKD_DESCRIPTION": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setDescription(fieldValue);
                    continue block40;
                }
                case "CKD_STATE": {
                    int state = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        state = Integer.parseInt(fieldValue);
                    }
                    checkResultData.getCheckDescription().setState(DesCheckState.getByCode(state));
                    continue block40;
                }
            }
        }
        this.fillFormulaDataByParsed(formula, formulaSchemeKey, queryContext, formulaShowInfo);
        checkResultData.setFormulaData(formula);
        return checkResultData;
    }

    private void fillFormulaDataByParsed(FormulaData formula, String formulaSchemeKey, QueryContext queryContext, FormulaShowInfo formulaShowInfo) throws Exception {
        IParsedExpression parsedExpression = this.formulaRunTimeController.getParsedExpression(formulaSchemeKey, formula.getFormKey(), formula.getParsedExpressionKey());
        if (parsedExpression != null) {
            formula.setKey(parsedExpression.getSource().getId());
            formula.setCode(parsedExpression.getSource().getCode());
            formula.setCheckType(parsedExpression.getSource().getChecktype());
            String formulaExpression = "";
            try {
                formulaExpression = parsedExpression.getFormula(queryContext, formulaShowInfo);
            }
            catch (InterpretException e) {
                logger.error("\u516c\u5f0f\u5185\u5bb9\u89e3\u6790\u5f02\u5e38:" + e.getMessage(), e);
            }
            formula.setFormula(formulaExpression);
            formula.setMeaning(parsedExpression.getSource().getMeanning());
        }
    }

    public CheckResultGroup getCheckResultGroup(CheckResultQueryParam checkResultQueryParam, CheckResultSqlBuilder checkResultSqlBuilder, EntityDataLoader dwEntityLoader) {
        CheckResultGroup checkResultGroup = new CheckResultGroup();
        GroupType groupType = checkResultQueryParam.getGroupType();
        List<Map<String, Object>> dataRows = checkResultSqlBuilder.groups();
        int totalCount = checkResultSqlBuilder.groupsCount();
        int showCount = checkResultSqlBuilder.itemsCount();
        checkResultGroup.setMessage("success");
        LinkedHashMap<String, CheckResultGroupData> groupDataMap = new LinkedHashMap<String, CheckResultGroupData>();
        Map<Integer, String> codeTitleMap = this.paramUtil.getCheckTypeCodeTitleMap();
        for (Map<String, Object> dataRow : dataRows) {
            int groupCount;
            String checkTypeStr;
            String unitKey;
            String formula;
            String formKey;
            CheckResultGroupData groupData = new CheckResultGroupData();
            try {
                formKey = AbstractData.valueOf((Object)dataRow.get("CKR_FORMKEY"), (int)6).getAsString();
                formula = AbstractData.valueOf((Object)dataRow.get("CKR_FORMULAID"), (int)6).getAsString();
                unitKey = AbstractData.valueOf((Object)dataRow.get("MDCODE"), (int)6).getAsString();
                checkTypeStr = AbstractData.valueOf((Object)dataRow.get("CKR_FORMULACHECKTYPE"), (int)6).getAsString();
                String asString = AbstractData.valueOf((Object)dataRow.get("CKR_RECID"), (int)6).getAsString();
                groupCount = new Double(asString).intValue();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                continue;
            }
            FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(formula);
            int checkTypeByFml = Integer.parseInt(checkTypeStr);
            String checkTypeTitle = codeTitleMap.get(checkTypeByFml);
            IEntityRow entityRow = dwEntityLoader.getRowByEntityDataKey(unitKey);
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
                        if (this.fillGroupDataByForm(groupData, formKey)) break;
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
                    this.fillGroupDataByForm(formGroupInfo, formKey);
                    formGroupInfo.setCount(groupCount);
                    groupData.getChildren().add(formGroupInfo);
                    groupData.setCount(groupData.getCount() + groupCount);
                    break;
                }
                case checktype_form: {
                    groupData = this.fillGroupDataByCheckType(checkResultGroup, groupDataMap, groupData, checkTypeStr, checkTypeTitle);
                    CheckResultGroupData formGroup = new CheckResultGroupData();
                    if (this.fillGroupDataByForm(formGroup, formKey)) break;
                    formGroup.setCount(groupCount);
                    groupData.getChildren().add(formGroup);
                    groupData.setCount(groupData.getCount() + groupCount);
                    break;
                }
                case checktype_unit: {
                    groupData = this.fillGroupDataByCheckType(checkResultGroup, groupDataMap, groupData, checkTypeStr, checkTypeTitle);
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
        return checkResultGroup;
    }

    public CheckResultGroupData fillGroupDataByCheckType(CheckResultGroup checkResultGroup, Map<String, CheckResultGroupData> groupDataMap, CheckResultGroupData groupData, String checkTypeStr, String checkTypeTitle) {
        if (groupDataMap.containsKey(checkTypeStr)) {
            groupData = groupDataMap.get(checkTypeStr);
        } else {
            groupDataMap.put(checkTypeStr, groupData);
            groupData.setKey(checkTypeStr);
            groupData.setCode(checkTypeStr);
            groupData.setTitle(checkTypeTitle);
        }
        return groupData;
    }

    public boolean fillGroupDataByForm(CheckResultGroupData groupData, String formKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(formKey)) {
            groupData.setKey(formKey);
            groupData.setCode("00000000-0000-0000-0000-000000000000");
            groupData.setTitle("\u8868\u95f4\u516c\u5f0f");
        } else {
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            if (formDefine == null) {
                return true;
            }
            groupData.setKey(formKey);
            groupData.setCode(formDefine.getFormCode());
            groupData.setTitle(formDefine.getTitle());
        }
        return false;
    }

    public void setOtherResultData(CheckResult checkResult, IDimDataLoader dwEntityDataLoader, String masterDimensionName) {
        checkResult.setTotalCount(checkResult.getResultData().size());
        HashMap<String, String> unitKeyTitleMap = new HashMap<String, String>(1000);
        HashMap<String, String> unitKeyCodeMap = new HashMap<String, String>(1000);
        List<CheckResultData> resultData = checkResult.getResultData();
        List<Map<String, DimensionValue>> dimensionList = checkResult.getDimensionList();
        Map<Integer, String> codeTitleMap = this.paramUtil.getCheckTypeCodeTitleMap();
        for (CheckResultData data : resultData) {
            DimensionValue dimensionValue = dimensionList.get(data.getDimensionIndex()).get(masterDimensionName);
            if (dimensionValue != null) {
                String value = dimensionValue.getValue();
                data.setUnitKey(value);
                if (unitKeyTitleMap.containsKey(value)) {
                    data.setUnitTitle((String)unitKeyTitleMap.get(value));
                    data.setUnitCode((String)unitKeyCodeMap.get(value));
                } else {
                    String code = dwEntityDataLoader.getCodeByEntityDataKey(value);
                    String title = dwEntityDataLoader.getTitleByEntityDataKey(value);
                    data.setUnitTitle(title);
                    data.setUnitCode(code);
                    unitKeyTitleMap.put(value, title);
                    unitKeyCodeMap.put(value, code);
                }
            }
            int checkType = data.getFormulaData().getCheckType();
            CheckResultUtil.fillCheckTypeCountMap(checkResult, codeTitleMap, checkType);
        }
    }

    public NvwaQueryModel getAllCKRQueryModel(CheckQueryContext checkQueryContext) {
        String rowFilterStr;
        QueryCondition queryCondition;
        Map<Integer, Boolean> checkTypes;
        String checkTypeFilterStr;
        List<String> rangeKeys;
        CheckResultQueryParam checkResultQueryParam = checkQueryContext.getCheckResultQueryParam();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine ckrTable = checkQueryContext.getCkrTable();
        TableModelDefine ckdTable = checkQueryContext.getCkdTable();
        String ckrTableName = ckrTable.getName();
        String ckdTableName = ckdTable.getName();
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
        queryModel.getColumns().add(new NvwaQueryColumn(this.dataModelService.getColumnModelDefineByCode(ckdTableID, "CKD_STATE")));
        queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_ASYNCTASKID"), checkResultQueryParam.getBatchId());
        if (checkResultQueryParam.isQueryByDim()) {
            DimensionChanger ckrDimChanger = checkQueryContext.getCkrDimChanger();
            DimensionValueSet masterKey = checkQueryContext.getMasterKey();
            for (int i = 0; i < masterKey.size(); ++i) {
                String columnCode = ckrDimChanger.getColumnCode(masterKey.getName(i));
                queryModel.getColumnFilters().put(codeColumnMap.get(columnCode), masterKey.getValue(i));
            }
        }
        if (!CollectionUtils.isEmpty(rangeKeys = checkResultQueryParam.getRangeKeys())) {
            if (checkResultQueryParam.getMode() == Mode.FORM) {
                queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_FORMKEY"), rangeKeys);
            } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                queryModel.getColumnFilters().put(codeColumnMap.get("ALLCKR_FORMULAID"), rangeKeys);
            }
        }
        StringBuilder rowFilter = new StringBuilder();
        String condition = checkResultQueryParam.getFilterCondition();
        if (StringUtils.isNotEmpty((String)condition)) {
            rowFilter.append("( ").append(condition).append(" ) ");
        }
        if (StringUtils.isNotEmpty((String)(checkTypeFilterStr = CheckResultDataUtil.getCheckTypeFilterStr(ckrTableName, ckdTableName, checkTypes = checkResultQueryParam.getCheckTypes(), "ALLCKR_FORMULACHECKTYPE")))) {
            if (rowFilter.length() > 0) {
                rowFilter.append(" AND ");
            }
            rowFilter.append("( ").append(checkTypeFilterStr).append(" )");
        }
        if ((queryCondition = checkResultQueryParam.getQueryCondition()) != null) {
            com.jiuqi.nr.data.logic.facade.param.input.QueryContext queryContext = new com.jiuqi.nr.data.logic.facade.param.input.QueryContext();
            queryContext.setAllCheck(true);
            queryContext.setCkrTableName(ckrTableName);
            queryContext.setCkdTableName(ckdTableName);
            String fml = queryCondition.buildFml(queryContext);
            if (StringUtils.isNotEmpty((String)fml)) {
                if (rowFilter.length() > 0) {
                    rowFilter.append(" AND ");
                }
                rowFilter.append("( ").append(fml).append(" )");
            }
        }
        if (StringUtils.isNotEmpty((String)(rowFilterStr = rowFilter.toString()))) {
            queryModel.setFilter(rowFilterStr);
        }
        queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_FORMORDER"), false));
        queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_FORMULAORDER"), false));
        queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_GLOBROW"), false));
        queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_GLOBCOL"), false));
        this.appendDimOrderItems(checkQueryContext, codeColumnMap, queryModel);
        queryModel.getOrderByItems().add(new OrderByItem(codeColumnMap.get("ALLCKR_DIMSTR"), false));
        queryModel.setMainTableName(ckrTableName);
        return queryModel;
    }

    private void appendDimOrderItems(CheckQueryContext checkQueryContext, Map<String, ColumnModelDefine> codeColumnMap, NvwaQueryModel queryModel) {
        List<String> dimColCodes = this.getOrderedDimColCodes(checkQueryContext.getFormSchemeDefine(), checkQueryContext.getCkrDimChanger());
        if (CollectionUtils.isEmpty(dimColCodes)) {
            return;
        }
        dimColCodes.forEach(colCode -> {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)codeColumnMap.get(colCode);
            if (columnModelDefine != null) {
                queryModel.getOrderByItems().add(new OrderByItem(columnModelDefine, false));
            }
        });
    }

    @Nullable
    private List<String> getOrderedDimColCodes(FormSchemeDefine formSchemeDefine, DimensionChanger ckrDimChanger) {
        List<EntityData> dimEntities = this.entityUtil.getDimEntities(formSchemeDefine);
        if (CollectionUtils.isEmpty(dimEntities)) {
            return null;
        }
        ArrayList<String> dimColCodes = new ArrayList<String>();
        dimEntities.forEach(o -> {
            String columnCode = ckrDimChanger.getColumnCode(o.getDimensionName());
            if (StringUtils.isNotEmpty((String)columnCode)) {
                dimColCodes.add(columnCode);
            }
        });
        if (CollectionUtils.isEmpty(dimColCodes)) {
            return null;
        }
        dimColCodes.sort(Comparator.naturalOrder());
        return dimColCodes;
    }

    private static String getCheckTypeFilterStr(String ckrTableName, String ckdTableName, Map<Integer, Boolean> checkTypes, String checkTypeColName) {
        StringBuilder checkTypeFilter = new StringBuilder();
        boolean addOR = false;
        for (Map.Entry<Integer, Boolean> entry : checkTypes.entrySet()) {
            Integer checkType = entry.getKey();
            Boolean des = entry.getValue();
            if (addOR) {
                checkTypeFilter.append(" OR ");
            }
            checkTypeFilter.append("( ");
            checkTypeFilter.append(ckrTableName).append("[").append(checkTypeColName).append("] = ").append(checkType);
            if (des != null) {
                checkTypeFilter.append(" AND ");
                if (des.booleanValue()) {
                    checkTypeFilter.append(" isnotnull(").append(ckdTableName).append("[").append("CKD_DESCRIPTION").append("] ) ");
                } else {
                    checkTypeFilter.append(" isnull(").append(ckdTableName).append("[").append("CKD_DESCRIPTION").append("] ) ");
                }
            }
            checkTypeFilter.append(" )");
            addOR = true;
        }
        return checkTypeFilter.toString();
    }

    public CheckResultData createAllCheckResultData(NvwaQueryModel queryModel, INvwaDataRow dataRow, DimensionValueSet dimensionValueSet, ExecutorContext executorContext, LinkEnvProvider linkEnvProvider) throws Exception {
        CheckResultData checkResultData = new CheckResultData();
        FormulaData formula = new FormulaData();
        String formulaSchemeKey = null;
        QueryContext queryContext = null;
        try {
            queryContext = new QueryContext(executorContext, null);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        block40: for (int i = 0; i < queryModel.getColumns().size(); ++i) {
            String columnName = ((NvwaQueryColumn)queryModel.getColumns().get(i)).getColumnModel().getCode();
            String fieldValue = "";
            Object value = dataRow.getValue(i);
            try {
                String str;
                if ((value instanceof String || value instanceof Number) && StringUtils.isNotEmpty((String)(str = AbstractData.valueOf((Object)value, (int)6).getAsString()))) {
                    fieldValue = str;
                }
            }
            catch (RuntimeException e) {
                logger.error(MSG_RESULT_TRANSFER_ERROR_PREFIX + e.getMessage(), e);
            }
            switch (columnName) {
                case "ALLCKR_RECID": {
                    dimensionValueSet.clearValue("ALLCKR_RECID");
                    checkResultData.setRecordId(fieldValue);
                    continue block40;
                }
                case "ALLCKR_FORMULASCHEMEKEY": {
                    formulaSchemeKey = fieldValue;
                    formula.setFormulaSchemeKey(formulaSchemeKey);
                    continue block40;
                }
                case "ALLCKR_FORMKEY": {
                    formula.setFormKey(fieldValue);
                    FormDefine formDefine = this.runTimeViewController.queryFormById(fieldValue);
                    String formTitle = formDefine == null ? "\u8868\u95f4" : formDefine.getTitle();
                    formula.setFormTitle(formTitle);
                    continue block40;
                }
                case "ALLCKR_FORMULACODE": {
                    formula.setParsedExpressionKey(fieldValue);
                    continue block40;
                }
                case "ALLCKR_GLOBROW": {
                    int globRow = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globRow = new Double(fieldValue).intValue();
                    }
                    formula.setGlobRow(globRow);
                    continue block40;
                }
                case "ALLCKR_GLOBCOL": {
                    int globCol = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        globCol = new Double(fieldValue).intValue();
                    }
                    formula.setGlobCol(globCol);
                    continue block40;
                }
                case "ALLCKR_DIMSTR": {
                    CheckResultUtil.fillDimStr(dimensionValueSet, fieldValue);
                    continue block40;
                }
                case "ALLCKR_ERRORDESC": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    List<FormulaNode> formulaNodes = CheckResultUtil.getFormulaNodes(fieldValue, linkEnvProvider);
                    checkResultData.setFormulaNodeList(formulaNodes);
                    continue block40;
                }
                case "ALLCKR_LEFT": {
                    checkResultData.setLeft(fieldValue);
                    continue block40;
                }
                case "ALLCKR_RIGHT": {
                    checkResultData.setRight(fieldValue);
                    continue block40;
                }
                case "ALLCKR_BALANCE": {
                    checkResultData.setDifference(fieldValue);
                    continue block40;
                }
                case "CKD_USERKEY": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setUserId(fieldValue);
                    continue block40;
                }
                case "CKD_UPDATETIME": {
                    this.fillCkdWriteTime(checkResultData, value, false);
                    continue block40;
                }
                case "CKD_USERNAME": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setUserNickName(fieldValue);
                    continue block40;
                }
                case "CKD_DESCRIPTION": {
                    if (!StringUtils.isNotEmpty((String)fieldValue)) continue block40;
                    checkResultData.getCheckDescription().setDescription(fieldValue);
                    continue block40;
                }
                case "CKD_STATE": {
                    int state = 0;
                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                        state = Integer.parseInt(fieldValue);
                    }
                    checkResultData.getCheckDescription().setState(DesCheckState.getByCode(state));
                    continue block40;
                }
            }
        }
        this.fillFormulaDataByParsed(formula, formulaSchemeKey, queryContext, formulaShowInfo);
        checkResultData.setFormulaData(formula);
        return checkResultData;
    }

    private void fillCkdWriteTime(CheckResultData checkResultData, Object value, boolean orgSql) {
        try {
            if (value != null) {
                Instant ckdUpdateTime = null;
                if (orgSql) {
                    if (value instanceof Timestamp) {
                        Timestamp timestamp = (Timestamp)value;
                        ckdUpdateTime = Instant.ofEpochMilli(timestamp.getTime());
                    }
                } else {
                    long asDateTime = AbstractData.valueOf((Object)value, (int)2).getAsDateTime();
                    ckdUpdateTime = Instant.ofEpochMilli(asDateTime);
                }
                checkResultData.getCheckDescription().setUpdateTime(ckdUpdateTime);
            }
        }
        catch (RuntimeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void appendPageInfo(SqlModel sqlModel, PagerInfo pagerInfo) {
        int start = -1;
        int end = -1;
        if (pagerInfo != null) {
            int offset = pagerInfo.getOffset();
            int limit = pagerInfo.getLimit();
            start = offset * limit;
            end = start + limit;
        }
        sqlModel.setPageStart(start);
        sqlModel.setPageEnd(end);
    }

    public void appendColumnFilters(CheckResultQueryParam checkResultQueryParam, SqlModel sqlModel) {
        if (!CollectionUtils.isEmpty(checkResultQueryParam.getFormulaSchemeKeys())) {
            sqlModel.getColumnFilters().put("CKR_FORMULASCHEMEKEY", checkResultQueryParam.getFormulaSchemeKeys());
        }
        if (!CollectionUtils.isEmpty(checkResultQueryParam.getRangeKeys())) {
            if (checkResultQueryParam.getMode() == Mode.FORM) {
                sqlModel.getColumnFilters().put("CKR_FORMKEY", checkResultQueryParam.getRangeKeys());
            } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                sqlModel.getColumnFilters().put("CKR_FORMULAID", checkResultQueryParam.getRangeKeys());
            }
        }
    }

    public void appendDimFilters(DimensionChanger dimensionChanger, Map<String, List<String>> dimFilters, DimensionValueSet dimensionValueSet) {
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            String columnCode = dimensionChanger.getColumnCode(dimensionName);
            if (!StringUtils.isNotEmpty((String)columnCode)) continue;
            ArrayList<String> whereValue = new ArrayList<String>();
            if (dimensionValue instanceof String) {
                whereValue.add((String)dimensionValue);
            } else if (dimensionValue instanceof List) {
                List list = (List)dimensionValue;
                for (Object o : list) {
                    whereValue.add((String)o);
                }
            } else if (dimensionValue instanceof Object[]) {
                Object[] objects;
                for (Object object : objects = (Object[])dimensionValue) {
                    whereValue.add((String)object);
                }
            }
            dimFilters.put(columnCode, whereValue);
        }
    }

    public List<UnionModel> appendUnionModel(Map<Integer, Boolean> mainCheckTypes, List<DimensionValueSet> dimensionValueSets, CustomQueryCondition customCondition, DimensionChanger dimensionChanger, List<String> depDimNames) {
        ArrayList<UnionModel> result = new ArrayList<UnionModel>();
        for (int i = 1; i < dimensionValueSets.size(); ++i) {
            UnionModel unionModel = new UnionModel();
            DimensionValueSet dimensionValueSet = dimensionValueSets.get(i);
            this.appendDimFilters(dimensionChanger, unionModel.getDimFilters(), dimensionValueSet);
            unionModel.setCheckTypes(mainCheckTypes);
            result.add(unionModel);
        }
        if (customCondition != null && customCondition.getDimensionCollection() != null && customCondition.getCheckTypes() != null) {
            List<Object> customDimensionValueSets = new ArrayList();
            try {
                customDimensionValueSets = this.dimensionCollectionUtil.mergeDimensionWithDep(customCondition.getDimensionCollection(), depDimNames);
            }
            catch (LogicCheckedException e) {
                logger.error(e.getMessage() + ExceptionEnum.DIM_EXPAND_EXC.getDesc(), e);
            }
            for (DimensionValueSet dimensionValueSet : customDimensionValueSets) {
                UnionModel unionModel = new UnionModel();
                this.appendDimFilters(dimensionChanger, unionModel.getDimFilters(), dimensionValueSet);
                unionModel.setCheckTypes(customCondition.getCheckTypes());
                result.add(unionModel);
            }
        }
        return result;
    }

    public List<INvwaDataRow> orderDataRow(INvwaDataSet dataSet, List<Integer> orderIndex) {
        ArrayList<INvwaDataRow> orderResult = new ArrayList<INvwaDataRow>();
        if (dataSet != null && dataSet.size() >= 0) {
            for (int i = 0; i < dataSet.size(); ++i) {
                orderResult.add(dataSet.getRow(i));
            }
        }
        for (Integer index : orderIndex) {
            orderResult.sort((o1, o2) -> {
                Object value1 = o1.getValue(index.intValue());
                Object value2 = o2.getValue(index.intValue());
                String asString1 = AbstractData.valueOf((Object)value1, (int)6).getAsString();
                String asString2 = AbstractData.valueOf((Object)value2, (int)6).getAsString();
                if (value1 instanceof Double) {
                    int real1 = new Double(asString1).intValue();
                    int real2 = new Double(asString2).intValue();
                    return real1 - real2;
                }
                return StringUtils.compare((String)asString1, (String)asString2);
            });
        }
        return orderResult;
    }

    public CheckQueryContext initCheckQueryContext(CheckResultQueryParam param) throws LogicCheckedException {
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKeys(formulaSchemeKeys);
        DimensionCollection dimensionCollection = param.getDimensionCollection();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(dimensionCollection);
        if (dimensionValueSet == null) {
            throw new LogicCheckedException(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
        }
        String ckrTableName = this.splitTableHelper.getCKRTableName(formScheme, param.getBatchId());
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByCode(ckrTableName);
        if (ckrTable == null) {
            throw new LogicCheckedException(ckrTableName + ExceptionEnum.TABLE_MODEL_EXC.getDesc());
        }
        CheckQueryContext checkQueryContext = new CheckQueryContext();
        checkQueryContext.setCheckResultQueryParam(param);
        checkQueryContext.setFormSchemeDefine(formScheme);
        checkQueryContext.setMasterKey(dimensionValueSet);
        return checkQueryContext;
    }
}

