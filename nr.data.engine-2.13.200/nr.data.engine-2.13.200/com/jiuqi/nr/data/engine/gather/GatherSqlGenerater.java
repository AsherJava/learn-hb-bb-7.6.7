/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.sql.loader.ILoadListener
 *  com.jiuqi.bi.database.sql.loader.ITableLoader
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertAutoRownumLoader
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMemTableLoader
 *  com.jiuqi.np.dataengine.intf.IQuerySqlUpdater
 *  com.jiuqi.np.dataengine.intf.impl.LoggingTableLoadListener
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.setting.OrderType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.TuplesFilter
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertAutoRownumLoader;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMemTableLoader;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.impl.LoggingTableLoadListener;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.engine.config.DataGatherConfig;
import com.jiuqi.nr.data.engine.execption.DataGatherExecption;
import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import com.jiuqi.nr.data.engine.gather.CheckSqlItem;
import com.jiuqi.nr.data.engine.gather.FieldItem;
import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import com.jiuqi.nr.data.engine.gather.GatherAssistantTable;
import com.jiuqi.nr.data.engine.gather.GatherDataTable;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.engine.gather.OrderFieldInfo;
import com.jiuqi.nr.data.engine.gather.SqlItem;
import com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo;
import com.jiuqi.nr.data.engine.gather.param.FileSumContext;
import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import com.jiuqi.nr.data.engine.gather.param.GatherRow;
import com.jiuqi.nr.data.engine.gather.param.IGatherColumn;
import com.jiuqi.nr.data.engine.gather.param.SortInfo;
import com.jiuqi.nr.data.engine.gather.param.impl.StatGatherColumn;
import com.jiuqi.nr.data.engine.gather.util.AggrTypeConvert;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import com.jiuqi.nr.data.engine.gather.util.GatherTempTable;
import com.jiuqi.nr.data.engine.gather.util.GatherTempTableUtils;
import com.jiuqi.nr.data.engine.gather.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.data.engine.gather.util.TempConvertObject;
import com.jiuqi.nr.data.engine.util.SubDataBaseUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.TuplesFilter;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

public class GatherSqlGenerater {
    private static final Logger logger = LoggerFactory.getLogger(GatherSqlGenerater.class);
    protected final DataModelDefinitionsCache dataDefinitionsCache;
    protected final Connection connection;
    protected final IDatabase database;
    protected final ExecutorContext executorContext;
    protected final IRuntimeDataSchemeService runtimeDataSchemeService;
    protected boolean isNodeCheck = false;
    protected final String executionId;
    protected final String DATATABLEALIAS = "c1";
    protected final String TEMPTABLEALIAS = "c2";
    protected final String NEMPTABLEALIAS = "c3";
    protected final String GATHERTEMPTABLEALIAS = "c4";
    protected final String DATAVBASE_MYSQL = "mysql";
    protected final String DATAVBASE_ORACLE = "oracle";
    protected final String DATAVBASE_H2 = "h2";
    protected final String DATAVBASE_DERBY = "derby";
    protected static final String EMPTY = "_empty_";
    protected static final String ORGTABLE = "org";
    protected boolean containTargetKey = false;
    protected final SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;
    protected final String taskKey;
    protected final String formSchemeKey;
    protected final DataGatherConfig dataGatherConfig;
    protected SubDataBaseUtil subDataBaseUtil;
    protected Map<String, String> gatherSingleDims;
    protected static final String FLOATORDER = "FLOATORDER";
    final String tempDate = "2024-01-01";
    protected IMemTableLoader memTableLoader;
    protected FileCalculateService fileCalculateService;
    private boolean fileGather = false;
    private static final int AVG_SIZE = 250;

    public GatherSqlGenerater(ExecutorContext executorContext, Connection connection, boolean isNodeCheck, String executionId, boolean containTargetKey, String taskKey, String formSchemeKey, Map<String, String> gatherSingleDims, IMemTableLoader memTableLoader, FileCalculateService fileCalculateService) throws ParseException, SQLException {
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        this.executorContext = executorContext;
        this.dataDefinitionsCache = cache.getDataModelDefinitionsCache();
        this.connection = connection;
        this.database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        this.isNodeCheck = isNodeCheck;
        this.executionId = executionId;
        this.containTargetKey = containTargetKey;
        this.subDatabaseTableNamesProvider = (SubDatabaseTableNamesProvider)BeanUtil.getBean(SubDatabaseTableNamesProvider.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataGatherConfig = (DataGatherConfig)BeanUtil.getBean(DataGatherConfig.class);
        this.subDataBaseUtil = (SubDataBaseUtil)BeanUtil.getBean(SubDataBaseUtil.class);
        this.taskKey = taskKey;
        this.gatherSingleDims = gatherSingleDims;
        this.memTableLoader = memTableLoader;
        this.formSchemeKey = formSchemeKey;
        this.fileCalculateService = fileCalculateService;
    }

    public void buildFixedGatherSqls(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, boolean isMinus, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField) throws SQLException, TableLoaderException {
        List<SqlItem> resultSqls = this.buildClearSqlsForFixedTable(gatherDataTable, gatherTempTable, noGatherTempTable, maxLevel, periodCode, targetDimensions);
        for (SqlItem deleteSql : resultSqls) {
            DataEngineUtil.executeUpdate((Connection)this.connection, (String)deleteSql.getExecutorSql(), (Object[])deleteSql.getParamValues().toArray());
        }
        String sourceSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, isMinus, false, -1, sourceDimensions, targetDimensions, versionField, false);
        String updateTemplateSql = "";
        if (this.matchConnType(this.connection, "oracle")) {
            updateTemplateSql = this.buildUpdateTemplateSql(gatherDataTable, gatherTempTable, noGatherTempTable, sourceSql, periodCode, targetDimensions);
        } else if (this.matchConnType(this.connection, "mysql")) {
            updateTemplateSql = this.buildUpdateSqlForMysql(gatherDataTable, gatherTempTable, noGatherTempTable, sourceSql, periodCode, targetDimensions);
        }
        if (querySqlUpdater != null) {
            QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
            updateTemplateSql = querySqlUpdater.updateQuerySql(queryTable, "c1", updateTemplateSql);
        }
        Integer level = maxLevel;
        while (level >= 1) {
            ArrayList<Object> paramValues = new ArrayList<Object>();
            paramValues.add(level);
            paramValues.add(this.executionId);
            this.addPeriodAndVersion(gatherDataTable.getGatherTableDefine().getPeriodField(), periodCode, versionField, paramValues);
            paramValues.add(level);
            paramValues.add(this.executionId);
            this.addPeriodAndVersion(gatherDataTable.getGatherTableDefine().getPeriodField(), periodCode, versionField, paramValues);
            DataEngineUtil.executeUpdate((Connection)this.connection, (String)updateTemplateSql, (Object[])paramValues.toArray());
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    public void executeFixedTableGatherForNrDb(GatherDataTable gatherDataTable, GatherEntityValue gatherEntityValue, NotGatherEntityValue notGatherEntityValue, Integer maxLevel, boolean isMinus, String periodCode, DimensionValueSet dimensionValueSet, EntityViewDefine viewDefine) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        NvwaDataEngineQueryUtil dataEngineQueryUtil = (NvwaDataEngineQueryUtil)BeanUtil.getBean(NvwaDataEngineQueryUtil.class);
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        ArrayList<ColumnModelDefine> allColumns = new ArrayList<ColumnModelDefine>();
        allColumns.addAll(gatherDataTable.getAllDimsColumns());
        allColumns.addAll(gatherDataTable.getAllColumns());
        Map<String, ColumnModelDefine> fieldNameAndDefine = allColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        String dwDimensionName = entityMetaService.getDimensionName(viewDefine.getEntityId());
        String periodDimension = periodAdapter.getPeriodDimensionName();
        String realTableName = this.getRealTableName(gatherDataTable);
        List<DataField> attachmentCollect = gatherTableDefine.getGatherFields().stream().filter(e -> e.getDataFieldType() == DataFieldType.PICTURE || e.getDataFieldType() == DataFieldType.FILE).collect(Collectors.toList());
        HashSet<ColumnModelDefine> attachmentSet = new HashSet<ColumnModelDefine>();
        attachmentCollect.forEach(a -> {
            int index = allColumns.indexOf(fieldNameAndDefine.get(this.queryFieldName((DataField)a)));
            attachmentSet.add((ColumnModelDefine)allColumns.get(index));
        });
        Set<String> noGatherUnit = null;
        if (notGatherEntityValue != null) {
            Map<String, Set<String>> notGatherUnitByFormKey = notGatherEntityValue.getNotGatherUnitByFormKey();
            noGatherUnit = notGatherUnitByFormKey.get(gatherTableDefine.getFormId());
        }
        ColumnModelDefine dwColumnModelDefine = dataEngineQueryUtil.getColumnModelDefine(realTableName, dwDimensionName);
        ArrayList<ColumnModelDefine> gatherSingleDefines = new ArrayList<ColumnModelDefine>();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims)) {
            for (Map.Entry<String, String> entry : this.gatherSingleDims.entrySet()) {
                gatherSingleDefines.add(dataEngineQueryUtil.getColumnModelDefine(realTableName, entry.getKey()));
            }
        }
        Integer level = maxLevel;
        while (level >= 1) {
            List<String> idValuesByLevel = gatherEntityValue.getIdValuesByLevel(level);
            Map<String, Double> idValuesAndMinusValuesByLevel = null;
            if (isMinus) {
                idValuesAndMinusValuesByLevel = gatherEntityValue.getIdValuesAndMinusValuesByLevel(level);
            }
            dimensionValueSet.setValue(dwDimensionName, idValuesByLevel);
            dimensionValueSet.setValue(periodDimension, (Object)periodCode);
            TuplesFilter tuplesFilter = this.getTupleFilter(dwColumnModelDefine, gatherSingleDefines, gatherEntityValue, dwDimensionName, level);
            Map<Object, Object> pidAndDimValue = new HashMap();
            if (!CollectionUtils.isEmpty(this.gatherSingleDims)) {
                pidAndDimValue = gatherEntityValue.getPIdAndDimValue(level);
            }
            INvwaDataSet dataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, null, tuplesFilter, true);
            Map<String, String> idValuesAndPidValuesByLevel = gatherEntityValue.getIdValuesAndPidValuesByLevel(level);
            HashMap<String, Set> gatherEntityMap = new HashMap<String, Set>();
            for (Map.Entry<String, String> entry : idValuesAndPidValuesByLevel.entrySet()) {
                String id = entry.getKey();
                String pid = entry.getValue();
                if (noGatherUnit != null && noGatherUnit.contains(pid)) continue;
                gatherEntityMap.computeIfAbsent(pid, k -> new HashSet()).add(id);
            }
            DataAccessContext context = new DataAccessContext(dataModelService);
            INvwaDataUpdator updatableDataSet = dataEngineQueryUtil.getUpdateDataSet(allColumns, context);
            for (Map.Entry entry : gatherEntityMap.entrySet()) {
                String pid = (String)entry.getKey();
                Set value = (Set)entry.getValue();
                GatherRow gatherRow = this.generateGatherRow(allColumns, attachmentSet);
                for (String id : value) {
                    dimensionValueSet.setValue(dwDimensionName, (Object)id);
                    ArrayKey arrayKey = dataEngineQueryUtil.convertMasterToArrayKey(realTableName, dataSet, dimensionValueSet);
                    INvwaDataRow row = dataSet.findRow(arrayKey);
                    if (row == null) continue;
                    Double minus = null;
                    if (isMinus) {
                        minus = idValuesAndMinusValuesByLevel.get(id);
                    }
                    for (int i = 0; i < allColumns.size(); ++i) {
                        ColumnModelDefine col = (ColumnModelDefine)allColumns.get(i);
                        if (minus != null && (col.getAggrType() == AggrType.AVERAGE || col.getAggrType() == AggrType.SUM)) {
                            Number value1;
                            if (col.getColumnType() == ColumnModelType.INTEGER) {
                                value1 = (Integer)row.getValue(i);
                                if (minus < 0.0 && value1 != null) {
                                    value1 = -((Integer)value1).intValue();
                                }
                                gatherRow.writeValue(i, value1);
                                continue;
                            }
                            value1 = (BigDecimal)row.getValue(i);
                            if (minus < 0.0 && value1 != null) {
                                value1 = ((BigDecimal)value1).negate();
                            }
                            gatherRow.writeValue(i, value1);
                            continue;
                        }
                        gatherRow.writeValue(i, row.getValue(i));
                    }
                }
                INvwaDataRow addRow = updatableDataSet.addUpdateOrInsertRow();
                for (int i = 0; i < allColumns.size(); ++i) {
                    addRow.setValue(i, gatherRow.readValue(i));
                }
                Map map = (Map)pidAndDimValue.get(pid);
                gatherSingleDefines.remove(dwColumnModelDefine);
                for (ColumnModelDefine columnModelDefine : gatherSingleDefines) {
                    addRow.setValue(allColumns.indexOf(columnModelDefine), map.get(columnModelDefine.getCode()));
                }
                addRow.setValue(allColumns.indexOf(dwColumnModelDefine), (Object)pid);
            }
            updatableDataSet.commitChanges(context);
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    private TuplesFilter getTupleFilter(ColumnModelDefine dwColumnModelDefine, List<ColumnModelDefine> gatherSingleDefines, GatherEntityValue gatherEntityValue, String dwDimensionName, Integer level) {
        TuplesFilter tuplesFilter = null;
        if (!CollectionUtils.isEmpty(gatherSingleDefines)) {
            gatherSingleDefines.add(dwColumnModelDefine);
            Map<String, Map<String, String>> idAndDimValue = gatherEntityValue.getIdAndDimValue(level);
            tuplesFilter = new TuplesFilter(gatherSingleDefines);
            for (Map.Entry<String, Map<String, String>> entry : idAndDimValue.entrySet()) {
                ArrayList<String> tuples = new ArrayList<String>();
                String id = entry.getKey();
                Map<String, String> value = entry.getValue();
                for (ColumnModelDefine columnModelDefine : gatherSingleDefines) {
                    if (!columnModelDefine.getCode().equals(dwDimensionName)) {
                        String dimValue = value.get(columnModelDefine.getCode());
                        tuples.add(dimValue);
                        continue;
                    }
                    tuples.add(id);
                }
                tuplesFilter.addTuple(tuples);
            }
        }
        return tuplesFilter;
    }

    private GatherRow generateGatherRow(List<ColumnModelDefine> allColumns, Set<ColumnModelDefine> attachmentSet) {
        IGatherColumn[] gatherColumns = new IGatherColumn[allColumns.size()];
        for (int i = 0; i < allColumns.size(); ++i) {
            ColumnModelDefine columnModelDefine = allColumns.get(i);
            int statKind = 0;
            if (columnModelDefine != null && !attachmentSet.contains(columnModelDefine)) {
                AggrType aggrType = columnModelDefine.getAggrType();
                statKind = AggrTypeConvert.gatherTypeToStatKind(aggrType);
                if (columnModelDefine.getColumnType() == ColumnModelType.STRING && aggrType == AggrType.COUNT) {
                    statKind = 249;
                }
            }
            gatherColumns[i] = new StatGatherColumn(statKind, DataTypesConvert.fieldTypeToDataType((ColumnModelType)columnModelDefine.getColumnType()));
        }
        return new GatherRow(gatherColumns);
    }

