/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 *  com.jiuqi.bi.syntax.reportparser.IReportContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import com.jiuqi.bi.syntax.reportparser.IReportContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.KeyOrderTempTable;
import com.jiuqi.np.dataengine.common.LoggerConfig;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.common.TwoKeyTempTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatItemCollection;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataMaskingProcesser;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IDimensionRelationProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.query.LinkQueryInfo;
import com.jiuqi.np.dataengine.query.QueryStatLeafHelper;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.dataengine.queryparam.TableNameFindParam;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryContext
implements IReportContext {
    private static final Logger logger = LoggerFactory.getLogger(QueryContext.class);
    protected SystemIdentityService systemIdentityService;
    protected IQueryFieldDataReader dataReader;
    protected MemoryDataSetReader memoryDataSetReader;
    protected final ExecutorContext exeContext;
    private IMonitor monitor;
    protected DimensionValueSet rowKey;
    protected UpdateDatas datas;
    protected DimensionValueSet masterKeys;
    protected int queryRowStart;
    protected final Map<QueryField, Object> rowValuesMap = new HashMap<QueryField, Object>();
    protected final StatItemCollection statItemCollection = new StatItemCollection();
    protected int startRow = -1;
    protected DimensionValueSet currentMasterKey;
    protected final QueryParam queryParam;
    protected boolean isBatch;
    protected boolean needMultiCalc = false;
    protected Map<String, DimensionTable> entityTables;
    protected final Map<QueryTable, String> tableLinkAliaMap = new HashMap<QueryTable, String>();
    protected final Map<QueryTable, String> queryTableAliaMap = new HashMap<QueryTable, String>();
    protected String defaultGroupName;
    protected final Map<Object, Object> cache = new HashMap<Object, Object>();
    protected TempResource tempResource;
    protected String defaultLinkAlias = null;
    protected ZBAuthJudger authJudger;
    protected final Map<Object, PeriodWrapper> periodMap = new HashMap<Object, PeriodWrapper>();
    protected Map<String, Object> varValueCache;
    protected final Map<String, LinkQueryInfo> linkQueryInfoMap = new HashMap<String, LinkQueryInfo>();
    protected DataEngineConsts.DataEngineRunType runnerType;
    protected final IColumnModelFinder columnModelFinder;
    protected boolean queryModule;
    protected boolean multiDimModule = false;
    protected Map<String, DimensionTable> fullEntityTables;
    protected boolean oldQueryModule;
    protected Set<String> rightJoinDimTables;
    protected LoggerConfig loggerConfig = new LoggerConfig();
    protected final IAccountColumnModelFinder accountColumnModelFinder;
    protected boolean needTableRegion = false;
    private Map<QueryTable, String> physicalTableSeach = new HashMap<QueryTable, String>();
    private NrdbHelper nrdbHelper;
    private IDimensionRelationProvider dimensionRelationProvider;
    private DBQueryExecutorProvider queryExecutorProvider;
    private String versionPeriod;
    private boolean zbCalcMode = false;
    private INvwaSystemOptionService nvwaSystemOptionService;
    private boolean isCompatibilityMode = false;
    private boolean evalRelatedUnitsNotFound = false;
    private IUnitLeafFinder unitLeafFinder;
    private QueryStatLeafHelper statLeafHelper;

    public void setTempResource(TempResource tempResource) {
        this.tempResource = tempResource;
    }

    public TempResource getTempResource() {
        return this.tempResource;
    }

    public QueryContext(ExecutorContext exeContext, QueryParam queryParam, IMonitor monitor) throws ParseException {
        this.exeContext = exeContext;
        this.defaultGroupName = exeContext.getDefaultGroupName();
        this.queryParam = queryParam;
        this.monitor = monitor;
        this.columnModelFinder = (IColumnModelFinder)SpringBeanProvider.getBean(IColumnModelFinder.class);
        this.accountColumnModelFinder = (IAccountColumnModelFinder)SpringBeanProvider.getBean(IAccountColumnModelFinder.class);
        this.systemIdentityService = (SystemIdentityService)SpringBeanProvider.getBean(SystemIdentityService.class);
        this.nrdbHelper = (NrdbHelper)SpringBeanProvider.getBean(NrdbHelper.class);
        this.isCompatibilityMode = "1".equals(this.getNvwaSystemOptionService().findValueById("@nr/logic/compatibility-mode")) ? "1".equals(this.getNvwaSystemOptionService().findValueById("@nr/logic/dividing-by-zero")) : false;
        this.evalRelatedUnitsNotFound = "1".equals(this.getNvwaSystemOptionService().findValueById("@nr/logic/compatibility-mode")) ? "1".equals(this.getNvwaSystemOptionService().findValueById("@nr/logic/related-units-not-found")) : false;
        this.setQueryParamToMonitor(queryParam, monitor);
        this.initOutFMLPlan(monitor);
    }

    public QueryContext(ExecutorContext exeContext, IMonitor monitor) throws ParseException {
        this(exeContext, null, monitor);
    }

    public Object readData(QueryField queryField) throws Exception {
        if (this.authJudger != null && !this.authJudger.canRead(queryField.getUID())) {
            return null;
        }
        if (this.rowValuesMap.size() > 0 && this.rowValuesMap.containsKey(queryField)) {
            return this.rowValuesMap.get(queryField);
        }
        if (this.dataReader != null) {
            return this.dataReader.readData(queryField);
        }
        return null;
    }

    public Object readData(QueryFieldInfo queryFieldInfo) throws Exception {
        if (queryFieldInfo != null && this.memoryDataSetReader != null) {
            if (this.authJudger != null && !this.authJudger.canRead(queryFieldInfo.queryField.getUID())) {
                return null;
            }
            return this.memoryDataSetReader.readDataByFieldInfo(queryFieldInfo);
        }
        return this.readData(queryFieldInfo.queryField);
    }

    public Object readData(Long number) throws Exception {
        AbstractData result = null;
        StatItem item = this.statItemCollection.getStatItem(number);
        if (item != null) {
            result = this.isBatch ? item.getResult(this) : item.getResult();
        }
        if (result != null) {
            return result.getAsObject();
        }
        return null;
    }

    public void writeData(QueryField queryField, Object value) {
        this.rowValuesMap.put(queryField, value);
    }

    public void writeData(QueryFieldInfo queryFieldInfo, Object value) {
        if (queryFieldInfo != null && this.memoryDataSetReader != null) {
            this.memoryDataSetReader.getRowDatas().getDatas()[queryFieldInfo.memoryIndex] = value;
        } else {
            this.writeData(queryFieldInfo.queryField, value);
        }
    }

    public IMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
        this.setQueryParamToMonitor(this.queryParam, monitor);
    }

    public FmlExecuteCollector getFmlExecuteCollector() {
        if (this.monitor != null && this.monitor instanceof AbstractMonitor) {
            return ((AbstractMonitor)this.monitor).getCollector();
        }
        return null;
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public void setRowKey(DimensionValueSet rowKey) {
        this.rowValuesMap.clear();
        this.rowKey = rowKey;
        if (rowKey != null && this.isBatch()) {
            DimensionValueSet currentMarsterKeys = this.getCurrentMasterKey();
            for (int i = 0; i < rowKey.size(); ++i) {
                String dimName = rowKey.getName(i);
                if (!currentMarsterKeys.hasValue(dimName)) continue;
                currentMarsterKeys.setValue(dimName, rowKey.getValue(i));
            }
            this.statItemCollection.setCurrentRow(currentMarsterKeys);
        }
    }

    public UpdateDatas getUpdateDatas() {
        return this.datas;
    }

    public IQueryFieldDataReader getDataReader() {
        return this.dataReader;
    }

    public void setDataReader(IQueryFieldDataReader dataReader) {
        this.dataReader = dataReader;
        if (dataReader == null) {
            this.memoryDataSetReader = null;
        }
        if (dataReader instanceof MemoryDataSetReader) {
            this.memoryDataSetReader = (MemoryDataSetReader)dataReader;
        }
    }

    public ExecutorContext getExeContext() {
        return this.exeContext;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
        this.currentMasterKey = new DimensionValueSet(masterKeys);
        if (this.exeContext.getVarDimensionValueSet() == null) {
            this.exeContext.setVarDimensionValueSet(new DimensionValueSet(masterKeys));
        }
    }

    public void setQueryRowStart(int startRowIndex) {
        this.queryRowStart = startRowIndex;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public int getQueryRowStart() {
        return this.queryRowStart;
    }

    public PeriodWrapper getPeriodWrapper() {
        Object period = this.getDimensionValue("DATATIME");
        if (period != null) {
            PeriodWrapper periodWrapper;
            List values;
            if (period instanceof List && (values = ((List)period).stream().map(Object::toString).sorted().collect(Collectors.toList())).size() > 0) {
                period = values.get(values.size() - 1);
            }
            if ((periodWrapper = this.periodMap.get(period)) == null && period instanceof String) {
                periodWrapper = new PeriodWrapper((String)period);
                this.periodMap.put(period, periodWrapper);
            }
            return periodWrapper;
        }
        return null;
    }

    public String getVersionPeriod() {
        if (this.versionPeriod != null) {
            return this.versionPeriod;
        }
        if (this.masterKeys == null) {
            return null;
        }
        Object period = this.masterKeys.getValue("DATATIME");
        if (period != null) {
            List values;
            if (period instanceof List && (values = ((List)period).stream().map(Object::toString).sorted().collect(Collectors.toList())).size() > 0) {
                period = values.get(values.size() - 1);
            }
            this.versionPeriod = period.toString();
        } else {
            PeriodWrapper pw = this.getPeriodWrapper();
            if (pw != null) {
                this.versionPeriod = pw.toString();
            }
        }
        return this.versionPeriod;
    }

    public void setVersionPeriod(String versionPeriod) {
        this.versionPeriod = versionPeriod;
    }

    public DimensionRow getFieldRefDimensionRow(FieldDefine field, Object keyValue) throws Exception {
        if (keyValue == null) {
            return null;
        }
        String entityId = field.getEntityKey();
        return this.getFieldRefDimensionRow(entityId, keyValue);
    }

    public DimensionRow getFieldRefDimensionRow(String entityId, Object keyValue) throws Exception {
        if (keyValue == null) {
            return null;
        }
        DimensionTable dimTable = this.getDimTable(entityId);
        return dimTable.findRowByKey(keyValue.toString());
    }

    public Object getDimensionValue(String dimensionName) {
        Object dimValue = null;
        if (this.exeContext.getVarDimensionValueSet() != null) {
            dimValue = this.exeContext.getVarDimensionValueSet().getValue(dimensionName);
        }
        if ((dimValue == null || dimValue instanceof List || StringUtils.isEmpty((String)dimValue.toString())) && this.currentMasterKey != null) {
            dimValue = this.currentMasterKey.getValue(dimensionName);
        }
        if (dimValue == null && this.masterKeys != null) {
            dimValue = this.masterKeys.getValue(dimensionName);
        }
        return dimValue;
    }

    public DimensionTable getDimTable(String tableCodeOrKey, PeriodWrapper period) throws Exception {
        if (period == null) {
            return this.getDimTable(tableCodeOrKey);
        }
        DimensionTable entityTable = this.getEntityTables().get(tableCodeOrKey + period.toString());
        if (entityTable == null) {
            IDimensionProvider dimensionProvider = this.exeContext.getCache().getDataDefinitionsCache().getDimensionProvider();
            String entityId = null;
            entityId = tableCodeOrKey.indexOf("@") < 0 ? dimensionProvider.getEntityIdByEntityTableCode(this.exeContext, tableCodeOrKey) : tableCodeOrKey;
            if (entityId != null) {
                String dimName = dimensionProvider.getDimensionNameByEntityId(entityId);
                entityTable = dimensionProvider.getDimensionTableByEntityId(this.exeContext, entityId, period, this.getMasterKeys().getValue(dimName));
                if (entityTable != null) {
                    this.getEntityTables().put(entityTable.getTableCode() + period.toString(), entityTable);
                    this.getEntityTables().put(entityTable.getTableKey() + period.toString(), entityTable);
                }
            }
        }
        return entityTable;
    }

    public DimensionTable getDimTable(String tableCodeOrKey) throws Exception {
        DimensionTable entityTable = this.getEntityTables().get(tableCodeOrKey);
        if (entityTable == null) {
            IDimensionProvider dimensionProvider = this.exeContext.getCache().getDataDefinitionsCache().getDimensionProvider();
            String entityId = null;
            entityId = tableCodeOrKey.indexOf("@") < 0 ? dimensionProvider.getEntityIdByEntityTableCode(this.exeContext, tableCodeOrKey) : tableCodeOrKey;
            if (entityId != null) {
                String dimName = dimensionProvider.getDimensionNameByEntityId(entityId);
                entityTable = dimensionProvider.getDimensionTableByEntityId(this.exeContext, entityId, this.getPeriodWrapper(), this.getMasterKeys().getValue(dimName));
                if (entityTable != null) {
                    this.getEntityTables().put(entityTable.getTableCode(), entityTable);
                    this.getEntityTables().put(entityTable.getTableKey(), entityTable);
                    this.getEntityTables().put(tableCodeOrKey, entityTable);
                    this.getEntityTables().put(entityId, entityTable);
                }
            }
        }
        return entityTable;
    }

    public DimensionTable getDimTableByKeyValue(String tableCodeOrKey, Object keyValue, PeriodWrapper period, String linkAlias) throws Exception {
        DimensionTable entityTable = this.getEntityTables().get(tableCodeOrKey);
        IDimensionProvider dimensionProvider = this.exeContext.getCache().getDataDefinitionsCache().getDimensionProvider();
        String entityId = null;
        entityId = tableCodeOrKey.indexOf("@") < 0 ? dimensionProvider.getEntityIdByEntityTableCode(this.exeContext, tableCodeOrKey) : tableCodeOrKey;
        if (entityId != null) {
            entityTable = dimensionProvider.getDimensionTableByEntityId(this.exeContext, entityId, period, keyValue, linkAlias);
        }
        return entityTable;
    }

    public DimensionTable getFullDimTable(String tableCodeOrKey) throws Exception {
        DimensionTable entityTable = this.getFullEntityTables().get(tableCodeOrKey);
        if (entityTable == null) {
            IDimensionProvider dimensionProvider = this.exeContext.getCache().getDataDefinitionsCache().getDimensionProvider();
            String entityId = null;
            entityId = tableCodeOrKey.indexOf("@") < 0 ? dimensionProvider.getEntityIdByEntityTableCode(this.exeContext, tableCodeOrKey) : tableCodeOrKey;
            if (entityId != null) {
                entityTable = dimensionProvider.getDimensionTableByEntityId(this.exeContext, entityId, this.getPeriodWrapper(), null);
                this.getFullEntityTables().put(entityTable.getTableCode(), entityTable);
                this.getFullEntityTables().put(entityTable.getTableKey(), entityTable);
            }
        }
        return entityTable;
    }

    private Map<String, DimensionTable> getFullEntityTables() {
        if (this.fullEntityTables == null) {
            this.fullEntityTables = new HashMap<String, DimensionTable>();
        }
        return this.fullEntityTables;
    }

    public void removeInternalTempTables(Map<String, TempAssistantTable> publicTempTables) {
        if (this.tempResource == null) {
            return;
        }
        Map<String, TempAssistantTable> tempAssistantTables = this.tempResource.getTempAssistantTables();
        for (String dimension : tempAssistantTables.keySet()) {
            if (publicTempTables != null && publicTempTables.containsKey(dimension)) continue;
            tempAssistantTables.remove(dimension);
        }
    }

    public void dropInternalTempTables(Map<String, TempAssistantTable> publicTempTables) {
        if (this.tempResource == null) {
            return;
        }
        Map<String, TempAssistantTable> tempAssistantTables = this.tempResource.getTempAssistantTables();
        ArrayList<String> tempTables = new ArrayList<String>();
        for (String dimension : tempAssistantTables.keySet()) {
            try {
                if (publicTempTables != null && publicTempTables.containsKey(dimension)) continue;
                TempAssistantTable tempAssistantTable = tempAssistantTables.get(dimension);
                tempAssistantTable.dropTempTable(this.queryParam.getConnection());
                tempTables.add(dimension);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        for (String dimension : tempTables) {
            tempAssistantTables.remove(dimension);
        }
    }

    public int generateRowNum(Number start, Number increment) {
        if (this.startRow < 0) {
            this.startRow = start.intValue() + this.queryRowStart;
            return this.startRow;
        }
        this.startRow += increment.intValue();
        return this.startRow;
    }

    public void resetRowNum() {
        this.startRow = -1;
    }

    public boolean isBatch() {
        return this.isBatch;
    }

    public void setBatch(boolean isBatch) {
        this.isBatch = isBatch;
    }

    public DimensionValueSet getCurrentMasterKey() {
        return this.currentMasterKey;
    }

    public void setCurrentMasterKey(DimensionValueSet currentMasterKey) {
        this.currentMasterKey = currentMasterKey;
    }

    public QueryParam getQueryParam() {
        return this.queryParam;
    }

    public boolean isNeedMultiCalc() {
        return this.needMultiCalc;
    }

    public void setNeedMultiCalc(boolean needMultiCalc) {
        this.needMultiCalc = needMultiCalc;
    }

    public Map<QueryTable, String> getTableLinkAliaMap() {
        return this.tableLinkAliaMap;
    }

    private Map<String, DimensionTable> getEntityTables() {
        if (this.entityTables == null) {
            this.entityTables = new HashMap<String, DimensionTable>();
        }
        return this.entityTables;
    }

    public Map<QueryTable, String> getQueryTableAliaMap() {
        return this.queryTableAliaMap;
    }

    public String getDefaultGroupName() {
        return this.defaultGroupName;
    }

    public void setDefaultGroupName(String defaultGroupName) {
        this.defaultGroupName = defaultGroupName;
    }

    public Map<Object, Object> getCache() {
        return this.cache;
    }

    public Map<String, TempAssistantTable> getTempAssistantTables() {
        if (this.tempResource == null) {
            return null;
        }
        return this.tempResource.getTempAssistantTables();
    }

    public TempAssistantTable findTempAssistantTable(String key) {
        if (this.tempResource == null) {
            return null;
        }
        return this.tempResource.getTempAssistantTables().get(key);
    }

    public String getDefaultLinkAlias() {
        return this.defaultLinkAlias;
    }

    public void setDefaultLinkAlias(String defaultLinkAlias) {
        this.defaultLinkAlias = defaultLinkAlias;
    }

    public ZBAuthJudger getAuthJudger() {
        return this.authJudger;
    }

    public void setAulthJuger(ZBAuthJudger authJudger) {
        this.authJudger = authJudger;
    }

    public ReportFormulaParser getFormulaParser() throws ParseException {
        return this.exeContext.getCache().getFormulaParser(this.exeContext);
    }

    public Map<QueryField, Object> getRowValuesMap() {
        return this.rowValuesMap;
    }

    public void setVarValue(String key, Object value) {
        if (this.varValueCache == null) {
            this.varValueCache = new HashMap<String, Object>();
        }
        this.varValueCache.put(key, value);
    }

    public Object getVarValue(String key) {
        if (this.varValueCache != null) {
            return this.varValueCache.get(key);
        }
        return null;
    }

    public Set<String> getUnKnownLinkUnitSet(String linkAlias) {
        if (linkAlias == null) {
            return null;
        }
        LinkQueryInfo info = this.getLinkQueryInfo(linkAlias);
        return info.getUnKnownLinkUnits();
    }

    private LinkQueryInfo getLinkQueryInfo(String linkAlias) {
        LinkQueryInfo info = this.linkQueryInfoMap.get(linkAlias);
        if (info == null) {
            info = new LinkQueryInfo(linkAlias);
            this.linkQueryInfoMap.put(linkAlias, info);
        }
        return info;
    }

    public DataEngineConsts.DataEngineRunType getRunnerType() {
        return this.runnerType;
    }

    public void setRunnerType(DataEngineConsts.DataEngineRunType runnerType) {
        this.runnerType = runnerType;
    }

    public boolean isFormulaRun() {
        return DataEngineConsts.DataEngineRunType.CALCULATE == this.runnerType || DataEngineConsts.DataEngineRunType.CHECK == this.runnerType;
    }

    public IColumnModelFinder getColumnModelFinder() {
        return this.columnModelFinder;
    }

    public boolean isQueryModule() {
        return this.queryModule;
    }

    public void setQueryModule(boolean queryModule) {
        this.queryModule = queryModule;
    }

    public TempAssistantTable getTempAssistantTable(String keyName, Object keyValue, int dimDataType) {
        if (this.isEnableNrdb()) {
            return null;
        }
        if (this.tempResource == null) {
            return null;
        }
        if (keyValue instanceof List) {
            List values = (List)keyValue;
            return this.tempResource.getTempAssistantTable(this.getQueryParam().getDatabase(), keyName, values, dimDataType);
        }
        return null;
    }

    public TwoKeyTempTable findTwoKeyTempTable(String key) {
        if (this.tempResource == null) {
            return null;
        }
        TempAssistantTable tempAssistantTable = this.tempResource.getTempAssistantTables().get(key);
        if (tempAssistantTable instanceof TwoKeyTempTable) {
            return (TwoKeyTempTable)tempAssistantTable;
        }
        return null;
    }

    public KeyOrderTempTable getKeyOrderTempTable(String keyName, List<?> values, int dimDataType) {
        if (this.isEnableNrdb()) {
            return null;
        }
        if (this.tempResource == null) {
            return null;
        }
        return this.tempResource.getKeyOrderTempTable(this.getQueryParam().getDatabase(), keyName, values, dimDataType);
    }

    public TwoKeyTempTable getTwoKeyTempTable(String cacheKey, Map<String, List<String>> keyValues) {
        if (this.isEnableNrdb()) {
            return null;
        }
        if (this.tempResource == null) {
            return null;
        }
        return this.tempResource.getTwoKeyTempTable(cacheKey, keyValues);
    }

    public ColumnModelDefine findColumnModel(String groupName, String refName, String defaultTableName) throws ParseException, Exception {
        DefinitionsCache cache2 = this.getExeContext().getCache();
        DataDefinitionsCache dataDefinitionsCache = cache2.getDataDefinitionsCache();
        DataModelDefinitionsCache dataModelDefinitionsCache = cache2.getDataModelDefinitionsCache();
        ColumnModelDefine columnModel = null;
        FieldDefine fieldDefine = dataDefinitionsCache.parseSearchField(groupName, refName, defaultTableName);
        if (fieldDefine != null) {
            columnModel = this.getColumnModelFinder().findColumnModelDefine(fieldDefine);
        }
        if (columnModel == null) {
            columnModel = dataModelDefinitionsCache.parseSearchField(groupName, refName, defaultTableName);
        }
        return columnModel;
    }

    public String getPhysicalTableName(QueryTable queryTable) {
        String physicalTableName = this.physicalTableSeach.get(queryTable);
        if (physicalTableName == null) {
            physicalTableName = queryTable.getTableName();
            ITableNameFinder tableNameFinder = this.getTableNameFinder();
            if (tableNameFinder != null) {
                if (queryTable.getIsSimple() && !this.getTableLinkAliaMap().containsKey(queryTable)) {
                    physicalTableName = tableNameFinder.findTableName(this.exeContext, new TableNameFindParam(queryTable));
                }
            } else if (this.queryParam.getSplitTableHelper() != null) {
                physicalTableName = this.queryParam.getSplitTableHelper().getCurrentSplitTableName(this.exeContext, physicalTableName);
            }
            this.physicalTableSeach.put(queryTable, physicalTableName);
        }
        return physicalTableName;
    }

    public ITableConditionProvider getTableConditionProvider() {
        ITableNameFinder tableNameFinder = this.getTableNameFinder();
        if (tableNameFinder != null) {
            return tableNameFinder;
        }
        return this.queryParam.getSplitTableHelper();
    }

    public ITableNameFinder getTableNameFinder() {
        ITableNameFinder tableNameFinder = this.exeContext.getEnv() == null ? null : this.exeContext.getEnv().getTableNameFinder();
        return tableNameFinder;
    }

    public String encrypt(String sceneId, String text) throws EncryptionException {
        if (StringUtils.isEmpty((String)sceneId)) {
            return text;
        }
        return this.queryParam.getSymmetricEncryptFactory().createEncryptor(sceneId).encrypt(text);
    }

    public String decrypt(String ciphertext) throws EncryptionException {
        return this.queryParam.getSymmetricEncryptFactory().createDecryptor().decrypt(ciphertext);
    }

    public boolean isOldQueryModule() {
        return this.oldQueryModule;
    }

    public void setOldQueryModule(boolean oldQueryModule) {
        this.oldQueryModule = oldQueryModule;
    }

    public Set<String> getRightJoinDimTables() {
        if (this.rightJoinDimTables == null) {
            this.rightJoinDimTables = new HashSet<String>();
        }
        return this.rightJoinDimTables;
    }

    public boolean isRightJoinTable(String tableName) {
        return this.rightJoinDimTables != null && (this.rightJoinDimTables.contains(tableName) || tableName.startsWith("NR_PERIOD"));
    }

    public boolean needRightJoinDimTables() {
        return this.rightJoinDimTables != null;
    }

    public boolean isAdmin() {
        return this.systemIdentityService.isAdmin();
    }

    private void initOutFMLPlan(IMonitor monitor) {
        boolean fmlplan = this.loggerConfig.isFmlplan();
        if (monitor != null) {
            fmlplan = fmlplan || monitor.isDebug();
        }
        this.loggerConfig.setFmlplan(fmlplan && this.isAdmin());
    }

    public boolean outFMLPlan() {
        return this.loggerConfig.isFmlplan();
    }

    public IAccountColumnModelFinder getAccountColumnModelFinder() {
        return this.accountColumnModelFinder;
    }

    public void setUpdateDatas(UpdateDatas datas) {
        this.datas = datas;
    }

    public boolean isNeedTableRegion() {
        return this.needTableRegion;
    }

    public void setNeedTableRegion(boolean needTableRegion) {
        this.needTableRegion = needTableRegion;
    }

    public Map<String, List<String>> getEffectiveRelationDimValues(List<String> dims, String mainDimValue, String linkAlias, HashMap<String, Object> linkednitKeyMap) {
        HashMap<String, List<String>> map = null;
        IDimensionRelationProvider relationProvider = this.getDimensionRelationProvider();
        if (relationProvider != null) {
            PeriodWrapper periodWrapper = this.getPeriodWrapper();
            for (String dim : dims) {
                List<String> currentRelationDimValues;
                String currentMainDimValue;
                Map<String, Map<Object, Object>> relationDim1V1Map;
                List<String> relationDimValues;
                boolean is1v1RelationDim = this.is1v1RelationDim(dim, linkAlias);
                if (this.masterKeys.hasValue(dim) && !is1v1RelationDim || (relationDimValues = relationProvider.getRelationValuesByDim(this.getExeContext(), dim, mainDimValue, periodWrapper == null ? null : periodWrapper.toString(), linkAlias)) == null) continue;
                if (map == null) {
                    map = new HashMap<String, List<String>>();
                }
                map.put(dim, relationDimValues);
                if (!StringUtils.isNotEmpty((String)linkAlias) || (relationDim1V1Map = this.getRelationDim1V1Map(linkAlias)).size() > 0) continue;
                Map<Object, Object> dimValueMap = relationDim1V1Map.get(dim);
                if (dimValueMap == null) {
                    dimValueMap = new HashMap<Object, Object>();
                    relationDim1V1Map.put(dim, dimValueMap);
                }
                if ((currentMainDimValue = (String)linkednitKeyMap.get(mainDimValue)) == null) {
                    currentMainDimValue = mainDimValue;
                }
                if ((currentRelationDimValues = relationProvider.getRelationValuesByDim(this.getExeContext(), dim, currentMainDimValue, periodWrapper == null ? null : periodWrapper.toString(), null)) == null) continue;
                for (String dimValue : currentRelationDimValues) {
                    dimValueMap.put(currentMainDimValue, dimValue);
                }
            }
        }
        return map;
    }

    public Map<String, Map<String, List<String>>> getAllEffectiveRelationDimValues(List<String> dims, List<String> mainDimValues, String linkAlias, HashMap<String, Object> linkednitKeyMap) {
        HashMap<String, Map<String, List<String>>> map = null;
        IDimensionRelationProvider relationProvider = this.getDimensionRelationProvider();
        if (relationProvider != null) {
            PeriodWrapper periodWrapper = this.getPeriodWrapper();
            for (String dim : dims) {
                Map<String, Map<Object, Object>> relationDim1V1Map;
                Map<String, List<String>> allRelationDimValues;
                if (this.masterKeys.hasValue(dim) && !this.is1v1RelationDim(dim, linkAlias) || (allRelationDimValues = relationProvider.getAllRelationValuesByDim(this.getExeContext(), dim, mainDimValues, periodWrapper == null ? null : periodWrapper.toString(), linkAlias)) == null || allRelationDimValues.size() <= 0) continue;
                if (map == null) {
                    map = new HashMap<String, Map<String, List<String>>>();
                }
                map.put(dim, allRelationDimValues);
                if (!StringUtils.isNotEmpty((String)linkAlias) || (relationDim1V1Map = this.getRelationDim1V1Map(linkAlias)).size() > 0) continue;
                Map<Object, Object> dimValueMap = relationDim1V1Map.get(dim);
                if (dimValueMap == null) {
                    dimValueMap = new HashMap<Object, Object>();
                    relationDim1V1Map.put(dim, dimValueMap);
                }
                ArrayList<String> currentMainDimValues = new ArrayList<String>();
                mainDimValues.forEach(mainDimValue -> {
                    String currentMainDimValue = (String)linkednitKeyMap.get(mainDimValue);
                    if (currentMainDimValue == null) {
                        currentMainDimValue = mainDimValue;
                    }
                    currentMainDimValues.add(currentMainDimValue);
                });
                Map<String, List<String>> allCurrentRelationDimValues = relationProvider.getAllRelationValuesByDim(this.getExeContext(), dim, currentMainDimValues, periodWrapper == null ? null : periodWrapper.toString(), null);
                if (allCurrentRelationDimValues == null) continue;
                for (Map.Entry<String, List<String>> entry : allCurrentRelationDimValues.entrySet()) {
                    String unitKey = entry.getKey();
                    List<String> relationDimValues = entry.getValue();
                    for (String dimValue : relationDimValues) {
                        dimValueMap.put(unitKey, dimValue);
                    }
                }
            }
        }
        return map;
    }

    public Map<String, Map<Object, Object>> getRelationDim1V1Map(String linkAlias) {
        if (linkAlias == null) {
            return null;
        }
        LinkQueryInfo linkQueryInfo = this.getLinkQueryInfo(linkAlias);
        return linkQueryInfo.getRelationDim1V1Map();
    }

    public boolean is1v1RelationDim(String dimension, String linkAlias) {
        IDimensionRelationProvider relationProvider = this.getDimensionRelationProvider();
        return relationProvider != null && relationProvider.is1v1RelationDim(this.exeContext, dimension, linkAlias);
    }

    public List<String> getDimsToBindRelation(TableModelRunInfo tableInfo, QueryTable queryTable, String keyName, String dimensionFieldName) {
        ArrayList<String> dims = new ArrayList<String>();
        DimensionSet tableDimensions = tableInfo.getDimensions();
        for (int i = 0; i < tableDimensions.size(); ++i) {
            String dim = tableDimensions.get(i);
            if (keyName.equals(dim) || queryTable.getDimensionRestriction() != null && queryTable.getDimensionRestriction().hasValue(dim) || tableInfo.isInnerDimension(dim) || "RECORDKEY".equals(dim)) continue;
            dims.add(dim);
        }
        return dims;
    }

    public boolean isEnableNrdb() {
        return this.nrdbHelper.isEnableNrdb();
    }

    private void setQueryParamToMonitor(QueryParam queryParam, IMonitor monitor) {
        if (monitor != null && monitor instanceof AbstractMonitor) {
            AbstractMonitor abstractMonitor = (AbstractMonitor)monitor;
            if (queryParam != null) {
                abstractMonitor.setQueryParam(queryParam);
            }
            abstractMonitor.setLoggerConfig(this.loggerConfig);
        }
    }

    public Object getOption(String key) {
        return this.cache.get(key);
    }

    public StatItemCollection getStatItemCollection() {
        return this.statItemCollection;
    }

    public IDimensionRelationProvider getDimensionRelationProvider() {
        if (this.dimensionRelationProvider == null) {
            this.dimensionRelationProvider = this.exeContext.getEnv();
        }
        return this.dimensionRelationProvider;
    }

    public void setDimensionRelationProvider(IDimensionRelationProvider dimensionRelationProvider) {
        this.dimensionRelationProvider = dimensionRelationProvider;
    }

    public DBQueryExecutorProvider getQueryExecutorProvider() {
        return this.queryExecutorProvider;
    }

    public void setQueryExecutorProvider(DBQueryExecutorProvider queryExecutorProvider) {
        this.queryExecutorProvider = queryExecutorProvider;
    }

    public boolean isZbCalcMode() {
        return this.zbCalcMode;
    }

    public void setZbCalcMode(boolean zbCalcMode) {
        this.zbCalcMode = zbCalcMode;
    }

    public INvwaSystemOptionService getNvwaSystemOptionService() {
        if (this.nvwaSystemOptionService == null) {
            this.nvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
        }
        return this.nvwaSystemOptionService;
    }

    public boolean isCompatibilityMode() {
        return this.isCompatibilityMode;
    }

    public boolean isEvalRelatedUnitsNotFound() {
        return this.evalRelatedUnitsNotFound;
    }

    public boolean isDebug() {
        if (this.monitor != null) {
            return this.monitor.isDebug();
        }
        return this.loggerConfig.isDebug();
    }

    public boolean isSqlLog() {
        return this.loggerConfig.isSqllog();
    }

    public String getMaskingData(ColumnModelDefine field, String value) {
        IDataMaskingProcesser dataMaskingProcesser;
        if (field == null || value == null) {
            return value;
        }
        if (this.exeContext.isAutoDataMasking() && (dataMaskingProcesser = this.queryParam.getDataMaskingProcesser()) != null) {
            return dataMaskingProcesser.getMaskingData(this.exeContext, field, value);
        }
        return value;
    }

    public IUnitLeafFinder getUnitLeafFinder() {
        IFmlExecEnvironment env;
        if (this.unitLeafFinder == null && (env = this.exeContext.getEnv()) != null) {
            this.unitLeafFinder = env.getUnitLeafFinder();
        }
        return this.unitLeafFinder;
    }

    public void addLeafParent(String leafKey, String parentKey) {
        this.getStatLeafHelper().addLeafParent(leafKey, parentKey);
    }

    public QueryStatLeafHelper getStatLeafHelper() {
        if (this.statLeafHelper == null) {
            this.statLeafHelper = new QueryStatLeafHelper(this.exeContext.getUnitDimension());
        }
        return this.statLeafHelper;
    }

    public boolean needStatLeaf() {
        return this.statLeafHelper != null;
    }

    public DateFormat getDateFormat() {
        if (this.monitor != null && this.monitor instanceof AbstractMonitor) {
            return ((AbstractMonitor)this.monitor).getDateFormat();
        }
        return null;
    }

    public CostCalculator getCostCalculator(ASTNodeType nodeType, String name) {
        FmlExecuteCollector fmlExecuteCollector = this.getFmlExecuteCollector();
        if (fmlExecuteCollector != null && ASTNodeType.FUNCTION == nodeType) {
            return fmlExecuteCollector.getNodeCostCollector().getFuncCostCalculator(name);
        }
        return null;
    }

    public Map<String, LinkQueryInfo> getLinkQueryInfoMap() {
        return this.linkQueryInfoMap;
    }
}

