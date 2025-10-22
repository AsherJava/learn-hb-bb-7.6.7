/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException
 *  com.jiuqi.bi.adhoc.datasource.reader.DataField
 *  com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy
 *  com.jiuqi.bi.adhoc.datasource.reader.DataPage
 *  com.jiuqi.bi.adhoc.datasource.reader.DataQuery
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.bi.adhoc.datasource.reader.ICloseableReader
 *  com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader
 *  com.jiuqi.bi.adhoc.datasource.reader.IReadContext
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.adhoc.model.AggregationType
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.FieldType
 *  com.jiuqi.bi.adhoc.model.RelationInfo
 *  com.jiuqi.bi.adhoc.model.RelationMode
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.authority.AuthFailedException
 *  com.jiuqi.bi.query.filter.HierarchyDataMode
 *  com.jiuqi.bi.query.model.PageInfo
 *  com.jiuqi.bi.query.model.SortMode
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$QueryTableType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.query.QueryStatLeafHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableMap
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableFactory
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException;
import com.jiuqi.bi.adhoc.datasource.reader.DataField;
import com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy;
import com.jiuqi.bi.adhoc.datasource.reader.DataPage;
import com.jiuqi.bi.adhoc.datasource.reader.DataQuery;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.bi.adhoc.datasource.reader.ICloseableReader;
import com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader;
import com.jiuqi.bi.adhoc.datasource.reader.IReadContext;
import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.adhoc.model.AggregationType;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.FieldType;
import com.jiuqi.bi.adhoc.model.RelationInfo;
import com.jiuqi.bi.adhoc.model.RelationMode;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.authority.AuthFailedException;
import com.jiuqi.bi.query.filter.HierarchyDataMode;
import com.jiuqi.bi.query.model.PageInfo;
import com.jiuqi.bi.query.model.SortMode;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.query.QueryStatLeafHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.common.NRBqlConsts;
import com.jiuqi.nr.bql.dataengine.ICommonQuery;
import com.jiuqi.nr.bql.dataengine.IDataAccessProvider;
import com.jiuqi.nr.bql.dataengine.IGroupingQuery;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.bql.datasource.DataTableColumn;
import com.jiuqi.nr.bql.datasource.MappingMainDimTable;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import com.jiuqi.nr.bql.datasource.UnitChekMonitor;
import com.jiuqi.nr.bql.datasource.UnitFilter;
import com.jiuqi.nr.bql.datasource.UnitFilterExpInfo;
import com.jiuqi.nr.bql.datasource.parse.DataFieldNode;
import com.jiuqi.nr.bql.datasource.parse.ParseInfo;
import com.jiuqi.nr.bql.datasource.reader.EntityDimTableReader;
import com.jiuqi.nr.bql.datasource.reader.EntityOrderItem;
import com.jiuqi.nr.bql.datasource.reader.EntityRowComparator;
import com.jiuqi.nr.bql.datasource.reader.PeriodDimTableReader;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.intf.ITableDimensionAdapter;
import com.jiuqi.nr.bql.util.AccountQueryUtils;
import com.jiuqi.nr.bql.util.FilterUtils;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataTableMap;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.xlib.utils.StringUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class DataSchemeDataSourceReader
implements IDataSourceReader,
ICloseableReader {
    private IDataAccessProvider dataAccessProvider;
    private IDataDefinitionRuntimeController dataDefinitionController;
    private IRuntimeDataSchemeService dataSchemeService;
    private PeriodDimTableReader periodDimTableReader;
    private EntityDimTableReader entityDimTableReader;
    private DataSource dataSource;
    private UnitFilter unitfilter;
    private BaseDataClient baseDataClient;
    private ICalibreDataService calibreDataService;
    private IAdjustPeriodService adjustPeriodService;
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    private DBQueryExecutorProvider dbQueryExecutorProvider;
    private ComponentSet componentSet;
    private static final int MAX_IN_SIZE = 100;
    private static final int OPTION_MODEL1 = 7;
    private static final int OPTION_MODEL2 = 1;
    private int readOptions = -1;
    private String expandMode = NRBqlConsts.ExpandMode.DONOTHING.getCode();
    private String expandByDimensions = null;
    private List<ColumnModelDefine> groupBys = new ArrayList<ColumnModelDefine>();
    private boolean needGroupQuery = false;
    private boolean eliminateUnitDim = false;

    public DataSchemeDataSourceReader(ComponentSet componentSet) {
        this.dataAccessProvider = componentSet.dataAccessProvider;
        this.dataDefinitionController = componentSet.dataDefinitionController;
        this.dataSchemeService = componentSet.dataSchemeService;
        this.periodDimTableReader = componentSet.periodDimTableReader;
        this.entityDimTableReader = componentSet.entityDimTableReader;
        this.dataSource = componentSet.dataSource;
        this.unitfilter = componentSet.unitfilter;
        this.baseDataClient = componentSet.baseDataClient;
        this.calibreDataService = componentSet.calibreDataService;
        this.adjustPeriodService = componentSet.adjustPeriodService;
        this.definitionAuthorityProvider = componentSet.definitionAuthorityProvider;
        this.dbQueryExecutorProvider = componentSet.dbQueryExecutorProvider;
        this.componentSet = componentSet;
    }

    public long readQuery(IReadContext context, DataQuery dataQuery, IDataListener listener, DataPage dataPage) throws AdhocDataSourceException {
        try {
            Set noAuthFields;
            if (this.readOptions < 0) {
                this.getReadOptions(context, dataQuery);
            }
            if ((noAuthFields = (Set)context.getBuffer().get("zbAuthCache")) != null && noAuthFields.size() > 0) {
                StringBuilder msg = new StringBuilder("\u5b58\u5728\u65e0\u8bbf\u95ee\u6743\u9650\u7684\u6307\u6807: ");
                HashMap noAuthMap = new HashMap();
                msg.append("[");
                noAuthFields.forEach(fieldKey -> {
                    com.jiuqi.nr.datascheme.api.DataField dataField = this.dataSchemeService.getDataField(fieldKey);
                    com.jiuqi.nr.datascheme.api.DataTable schemeDataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey());
                    noAuthMap.put(schemeDataTable.getCode() + "." + dataField.getCode(), dataField.getTitle());
                    msg.append(dataField.getTitle()).append(",");
                });
                msg.setLength(msg.length() - 1);
                msg.append("]");
                throw new AuthFailedException(msg.toString(), null, noAuthMap);
            }
            ExecutorContext exeContext = new ExecutorContext(this.dataDefinitionController);
            Metadata metadata = new Metadata();
            ArrayList<QueryColumnInfo> columnInfos = new ArrayList<QueryColumnInfo>();
            QueryContext qContext = new QueryContext(context, exeContext, null, dataQuery);
            qContext.setEliminateUnitDim(this.eliminateUnitDim);
            qContext.getLogger().debug("dataQuery:" + dataQuery);
            DataTable dataTable = dataQuery.getDataTable();
            TableInfo tableInfo = dataTable.getTable().getTable();
            String dsvName = this.getDsvName(dataQuery, tableInfo);
            qContext.initByDSV(dsvName, this.componentSet);
            qContext.initTimeKeyCache();
            if (tableInfo.getType() == TableType.DIM) {
                if (tableInfo.getName().equals(qContext.getUnitEntityDefine().getDimensionName())) {
                    DataTable periodDataTable = null;
                    DataTable mdInfoDataTable = null;
                    if (dataQuery.getRefTables() != null && !dataQuery.getRefTables().isEmpty()) {
                        for (DataTable refTable : dataQuery.getRefTables()) {
                            TableInfo refTableInfo = refTable.getTable().getTable();
                            if (this.periodDimTableReader.getPeriodEntityAdapter().isPeriodEntity(refTableInfo.getGuid())) {
                                periodDataTable = refTable;
                                continue;
                            }
                            if (qContext.getMdInfoTableModelRunInfo() == null || !qContext.getMdInfoTableModelRunInfo().getTableModelDefine().getCode().equals(refTableInfo.getName())) continue;
                            mdInfoDataTable = refTable;
                        }
                    } else {
                        DimensionValueSet masterKeys = new DimensionValueSet();
                        return this.entityDimTableReader.queryEntityDimTable(qContext, dataTable, listener, dataPage, masterKeys, null);
                    }
                    if (periodDataTable != null) {
                        if (mdInfoDataTable == null) {
                            DimensionValueSet masterKeys = new DimensionValueSet();
                            this.periodDimTableReader.periodFilterToMasterKeys(qContext, masterKeys, periodDataTable);
                            return this.entityDimTableReader.queryEntityDimTable(qContext, dataTable, listener, dataPage, masterKeys, periodDataTable);
                        }
                        DataQuery newDataQuery = dataQuery.clone();
                        newDataQuery.setDataTable(mdInfoDataTable);
                        for (int i = newDataQuery.getRefTables().size() - 1; i >= 0; --i) {
                            DataTable refTable = (DataTable)newDataQuery.getRefTables().get(i);
                            if (!refTable.getTable().getTableName().equals(mdInfoDataTable.getTable().getTableName())) continue;
                            newDataQuery.getRefTables().remove(i);
                        }
                        newDataQuery.getRefTables().add(dataTable);
                        return this.readQuery(context, newDataQuery, listener, dataPage);
                    }
                } else {
                    if (dataTable.getTable().getTableName().startsWith(NrPeriodConst.PREFIX_CODE)) {
                        return this.periodDimTableReader.queryPeriodDimTable(context, dataTable, listener, dataPage);
                    }
                    if (qContext.getMdInfoTableModelRunInfo() == null || !qContext.getMdInfoTableModelRunInfo().getTableModelDefine().getCode().equals(tableInfo.getName())) {
                        DimensionValueSet masterKeys = new DimensionValueSet();
                        return this.entityDimTableReader.queryEntityDimTable(qContext, dataTable, listener, dataPage, masterKeys, null);
                    }
                }
            }
            this.analysisDataQuery(dataQuery, qContext);
            ICommonQuery request = this.createQueryRequest(qContext, listener, dataQuery, dataPage, (Metadata<ColumnInfo>)metadata, columnInfos);
            this.doQuery(qContext, request);
            int totalCount = request.getReader().getTotalCount();
            if (dataPage != null && dataPage.getPageInfo() != null && PageInfo.isPageable((PageInfo)dataPage.getPageInfo())) {
                dataPage.getPageInfo().setRecordSize(request.getReader().getTotalCount());
            }
            return totalCount;
        }
        catch (AuthFailedException afe) {
            throw afe;
        }
        catch (Exception e) {
            throw new AdhocDataSourceException(e.getMessage(), (Throwable)e);
        }
    }

    private String getDsvName(DataQuery dataQuery, TableInfo tableInfo) {
        String dsvName = tableInfo.getDsvName();
        if (tableInfo.getType() == TableType.DIM && tableInfo.getRelations().isEmpty()) {
            for (DataTable refTable : dataQuery.getRefTables()) {
                TableInfo refTableInfo = refTable.getTable().getTable();
                if (refTableInfo.getRelations().size() <= 0) continue;
                dsvName = refTableInfo.getDsvName();
            }
        }
        return dsvName;
    }

    public int getReadOptions(IReadContext context, DataQuery dataQuery) throws AdhocDataSourceException {
        if (this.readOptions > 0) {
            return this.readOptions;
        }
        if (context.getOptions().containsKey("expandMode")) {
            this.expandMode = (String)context.getOptions().get("expandMode");
        } else {
            String cfg_fullMasterDimMode = (String)context.getOptions().get("NR.fullMasterDimMode");
            if ("true".equals(cfg_fullMasterDimMode)) {
                this.expandMode = NRBqlConsts.ExpandMode.SHOW_ALLNULL.getCode();
            }
        }
        this.expandByDimensions = (String)context.getOptions().get("expandByDimensions");
        if (this.expandMode.equals(NRBqlConsts.ExpandMode.HIDE_ALLNULL.getCode()) || this.expandMode.equals(NRBqlConsts.ExpandMode.HIDE_ALLZERO.getCode())) {
            this.expandByDimensions = null;
        }
        if (NRBqlConsts.ExpandMode.SHOW_ALLNULL.getCode().equals(this.expandMode) || StringUtils.isNotEmpty((String)this.expandByDimensions)) {
            List refTables = dataQuery.getRefTables();
            for (DataTable refTable : refTables) {
                IEntityDefine entityDefine = this.entityDimTableReader.getEntityDefineByTable(null, refTable);
                if (entityDefine == null || entityDefine.getIsolation() <= 0) continue;
                this.expandMode = NRBqlConsts.ExpandMode.DONOTHING.getCode();
                this.expandByDimensions = null;
                break;
            }
        }
        if (!NRBqlConsts.ExpandMode.DONOTHING.getCode().equals(this.expandMode) || StringUtils.isNotEmpty((String)this.expandByDimensions)) {
            this.readOptions = 1;
            return this.readOptions;
        }
        if (dataQuery.getDataTable().getTable().getTable().getType() == TableType.DIM) {
            this.readOptions = 1;
            return this.readOptions;
        }
        try {
            DataTable dataTable = dataQuery.getDataTable();
            TableInfo table = dataTable.getTable().getTable();
            String dsvName = this.getDsvName(dataQuery, table);
            ExecutorContext exeContext = new ExecutorContext(this.dataDefinitionController);
            QueryContext qContext = new QueryContext(context, exeContext, null, dataQuery);
            qContext.initByDSV(dsvName, this.componentSet);
            if (qContext.getParentTableModelRunInfo() != null) {
                this.readOptions = 1;
                return this.readOptions;
            }
            if (exeContext.getEnv().getUnitLeafFinder() != null) {
                this.readOptions = 1;
                return this.readOptions;
            }
            DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
            boolean hasMultipPhysicalTable = false;
            if (table.getType() == TableType.DATA) {
                if (table.getPropMap().containsKey("NR.dataTableMap") && qContext.getConnectionProvider() == null) {
                    this.readOptions = 1;
                    return this.readOptions;
                }
                boolean queryDetail = false;
                DimensionSet openDimensions = new DimensionSet();
                TableModelRunInfo tableRunInfo = qContext.getMainTableModelRunInfo();
                List dimFields = tableRunInfo.getDimFields();
                HashMap<String, ColumnModelDefine> keyFieldCodes = new HashMap<String, ColumnModelDefine>();
                for (ColumnModelDefine dimField : dimFields) {
                    keyFieldCodes.put(dimField.getCode(), dimField);
                }
                ArrayList<String> fieldsToJudgeAuth = new ArrayList<String>();
                com.jiuqi.nr.datascheme.api.DataTable schemeDataTable = this.dataSchemeService.getDataTableByCode(dataTable.getTable().getTableName());
                for (DataField dataField : dataTable.getFields()) {
                    if (!dataField.isVisible()) continue;
                    if (dataField.getField().isTimeKey() || dataField.getField().getName().equals("DATATIME")) {
                        openDimensions.addDimension("DATATIME");
                        continue;
                    }
                    String fieldCode = dataField.getField().getName();
                    if (fieldCode.equals("BIZKEYORDER")) {
                        queryDetail = true;
                        continue;
                    }
                    ColumnModelDefine dimField = (ColumnModelDefine)keyFieldCodes.get(fieldCode);
                    if (dimField != null) {
                        openDimensions.addDimension(tableRunInfo.getDimensionName(dimField.getCode()));
                        continue;
                    }
                    com.jiuqi.nr.datascheme.api.DataField schemeDataField = this.dataSchemeService.getDataFieldByTableKeyAndCode(schemeDataTable.getKey(), dataField.getField().getName());
                    fieldsToJudgeAuth.add(schemeDataField.getKey());
                }
                Set canReadFields = this.definitionAuthorityProvider.canReadFields(fieldsToJudgeAuth);
                if (canReadFields.size() < fieldsToJudgeAuth.size()) {
                    fieldsToJudgeAuth.removeAll(canReadFields);
                    HashSet noAuthFields = (HashSet)qContext.getReadContext().getBuffer().get("zbAuthCache");
                    if (noAuthFields == null) {
                        noAuthFields = new HashSet();
                        qContext.getReadContext().getBuffer().put("zbAuthCache", noAuthFields);
                    }
                    noAuthFields.addAll(fieldsToJudgeAuth);
                }
                if (hasMultipPhysicalTable) {
                    this.readOptions = 1;
                    return this.readOptions;
                }
                if (dataQuery.getRefTables() != null) {
                    for (DataTable refTable : dataQuery.getRefTables()) {
                        String refTableName = refTable.getTable().getTableName();
                        if (openDimensions.contains(refTableName) || queryDetail) continue;
                        boolean canEliminate = true;
                        if (refTableName.startsWith(NrPeriodConst.PREFIX_CODE)) {
                            for (DataField dataField : refTable.getFields()) {
                                if (!dataField.isVisible() || !dataField.getField().isTimeKey() && !dataField.getField().getName().equals(PeriodTableColumn.CODE.getCode())) continue;
                                canEliminate = false;
                            }
                        } else {
                            for (DataField dataField : refTable.getFields()) {
                                FieldInfo keyFieldInfo;
                                if (!dataField.isVisible()) continue;
                                if (dataField.getField().isKey()) {
                                    canEliminate = false;
                                    continue;
                                }
                                String keyField = dataField.getField().getKeyField();
                                if (keyField == null || keyField.equals(dataField.getField().getName()) || (keyFieldInfo = refTable.getTable().getTable().findField(keyField)) == null || !keyFieldInfo.isKey()) continue;
                                canEliminate = false;
                            }
                        }
                        if (!canEliminate) continue;
                        this.readOptions = 1;
                        return this.readOptions;
                    }
                }
            }
            if (dataQuery.requireGroupBy()) {
                this.needGroupQuery = true;
                this.parseGroupBy(dataQuery, qContext);
                if (!this.needGroupQuery) {
                    this.readOptions = 1;
                    return this.readOptions;
                }
                HashMap<String, DataField> fieldMap = new HashMap<String, DataField>();
                for (DataField field : dataQuery.getDataTable().getFields()) {
                    DataField sameField = (DataField)fieldMap.get(field.getField().getName());
                    if (sameField != null) {
                        if (sameField.getAggregationType() == null || field.getAggregationType() == null || sameField.getAggregationType() == field.getAggregationType()) continue;
                        qContext.getLogger().debug("\u5b58\u5728\u5bf9\u540c\u4e00\u5b57\u6bb5\u7684\u591a\u4e2a\u805a\u5408\u65b9\u5f0f\uff0c\u4ea4\u7ed9bql\u5f15\u64ce\u5904\u7406");
                        this.readOptions = 1;
                        this.groupBys.clear();
                        this.needGroupQuery = false;
                        return this.readOptions;
                    }
                    fieldMap.put(field.getField().getName(), field);
                }
                List dimFields = qContext.getMainTableModelRunInfo().getDimFields();
                List eliminateDimFields = dimFields.stream().filter(f -> !this.groupBys.contains(f)).collect(Collectors.toList());
                for (ColumnModelDefine dimColumn : eliminateDimFields) {
                    String dimension = qContext.getMainTableModelRunInfo().getDimensionName(dimColumn.getCode());
                    TableNode refTable = qContext.getTableGraph().findTable(dimension);
                    if (refTable == null || refTable.getTable().getHierarchies().isEmpty()) continue;
                    HierarchyDataMode hModel = HierarchyDataMode.ALL_LEVELS;
                    for (RelationInfo relation : table.getRelations()) {
                        if (!relation.getTargetTable().equals(refTable.getTableName()) || relation.getHierarchyDataMode() == null) continue;
                        hModel = relation.getHierarchyDataMode();
                    }
                    if (HierarchyDataMode.ALL_LEVELS != hModel) continue;
                    if (dimension.equals(qContext.getUnitEntityDefine().getDimensionName())) {
                        this.eliminateUnitDim = true;
                        continue;
                    }
                    qContext.getLogger().debug(dimension + "\u4e0d\u652f\u6301\u5c42\u7ea7\u6c47\u603b");
                    this.readOptions = 1;
                    this.groupBys.clear();
                    this.needGroupQuery = false;
                    return this.readOptions;
                }
            }
        }
        catch (Exception e) {
            throw new AdhocDataSourceException(e.getMessage(), (Throwable)e);
        }
        this.readOptions = 7;
        return this.readOptions;
    }

    private void analysisDataQuery(DataQuery dataQuery, QueryContext qContext) throws ParseException, DataSetException {
        if (dataQuery.getRefTables() != null) {
            for (DataTable refTable : dataQuery.getRefTables()) {
                for (DataField dataField : refTable.getFields()) {
                    if (!dataField.getField().getName().equals("H_ORDER")) continue;
                    qContext.setNeedHOrder(refTable.getTable().getTableName());
                }
            }
        }
    }

    private void parseGroupBy(DataQuery dataQuery, QueryContext qContext) throws ParseException {
        DataTable dataTable = dataQuery.getDataTable();
        boolean queryDetail = false;
        DimensionSet openDimensions = new DimensionSet();
        DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = qContext.getMainTableModelRunInfo();
        if (tableRunInfo == null) {
            return;
        }
        List dimFields = tableRunInfo.getDimFields();
        HashMap<String, ColumnModelDefine> keyFieldCodes = new HashMap<String, ColumnModelDefine>();
        for (ColumnModelDefine dimField : dimFields) {
            keyFieldCodes.put(dimField.getCode(), dimField);
        }
        for (Object dataField : dataTable.getFields()) {
            if (dataField.getAggregationType() != null) continue;
            if (!dataField.isVisible()) {
                ColumnModelDefine field;
                if (dataField.getValues() == null || dataField.getValues().size() != 1 || (field = tableRunInfo.parseSearchField(dataField.getField().getName())) == null || !tableRunInfo.isKeyField(field.getCode())) continue;
                openDimensions.addDimension(tableRunInfo.getDimensionName(field.getCode()));
                continue;
            }
            if (dataField.getField().isTimeKey() || dataField.getField().getName().equals("DATATIME")) {
                openDimensions.addDimension("DATATIME");
                continue;
            }
            String fieldCode = dataField.getField().getName();
            if (fieldCode.equals("BIZKEYORDER")) {
                queryDetail = true;
                break;
            }
            ColumnModelDefine dimField = (ColumnModelDefine)keyFieldCodes.get(fieldCode);
            if (dimField == null) continue;
            openDimensions.addDimension(tableRunInfo.getDimensionName(dimField.getCode()));
        }
        DataTable timeDimTable = null;
        if (dataQuery.getRefTables() != null) {
            for (DataTable refTable : dataQuery.getRefTables()) {
                int dimensionIndex;
                if (openDimensions == null || queryDetail) continue;
                String refTableName = refTable.getTable().getTableName();
                boolean showDim = false;
                boolean hasSingleValueFilter = false;
                boolean showKeyField = false;
                for (DataField dataField : refTable.getFields()) {
                    if (dataField.getAggregationType() != null) continue;
                    if (dataField.getValues() != null && dataField.getValues().size() == 1) {
                        hasSingleValueFilter = true;
                    }
                    if (!dataField.isVisible()) continue;
                    showDim = true;
                    if (!dataField.getField().isKey()) continue;
                    showKeyField = true;
                }
                if (!showDim && hasSingleValueFilter) {
                    showDim = true;
                }
                if (!showDim) continue;
                if (refTableName.startsWith(NrPeriodConst.PREFIX_CODE)) {
                    openDimensions.addDimension("DATATIME");
                    timeDimTable = refTable;
                    continue;
                }
                if (!showKeyField) {
                    return;
                }
                String dimensionName = refTableName;
                IEntityDefine entityDefine = this.entityDimTableReader.getEntityDefineByTable(qContext, refTable);
                if (entityDefine != null && !entityDefine.getDimensionName().equals(refTableName) && (dimensionIndex = refTableName.indexOf(entityDefine.getDimensionName())) > 0) {
                    dimensionName = refTableName.substring(0, dimensionIndex - 1);
                }
                openDimensions.addDimension(dimensionName);
            }
        }
        if (openDimensions != null && openDimensions.size() < tableRunInfo.getDimensions().size() && !queryDetail) {
            for (int i = 0; i < openDimensions.size(); ++i) {
                ColumnModelDefine groupbyField;
                String dimension = openDimensions.get(i);
                if (dimension.equals("DATATIME")) {
                    if (qContext.getMainQueryTableType() == DataEngineConsts.QueryTableType.ACCOUNT) continue;
                    if (timeDimTable != null) {
                        TableModelRunInfo timeDimTableRunInfo = this.getTableModelRunInfoByDataTable(timeDimTable, dataDefinitionsCache);
                        for (DataField dataField : timeDimTable.getFields()) {
                            ColumnModelDefine groupbyField2;
                            if (!dataField.isVisible() || (groupbyField2 = timeDimTableRunInfo.parseSearchField(dataField.getField().getName())) == null) continue;
                            this.groupBys.add(groupbyField2);
                        }
                        continue;
                    }
                    groupbyField = tableRunInfo.getDimensionField(dimension);
                    if (groupbyField == null) continue;
                    this.groupBys.add(groupbyField);
                    continue;
                }
                groupbyField = tableRunInfo.getDimensionField(dimension);
                if (groupbyField == null) continue;
                this.groupBys.add(groupbyField);
            }
        }
    }

    private ICommonQuery createQueryRequest(QueryContext qContext, IDataListener listener, DataQuery dataQuery, DataPage dataPage, Metadata<ColumnInfo> metadata, List<QueryColumnInfo> columnInfos) throws Exception {
        boolean rightJoinDimTable;
        ICommonQuery request = null;
        IGroupingQuery groupingQueryRequest = null;
        if (!this.needGroupQuery) {
            request = qContext.getMainQueryTableType() == DataEngineConsts.QueryTableType.ACCOUNT ? this.dataAccessProvider.newAccountDataQuery() : this.dataAccessProvider.newDataQuery();
        } else {
            groupingQueryRequest = qContext.getMainQueryTableType() == DataEngineConsts.QueryTableType.ACCOUNT ? this.dataAccessProvider.newAccountGroupingQuery() : this.dataAccessProvider.newGroupingQuery();
            for (ColumnModelDefine field : this.groupBys) {
                groupingQueryRequest.addGroupColumn(field);
            }
            groupingQueryRequest.setWantDetail(false);
            request = groupingQueryRequest;
        }
        request.setDBQueryExecutorProvider(this.dbQueryExecutorProvider);
        request.setIgnoreDefaultOrderBy(true);
        request.setOption("orgType", qContext.getUnitEntityDefine().getCode());
        request.setDefaultGroupName(dataQuery.getDataTable().getTable().getTableName());
        MappingMainDimTable mappingMainDimTable = qContext.getMappingMainDimTable();
        if (mappingMainDimTable != null) {
            request.setOption("MappingMainDimTable_" + qContext.getUnitEntityDefine().getCode(), mappingMainDimTable);
        }
        DimensionValueSet masterKeys = new DimensionValueSet();
        StringBuilder rowFilter = new StringBuilder();
        DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        DataTable dataTable = dataQuery.getDataTable();
        qContext.getLogger().debug("\u5f00\u59cb\u5206\u6790\u6570\u636e\u8868\uff1a" + dataTable);
        qContext.getTableInfoMap().put(dataTable.getTable().getTableName(), dataTable.getTable().getTable());
        this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, dataTable, null, true);
        String mdInfoFilter = null;
        DataTable mdInfoTable = null;
        if (rowFilter.length() > 0 && qContext.getMdInfoTableModelRunInfo() != null && dataTable.getTable().getTableName().equals(qContext.getMdInfoTableModelRunInfo().getTableModelDefine().getCode())) {
            mdInfoFilter = rowFilter.toString();
            rowFilter.setLength(0);
        }
        ArrayList<DataTable> entityDimTables = new ArrayList<DataTable>();
        if (dataQuery.getRefTables() != null) {
            for (DataTable refTable : dataQuery.getRefTables()) {
                qContext.getTableInfoMap().put(refTable.getTable().getTableName(), refTable.getTable().getTable());
                if (!this.periodDimTableReader.getPeriodEntityAdapter().isPeriodEntity(refTable.getTable().getTable().getGuid())) {
                    if (qContext.getMdInfoTableModelRunInfo() != null && refTable.getTable().getTable().getName().equals(qContext.getMdInfoTableModelRunInfo().getTableModelDefine().getCode())) {
                        mdInfoTable = refTable;
                        continue;
                    }
                    if (qContext.getParentTableModelRunInfo() != null && refTable.getTable().getTable().getName().equals(qContext.getParentTableModelRunInfo().getTableModelDefine().getCode())) {
                        String parentTableFilter = this.parseDataTableFilter(qContext, masterKeys, refTable);
                        if (StringUtils.isNotEmpty((String)parentTableFilter)) {
                            FilterUtils.appendFilter(rowFilter, parentTableFilter);
                        }
                        this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, refTable, null, false);
                        continue;
                    }
                    entityDimTables.add(refTable);
                    continue;
                }
                qContext.getLogger().debug("\u5f00\u59cb\u5206\u6790\u65f6\u671f\u7ef4\u5ea6\u8868\uff1a" + refTable);
                this.periodDimTableReader.periodFilterToMasterKeys(qContext, masterKeys, refTable);
                this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, refTable, null, false);
            }
        }
        if (!masterKeys.hasValue("DATATIME")) {
            for (DataField field : dataTable.getFields()) {
                FieldInfo fieldInfo = field.getField();
                if (fieldInfo.getFieldType() != FieldType.TIME_DIM || !fieldInfo.isTimeKey()) continue;
                String fieldFilter = field.getFilter();
                if (StringUtils.isNotEmpty((String)fieldFilter)) {
                    IExpression parseFilterFormula = FilterUtils.parseFilterFormula(fieldFilter, qContext);
                    TableInfo periodTableInfo = null;
                    TableInfo tableInfo = dataTable.getTable().getTable();
                    if (tableInfo.getName().startsWith(NrPeriodConst.PREFIX_CODE)) {
                        periodTableInfo = tableInfo;
                    } else {
                        for (IASTNode node : parseFilterFormula) {
                            if (!(node instanceof DataFieldNode)) continue;
                            DataFieldNode dataFieldNode = (DataFieldNode)node;
                            for (RelationInfo r : tableInfo.getRelations()) {
                                if (!r.getTargetTable().startsWith(NrPeriodConst.PREFIX_CODE)) continue;
                                dataFieldNode.setTableCode(r.getTargetTable());
                                periodTableInfo = ((TableNode)qContext.getReadContext().getCache().findTable(r.getTargetTable()).get(0)).getTable();
                                dataFieldNode.setFieldInfo(periodTableInfo.getKeyField());
                            }
                        }
                    }
                    ParseInfo parseInfo = new ParseInfo();
                    parseInfo.setBiSyntax(true);
                    String periodDimFilter = parseFilterFormula.interpret((IContext)qContext, Language.FORMULA, (Object)parseInfo);
                    qContext.getLogger().debug("TIMEKEY fieldFilter '" + fieldFilter + "' to pierodDimFilter '" + periodDimFilter + "'");
                    this.periodDimTableReader.periodFilterToMasterKeys(qContext, masterKeys, periodDimFilter, periodTableInfo);
                    continue;
                }
                if (field.getValues() == null || field.getValues().size() <= 0) continue;
                ArrayList periods = new ArrayList();
                field.getValues().forEach(o -> periods.add(qContext.getDatatimeByTimeKey(o.toString())));
                qContext.getLogger().debug("TIMEKEY values  to pierodDimValues");
                masterKeys.setValue("DATATIME", periods);
            }
        }
        for (DataTable refTable : entityDimTables) {
            qContext.getLogger().debug("\u5f00\u59cb\u5206\u6790\u7ef4\u5ea6\u8868\uff1a" + refTable);
            if (refTable.getTable().getTableName().equals("ADJUST")) {
                for (Object field : refTable.getFields()) {
                    QueryColumnInfo info;
                    Column column;
                    int index;
                    if (field.getField().getName().equals("CODE")) {
                        if (field.getValues() != null && field.getValues().size() > 0) {
                            masterKeys.setValue("ADJUST", (Object)field.getValues());
                            qContext.getLogger().debug("\u7ef4\u5ea6\u8868\u5b57\u6bb5" + field.getField().getName() + "\u7684\u53d6\u503c\u5217\u8868 " + field.getValues() + "\u8f6c\u6362\u4e3a\u4e3b\u7ef4\u5ea6" + "ADJUST" + "\u53d6\u503c\u8303\u56f4");
                        }
                        if (!field.isVisible()) continue;
                        index = request.addExpressionColumn("[ADJUST]");
                        column = new Column(field.getReturnName(), field.getField().getDataType());
                        metadata.addColumn(column);
                        info = new QueryColumnInfo((Column<ColumnInfo>)column, index);
                        columnInfos.add(info);
                        continue;
                    }
                    if (!field.isVisible()) continue;
                    index = request.addExpressionColumn("[ADJUST_TITLE]");
                    column = new Column(field.getReturnName(), field.getField().getDataType());
                    metadata.addColumn(column);
                    info = new QueryColumnInfo((Column<ColumnInfo>)column, index);
                    columnInfos.add(info);
                }
                continue;
            }
            IEntityDefine entityDefine = this.entityDimTableReader.getEntityDefineByTable(qContext, refTable);
            if (mdInfoTable != null && entityDefine.getDimensionName().equals(qContext.getUnitEntityDefine().getDimensionName())) {
                mdInfoFilter = this.parseDataTableFilter(qContext, masterKeys, mdInfoTable);
                this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, mdInfoTable, null, false);
            }
            this.entityDimTableReader.processEntityFieldFilter(qContext, masterKeys, refTable, mdInfoFilter);
            this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, refTable, entityDefine, false);
        }
        if (mdInfoTable != null && qContext.getMdTable() == null) {
            mdInfoFilter = this.parseDataTableFilter(qContext, masterKeys, mdInfoTable);
            if (StringUtils.isNotEmpty((String)mdInfoFilter)) {
                FilterUtils.appendFilter(rowFilter, mdInfoFilter);
            }
            this.setRequestByTable(qContext, metadata, columnInfos, request, masterKeys, rowFilter, dataDefinitionsCache, mdInfoTable, null, false);
        }
        this.processDefaultDimValues(qContext, dataQuery, masterKeys);
        this.filterUnits(qContext, masterKeys);
        for (int i = 0; i < masterKeys.size(); ++i) {
            String dimName = masterKeys.getName(i);
            Object dimValues = masterKeys.getValue(i);
            if (!(dimValues instanceof List)) continue;
            if (qContext.isUnitDim(dimName) && qContext.isEliminateUnitDim() && qContext.getAuthUnitEntityRows() != null) {
                qContext.getLogger().debug("\u4e3b\u7ef4\u5ea6\u6d88\u7ef4\uff0c\u6309\u5c42\u7ea7\u6c47\u603b,\u53bb\u9664\u53ef\u80fd\u5bfc\u81f4\u91cd\u590d\u7d2f\u52a0\u7684\u5355\u4f4d");
                this.removeSurplusUnits(qContext, dimValues);
            }
            qContext.putDimValuesToCache(masterKeys.getName(i), (List)dimValues);
        }
        StringBuilder filter = this.getRequestFilter(qContext, dataQuery, request, rowFilter, dataTable);
        boolean bl = rightJoinDimTable = NRBqlConsts.ExpandMode.SHOW_ALLNULL.getCode().equals(this.expandMode) || StringUtils.isNotEmpty((String)this.expandByDimensions);
        if (filter != null && filter.length() > 0) {
            request.setRowFilter(filter.toString());
        } else {
            this.setShowAllNullToRequest(qContext, dataQuery, request, rightJoinDimTable);
            this.setAdjustMapOption(qContext, request, rightJoinDimTable, masterKeys);
        }
        this.processNoSummaryDims(qContext, masterKeys);
        this.processCurrencyDim(qContext, dataQuery, masterKeys);
        List<DimQueryInfo> dimQueryInfos = qContext.getDimQueryInfos();
        if (dimQueryInfos != null) {
            for (DimQueryInfo dimQueryInfo : dimQueryInfos) {
                masterKeys.clearValue(dimQueryInfo.getDimension());
            }
            request.setOption("DimQueryInfos", dimQueryInfos);
        }
        if (qContext.getMdInfoTableModelRunInfo() != null) {
            request.setOption("MDInfo_Table", qContext.getMdInfoTableModelRunInfo());
        }
        if (qContext.getParentTableRelation() != null) {
            request.setOption("parnetTableRelation", qContext.getParentTableRelation());
            request.setMainTable(qContext.getMainTableModelRunInfo().getTableModelDefine().getName());
        }
        this.setPageInfoToRequest(qContext, dataQuery, dataPage, request, masterKeys);
        request.setMasterKeys(masterKeys);
        if (rightJoinDimTable) {
            if (StringUtils.isNotEmpty((String)this.expandByDimensions)) {
                String[] dimNames;
                for (String dimName : dimNames = this.expandByDimensions.split(";")) {
                    if (!masterKeys.hasValue(dimName)) continue;
                    request.addExpandDimValues(dimName, masterKeys.getValue(dimName));
                }
                request.setOption("dynamicExpand", "true");
            }
            if (NRBqlConsts.ExpandMode.SHOW_ALLNULL.getCode().equals(this.expandMode)) {
                request.setOption("dynamicExpand", "false");
                for (int i = 0; i < masterKeys.size(); ++i) {
                    String dimName = masterKeys.getName(i);
                    if (!qContext.getSchemeDimNames().contains(dimName)) continue;
                    request.addExpandDimValues(dimName, masterKeys.getValue(i));
                }
            }
        }
        this.initAggrType(groupingQueryRequest);
        QueryDataReader reader = new QueryDataReader(qContext, listener, metadata, columnInfos, this.expandMode);
        request.setReader(reader);
        return request;
    }

    private void processNoSummaryDims(QueryContext qContext, DimensionValueSet masterKeys) {
        DimensionValueSet noSummaryDims;
        ITableDimensionAdapter tableDimensionAdapter = this.componentSet.tableDimensionAdapter;
        if (tableDimensionAdapter != null && (noSummaryDims = tableDimensionAdapter.getNoSummaryDims(qContext.getMainTable().getTable().getTableName())) != null) {
            for (int i = 0; i < noSummaryDims.size(); ++i) {
                String dimName = noSummaryDims.getName(i);
                if (masterKeys.hasValue(dimName) || qContext.getShowDims().contains(dimName)) continue;
                masterKeys.setValue(dimName, noSummaryDims.getValue(i));
            }
        }
    }

    private void removeSurplusUnits(QueryContext qContext, Object dimValues) {
        HashSet<String> topUnitKeys = new HashSet<String>();
        Set authUnitKeySet = qContext.getAuthUnitEntityRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
        for (IEntityRow unitRow : qContext.getAuthUnitEntityRows()) {
            String[] parents = unitRow.getParentsEntityKeyDataPath();
            if (parents != null && parents.length > 0) {
                boolean parentExsit = false;
                for (String parent : parents) {
                    if (!authUnitKeySet.contains(parent)) continue;
                    parentExsit = true;
                    break;
                }
                if (parentExsit) continue;
                topUnitKeys.add(unitRow.getEntityKeyData());
                continue;
            }
            topUnitKeys.add(unitRow.getEntityKeyData());
        }
        List authUnits = (List)dimValues;
        Iterator it = authUnits.iterator();
        while (it.hasNext()) {
            String unitKey = (String)it.next();
            if (topUnitKeys.contains(unitKey)) continue;
            it.remove();
        }
    }

    private StringBuilder getRequestFilter(QueryContext qContext, DataQuery dataQuery, ICommonQuery request, StringBuilder rowFilter, DataTable dataTable) throws ParseException, InterpretException, DataTableAdaptException {
        DataTableMap dataTableMap;
        if (StringUtils.isNotEmpty((String)dataQuery.getFilter())) {
            qContext.getLogger().debug("dataQueryFilter (" + dataQuery.getFilter() + ") append to rowFilter");
            FilterUtils.appendFilter(rowFilter, dataQuery.getFilter());
        }
        StringBuilder filter = new StringBuilder();
        if (rowFilter.length() > 0) {
            qContext.setDefaultTableName(dataTable.getTable().getTableName());
            String filterExp = FilterUtils.transFormula(qContext, rowFilter.toString(), null);
            filter.append(filterExp);
        }
        if ((dataTableMap = (DataTableMap)dataTable.getTable().getTable().getPropMap().get("NR.dataTableMap")) != null) {
            DataTableFactoryManager.getInstance().getFactory(dataTableMap.getSrcType());
            IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(dataTableMap.getSrcType());
            request.setOption("DataTableQueryExecutor", factory.createQueryExecuter(dataTableMap.getSrcKey()));
            com.jiuqi.nr.datascheme.api.DataTable schemeTable = this.dataSchemeService.getDataTable(dataTableMap.getTableKey());
            if (StringUtils.isNotEmpty((String)schemeTable.getExpression())) {
                FilterUtils.appendFilter(filter, schemeTable.getExpression());
            }
        }
        return filter;
    }

    private void initAggrType(IGroupingQuery groupingQueryRequest) {
        if (groupingQueryRequest != null) {
            for (int i = 0; i < groupingQueryRequest.getColumnSize(); ++i) {
                if (groupingQueryRequest.getGatherType(i) != AggrType.NONE) continue;
                groupingQueryRequest.setGatherType(i, AggrType.SUM);
            }
        }
    }

    private void setShowAllNullToRequest(QueryContext qContext, DataQuery dataQuery, ICommonQuery request, boolean rightJoinDimTable) throws JQException {
        if (rightJoinDimTable) {
            IEntityDefine unitEntityDefine = qContext.getUnitEntityDefine();
            request.addRightJoinDimTable(unitEntityDefine.getCode());
            String startTimeKey = (String)qContext.getReadContext().getOptions().get("startTimeKey");
            String endTimeKey = (String)qContext.getReadContext().getOptions().get("endTimeKey");
            if (startTimeKey != null) {
                request.setOption("startTimeKey", qContext.getDatatimeByTimeKey(startTimeKey));
            }
            if (endTimeKey != null) {
                request.setOption("endTimeKey", qContext.getDatatimeByTimeKey(endTimeKey));
            }
        }
    }

    private void setPageInfoToRequest(QueryContext qContext, DataQuery dataQuery, DataPage dataPage, ICommonQuery request, DimensionValueSet masterKeys) throws JQException {
        if (dataPage != null) {
            PageInfo pageInfo = dataPage.getPageInfo();
            if (pageInfo != null && PageInfo.isPageable((PageInfo)pageInfo) && this.readOptions == 7) {
                request.setPagingInfo(pageInfo.getPageSize(), pageInfo.getPageIndex() - 1);
            }
            List orderBys = dataPage.getOrderBys();
            ArrayList dimOrderItems = null;
            if (orderBys != null && orderBys.size() > 0) {
                for (DataOrderBy orderBy : orderBys) {
                    FieldInfo field = orderBy.getField();
                    if (field.getName().equals("H_ORDER") && field.getTableName().equals(qContext.getUnitEntityDefine().getDimensionName())) {
                        qContext.setOrderByUnitTree(true);
                    }
                    String exp = field.getPhysicalName();
                    if (field.getTableName().equals("ADJUST")) {
                        exp = dataQuery.getDataTable().getTable().getTableName() + "[" + "ADJUST" + "]";
                        continue;
                    }
                    if (exp.indexOf("[") < 0) {
                        DataTable table = dataQuery.findTable(field.getTableName());
                        if (field.getTableName().indexOf(qContext.getUnitEntityDefine().getDimensionName()) > 0) continue;
                        if (qContext.isJdbcQuery()) {
                            String tableCode = qContext.getTableCode(table);
                            exp = tableCode + "[" + field.getPhysicalName() + "]";
                            request.addOrderByItem(exp, orderBy.getMode() == SortMode.DESC);
                            continue;
                        }
                        TableInfo tableInfo = table.getTable().getTable();
                        TableModelRunInfo mainTableRunInfo = qContext.getMainTableModelRunInfo();
                        TableModelRunInfo parentTableRunInfo = qContext.getParentTableModelRunInfo();
                        String dimensionName = tableInfo.getName();
                        ColumnModelDefine dimField = mainTableRunInfo.getDimensionField(dimensionName);
                        DataTable mainTable = dataQuery.getDataTable();
                        TableInfo mainTableInfo = mainTable.getTable().getTable();
                        if (dimField == null) {
                            if (parentTableRunInfo != null && (dimField = parentTableRunInfo.getDimensionField(dimensionName)) == null) {
                                TableInfo parentTableInfo = qContext.getTableGraph().findTable(parentTableRunInfo.getTableModelDefine().getCode()).getTable();
                                for (RelationInfo rel : parentTableInfo.getRelations()) {
                                    if (!rel.getTargetTable().equals(parentTableInfo.getName())) continue;
                                    String dimFieldName = (String)rel.getFieldMaps().keySet().stream().findFirst().get();
                                    dimField = parentTableRunInfo.parseSearchField(dimFieldName);
                                    break;
                                }
                            }
                            if (dimField == null) {
                                for (RelationInfo rel : mainTableInfo.getRelations()) {
                                    if (!rel.getTargetTable().equals(tableInfo.getName())) continue;
                                    String dimFieldName = (String)rel.getFieldMaps().keySet().stream().findFirst().get();
                                    dimField = mainTableRunInfo.parseSearchField(dimFieldName);
                                    break;
                                }
                            }
                        }
                        if (field.isKey()) {
                            request.addOrderByItem(dimField, orderBy.getMode() == SortMode.DESC);
                            qContext.getLogger().debug("\u6392\u5e8f\u5b57\u6bb5 '" + field.getTableName() + "." + field.getName() + "' \u8f6c\u6362\u6210 " + exp);
                            continue;
                        }
                        if (field.getName().equals("H_ORDER")) {
                            Map<String, Integer> orderCache;
                            boolean isOnlyLeaf = false;
                            if (dimField != null) {
                                FieldInfo mainTableDimField = mainTableInfo.findField(dimField.getCode());
                                if (mainTableDimField == null && parentTableRunInfo != null) {
                                    TableInfo parentTableInfo = qContext.getTableGraph().findTable(parentTableRunInfo.getTableModelDefine().getCode()).getTable();
                                    mainTableDimField = parentTableInfo.findField(dimField.getCode());
                                }
                                if (mainTableDimField != null && mainTableDimField.getPropMap().containsKey("isOnlyLeaf")) {
                                    isOnlyLeaf = (Boolean)mainTableDimField.getPropMap().get("isOnlyLeaf");
                                }
                            }
                            if (isOnlyLeaf) {
                                exp = mainTableInfo.getName() + "[" + dimField.getCode() + "]";
                                request.addOrderByItem(exp, orderBy.getMode() == SortMode.DESC);
                                qContext.getLogger().debug("\u6392\u5e8f\u5b57\u6bb5 '" + field.getTableName() + "." + field.getName() + "' \u8f6c\u6362\u6210 " + exp);
                                continue;
                            }
                            request.addSpecifiedOrderByItem(dimField);
                            if (!masterKeys.hasValue(dimensionName) && (orderCache = qContext.getOrderCache(dimensionName)) != null) {
                                List dimValueList = orderCache.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
                                masterKeys.setValue(dimensionName, dimValueList);
                            }
                            qContext.getLogger().debug("\u6392\u5e8f\u5b57\u6bb5 '" + field.getTableName() + "." + field.getName() + "' \u8f6c\u6362\u6210 " + dimField.getCode() + " specified");
                            continue;
                        }
                        request.addSpecifiedOrderByItem(dimField);
                        if (!masterKeys.hasValue(dimensionName)) {
                            if (dimOrderItems == null) {
                                dimOrderItems = new ArrayList();
                            }
                            EntityOrderItem item = new EntityOrderItem();
                            item.column = dimField;
                            item.dimensionName = dimensionName;
                            item.entityDataTable = table;
                            item.orderBys.add(orderBy);
                        }
                        qContext.getLogger().debug("\u6392\u5e8f\u5b57\u6bb5 '" + field.getTableName() + "." + field.getName() + "' \u8f6c\u6362\u6210 " + dimField.getCode() + " specified");
                        continue;
                    }
                    if (!qContext.isJdbcQuery() && (field.isTimeKey() || field.getTableName().startsWith(NrPeriodConst.PREFIX_CODE))) {
                        exp = dataQuery.getDataTable().getTable().getTableName() + "[" + "DATATIME" + "]";
                    }
                    request.addOrderByItem(exp, orderBy.getMode() == SortMode.DESC);
                }
            }
            if (dimOrderItems != null) {
                for (EntityOrderItem item : dimOrderItems) {
                    IEntityDefine entityDefine = this.entityDimTableReader.getEntityDefineByTable(qContext, item.entityDataTable);
                    List<IEntityRow> entityRows = this.entityDimTableReader.getEntityRows(qContext, entityDefine, masterKeys, null, false, false);
                    List dimValueList = entityRows.stream().sorted(new EntityRowComparator(item.orderBys, null)).map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    masterKeys.setValue(item.dimensionName, dimValueList);
                }
            }
        }
    }

    private void processDefaultDimValues(QueryContext qContext, DataQuery dataQuery, DimensionValueSet masterKeys) {
        String dimDefaultValues = (String)qContext.getReadContext().getOptions().get("dimDefaultValue");
        if (dimDefaultValues == null || dimDefaultValues.length() == 0) {
            return;
        }
        TableInfo tableInfo = dataQuery.getDataTable().getTable().getTable();
        List relations = tableInfo.getRelations();
        if (qContext.getParentTableModelRunInfo() != null) {
            TableInfo parentTableInfo = qContext.getTableGraph().findTable(qContext.getParentTableModelRunInfo().getTableModelDefine().getCode()).getTable();
            relations.addAll(parentTableInfo.getRelations());
        }
        for (RelationInfo relation : relations) {
            int index;
            String dimName;
            if (relation.getMode() != RelationMode.DIM_REFERENCE) continue;
            String dimTableName = relation.getTargetTable();
            String dimFieldName = (String)relation.getFieldMaps().keySet().iterator().next();
            if (dimFieldName.equals(PeriodTableColumn.TIMEKEY.name())) {
                dimFieldName = "DATATIME";
            }
            if ((dimName = qContext.getMainTableModelRunInfo().getDimensionName(dimFieldName)) == null && qContext.getParentTableModelRunInfo() != null) {
                dimName = qContext.getParentTableModelRunInfo().getDimensionName(dimFieldName);
            }
            if (dimName == null || masterKeys.hasValue(dimName) || (index = dimDefaultValues.indexOf(dimTableName)) < 0) continue;
            String temp = dimDefaultValues.substring(index + dimTableName.length() + 2);
            if (!temp.endsWith(";")) {
                temp = temp + ";";
            }
            if ((index = temp.indexOf("\";")) < 0) continue;
            String valueStr = temp.substring(0, index);
            masterKeys.setValue(dimName, (Object)valueStr);
        }
    }

    protected void setAdjustMapOption(QueryContext qContext, ICommonQuery request, boolean cfg_fullMasterDimMode, DimensionValueSet masterKeys) {
        if (cfg_fullMasterDimMode && qContext.hasDimension("ADJUST") && !masterKeys.hasValue("ADJUST")) {
            HashMap<String, HashSet<String>> adjustMap = new HashMap<String, HashSet<String>>();
            List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(qContext.getDataScheme().getKey());
            for (AdjustPeriod adjustPeriod : adjustPeriods) {
                String period = adjustPeriod.getPeriod();
                HashSet<String> adjustSet = (HashSet<String>)adjustMap.get(period);
                if (adjustSet == null) {
                    adjustSet = new HashSet<String>();
                    adjustMap.put(period, adjustSet);
                    adjustSet.add("0");
                }
                adjustSet.add(adjustPeriod.getCode());
            }
            request.setOption("adjustMap", adjustMap);
        }
    }

    protected void processCurrencyDim(QueryContext qContext, DataQuery dataQuery, DimensionValueSet masterKeys) throws JQException {
        if (!masterKeys.hasValue("MD_CURRENCY") && qContext.hasDimension("MD_CURRENCY")) {
            masterKeys.setValue("MD_CURRENCY", (Object)"PROVIDER_BASECURRENCY");
        }
        if (masterKeys.hasValue("MD_CURRENCY")) {
            IEntityModel unitEntityModel = this.entityDimTableReader.getEntityMetaService().getEntityModel(qContext.getUnitEntityDefine().getId());
            if (unitEntityModel.getAttribute("CURRENCYID") == null) {
                return;
            }
            boolean queryBwb = false;
            Object value = masterKeys.getValue("MD_CURRENCY");
            ArrayList currencyValues = null;
            if (value.equals("PROVIDER_BASECURRENCY")) {
                queryBwb = true;
            } else if (value instanceof List) {
                List values = (List)value;
                for (Object v : values) {
                    if (v.equals("PROVIDER_BASECURRENCY")) {
                        queryBwb = true;
                        continue;
                    }
                    if (currencyValues == null) {
                        currencyValues = new ArrayList();
                    }
                    currencyValues.add(v);
                }
            }
            if (queryBwb) {
                DimQueryInfo dimQueryInfo = new DimQueryInfo("MD_CURRENCY");
                dimQueryInfo.setEntityId("MD_CURRENCY@BASE");
                dimQueryInfo.setRefTableName(qContext.getUnitEntityDefine().getCode());
                dimQueryInfo.setRefFieldName("CURRENCYID");
                dimQueryInfo.setValues(currencyValues);
                dimQueryInfo.setVariableValue("PROVIDER_BASECURRENCY");
                qContext.addDimQueryInfo(dimQueryInfo);
            }
        }
    }

    private void setRequestByTable(QueryContext qContext, Metadata<ColumnInfo> metadata, List<QueryColumnInfo> columnInfos, ICommonQuery request, DimensionValueSet masterKeys, StringBuilder rowFilter, DataModelDefinitionsCache dataDefinitionsCache, DataTable dataTable, IEntityDefine entityDefine, boolean isMainTable) throws Exception {
        TableModelRunInfo tableRunInfo = this.getTableModelRunInfoByDataTable(dataTable, dataDefinitionsCache);
        Set<String> groupByFieldNames = this.getGroupByFieldNames();
        if (groupByFieldNames != null && tableRunInfo.getTableType() == DataEngineConsts.QueryTableType.ACCOUNT) {
            groupByFieldNames.add(PeriodTableColumn.TIMEKEY.name());
        }
        IGroupingQuery groupingQueryRequest = null;
        if (request instanceof IGroupingQuery) {
            groupingQueryRequest = (IGroupingQuery)request;
        }
        boolean isMdInfoQuery = qContext.getMdInfoTableModelRunInfo() != null && dataTable.getTable().getTableName().equals(qContext.getMdInfoTableModelRunInfo().getTableModelDefine().getCode());
        for (DataField field : dataTable.getFields()) {
            FieldInfo fieldInfo = field.getField();
            int index = -1;
            String exp = fieldInfo.getPhysicalName();
            DataTableColumn dataTableColumn = null;
            if (!isMainTable) {
                if (entityDefine != null) {
                    String fieldDimensionName;
                    String dimTableName = dataTable.getTable().getTableName();
                    dataTableColumn = qContext.getDataTableColumnByDim(entityDefine.getDimensionName());
                    if (field.isVisible() && dataTableColumn != null && (fieldDimensionName = dataTableColumn.getDimensionName()) != null) {
                        qContext.getShowDims().addDimension(fieldDimensionName);
                    }
                    if (entityDefine.getDimensionName().equals(dimTableName)) {
                        if (qContext.isJdbcQuery() && qContext.getExeContext().getEnv().getUnitLeafFinder() == null) {
                            exp = entityDefine.getCode() + "[" + fieldInfo.getPhysicalName() + "]";
                            if (!qContext.getSchemeDimEntitys().contains(entityDefine.getId()) && fieldInfo.getName().equals("OBJECTCODE") && dataTableColumn != null) {
                                exp = dataTableColumn.toString();
                            }
                        } else {
                            String dimFieldExp;
                            String string = dimFieldExp = qContext.getSchemeDimEntitys().contains(entityDefine.getId()) ? "[" + entityDefine.getCode() + "_CODE]" : dataTableColumn.toString();
                            exp = fieldInfo.getName().equals("CODE") || fieldInfo.getName().equals("OBJECTCODE") ? dimFieldExp : "GetEntityField('" + entityDefine.getCode() + "'," + dimFieldExp + ",'" + fieldInfo.getPhysicalName() + "')";
                        }
                    } else {
                        int nameIndex = dimTableName.indexOf(entityDefine.getDimensionName());
                        if (nameIndex > 0) {
                            String dataKeyCode = dimTableName.substring(0, nameIndex - 1);
                            dataTableColumn = qContext.getDataTableColumnByCode(dataKeyCode);
                            String dimFieldExp = dataTableColumn.toString();
                            exp = dimFieldExp != null && fieldInfo.getName().equals("CODE") || fieldInfo.getName().equals("OBJECTCODE") ? dimFieldExp : "GetEntityField('" + entityDefine.getCode() + "'," + dimFieldExp + ",'" + fieldInfo.getPhysicalName() + "')";
                        }
                    }
                } else if ((!qContext.isJdbcQuery() || qContext.getExeContext().getEnv().getUnitLeafFinder() != null) && dataTable.getTable().getTableName().startsWith(NrPeriodConst.PREFIX_CODE)) {
                    if (fieldInfo.isTimeKey()) {
                        exp = "[CUR_TIMEKEY]";
                        qContext.getShowDims().addDimension("DATATIME");
                    } else {
                        exp = "GetPeriodField([CUR_PERIODSTR],'" + fieldInfo.getName() + "')";
                    }
                } else {
                    exp = fieldInfo.getPhysicalName();
                }
            } else if (fieldInfo.isTimeKey() && (!qContext.isJdbcQuery() || qContext.getExeContext().getEnv().getUnitLeafFinder() != null)) {
                exp = "[CUR_TIMEKEY]";
                qContext.getShowDims().addDimension("DATATIME");
            } else {
                String fieldDimensionName;
                dataTableColumn = qContext.getDataTableColumnByCode(fieldInfo.getName());
                if (field.isVisible() && dataTableColumn != null && (fieldDimensionName = dataTableColumn.getDimensionName()) != null) {
                    DataModelDefinitionsCache dataModelDefinitionsCache;
                    ColumnModelDefine refColumn;
                    String referColumnID;
                    ColumnModelDefine dataColumnModel = dataTableColumn.getDataColumnModel();
                    String dimName = dataTableColumn.getTableRunInfo().getDimensionName(dataColumnModel.getCode());
                    if (qContext.getSchemeDimNames().contains(dimName) && StringUtils.isNotEmpty((String)(referColumnID = dataColumnModel.getReferColumnID())) && !fieldInfo.getPropMap().containsKey("allowUndefinedCode") && (refColumn = (dataModelDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache()).findField(referColumnID)) != null) {
                        String dimTableName = dataModelDefinitionsCache.getTableName(refColumn);
                        exp = dimTableName + "_" + refColumn.getCode();
                    }
                    qContext.getShowDims().addDimension(fieldDimensionName);
                }
            }
            QueryColumnInfo info = null;
            if (field.isVisible()) {
                AggregationType aggregationType = field.getAggregationType();
                if (aggregationType == null) {
                    aggregationType = field.getField().getAggregationType();
                }
                boolean ignoreValue = false;
                if (isMainTable && groupByFieldNames != null && !groupByFieldNames.contains(fieldInfo.getName()) && !this.isNum(fieldInfo.getDataType()) && aggregationType != AggregationType.COUNT) {
                    exp = "\"\"";
                    ignoreValue = true;
                }
                index = request.addExpressionColumn(exp);
                int dataType = field.getField().getDataType();
                if (groupingQueryRequest != null && aggregationType == AggregationType.COUNT) {
                    dataType = 3;
                }
                Column column = new Column(field.getReturnName(), dataType);
                metadata.addColumn(column);
                info = new QueryColumnInfo((Column<ColumnInfo>)column, index);
                info.setDataColumnModel(dataTableColumn == null ? null : dataTableColumn.getDataColumnModel());
                info.setIgnoreValue(ignoreValue);
                if (entityDefine != null) {
                    IEntityModel entityModel = this.entityDimTableReader.getEntityMetaService().getEntityModel(entityDefine.getId());
                    info.setEntityDefine(entityDefine);
                    info.setBaseDataClient(this.baseDataClient);
                    if (fieldInfo.getName().equals("H_ORDER")) {
                        info.setHOrderField(true);
                        info.setOrderCache(qContext.getOrderCache(entityDefine.getDimensionName()));
                    } else if (fieldInfo.getName().equals("PARENTCODE")) {
                        info.setParentCode(true);
                    } else if (fieldInfo.getName().equals(entityModel.getBizKeyField().getCode())) {
                        info.setEntityKey(true);
                    } else if (qContext.isNeedResetDimTitle() && entityDefine.getDimensionName().equals(qContext.getUnitEntityDefine().getDimensionName()) && fieldInfo.getName().equals(entityModel.getNameField().getCode())) {
                        info.setResetMainDimTitle(true);
                    }
                    info.setRefDimensionName(entityDefine.getDimensionName());
                }
                if (groupingQueryRequest != null) {
                    if (isMainTable) {
                        AggrType aggrType = AggrType.SUM;
                        if (aggregationType != null) {
                            aggrType = this.transAggrType(aggregationType, dataType, field.getField().isDimension());
                        }
                        groupingQueryRequest.setGatherType(index, aggrType);
                    } else {
                        groupingQueryRequest.setGatherType(index, AggrType.MIN);
                    }
                    info.setAggregationType(aggregationType);
                }
                columnInfos.add(info);
            }
            if (!isMainTable) continue;
            if (info != null) {
                info.setMeasure(true);
            }
            if (fieldInfo.isTimeKey()) {
                if (info != null) {
                    info.setDataTimeField(true);
                }
                qContext.getShowDims().addDimension("DATATIME");
            }
            if (isMdInfoQuery) continue;
            this.parseMainTableFilter(qContext, request, masterKeys, rowFilter, dataTable, tableRunInfo, field, fieldInfo, index, exp);
        }
        if (isMdInfoQuery) {
            String mdInfoFilter;
            if (isMainTable && StringUtil.isNotEmpty((String)(mdInfoFilter = this.parseDataTableFilter(qContext, masterKeys, dataTable)))) {
                FilterUtils.appendFilter(rowFilter, mdInfoFilter);
            }
        } else if (isMainTable && StringUtil.isNotEmpty((String)dataTable.getFilter())) {
            qContext.getLogger().debug("tableFilter (" + dataTable.getFilter() + ") append to rowFilter");
            FilterUtils.appendFilter(rowFilter, dataTable.getFilter());
        }
    }

    private void parseMainTableFilter(QueryContext qContext, ICommonQuery request, DimensionValueSet masterKeys, StringBuilder rowFilter, DataTable dataTable, TableModelRunInfo tableRunInfo, DataField field, FieldInfo fieldInfo, int index, String exp) throws ParseException, InterpretException, DataSetException {
        String fieldFilter;
        List values = field.getValues();
        if (values != null && values.size() > 0) {
            boolean needColumnListfilter = true;
            if (fieldInfo.getFieldType() == FieldType.GENERAL_DIM) {
                ColumnModelDefine fieldDefine = tableRunInfo.parseSearchField(fieldInfo.getName());
                String dimensionName = tableRunInfo.getDimensionName(fieldDefine.getCode());
                if (dimensionName != null) {
                    needColumnListfilter = false;
                    StringBuilder buff = new StringBuilder();
                    buff.append("\u6570\u636e\u8868\u5b57\u6bb5").append(fieldInfo.getName()).append("\u7684\u53d6\u503c\u5217\u8868 ");
                    FilterUtils.printValueList(buff, values);
                    buff.append("\u8f6c\u6362\u4e3a\u4e3b\u7ef4\u5ea6").append(dimensionName).append("\u7684\u53d6\u503c\u8303\u56f4");
                    qContext.getLogger().debug(buff.toString());
                    masterKeys.setValue(dimensionName, (Object)values);
                }
            } else if (fieldInfo.getFieldType() == FieldType.TIME_DIM && fieldInfo.isTimeKey()) {
                needColumnListfilter = false;
                ArrayList<String> periodList = new ArrayList<String>(values.size());
                for (String timeKey : values) {
                    periodList.add(qContext.getDatatimeByTimeKey(timeKey));
                }
                qContext.getLogger().debug("\u6570\u636e\u8868\u5b57\u6bb5" + fieldInfo.getName() + "\u7684\u53d6\u503c\u5217\u8868 " + values + "\u8f6c\u6362\u4e3a\u4e3b\u7ef4\u5ea6" + "DATATIME" + "\u53d6\u503c\u8303\u56f4");
                masterKeys.setValue("DATATIME", periodList);
            } else {
                needColumnListfilter = true;
            }
            if (needColumnListfilter) {
                if (index < 0) {
                    index = request.addExpressionColumn(exp);
                }
                if (values.size() < DataEngineUtil.getMaxInSize((IDatabase)DatabaseInstance.getDatabase())) {
                    StringBuilder buf = new StringBuilder();
                    buf.append(field.getField().getTableName()).append(".").append(field.getField().getName());
                    buf.append(" in {");
                    for (String value : field.getValues()) {
                        buf.append("'").append(value).append("',");
                    }
                    buf.setLength(buf.length() - 1);
                    buf.append("}");
                    String infilter = buf.toString();
                    qContext.getLogger().debug("\u5b57\u6bb5" + field.getField().getName() + "\u7684\u53d6\u503c\u5217\u8868 " + field.getValues() + "\u8f6c\u6362\u4e3a\u8868\u8fbe\u5f0f " + infilter);
                    FilterUtils.appendFilter(rowFilter, infilter);
                } else {
                    ArrayList<Object> valueList = new ArrayList<Object>();
                    valueList.addAll(values);
                    qContext.getLogger().debug("\u6570\u636e\u8868\u5b57\u6bb5" + fieldInfo.getName() + "\u7684\u53d6\u503c\u5217\u8868 " + values + "\u8f6c\u6362\u4e3aColumnFilterValueList");
                    request.setColumnFilterValueList(index, valueList);
                }
            }
        }
        if (StringUtil.isNotEmpty((String)(fieldFilter = field.getFilter())) && (fieldInfo.getFieldType() != FieldType.TIME_DIM || !fieldInfo.isTimeKey())) {
            qContext.getLogger().debug("fieldFilter (" + fieldFilter + ") append to rowFilter");
            FilterUtils.appendFilter(rowFilter, fieldFilter);
        }
    }

    private void doQuery(QueryContext qContext, ICommonQuery request) throws Exception {
        DimensionValueSet marsterKeys = request.getMasterKeys();
        qContext.getLogger().debug("\u62a5\u8868\u6570\u636e\u5f15\u64ce\u67e5\u8be2\u8bf7\u6c42\uff1a\n" + request.toString());
        try (Connection conn = this.getConnection(qContext.getConnectionProvider());){
            if (qContext.getMainQueryTableType() == DataEngineConsts.QueryTableType.ACCOUNT && !marsterKeys.hasValue("DATATIME")) {
                String currentPeriod = this.periodDimTableReader.getCurrentPeriod(qContext.getPeriodEntityId());
                String startPeriod = AccountQueryUtils.getAccountStartPeriod(qContext, qContext.getMainTableModelRunInfo().getTableModelDefine().getCode(), conn);
                if (startPeriod == null) {
                    startPeriod = currentPeriod;
                }
                ArrayList periods = PeriodUtil.getPeiodStrList((PeriodWrapper)new PeriodWrapper(currentPeriod), (PeriodWrapper)new PeriodWrapper(startPeriod));
                marsterKeys.setValue("DATATIME", (Object)periods);
            }
            String unitDimensionName = qContext.getUnitEntityDefine().getDimensionName();
            if (qContext.ignoreUnitDim()) {
                marsterKeys.clearValue(unitDimensionName);
            } else {
                int allCount;
                List values;
                Object unitDimValue = marsterKeys.getValue(unitDimensionName);
                if (unitDimValue instanceof List && (values = (List)unitDimValue).size() > 10000 && !qContext.isOrderByUnitTree() && qContext.isJdbcQuery() && (allCount = this.entityDimTableReader.getAllUnitCount(qContext)) == values.size()) {
                    marsterKeys.clearValue(unitDimensionName);
                    qContext.setIgnoreUnitDim(true);
                }
            }
            for (int i = 0; i < marsterKeys.size(); ++i) {
                OrderTempAssistantTable tempAssistantTable;
                IUnitLeafFinder unitLeafFinder;
                String dimension;
                Object dimValue = marsterKeys.getValue(i);
                if (!(dimValue instanceof List)) continue;
                List values = (List)dimValue;
                String cacheKey = dimension = marsterKeys.getName(i);
                String dataSourceKey = qContext.getDataSourceKey();
                if (dataSourceKey != null) {
                    cacheKey = cacheKey + dataSourceKey;
                }
                if (qContext.isCalcQuery()) {
                    cacheKey = UUID.randomUUID().toString();
                }
                if (qContext.isUnitDim(dimension) && (unitLeafFinder = qContext.getExeContext().getEnv().getUnitLeafFinder()) != null) {
                    QueryStatLeafHelper helper = new QueryStatLeafHelper(dimension);
                    com.jiuqi.np.dataengine.query.QueryContext tempContext = new com.jiuqi.np.dataengine.query.QueryContext(qContext.getExeContext(), null);
                    tempContext.setMasterKeys(marsterKeys);
                    values = (List)helper.processUnitLeafs(tempContext, unitLeafFinder, (Object)values);
                }
                if (values.size() > 1 && qContext.isNeedHOrder(dimension)) {
                    tempAssistantTable = qContext.getTempAssistantTable(conn, cacheKey, values, true);
                    request.setTempAssistantTable(dimension, tempAssistantTable);
                    continue;
                }
                if (values.size() < 100) continue;
                tempAssistantTable = qContext.getTempAssistantTable(conn, cacheKey, values, false);
                qContext.getLogger().debug(dimension + "\u7ef4\u5ea6\u503c\u4e2a\u6570\u8d85\u8fc7 MAX_IN_SIZE\uff1a" + 100 + ", \u521b\u5efa\u4e34\u65f6\u8868" + tempAssistantTable.getTableName());
                request.setTempAssistantTable(dimension, tempAssistantTable);
            }
            request.queryToReader(qContext.getExeContext());
        }
    }

    private void filterUnits(QueryContext qContext, DimensionValueSet masterKeys) throws Exception {
        IEntityDefine unitEntityDefine = qContext.getUnitEntityDefine();
        if (unitEntityDefine != null) {
            String dimensionName = unitEntityDefine.getDimensionName();
            List<?> dimValues = qContext.getDimValuesFromCache(dimensionName);
            if (dimValues != null) {
                masterKeys.setValue(dimensionName, dimValues);
                return;
            }
            UnitFilterExpInfo unitFilterExpInfo = qContext.getUnitFilterExpInfo(this.calibreDataService);
            if (unitFilterExpInfo != null) {
                UnitChekMonitor condigionMonitor = new UnitChekMonitor(masterKeys);
                condigionMonitor.setMainDim(dimensionName);
                this.unitfilter.judgeUnitCondition(qContext, masterKeys, condigionMonitor, unitFilterExpInfo, this.entityDimTableReader);
            }
            List<IEntityRow> authUnitEntityRows = this.unitfilter.initAuthListToMasterKey(qContext, masterKeys, this.entityDimTableReader);
            qContext.setAuthUnitEntityRows(authUnitEntityRows);
        }
    }

    public void close(IReadContext context) throws AdHocEngineException {
        Map tempAssistantTables = (Map)context.getBuffer().get("TempAssistantTableCache");
        if (tempAssistantTables != null) {
            try (Connection conn = this.dataSource.getConnection();){
                for (String dimension : tempAssistantTables.keySet()) {
                    try {
                        long start = System.currentTimeMillis();
                        OrderTempAssistantTable tempAssistantTable = (OrderTempAssistantTable)tempAssistantTables.get(dimension);
                        if (tempAssistantTable.getConnectionProvider() != null) {
                            try (Connection extConnection = tempAssistantTable.getConnectionProvider().getConnection();){
                                tempAssistantTable.dropTempTable(extConnection);
                            }
                        } else {
                            tempAssistantTable.dropTempTable(conn);
                        }
                        context.getLogger().debug("\u6e05\u7a7a\u4e34\u65f6\u8868" + tempAssistantTable.getTableName() + ",\u7528\u65f6" + (System.currentTimeMillis() - start) + "ms");
                    }
                    catch (Exception e) {
                        context.getLogger().error(e.getMessage(), (Throwable)e);
                    }
                }
            }
            catch (SQLException e1) {
                throw new AdHocEngineException(e1.getMessage(), (Throwable)e1);
            }
            context.getBuffer().remove("TempAssistantTableCache");
        }
    }

    private String parseDataTableFilter(QueryContext qContext, DimensionValueSet masterKeys, DataTable dataTable) {
        StringBuilder filterBuff = new StringBuilder();
        if (StringUtil.isNotEmpty((String)dataTable.getFilter())) {
            FilterUtils.appendFilter(filterBuff, dataTable.getFilter());
        }
        for (DataField field : dataTable.getFields()) {
            String fieldFilter = field.getFilter();
            if (StringUtil.isNotEmpty((String)fieldFilter)) {
                qContext.getLogger().debug("\u7ef4\u5ea6\u8868\u5b57\u6bb5" + field.getField().getName() + " fieldFilter (" + fieldFilter + ") append to dataTableFilter");
                FilterUtils.appendFilter(filterBuff, fieldFilter);
            }
            if (field.getValues() == null || field.getValues().size() <= 0) continue;
            StringBuilder buf = new StringBuilder();
            buf.append(dataTable.getTable().getTableName()).append(".").append(field.getField().getName());
            buf.append(" in {");
            for (String value : field.getValues()) {
                buf.append("'").append(value).append("',");
            }
            buf.setLength(buf.length() - 1);
            buf.append("}");
            String infilter = buf.toString();
            qContext.getLogger().debug("\u7ef4\u5ea6\u8868\u5b57\u6bb5" + field.getField().getName() + "\u7684\u53d6\u503c\u5217\u8868 " + field.getValues() + "\u8f6c\u6362\u8868\u8fbe\u5f0f " + infilter);
            FilterUtils.appendFilter(filterBuff, infilter);
        }
        return filterBuff.length() > 0 ? filterBuff.toString() : null;
    }

    private boolean isNum(int dataType) {
        return dataType == 5 || dataType == 10 || dataType == 3;
    }

    private Set<String> getGroupByFieldNames() {
        HashSet<String> names = null;
        if (this.groupBys != null && this.groupBys.size() > 0) {
            names = new HashSet<String>();
            for (ColumnModelDefine field : this.groupBys) {
                String code = field.getCode();
                if (code.equals("DATATIME")) {
                    code = PeriodTableColumn.TIMEKEY.name();
                }
                names.add(code);
            }
        }
        return names;
    }

    private AggrType transAggrType(AggregationType aggregationType, int dataType, boolean isDim) {
        if (aggregationType != null) {
            switch (aggregationType) {
                case SUM: {
                    return AggrType.SUM;
                }
                case COUNT: {
                    if (isDim) {
                        return AggrType.DISTINCT_COUNT;
                    }
                    return AggrType.COUNT;
                }
                case MIN: {
                    return AggrType.MIN;
                }
                case MAX: {
                    return AggrType.MAX;
                }
                case AVG: {
                    return AggrType.AVERAGE;
                }
            }
        }
        if (!DataTypes.isNumber((int)dataType)) {
            return AggrType.MIN;
        }
        return AggrType.SUM;
    }

    private Connection getConnection(IConnectionProvider connectionProvider) throws SQLException {
        if (connectionProvider != null) {
            return connectionProvider.getConnection();
        }
        return this.dataSource.getConnection();
    }

    private TableModelRunInfo getTableModelRunInfoByDataTable(DataTable dataTable, DataModelDefinitionsCache dataDefinitionsCache) {
        com.jiuqi.nr.datascheme.api.DataTable schemeDataTable;
        List deployInfos;
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfoByCode(dataTable.getTable().getPhysicalName());
        if (tableRunInfo == null && (deployInfos = this.dataSchemeService.getDeployInfoByDataTableKey((schemeDataTable = this.dataSchemeService.getDataTableByCode(dataTable.getTable().getPhysicalName())).getKey())).size() > 0) {
            tableRunInfo = dataDefinitionsCache.getTableInfoByCode(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
        }
        return tableRunInfo;
    }
}

