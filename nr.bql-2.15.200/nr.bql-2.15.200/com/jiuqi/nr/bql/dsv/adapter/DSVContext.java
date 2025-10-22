/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.dataengine.adapt.QueryColumnModelFinder;
import com.jiuqi.nr.bql.dataengine.adapt.QueryDataModelDefinitionsCache;
import com.jiuqi.nr.bql.dsv.adapter.PeriodDimensionInfo;
import com.jiuqi.nr.bql.extend.model.QueryColumnModel;
import com.jiuqi.nr.bql.extend.model.QueryTableModel;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DSVContext {
    private DSV dsv;
    private DataScheme dataScheme;
    private PeriodDimensionInfo periodDimensionInfo;
    private String mainDimensionEntityId;
    private List<String> unitScopeEntityKeys;
    private Map<String, TableInfo> periodTableInfos = new HashMap<String, TableInfo>();
    private ExecutorContext executorContext = null;
    private Map<String, String> fieldDimNameMap = new HashMap<String, String>();
    private Set<String> ignoreDims = new HashSet<String>();
    private TableInfo mdInfoTable;
    private TableInfo mdTable;
    private boolean queryDataScheme;
    private List<DataTable> allDataTables;
    private Map<String, DataFieldDeployInfo> deployInfoSearch;
    private Map<String, DataField> dataFieldSearch;
    private Map<String, List<DataField>> dataTableFields;
    private Map<String, DataTable> dataTableSearch;
    private Map<String, TableModelRunInfo> tableInfos = new HashMap<String, TableModelRunInfo>();

    public DSVContext(DSV dsv, DataScheme dataScheme, IDataDefinitionRuntimeController dataDefinitionRuntimeController) {
        this.dsv = dsv;
        this.dataScheme = dataScheme;
        this.queryDataScheme = dataScheme.getType() == DataSchemeType.QUERY;
    }

    public void doInit(IRuntimeDataSchemeService dataSchemeService, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, IDataDefinitionRuntimeController dataDefinitionRuntimeController) throws Exception {
        this.initExecutorContext(dataSchemeService, entityMetaService, dataDefinitionRuntimeController);
        this.initByDataDimensions(dataSchemeService, entityMetaService, periodEngineService);
        this.allDataTables = dataSchemeService.getAllDataTable(this.dataScheme.getKey());
        this.dataTableSearch = this.allDataTables.stream().collect(Collectors.toMap(Basic::getKey, v -> v, (v1, v2) -> v1));
        this.deployInfoSearch = dataSchemeService.getDeployInfoBySchemeKey(this.dataScheme.getKey()).stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, v -> v, (v1, v2) -> v1));
        List allDataFields = dataSchemeService.getAllDataField(this.dataScheme.getKey());
        this.dataFieldSearch = allDataFields.stream().collect(Collectors.toMap(Basic::getKey, v -> v, (v1, v2) -> v1));
        this.dataTableFields = allDataFields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
    }

    private void initByDataDimensions(IRuntimeDataSchemeService dataSchemeService, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService) {
        List dataDimensions = dataSchemeService.getDataSchemeDimension(this.dataScheme.getKey());
        for (DataDimension dataDimension : dataDimensions) {
            DimensionType dimensionType = dataDimension.getDimensionType();
            if (dimensionType == DimensionType.PERIOD) {
                IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
                TableModelDefine tableModel = periodAdapter.getPeriodEntityTableModel(dataDimension.getDimKey());
                PeriodDimensionInfo info = new PeriodDimensionInfo(tableModel, dataDimension.getPeriodType(), dataDimension.getDimKey());
                this.setPeriodDimensionInfo(info);
                continue;
            }
            if (dimensionType == DimensionType.UNIT) {
                this.setMainDimensionEntityId(dataDimension.getDimKey());
                continue;
            }
            if (dimensionType == DimensionType.UNIT_SCOPE) {
                this.getUnitScopeEntityKeys().add(dataDimension.getDimKey());
                continue;
            }
            String dimName = dataDimension.getDimKey();
            IEntityDefine unitEntity = entityMetaService.queryEntity(dataDimension.getDimKey());
            if (unitEntity == null) continue;
            dimName = unitEntity.getDimensionName();
        }
        for (DataDimension dataDimension : dataDimensions) {
            if (!StringUtils.isNotEmpty((String)dataDimension.getDimAttribute())) continue;
            String dimEntityId = dataDimension.getDimKey();
            IEntityModel entityModel = entityMetaService.getEntityModel(this.getMainDimensionEntityId());
            IEntityAttribute arttri = entityModel.getAttribute(dataDimension.getDimAttribute());
            if (arttri == null || arttri.isMultival()) continue;
            this.getIgnoreDims().add(dimEntityId);
        }
    }

    private void initExecutorContext(IRuntimeDataSchemeService dataSchemeService, IEntityMetaService entityMetaService, IDataDefinitionRuntimeController dataDefinitionRuntimeController) throws ParseException {
        this.executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        if (this.dataScheme.getType() == DataSchemeType.QUERY) {
            DefinitionsCache cache = new DefinitionsCache(this.executorContext);
            QueryColumnModelFinder queryColumnModelFinder = new QueryColumnModelFinder(dataSchemeService, entityMetaService, cache.getDataModelDefinitionsCache().getColumnModelFinder());
            QueryDataModelDefinitionsCache dataModelcache = new QueryDataModelDefinitionsCache(this.executorContext, this.executorContext.getCache().getFormulaParser(true), dataSchemeService, entityMetaService, queryColumnModelFinder);
            dataModelcache.setDataDefinitionsCache(cache.getDataDefinitionsCache());
            cache.setDataModelDefinitionsCache((DataModelDefinitionsCache)dataModelcache);
            this.executorContext.setCache(cache);
        }
    }

    public PeriodDimensionInfo getPeriodDimensionInfo() {
        return this.periodDimensionInfo;
    }

    public void setPeriodDimensionInfo(PeriodDimensionInfo periodDimensionInfo) {
        this.periodDimensionInfo = periodDimensionInfo;
    }

    public DSV getDsv() {
        return this.dsv;
    }

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public Map<String, TableInfo> getPeriodTableInfos() {
        return this.periodTableInfos;
    }

    public boolean isCustomPeriod() {
        if (this.periodDimensionInfo == null) {
            return false;
        }
        PeriodType periodType = this.periodDimensionInfo.getPeriodType();
        return periodType == PeriodType.CUSTOM || periodType == PeriodType.WEEK;
    }

    public boolean isQueryDataScheme() {
        return this.queryDataScheme;
    }

    public String getMainDimensionEntityId() {
        return this.mainDimensionEntityId;
    }

    public void setMainDimensionEntityId(String mainDimensionEntityId) {
        this.mainDimensionEntityId = mainDimensionEntityId;
    }

    public List<String> getUnitScopeEntityKeys() {
        if (this.unitScopeEntityKeys == null) {
            this.unitScopeEntityKeys = new ArrayList<String>();
        }
        return this.unitScopeEntityKeys;
    }

    public boolean hasUnitScope() {
        return this.unitScopeEntityKeys != null && this.unitScopeEntityKeys.size() > 0;
    }

    public Map<String, String> getFieldDimNameMap() {
        return this.fieldDimNameMap;
    }

    public Set<String> getIgnoreDims() {
        return this.ignoreDims;
    }

    public boolean dimCanIgnroe(String entityId) {
        return this.ignoreDims.size() > 0 && this.ignoreDims.contains(entityId);
    }

    public TableInfo getMdInfoTable() {
        return this.mdInfoTable;
    }

    public void setMdInfoTable(TableInfo mdInfoTable) {
        this.mdInfoTable = mdInfoTable;
    }

    public TableInfo getMdTable() {
        return this.mdTable;
    }

    public void setMdTable(TableInfo mdTable) {
        this.mdTable = mdTable;
    }

    public List<DataTable> getAllDataTables() {
        return this.allDataTables;
    }

    public DataTable getDataTable(String tableKey) {
        return this.dataTableSearch.get(tableKey);
    }

    public DataField getDataField(String fieldKey) {
        return this.dataFieldSearch.get(fieldKey);
    }

    public List<DataField> getTableFields(String tableKey) {
        return this.dataTableFields.get(tableKey);
    }

    public DataFieldDeployInfo getDataFieldDeployInfo(String fieldKey) {
        return this.deployInfoSearch.get(fieldKey);
    }

    public String getColumnDimensionName(DataModelService dataModelService, DataTable dataTable, ColumnModelDefine columnModel) throws Exception {
        TableModelRunInfo tableInfo = this.tableInfos.get(columnModel.getTableID());
        if (tableInfo == null) {
            DataModelDefinitionsCache dataModelDefinitionsCache = this.executorContext.getCache().getDataModelDefinitionsCache();
            if (this.dataScheme.getType() == DataSchemeType.QUERY) {
                QueryDataModelDefinitionsCache dataModelcache = (QueryDataModelDefinitionsCache)dataModelDefinitionsCache;
                List<ColumnModelDefine> allFields = dataModelcache.getQueryColumnModels(dataTable);
                QueryTableModel tableDefine = new QueryTableModel(dataTable, ((QueryColumnModel)allFields.get(0)).getDeployInfo().getTableName());
                tableInfo = new TableModelRunInfo((TableModelDefine)tableDefine, allFields, null);
                tableInfo.buildTableDimension(dataModelDefinitionsCache);
                this.tableInfos.put(tableDefine.getID(), tableInfo);
            } else {
                TableModelDefine tableDefine = dataModelService.getTableModelDefineById(columnModel.getTableID());
                List allFields = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                tableInfo = new TableModelRunInfo(tableDefine, allFields, null);
                tableInfo.buildTableDimension(dataModelDefinitionsCache);
                this.tableInfos.put(tableDefine.getID(), tableInfo);
            }
        }
        return tableInfo.getDimensionName(columnModel.getCode());
    }
}