    /*
     * WARNING - void declaration
     */
    public void executeFixedTableGatherForSql(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, boolean isMinus, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        List<ColumnModelDefine> allColumns = gatherDataTable.getAllColumns();
        boolean isAllFileFiledGather = true;
        for (ColumnModelDefine n : allColumns) {
            if (n.getColumnType() == ColumnModelType.ATTACHMENT) continue;
            isAllFileFiledGather = false;
            break;
        }
        if (!isAllFileFiledGather) {
            List<SqlItem> resultSqls = this.buildClearSqlsForFixedTable(gatherDataTable, gatherTempTable, noGatherTempTable, maxLevel, periodCode, targetDimensions);
            if (this.containTargetKey && resultSqls.size() == 2) {
                DataEngineUtil.executeUpdate((Connection)this.connection, (String)resultSqls.get(1).getExecutorSql(), (Object[])resultSqls.get(1).getParamValues().toArray());
            } else {
                for (SqlItem deleteSql : resultSqls) {
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    this.printLoggerSQL(deleteSql, "\u6c47\u603b\u524d\u7f6e", gatherDataTable.getTableModelDefine().getName());
                    DataEngineUtil.executeUpdate((Connection)this.connection, (String)deleteSql.getExecutorSql(), (Object[])deleteSql.getParamValues().toArray());
                    stopWatch.stop();
                    logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
                }
            }
        }
        Integer level = maxLevel;
        while (level >= 1) {
            String sourceSql;
            void var15_21;
            List<ColumnModelDefine> attachmentCols;
            Object var15_19 = null;
            if (!isMinus && !(attachmentCols = allColumns.stream().filter(e -> e.getColumnType() == ColumnModelType.ATTACHMENT).collect(Collectors.toList())).isEmpty()) {
                List<DataField> collect = this.getDataFields(gatherDataTable, attachmentCols);
                String getGroupKeySql = this.buildGetGroupKeySql(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, level, sourceDimensions, collect);
                List<TempConvertObject> groupKeyInfo = this.getFixGroupKeyInfo(periodCode, gatherTableDefine, collect, getGroupKeySql);
                List<FileSumInfo> sumInfos = this.getFileSumInfos(sourceDimensions, periodCode, groupKeyInfo);
                FileSumContext fileSumContext = new FileSumContext();
                fileSumContext.setTaskKey(this.taskKey);
                fileSumContext.setFileSumInfos(sumInfos);
                fileSumContext.setFormSchemeKey(this.formSchemeKey);
                fileSumContext.setGatherDataTable(gatherDataTable);
                List<FileSumInfo> sumInfos1 = this.fileCalculateService.sumFileGroup(fileSumContext);
                if (!CollectionUtils.isEmpty(sumInfos1)) {
                    ITempTable iTempTable = this.getFixFileGatherTempTable(gatherTableDefine, collect);
                    List<Object[]> objects = GatherTempTableUtils.fileGatherBuildTempValues(sumInfos1, iTempTable, collect);
                    iTempTable.insertRecords((List)objects);
                }
            }
            boolean bl = this.fileGather = var15_21 != null;
            if (this.fileGather) {
                sourceSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, isMinus, false, (int)level, sourceDimensions, targetDimensions, false, (ITempTable)var15_21);
            } else {
                if (isAllFileFiledGather) {
                    return;
                }
                sourceSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, isMinus, false, (int)level, sourceDimensions, targetDimensions, versionField, false);
            }
            if (querySqlUpdater != null) {
                QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
                sourceSql = querySqlUpdater.updateQuerySql(queryTable, "c1", sourceSql);
            }
            ArrayList<Object> paramValues = new ArrayList<Object>();
            this.addNoGatherParamValues(gatherTableDefine, noGatherTempTable, null, paramValues);
            this.addPeriodAndVersion(gatherTableDefine.getPeriodField(), periodCode, versionField, paramValues);
            List<HashMap<String, Object>> valMapList = this.doExecuteQuery(gatherDataTable, sourceSql, periodCode, paramValues);
            String updateTemplateSql = this.buildExecuteUpdateSqlNormal(gatherDataTable, versionField, this.fileGather);
            for (HashMap<String, Object> valMap : valMapList) {
                ArrayList<Object> params = new ArrayList<Object>();
                for (ColumnModelDefine gatherField : allColumns) {
                    if (!this.fileGather && gatherField.getColumnType() == ColumnModelType.ATTACHMENT) continue;
                    params.add(valMap.getOrDefault(Objects.requireNonNull(this.queryFieldName(gatherField)).toUpperCase(), null));
                }
                this.addUpdateWhereParams(params, gatherTableDefine, versionField, valMap);
                if (!StringUtils.isNotEmpty((String)updateTemplateSql)) continue;
                StopWatch stopWatch = new StopWatch();
                this.printLoggerSQL(updateTemplateSql, params, "\u6c47\u603b\u66f4\u65b0", gatherDataTable.getTableModelDefine().getName());
                stopWatch.start();
                DataEngineUtil.executeUpdate((Connection)this.connection, (String)updateTemplateSql, (Object[])params.toArray());
                stopWatch.stop();
                logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
            }
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    private List<DataField> getDataFields(GatherDataTable gatherDataTable, List<ColumnModelDefine> attachmentCols) {
        ArrayList<DataField> result = new ArrayList<DataField>();
        Map<ColumnModelDefine, DataField> gatherColumns2DataField = gatherDataTable.getGatherColumns2DataField();
        for (ColumnModelDefine columnModelDefine : attachmentCols) {
            result.add(gatherColumns2DataField.get(columnModelDefine));
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    public void executeFixedTableGather(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, boolean isMinus, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        List<ColumnModelDefine> allColumns = gatherDataTable.getAllColumns();
        boolean isAllFileFiledGather = true;
        for (ColumnModelDefine columnModelDefine : allColumns) {
            if (columnModelDefine.getColumnType() == ColumnModelType.ATTACHMENT) continue;
            isAllFileFiledGather = false;
            break;
        }
        if (isAllFileFiledGather) {
            return;
        }
        List<SqlItem> resultSqls = this.buildClearSqlsForFixedTable(gatherDataTable, gatherTempTable, noGatherTempTable, maxLevel, periodCode, targetDimensions);
        for (SqlItem deleteSql : resultSqls) {
            DataEngineUtil.executeUpdate((Connection)this.connection, (String)deleteSql.getExecutorSql(), (Object[])deleteSql.getParamValues().toArray());
        }
        String string = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, isMinus, true, -1, sourceDimensions, targetDimensions, versionField, false);
        if (querySqlUpdater != null) {
            QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
            String string2 = querySqlUpdater.updateQuerySql(queryTable, "c1", string);
        }
        Set<String> groupKeyFields = this.buildGroupKey(gatherDataTable, targetDimensions, gatherTableDefine.getClassifyFields());
        List<ColumnModelDefine> gatherFields = gatherDataTable.getAllColumns();
        Integer level = maxLevel;
        while (level >= 1) {
            CharSequence groupKey;
            void var15_19;
            ArrayList<Object> paramValues = new ArrayList<Object>();
            this.addNoGatherParamValues(gatherTableDefine, noGatherTempTable, level, paramValues);
            this.addPeriodAndVersion(gatherTableDefine.getPeriodField(), periodCode, versionField, paramValues);
            List<HashMap<String, Object>> valMapList = this.doExecuteQuery(gatherDataTable, (String)var15_19, periodCode, paramValues);
            HashMap groupQueryData = new HashMap();
            for (HashMap<String, Object> hashMap : valMapList) {
                groupKey = new StringBuffer();
                groupKeyFields.forEach(arg_0 -> GatherSqlGenerater.lambda$executeFixedTableGather$5((StringBuffer)groupKey, hashMap, arg_0));
                if (!groupQueryData.containsKey(((StringBuffer)groupKey).toString())) {
                    ArrayList newRow = new ArrayList();
                    groupQueryData.put(((StringBuffer)groupKey).toString(), newRow);
                }
                List rows = (List)groupQueryData.get(((StringBuffer)groupKey).toString());
                rows.add(hashMap);
            }
            for (Map.Entry entry : groupQueryData.entrySet()) {
                groupKey = (String)entry.getKey();
                List groupRows = (List)entry.getValue();
                HashMap<String, Object> sumMap = new HashMap<String, Object>();
                for (HashMap valMap : groupRows) {
                    this.gatherDataSum(gatherFields, valMap, sumMap, isMinus);
                    sumMap.putAll(this.parseExtraParam(gatherTableDefine, versionField, valMap));
                }
                this.avgFieldDataSum(gatherDataTable, sumMap, groupRows);
                ArrayList<HashMap<String, Object>> newGroupRows = new ArrayList<HashMap<String, Object>>();
                newGroupRows.add(sumMap);
                groupQueryData.put(groupKey, newGroupRows);
            }
            String updateTemplateSql = this.buildExecuteUpdateSqlNormal(gatherDataTable, versionField);
            for (Map.Entry entry : groupQueryData.entrySet()) {
                ArrayList<Object> params = new ArrayList<Object>();
                Optional rows = ((List)entry.getValue()).stream().findFirst();
                if (!rows.isPresent()) continue;
                for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
                    String fieldName = this.queryFieldName(gatherField).toUpperCase();
                    params.add(((HashMap)rows.get()).containsKey(fieldName) ? (Object)((HashMap)rows.get()).get(fieldName) : null);
                }
                this.addUpdateWhereParams(params, gatherTableDefine, versionField, (Map)rows.get());
                if (!StringUtils.isNotEmpty((String)updateTemplateSql)) continue;
                DataEngineUtil.executeUpdate((Connection)this.connection, (String)updateTemplateSql, (Object[])params.toArray());
            }
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    private void avgFieldDataSum(GatherDataTable gatherDataTable, HashMap<String, Object> sumMap, List<HashMap<String, Object>> groupRows) {
        List avgFields = gatherDataTable.getAllColumns().stream().filter(e -> e.getAggrType() == AggrType.AVERAGE).collect(Collectors.toList());
        if (!avgFields.isEmpty()) {
            for (ColumnModelDefine field : avgFields) {
                ColumnModelType fieldType = field.getColumnType();
                String fieldName = this.queryFieldName(field).toUpperCase();
                Object sumValue = sumMap.get(fieldName);
                if (sumValue == null) continue;
                if (fieldType == ColumnModelType.BIGDECIMAL) {
                    sumValue = BigDecimal.valueOf(((Number)sumValue).doubleValue()).divide(new BigDecimal(groupRows.size()));
                } else if (fieldType == ColumnModelType.DOUBLE) {
                    sumValue = Convert.toDouble((Object)sumValue) / Convert.toDouble((int)groupRows.size());
                } else if (fieldType == ColumnModelType.INTEGER) {
                    sumValue = Math.round(((Number)sumValue).doubleValue()) / (long)groupRows.size();
                }
                sumMap.put(fieldName, sumValue);
            }
        }
    }

    private Set<String> buildGroupKey(GatherDataTable gatherDataTable, DimensionValueSet targetDimensions, List<DataField> classifyFields) {
        LinkedHashSet<String> groupKey;
        block5: {
            GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
            groupKey = new LinkedHashSet<String>();
            groupKey.add(this.queryFieldName(gatherTableDefine.getUnitField()));
            if (gatherTableDefine.getPeriodField() != null) {
                groupKey.add(this.queryFieldName(gatherTableDefine.getPeriodField()));
            }
            if (classifyFields == null) break block5;
            if (targetDimensions != null && targetDimensions.size() > 0) {
                TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                for (DataField classifyField : classifyFields) {
                    String dimName;
                    String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                    if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) continue;
                    groupKey.add(this.queryFieldName(classifyField));
                }
            } else {
                for (DataField classifyField : classifyFields) {
                    groupKey.add(this.queryFieldName(classifyField));
                }
            }
        }
        return groupKey;
    }

    private void gatherDataSum(List<ColumnModelDefine> gatherFields, HashMap<String, Object> valMap, HashMap<String, Object> sumMap, boolean isMinus) {
        for (ColumnModelDefine fieldDefine : gatherFields) {
            String fieldName = this.queryFieldName(fieldDefine).toUpperCase();
            ColumnModelType fieldType = fieldDefine.getColumnType();
            Object value = valMap.get(fieldName);
            if (value == null) continue;
            if (isMinus) {
                Object minusValue = valMap.get("GT_ISMINUS");
                switch (fieldDefine.getAggrType()) {
                    case AVERAGE: {
                        MathUtil.mathAdd(fieldType, fieldName, sumMap, value, minusValue);
                        break;
                    }
                    case COUNT: {
                        MathUtil.mathCount(fieldName, sumMap, value);
                        break;
                    }
                    case MAX: {
                        MathUtil.mathMax(fieldType, fieldName, sumMap, value);
                        break;
                    }
                    case MIN: {
                        MathUtil.mathMin(fieldType, fieldName, sumMap, value);
                        break;
                    }
                    case SUM: {
                        MathUtil.mathAdd(fieldType, fieldName, sumMap, value, minusValue);
                        break;
                    }
                }
                continue;
            }
            switch (fieldDefine.getAggrType()) {
                case AVERAGE: {
                    MathUtil.mathAdd(fieldType, fieldName, sumMap, value, null);
                    break;
                }
                case COUNT: {
                    MathUtil.mathCount(fieldName, sumMap, value);
                    break;
                }
                case MAX: {
                    MathUtil.mathMax(fieldType, fieldName, sumMap, value);
                    break;
                }
                case MIN: {
                    MathUtil.mathMin(fieldType, fieldName, sumMap, value);
                    break;
                }
                case SUM: {
                    MathUtil.mathAdd(fieldType, fieldName, sumMap, value, null);
                    break;
                }
            }
        }
    }

    private String buildUpdateTemplateSql(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String sourceSql, String periodCode, DimensionValueSet targetDimensions) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(this.getRealTableName(gatherDataTable));
        updateSql.append(" set (");
        DataField periodField = gatherTableDefine.getPeriodField();
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        boolean addDot = false;
        for (ColumnModelDefine columnModelDefine : gatherDataTable.getAllColumns()) {
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(this.queryFieldName(columnModelDefine));
        }
        updateSql.append(")=(select ");
        addDot = false;
        for (ColumnModelDefine columnModelDefine : gatherDataTable.getAllColumns()) {
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(this.queryFieldName(columnModelDefine));
        }
        updateSql.append(" from (").append(sourceSql).append(") st where ");
        String tableName = this.getRealTableName(gatherDataTable);
        updateSql.append(String.format("%s.%s=%s.%s", tableName, this.queryFieldName(gatherTableDefine.getUnitField()), "st", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            updateSql.append(" and ").append(String.format("%s.%s='%s'", tableName, this.queryFieldName(periodField), periodCode));
        }
        if (classifyFields != null) {
            for (DataField dataField : classifyFields) {
                updateSql.append(String.format(" and %s.%s=%s.%s", "st", this.queryFieldName(dataField), tableName, this.queryFieldName(dataField)));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField dataField : gatherTableDefine.getUnClassifyFields()) {
                updateSql.append(String.format(" and %s.%s=%s.%s", "st", this.queryFieldName(dataField), tableName, this.queryFieldName(dataField)));
            }
        }
        updateSql.append(")");
        updateSql.append(" where exists (select 1 from ").append(String.format("%s %s", tableName, "c1")).append(" left join ").append(String.format("%s %s", gatherTempTable, "c2")).append(" on ");
        updateSql.append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_PID"));
        updateSql.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            updateSql.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
        }
        if (classifyFields != null && targetDimensions != null && targetDimensions.size() > 0) {
            TableModelRunInfo tableModelRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
            for (DataField classifyField : classifyFields) {
                String dimName = tableModelRunInfo == null ? "" : tableModelRunInfo.getDimensionName(classifyField.getCode());
                if (StringUtils.isEmpty((String)dimName) || !targetDimensions.hasValue(dimName)) continue;
                if (classifyField.getType() == FieldType.FIELD_TYPE_UUID) {
                    updateSql.append(String.format(" and %s.%s=%s", "c1", this.queryFieldName(classifyField), DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimName).toString()))));
                    continue;
                }
                updateSql.append(String.format(" and %s.%s='%s'", "c1", this.queryFieldName(classifyField), targetDimensions.getValue(dimName).toString()));
            }
        }
        updateSql.append(String.format(" and %s.%s=%s.%s", tableName, this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_PID"));
        if (periodField != null) {
            updateSql.append(String.format(" and %s.%s=%s.%s", tableName, this.queryFieldName(periodField), "c1", this.queryFieldName(periodField)));
        }
        if (classifyFields != null) {
            for (DataField dataField : classifyFields) {
                updateSql.append(String.format(" and %s.%s=%s.%s", tableName, this.queryFieldName(dataField), "c1", this.queryFieldName(dataField)));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField dataField : gatherTableDefine.getUnClassifyFields()) {
                updateSql.append(String.format(" and %s.%s=%s.%s", tableName, this.queryFieldName(dataField), "c1", this.queryFieldName(dataField)));
            }
        }
        this.appendExecutionSql(updateSql, true, false, null, "c2");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, updateSql, true, false, null, "c2");
        updateSql.append(")");
        return updateSql.toString();
    }

    private String buildUpdateSqlForMysql(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String sourceSql, String periodCode, DimensionValueSet targetDimensions) throws SQLException {
        String dimName;
        TableModelRunInfo tableRunInfo;
        DataField bizOrderField;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        String tableName = this.getRealTableName(gatherDataTable);
        DataField periodField = gatherTableDefine.getPeriodField();
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        boolean addDot = false;
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(this.getRealTableName(gatherDataTable));
        updateSql.append(" sr,");
        updateSql.append("(");
        updateSql.append(this.buildQuerySumDataSql(gatherDataTable, sourceSql, periodCode));
        updateSql.append(") ss");
        updateSql.append(" , (select ");
        updateSql.append(String.format("%s.%s as %s,", "c2", "GT_PID", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            updateSql.append(String.format("%s.%s,", "c1", this.queryFieldName(periodField)));
        }
        if ((bizOrderField = gatherTableDefine.getBizOrderField()) != null) {
            updateSql.append(String.format("%s as %s,", this.buildGuidSql(), this.queryFieldName(bizOrderField)));
        }
        if (classifyFields != null) {
            if (targetDimensions != null && targetDimensions.size() > 0) {
                tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                for (DataField classifyField : classifyFields) {
                    String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                    if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) {
                        updateSql.append(String.format(" %s,", this.queryFieldName(classifyField)));
                        continue;
                    }
                    updateSql.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            } else {
                for (DataField dataField : classifyFields) {
                    updateSql.append(String.format("%s.%s,", "c1", this.queryFieldName(dataField)));
                }
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField dataField : gatherTableDefine.getUnClassifyFields()) {
                updateSql.append(String.format("%s.%s,", "c1", this.queryFieldName(dataField)));
            }
        }
        updateSql.append(" 1 from ");
        updateSql.append(String.format("%s %s", tableName, "c1")).append(" left join ").append(String.format("%s %s", gatherTempTable, "c2")).append(" on ");
        updateSql.append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_PID"));
        updateSql.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        this.appendExecutionSql(updateSql, true, false, null, "c2");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, updateSql, true, false, null, "c2");
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            updateSql.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
        }
        if (classifyFields != null && targetDimensions != null && targetDimensions.size() > 0) {
            tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
            for (DataField classifyField : classifyFields) {
                dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                if (StringUtils.isEmpty((String)dimName) || !targetDimensions.hasValue(dimName)) continue;
                if (classifyField.getType() == FieldType.FIELD_TYPE_UUID) {
                    updateSql.append(String.format(" and %s.%s=%s ", "c1", this.queryFieldName(classifyField), DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimName).toString()))));
                    continue;
                }
                updateSql.append(String.format(" and %s.%s='%s' ", "c1", this.queryFieldName(classifyField), targetDimensions.getValue(dimName).toString()));
            }
        }
        updateSql.append(") sw ");
        updateSql.append("set ");
        addDot = false;
        for (ColumnModelDefine columnModelDefine : gatherDataTable.getAllColumns()) {
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append("sr." + this.queryFieldName(columnModelDefine));
            updateSql.append("=");
            updateSql.append("ss." + this.queryFieldName(columnModelDefine));
        }
        updateSql.append(" where ");
        updateSql.append(String.format(" %s.%s=%s.%s", "sr", this.queryFieldName(gatherTableDefine.getUnitField()), "sw", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            updateSql.append(String.format(" and %s.%s=%s.%s", "sr", this.queryFieldName(periodField), "sw", this.queryFieldName(periodField)));
        }
        if (classifyFields != null) {
            for (DataField dataField : classifyFields) {
                updateSql.append(String.format(" and %s.%s=%s.%s", "sr", this.queryFieldName(dataField), "sw", this.queryFieldName(dataField)));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField dataField : gatherTableDefine.getUnClassifyFields()) {
                updateSql.append(String.format(" and %s.%s=%s.%s", "sr", this.queryFieldName(dataField), "sw", this.queryFieldName(dataField)));
            }
        }
        return updateSql.toString();
    }

    private List<String> generateSourceFields(String sourceSql) {
        String upSql = sourceSql;
        String[] splitSql = upSql.toUpperCase().split("SELECT");
        String res = upSql.toUpperCase().split("SELECT")[1];
        int start = splitSql[0].length() + 6;
        String newSql = sourceSql.substring(start, start + res.indexOf("FROM"));
        String[] fields = newSql.split(",");
        List<String> fieldList = Arrays.asList(fields);
        return fieldList;
    }

    private List<String> generateSqlFields(GatherDataTable gatherDataTable) {
        DataField bizOrderField;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        ArrayList<String> sqlFieldsList = new ArrayList<String>();
        DataField periodField = gatherTableDefine.getPeriodField();
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        sqlFieldsList.add(this.queryFieldName(gatherTableDefine.getUnitField()));
        if (periodField != null) {
            sqlFieldsList.add(this.queryFieldName(periodField));
        }
        if ((bizOrderField = gatherTableDefine.getBizOrderField()) != null) {
            sqlFieldsList.add(this.queryFieldName(bizOrderField));
        }
        if (classifyFields != null) {
            for (DataField classifyField : classifyFields) {
                sqlFieldsList.add(this.queryFieldName(classifyField));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                sqlFieldsList.add(this.queryFieldName(unClassifyField));
            }
        }
        for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
            if (!this.fileGather && gatherField.getColumnType() == ColumnModelType.ATTACHMENT) continue;
            sqlFieldsList.add(this.queryFieldName(gatherField));
        }
        return sqlFieldsList;
    }

    private List<String> buildQuerySumDataSql(GatherDataTable gatherDataTable, String sourceSql, String periodCode) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        DataField periodField = gatherTableDefine.getPeriodField();
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        String tableName = this.getRealTableName(gatherDataTable);
        DataField bizOrderField = gatherTableDefine.getBizOrderField();
        List<String> sqlFields = this.generateSqlFields(gatherDataTable);
        List<String> sourceSqlFields = this.generateSourceFields(sourceSql);
        ArrayList<String> sqlList = new ArrayList<String>();
        List<List<String>> fieldsList = this.avgerList(sqlFields);
        List<List<String>> sourceFieldsList = this.avgerList(sourceSqlFields);
        if (CollectionUtils.isEmpty(sourceFieldsList)) {
            return Collections.emptyList();
        }
        List<String> firstFields = sourceFieldsList.get(0);
        ArrayList<String> dims = new ArrayList<String>();
        dims.add(this.queryFieldName(gatherTableDefine.getUnitField()));
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            dims.add(this.queryFieldName(periodField));
        }
        if (bizOrderField != null) {
            dims.add(this.queryFieldName(bizOrderField));
        }
        if (classifyFields != null) {
            for (DataField classifyField : classifyFields) {
                dims.add(this.queryFieldName(classifyField));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                dims.add(this.queryFieldName(unClassifyField));
            }
        }
        List<String> prefixFields = firstFields.subList(0, dims.size());
        int size = fieldsList.size();
        boolean isFirst = false;
        for (int i = 0; i < size; ++i) {
            StringBuilder querySql = new StringBuilder();
            querySql.append("select ");
            List<String> fields = fieldsList.get(i);
            boolean addFlag = false;
            for (String field : fields) {
                if (addFlag) {
                    querySql.append(",");
                }
                querySql.append("st.").append(field);
                addFlag = true;
            }
            List<String> sourceFields = sourceFieldsList.get(i);
            ArrayList<String> sourceMergeFields = new ArrayList<String>();
            if (isFirst) {
                sourceMergeFields.addAll(prefixFields);
            }
            sourceMergeFields.addAll(sourceFields);
            querySql.append(" from (").append(this.rebuildSourceSQl(sourceSql, sourceMergeFields)).append(") st,");
            querySql.append(tableName);
            querySql.append(" where ");
            querySql.append(String.format("%s.%s=%s.%s", tableName, this.queryFieldName(gatherTableDefine.getUnitField()), "st", this.queryFieldName(gatherTableDefine.getUnitField())));
            if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
                querySql.append(" and ").append(String.format("%s.%s=?", tableName, this.queryFieldName(periodField)));
            }
            if (bizOrderField != null) {
                querySql.append(String.format(" and %s.%s=%s.%s,", tableName, this.queryFieldName(bizOrderField), "st", this.queryFieldName(bizOrderField)));
            }
            if (classifyFields != null) {
                for (DataField classifyField : classifyFields) {
                    querySql.append(String.format(" and %s.%s=%s.%s", "st", this.queryFieldName(classifyField), tableName, this.queryFieldName(classifyField)));
                }
            }
            if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
                for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                    querySql.append(String.format(" and %s.%s=%s.%s", "st", this.queryFieldName(unClassifyField), tableName, this.queryFieldName(unClassifyField)));
                }
            }
            sqlList.add(querySql.toString());
            isFirst = true;
        }
        return sqlList;
    }

    protected List<SqlItem> buildClearSqlsForFixedTable(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, String periodCode, DimensionValueSet targetDimensions) {
        ArrayList<String> keysList;
        Object tableRunInfo;
        HashMap<String, ColumnModelDefine> dimFields;
        ArrayList<SqlItem> clearSqls = new ArrayList<SqlItem>();
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(this.getRealTableName(gatherDataTable));
        updateSql.append(" set ");
        for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
            if (gatherField.getColumnType() == ColumnModelType.ATTACHMENT) continue;
            updateSql.append(String.format("%s=null,", this.queryFieldName(gatherField)));
        }
        updateSql.setLength(updateSql.length() - 1);
        updateSql.append(" where ");
        if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
            updateSql.append(String.format("%s=? and ", this.queryFieldName(gatherTableDefine.getPeriodField())));
            paramValues.add(periodCode);
        }
        if ((dimFields = this.getTargetDimFields(gatherDataTable, targetDimensions)).size() > 0) {
            this.buildWhereDimensions(updateSql, dimFields, targetDimensions, false, "", paramValues);
        }
        if (this.database.getName().equalsIgnoreCase("Informix")) {
            updateSql.append(String.format("%s in (select %s from %s where ", this.queryFieldName(gatherTableDefine.getUnitField()), "GT_PID", gatherTempTable));
            this.appendGatherDimSQL(updateSql, true, gatherDataTable, null);
            this.appendExecutionSql(updateSql, true, false, paramValues, gatherTempTable);
            int lastWhereIndex = updateSql.lastIndexOf("where");
            int firstAndIndex = updateSql.indexOf("and", lastWhereIndex);
            if (firstAndIndex > lastWhereIndex) {
                Iterator<DataField> updateSql1 = updateSql.substring(0, lastWhereIndex + 5) + " " + updateSql.substring(firstAndIndex + 3);
                updateSql = new StringBuilder((String)((Object)updateSql1));
            }
        } else {
            updateSql.append(String.format("exists (select 1 from %s where %s=%s", gatherTempTable, this.queryFieldName(gatherTableDefine.getUnitField()), "GT_PID"));
            this.appendGatherDimSQL(updateSql, true, gatherDataTable, null);
            this.appendExecutionSql(updateSql, true, false, paramValues, gatherTempTable);
        }
        this.appendNoGatherSql(gatherTableDefine, noGatherTempTable, updateSql, true, false, paramValues, gatherTempTable);
        updateSql.append(")");
        SqlItem sqlItem = new SqlItem();
        sqlItem.setExecutorSql(updateSql.toString());
        sqlItem.setParamValues(paramValues);
        clearSqls.add(sqlItem);
        paramValues = new ArrayList();
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(this.getRealTableName(gatherDataTable));
        insertSql.append("(");
        insertSql.append(this.queryFieldName(gatherTableDefine.getUnitField())).append(",");
        if (gatherTableDefine.getPeriodField() != null) {
            insertSql.append(this.queryFieldName(gatherTableDefine.getPeriodField())).append(",");
        }
        if (gatherTableDefine.getClassifyFields() != null) {
            for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                insertSql.append(this.queryFieldName(classifyField)).append(",");
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                insertSql.append(this.queryFieldName(unClassifyField)).append(",");
            }
        }
        insertSql.setLength(insertSql.length() - 1);
        insertSql.append(")");
        insertSql.append(" select distinct ").append(String.format("%s.%s", "c2", "GT_PID"));
        insertSql.append(" as ").append(this.queryFieldName(gatherTableDefine.getUnitField())).append(",");
        if (gatherTableDefine.getPeriodField() != null) {
            if (!StringUtils.isEmpty((String)periodCode)) {
                insertSql.append(String.format("'%s' as %s,", periodCode, this.queryFieldName(gatherTableDefine.getPeriodField())));
            } else {
                insertSql.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getPeriodField())));
            }
        }
        boolean allValue = false;
        if (gatherTableDefine.getClassifyFields() != null) {
            TableModelRunInfo tableRunInfo2 = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
            ArrayList<String> keysList2 = new ArrayList<String>(this.gatherSingleDims.keySet());
            for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                String dimName = tableRunInfo2.getDimensionName(classifyField.getCode());
                int index = keysList2.indexOf(dimName);
                if (index >= 0) {
                    LogicField field = GatherTempTableHandler.getDimCols().get(index);
                    insertSql.append(String.format("%s.%s,", "c2", field.getFieldName()));
                    continue;
                }
                if (targetDimensions != null && targetDimensions.hasValue(dimName)) {
                    Object paramValue = targetDimensions.getValue(dimName);
                    insertSql.append(String.format("? as %s,", this.queryFieldName(classifyField)));
                    paramValues.add(paramValue);
                    allValue = true;
                    continue;
                }
                insertSql.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                insertSql.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
            }
        }
        insertSql.setLength(insertSql.length() - 1);
        int paramIndex = paramValues.size();
        if (allValue) {
            insertSql.append(" from ");
            insertSql.append(String.format("%s %s where ", gatherTempTable, "c2"));
            insertSql.append(String.format("not exists (select 1 from %s t3 where t3.%s=%s.%s", this.getRealTableName(gatherDataTable), this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_PID"));
            if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
                insertSql.append(" and ").append(String.format("t3.%s=?", this.queryFieldName(gatherTableDefine.getPeriodField())));
                paramValues.add(periodCode);
            }
            if (dimFields.size() > 0) {
                this.buildWhereDimensions(insertSql, dimFields, targetDimensions, true, String.format("%s.", "t3"), paramValues);
            }
            if (gatherTableDefine.getClassifyFields() != null) {
                tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                keysList = new ArrayList<String>(this.gatherSingleDims.keySet());
                for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                    String dimName = tableRunInfo.getDimensionName(classifyField.getCode());
                    int index = keysList.indexOf(dimName);
                    if (index < 0) continue;
                    LogicField field = GatherTempTableHandler.getDimCols().get(index);
                    insertSql.append(String.format(" and t3.%s=%s.%s", this.queryFieldName(classifyField), "c2", field.getFieldName()));
                }
            }
            insertSql.append(")");
            this.appendExecutionSql(insertSql, true, false, paramValues, "c2");
            this.appendNoGatherSql(gatherTableDefine, noGatherTempTable, insertSql, true, false, paramValues, "c2");
            sqlItem = new SqlItem();
            sqlItem.setExecutorSql(insertSql.toString());
            ArrayList<Object> params = new ArrayList<Object>(paramValues);
            sqlItem.setParamValues(params);
            clearSqls.add(sqlItem);
        } else {
            insertSql.append(" from ").append(String.format("%s %s,", this.getRealTableName(gatherDataTable), "c1"));
            insertSql.append(String.format("%s %s where ", gatherTempTable, "c2"));
            insertSql.append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_ID"));
            insertSql.append(" and ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
            if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
                insertSql.append(" and ").append(String.format("%s.%s=?", "c1", this.queryFieldName(gatherTableDefine.getPeriodField())));
                paramValues.add(periodCode);
            }
            if (dimFields.size() > 0) {
                this.buildWhereDimensions(insertSql, dimFields, targetDimensions, true, String.format("%s.", "c1"), paramValues);
            }
            insertSql.append(String.format(" and not exists (select 1 from %s t3 where t3.%s=%s.%s", this.getRealTableName(gatherDataTable), this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_PID"));
            if (gatherTableDefine.getPeriodField() != null) {
                insertSql.append(" and ").append(String.format("t3.%s=%s.%s", this.queryFieldName(gatherTableDefine.getPeriodField()), "c1", this.queryFieldName(gatherTableDefine.getPeriodField())));
            }
            if (gatherTableDefine.getClassifyFields() != null) {
                tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                keysList = new ArrayList<String>(this.gatherSingleDims.keySet());
                for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                    String dimName = tableRunInfo.getDimensionName(classifyField.getCode());
                    int index = keysList.indexOf(dimName);
                    if (index >= 0) {
                        LogicField field = GatherTempTableHandler.getDimCols().get(index);
                        insertSql.append(String.format(" and t3.%s=%s.%s", this.queryFieldName(classifyField), "c2", field.getFieldName()));
                        continue;
                    }
                    insertSql.append(" and ").append(String.format("t3.%s=%s.%s", this.queryFieldName(classifyField), "c1", this.queryFieldName(classifyField)));
                }
            }
            if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
                for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                    insertSql.append(" and ").append(String.format("t3.%s=%s.%s", this.queryFieldName(unClassifyField), "c1", this.queryFieldName(unClassifyField)));
                }
            }
            insertSql.append(")");
            this.appendExecutionSql(insertSql, true, false, paramValues, "c2");
            this.appendNoGatherSql(gatherTableDefine, noGatherTempTable, insertSql, true, false, paramValues, "c2");
            Integer index = maxLevel;
            while (index >= 1) {
                sqlItem = new SqlItem();
                sqlItem.setExecutorSql(insertSql.toString());
                ArrayList<Object> params = new ArrayList<Object>(paramValues);
                params.add(paramIndex, index);
                sqlItem.setParamValues(params);
                clearSqls.add(sqlItem);
                Integer n = index;
                index = index - 1;
                Integer n2 = index;
            }
        }
        return clearSqls;
    }

    private String rebuildSourceSQl(String sourceSql, List<String> sourceSqlFiedls) {
        String tmpSql = sourceSql;
        tmpSql = tmpSql.toUpperCase();
        StringBuffer strSQL = new StringBuffer();
        strSQL.append(" SELECT ");
        boolean addFlag = false;
        for (String field : sourceSqlFiedls) {
            if (addFlag) {
                strSQL.append(",");
            }
            strSQL.append(field);
            addFlag = true;
        }
        strSQL.append(" ");
        strSQL.append(sourceSql.substring(tmpSql.indexOf("FROM")));
        return strSQL.toString();
    }

    protected void buildWhereDimensions(StringBuilder updateSql, HashMap<String, ColumnModelDefine> dimFields, DimensionValueSet targetDimensions, boolean andPrev, String tableAlias, List<Object> paramValues) {
        for (Map.Entry<String, ColumnModelDefine> dimPair : dimFields.entrySet()) {
            String fieldName = dimPair.getValue().getName();
            String dimName = dimPair.getKey();
            if (this.gatherSingleDims.keySet().contains(dimName)) continue;
            if (paramValues == null) {
                Object argValue = targetDimensions.getValue(dimPair.getKey());
                if (argValue instanceof List) {
                    List listValue = (List)argValue;
                    updateSql.append(andPrev ? " and " : "").append(String.format("%s%s in (", tableAlias, fieldName));
                    boolean addDot = false;
                    for (int i = 0; i < listValue.size(); ++i) {
                        if (addDot) {
                            updateSql.append(",");
                        }
                        addDot = true;
                        if (dimPair.getValue().getColumnType() == ColumnModelType.UUID) {
                            updateSql.append(DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(listValue.get(i).toString())));
                            continue;
                        }
                        updateSql.append(String.format("'%s'", listValue.get(i).toString()));
                    }
                    updateSql.append(") ").append(andPrev ? "" : " and ");
                    continue;
                }
                if (dimPair.getValue().getColumnType() == ColumnModelType.UUID) {
                    updateSql.append(String.format("%s%s%s=%s %s", andPrev ? " and " : "", tableAlias, fieldName, DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimPair.getKey()).toString())), andPrev ? "" : " and "));
                    continue;
                }
                updateSql.append(String.format("%s%s%s='%s' %s", andPrev ? " and " : "", tableAlias, fieldName, targetDimensions.getValue(dimPair.getKey()), andPrev ? "" : " and "));
                continue;
            }
            updateSql.append(String.format("%s%s%s=? %s", andPrev ? " and " : "", tableAlias, fieldName, andPrev ? "" : " and "));
            if (dimPair.getValue().getColumnType() == ColumnModelType.UUID) {
                paramValues.add(DataEngineConsts.toBytes((Object)targetDimensions.getValue(dimPair.getKey())));
                continue;
            }
            paramValues.add(targetDimensions.getValue(dimPair.getKey()));
        }
    }

    protected HashMap<String, ColumnModelDefine> getTargetDimFields(GatherDataTable gatherDataTable, DimensionValueSet targetDimensions) {
        HashMap<String, ColumnModelDefine> fieldDefines = new HashMap<String, ColumnModelDefine>();
        if (targetDimensions == null || targetDimensions.size() <= 0) {
            return fieldDefines;
        }
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
        if (tableRunInfo == null) {
            return fieldDefines;
        }
        for (int index = 0; index < targetDimensions.size(); ++index) {
            String dimName = targetDimensions.getName(index);
            ColumnModelDefine dimField = tableRunInfo.getDimensionField(dimName);
            if (dimField == null) continue;
            fieldDefines.put(dimName, dimField);
        }
        return fieldDefines;
    }

    public List<String> buildClassifyGatherSqls(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, Integer maxLevel, boolean isMinus, boolean listGather, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        ArrayList<String> resultSqls = new ArrayList<String>();
        if (!this.containTargetKey) {
            resultSqls.add(this.buildClearSqlsForFloatTable(gatherDataTable, gatherTempTable, noGatherTable, periodCode, targetDimensions));
        }
        Integer index = maxLevel;
        while (index >= 1) {
            String excuteSql = this.buildFloatTableSqlFormat(gatherDataTable, gatherTempTable, noGatherTable, periodCode, isMinus, listGather, index, sourceDimensions, targetDimensions, versionField);
            if (querySqlUpdater != null) {
                QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
                excuteSql = querySqlUpdater.updateQuerySql(queryTable, "c1", excuteSql);
            }
            resultSqls.add(excuteSql);
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index - 1);
        }
        return resultSqls;
    }

    public void executeGatherTableByOrderFieldForNrdb(GatherDataTable gatherDataTable, GatherEntityValue gatherEntityValue, NotGatherEntityValue notGatherEntityValue, Integer maxLevel, boolean isMinus, boolean listGather, String periodCode, DimensionValueSet dimensionValueSet, EntityViewDefine viewDefine, Map<Integer, Map<String, String>> bizKeyOrderMappings) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        FloatTableGatherSetting floatTableGatherSetting = gatherTableDefine.getFloatTableGatherSetting();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        NvwaDataEngineQueryUtil dataEngineQueryUtil = (NvwaDataEngineQueryUtil)BeanUtil.getBean(NvwaDataEngineQueryUtil.class);
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        ArrayList<ColumnModelDefine> allColumns = new ArrayList<ColumnModelDefine>();
        allColumns.addAll(gatherDataTable.getAllDimsColumns());
        allColumns.addAll(gatherDataTable.getAllColumns());
        Map<String, ColumnModelDefine> fieldNameAndDefine = allColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        String dimensionName = entityMetaService.getDimensionName(viewDefine.getEntityId());
        String periodDimension = periodAdapter.getPeriodDimensionName();
        String realTableName = this.getRealTableName(gatherDataTable);
        DataAccessContext context = new DataAccessContext(dataModelService);
        ColumnModelDefine dwColumnModelDefine = dataEngineQueryUtil.getColumnModelDefine(realTableName, dimensionName);
        int dwIndex = allColumns.indexOf(dwColumnModelDefine);
        ColumnModelDefine bizKeyOrder = fieldNameAndDefine.get(this.queryFieldName(gatherTableDefine.getBizOrderField()));
        ColumnModelDefine floatOrder = fieldNameAndDefine.get(this.queryFieldName(gatherTableDefine.getOrderField()));
        ArrayList<ColumnModelDefine> rowKeyColumns = new ArrayList<ColumnModelDefine>(gatherDataTable.getAllDimsColumns());
        rowKeyColumns.remove(dwColumnModelDefine);
        rowKeyColumns.remove(bizKeyOrder);
        rowKeyColumns.remove(floatOrder);
        int floatOrderIndex = allColumns.indexOf(floatOrder);
        ArrayList<ColumnModelDefine> gatherSingleDefines = new ArrayList<ColumnModelDefine>();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims)) {
            for (Map.Entry<String, String> entry : this.gatherSingleDims.entrySet()) {
                gatherSingleDefines.add(dataEngineQueryUtil.getColumnModelDefine(realTableName, entry.getKey()));
            }
        }
        List<DataField> attachmentCollect = gatherTableDefine.getGatherFields().stream().filter(e -> e.getDataFieldType() == DataFieldType.PICTURE || e.getDataFieldType() == DataFieldType.FILE).collect(Collectors.toList());
        HashSet<ColumnModelDefine> attachmentSet = new HashSet<ColumnModelDefine>();
        attachmentCollect.forEach(a -> {
            int index = allColumns.indexOf(fieldNameAndDefine.get(this.queryFieldName((DataField)a)));
            attachmentSet.add((ColumnModelDefine)allColumns.get(index));
        });
        ArrayList<DataField> dimFields = new ArrayList<DataField>();
        dimFields.addAll(gatherTableDefine.getClassifyFields());
        dimFields.addAll(gatherTableDefine.getUnClassifyFields());
        Map<String, Boolean> orderMap = null;
        ArrayList<SortInfo> sortInfo = new ArrayList();
        if (listGather && !isMinus) {
            orderMap = this.getOrderColumn(dimFields, floatTableGatherSetting);
        } else {
            ArrayList<ColumnModelDefine> dimColumnModelDefines = new ArrayList<ColumnModelDefine>();
            for (DataField dataField : dimFields) {
                dimColumnModelDefines.add(fieldNameAndDefine.get(this.queryFieldName(dataField)));
            }
            sortInfo = this.getSortInfo(allColumns, fieldNameAndDefine, dimColumnModelDefines, floatTableGatherSetting);
        }
        DimensionValueSet refreshValueSet = new DimensionValueSet(dimensionValueSet);
        Integer level = maxLevel;
        while (level >= 1) {
            List<String> idValuesByLevel;
            TuplesFilter tupleFilter = this.getTupleFilter(dwColumnModelDefine, gatherSingleDefines, gatherEntityValue, dimensionName, level);
            List<String> pids = gatherEntityValue.getPIdValuesByLevelAndRemove(level, notGatherEntityValue, gatherTableDefine.getFormId());
            HashMap parentsBizKeyOrderInfo = new HashMap();
            if (!listGather && gatherTableDefine.getTableDefine().isRepeatCode() && !CollectionUtils.isEmpty(pids)) {
                dimensionValueSet.setValue(dimensionName, pids);
                dimensionValueSet.setValue(periodDimension, (Object)periodCode);
                INvwaDataSet infoDataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, null, tupleFilter, true);
                for (int i = 0; i < infoDataSet.size(); ++i) {
                    INvwaDataRow item = infoDataSet.getRow(i);
                    String pid = item.getValue(dwColumnModelDefine).toString();
                    if (!parentsBizKeyOrderInfo.containsKey(pid)) {
                        parentsBizKeyOrderInfo.put(pid, new HashMap());
                    }
                    Map map = (Map)parentsBizKeyOrderInfo.get(pid);
                    StringBuilder rowKey = new StringBuilder();
                    for (ColumnModelDefine columnModelDefine : rowKeyColumns) {
                        rowKey.append(item.getValue(columnModelDefine)).append(";");
                    }
                    map.put(rowKey.toString(), item.getValue(bizKeyOrder).toString());
                }
            }
            if (!CollectionUtils.isEmpty(pids)) {
                dimensionValueSet.setValue(dimensionName, pids);
                dimensionValueSet.setValue(periodDimension, (Object)periodCode);
                INvwaDataSet deleteDataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, null, tupleFilter, false);
                INvwaUpdatableDataSet delete = (INvwaUpdatableDataSet)deleteDataSet;
                delete.deleteAll();
                delete.commitChanges(context);
            }
            Map<Object, Object> pidAndDimValue = new HashMap();
            if (!CollectionUtils.isEmpty(this.gatherSingleDims)) {
                pidAndDimValue = gatherEntityValue.getPIdAndDimValue(level);
            }
            if (!CollectionUtils.isEmpty(idValuesByLevel = gatherEntityValue.getIdValuesByLevelAndRemove(level, notGatherEntityValue, gatherTableDefine.getFormId()))) {
                List<String> pIdValuesByLevelAndRemove;
                String pid;
                dimensionValueSet.setValue(dimensionName, idValuesByLevel);
                INvwaDataSet gatherDataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, orderMap, tupleFilter, true);
                Map<String, String> idValuesAndPidValuesByLevel = gatherEntityValue.getIdValuesAndPidValuesByLevel(level);
                if (listGather && !isMinus) {
                    Map<Object, Object> fileInfo = new HashMap();
                    if (!CollectionUtils.isEmpty(attachmentCollect)) {
                        dimensionValueSet.clearValue(dimensionName);
                        dimensionValueSet.clearValue(periodDimension);
                        fileInfo = this.getFileInfo(dimensionValueSet, periodCode, this.formSchemeKey, dwColumnModelDefine, bizKeyOrder, gatherDataSet, attachmentCollect, fieldNameAndDefine);
                    }
                    INvwaDataUpdator updatableDataSet = dataEngineQueryUtil.getUpdateDataSet(allColumns, context);
                    HashMap<String, String> bizKeyOrderMap = new HashMap<String, String>();
                    int floatNumber = 1;
                    for (int i = 0; i < gatherDataSet.size(); ++i) {
                        INvwaDataRow item = gatherDataSet.getRow(i);
                        Object dw = item.getValue(dwIndex);
                        pid = idValuesAndPidValuesByLevel.get((String)dw);
                        INvwaDataRow addRow = updatableDataSet.addInsertRow();
                        for (int j = 0; j < allColumns.size(); ++j) {
                            addRow.setValue(j, item.getValue(j));
                        }
                        String bizKeyOrderValue = item.getValue(bizKeyOrder).toString();
                        LinkedHashMap groupKeyValue = (LinkedHashMap)fileInfo.get(bizKeyOrderValue);
                        if (!CollectionUtils.isEmpty(groupKeyValue)) {
                            for (DataField dataField : attachmentCollect) {
                                ColumnModelDefine fileFieldDefine = fieldNameAndDefine.get(this.queryFieldName(dataField));
                                int index = allColumns.indexOf(fileFieldDefine);
                                addRow.setValue(index, groupKeyValue.get(dataField.getKey()));
                            }
                        }
                        String string = UUIDUtils.getKey();
                        addRow.setKeyValue(bizKeyOrder, (Object)string);
                        if (gatherTableDefine.getTableDefine().getSyncError().booleanValue()) {
                            bizKeyOrderMap.put(bizKeyOrderValue, string);
                        }
                        Map map = (Map)pidAndDimValue.get(pid);
                        gatherSingleDefines.remove(dwColumnModelDefine);
                        for (ColumnModelDefine columnModelDefine : gatherSingleDefines) {
                            addRow.setValue(allColumns.indexOf(columnModelDefine), map.get(columnModelDefine.getCode()));
                        }
                        addRow.setValue(dwIndex, (Object)pid);
                        addRow.setValue(floatOrderIndex, (Object)floatNumber++);
                    }
                    bizKeyOrderMappings.put(level, bizKeyOrderMap);
                    updatableDataSet.commitChanges(context);
                } else {
                    Map<String, Double> idValuesAndMinusValuesByLevel = null;
                    if (isMinus) {
                        idValuesAndMinusValuesByLevel = gatherEntityValue.getIdValuesAndMinusValuesByLevel(level);
                    }
                    HashMap pidAndGatherRows = new HashMap();
                    INvwaDataUpdator updatableDataSet = dataEngineQueryUtil.getUpdateDataSet(allColumns, context);
                    for (int i = 0; i < gatherDataSet.size(); ++i) {
                        INvwaDataRow item = gatherDataSet.getRow(i);
                        StringBuilder rowKey = new StringBuilder();
                        for (ColumnModelDefine columnModelDefine : rowKeyColumns) {
                            rowKey.append(item.getValue(columnModelDefine)).append(";");
                        }
                        String id = (String)item.getValue(dwIndex);
                        pid = idValuesAndPidValuesByLevel.get(id);
                        if (!pidAndGatherRows.containsKey(pid)) {
                            pidAndGatherRows.put(pid, new HashMap());
                        }
                        GatherRow gatherRow = this.getGatherRow(allColumns, gatherDataTable.getAllDimsColumns(), attachmentSet, (Map)pidAndGatherRows.get(pid), rowKey.toString(), sortInfo, floatTableGatherSetting);
                        Double minus = null;
                        if (isMinus) {
                            minus = idValuesAndMinusValuesByLevel.get(id);
                        }
                        for (int j = 0; j < allColumns.size(); ++j) {
                            ColumnModelDefine columnModelDefine = (ColumnModelDefine)allColumns.get(j);
                            if (minus != null && (columnModelDefine.getAggrType() == AggrType.AVERAGE || columnModelDefine.getAggrType() == AggrType.SUM)) {
                                Number value1;
                                if (columnModelDefine.getColumnType() == ColumnModelType.INTEGER) {
                                    value1 = (Integer)item.getValue(j);
                                    if (minus < 0.0 && value1 != null) {
                                        value1 = -((Integer)value1).intValue();
                                    }
                                    gatherRow.writeValue(j, value1);
                                    continue;
                                }
                                value1 = (BigDecimal)item.getValue(j);
                                if (minus < 0.0 && value1 != null) {
                                    value1 = ((BigDecimal)value1).negate();
                                }
                                gatherRow.writeValue(j, value1);
                                continue;
                            }
                            gatherRow.writeValue(j, item.getValue(j));
                        }
                    }
                    block12: for (Map.Entry mapEntry : pidAndGatherRows.entrySet()) {
                        String pid2 = (String)mapEntry.getKey();
                        Map value = (Map)mapEntry.getValue();
                        value.values().forEach(GatherRow::generateSortValues);
                        ArrayList sortedValue = new ArrayList(value.entrySet());
                        sortedValue.sort(Map.Entry.comparingByValue());
                        Map bizKeyOrderMap = (Map)parentsBizKeyOrderInfo.get(pid2);
                        int floatNumber = 1;
                        for (Map.Entry entry : sortedValue) {
                            Integer reservedRows;
                            INvwaDataRow addRow = updatableDataSet.addInsertRow();
                            String rowKey = (String)entry.getKey();
                            GatherRow row = (GatherRow)entry.getValue();
                            for (int i = 0; i < allColumns.size(); ++i) {
                                addRow.setValue(i, row.readValue(i));
                            }
                            Map valueMap = (Map)pidAndDimValue.get(pid2);
                            gatherSingleDefines.remove(dwColumnModelDefine);
                            for (ColumnModelDefine columnModelDefine : gatherSingleDefines) {
                                addRow.setValue(allColumns.indexOf(columnModelDefine), valueMap.get(columnModelDefine.getCode()));
                            }
                            addRow.setValue(dwIndex, (Object)pid2);
                            addRow.setValue(floatOrderIndex, (Object)floatNumber++);
                            if (!CollectionUtils.isEmpty(bizKeyOrderMap)) {
                                String parentBizKeyOrder = (String)bizKeyOrderMap.get(rowKey);
                                if (StringUtils.isNotEmpty((String)parentBizKeyOrder)) {
                                    addRow.setValue(allColumns.indexOf(bizKeyOrder), (Object)parentBizKeyOrder);
                                }
                            } else {
                                addRow.setValue(allColumns.indexOf(bizKeyOrder), (Object)UUIDUtils.getKey());
                            }
                            if (floatTableGatherSetting == null || (reservedRows = floatTableGatherSetting.getReservedRows()) == null || reservedRows >= floatNumber) continue;
                            continue block12;
                        }
                    }
                    updatableDataSet.commitChanges(context);
                }
                if (this.memTableLoader != null && !CollectionUtils.isEmpty(pIdValuesByLevelAndRemove = gatherEntityValue.getPIdValuesByLevelAndRemove(level, notGatherEntityValue, gatherTableDefine.getFormId()))) {
                    refreshValueSet.setValue(dimensionName, pIdValuesByLevelAndRemove);
                    refreshValueSet.setValue("DATATIME", (Object)periodCode);
                    this.memTableLoader.reloadTableData(gatherTableDefine.getTableDefine().getCode(), refreshValueSet);
                }
            }
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    private List<SortInfo> getSortInfo(List<ColumnModelDefine> allColumns, Map<String, ColumnModelDefine> fieldNameAndDefine, List<ColumnModelDefine> dimColumnModelDefines, FloatTableGatherSetting setting) {
        ArrayList<SortInfo> sortInfos = new ArrayList<SortInfo>();
        if (setting != null) {
            List<OrderFieldInfo> orderInfos = setting.getOrderFields();
            for (OrderFieldInfo orderInfo : orderInfos) {
                ColumnModelDefine columnModelDefine = fieldNameAndDefine.get(this.queryFieldName(this.runtimeDataSchemeService.getDataField(orderInfo.getFieldKey())));
                int i = allColumns.indexOf(columnModelDefine);
                SortInfo sortInfo = new SortInfo();
                sortInfo.setIndex(i);
                sortInfo.setDesc(orderInfo.getOrderType().equals((Object)OrderType.DESC));
                sortInfos.add(sortInfo);
            }
        }
        if (CollectionUtils.isEmpty(sortInfos)) {
            for (ColumnModelDefine columnModelDefine : dimColumnModelDefines) {
                SortInfo sortInfo = new SortInfo();
                sortInfo.setIndex(allColumns.indexOf(columnModelDefine));
                sortInfo.setDesc(false);
                sortInfos.add(sortInfo);
            }
        }
        return sortInfos;
    }

    private Map<String, Boolean> getOrderColumn(List<DataField> orderFields, FloatTableGatherSetting setting) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        if (setting != null) {
            List<OrderFieldInfo> orderInfos = setting.getOrderFields();
            for (OrderFieldInfo orderInfo : orderInfos) {
                result.put(this.queryFieldName(this.runtimeDataSchemeService.getDataField(orderInfo.getFieldKey())), orderInfo.getOrderType().equals((Object)OrderType.DESC));
            }
        }
        if (CollectionUtils.isEmpty(result)) {
            for (DataField dataField : orderFields) {
                result.put(this.queryFieldName(dataField), false);
            }
        }
        return result;
    }

    private Map<String, LinkedHashMap<String, String>> getFileInfo(DimensionValueSet dimensionValueSet, String periodCode, String formSchemeKey, ColumnModelDefine dwColumnModelDefine, ColumnModelDefine bizKeyOrder, INvwaDataSet gatherDataSet, List<DataField> collect, Map<String, ColumnModelDefine> fieldNameAndDefine) {
        ArrayList<TempConvertObject> groupKeyInfo = new ArrayList<TempConvertObject>();
        for (int i = 0; i < gatherDataSet.size(); ++i) {
            INvwaDataRow item = gatherDataSet.getRow(i);
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            for (DataField dataField : collect) {
                Object groupKey = item.getValue(fieldNameAndDefine.get(this.queryFieldName(dataField)));
                if (groupKey == null) continue;
                map.put(dataField.getKey(), groupKey.toString());
            }
            if (CollectionUtils.isEmpty(map)) continue;
            FieldAndGroupKeyInfo info = new FieldAndGroupKeyInfo();
            info.setBizKey(item.getValue(bizKeyOrder).toString());
            info.setFieldGroupMap(map);
            TempConvertObject temp = new TempConvertObject();
            temp.setUintKey(item.getValue(dwColumnModelDefine).toString());
            temp.setPeriodCode(periodCode);
            temp.setFieldAndGroupKeyInfo(info);
            groupKeyInfo.add(temp);
        }
        if (CollectionUtils.isEmpty(groupKeyInfo)) {
            return new HashMap<String, LinkedHashMap<String, String>>();
        }
        List<FileSumInfo> sumInfos = this.getFileSumInfos(dimensionValueSet, periodCode, groupKeyInfo);
        FileSumContext fileSumContext = new FileSumContext();
        fileSumContext.setTaskKey(this.taskKey);
        fileSumContext.setFormSchemeKey(formSchemeKey);
        fileSumContext.setFileSumInfos(sumInfos);
        List<FileSumInfo> sumInfos1 = this.fileCalculateService.sumFileGroup(fileSumContext);
        ArrayList res = new ArrayList();
        sumInfos1.forEach(a -> res.addAll(a.getFieldAndGroupKeyInfos()));
        return res.stream().collect(Collectors.toMap(FieldAndGroupKeyInfo::getBizKey, FieldAndGroupKeyInfo::getFieldGroupMap));
    }

    private GatherRow getGatherRow(List<ColumnModelDefine> allColumns, List<ColumnModelDefine> dimsColumns, Set<ColumnModelDefine> attachmentSet, Map<String, GatherRow> gatherRows, String rowKey, List<SortInfo> sortInfo, FloatTableGatherSetting floatTableGatherSetting) {
        if (!gatherRows.containsKey(rowKey)) {
            IGatherColumn[] gatherColumns = new IGatherColumn[allColumns.size()];
            for (int i = 0; i < allColumns.size(); ++i) {
                ColumnModelDefine columnModelDefine = allColumns.get(i);
                int statKind = 0;
                if (columnModelDefine != null && !attachmentSet.contains(columnModelDefine)) {
                    ColumnModelType columnType = columnModelDefine.getColumnType();
                    AggrType aggrType = columnModelDefine.getAggrType();
                    if (floatTableGatherSetting != null && floatTableGatherSetting.isAllNumFieldsSum() && AggrType.NONE == aggrType && (columnType.equals((Object)ColumnModelType.INTEGER) || columnType.equals((Object)ColumnModelType.BIGDECIMAL))) {
                        statKind = 1;
                    } else {
                        if (AggrType.NONE == aggrType && !dimsColumns.contains(columnModelDefine)) {
                            aggrType = null;
                        }
                        statKind = AggrTypeConvert.gatherTypeToStatKind(aggrType);
                        if (columnModelDefine.getColumnType() == ColumnModelType.STRING && aggrType == AggrType.COUNT) {
                            statKind = 249;
                        }
                    }
                }
                gatherColumns[i] = new StatGatherColumn(statKind, DataTypesConvert.fieldTypeToDataType((ColumnModelType)columnModelDefine.getColumnType()));
            }
            GatherRow gatherRow = new GatherRow(gatherColumns);
            gatherRow.setSorts(sortInfo);
            gatherRows.put(rowKey, gatherRow);
        }
        return gatherRows.get(rowKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeGatherTableByOrderField(GatherDataTable gatherDataTable, GatherEntityValue gatherEntityValue, NotGatherEntityValue notGatherEntityValue, String gatherTempTable, String noGatherTable, Integer maxLevel, boolean isMinus, String periodCode, boolean listGather, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField, String dwDimensionName, Map<Integer, Map<String, String>> bizKeyOrderMappings) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        ITempTable bizKeyOrderTmp = null;
        try {
            if (!listGather && gatherTableDefine.getTableDefine().isRepeatCode() && !isMinus) {
                String getBizKeyOrderInfoSql = this.buildGetParentInfoSql(gatherDataTable, gatherTempTable, periodCode, sourceDimensions);
                bizKeyOrderTmp = this.getBizKeyOrderTempTable(gatherTableDefine);
                List<Object[]> bizKeyOrderInfo = this.getBizKeyOrderInfo(bizKeyOrderTmp, getBizKeyOrderInfoSql);
                bizKeyOrderTmp.insertRecords(bizKeyOrderInfo);
            }
            this.doClearDataBeforeFloatGather(gatherDataTable, gatherTempTable, noGatherTable, periodCode, targetDimensions);
            Integer level = maxLevel;
            while (level >= 1) {
                ITempTable tempTable = null;
                ITempTable fileGatherTmp = null;
                try {
                    List<DataField> collect;
                    if (listGather && !isMinus && !CollectionUtils.isEmpty(collect = gatherTableDefine.getGatherFields().stream().filter(e -> e.getDataFieldType() == DataFieldType.PICTURE || e.getDataFieldType() == DataFieldType.FILE).collect(Collectors.toList()))) {
                        String getGroupKeySql = this.buildGetGroupKeySql(gatherDataTable, gatherTempTable, noGatherTable, periodCode, level, sourceDimensions, collect);
                        List<TempConvertObject> groupKeyInfo = this.getGroupKeyInfo(periodCode, gatherTableDefine, collect, getGroupKeySql);
                        List<FileSumInfo> sumInfos = this.getFileSumInfos(sourceDimensions, periodCode, groupKeyInfo);
                        FileSumContext fileSumContext = new FileSumContext();
                        fileSumContext.setTaskKey(this.taskKey);
                        fileSumContext.setFileSumInfos(sumInfos);
                        fileSumContext.setFormSchemeKey(this.formSchemeKey);
                        fileSumContext.setGatherDataTable(gatherDataTable);
                        List<FileSumInfo> sumInfos1 = this.fileCalculateService.sumFileGroup(fileSumContext);
                        fileGatherTmp = this.getFloatGatherTempTable(gatherTableDefine, collect);
                        List<Object[]> objects = GatherTempTableUtils.fileGatherBuildTempValues(sumInfos1, fileGatherTmp, collect);
                        fileGatherTmp.insertRecords(objects);
                    }
                    String sourceSql = fileGatherTmp != null ? this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTable, periodCode, false, true, (int)level, sourceDimensions, targetDimensions, true, fileGatherTmp) : this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTable, periodCode, isMinus, listGather, (int)level, sourceDimensions, targetDimensions, versionField, true);
                    if (bizKeyOrderTmp != null) {
                        sourceSql = this.joinBizKeyOrderTmp(gatherDataTable, bizKeyOrderTmp, sourceSql);
                    }
                    if (!gatherTableDefine.isFixed() && listGather && gatherTableDefine.getTableDefine().getSyncError().booleanValue() && !isMinus) {
                        HashMap<String, String> bizKeyMap = new HashMap<String, String>();
                        tempTable = GatherAssistantTable.getErrorListGatherTempTable();
                        if (tempTable == null) {
                            throw new DataGatherExecption("\u6ca1\u6709\u83b7\u53d6\u5230bizKeyOrder\u6620\u5c04\u4e34\u65f6\u8868\uff01");
                        }
                        String errorItemTemp = tempTable.getTableName();
                        try (PreparedStatement prep = this.connection.prepareStatement(sourceSql);
                             ResultSet resultSet = prep.executeQuery();){
                            while (resultSet.next()) {
                                String bizKey = resultSet.getString(this.queryFieldName(gatherTableDefine.getBizOrderField()));
                                if (!StringUtils.isNotEmpty((String)bizKey)) continue;
                                bizKeyMap.put(bizKey, UUIDUtils.getKey());
                            }
                        }
                        catch (Exception e2) {
                            logger.error(e2.getMessage(), e2);
                            throw e2;
                        }
                        tempTable.insertRecords(GatherAssistantTable.buildErrorItemListGahterTempValues(bizKeyMap, this.executionId));
                        Map<String, String> bizKeyOrderMap = bizKeyOrderMappings.get(level);
                        if (CollectionUtils.isEmpty(bizKeyOrderMap)) {
                            bizKeyOrderMappings.put(level, bizKeyMap);
                        } else {
                            bizKeyOrderMap.putAll(bizKeyMap);
                        }
                        sourceSql = this.buildGroupSqlForChildren2ErrorList(gatherDataTable, gatherTempTable, noGatherTable, periodCode, false, true, level, sourceDimensions, targetDimensions, versionField, true, tempTable, fileGatherTmp);
                    }
                    this.printLoggerSQL(sourceSql, null, "\u6d6e\u52a8\u8868\u6c47\u603b\u67e5\u8be2", gatherDataTable.getTableModelDefine().getName());
                    this.doFloatTableGather(gatherDataTable, sourceSql);
                }
                finally {
                    GatherAssistantTable.releaseTempTable(tempTable);
                    GatherTempTableUtils.releaseTempTable(fileGatherTmp);
                }
                if (this.memTableLoader != null) {
                    DimensionValueSet valueSet = new DimensionValueSet(sourceDimensions);
                    List<String> pIdValuesByLevelAndRemove = gatherEntityValue.getPIdValuesByLevelAndRemove(level, notGatherEntityValue, gatherTableDefine.getFormId());
                    if (!CollectionUtils.isEmpty(pIdValuesByLevelAndRemove)) {
                        valueSet.setValue(dwDimensionName, pIdValuesByLevelAndRemove);
                        valueSet.setValue("DATATIME", (Object)periodCode);
                        this.memTableLoader.reloadTableData(gatherTableDefine.getTableDefine().getCode(), valueSet);
                    }
                }
                Integer n = level;
                Integer n2 = level = Integer.valueOf(level - 1);
            }
        }
        finally {
            GatherTempTableUtils.releaseTempTable(bizKeyOrderTmp);
        }
    }

    protected void doClearDataBeforeFloatGather(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, DimensionValueSet targetDimensions) throws SQLException {
        if (!this.containTargetKey) {
            String deleteSql = this.buildClearSqlsForFloatTable(gatherDataTable, gatherTempTable, noGatherTable, periodCode, targetDimensions);
            try (Statement statement = null;){
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                this.printLoggerSQL(deleteSql, null, "\u6d6e\u52a8\u6c47\u603b\u524d\u7f6e", gatherDataTable.getTableModelDefine().getName());
                statement = this.connection.prepareStatement(deleteSql);
                statement.execute();
                stopWatch.stop();
                logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
            }
        }
    }

    protected void doFloatTableGather(GatherDataTable gatherDataTable, String sourceSql) throws SQLException, TableLoaderException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(this.connection);
        ITableLoader tableLoader = this.createInsertAutoRownumLoader(database, this.connection, this.queryFieldName(gatherTableDefine.getOrderField()));
        tableLoader.setListener((ILoadListener)new LoggingTableLoadListener());
        SimpleTable desTable = new SimpleTable(this.getRealTableName(gatherDataTable), "T2");
        ArrayList<ISQLField> desFields = new ArrayList<ISQLField>();
        ArrayList<ISQLField> srcFields = new ArrayList<ISQLField>();
        InnerTable srcTable = new InnerTable(sourceSql, "T1");
        desFields.add(desTable.addField(this.queryFieldName(gatherTableDefine.getUnitField())));
        srcFields.add(srcTable.addField(this.queryFieldName(gatherTableDefine.getUnitField())));
        if (gatherTableDefine.getPeriodField() != null) {
            desFields.add(desTable.addField(this.queryFieldName(gatherTableDefine.getPeriodField())));
            srcFields.add(srcTable.addField(this.queryFieldName(gatherTableDefine.getPeriodField())));
        }
        if (gatherTableDefine.getBizOrderField() != null) {
            desFields.add(desTable.addField(this.queryFieldName(gatherTableDefine.getBizOrderField())));
            srcFields.add(srcTable.addField(this.queryFieldName(gatherTableDefine.getBizOrderField())));
        }
        if (gatherTableDefine.getClassifyFields() != null) {
            for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                desFields.add(desTable.addField(this.queryFieldName(classifyField)));
                srcFields.add(srcTable.addField(this.queryFieldName(classifyField)));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                desFields.add(desTable.addField(this.queryFieldName(unClassifyField)));
                srcFields.add(srcTable.addField(this.queryFieldName(unClassifyField)));
            }
        }
        for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
            desFields.add(desTable.addField(this.queryFieldName(gatherField)));
            srcFields.add(srcTable.addField(this.queryFieldName(gatherField)));
        }
        tableLoader.setDestTable(desTable);
        tableLoader.setSourceTable((ISQLTable)srcTable);
        List fieldMaps = tableLoader.getFieldMaps();
        for (int index = 0; index < desFields.size(); ++index) {
            fieldMaps.add(new LoadFieldMap((ISQLField)srcFields.get(index), (ISQLField)desFields.get(index)));
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        tableLoader.execute();
        stopWatch.stop();
        logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
    }

    private String joinBizKeyOrderTmp(GatherDataTable gatherDataTable, ITempTable bizKeyOrderTmp, String sourceSql) throws SQLException {
        StringBuilder FloatGatherOrder;
        List<DataField> classifyFields;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlBuilder = new StringBuilder();
        String leftTableAlias = "t1";
        String rightTableAlias = "t2";
        DataField periodField = gatherTableDefine.getPeriodField();
        DataField bizOrderField = gatherTableDefine.getBizOrderField();
        sqlBuilder.append(" select ");
        sqlBuilder.append(String.format(" %s.%s,", "t1", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            sqlBuilder.append(String.format(" %s.%s,", "t1", this.queryFieldName(periodField)));
        }
        if (bizOrderField != null) {
            String nvlSql = this.buildNvlSql();
            String fieldName = this.queryFieldName(bizOrderField);
            sqlBuilder.append(String.format("%s(%s.%s,%s.%s) as %s,", nvlSql, "t2", bizKeyOrderTmp.getRealColName(fieldName), "t1", fieldName, fieldName));
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null && classifyFields.size() > 0) {
            for (DataField dataField : classifyFields) {
                sqlBuilder.append(String.format("%s.%s,", "t1", this.queryFieldName(dataField)));
            }
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null && unClassifyFields.size() > 0) {
            for (DataField unClassifyField : unClassifyFields) {
                sqlBuilder.append(String.format("%s.%s,", "t1", this.queryFieldName(unClassifyField)));
            }
        }
        for (DataField dataField : gatherTableDefine.getGatherFields()) {
            sqlBuilder.append(String.format("%s.%s,", "t1", this.queryFieldName(dataField)));
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" from ( ").append(sourceSql).append(" ) ").append("t1");
        sqlBuilder.append(" left join ").append(bizKeyOrderTmp.getTableName()).append(" t2");
        sqlBuilder.append(" on ");
        String string = this.buildOnConditional(gatherTableDefine, bizKeyOrderTmp);
        sqlBuilder.append(string);
        sqlBuilder.append(" order by ");
        sqlBuilder.append(String.format(" %s,", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            sqlBuilder.append(String.format(" %s,", this.queryFieldName(periodField)));
        }
        StringBuilder orderSql = new StringBuilder();
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
        if (classifyFields != null && classifyFields.size() > 0) {
            for (DataField classifyField : classifyFields) {
                String dimName;
                String string2 = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                if (!StringUtils.isEmpty((String)dimName) && this.gatherSingleDims.containsKey(dimName)) continue;
                orderSql.append(String.format("%s,", this.queryFieldName(classifyField)));
            }
        }
        if (!gatherTableDefine.isFixed() && unClassifyFields != null) {
            for (DataField unClassifyField : unClassifyFields) {
                orderSql.append(String.format("%s,", this.queryFieldName(unClassifyField)));
            }
        }
        if (!(FloatGatherOrder = this.getOrderSql(gatherTableDefine)).toString().isEmpty()) {
            orderSql = FloatGatherOrder;
        }
        sqlBuilder.append((CharSequence)orderSql);
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        return sqlBuilder.toString();
    }

    private String buildOnConditional(GatherTableDefine gatherTableDefine, ITempTable bizKeyOrderTmp) {
        StringBuilder onSql = new StringBuilder();
        ArrayList<DataField> columns = new ArrayList<DataField>();
        columns.add(gatherTableDefine.getUnitField());
        columns.add(gatherTableDefine.getPeriodField());
        columns.addAll(gatherTableDefine.getClassifyFields());
        columns.addAll(gatherTableDefine.getUnClassifyFields());
        for (DataField dataField : columns) {
            String columnName = this.queryFieldName(dataField);
            String realColName = bizKeyOrderTmp.getRealColName(dataField.getCode());
            if (dataField.isNullable()) {
                if (dataField.getDataFieldType() == DataFieldType.STRING) {
                    onSql.append(String.format(" (%s.%s=%s.%s or %s.%s is null and %s.%s = '' or %s.%s is null and %s.%s = '' or %s.%s is null and %s.%s is null) and", "t1", columnName, "t2", realColName, "t1", columnName, "t2", realColName, "t2", realColName, "t1", columnName, "t1", columnName, "t2", realColName));
                }
                if (dataField.getDataFieldType() != DataFieldType.DATE) continue;
                onSql.append(String.format(" (%s.%s=%s.%s or %s.%s is null and %s.%s is null) and", "t1", columnName, "t2", realColName, "t1", columnName, "t2", realColName));
                continue;
            }
            onSql.append(String.format(" %s.%s=%s.%s and", "t1", columnName, "t2", realColName));
        }
        onSql.setLength(onSql.length() - 3);
        return onSql.toString();
    }

    private List<FileSumInfo> getFileSumInfos(DimensionValueSet sourceDimensions, String periodCode, List<TempConvertObject> groupKeyInfo) {
        Map<String, List<TempConvertObject>> collect1 = groupKeyInfo.stream().collect(Collectors.groupingBy(TempConvertObject::getUintKey));
        Set<String> keys = collect1.keySet();
        IRunTimeViewController runtimeController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskDefine taskDefine = runtimeController.queryTaskDefine(this.taskKey);
        String entityId = taskDefine.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        String entityDimension = entityMetaService.getDimensionName(entityId);
        PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        ArrayList<FileSumInfo> sumInfos = new ArrayList<FileSumInfo>();
        keys.forEach(key -> {
            DimensionValueSet dimensionValueSet = new DimensionValueSet(sourceDimensions);
            dimensionValueSet.setValue(entityDimension, key);
            dimensionValueSet.setValue(periodDimension, (Object)periodCode);
            FileSumInfo fileSumInfo = new FileSumInfo();
            fileSumInfo.setFromDims(dimensionValueSet);
            ArrayList<FieldAndGroupKeyInfo> groupKeyInfos = new ArrayList<FieldAndGroupKeyInfo>();
            List tempConvertObjects = (List)collect1.get(key);
            tempConvertObjects.forEach(a -> groupKeyInfos.add(a.getFieldAndGroupKeyInfo()));
            fileSumInfo.setFieldAndGroupKeyInfos(groupKeyInfos);
            sumInfos.add(fileSumInfo);
        });
        return sumInfos;
    }

    /*
     * Exception decompiling
     */
    private List<TempConvertObject> getFixGroupKeyInfo(String periodCode, GatherTableDefine gatherTableDefine, List<DataField> collect, String getGroupKeySql) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private List<TempConvertObject> getGroupKeyInfo(String periodCode, GatherTableDefine gatherTableDefine, List<DataField> collect, String getGroupKeySql) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private List<Object[]> getBizKeyOrderInfo(ITempTable tempTable, String getBizKeyOrderInfoSql) throws SQLException {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        List columns = tempTable.getMeta().getLogicFields();
        try (PreparedStatement prep = this.connection.prepareStatement(getBizKeyOrderInfoSql);
             ResultSet resultSet = prep.executeQuery();){
            while (resultSet.next()) {
                Object[] rowData = new Object[columns.size()];
                for (int i = 0; i < columns.size(); ++i) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                batchValues.add(rowData);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return batchValues;
    }

    private ITempTable getFixFileGatherTempTable(GatherTableDefine gatherTableDefine, List<DataField> fileAndPic) {
        GatherTempTable tmpTable = new GatherTempTable();
        ArrayList<DataField> columns = new ArrayList<DataField>();
        columns.add(gatherTableDefine.getUnitField());
        tmpTable.setLogicFields(GatherTempTableUtils.ConvertNotNullFields(columns));
        tmpTable.getLogicFields().addAll(GatherTempTableUtils.ConvertFields(fileAndPic));
        ArrayList<String> pks = new ArrayList<String>();
        pks.add(gatherTableDefine.getUnitField().getCode());
        tmpTable.setPrimaryKeyFields(pks);
        return GatherTempTableUtils.getTempTable(this.connection, tmpTable);
    }

    private ITempTable getFloatGatherTempTable(GatherTableDefine gatherTableDefine, List<DataField> fileAndPic) {
        GatherTempTable tmpTable = new GatherTempTable();
        ArrayList<DataField> columns = new ArrayList<DataField>();
        columns.add(gatherTableDefine.getBizOrderField());
        tmpTable.setLogicFields(GatherTempTableUtils.ConvertNotNullFields(columns));
        tmpTable.getLogicFields().addAll(GatherTempTableUtils.ConvertFields(fileAndPic));
        ArrayList<String> pks = new ArrayList<String>();
        pks.add(gatherTableDefine.getBizOrderField().getCode());
        tmpTable.setPrimaryKeyFields(pks);
        return GatherTempTableUtils.getTempTable(this.connection, tmpTable);
    }

    private ITempTable getBizKeyOrderTempTable(GatherTableDefine gatherTableDefine) {
        GatherTempTable tmpTable = new GatherTempTable();
        ArrayList<DataField> columns = new ArrayList<DataField>();
        columns.add(gatherTableDefine.getUnitField());
        columns.add(gatherTableDefine.getPeriodField());
        columns.add(gatherTableDefine.getBizOrderField());
        columns.addAll(gatherTableDefine.getClassifyFields());
        tmpTable.setLogicFields(GatherTempTableUtils.ConvertNotNullFields(columns));
        tmpTable.getLogicFields().addAll(GatherTempTableUtils.ConvertFields(gatherTableDefine.getUnClassifyFields()));
        ArrayList<String> pks = new ArrayList<String>();
        pks.add(gatherTableDefine.getUnitField().getCode());
        pks.add(gatherTableDefine.getPeriodField().getCode());
        pks.add(gatherTableDefine.getBizOrderField().getCode());
        pks.addAll(gatherTableDefine.getClassifyFields().stream().map(Basic::getCode).collect(Collectors.toList()));
        tmpTable.setPrimaryKeyFields(pks);
        return GatherTempTableUtils.getTempTable(this.connection, tmpTable);
    }

    public ITableLoader createInsertAutoRownumLoader(IDatabase database, Connection conn, String rownumFieldName) throws TableLoaderException {
        if (rownumFieldName == null) {
            return database.createInsertLoader(conn);
        }
        return new DefaultInsertAutoRownumLoader(conn, database, rownumFieldName);
    }

    protected String buildClearSqlsForFloatTable(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, DimensionValueSet targetDimensions) {
        String filterSql;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder deleteSql = new StringBuilder();
        String filterCondition = gatherTableDefine.getFilterCondition();
        String tableName = this.getRealTableName(gatherDataTable);
        deleteSql.append("delete from ").append(tableName);
        deleteSql.append(" where ");
        if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
            deleteSql.append(this.queryFieldName(gatherTableDefine.getPeriodField())).append("='").append(periodCode).append("'").append(" and ");
        }
        if (targetDimensions != null && targetDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, targetDimensions);
            this.buildWhereDimensions(deleteSql, dimFields, targetDimensions, false, "", null);
        }
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName, tableName)))) {
            deleteSql.append(" (").append(filterSql).append(") and ");
        }
        deleteSql.append(" exists (select 1 from ").append(gatherTempTable).append(" gt");
        deleteSql.append(" where ").append(this.queryFieldName(gatherTableDefine.getUnitField())).append(" = gt.").append("GT_PID");
        this.appendGatherDimSQL(deleteSql, true, gatherDataTable, "gt.");
        this.appendExecutionSql(deleteSql, true, true, null, "gt");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, deleteSql, true, true, null, "gt");
        deleteSql.append(")");
        return deleteSql.toString();
    }

    private String buildFloatTableSqlFormat(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, boolean isMinus, boolean listGather, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, String versionField) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder resultSql = new StringBuilder();
        resultSql.append("insert into ");
        resultSql.append(this.getRealTableName(gatherDataTable));
        resultSql.append(" (");
        resultSql.append(this.queryFieldName(gatherTableDefine.getUnitField())).append(",");
        if (gatherTableDefine.getPeriodField() != null) {
            resultSql.append(this.queryFieldName(gatherTableDefine.getPeriodField())).append(",");
        }
        if (gatherTableDefine.getBizOrderField() != null) {
            resultSql.append(this.queryFieldName(gatherTableDefine.getBizOrderField())).append(",");
        }
        if (gatherTableDefine.getOrderField() != null) {
            resultSql.append(this.queryFieldName(gatherTableDefine.getOrderField())).append(",");
        }
        if (gatherTableDefine.getClassifyFields() != null) {
            for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                resultSql.append(this.queryFieldName(classifyField)).append(",");
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                resultSql.append(this.queryFieldName(unClassifyField)).append(",");
            }
        }
        for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
            resultSql.append(this.queryFieldName(gatherField)).append(",");
        }
        resultSql.setLength(resultSql.length() - 1);
        resultSql.append(") ");
        resultSql.append(this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTable, periodCode, isMinus, listGather, level, sourceDimensions, targetDimensions, versionField, true));
        return resultSql.toString();
    }

    protected String buildGroupSqlForChildren(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, boolean isMinus, boolean listGather, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, String versionField, boolean exeHardParse) throws SQLException {
        return this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTable, periodCode, isMinus, listGather, level, sourceDimensions, targetDimensions, exeHardParse, null);
    }

    protected String buildGroupSqlForChildren(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, boolean isMinus, boolean listGather, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, boolean exeHardParse, ITempTable fileGatherTmp) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlBuilder = this.getSelectDimSql(gatherDataTable, isMinus, listGather, targetDimensions);
        String tableName = this.getRealTableName(gatherDataTable);
        FloatTableGatherSetting floatTableGatherSetting = gatherTableDefine.getFloatTableGatherSetting();
        boolean isAllNumFieldsSum = false;
        if (floatTableGatherSetting != null) {
            isAllNumFieldsSum = floatTableGatherSetting.isAllNumFieldsSum();
        }
        sqlBuilder.append(this.buildGatherFieldsSql(gatherDataTable.getAllColumns(), "c1", !listGather, isMinus, String.format("%s.%s", "c2", "GT_ISMINUS"), isAllNumFieldsSum, fileGatherTmp));
        this.addFromSql(gatherTempTable, periodCode, sqlBuilder, tableName, gatherTableDefine);
        if (fileGatherTmp != null) {
            sqlBuilder.append(" left join ").append(String.format("%s %s", fileGatherTmp.getTableName(), "c4"));
            if (!gatherTableDefine.isFixed()) {
                sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getBizOrderField()), "c4", fileGatherTmp.getRealColName(gatherTableDefine.getBizOrderField().getCode())));
            } else {
                sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c2", "GT_PID", "c4", fileGatherTmp.getRealColName(gatherTableDefine.getUnitField().getCode())));
            }
        }
        this.addWhereSql(gatherDataTable, sqlBuilder, level, periodCode, noGatherTable, exeHardParse, sourceDimensions);
        if (!listGather || isMinus) {
            this.addGroupBySql(gatherDataTable, sqlBuilder, targetDimensions);
            StringBuilder floatGatherOrder = this.getOrderSql(gatherTableDefine);
            boolean needOrder = this.checkNeedOrder(gatherTableDefine);
            if (needOrder) {
                if (!floatGatherOrder.toString().isEmpty()) {
                    logger.debug("\u6d6e\u52a8\u8868\u6c47\u603b\u8bbe\u7f6e\u6392\u5e8f\u5b57\u6bb5\u53c2\u6570");
                    floatGatherOrder.setLength(floatGatherOrder.length() - 1);
                    sqlBuilder.append(" order by ").append((CharSequence)floatGatherOrder);
                } else {
                    sqlBuilder.append(" order by ");
                    this.addOrderBySql(gatherDataTable, sqlBuilder);
                }
            }
            if (floatTableGatherSetting != null) {
                Integer reservedRows = floatTableGatherSetting.getReservedRows();
                if (!gatherTableDefine.isFixed() && reservedRows != null) {
                    logger.debug("\u6d6e\u52a8\u8868\u6c47\u603b\u8bbe\u7f6e\u4fdd\u7559\u884c\u6570\u53c2\u6570");
                    sqlBuilder = this.appendLimitSql(sqlBuilder, reservedRows);
                }
            }
        } else {
            sqlBuilder.append(" order by ");
            StringBuilder orderSql = this.listGatherAddOrderBySql(gatherDataTable);
            StringBuilder floatGatherOrder = this.getOrderSql(gatherTableDefine);
            if (!floatGatherOrder.toString().isEmpty()) {
                orderSql = floatGatherOrder;
                sqlBuilder.append((CharSequence)orderSql);
                sqlBuilder.setLength(sqlBuilder.length() - 1);
            } else {
                sqlBuilder.append((CharSequence)orderSql);
            }
            if (floatTableGatherSetting != null) {
                Integer reservedRows = floatTableGatherSetting.getReservedRows();
                if (!gatherTableDefine.isFixed() && reservedRows != null) {
                    sqlBuilder = this.appendLimitSql(sqlBuilder, reservedRows);
                }
            }
        }
        return sqlBuilder.toString();
    }

    protected StringBuilder getSelectDimSql(GatherDataTable gatherDataTable, boolean isMinus, boolean listGather, DimensionValueSet targetDimensions) throws SQLException {
        StringBuilder sqlBuilder;
        block19: {
            List<DataField> classifyFields;
            DataField bizOrderField;
            GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
            sqlBuilder = new StringBuilder();
            sqlBuilder.append(" select ");
            sqlBuilder.append(String.format("%s.%s as %s,", "c2", "GT_PID", this.queryFieldName(gatherTableDefine.getUnitField())));
            DataField periodField = gatherTableDefine.getPeriodField();
            if (periodField != null) {
                sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(periodField)));
            }
            if ((bizOrderField = gatherTableDefine.getBizOrderField()) != null) {
                if (!gatherTableDefine.isFixed() && listGather && gatherTableDefine.getTableDefine().getSyncError().booleanValue() && !isMinus) {
                    sqlBuilder.append(String.format("%s.%s as %s,", "c1", this.queryFieldName(bizOrderField), this.queryFieldName(bizOrderField)));
                } else {
                    sqlBuilder.append(String.format("%s as %s,", this.buildGuidSql(), this.queryFieldName(bizOrderField)));
                }
            }
            if ((classifyFields = gatherTableDefine.getClassifyFields()) != null) {
                if (targetDimensions != null && targetDimensions.size() > 0) {
                    TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                    ArrayList<String> keysList = new ArrayList<String>(this.gatherSingleDims.keySet());
                    for (DataField classifyField : classifyFields) {
                        String dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                        int index = keysList.indexOf(dimName);
                        if (index >= 0) {
                            LogicField field = GatherTempTableHandler.getDimCols().get(index);
                            if (listGather && !isMinus) {
                                sqlBuilder.append(String.format("%s.%s as %s,", "c2", field.getFieldName(), this.queryFieldName(classifyField)));
                                continue;
                            }
                            sqlBuilder.append(String.format("min(%s.%s) as %s,", "c2", field.getFieldName(), this.queryFieldName(classifyField)));
                            continue;
                        }
                        if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) {
                            Object dimValue = targetDimensions.getValue(dimName);
                            if (dimValue instanceof List) {
                                sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                                continue;
                            }
                            if (classifyField.getType() == FieldType.FIELD_TYPE_UUID) {
                                sqlBuilder.append(String.format("%s as %s,", DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimName).toString())), this.queryFieldName(classifyField)));
                                continue;
                            }
                            String sql = this.database.isDatabase("POSTGRESQL") ? "'%s'::text as %s," : "'%s' as %s,";
                            sqlBuilder.append(String.format(sql, targetDimensions.getValue(dimName).toString(), this.queryFieldName(classifyField)));
                            continue;
                        }
                        sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                    }
                } else {
                    for (DataField classifyField : classifyFields) {
                        sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                    }
                }
            }
            List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
            if (gatherTableDefine.isFixed() || unClassifyFields == null) break block19;
            if (this.isNodeCheck) {
                String nvlSql = this.buildNvlSql();
                for (DataField unClassifyField : unClassifyFields) {
                    DataFieldType dataFieldType = unClassifyField.getDataFieldType();
                    if (dataFieldType == DataFieldType.DATE || dataFieldType == DataFieldType.DATE_TIME) {
                        sqlBuilder.append(String.format(" %s(%s.%s, %s) as %s,", nvlSql, "c1", this.queryFieldName(unClassifyField), this.buildDateSql("2024-01-01"), this.queryFieldName(unClassifyField)));
                        continue;
                    }
                    sqlBuilder.append(String.format(" %s(%s.%s, '%s') as %s,", nvlSql, "c1", this.queryFieldName(unClassifyField), EMPTY, this.queryFieldName(unClassifyField)));
                }
            } else {
                for (DataField unClassifyField : unClassifyFields) {
                    sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
                }
            }
        }
        return sqlBuilder;
    }

    protected void addFromSql(String gatherTempTable, String periodCode, StringBuilder sqlBuilder, String tableName, GatherTableDefine gatherTableDefine) {
        sqlBuilder.append(" from ");
        sqlBuilder.append(String.format("%s %s", tableName, "c1"));
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlBuilder, gatherTableDefine, periodCode);
        }
        sqlBuilder.append(" left join ").append(String.format("%s %s", gatherTempTable, "c2"));
        sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_ID"));
    }

    protected void addWhereSql(GatherDataTable gatherDataTable, StringBuilder sqlBuilder, int level, String periodCode, String noGatherTable, boolean exeHardParse, DimensionValueSet sourceDimensions) {
        String tableName;
        String filterSql;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        if (level < 0) {
            sqlBuilder.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        } else {
            sqlBuilder.append(" where ").append(String.format("%s.%s=%s", "c2", "GT_LEVEL", level));
        }
        this.appendExecutionSql(sqlBuilder, true, exeHardParse, null, "c2");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, sqlBuilder, true, exeHardParse, null, "c2");
        DataField periodField = gatherTableDefine.getPeriodField();
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            if (exeHardParse) {
                sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
            } else {
                sqlBuilder.append(" and ").append(String.format("%s.%s=?", "c1", this.queryFieldName(periodField)));
            }
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName = this.getRealTableName(gatherDataTable), "c1")))) {
            sqlBuilder.append(" and (").append(filterSql).append(")");
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            this.buildWhereDimensions(sqlBuilder, dimFields, sourceDimensions, true, String.format("%s.", "c1"), null);
        }
    }

    protected void addGroupBySql(GatherDataTable gatherDataTable, StringBuilder sqlBuilder, DimensionValueSet targetDimensions) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        sqlBuilder.append(" group by ");
        sqlBuilder.append(String.format("%s.%s,", "c2", "GT_PID"));
        if (gatherTableDefine.getPeriodField() != null) {
            sqlBuilder.append(String.format("%s,", this.queryFieldName(gatherTableDefine.getPeriodField())));
        }
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        if (classifyFields != null) {
            String dimName;
            if (targetDimensions != null && targetDimensions.size() > 0) {
                for (DataField classifyField : classifyFields) {
                    String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                    if (!StringUtils.isEmpty((String)dimName) && this.gatherSingleDims.containsKey(dimName)) continue;
                    if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) {
                        Object value = targetDimensions.getValue(dimName);
                        if (!(value instanceof List)) continue;
                        sqlBuilder.append(String.format("%s,", this.queryFieldName(classifyField)));
                        continue;
                    }
                    sqlBuilder.append(String.format("%s,", this.queryFieldName(classifyField)));
                }
            } else {
                for (DataField classifyField : classifyFields) {
                    String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                    if (!StringUtils.isEmpty((String)dimName) && this.gatherSingleDims.containsKey(dimName)) continue;
                    sqlBuilder.append(String.format("%s,", this.queryFieldName(classifyField)));
                }
            }
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null) {
            for (DataField unClassifyField : unClassifyFields) {
                sqlBuilder.append(String.format("%s,", this.queryFieldName(unClassifyField)));
            }
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
    }

    protected void addOrderBySql(GatherDataTable gatherDataTable, StringBuilder sqlBuilder) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
        sqlBuilder.append(String.format("%s.%s,", "c2", "GT_PID"));
        if (gatherTableDefine.getPeriodField() != null) {
            sqlBuilder.append(String.format("%s,", this.queryFieldName(gatherTableDefine.getPeriodField())));
        }
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        for (DataField classifyField : classifyFields) {
            String dimName;
            String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
            if (!StringUtils.isEmpty((String)dimName) && this.gatherSingleDims.containsKey(dimName)) continue;
            sqlBuilder.append(String.format("%s,", this.queryFieldName(classifyField)));
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null) {
            for (DataField unClassifyField : unClassifyFields) {
                sqlBuilder.append(String.format("%s,", this.queryFieldName(unClassifyField)));
            }
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
    }

    protected StringBuilder listGatherAddOrderBySql(GatherDataTable gatherDataTable) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder orderSql = new StringBuilder();
        orderSql.append(String.format("%s.%s,", "c2", "GT_ORDER"));
        if (!gatherTableDefine.isFixed()) {
            orderSql.append(String.format("%s,", FLOATORDER));
        }
        orderSql.append(String.format("%s.%s,", "c2", "GT_ID"));
        if (gatherTableDefine.getOrderField() != null) {
            orderSql.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getOrderField())));
        }
        orderSql.setLength(orderSql.length() - 1);
        return orderSql;
    }

    private StringBuilder getOrderSql(GatherTableDefine gatherTableDefine) {
        StringBuilder floatGatherOrder = new StringBuilder();
        FloatTableGatherSetting floatTableGatherSetting = gatherTableDefine.getFloatTableGatherSetting();
        if (floatTableGatherSetting != null) {
            List<OrderFieldInfo> singleFloatSumOrderFields = floatTableGatherSetting.getOrderFields();
            if (!gatherTableDefine.isFixed() && !CollectionUtils.isEmpty(singleFloatSumOrderFields)) {
                ArrayList<DataField> gatherFields = new ArrayList<DataField>();
                gatherFields.addAll(gatherTableDefine.getGatherFields());
                gatherFields.addAll(gatherTableDefine.getUnClassifyFields());
                Map<String, DataField> fieldMap = gatherFields.stream().collect(Collectors.toMap(Basic::getKey, a -> a, (k1, k2) -> k1));
                for (OrderFieldInfo orderFieldInfo : singleFloatSumOrderFields) {
                    DataField dataField = fieldMap.get(orderFieldInfo.getFieldKey());
                    OrderType orderType = orderFieldInfo.getOrderType();
                    if (dataField == null || orderType == null) continue;
                    String order = orderType.equals((Object)OrderType.DESC) ? "desc," : "asc,";
                    floatGatherOrder.append(String.format("%s " + order, this.queryFieldName(dataField)));
                }
            }
        }
        return floatGatherOrder;
    }

    private StringBuilder appendLimitSql(StringBuilder sqlBuilder, int reservedRows) {
        try {
            IPagingSQLBuilder pagingSQLBuilder = this.database.createPagingSQLBuilder();
            pagingSQLBuilder.setRawSQL(sqlBuilder.toString());
            return new StringBuilder(pagingSQLBuilder.buildSQL(0, reservedRows));
        }
        catch (Exception e) {
            logger.error("\u5206\u9875\u5904\u7406\u5f02\u5e38", e);
            return sqlBuilder;
        }
    }

    protected void appendOrgSQL(StringBuilder sqlBuilder, GatherTableDefine gatherTableDefine, String period) {
        GatherTempTableHandler gatherTempTableHandler = (GatherTempTableHandler)BeanUtil.getBean(GatherTempTableHandler.class);
        String orgTableName = gatherTempTableHandler.getOrgTable(this.taskKey);
        if (StringUtils.isEmpty((String)orgTableName)) {
            logger.warn("\u7ec4\u7ec7\u673a\u6784\u8868\u672a\u627e\u5230\uff01");
        } else {
            orgTableName = this.subDataBaseUtil.getRealTableName(orgTableName, this.taskKey);
            sqlBuilder.append(" inner join ").append(orgTableName).append(" ").append(ORGTABLE);
            sqlBuilder.append(" on ");
            Set<String> keys = this.gatherSingleDims.keySet();
            boolean addFlag = false;
            for (String key : keys) {
                if (addFlag) {
                    sqlBuilder.append(" and ");
                }
                String attribute = this.gatherSingleDims.get(key);
                String dataFieldName = this.getDataFieldName(gatherTableDefine.getClassifyFields(), key);
                sqlBuilder.append(String.format(" %s.%s=%s.%s ", "c1", dataFieldName, ORGTABLE, attribute));
                addFlag = true;
            }
            sqlBuilder.append(String.format(" and %s.%s=%s.%s ", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), ORGTABLE, "CODE"));
            Optional<DataDimension> periodEntity = this.getDimensionEntity();
            if (periodEntity.isPresent()) {
                IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
                IPeriodProvider periodAdapter = periodEntityAdapter.getPeriodProvider(periodEntity.get().getDimKey());
                try {
                    String[] dateRange = periodAdapter.getPeriodDateStrRegion(period);
                    sqlBuilder.append(" and ").append(ORGTABLE).append(".VALIDTIME<=").append(this.buildDateSql(dateRange[1]));
                    sqlBuilder.append(" and ").append(ORGTABLE).append(".INVALIDTIME>").append(this.buildDateSql(dateRange[1])).append(" ");
                }
                catch (Exception e) {
                    logger.error("\u89e3\u6790\u65f6\u671f\u51fa\u9519\uff01", e);
                }
            }
        }
    }

    private String getDataFieldName(List<DataField> fields, String baseDataCode) {
        Map<String, List<DataField>> collect = fields.stream().filter(a -> StringUtils.isNotEmpty((String)a.getRefDataEntityKey())).collect(Collectors.groupingBy(DataField::getRefDataEntityKey));
        Set<String> strings = collect.keySet();
        for (String code : strings) {
            if (!code.contains(baseDataCode)) continue;
            return this.queryFieldName(collect.get(code).get(0));
        }
        return "";
    }

    private Optional<DataDimension> getDimensionEntity() {
        IRunTimeViewController runtimeController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskDefine taskDefine = runtimeController.queryTaskDefine(this.taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List dimesion = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.PERIOD);
        return dimesion.stream().findAny();
    }

    protected boolean checkNeedOrder(GatherTableDefine gatherTableDefine) {
        if (gatherTableDefine.isFixed()) {
            return false;
        }
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        return classifyFields != null && !classifyFields.isEmpty() || unClassifyFields != null && !unClassifyFields.isEmpty();
    }

    private boolean checkNeedOrder(GatherTableDefine gatherTableDefine, List<DataField> classifyFields, List<DataField> unClassifyFields) {
        if (gatherTableDefine.isFixed()) {
            return false;
        }
        return classifyFields != null && classifyFields.size() > 0 || unClassifyFields != null && unClassifyFields.size() > 0;
    }

    private String buildGuidSql() throws SQLException {
        return DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)true);
    }

    private String buildNvlSql() throws SQLException {
        return this.database.getDescriptor().getNVLName();
    }

    private String buildDateSql(String date) throws SQLException {
        return DataEngineUtil.buildDateValueSql((IDatabase)this.database, (Object)date, (Connection)this.connection);
    }

    protected Object buildGatherFieldsSql(List<ColumnModelDefine> gatherFields, String tableAlias, boolean isSum, boolean useMinus, String minusField) {
        return this.buildGatherFieldsSql(gatherFields, tableAlias, isSum, useMinus, minusField, false, null);
    }

    protected Object buildGatherFieldsSql(List<ColumnModelDefine> gatherFields, String tableAlias, boolean isSum, boolean useMinus, String minusField, boolean isAllNumFieldsSum, ITempTable fileGatherTmp) {
        StringBuilder resultSql = new StringBuilder();
        if (isSum || useMinus) {
            for (ColumnModelDefine fieldDefine : gatherFields) {
                boolean isAttachment = fieldDefine.getColumnType().equals((Object)ColumnModelType.ATTACHMENT);
                if (fileGatherTmp == null && !this.fileGather && isAttachment) continue;
                if (!useMinus && isAttachment && fileGatherTmp != null) {
                    String fieldName = this.queryFieldName(fieldDefine);
                    resultSql.append(String.format("min(%s.%s) as %s", "c4", fileGatherTmp.getRealColName(fieldName), fieldName)).append(",");
                    continue;
                }
                resultSql.append(this.getSumGatherFieldSql(fieldDefine, tableAlias, useMinus, minusField, isAllNumFieldsSum)).append(",");
            }
        } else {
            String preStr = StringUtils.isEmpty((String)tableAlias) ? "" : String.format("%s.", tableAlias);
            for (ColumnModelDefine fieldDefine : gatherFields) {
                String fieldName = this.queryFieldName(fieldDefine);
                if (fieldDefine.getColumnType().equals((Object)ColumnModelType.ATTACHMENT)) {
                    resultSql.append(String.format("(%s.%s) as %s", "c4", fileGatherTmp.getRealColName(fieldName), fieldName)).append(",");
                    continue;
                }
                resultSql.append(String.format("(%s%s) as %s", preStr, fieldName, fieldName)).append(",");
            }
        }
        resultSql.setLength(resultSql.length() - 1);
        return resultSql.toString();
    }

    private String getSumGatherFieldSql(ColumnModelDefine fieldDefine, String tableAlias, boolean useMinus, String minusField, boolean isAllNumFieldsSum) {
        ColumnModelType fieldType;
        String preStr = StringUtils.isEmpty((String)tableAlias) ? "" : String.format("%s.", tableAlias);
        String fieldName = this.queryFieldName(fieldDefine);
        boolean canGather = this.checkCanGather(fieldDefine);
        if (!canGather) {
            if (this.database.isDatabase("derby")) {
                return String.format("'' as %s", fieldName);
            }
            return String.format("null as %s", fieldName);
        }
        if (useMinus) {
            switch (fieldDefine.getAggrType()) {
                case AVERAGE: {
                    return String.format("Avg(%s%s*%s) as %s", preStr, fieldName, minusField, fieldName);
                }
                case COUNT: {
                    return String.format("Count(%s%s) as %s", preStr, fieldName, fieldName);
                }
                case MAX: {
                    return String.format("Max(%s%s) as %s", preStr, fieldName, fieldName);
                }
                case MIN: {
                    return String.format("Min(%s%s) as %s", preStr, fieldName, fieldName);
                }
                case SUM: {
                    return String.format("Sum(%s%s*%s) as %s", preStr, fieldName, minusField, fieldName);
                }
            }
            if (this.database.isDatabase("derby")) {
                return String.format("'' as %s", fieldName);
            }
            return String.format("null as %s", fieldName);
        }
        if (isAllNumFieldsSum && fieldDefine.getAggrType() == AggrType.NONE && ((fieldType = fieldDefine.getColumnType()) == ColumnModelType.DOUBLE || fieldType == ColumnModelType.BIGDECIMAL || fieldType == ColumnModelType.INTEGER)) {
            logger.debug("\u6d6e\u52a8\u8868\u6c47\u603b\u8bbe\u7f6e\u4e0d\u6c47\u603b\u7684\u6570\u503c\u578b\u6307\u6807\u9ed8\u8ba4\u4e3a\u6c42\u548c");
            return String.format("Sum(%s%s) as %s", preStr, fieldName, fieldName);
        }
        switch (fieldDefine.getAggrType()) {
            case AVERAGE: {
                return String.format("Avg(%s%s) as %s", preStr, fieldName, fieldName);
            }
            case COUNT: {
                return String.format("Count(%s%s) as %s", preStr, fieldName, fieldName);
            }
            case MAX: {
                return String.format("Max(%s%s) as %s", preStr, fieldName, fieldName);
            }
            case MIN: {
                return String.format("Min(%s%s) as %s", preStr, fieldName, fieldName);
            }
            case SUM: {
                return String.format("Sum(%s%s) as %s", preStr, fieldName, fieldName);
            }
        }
        if (this.database.isDatabase("derby")) {
            return String.format("'' as %s", fieldName);
        }
        return String.format("null as %s", fieldName);
    }

    private boolean checkCanGather(ColumnModelDefine fieldDefine) {
        ColumnModelType fieldType = fieldDefine.getColumnType();
        if (fieldType == ColumnModelType.DOUBLE || fieldType == ColumnModelType.BIGDECIMAL || fieldType == ColumnModelType.INTEGER) {
            return true;
        }
        AggrType gatherType = fieldDefine.getAggrType();
        if (fieldType == ColumnModelType.STRING) {
            if (this.isNodeCheck) {
                return false;
            }
            if (gatherType == AggrType.COUNT || gatherType == AggrType.MAX || gatherType == AggrType.MIN) {
                return true;
            }
        }
        if (fieldType == ColumnModelType.BOOLEAN && (gatherType == AggrType.MAX || gatherType == AggrType.MIN)) {
            return true;
        }
        return fieldType == ColumnModelType.DATETIME && (gatherType == AggrType.MAX || gatherType == AggrType.MIN);
    }

    public void executeNodeCheckForNrdb(GatherDataTable gatherDataTable, GatherEntityValue gatherEntityValue, Integer maxLevel, String periodCode, EntityViewDefine viewDefine, DimensionValueSet dimensionValueSet, List<CheckErrorItem> errorItems, BigDecimal precisionValue, Map<String, String> unitCache, HashSet<String> unpassedNodeCheck) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        Map<ColumnModelDefine, DataField> gatherColumns2DataField = gatherDataTable.getGatherColumns2DataField();
        String regionKey = gatherTableDefine.getRegionKey();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
        NvwaDataEngineQueryUtil dataEngineQueryUtil = (NvwaDataEngineQueryUtil)BeanUtil.getBean(NvwaDataEngineQueryUtil.class);
        ArrayList<ColumnModelDefine> allColumns = new ArrayList<ColumnModelDefine>();
        allColumns.addAll(gatherDataTable.getAllDimsColumns());
        allColumns.addAll(gatherDataTable.getAllColumns());
        Map<String, ColumnModelDefine> fieldNameAndDefine = allColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, a -> a));
        List<ColumnModelDefine> gatherFields = gatherDataTable.getAllColumns();
        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
        String dimensionName = entityMetaService.getDimensionName(viewDefine.getEntityId());
        String periodDimension = periodAdapter.getPeriodDimensionName();
        String realTableName = this.getRealTableName(gatherDataTable);
        ColumnModelDefine dwColumnModelDefine = dataEngineQueryUtil.getColumnModelDefine(realTableName, dimensionName);
        int dwIndex = allColumns.indexOf(dwColumnModelDefine);
        ColumnModelDefine bizKeyOrder = fieldNameAndDefine.get(this.queryFieldName(gatherTableDefine.getBizOrderField()));
        ColumnModelDefine floatOrder = fieldNameAndDefine.get(this.queryFieldName(gatherTableDefine.getOrderField()));
        ArrayList<ColumnModelDefine> rowKeyColumns = new ArrayList<ColumnModelDefine>(gatherDataTable.getAllDimsColumns());
        rowKeyColumns.remove(bizKeyOrder);
        rowKeyColumns.remove(floatOrder);
        ArrayList<ColumnModelDefine> gatherSingleDefines = new ArrayList<ColumnModelDefine>();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims)) {
            for (Map.Entry<String, String> entry : this.gatherSingleDims.entrySet()) {
                gatherSingleDefines.add(dataEngineQueryUtil.getColumnModelDefine(realTableName, entry.getKey()));
            }
        }
        Integer level = maxLevel;
        while (level >= 1) {
            TuplesFilter tupleFilter = this.getTupleFilter(dwColumnModelDefine, gatherSingleDefines, gatherEntityValue, dimensionName, level);
            List pidValuesByLevel = gatherEntityValue.getPidValuesByLevel(level).stream().distinct().collect(Collectors.toList());
            dimensionValueSet.setValue(dimensionName, pidValuesByLevel);
            dimensionValueSet.setValue(periodDimension, (Object)periodCode);
            INvwaDataSet parentDataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, null, tupleFilter, true);
            Map<String, GatherRow> parentData = this.buildCheckData(parentDataSet, allColumns, rowKeyColumns, gatherDataTable.getAllDimsColumns(), dwIndex, null);
            List<String> idValuesByLevel = gatherEntityValue.getIdValuesByLevel(level);
            dimensionValueSet.setValue(dimensionName, idValuesByLevel);
            INvwaDataSet childDataSet = dataEngineQueryUtil.queryDataSet(realTableName, dimensionValueSet, allColumns, null, null, null, tupleFilter, true);
            Map<String, String> idValuesAndPidValuesByLevel = gatherEntityValue.getIdValuesAndPidValuesByLevel(level);
            Map<String, GatherRow> childData = this.buildCheckData(childDataSet, allColumns, rowKeyColumns, gatherDataTable.getAllDimsColumns(), dwIndex, idValuesAndPidValuesByLevel);
            HashSet<String> bizKeyOrders = new HashSet<String>();
            if (!CollectionUtils.isEmpty(parentData)) {
                this.leftJoin(periodCode, dimensionValueSet, errorItems, precisionValue, unitCache, unpassedNodeCheck, parentData, dwIndex, childData, dimensionName, bizKeyOrder, allColumns, rowKeyColumns, gatherFields, gatherColumns2DataField, gatherTableDefine.getTableDefine().getKey(), regionKey, bizKeyOrders);
            }
            if (CollectionUtils.isEmpty(parentData) || bizKeyOrder != null) {
                this.rightJoin(periodCode, dimensionValueSet, errorItems, precisionValue, unitCache, unpassedNodeCheck, childData, dwIndex, parentData, dimensionName, bizKeyOrder, allColumns, rowKeyColumns, gatherFields, gatherColumns2DataField, gatherTableDefine.getTableDefine().getKey(), regionKey, bizKeyOrders);
            }
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
        }
    }

    private void rightJoin(String periodCode, DimensionValueSet dimensionValueSet, List<CheckErrorItem> errorItems, BigDecimal precisionValue, Map<String, String> unitCache, HashSet<String> unpassedNodeCheck, Map<String, GatherRow> childData, int dwIndex, Map<String, GatherRow> parentData, String dimensionName, ColumnModelDefine bizKeyOrder, List<ColumnModelDefine> allColumns, List<ColumnModelDefine> rowKeyColumns, List<ColumnModelDefine> gatherFields, Map<ColumnModelDefine, DataField> gatherColumns2DataField, String dataTableKey, String regionKey, Set<String> bizKeyOrders) {
        for (Map.Entry<String, GatherRow> mapEntry : childData.entrySet()) {
            String rowKey = mapEntry.getKey();
            GatherRow childDataRow = mapEntry.getValue();
            String unitKey = childDataRow.readValue(dwIndex).toString();
            GatherRow parentDataRow = parentData.get(rowKey);
            dimensionValueSet.setValue(dimensionName, (Object)unitKey);
            DimensionValueSet resultDim = new DimensionValueSet(dimensionValueSet);
            String bizOrderValue = null;
            if (bizKeyOrder != null) {
                bizOrderValue = childDataRow.readValue(allColumns.indexOf(bizKeyOrder)).toString();
                if (bizKeyOrders.contains(bizOrderValue)) continue;
                bizKeyOrders.add(bizOrderValue);
            }
            DimensionValueSet rowKeyDim = this.getRowKeyDim(allColumns, rowKeyColumns, childDataRow);
            BigDecimal constValue = BigDecimal.ZERO;
            for (ColumnModelDefine dataField : gatherFields) {
                BigDecimal parentValue;
                DataField fieldDefine = gatherColumns2DataField.get(dataField);
                int index = allColumns.indexOf(dataField);
                BigDecimal childValue = (BigDecimal)childDataRow.readValue(index);
                BigDecimal bigDecimal = childValue = childValue == null ? BigDecimal.ZERO : childValue;
                parentValue = parentDataRow != null ? ((parentValue = (BigDecimal)parentDataRow.readValue(index)) == null ? BigDecimal.ZERO : parentValue) : constValue;
                int decimal = dataField.getDecimal();
                BigDecimal minusValue = (parentValue = parentValue.setScale(decimal, RoundingMode.HALF_UP)).subtract(childValue = childValue.setScale(decimal, RoundingMode.HALF_UP));
                if (minusValue.abs().compareTo(precisionValue) <= 0) continue;
                CheckErrorItem errorItem = this.getCheckErrorItem(resultDim, unitCache, unitKey, dataTableKey, bizOrderValue, periodCode, fieldDefine, parentValue, childValue, minusValue, rowKeyDim);
                errorItem.setRegionKey(regionKey);
                unpassedNodeCheck.add(unitKey);
                errorItems.add(errorItem);
            }
        }
    }

    private void leftJoin(String periodCode, DimensionValueSet dimensionValueSet, List<CheckErrorItem> errorItems, BigDecimal precisionValue, Map<String, String> unitCache, HashSet<String> unpassedNodeCheck, Map<String, GatherRow> parentData, int dwIndex, Map<String, GatherRow> childData, String dimensionName, ColumnModelDefine bizKeyOrder, List<ColumnModelDefine> allColumns, List<ColumnModelDefine> rowKeyColumns, List<ColumnModelDefine> gatherFields, Map<ColumnModelDefine, DataField> gatherColumns2DataField, String dataTableKey, String regionKey, Set<String> bizKeyOrders) {
        for (Map.Entry<String, GatherRow> mapEntry : parentData.entrySet()) {
            String rowKey = mapEntry.getKey();
            GatherRow parentDataRow = mapEntry.getValue();
            String unitKey = parentDataRow.readValue(dwIndex).toString();
            GatherRow childDataRow = childData.get(rowKey);
            dimensionValueSet.setValue(dimensionName, (Object)unitKey);
            DimensionValueSet resultDim = new DimensionValueSet(dimensionValueSet);
            String bizOrderValue = null;
            if (bizKeyOrder != null) {
                bizOrderValue = parentDataRow.readValue(allColumns.indexOf(bizKeyOrder)).toString();
                if (bizKeyOrders.contains(bizOrderValue)) continue;
                bizKeyOrders.add(bizOrderValue);
            }
            DimensionValueSet rowKeyDim = this.getRowKeyDim(allColumns, rowKeyColumns, parentDataRow);
            BigDecimal constValue = BigDecimal.ZERO;
            for (ColumnModelDefine dataField : gatherFields) {
                BigDecimal childValue;
                DataField fieldDefine = gatherColumns2DataField.get(dataField);
                int index = allColumns.indexOf(dataField);
                BigDecimal parentValue = (BigDecimal)parentDataRow.readValue(index);
                BigDecimal bigDecimal = parentValue = parentValue == null ? BigDecimal.ZERO : parentValue;
                childValue = childDataRow != null ? ((childValue = (BigDecimal)childDataRow.readValue(index)) == null ? BigDecimal.ZERO : childValue) : constValue;
                int decimal = dataField.getDecimal();
                BigDecimal minusValue = (parentValue = parentValue.setScale(decimal, RoundingMode.HALF_UP)).subtract(childValue = childValue.setScale(decimal, RoundingMode.HALF_UP));
                if (minusValue.abs().compareTo(precisionValue) <= 0) continue;
                CheckErrorItem errorItem = this.getCheckErrorItem(resultDim, unitCache, unitKey, dataTableKey, bizOrderValue, periodCode, fieldDefine, parentValue, childValue, minusValue, rowKeyDim);
                errorItem.setRegionKey(regionKey);
                unpassedNodeCheck.add(unitKey);
                errorItems.add(errorItem);
            }
        }
    }

    private DimensionValueSet getRowKeyDim(List<ColumnModelDefine> allColumns, List<ColumnModelDefine> rowKeyCols, GatherRow gatherRow) {
        DimensionValueSet rowKeyDim = new DimensionValueSet();
        for (ColumnModelDefine columnModelDefine : rowKeyCols) {
            String value = gatherRow.readValue(allColumns.indexOf(columnModelDefine)).toString();
            rowKeyDim.setValue(columnModelDefine.getName(), (Object)value);
        }
        return rowKeyDim;
    }

    private CheckErrorItem getCheckErrorItem(DimensionValueSet valueSet, Map<String, String> unitCache, String unitKey, String tableKey, String bizOrderValue, String periodCode, DataField dataField, BigDecimal parentValue, BigDecimal childValue, BigDecimal minusValue, DimensionValueSet rowKeyDim) {
        CheckErrorItem errorItem = new CheckErrorItem();
        errorItem.setUnitKey(unitKey);
        errorItem.setDimension(valueSet);
        errorItem.setPeriodCode(periodCode);
        errorItem.setTableKey(tableKey);
        errorItem.setFieldCode(dataField.getCode());
        errorItem.setParentValue(parentValue);
        errorItem.setChildValue(childValue);
        errorItem.setMinusValue(minusValue);
        errorItem.setFieldKey(dataField.getKey());
        errorItem.setFieldTitle(dataField.getTitle());
        errorItem.setBizOrder(bizOrderValue);
        errorItem.setRowKeys(rowKeyDim);
        if (unitCache.containsKey(unitKey)) {
            errorItem.setUnitTitle(unitCache.get(unitKey));
        }
        return errorItem;
    }

    private Map<String, GatherRow> buildCheckData(INvwaDataSet dataSet, List<ColumnModelDefine> allColumns, List<ColumnModelDefine> rowKeyColumns, List<ColumnModelDefine> dimColumnModelDefines, Integer dwIndex, Map<String, String> idMap) {
        HashMap<String, GatherRow> rowKeyAndGatherRows = new HashMap<String, GatherRow>();
        for (int i = 0; i < dataSet.size(); ++i) {
            INvwaDataRow item = dataSet.getRow(i);
            if (idMap != null) {
                item.setValue(dwIndex.intValue(), (Object)idMap.get(item.getValue(dwIndex.intValue()).toString()));
            }
            StringBuilder rowKey = new StringBuilder();
            for (ColumnModelDefine columnModelDefine : rowKeyColumns) {
                rowKey.append(item.getValue(columnModelDefine)).append(";");
            }
            GatherRow gatherRow = this.getGatherRow(allColumns, dimColumnModelDefines, new HashSet<ColumnModelDefine>(), rowKeyAndGatherRows, rowKey.toString(), new ArrayList<SortInfo>(), null);
            for (int j = 0; j < allColumns.size(); ++j) {
                gatherRow.writeValue(j, item.getValue(j));
            }
        }
        return rowKeyAndGatherRows;
    }

    public CheckSqlItem buildCheckTableSqls(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimension, IQuerySqlUpdater querySqlUpdater, String versionField) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        Map<ColumnModelDefine, DataField> gatherColumns2DataField = gatherDataTable.getGatherColumns2DataField();
        CheckSqlItem checkSqlItem = new CheckSqlItem();
        ArrayList<SqlItem> checkSqls = new ArrayList<SqlItem>();
        StringBuilder sqlBuilderParent = new StringBuilder();
        sqlBuilderParent.append("select");
        String fieldName = this.queryFieldName(gatherTableDefine.getUnitField());
        String nvlSql = this.buildNvlSql();
        int colIndex = 1;
        sqlBuilderParent.append(String.format(" %s(tt.%s, st.%s) as %s,", nvlSql, fieldName, fieldName, fieldName));
        if (gatherTableDefine.getPeriodField() != null) {
            fieldName = this.queryFieldName(gatherTableDefine.getPeriodField());
            sqlBuilderParent.append(String.format(" %s(tt.%s, st.%s) as %s,", nvlSql, fieldName, fieldName, fieldName));
            ++colIndex;
        }
        if (gatherTableDefine.getBizOrderField() != null) {
            fieldName = this.queryFieldName(gatherTableDefine.getBizOrderField());
            sqlBuilderParent.append(String.format(" %s(tt.%s, st.%s) as %s,", nvlSql, fieldName, fieldName, fieldName));
            ++colIndex;
        }
        if (gatherTableDefine.getClassifyFields() != null) {
            for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                fieldName = this.queryFieldName(classifyField);
                sqlBuilderParent.append(String.format(" %s(tt.%s, st.%s) as %s,", nvlSql, fieldName, fieldName, fieldName));
                ++colIndex;
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                fieldName = this.queryFieldName(unClassifyField);
                sqlBuilderParent.append(String.format(" %s(tt.%s, st.%s) as %s,", nvlSql, fieldName, fieldName, fieldName));
                ++colIndex;
            }
        }
        ++colIndex;
        List<ColumnModelDefine> column = gatherDataTable.getAllColumns();
        List<List<ColumnModelDefine>> columnsAvgList = this.avgerList(column);
        for (List<ColumnModelDefine> columnModelDefines : columnsAvgList) {
            ArrayList<FieldItem> fieldItems = new ArrayList<FieldItem>();
            StringBuilder sqlBuilder = new StringBuilder();
            int innerColIndex = colIndex;
            gatherDataTable.setAllColumns(columnModelDefines);
            for (ColumnModelDefine gatherField : columnModelDefines) {
                fieldName = this.queryFieldName(gatherField);
                ColumnModelType fieldType = gatherField.getColumnType();
                DataField fieldDefine = gatherColumns2DataField.get(gatherField);
                if (!fieldType.equals((Object)ColumnModelType.INTEGER) && !fieldType.equals((Object)ColumnModelType.DOUBLE) && !fieldType.equals((Object)ColumnModelType.BIGDECIMAL)) continue;
                FieldItem fieldItem = new FieldItem(fieldDefine, gatherTableDefine.getTableDefine().getKey(), innerColIndex);
                fieldItems.add(fieldItem);
                sqlBuilder.append(String.format(" %s(tt.%s, 0) as %s%s,", nvlSql, fieldName, "P_", fieldName));
                sqlBuilder.append(String.format(" %s(st.%s, 0) as %s%s,", nvlSql, fieldName, "C_", fieldName));
                innerColIndex += 2;
            }
            sqlBuilder.setLength(sqlBuilder.length() - 1);
            sqlBuilder.append(" from ");
            if (!this.matchConnType(this.connection, "h2")) {
                sqlBuilder.append(" (");
            }
            String parentSql = this.buildSelfSql(gatherDataTable, gatherTempTable, periodCode, -1, sourceDimensions, targetDimension, versionField);
            if (querySqlUpdater != null) {
                QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
                parentSql = querySqlUpdater.updateQuerySql(queryTable, "c1", parentSql);
            }
            String childSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, noGatherTempTable, periodCode, false, false, -1, sourceDimensions, targetDimension, versionField, false);
            if (querySqlUpdater != null) {
                QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
                childSql = querySqlUpdater.updateQuerySql(queryTable, "c1", childSql);
            }
            sqlBuilder.append(String.format("(%s) tt left join (%s) st ", parentSql, childSql));
            fieldName = this.queryFieldName(gatherTableDefine.getUnitField());
            sqlBuilder.append(String.format(" on tt.%s=st.%s ", fieldName, fieldName));
            if (gatherTableDefine.getPeriodField() != null) {
                fieldName = this.queryFieldName(gatherTableDefine.getPeriodField());
                sqlBuilder.append(String.format("and tt.%s=st.%s ", fieldName, fieldName));
            }
            if (gatherTableDefine.getClassifyFields() != null) {
                for (DataField classifyField : gatherTableDefine.getClassifyFields()) {
                    fieldName = this.queryFieldName(classifyField);
                    if (this.gatherSingleDims.containsKey(fieldName)) continue;
                    sqlBuilder.append(String.format("and tt.%s=st.%s ", fieldName, fieldName));
                }
            }
            if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
                for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                    fieldName = this.queryFieldName(unClassifyField);
                    sqlBuilder.append(String.format("and tt.%s=st.%s ", fieldName, fieldName));
                }
            }
            if (!this.matchConnType(this.connection, "h2")) {
                sqlBuilder.append(")");
            }
            String executeSql = sqlBuilderParent.toString().concat(sqlBuilder.toString());
            Integer index = maxLevel;
            while (index >= 1) {
                SqlItem sqlItem = new SqlItem();
                sqlItem.setExecutorSql(executeSql);
                ArrayList<Object> paramValues = new ArrayList<Object>();
                this.addPeriodAndVersion(gatherTableDefine.getPeriodField(), periodCode, versionField, paramValues);
                paramValues.add(index);
                paramValues.add(this.executionId);
                this.addNoGatherParamValues(gatherTableDefine, noGatherTempTable, index, paramValues);
                this.addPeriodAndVersion(gatherTableDefine.getPeriodField(), periodCode, versionField, paramValues);
                sqlItem.setParamValues(paramValues);
                sqlItem.setInnerfieldItems(fieldItems);
                checkSqls.add(sqlItem);
                Integer n = index;
                Integer n2 = index = Integer.valueOf(index - 1);
            }
        }
        checkSqlItem.setSqlItems(checkSqls);
        return checkSqlItem;
    }

    private String buildSelfSql(GatherDataTable gatherDataTable, String gatherTempTable, String periodCode, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, String versionField) throws SQLException {
        String filterSql;
        List<DataField> classifyFields;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlStr = new StringBuilder();
        DataField periodField = gatherTableDefine.getPeriodField();
        DataField bizOrderField = gatherTableDefine.getBizOrderField();
        sqlStr.append("select");
        sqlStr.append(String.format(" %s.%s,", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            sqlStr.append(String.format(" %s.%s,", "c1", this.queryFieldName(periodField)));
        }
        if (bizOrderField != null) {
            sqlStr.append(String.format(" Min(%s.%s) as %s,", "c1", this.queryFieldName(bizOrderField), this.queryFieldName(bizOrderField)));
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null && classifyFields.size() > 0) {
            if (targetDimensions != null && targetDimensions.size() > 0) {
                TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                for (DataField classifyField : classifyFields) {
                    String dimName;
                    String string = dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(classifyField.getCode());
                    if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) {
                        Object dimValue = targetDimensions.getValue(dimName);
                        if (dimValue instanceof List) {
                            sqlStr.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                            continue;
                        }
                        if (classifyField.getType() == FieldType.FIELD_TYPE_UUID) {
                            sqlStr.append(String.format("%s as %s,", DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimName).toString())), this.queryFieldName(classifyField)));
                            continue;
                        }
                        sqlStr.append(String.format("'%s' as %s,", targetDimensions.getValue(dimName).toString(), this.queryFieldName(classifyField)));
                        continue;
                    }
                    sqlStr.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            } else {
                for (DataField classifyField : classifyFields) {
                    sqlStr.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            }
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null && unClassifyFields.size() > 0) {
            String nvlSql = this.buildNvlSql();
            for (DataField unClassifyField : unClassifyFields) {
                DataFieldType dataFieldType = unClassifyField.getDataFieldType();
                if (dataFieldType == DataFieldType.DATE || dataFieldType == DataFieldType.DATE_TIME) {
                    sqlStr.append(String.format(" %s(%s.%s, %s) as %s,", nvlSql, "c1", this.queryFieldName(unClassifyField), this.buildDateSql("2024-01-01"), this.queryFieldName(unClassifyField)));
                    continue;
                }
                sqlStr.append(String.format(" %s(%s.%s, '%s') as %s,", nvlSql, "c1", this.queryFieldName(unClassifyField), EMPTY, this.queryFieldName(unClassifyField)));
            }
        }
        sqlStr.append(this.buildGatherFieldsSql(gatherDataTable.getAllColumns(), "c1", !gatherTableDefine.isFixed(), false, String.format("%s.%s", "c2", "GT_ISMINUS")));
        sqlStr.append(" from ");
        String tableName = this.getRealTableName(gatherDataTable);
        sqlStr.append(String.format("%s %s  ", tableName, "c1"));
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlStr, gatherTableDefine, periodCode);
        }
        sqlStr.append(" where ");
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            sqlStr.append(String.format("%s.%s=? and ", "c1", this.queryFieldName(periodField)));
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            this.buildWhereDimensions(sqlStr, dimFields, sourceDimensions, false, String.format("%s.", "c1"), null);
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            sqlStr.append(String.format("%s.%s=? and ", "c1", versionField));
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName, "c1")))) {
            sqlStr.append(" (").append(filterSql).append(") and ");
        }
        sqlStr.append(String.format(" %s.%s in ", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        sqlStr.append(String.format(" (select distinct %s  from %s %s where %s=? and %s=?)", "GT_PID", gatherTempTable, "c2", "GT_LEVEL", "EXECUTION_ID"));
        boolean needOrder = this.checkNeedOrder(gatherTableDefine, classifyFields, unClassifyFields);
        StringBuilder groupSql = new StringBuilder();
        if (!gatherTableDefine.isFixed()) {
            sqlStr.append(" group by ");
            groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
            if (periodField != null) {
                groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(periodField)));
            }
            if (classifyFields != null && classifyFields.size() > 0) {
                for (DataField classifyField : classifyFields) {
                    groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            }
        }
        if (!gatherTableDefine.isFixed() && unClassifyFields != null && unClassifyFields.size() > 0) {
            for (DataField unClassifyField : unClassifyFields) {
                groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
            }
        }
        if (!gatherTableDefine.isFixed()) {
            groupSql.setLength(groupSql.length() - 1);
        }
        if (needOrder) {
            sqlStr.append((CharSequence)groupSql).append(" order by ").append((CharSequence)groupSql);
        } else {
            sqlStr.append((CharSequence)groupSql);
        }
        return sqlStr.toString();
    }

    protected List<HashMap<String, Object>> doExecuteQuery(GatherDataTable gatherDataTable, String sourceSql, String periodCode, List<Object> paramValues) throws SQLException {
        ArrayList<HashMap<String, Object>> valMapList = new ArrayList<HashMap<String, Object>>();
        if (gatherDataTable.getGatherTableDefine().getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
            paramValues.add(periodCode);
        }
        List<String> querySql = this.buildQuerySumDataSql(gatherDataTable, sourceSql, periodCode);
        for (String selectSql : querySql) {
            try {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                this.printLoggerSQL(selectSql, paramValues, "\u6c47\u603b\u67e5\u8be2", gatherDataTable.getTableModelDefine().getName());
                PreparedStatement prep = this.connection.prepareStatement(selectSql);
                Throwable throwable = null;
                try {
                    Object[] params = paramValues.toArray();
                    if (params.length > 0) {
                        for (int i = 0; i < params.length; ++i) {
                            GatherSqlGenerater.setValue(params[i], prep, i);
                        }
                    }
                    prep.setFetchSize(100);
                    ResultSet queryResult = prep.executeQuery();
                    Throwable throwable2 = null;
                    try {
                        stopWatch.stop();
                        logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
                        List<LinkedHashMap<String, Object>> formData = this.parseResultSet(queryResult, gatherDataTable);
                        if (formData.isEmpty()) continue;
                        for (int j = 0; j < formData.size(); ++j) {
                            HashMap valMap;
                            if (valMapList.isEmpty() || valMapList.size() - 1 < j) {
                                valMap = new HashMap();
                                valMapList.add(valMap);
                            }
                            valMap = (HashMap)valMapList.get(j);
                            for (Map.Entry<String, Object> entry : formData.get(j).entrySet()) {
                                valMap.put(entry.getKey().toUpperCase(), entry.getValue());
                            }
                        }
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (queryResult == null) continue;
                        if (throwable2 != null) {
                            try {
                                queryResult.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        queryResult.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (prep == null) continue;
                    if (throwable != null) {
                        try {
                            prep.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    prep.close();
                }
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return valMapList;
    }

    public static void setValue(Object value, PreparedStatement prep, int i) throws SQLException {
        if (value instanceof UUID) {
            prep.setBytes(i + 1, Convert.toBytes((UUID)((UUID)value)));
        } else if (value instanceof Date) {
            Timestamp timestamp = new Timestamp(((Date)value).getTime());
            prep.setTimestamp(i + 1, timestamp);
        } else {
            prep.setObject(i + 1, value);
        }
    }

    private String buildExecuteUpdateSqlNormal(GatherDataTable gatherDataTable, String versionField) {
        return this.buildExecuteUpdateSqlNormal(gatherDataTable, versionField, false);
    }

    protected String buildExecuteUpdateSqlNormal(GatherDataTable gatherDataTable, String versionField, boolean fileGather) {
        String field;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        String tableName = this.getRealTableName(gatherDataTable);
        DataField periodField = gatherTableDefine.getPeriodField();
        List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
        boolean addDot = false;
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("update " + tableName);
        updateSql.append(" sw set ");
        for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
            if (!fileGather && gatherField.getColumnType() == ColumnModelType.ATTACHMENT) continue;
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            String fieldName = this.queryFieldName(gatherField);
            updateSql.append(fieldName);
            updateSql.append("=");
            updateSql.append("?").append(" ");
        }
        updateSql.append(" where ");
        String unitFieldName = this.queryFieldName(gatherTableDefine.getUnitField()).toUpperCase();
        updateSql.append(String.format(" %s.%s=?", "sw", unitFieldName));
        if (periodField != null) {
            String perField = this.queryFieldName(periodField).toUpperCase();
            updateSql.append(" and ");
            updateSql.append(String.format("%s.%s=?", "sw", perField));
        }
        if (classifyFields != null) {
            for (DataField classifyField : classifyFields) {
                updateSql.append(" and ");
                field = this.queryFieldName(classifyField).toUpperCase();
                updateSql.append(String.format("%s.%s=?", "sw", field));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                updateSql.append(" and ");
                field = this.queryFieldName(unClassifyField).toUpperCase();
                updateSql.append(String.format("%s.%s=?", "sw", field));
            }
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            updateSql.append(" and ").append("sw.").append(versionField).append("=?");
        }
        return updateSql.toString();
    }

    private Map<String, Object> parseExtraParam(GatherTableDefine gatherTableDefine, String versionField, Map<String, Object> valMap) {
        String field;
        List<DataField> classifyFields;
        HashMap<String, Object> extraParamValues = new HashMap<String, Object>();
        String unitFieldName = this.queryFieldName(gatherTableDefine.getUnitField()).toUpperCase();
        if (gatherTableDefine.getUnitField().getType().equals((Object)FieldType.FIELD_TYPE_UUID)) {
            extraParamValues.put(unitFieldName, DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)Convert.toUUID((Object)valMap.get(unitFieldName))));
        } else {
            extraParamValues.put(unitFieldName, valMap.get(unitFieldName));
        }
        DataField periodField = gatherTableDefine.getPeriodField();
        if (periodField != null) {
            String perField = this.queryFieldName(periodField).toUpperCase();
            extraParamValues.put(perField, valMap.get(perField));
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null) {
            for (DataField classifyField : classifyFields) {
                field = this.queryFieldName(classifyField).toUpperCase();
                if (classifyField.getType().equals((Object)FieldType.FIELD_TYPE_UUID)) {
                    extraParamValues.put(field, DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)Convert.toUUID((Object)valMap.get(field))));
                    continue;
                }
                extraParamValues.put(field, valMap.get(field));
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                field = this.queryFieldName(unClassifyField).toUpperCase();
                extraParamValues.put(field, valMap.get(field));
            }
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            extraParamValues.put(versionField, "00000000-0000-0000-0000-000000000000");
        }
        return extraParamValues;
    }

    protected void addUpdateWhereParams(List<Object> paramValues, GatherTableDefine gatherTableDefine, String versionField, Map<String, Object> valMap) {
        String field;
        List<DataField> classifyFields;
        String unitFieldName = this.queryFieldName(gatherTableDefine.getUnitField()).toUpperCase();
        if (gatherTableDefine.getUnitField().getDataFieldType().equals((Object)DataFieldType.UUID)) {
            paramValues.add(valMap.containsKey(unitFieldName) && null != valMap.get(unitFieldName) ? DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)Convert.toUUID((Object)valMap.get(unitFieldName))) : null);
        } else {
            paramValues.add(valMap.containsKey(unitFieldName) && null != valMap.get(unitFieldName) ? valMap.get(unitFieldName) : null);
        }
        DataField periodField = gatherTableDefine.getPeriodField();
        if (periodField != null) {
            String perField = this.queryFieldName(periodField).toUpperCase();
            paramValues.add(valMap.containsKey(perField) && null != valMap.get(perField) ? valMap.get(perField) : null);
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null) {
            for (DataField classifyField : classifyFields) {
                field = this.queryFieldName(classifyField).toUpperCase();
                if (classifyField.getType().equals((Object)FieldType.FIELD_TYPE_UUID)) {
                    paramValues.add(valMap.containsKey(field) && null != valMap.get(field) ? DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)Convert.toUUID((Object)valMap.get(field))) : null);
                    continue;
                }
                paramValues.add(valMap.containsKey(field) && null != valMap.get(field) ? valMap.get(field) : null);
            }
        }
        if (!gatherTableDefine.isFixed() && gatherTableDefine.getUnClassifyFields() != null) {
            for (DataField unClassifyField : gatherTableDefine.getUnClassifyFields()) {
                field = this.queryFieldName(unClassifyField).toUpperCase();
                paramValues.add(valMap.containsKey(field) && null != valMap.get(field) ? valMap.get(field) : null);
            }
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            paramValues.add("00000000-0000-0000-0000-000000000000");
        }
    }

    protected void addNoGatherParamValues(GatherTableDefine gatherTableDefine, String noGatherTable, Integer level, List<Object> paraValues) {
        if (level != null) {
            paraValues.add(level);
        }
        paraValues.add(this.executionId);
        if (StringUtils.isNotEmpty((String)noGatherTable)) {
            paraValues.add(gatherTableDefine.getFormId().toUpperCase());
            paraValues.add(this.executionId);
        }
    }

    protected void addPeriodAndVersion(DataField periodField, String periodCode, String versionField, List<Object> parmaValues) {
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            parmaValues.add(periodCode);
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            parmaValues.add("00000000-0000-0000-0000-000000000000");
        }
    }

    private List<LinkedHashMap<String, Object>> parseResultSet(ResultSet result, GatherDataTable gatherDataTable) throws SQLException {
        ArrayList<LinkedHashMap<String, Object>> res = new ArrayList<LinkedHashMap<String, Object>>();
        ResultSetMetaData metadata = result.getMetaData();
        int count = metadata.getColumnCount();
        List<ColumnModelDefine> fieldList = gatherDataTable.getAllColumns();
        HashMap keyMap = new HashMap();
        fieldList.forEach(item -> keyMap.put(this.queryFieldName((ColumnModelDefine)item).toUpperCase(), item));
        while (result.next()) {
            LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
            for (int i = 1; i <= count; ++i) {
                String colName = this.database.isDatabase("DM") ? metadata.getColumnLabel(i).toUpperCase() : metadata.getColumnName(i).toUpperCase();
                Object value = result.getObject(i);
                if (keyMap.containsKey(colName)) {
                    ColumnModelType fieldType = ((ColumnModelDefine)keyMap.get(colName)).getColumnType();
                    if (fieldType.equals((Object)ColumnModelType.DOUBLE)) {
                        value = Convert.toDouble((Object)value);
                    } else if (fieldType.equals((Object)ColumnModelType.INTEGER) && null != value) {
                        value = Math.round(((Number)value).doubleValue());
                    }
                }
                obj.put(colName, value);
            }
            res.add(obj);
        }
        return res;
    }

    private boolean matchConnType(Connection connection, String type) throws SQLException {
        String url = connection.getMetaData().getURL();
        return url.contains(type.toLowerCase());
    }

    private void executeUpdate(String sql, List<Object> args, Connection conn) throws SQLException {
        try (Statement stat = null;){
            stat = conn.createStatement();
            for (Object obj : args) {
                sql = sql.replaceFirst("\\?", "'" + obj.toString() + "'");
            }
            stat.execute(sql);
        }
    }

    private <T> List<List<T>> avgerList(List<T> entityList) {
        ArrayList<List<T>> avgList = new ArrayList<List<T>>();
        if (Objects.nonNull(this.dataGatherConfig) && this.dataGatherConfig.getAvg().booleanValue()) {
            Integer splitSzie = 250;
            if (this.dataGatherConfig.getAvgSize() > 0) {
                splitSzie = this.dataGatherConfig.getAvgSize();
            }
            int size = entityList.size();
            int count = size / splitSzie;
            int remainder = size % splitSzie;
            for (int i = 0; i < count; ++i) {
                avgList.add(entityList.subList(i * splitSzie, (i + 1) * splitSzie));
            }
            if (remainder > 0) {
                avgList.add(entityList.subList(count * splitSzie, size));
            }
        } else {
            avgList.add(entityList);
        }
        return avgList;
    }

    protected String parseFilterToSQL(ExecutorContext executorContext, String filterCondition, String tableName, String aliasTableName) {
        QueryContext qContext = null;
        try {
            qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
            IExpression rowFilterNode = parser.parseCond(filterCondition, (IContext)qContext);
            SQLInfoDescr conditionSqlInfo = new SQLInfoDescr(this.database, true, 0, 0);
            if (StringUtils.isNotEmpty((String)aliasTableName)) {
                for (int index = 0; index < rowFilterNode.childrenSize(); ++index) {
                    IASTNode child = rowFilterNode.getChild(index);
                    if (child instanceof DynamicDataNode) {
                        DynamicDataNode dataNode = (DynamicDataNode)child;
                        if (!tableName.equals(this.subDatabaseTableNamesProvider.getSubDatabaseTableName(this.taskKey, dataNode.getQueryField().getTableName()))) continue;
                        dataNode.setTableAlias(aliasTableName);
                        continue;
                    }
                    this.internalSetTableAlias(child, tableName, aliasTableName);
                }
            }
            String sql = rowFilterNode.interpret((IContext)qContext, Language.SQL, (Object)conditionSqlInfo);
            return sql;
        }
        catch (Exception e) {
            logger.warn("\u6c47\u603b\u533a\u57df\u8fc7\u6ee4\u6761\u4ef6\u8f6c\u5316SQL\u5931\u8d25\uff0c\u65e0\u6cd5\u6b63\u5e38\u8fc7\u6ee4\uff0c\u8bf7\u68c0\u67e5\uff01", e);
            return null;
        }
    }

    private void internalSetTableAlias(IASTNode parentNode, String tableName, String aliasTableName) {
        for (int index = 0; index < parentNode.childrenSize(); ++index) {
            IASTNode child = parentNode.getChild(index);
            if (child instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)child;
                if (!tableName.equals(this.subDatabaseTableNamesProvider.getSubDatabaseTableName(this.taskKey, dataNode.getQueryField().getTableName()))) continue;
                dataNode.setTableAlias(aliasTableName);
                continue;
            }
            this.internalSetTableAlias(child, tableName, aliasTableName);
        }
    }

    private void appendGatherDimSQL(StringBuilder sql, boolean andPrev, GatherDataTable gatherTableDefine, String tempTableAliasName) {
        if (this.gatherSingleDims.isEmpty()) {
            return;
        }
        ArrayList<String> keys = new ArrayList<String>(this.gatherSingleDims.keySet());
        List<DataField> classifyFields = gatherTableDefine.getGatherTableDefine().getClassifyFields();
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherTableDefine.getTableModelDefine().getName());
        for (DataField classifyField : classifyFields) {
            String dimName = tableRunInfo.getDimensionName(classifyField.getCode());
            if (!keys.contains(dimName)) continue;
            LogicField field = GatherTempTableHandler.getDimCols().get(keys.indexOf(dimName));
            sql.append(String.format(" %s %s=%s%s ", andPrev ? "and" : "", this.queryFieldName(classifyField), StringUtils.isEmpty((String)tempTableAliasName) ? "" : tempTableAliasName, field.getFieldName()));
        }
    }

    protected void appendExecutionSql(StringBuilder sql, boolean andPrev, boolean hardParse, List<Object> paramValues, String aliasName) {
        String string = aliasName = StringUtils.isNotEmpty((String)aliasName) ? aliasName : "c2";
        if (this.executionId != null) {
            if (hardParse) {
                sql.append(String.format(" %s %s.%s='%s' %s", andPrev ? "and" : "", aliasName, "EXECUTION_ID", this.executionId, andPrev ? "" : " and "));
            } else {
                sql.append(String.format(" %s %s.%s=? %s", andPrev ? "and" : "", aliasName, "EXECUTION_ID", andPrev ? "" : " and "));
                if (paramValues != null) {
                    paramValues.add(this.executionId);
                }
            }
        }
    }

    protected void appendNoGatherSql(GatherTableDefine gatherTableDefine, String noGatherTempTable, StringBuilder sql, boolean andPrev, boolean hardParse, List<Object> paramValues, String aliasName) {
        String string = aliasName = StringUtils.isNotEmpty((String)aliasName) ? aliasName : "c2";
        if (StringUtils.isNotEmpty((String)noGatherTempTable)) {
            if (!hardParse) {
                sql.append(String.format(" %s not exists (select 1 from %s t4 where t4.%s=%s.%s and t4.%s=? and t4.%s=?)", andPrev ? "and" : "", noGatherTempTable, "NT_ID", aliasName, "GT_PID", "NT_FID", "EXECUTION_ID"));
                if (paramValues != null) {
                    paramValues.add(gatherTableDefine.getFormId().toUpperCase());
                    paramValues.add(this.executionId);
                }
            } else {
                sql.append(String.format(" %s not exists (select 1 from %s t4 where t4.%s=%s.%s and t4.%s='%s' and t4.%s='%s')", andPrev ? "and" : "", noGatherTempTable, "NT_ID", aliasName, "GT_PID", "NT_FID", gatherTableDefine.getFormId().toUpperCase(), "EXECUTION_ID", this.executionId));
            }
        }
    }

    protected String getRealTableName(GatherDataTable gatherTableDefine) {
        return gatherTableDefine.getTableModelDefine().getName();
    }

    protected String queryFieldName(ColumnModelDefine columnModelDefine) {
        if (Objects.isNull(columnModelDefine)) {
            return null;
        }
        return columnModelDefine.getName();
    }

    protected String queryFieldName(DataField dataField) {
        if (Objects.isNull(dataField)) {
            return null;
        }
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (!CollectionUtils.isEmpty(deployInfo)) {
            return ((DataFieldDeployInfo)deployInfo.get(0)).getFieldName();
        }
        return null;
    }

    private String queryTableName(TableModelDefine tableModelDefine) {
        if (Objects.isNull(tableModelDefine)) {
            return null;
        }
        return tableModelDefine.getName();
    }

    public void printLoggerSQL(SqlItem executeSql, String title, String tableName) {
        StringBuffer str = new StringBuffer();
        str.append(tableName).append("\u8868\uff0c");
        str.append(title);
        str.append("SQL\uff1a");
        str.append(executeSql.getExecutorSql());
        str.append(",\u53c2\u6570\uff1a");
        str.append(executeSql.getParamValues());
        logger.debug(str.toString());
    }

    public void printLoggerSQL(String executeSql, List<Object> paramValues, String title, String tableName) {
        StringBuffer str = new StringBuffer();
        str.append(tableName).append("\u8868\uff0c");
        str.append(title);
        str.append("SQL\uff1a");
        str.append(executeSql);
        str.append(",\u53c2\u6570\uff1a");
        str.append(paramValues);
        logger.debug(str.toString());
    }

    private String buildGroupSqlForChildren2ErrorList(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, boolean isMinus, boolean listGather, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, String versionField, boolean exeHardParse, ITempTable errorItemTemp, ITempTable fileGatherTmp) {
        String filterSql;
        List<DataField> classifyFields;
        DataField bizOrderField;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select ");
        sqlBuilder.append(String.format("%s.%s as %s,", "c2", "GT_PID", this.queryFieldName(gatherTableDefine.getUnitField())));
        DataField periodField = gatherTableDefine.getPeriodField();
        if (periodField != null) {
            sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(periodField)));
        }
        if ((bizOrderField = gatherTableDefine.getBizOrderField()) != null) {
            sqlBuilder.append(String.format("%s.%s as %s,", "c3", errorItemTemp.getRealColName("PARENT_BIEKEY"), this.queryFieldName(bizOrderField)));
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null) {
            if (targetDimensions != null && targetDimensions.size() > 0) {
                TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
                ArrayList<String> keysList = new ArrayList<String>(this.gatherSingleDims.keySet());
                for (DataField classifyField : classifyFields) {
                    String dimName = tableRunInfo == null ? "" : tableRunInfo.getDimensionName(this.queryFieldName(classifyField));
                    int index = keysList.indexOf(dimName);
                    if (index >= 0) {
                        LogicField field = GatherTempTableHandler.getDimCols().get(index);
                        sqlBuilder.append(String.format("%s.%s as %s,", "c2", field.getFieldName(), this.queryFieldName(classifyField)));
                        continue;
                    }
                    if (!StringUtils.isEmpty((String)dimName) && targetDimensions.hasValue(dimName)) {
                        Object dimValue = targetDimensions.getValue(dimName);
                        if (dimValue instanceof List) {
                            sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                            continue;
                        }
                        if (classifyField.getType() == FieldType.FIELD_TYPE_UUID) {
                            sqlBuilder.append(String.format("%s as %s,", DataEngineUtil.buildGUIDValueSql((IDatabase)this.database, (UUID)UUID.fromString(targetDimensions.getValue(dimName).toString())), this.queryFieldName(classifyField)));
                            continue;
                        }
                        sqlBuilder.append(String.format("'%s' as %s,", targetDimensions.getValue(dimName).toString(), this.queryFieldName(classifyField)));
                        continue;
                    }
                    sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            } else {
                for (DataField classifyField : classifyFields) {
                    sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
                }
            }
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null) {
            for (DataField unClassifyField : unClassifyFields) {
                sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
            }
        }
        String tableName = this.getRealTableName(gatherDataTable);
        sqlBuilder.append(this.buildGatherFieldsSql(gatherDataTable.getAllColumns(), "c1", !listGather, isMinus, String.format("%s.%s", "c2", "GT_ISMINUS"), false, fileGatherTmp));
        sqlBuilder.append(" from ");
        sqlBuilder.append(String.format("%s %s", tableName, "c1"));
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlBuilder, gatherTableDefine, periodCode);
        }
        sqlBuilder.append(" left join ").append(String.format("%s %s", gatherTempTable, "c2"));
        sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_ID"));
        if (bizOrderField != null) {
            sqlBuilder.append(" left join ").append(String.format("%s %s", errorItemTemp.getTableName(), "c3"));
            sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(bizOrderField), "c3", errorItemTemp.getRealColName("CHILDREN_BIZKEY")));
        }
        if (fileGatherTmp != null) {
            sqlBuilder.append(" left join ").append(String.format("%s %s", fileGatherTmp.getTableName(), "c4"));
            sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getBizOrderField()), "c4", fileGatherTmp.getRealColName(gatherTableDefine.getBizOrderField().getCode())));
        }
        if (level < 0) {
            sqlBuilder.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        } else {
            sqlBuilder.append(" where ").append(String.format("%s.%s=%s", "c2", "GT_LEVEL", level));
        }
        this.appendExecutionSql(sqlBuilder, true, exeHardParse, null, "c2");
        this.appendExecutionSql(sqlBuilder, true, exeHardParse, null, "c3");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, sqlBuilder, true, exeHardParse, null, "c2");
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            if (exeHardParse) {
                sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
            } else {
                sqlBuilder.append(" and ").append(String.format("%s.%s=?", "c1", this.queryFieldName(periodField)));
            }
        }
        if (StringUtils.isNotEmpty((String)versionField)) {
            if (exeHardParse) {
                sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", versionField, "00000000-0000-0000-0000-000000000000"));
            } else {
                sqlBuilder.append(" and ").append(String.format("%s.%s=?", "c1", versionField));
            }
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName, "c1")))) {
            sqlBuilder.append(" and (").append(filterSql).append(")");
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            this.buildWhereDimensions(sqlBuilder, dimFields, sourceDimensions, true, String.format("%s.", "c1"), null);
        }
        sqlBuilder.append(" order by ");
        sqlBuilder.append(String.format("%s.%s,", "c2", "GT_ORDER"));
        if (!gatherTableDefine.isFixed()) {
            sqlBuilder.append(String.format("%s,", FLOATORDER));
        }
        sqlBuilder.append(String.format("%s.%s,", "c2", "GT_ID"));
        if (gatherTableDefine.getOrderField() != null) {
            sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getOrderField())));
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        return sqlBuilder.toString();
    }

    private String buildGetGroupKeySql(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTable, String periodCode, int level, DimensionValueSet sourceDimensions, List<DataField> tempColumn) {
        String filterSql;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select ");
        sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        sqlBuilder.append(String.format("%s.%s,", "c2", "GT_PID"));
        DataField periodField = gatherTableDefine.getPeriodField();
        DataField bizOrderField = gatherTableDefine.getBizOrderField();
        if (bizOrderField != null) {
            sqlBuilder.append(String.format("%s.%s,", "c1", this.queryFieldName(bizOrderField)));
        }
        String tableName = this.getRealTableName(gatherDataTable);
        sqlBuilder.append(this.buildTempGatherFieldsSql(tempColumn));
        sqlBuilder.append(" from ");
        sqlBuilder.append(String.format("%s %s", tableName, "c1"));
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlBuilder, gatherTableDefine, periodCode);
        }
        sqlBuilder.append(" left join ").append(String.format("%s %s", gatherTempTable, "c2"));
        sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), "c2", "GT_ID"));
        if (level < 0) {
            sqlBuilder.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        } else {
            sqlBuilder.append(" where ").append(String.format("%s.%s=%s", "c2", "GT_LEVEL", level));
        }
        this.appendExecutionSql(sqlBuilder, true, true, null, "c2");
        this.appendNoGatherSql(gatherTableDefine, noGatherTable, sqlBuilder, true, true, null, "c2");
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName, "c1")))) {
            sqlBuilder.append(" and (").append(filterSql).append(")");
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            this.buildWhereDimensions(sqlBuilder, dimFields, sourceDimensions, true, String.format("%s.", "c1"), null);
        }
        sqlBuilder.append(" order by ");
        StringBuilder orderSql = new StringBuilder();
        orderSql.append(String.format("%s.%s,", "c2", "GT_ORDER"));
        if (!gatherTableDefine.isFixed()) {
            orderSql.append(String.format("%s,", FLOATORDER));
        }
        orderSql.append(String.format("%s.%s,", "c2", "GT_ID"));
        if (gatherTableDefine.getOrderField() != null) {
            orderSql.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getOrderField())));
        }
        sqlBuilder.append((CharSequence)orderSql);
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        return sqlBuilder.toString();
    }

    private String buildGetParentInfoSql(GatherDataTable gatherDataTable, String gatherTempTable, String periodCode, DimensionValueSet sourceDimensions) throws SQLException {
        String filterSql;
        List<DataField> classifyFields;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlStr = new StringBuilder();
        DataField periodField = gatherTableDefine.getPeriodField();
        DataField bizOrderField = gatherTableDefine.getBizOrderField();
        sqlStr.append("select");
        sqlStr.append(String.format(" %s.%s,", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            sqlStr.append(String.format(" %s.%s,", "c1", this.queryFieldName(periodField)));
        }
        if (bizOrderField != null) {
            sqlStr.append(String.format("Min(%s.%s) as %s,", "c1", this.queryFieldName(bizOrderField), this.queryFieldName(bizOrderField)));
        }
        if ((classifyFields = gatherTableDefine.getClassifyFields()) != null && classifyFields.size() > 0) {
            for (DataField dataField : classifyFields) {
                sqlStr.append(String.format("%s.%s,", "c1", this.queryFieldName(dataField)));
            }
        }
        List<DataField> unClassifyFields = gatherTableDefine.getUnClassifyFields();
        if (!gatherTableDefine.isFixed() && unClassifyFields != null && unClassifyFields.size() > 0) {
            for (DataField unClassifyField : unClassifyFields) {
                sqlStr.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
            }
        }
        sqlStr.setLength(sqlStr.length() - 1);
        sqlStr.append(" from ");
        String string = this.getRealTableName(gatherDataTable);
        sqlStr.append(String.format("%s %s  ", string, "c1"));
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlStr, gatherTableDefine, periodCode);
        }
        sqlStr.append(" where ");
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            sqlStr.append(String.format("%s.%s='%s' and ", "c1", this.queryFieldName(periodField), periodCode));
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            this.buildWhereDimensions(sqlStr, dimFields, sourceDimensions, false, String.format("%s.", "c1"), null);
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, string, "c1")))) {
            sqlStr.append(" (").append(filterSql).append(") and ");
        }
        sqlStr.append(String.format(" %s.%s in ", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        sqlStr.append(String.format(" (select distinct %s  from %s %s where %s='%s')", "GT_PID", gatherTempTable, "c2", "EXECUTION_ID", this.executionId));
        sqlStr.append(" group by ");
        StringBuilder groupSql = new StringBuilder();
        groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
        if (periodField != null) {
            groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(periodField)));
        }
        if (classifyFields != null && classifyFields.size() > 0) {
            for (DataField classifyField : classifyFields) {
                groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(classifyField)));
            }
        }
        if (unClassifyFields != null && unClassifyFields.size() > 0) {
            for (DataField unClassifyField : unClassifyFields) {
                groupSql.append(String.format("%s.%s,", "c1", this.queryFieldName(unClassifyField)));
            }
        }
        groupSql.setLength(groupSql.length() - 1);
        boolean needOrder = this.checkNeedOrder(gatherTableDefine, classifyFields, unClassifyFields);
        if (needOrder) {
            sqlStr.append((CharSequence)groupSql).append(" order by ").append((CharSequence)groupSql);
        } else {
            sqlStr.append((CharSequence)groupSql);
        }
        return sqlStr.toString();
    }

    private Object buildTempGatherFieldsSql(List<DataField> gatherFields) {
        StringBuilder resultSql = new StringBuilder();
        String preStr = String.format("%s.", "c1");
        for (DataField fieldDefine : gatherFields) {
            String fieldName = this.queryFieldName(fieldDefine);
            resultSql.append(String.format("(%s%s) as %s", preStr, fieldName, fieldName)).append(",");
        }
        resultSql.setLength(resultSql.length() - 1);
        return resultSql.toString();
    }

    public void clearMinusZeroData(GatherDataTable gatherDataTable, String periodCode, DimensionValueSet targetDimension, List<String> minusMD) {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelRunInfo tableInfo = this.dataDefinitionsCache.getTableInfo(this.getRealTableName(gatherDataTable));
        if (tableInfo == null) {
            return;
        }
        List allFields = tableInfo.getAllFields();
        for (ColumnModelDefine columnModelDefine : allFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
            ColumnModelDefine periodCol = tableInfo.getFieldByName(this.queryFieldName(gatherTableDefine.getPeriodField()));
            queryModel.getColumnFilters().put(periodCol, periodCode);
        }
        if (targetDimension != null && targetDimension.size() > 0) {
            for (int index = 0; index < targetDimension.size(); ++index) {
                String dimName = targetDimension.getName(index);
                ColumnModelDefine dimField = tableInfo.getDimensionField(dimName);
                if (dimField == null) continue;
                queryModel.getColumnFilters().put(dimField, targetDimension.getValue(dimName));
            }
        }
        ColumnModelDefine unitCol = tableInfo.getFieldByName(this.queryFieldName(gatherTableDefine.getUnitField()));
        queryModel.getColumnFilters().put(unitCol, minusMD);
        List collect = gatherTableDefine.getGatherFields().stream().filter(e -> e.getDataFieldType() == DataFieldType.BIGDECIMAL || e.getDataFieldType() == DataFieldType.INTEGER).collect(Collectors.toList());
        for (DataField dataField : collect) {
            ColumnModelDefine fieldByName = tableInfo.getFieldByName(this.queryFieldName(dataField));
            if (fieldByName == null) continue;
            queryModel.getColumnFilters().put(fieldByName, 0);
        }
        INvwaDataAccessProvider dataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
        INvwaUpdatableDataAccess updatableDataAccess = dataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
            DataAccessContext context = new DataAccessContext(dataModelService);
            INvwaDataUpdator tableDataUpdator = updatableDataAccess.openForUpdate(context);
            tableDataUpdator.deleteAll();
            tableDataUpdator.commitChanges(context);
        }
        catch (Exception e2) {
            logger.error("\u5dee\u989d\u6c47\u603b\u5220\u96640\u884c\u5931\u8d25");
        }
    }

    private static /* synthetic */ void lambda$executeFixedTableGather$5(StringBuffer groupKey, HashMap rowData, String e) {
        groupKey.append((Object)(rowData.get(e) == null ? "" : rowData.get(e))).append(";");
    }

    private static class MathUtil {
        private MathUtil() {
        }

        private static void mathAdd(ColumnModelType fieldType, String fieldName, Map<String, Object> sumMap, Object value, Object minusValue) {
            if (fieldType == ColumnModelType.DOUBLE) {
                double newValue = Convert.toDouble((Object)value);
                if (minusValue != null) {
                    newValue *= Convert.toDouble((Object)minusValue);
                }
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    sumMap.put(fieldName, newValue + Convert.toDouble((Object)oldValue));
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.INTEGER) {
                long newValue = Math.round(((Number)value).doubleValue());
                if (minusValue != null) {
                    newValue *= Math.round(((Number)minusValue).doubleValue());
                }
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    sumMap.put(fieldName, Math.addExact(newValue, Math.round(((Number)oldValue).doubleValue())));
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.BIGDECIMAL) {
                BigDecimal newValue = new BigDecimal(value.toString());
                if (minusValue != null) {
                    BigDecimal decimalMinus = new BigDecimal(minusValue.toString());
                    newValue = newValue.multiply(decimalMinus);
                }
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    BigDecimal decimalOldValue = new BigDecimal(oldValue.toString());
                    sumMap.put(fieldName, newValue.add(decimalOldValue));
                } else {
                    sumMap.put(fieldName, newValue);
                }
            }
        }

        private static void mathCount(String fieldName, Map<String, Object> sumMap, Object value) {
            long newCount;
            long l = newCount = value == null ? 0L : Math.round(((Number)value).doubleValue());
            if (sumMap.containsKey(fieldName)) {
                Object oldCount = sumMap.get(fieldName);
                sumMap.put(fieldName, Math.round(((Number)oldCount).doubleValue()) + newCount);
            } else {
                sumMap.put(fieldName, newCount);
            }
        }

        private static void mathMax(ColumnModelType fieldType, String fieldName, Map<String, Object> sumMap, Object value) {
            if (fieldType == ColumnModelType.DOUBLE) {
                double newValue = Convert.toDouble((Object)value);
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    double compareValue = Math.max(newValue, Convert.toDouble((Object)oldValue));
                    sumMap.put(fieldName, compareValue);
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.INTEGER) {
                long newValue = Math.round(((Number)value).doubleValue());
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    long compareValue = Math.max(newValue, Math.round(((Number)oldValue).doubleValue()));
                    sumMap.put(fieldName, compareValue);
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.BIGDECIMAL) {
                BigDecimal newValue = new BigDecimal(((Number)value).doubleValue());
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    BigDecimal decimalOldValue = new BigDecimal(((Number)oldValue).doubleValue());
                    sumMap.put(fieldName, newValue.max(decimalOldValue));
                } else {
                    sumMap.put(fieldName, newValue);
                }
            }
        }

        private static void mathMin(ColumnModelType fieldType, String fieldName, Map<String, Object> sumMap, Object value) {
            if (fieldType == ColumnModelType.DOUBLE) {
                double newValue = Convert.toDouble((Object)value);
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    double compareValue = Math.min(newValue, Convert.toDouble((Object)oldValue));
                    sumMap.put(fieldName, compareValue);
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.INTEGER) {
                long newValue = Math.round(((Number)value).doubleValue());
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    long compareValue = Math.min(newValue, Math.round(((Number)oldValue).doubleValue()));
                    sumMap.put(fieldName, compareValue);
                } else {
                    sumMap.put(fieldName, newValue);
                }
            } else if (fieldType == ColumnModelType.BIGDECIMAL) {
                BigDecimal newValue = new BigDecimal(((Number)value).doubleValue());
                if (sumMap.containsKey(fieldName)) {
                    Object oldValue = sumMap.get(fieldName);
                    BigDecimal decimalOldValue = new BigDecimal(((Number)oldValue).doubleValue());
                    sumMap.put(fieldName, newValue.min(decimalOldValue));
                } else {
                    sumMap.put(fieldName, newValue);
                }
            }
        }
    }
}

