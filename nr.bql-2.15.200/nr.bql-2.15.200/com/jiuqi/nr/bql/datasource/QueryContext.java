/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.TableCache
 *  com.jiuqi.bi.adhoc.cache.graph.TableGraph
 *  com.jiuqi.bi.adhoc.datasource.reader.DataQuery
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.bi.adhoc.datasource.reader.IReadContext
 *  com.jiuqi.bi.adhoc.engine.restriction.FilterOrigin
 *  com.jiuqi.bi.adhoc.model.RelationInfo
 *  com.jiuqi.bi.adhoc.model.RelationMode
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$QueryTableType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper
 *  com.jiuqi.nr.common.util.TimeDimHelper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableMap
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableFactory
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.query.DBAggregation
 *  com.jiuqi.nvwa.memdb.api.query.DBQuery
 *  com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.adhoc.cache.TableCache;
import com.jiuqi.bi.adhoc.cache.graph.TableGraph;
import com.jiuqi.bi.adhoc.datasource.reader.DataQuery;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.bi.adhoc.datasource.reader.IReadContext;
import com.jiuqi.bi.adhoc.engine.restriction.FilterOrigin;
import com.jiuqi.bi.adhoc.model.RelationInfo;
import com.jiuqi.bi.adhoc.model.RelationMode;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.dataengine.adapt.QueryColumnModelFinder;
import com.jiuqi.nr.bql.dataengine.adapt.QueryDataModelDefinitionsCache;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.bql.datasource.DataTableColumn;
import com.jiuqi.nr.bql.datasource.MappingMainDimTable;
import com.jiuqi.nr.bql.datasource.UnitFilterExpInfo;
import com.jiuqi.nr.bql.datasource.parse.QueryFieldProvider;
import com.jiuqi.nr.bql.datasource.parse.func.BqlFuncProvider;
import com.jiuqi.nr.bql.datasource.reader.EntityDimTableReader;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.extend.NvwaDataSourceTableQueryExecuter;
import com.jiuqi.nr.bql.intf.CalibreDimTable;
import com.jiuqi.nr.bql.intf.TableRelation;
import com.jiuqi.nr.bql.util.FilterUtils;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
import com.jiuqi.nr.common.util.TimeDimHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTableMap;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.query.DBAggregation;
import com.jiuqi.nvwa.memdb.api.query.DBQuery;
import com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class QueryContext
implements IContext {
    private static final String DIM_VALUES_CACHE_PREFIX = "dimValueCache_";
    private IReadContext readContext;
    private ExecutorContext exeContext;
    private DataRow row;
    private Metadata<ColumnInfo> metadata;
    private String defaultTableName;
    private FormulaParser parser;
    private final Map<String, TableInfo> tableInfoMap = new HashMap<String, TableInfo>();
    private final Map<String, String> timeKeyMap = new HashMap<String, String>();
    private final Map<String, String> dataTimeMap = new HashMap<String, String>();
    private final Set<String> needHOrderDims = new HashSet<String>();
    private TableModelRunInfo tableModelRunInfo;
    private TableModelRunInfo mdInfoTableModelRunInfo;
    private DataTable mainTable;
    private TableModelRunInfo parentTableModelRunInfo;
    private TableRelation parentTableRelation;
    private DataTable mdTable;
    private IEntityDefine unitEntityDefine = null;
    private IPeriodEntity periodEntity;
    private IPeriodProvider periodProvider;
    private TimeDimHelper timeDimHelper;
    private MappingMainDimTable mappingMainDimTable;
    private Set<String> schemeDimEntitys = new HashSet<String>();
    private Set<String> schemeDimNames = new HashSet<String>();
    private DataScheme dataScheme;
    private List<DimQueryInfo> dimQueryInfos;
    private boolean hasSchemeDimension = false;
    private String gatherSchemeCode;
    private DataQuery dataQuery;
    private String versionPeriod;
    private NrdbHelper nrdbHelper;
    private DBQueryExecutorProvider dbQueryExecutorProvider;
    private boolean orderByUnitTree = false;
    private String unitEntityFilter = null;
    private IDataTableQueryExecutor tableQueryExeturor;
    private boolean needResetDimTitle = false;
    private boolean eliminateUnitDim = false;
    private List<IEntityRow> authUnitEntityRows = null;
    private DimensionSet showDims = new DimensionSet();
    private TableGraph tableGraph;

    public QueryContext(IReadContext readContext, ExecutorContext exeContext, String defaultTableName, DataQuery dataQuery) {
        this.readContext = readContext;
        this.exeContext = exeContext;
        this.defaultTableName = defaultTableName;
        this.dataQuery = dataQuery;
    }

    public Object getValue(String columnName) {
        int index;
        Object value = null;
        if (this.row != null && this.metadata != null && (index = this.metadata.indexOf(columnName)) >= 0) {
            value = this.row.getValue(index);
        }
        return value;
    }

    public String getTimeKeyByDataTime(String periodCode) {
        String timeKey = this.timeKeyMap.get(periodCode);
        if (StringUtils.isNotEmpty((String)timeKey)) {
            return timeKey;
        }
        return this.timeDimHelper.periodToTimeKey(periodCode);
    }

    public String getDatatimeByTimeKey(String timeKey) {
        String periodCode = this.dataTimeMap.get(timeKey);
        if (StringUtils.isNotEmpty((String)periodCode)) {
            return periodCode;
        }
        return this.timeDimHelper.timeKeyToPeriod(timeKey, (PeriodType)Optional.ofNullable(this.periodEntity).map(p -> p.getPeriodType()).orElse(null));
    }

    public void initTimeKeyCache() {
        if (this.periodProvider == null) {
            return;
        }
        List periodRows = this.periodProvider.getPeriodItems();
        for (IPeriodRow periodRow : periodRows) {
            this.timeKeyMap.put(periodRow.getCode(), periodRow.getTimeKey());
            this.dataTimeMap.put(periodRow.getTimeKey(), periodRow.getCode());
        }
    }

    public String getTableCode(DataTable dataTable) {
        TableInfo tableInfo = dataTable.getTable().getTable();
        return this.getTableCode(tableInfo);
    }

    public String getTableCode(TableInfo tableInfo) {
        String tableCode = tableInfo.getName();
        if (tableInfo.getType() == TableType.DIM) {
            tableCode = tableInfo.getPhysicalTableName();
            String dimName = tableInfo.getName();
            tableCode = this.getDimTableCode(dimName, tableCode);
        }
        return tableCode;
    }

    public String getDimTableCode(String dimName, String defaultTableCode) {
        if (this.readContext.getHierarchies() != null) {
            for (String hier : this.readContext.getHierarchies()) {
                if (hier.indexOf(dimName + ".") != 0) continue;
                return hier.substring(dimName.length() + 1);
            }
        }
        return defaultTableCode;
    }

    public void initOrderCache(String dimensionName, List<String> values) {
        if (this.isNeedHOrder(dimensionName)) {
            String catchKey = DIM_VALUES_CACHE_PREFIX + dimensionName;
            HashMap<String, Integer> orderCache = (HashMap<String, Integer>)this.getReadContext().getBuffer().get(catchKey);
            if (orderCache == null) {
                orderCache = new HashMap<String, Integer>();
                for (int i = 0; i < values.size(); ++i) {
                    String value = values.get(i);
                    orderCache.put(value, i);
                }
                this.getReadContext().getBuffer().put(catchKey, orderCache);
            }
        }
    }

    public Map<String, Integer> getOrderCache(String dimensionName) {
        String catchKey = DIM_VALUES_CACHE_PREFIX + dimensionName;
        return (Map)this.getReadContext().getBuffer().get(catchKey);
    }

    public FormulaParser getParser() {
        if (this.parser == null) {
            this.parser = FormulaParser.getInstance();
            this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new QueryFieldProvider());
        }
        return this.parser;
    }

    private Map<String, OrderTempAssistantTable> getTempAssistantTables() {
        HashMap tempAssistantTables = (HashMap)this.readContext.getBuffer().get("TempAssistantTableCache");
        if (tempAssistantTables == null) {
            tempAssistantTables = new HashMap();
            this.readContext.getBuffer().put("TempAssistantTableCache", tempAssistantTables);
        }
        return tempAssistantTables;
    }

    public OrderTempAssistantTable getTempAssistantTable(Connection conn, String keyName, List<String> values, boolean isUnit) throws Exception {
        OrderTempAssistantTable tempAssistantTable = this.getTempAssistantTables().get(keyName);
        if (tempAssistantTable == null) {
            Map<String, Integer> orderCache;
            if (isUnit && (orderCache = this.getOrderCache(keyName)) != null) {
                HashMap<String, Integer> valuesOrderCache = new HashMap<String, Integer>();
                Iterator<String> iterator = values.iterator();
                while (iterator.hasNext()) {
                    String value;
                    Integer order = orderCache.get(value = iterator.next());
                    valuesOrderCache.put(value, order == null ? 0 : order);
                }
                tempAssistantTable = new OrderTempAssistantTable(valuesOrderCache);
            }
            if (tempAssistantTable == null) {
                tempAssistantTable = new OrderTempAssistantTable(new ArrayList<String>(new HashSet<String>(values)));
            }
            tempAssistantTable.setConnectionProvider(this.getConnectionProvider());
            long start = System.currentTimeMillis();
            tempAssistantTable.createTempTable(conn);
            tempAssistantTable.insertIntoTempTable(conn);
            this.getTempAssistantTables().put(keyName, tempAssistantTable);
            this.getLogger().debug(keyName + "\u7ef4\u5ea6\u9700\u8981\u6309\u6811\u5f62\u6392\u5e8f, \u521b\u5efa\u987a\u5e8f\u4e34\u65f6\u8868" + tempAssistantTable.getTableName() + ",\u63d2\u5165\u4e86" + tempAssistantTable.size() + "\u6761\u8bb0\u5f55\uff0c\u7528\u65f6" + (System.currentTimeMillis() - start) + "ms");
        }
        return tempAssistantTable;
    }

    public String getPeriod(DimensionValueSet masterKeys) {
        Object period = masterKeys.getValue("DATATIME");
        if (period instanceof List) {
            List values = (List)period;
            if (values.size() > 0) {
                Collections.sort(values);
                period = values.get(values.size() - 1);
            } else {
                period = null;
            }
        }
        return period == null ? null : period.toString();
    }

    public UnitFilterExpInfo getUnitFilterExpInfo(ICalibreDataService calibreDataService) {
        String filter;
        String caliberFilterExp;
        String unitFiterStr;
        String[] eqs;
        String[] dimFilters;
        String caliberFilter;
        StringBuilder filterBuff = new StringBuilder();
        if (this.unitEntityFilter != null) {
            FilterUtils.appendFilter(filterBuff, this.unitEntityFilter);
        }
        if (StringUtils.isNotEmpty((String)(caliberFilter = (String)this.readContext.getOptions().get("calibreFilter"))) && (dimFilters = caliberFilter.split(";")).length > 0 && (eqs = (unitFiterStr = dimFilters[0]).split(":")).length == 2 && (caliberFilterExp = this.getCaliberFilterExp(calibreDataService, eqs)) != null) {
            FilterUtils.appendFilter(filterBuff, caliberFilterExp);
        }
        if (StringUtils.isNotEmpty((String)(filter = (String)this.readContext.getOptions().get("NR.orgFilter")))) {
            FilterUtils.appendFilter(filterBuff, filter);
        }
        if (filterBuff.length() > 0) {
            UnitFilterExpInfo unitFilterExpInfo = new UnitFilterExpInfo();
            unitFilterExpInfo.setFilter(filterBuff.toString());
            unitFilterExpInfo.setFormSchemeKey((String)this.readContext.getOptions().get("NR.reportScheme"));
            return unitFilterExpInfo;
        }
        return null;
    }

    private String getCaliberFilterExp(ICalibreDataService calibreDataService, String[] eqs) {
        String[] caliberDataCodes;
        String caliberFilterExp = null;
        String calibreCode = eqs[0];
        for (String code : caliberDataCodes = eqs[1].split(",")) {
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            calibreDataDTO.setCalibreCode(calibreCode);
            calibreDataDTO.setCode(code);
            Result result = calibreDataService.get(calibreDataDTO);
            if (result == null || result.getData() == null) continue;
            String calibreDataExp = this.getCalibreDataExp((Result<CalibreDataDTO>)result);
            if (caliberFilterExp == null) {
                if (caliberDataCodes.length > 1) {
                    caliberFilterExp = "(" + calibreDataExp + ")";
                    continue;
                }
                caliberFilterExp = calibreDataExp;
                continue;
            }
            caliberFilterExp = caliberFilterExp + " or (" + calibreDataExp + ")";
        }
        return caliberFilterExp;
    }

    private String getCalibreDataExp(Result<CalibreDataDTO> result) {
        String calibreDataExp = null;
        CalibreDataDTO calibreData = (CalibreDataDTO)result.getData();
        Object expression = calibreData.getValue().getExpression();
        if (expression instanceof List) {
            List values = (List)expression;
            if (values.size() > 0) {
                StringBuilder exp = new StringBuilder();
                exp.append("[SYS_UNITKEY] in {");
                for (Object v : values) {
                    exp.append("'").append(v.toString()).append("',");
                }
                exp.setLength(exp.length() - 1);
                exp.append("}");
                calibreDataExp = exp.toString();
            } else {
                calibreDataExp = "1=0";
            }
        } else if (expression instanceof String) {
            calibreDataExp = expression.toString();
        }
        return calibreDataExp;
    }

    public List<?> getDimValuesFromCache(String dimensionName) {
        if (this.isCalcQuery()) {
            return null;
        }
        String dimValuesCacheKey = this.getDimValuesCacheKey(dimensionName);
        return (List)this.readContext.getBuffer().get(dimValuesCacheKey);
    }

    public boolean ignoreUnitDim() {
        return "true".equals(this.readContext.getBuffer().get("ignoreUnitDim"));
    }

    public void setIgnoreUnitDim(boolean ignoreUnitDim) {
        this.readContext.getBuffer().put("ignoreUnitDim", String.valueOf(ignoreUnitDim));
    }

    public void putDimValuesToCache(String dimensionName, List<?> values) {
        if (this.isCalcQuery()) {
            return;
        }
        String dimValuesCacheKey = this.getDimValuesCacheKey(dimensionName);
        this.readContext.getBuffer().put(dimValuesCacheKey, values);
    }

    public void putMainDimTitlesToCache(List<IEntityRow> rows) {
        if (this.needResetDimTitle) {
            HashMap cache = new HashMap();
            rows.forEach(r -> cache.put(r.getEntityKeyData(), r.getTitle()));
            this.readContext.getBuffer().put("MainDimTitleCache", cache);
        }
    }

    public HashMap<String, String> getMainDimTitlesCache() {
        return (HashMap)this.readContext.getBuffer().get("MainDimTitleCache");
    }

    protected String getDimValuesCacheKey(String dimensionName) {
        return "DimValueList_" + dimensionName;
    }

    public boolean isFloatTableQuery() {
        return this.tableModelRunInfo != null && this.tableModelRunInfo.getBizOrderField() != null;
    }

    public DataTable getMainTable() {
        return this.mainTable;
    }

    private void setMainTable(DataTable mainTable) {
        this.mainTable = mainTable;
        DataTableMap dataTableMap = (DataTableMap)mainTable.getTable().getTable().getPropMap().get("NR.dataTableMap");
        if (dataTableMap != null) {
            DataTableFactoryManager.getInstance().getFactory(dataTableMap.getSrcType());
            IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(dataTableMap.getSrcType());
            try {
                this.tableQueryExeturor = factory.createQueryExecuter(dataTableMap.getSrcKey());
            }
            catch (DataTableAdaptException e) {
                this.getLogger().error(e.getMessage(), (Throwable)e);
            }
        }
    }

    public IEntityDefine getUnitEntityDefine() throws JQException {
        return this.unitEntityDefine;
    }

    public void initByDSV(String dsvName, ComponentSet componentSet) throws Exception {
        IRuntimeDataSchemeService dataSchemeService = componentSet.dataSchemeService;
        EntityDimTableReader entityDimTableReader = componentSet.entityDimTableReader;
        String schemeCode = DSVAdapter.getDataSchemeCodebyDSVName(dsvName);
        this.dataScheme = dataSchemeService.getDataSchemeByCode(schemeCode);
        this.nrdbHelper = componentSet.nrdbHelper;
        this.dbQueryExecutorProvider = componentSet.dbQueryExecutorProvider;
        this.timeDimHelper = new TimeDimHelper();
        this.tableGraph = TableCache.getGlobal().findGraph(dsvName);
        List dims = dataSchemeService.getDataSchemeDimension(this.dataScheme.getKey());
        this.setMainTable(this.dataQuery.getDataTable());
        for (DataDimension dim : dims) {
            this.schemeDimEntitys.add(dim.getDimKey());
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType == DimensionType.UNIT) {
                this.unitEntityDefine = entityDimTableReader.getEntityDefineByKey(this, dim.getDimKey());
                this.schemeDimNames.add(this.unitEntityDefine.getDimensionName());
                continue;
            }
            if (dimensionType == DimensionType.PERIOD) {
                if (!StringUtils.isNotEmpty((String)dim.getDimKey())) continue;
                this.periodProvider = componentSet.periodDimTableReader.getPeriodEntityAdapter().getPeriodProvider(dim.getDimKey());
                this.periodEntity = this.periodProvider.getPeriodEntity();
                this.exeContext.setPeriodProvider((IPeriodAdapter)this.periodProvider);
                this.exeContext.setPeriodView(this.periodEntity.getKey());
                this.schemeDimNames.add("DATATIME");
                continue;
            }
            if (dimensionType != DimensionType.DIMENSION) continue;
            this.hasSchemeDimension = true;
            if (!StringUtils.isNotEmpty((String)dim.getDimKey())) continue;
            if ("ADJUST".equals(dim.getDimKey())) {
                this.schemeDimNames.add("ADJUST");
                continue;
            }
            IEntityDefine dimEntity = entityDimTableReader.getEntityDefineByKey(this, dim.getDimKey());
            if (dimEntity == null) continue;
            this.schemeDimNames.add(dimEntity.getDimensionName());
        }
        if (this.dataScheme.getType() == DataSchemeType.QUERY) {
            DefinitionsCache cache = new DefinitionsCache(this.exeContext);
            QueryColumnModelFinder queryColumnModelFinder = new QueryColumnModelFinder(dataSchemeService, entityDimTableReader.getEntityMetaService(), cache.getDataModelDefinitionsCache().getColumnModelFinder());
            QueryDataModelDefinitionsCache dataModelcache = new QueryDataModelDefinitionsCache(this.exeContext, this.exeContext.getCache().getFormulaParser(true), dataSchemeService, entityDimTableReader.getEntityMetaService(), queryColumnModelFinder);
            dataModelcache.setDataDefinitionsCache(cache.getDataDefinitionsCache());
            cache.setDataModelDefinitionsCache((DataModelDefinitionsCache)dataModelcache);
            this.exeContext.setCache(cache);
        }
        DataModelDefinitionsCache dataModelDefinitionsCache = this.exeContext.getCache().getDataModelDefinitionsCache();
        this.initTableModelRunInfoByDataTable(componentSet, this.mainTable, dataModelDefinitionsCache);
        com.jiuqi.nr.datascheme.api.DataTable mdInfoDataTable = dataSchemeService.getDataTableForMdInfo(this.dataScheme.getKey());
        boolean queryMdInfoTable = false;
        if (mdInfoDataTable != null) {
            List deployInfos = dataSchemeService.getDeployInfoByDataTableKey(mdInfoDataTable.getKey());
            if (deployInfos.size() > 0) {
                this.mdInfoTableModelRunInfo = dataModelDefinitionsCache.getTableInfoByCode(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
            }
            if (this.dataQuery.getDataTable().getTable().getTableName().equals(mdInfoDataTable.getCode())) {
                queryMdInfoTable = true;
            } else {
                for (DataTable table : this.dataQuery.getRefTables()) {
                    String tableName = table.getTable().getTableName();
                    if (tableName.equals(mdInfoDataTable.getCode())) {
                        queryMdInfoTable = true;
                        continue;
                    }
                    if (!tableName.equals(this.unitEntityDefine.getDimensionName())) continue;
                    this.mdTable = table;
                }
            }
            if (queryMdInfoTable && !this.getReadContext().getBuffer().containsKey("adjustVersionPeriod")) {
                this.adjustPeriodForMdInfoTable(entityDimTableReader);
            }
        }
        String formSchemeKey = (String)this.readContext.getOptions().get("NR.reportScheme");
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(entityDimTableReader.getRunTimeViewController(), entityDimTableReader.getDataDefinitionController(), entityDimTableReader.getiEntityViewRunTimeController(), formSchemeKey);
        env.setDataScehmeKey(this.getDataScheme().getKey());
        this.exeContext.setEnv((IFmlExecEnvironment)env);
        this.exeContext.registerFunctionProvider((IFunctionProvider)new BqlFuncProvider());
        this.exeContext.setAutoDataMasking(true);
        this.exeContext.setCurrentEntityId(this.unitEntityDefine.getId());
        this.processRefDim(entityDimTableReader, dims);
        String varDataScheme = "NR.var.dataScheme";
        VariableManager variableManager = this.exeContext.getVariableManager();
        variableManager.add(new Variable(varDataScheme, varDataScheme, 6, (Object)this.dataScheme.getKey()));
        variableManager.add(new Variable("QUERY_CONTEXT_STOP_FLAG", "QUERY_CONTEXT_STOP_FLAG", 3, (Object)0));
        String varGatherScheme = "batchGatherSchemeCode";
        this.gatherSchemeCode = (String)this.readContext.getOptions().get(varGatherScheme);
        if (StringUtils.isNotEmpty((String)this.gatherSchemeCode)) {
            variableManager.add(new Variable(varGatherScheme, varGatherScheme, 6, (Object)this.gatherSchemeCode));
            CalibreDimTable calibreDimTable = entityDimTableReader.getCalibreDimTableProvider().findByGatherScheme(this.gatherSchemeCode);
            if (calibreDimTable != null) {
                variableManager.add(new Variable("dimType", "\u7ef4\u5ea6\u7c7b\u578b", 4, (Object)calibreDimTable.getType()));
                variableManager.add(new Variable("dimValue", "\u7ef4\u5ea6\u6807\u8bc6", 6, (Object)calibreDimTable.getTableCode()));
                this.mappingMainDimTable = new MappingMainDimTable();
                this.mappingMainDimTable.setDimensionName(this.unitEntityDefine.getDimensionName());
                if (calibreDimTable.getType() == 1) {
                    this.mappingMainDimTable.setTableName(calibreDimTable.getTableCode());
                    this.mappingMainDimTable.getFieldMappings().put("CODE", "OBJECTCODE");
                    this.mappingMainDimTable.getFieldMappings().put("NAME", "NAME");
                    this.mappingMainDimTable.getFieldMappings().put("PARENTCODE", "PARENTCODE");
                    this.mappingMainDimTable.getFieldMappings().put("ORDINAL", "ORDINAL");
                } else {
                    this.mappingMainDimTable.setTableName("NR_CALIBRE_DATA");
                    this.mappingMainDimTable.getFieldMappings().put("CODE", CalibreDataMapper.FIELD_CODE);
                    this.mappingMainDimTable.getFieldMappings().put("NAME", CalibreDataMapper.FIELD_NAME);
                    this.mappingMainDimTable.getFieldMappings().put("PARENTCODE", CalibreDataMapper.FIELD_PARENT);
                    this.mappingMainDimTable.getFieldMappings().put("ORDINAL", CalibreDataMapper.FIELD_ORDER);
                    this.mappingMainDimTable.getArgs().put(CalibreDataMapper.FIELD_CALIBRE_CODE, calibreDimTable.getTableCode());
                }
            }
        }
        I18nHelper i18nHelper = (I18nHelper)SpringBeanUtils.getBean((String)"DataModelManage", I18nHelper.class);
        try {
            this.needResetDimTitle = i18nHelper.isOpenLanaguage() && NpContextHolder.getContext().getLocale() != Locale.getDefault();
        }
        catch (Exception e) {
            this.getLogger().error(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void adjustPeriodForMdInfoTable(EntityDimTableReader entityDimTableReader) throws SQLException, Exception {
        block54: {
            if (!this.nrdbHelper.isEnableNrdb()) {
                StringBuilder sql = new StringBuilder();
                sql.append(" select max(t.DATATIME) from ").append(this.mdInfoTableModelRunInfo.getTableModelDefine().getName()).append(" t");
                IConnectionProvider connectionProvider = entityDimTableReader.getUnitfilter().getConnectionProvider();
                Connection connection = null;
                try {
                    connection = connectionProvider.getConnection();
                    try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                         ResultSet rs = sqlHelper.executeQuery(connection, sql.toString());){
                        if (rs.next()) {
                            this.getReadContext().getBuffer().put("adjustVersionPeriod", rs.getString(1));
                        }
                        break block54;
                    }
                }
                finally {
                    connectionProvider.closeConnection(connection);
                }
            }
            DBQueryBuilder dbQueryBuilder = new DBQueryBuilder();
            dbQueryBuilder.select("DATATIME", DBAggregation.MAX);
            try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(this.mdInfoTableModelRunInfo.getTableModelDefine());){
                DBQuery dbQuery = dbQueryBuilder.build();
                this.getLogger().debug(" query table " + dbTable.getName() + " " + dbQuery.toString());
                try (DBCursor cursor = dbTable.query(dbQuery);){
                    if (cursor.hasNext()) {
                        DBRecord record = (DBRecord)cursor.next();
                        this.getReadContext().getBuffer().put("adjustVersionPeriod", record.getString(0));
                    }
                }
            }
        }
    }

    private void processRefDim(EntityDimTableReader entityDimTableReader, List<DataDimension> dims) {
        for (DataDimension dim : dims) {
            if (!StringUtils.isNotEmpty((String)dim.getDimAttribute()) || dim.getDimAttribute().equals("CURRENCYID")) continue;
            String dimKey = dim.getDimKey();
            IEntityMetaService entityMetaService = entityDimTableReader.getEntityMetaService();
            IEntityModel entityModel = entityMetaService.getEntityModel(this.unitEntityDefine.getId());
            IEntityAttribute arttri = entityModel.getAttribute(dim.getDimAttribute());
            if (arttri == null || arttri.isMultival()) continue;
            IEntityDefine dimEntityDefine = entityMetaService.queryEntity(dimKey);
            DimQueryInfo dimQueryInfo = new DimQueryInfo(dimEntityDefine.getDimensionName());
            dimQueryInfo.setEntityId(dimKey);
            dimQueryInfo.setRefTableName(this.unitEntityDefine.getCode());
            dimQueryInfo.setRefFieldName(arttri.getName());
            this.addDimQueryInfo(dimQueryInfo);
        }
    }

    private void initTableModelRunInfoByDataTable(ComponentSet componentSet, DataTable dataTable, DataModelDefinitionsCache dataDefinitionsCache) {
        Optional<RelationInfo> moreToOneRelation;
        List relations;
        com.jiuqi.nr.datascheme.api.DataTable schemeDataTable;
        List deployInfos;
        this.tableModelRunInfo = dataDefinitionsCache.getTableInfoByCode(dataTable.getTable().getPhysicalName());
        if (this.tableModelRunInfo == null && (deployInfos = componentSet.dataSchemeService.getDeployInfoByDataTableKey((schemeDataTable = componentSet.dataSchemeService.getDataTableByCode(dataTable.getTable().getPhysicalName())).getKey())).size() > 0) {
            this.tableModelRunInfo = dataDefinitionsCache.getTableInfoByCode(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
        }
        if ((relations = dataTable.getTable().getTable().getRelations()) != null && relations.size() > 0 && (moreToOneRelation = relations.stream().filter(r -> r.getMode() == RelationMode.MORE_TO_ONE).findAny()).isPresent()) {
            RelationInfo parentRelation = moreToOneRelation.get();
            String targetTableName = parentRelation.getTargetTable();
            this.parentTableModelRunInfo = dataDefinitionsCache.getTableInfo(targetTableName);
            this.parentTableRelation = new TableRelation();
            this.parentTableRelation.setDestTableName(targetTableName);
            for (Map.Entry entry : parentRelation.getFieldMaps().entrySet()) {
                String srcFieldCode = (String)entry.getKey();
                String targatCode = (String)entry.getValue();
                this.parentTableRelation.getFieldMap().put(this.tableModelRunInfo.parseSearchField(srcFieldCode).getName(), this.parentTableModelRunInfo.parseSearchField(targatCode).getName());
            }
        }
    }

    public void addDimQueryInfo(DimQueryInfo dimQueryInfo) {
        if (this.dimQueryInfos == null) {
            this.dimQueryInfos = new ArrayList<DimQueryInfo>();
        }
        this.dimQueryInfos.add(dimQueryInfo);
    }

    public ILogger getLogger() {
        return this.readContext.getLogger();
    }

    public IReadContext getReadContext() {
        return this.readContext;
    }

    public void setReadContext(IReadContext readContext) {
        this.readContext = readContext;
    }

    public ExecutorContext getExeContext() {
        return this.exeContext;
    }

    public void setExeContext(ExecutorContext exeContext) {
        this.exeContext = exeContext;
    }

    public DataRow getRow() {
        return this.row;
    }

    public void setRow(DataRow row) {
        this.row = row;
    }

    public Metadata<ColumnInfo> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata<ColumnInfo> metadata) {
        this.metadata = metadata;
    }

    public String getDefaultTableName() {
        return this.defaultTableName;
    }

    public void setDefaultTableName(String defaultTableName) {
        this.defaultTableName = defaultTableName;
    }

    public Map<String, TableInfo> getTableInfoMap() {
        return this.tableInfoMap;
    }

    public boolean isNeedHOrder(String dimensionName) {
        return this.needHOrderDims.contains(dimensionName);
    }

    public void setNeedHOrder(String dimensionName) {
        this.needHOrderDims.add(dimensionName);
    }

    public boolean isCalcQuery() {
        if (this.dataQuery == null) {
            return false;
        }
        Set filterOrigins = this.dataQuery.getFilterOrigins();
        if (filterOrigins != null) {
            for (FilterOrigin filterOrigin : filterOrigins) {
                if (filterOrigin != FilterOrigin.MEASURE) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isJdbcQuery() {
        if (this.nrdbHelper.isEnableNrdb()) {
            return false;
        }
        if (this.dbQueryExecutorProvider != null && this.dbQueryExecutorProvider.getDBQueryExecutor(this.mainTable.getTable().getTableName()) != null) {
            return false;
        }
        return this.tableQueryExeturor == null || this.tableQueryExeturor.getConnectionProvider() == null;
    }

    public String getPeriodEntityId() {
        return Optional.ofNullable(this.periodEntity).map(p -> p.getKey()).orElse(null);
    }

    public TableModelRunInfo getMainTableModelRunInfo() {
        return this.tableModelRunInfo;
    }

    public DataTableColumn getDataTableColumnByDim(String dimension) {
        ColumnModelDefine dimensionField = this.tableModelRunInfo.getDimensionField(dimension);
        if (dimensionField != null) {
            return new DataTableColumn(this.tableModelRunInfo, dimensionField);
        }
        if (this.parentTableModelRunInfo != null && (dimensionField = this.parentTableModelRunInfo.getDimensionField(dimension)) != null) {
            return new DataTableColumn(this.parentTableModelRunInfo, dimensionField);
        }
        return null;
    }

    public DataTableColumn getDataTableColumnByCode(String code) {
        ColumnModelDefine columnModel = this.tableModelRunInfo.parseSearchField(code);
        if (columnModel != null) {
            return new DataTableColumn(this.tableModelRunInfo, columnModel);
        }
        if (this.parentTableModelRunInfo != null && (columnModel = this.parentTableModelRunInfo.parseSearchField(code)) != null) {
            return new DataTableColumn(this.parentTableModelRunInfo, columnModel);
        }
        return null;
    }

    public boolean isUnitDim(String dimension) {
        return dimension.equals(this.unitEntityDefine.getDimensionName());
    }

    public boolean hasDimension(String dimension) {
        ColumnModelDefine dimensionField = this.getDimensionField(dimension);
        return dimensionField != null;
    }

    public ColumnModelDefine getDimensionField(String dimension) {
        ColumnModelDefine dimensionField = this.tableModelRunInfo.getDimensionField(dimension);
        if (dimensionField == null && this.parentTableModelRunInfo != null) {
            dimensionField = this.parentTableModelRunInfo.getDimensionField(dimension);
        }
        return dimensionField;
    }

    public MappingMainDimTable getMappingMainDimTable() {
        return this.mappingMainDimTable;
    }

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<DimQueryInfo> getDimQueryInfos() {
        return this.dimQueryInfos;
    }

    public boolean hasSchemeDimension() {
        return this.hasSchemeDimension;
    }

    public void setHasSchemeDimension(boolean hasSchemeDimension) {
        this.hasSchemeDimension = hasSchemeDimension;
    }

    public String getGatherSchemeCode() {
        return this.gatherSchemeCode;
    }

    public TableModelRunInfo getMdInfoTableModelRunInfo() {
        return this.mdInfoTableModelRunInfo;
    }

    public String getAdjustVersionPeriod() {
        return (String)this.getReadContext().getBuffer().get("adjustVersionPeriod");
    }

    public DataTable getMdTable() {
        return this.mdTable;
    }

    public String getVersionPeriod() {
        return this.versionPeriod;
    }

    public void setVersionPeriod(String versionPeriod) {
        this.versionPeriod = versionPeriod;
    }

    public Set<String> getSchemeDimEntitys() {
        return this.schemeDimEntitys;
    }

    public boolean isOrderByUnitTree() {
        return this.orderByUnitTree;
    }

    public void setOrderByUnitTree(boolean orderByUnitTree) {
        this.orderByUnitTree = orderByUnitTree;
    }

    public String getUnitEntityFilter() {
        return this.unitEntityFilter;
    }

    public void setUnitEntityFilter(String unitEntityFilter) {
        this.unitEntityFilter = unitEntityFilter;
    }

    public IConnectionProvider getConnectionProvider() {
        if (this.tableQueryExeturor != null) {
            return this.tableQueryExeturor.getConnectionProvider();
        }
        return null;
    }

    public String getDataSourceKey() {
        if (this.tableQueryExeturor != null && this.tableQueryExeturor instanceof NvwaDataSourceTableQueryExecuter) {
            return ((NvwaDataSourceTableQueryExecuter)this.tableQueryExeturor).getDatasourceKey();
        }
        return null;
    }

    public boolean isNeedResetDimTitle() {
        return this.needResetDimTitle;
    }

    public boolean isEliminateUnitDim() {
        return this.eliminateUnitDim;
    }

    public void setEliminateUnitDim(boolean eliminateUnitDim) {
        this.eliminateUnitDim = eliminateUnitDim;
    }

    public List<IEntityRow> getAuthUnitEntityRows() {
        return this.authUnitEntityRows;
    }

    public void setAuthUnitEntityRows(List<IEntityRow> authUnitEntityRows) {
        this.authUnitEntityRows = authUnitEntityRows;
    }

    public Set<String> getSchemeDimNames() {
        return this.schemeDimNames;
    }

    public DimensionSet getShowDims() {
        return this.showDims;
    }

    public TableModelRunInfo getParentTableModelRunInfo() {
        return this.parentTableModelRunInfo;
    }

    public void setParentTableModelRunInfo(TableModelRunInfo parentTableModelRunInfo) {
        this.parentTableModelRunInfo = parentTableModelRunInfo;
    }

    public TableGraph getTableGraph() {
        return this.tableGraph;
    }

    public DataEngineConsts.QueryTableType getMainQueryTableType() {
        return this.tableModelRunInfo.getTableType();
    }

    public TableRelation getParentTableRelation() {
        return this.parentTableRelation;
    }
}

