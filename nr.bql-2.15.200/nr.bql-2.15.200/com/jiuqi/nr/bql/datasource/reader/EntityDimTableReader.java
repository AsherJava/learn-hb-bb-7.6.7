/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.reader.DataField
 *  com.jiuqi.bi.adhoc.datasource.reader.DataPage
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.model.PageInfo
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.bql.datasource.reader;

import com.jiuqi.bi.adhoc.datasource.reader.DataField;
import com.jiuqi.bi.adhoc.datasource.reader.DataPage;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.model.PageInfo;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.UnitChekMonitor;
import com.jiuqi.nr.bql.datasource.UnitFilter;
import com.jiuqi.nr.bql.datasource.UnitFilterExpInfo;
import com.jiuqi.nr.bql.datasource.parse.ParseInfo;
import com.jiuqi.nr.bql.datasource.reader.EntityRowComparator;
import com.jiuqi.nr.bql.intf.ICalibreDimTableProvider;
import com.jiuqi.nr.bql.util.FilterUtils;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDimTableReader {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ICalibreDimTableProvider calibreDimTableProvider;
    @Autowired
    public ICalibreDataService calibreDataService;
    @Autowired
    private UnitFilter unitfilter;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private BaseDataClient baseDataClient;

    public long queryEntityDimTable(QueryContext qContext, DataTable dataTable, IDataListener listener, DataPage dataPage, DimensionValueSet masterKeys, DataTable periodTable) throws Exception {
        IEntityDefine unitEntityDefine;
        qContext.setDefaultTableName(dataTable.getTable().getTableName());
        String tableCode = qContext.getTableCode(dataTable);
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(tableCode);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        Metadata metadata = new Metadata();
        int h_orderIndex = -1;
        HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
        for (Object dataField : dataTable.getFields()) {
            if (!dataField.isVisible()) continue;
            Column column = new Column(dataField.getReturnName(), dataField.getField().getDataType());
            metadata.addColumn(column);
            int index = column.getIndex();
            columnMap.put(dataField.getField().getPhysicalName(), index);
            if (!dataField.getField().getName().equals("H_ORDER")) continue;
            h_orderIndex = index;
        }
        HashMap<String, Integer> periodColumnMap = null;
        if (periodTable != null) {
            for (DataField dataField : periodTable.getFields()) {
                if (!dataField.isVisible()) continue;
                if (periodColumnMap == null) {
                    periodColumnMap = new HashMap<String, Integer>();
                }
                Column column = new Column(dataField.getReturnName(), dataField.getField().getDataType());
                metadata.addColumn(column);
                int index = column.getIndex();
                periodColumnMap.put(dataField.getField().getName(), index);
            }
        }
        if (masterKeys == null) {
            masterKeys = new DimensionValueSet();
        }
        qContext.getTableInfoMap().put(dataTable.getTable().getTableName(), dataTable.getTable().getTable());
        String dimensionName = entityDefine.getDimensionName();
        StringBuilder filterBuff = this.parseEntityFilter(qContext, masterKeys, dataTable, entityModel, dimensionName);
        if (h_orderIndex >= 0) {
            qContext.setNeedHOrder(dimensionName);
        }
        String rowFilter = null;
        if (filterBuff.length() > 0) {
            try {
                ParseInfo info = new ParseInfo();
                info.setEntityFilter(true);
                rowFilter = FilterUtils.transFormula(qContext, filterBuff.toString(), info);
                qContext.setUnitEntityFilter(rowFilter);
            }
            catch (ParseException e) {
                qContext.getReadContext().getLogger().error(e.getMessage(), (Throwable)e);
            }
            catch (InterpretException e) {
                qContext.getReadContext().getLogger().error(e.getMessage(), (Throwable)e);
            }
        }
        listener.start(metadata);
        List<IEntityRow> entityRows = new ArrayList<IEntityRow>();
        if (qContext.getReadContext().getOptions().containsKey("TreeRootOnly")) {
            IEntityTable entityTable = this.getEntityTable(qContext, entityDefine, masterKeys, null, true, true);
            entityRows.addAll(entityTable.getRootRows());
        } else {
            entityRows.addAll(this.getEntityRows(qContext, entityDefine, masterKeys, null, true, true));
        }
        UnitFilterExpInfo unitFilterExpInfo = qContext.getUnitFilterExpInfo(this.calibreDataService);
        if (unitFilterExpInfo != null && (unitEntityDefine = qContext.getUnitEntityDefine()) != null && unitEntityDefine.getDimensionName().equals(dimensionName)) {
            this.filterUnits(masterKeys, qContext, dimensionName, entityRows, unitFilterExpInfo);
        }
        int totalCount = entityRows.size();
        if (entityRows.size() == 0) {
            listener.finish();
            return totalCount;
        }
        HashMap<String, Integer> orderCache = null;
        if (h_orderIndex >= 0) {
            orderCache = new HashMap<String, Integer>();
            Object dimValue = masterKeys.getValue(dimensionName);
            if (dimValue != null && dimValue instanceof List) {
                List dimValues = (List)dimValue;
                for (int order = 0; order < dimValues.size(); ++order) {
                    String value = (String)dimValues.get(order);
                    orderCache.put(value, order);
                }
            } else {
                for (int order = 0; order < entityRows.size(); ++order) {
                    IEntityRow row = entityRows.get(order);
                    orderCache.put(row.getEntityKeyData(), order);
                }
            }
        }
        if (dataPage != null) {
            this.doOrderBy(dataPage, entityRows, orderCache);
            entityRows = this.doPage(dataPage, entityRows);
        }
        if (!CollectionUtils.isEmpty(entityRows)) {
            int orderIndex = h_orderIndex;
            HashMap<String, Integer> cache = orderCache;
            int orderDataType = orderIndex >= 0 ? metadata.getColumn(orderIndex).getDataType() : 10;
            IPeriodRow periodRow = null;
            if (periodColumnMap != null && qContext.getVersionPeriod() != null) {
                periodRow = this.periodEngineService.getPeriodAdapter().getPeriodProvider(qContext.getPeriodEntityId()).getPeriodItems().stream().filter(period -> period.getCode().equals(qContext.getVersionPeriod())).findAny().get();
            }
            for (IEntityRow entityRow : entityRows) {
                MemoryDataRow row = new MemoryDataRow(metadata.getColumnCount());
                columnMap.forEach((arg_0, arg_1) -> EntityDimTableReader.lambda$queryEntityDimTable$1(orderIndex, cache, entityRow, orderDataType, (DataRow)row, arg_0, arg_1));
                if (periodRow != null) {
                    for (Map.Entry entry : periodColumnMap.entrySet()) {
                        String fieldName = (String)entry.getKey();
                        int index = (Integer)entry.getValue();
                        Object value = this.getPeriodFieldValue(periodRow, fieldName);
                        if (value == null) continue;
                        row.setValue(index, value);
                    }
                }
                listener.process((DataRow)row);
            }
        }
        listener.finish();
        return totalCount;
    }

    protected void filterUnits(DimensionValueSet masterKeys, QueryContext qContext, String dimensionName, List<IEntityRow> entityRows, UnitFilterExpInfo unitFilterExpInfo) throws Exception {
        DimensionValueSet dim = new DimensionValueSet(masterKeys);
        ArrayList<String> unitKeys = new ArrayList<String>(entityRows.size());
        for (IEntityRow row : entityRows) {
            unitKeys.add(row.getEntityKeyData());
        }
        dim.setValue(dimensionName, unitKeys);
        UnitChekMonitor condigionMonitor = new UnitChekMonitor(dim);
        condigionMonitor.setMainDim(dimensionName);
        this.unitfilter.judgeUnitCondition(qContext, dim, condigionMonitor, unitFilterExpInfo, this);
        Set<Object> filteredKeys = condigionMonitor.getFilteredKeys();
        Iterator<IEntityRow> it = entityRows.iterator();
        while (it.hasNext()) {
            if (!filteredKeys.contains(it.next().getEntityKeyData())) continue;
            it.remove();
        }
    }

    private Object getPeriodFieldValue(IPeriodRow periodRow, String fieldName) {
        if (fieldName.equals(PeriodTableColumn.TIMEKEY.getCode())) {
            return periodRow.getTimeKey();
        }
        if (fieldName.equals(PeriodTableColumn.YEAR.getCode())) {
            return periodRow.getYear();
        }
        if (fieldName.equals(PeriodTableColumn.QUARTER.getCode())) {
            return periodRow.getQuarter();
        }
        if (fieldName.equals(PeriodTableColumn.MONTH.getCode())) {
            return periodRow.getMonth();
        }
        if (fieldName.equals(PeriodTableColumn.DAY.getCode())) {
            return periodRow.getDay();
        }
        if (fieldName.equals(PeriodTableColumn.CODE.getCode())) {
            return periodRow.getCode();
        }
        if ("SYS_DAYS".equals(fieldName)) {
            return periodRow.getDays();
        }
        if ("SYS_LASTDAY".equals(fieldName)) {
            return periodRow.getEndDate();
        }
        return null;
    }

    private List<IEntityRow> doPage(DataPage dataPage, List<IEntityRow> entityRows) {
        PageInfo pageInfo = dataPage.getPageInfo();
        if (pageInfo != null && PageInfo.isPageable((PageInfo)pageInfo)) {
            pageInfo.setRecordSize(entityRows.size());
            int recordStart = pageInfo.getRecordStart();
            if (recordStart >= entityRows.size()) {
                return new ArrayList<IEntityRow>();
            }
            int recordEnd = pageInfo.getRecordEnd();
            if (recordEnd > entityRows.size()) {
                recordEnd = entityRows.size();
            }
            List<IEntityRow> pageRows = entityRows.subList(recordStart, recordEnd);
            entityRows = pageRows;
        }
        return entityRows;
    }

    private void doOrderBy(DataPage dataPage, List<IEntityRow> entityRows, Map<String, Integer> orderCache) {
        List orderBys = dataPage.getOrderBys();
        if (orderBys != null && orderBys.size() > 0) {
            for (int i = 0; i < orderBys.size(); ++i) {
            }
            Collections.sort(entityRows, new EntityRowComparator(orderBys, orderCache));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void processEntityFieldFilter(QueryContext qContext, DimensionValueSet masterKeys, DataTable dataTable, String mdInfoFilter) throws JQException, ParseException, InterpretException {
        List<?> dimValues;
        int dimensionIndex;
        IEntityDefine entityDefine = this.getEntityDefineByTable(qContext, dataTable);
        String dimensionName = entityDefine.getDimensionName();
        if (!dimensionName.equals(dataTable.getTable().getTableName()) && (dimensionIndex = dataTable.getTable().getTableName().indexOf(entityDefine.getDimensionName())) > 0) {
            dimensionName = dataTable.getTable().getTableName().substring(0, dimensionIndex - 1);
        }
        if ((dimValues = qContext.getDimValuesFromCache(dimensionName)) != null) {
            if (dimensionName.equals(qContext.getUnitEntityDefine().getDimensionName()) && qContext.ignoreUnitDim()) {
                return;
            }
            masterKeys.setValue(dimensionName, dimValues);
            return;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        StringBuilder filterBuff = this.parseEntityFilter(qContext, masterKeys, dataTable, entityModel, dimensionName);
        if (qContext.isNeedHOrder(dimensionName) && qContext.getOrderCache(dimensionName) == null) {
            long start = System.currentTimeMillis();
            List<IEntityRow> rows = this.getEntityRows(qContext, entityDefine, masterKeys, null, false, true);
            ArrayList<String> values = new ArrayList<String>();
            for (IEntityRow row : rows) {
                values.add(row.getEntityKeyData());
            }
            qContext.initOrderCache(dimensionName, values);
            long end = System.currentTimeMillis();
            qContext.getLogger().debug("\u7ef4\u5ea6 " + dimensionName + "\u6ca1\u6709\u6307\u5b9a\u5217\u8868\u987a\u5e8f\uff0c\u9700\u8981\u7528\u5b8c\u6574\u6811\u578b\u521d\u59cb\u5316H_ORDER\u7f13\u5b58,\u8017\u65f6" + (end - start) + "ms");
        }
        if (StringUtils.isNotEmpty((String)mdInfoFilter)) {
            FilterUtils.appendFilter(filterBuff, mdInfoFilter);
        }
        if (filterBuff.length() > 0) {
            qContext.setDefaultTableName(dataTable.getTable().getTableName());
            ParseInfo info = new ParseInfo();
            info.setEntityFilter(true);
            String entityRowFilter = filterBuff.toString();
            IExpression exp = FilterUtils.parseFilterFormula(entityRowFilter, qContext);
            boolean filteredBySql = false;
            if (dimensionName.equals(qContext.getUnitEntityDefine().getDimensionName()) && !qContext.isEliminateUnitDim() && exp.support(Language.SQL)) {
                com.jiuqi.np.dataengine.query.QueryContext unitCheckContext = null;
                try (TempResource tempResource = new TempResource();){
                    String period = this.unitfilter.getVersionPeriod(qContext, masterKeys);
                    UnitChekMonitor condigionMonitor = new UnitChekMonitor(masterKeys);
                    condigionMonitor.setMainDim(dimensionName);
                    unitCheckContext = this.unitfilter.createUnitCheckContext(qContext, null, tempResource, masterKeys, period, null, condigionMonitor);
                    String transdEntityRowFilter = exp.interpret((IContext)unitCheckContext, Language.FORMULA, (Object)info);
                    ReportFormulaParser formulaParser = qContext.getExeContext().getCache().getFormulaParser(true);
                    IExpression condition = formulaParser.parseEval(transdEntityRowFilter, (IContext)unitCheckContext);
                    filteredBySql = this.unitfilter.filterBySql(qContext, masterKeys, period, unitCheckContext, entityRowFilter, condition, dimensionName);
                }
                catch (Exception e) {
                    qContext.getLogger().error(e.getMessage(), (Throwable)e);
                }
                finally {
                    if (unitCheckContext != null) {
                        unitCheckContext.getQueryParam().closeConnection();
                    }
                }
            }
            if (!filteredBySql) {
                String transdEntityRowFilter = exp.interpret((IContext)qContext, Language.FORMULA, (Object)info);
                if (entityDefine.getDimensionName().equals(qContext.getUnitEntityDefine().getDimensionName()) && !masterKeys.hasValue(entityDefine.getDimensionName())) {
                    qContext.setUnitEntityFilter(transdEntityRowFilter);
                } else {
                    List<String> filteredKeys = this.getFilteredKeys(qContext, masterKeys, entityDefine, transdEntityRowFilter);
                    StringBuilder buff = new StringBuilder();
                    buff.append("\u7ef4\u5ea6\u8868\u8fc7\u6ee4\u516c\u5f0f\uff1a").append(entityRowFilter).append("\u7684\u6267\u884c\u7ed3\u679c\uff1a\n");
                    FilterUtils.printValueList(buff, filteredKeys);
                    buff.append("\n \u8f6c\u6362\u6210\u4e3b\u7ef4\u5ea6").append(dimensionName).append("\u7684\u53d6\u503c\u8303\u56f4");
                    qContext.getLogger().debug(buff.toString());
                    masterKeys.setValue(dimensionName, filteredKeys);
                }
            }
        }
    }

    public List<String> getFilteredKeys(QueryContext qContext, DimensionValueSet masterKeys, IEntityDefine entityDefine, String entityRowFilter) {
        List<IEntityRow> rows = this.getEntityRows(qContext, entityDefine, masterKeys, entityRowFilter, false, false);
        ArrayList<String> filteredKeys = new ArrayList<String>();
        for (IEntityRow row : rows) {
            filteredKeys.add(row.getEntityKeyData());
        }
        return filteredKeys;
    }

    public IEntityDefine getEntityDefineByTable(QueryContext qContext, DataTable dataTable) {
        String tableCode = dataTable.getTable().getTableName();
        if (qContext != null) {
            tableCode = qContext.getTableCode(dataTable);
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(tableCode);
        return entityDefine;
    }

    public boolean isEnableAuthority(String entityId) throws JQException {
        if (entityId == null) {
            return false;
        }
        return this.entityAuthorityService.isEnableAuthority(entityId) && !this.isSystemIdentity();
    }

    public IEntityDefine getEntityDefineByKey(QueryContext qContext, String entityId) throws JQException {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        if (entityDefine == null) {
            return null;
        }
        String dimName = entityDefine.getDimensionName();
        String tableCode = qContext.getDimTableCode(dimName, entityDefine.getCode());
        return this.entityMetaService.queryEntityByCode(tableCode);
    }

    public List<IEntityRow> getEntityRows(QueryContext qContext, IEntityDefine entityDefine, DimensionValueSet masterKeys, String filter, boolean judgeAuth, boolean sorted) {
        ArrayList<IEntityRow> allRows = new ArrayList();
        IEntityTable entityTable = this.getEntityTable(qContext, entityDefine, masterKeys, filter, judgeAuth, sorted);
        allRows = entityTable.getAllRows();
        return allRows;
    }

    private IEntityTable getEntityTable(QueryContext qContext, IEntityDefine entityDefine, DimensionValueSet masterKeys, String filter, boolean judgeAuth, boolean sorted) {
        IEntityTable entityTable = null;
        try {
            String superUnitAuth;
            IPeriodProvider periodProvider;
            DimensionValueSet dim = new DimensionValueSet();
            String dimensionName = entityDefine.getDimensionName();
            String versionPeriod = null;
            for (int i = 0; i < masterKeys.size(); ++i) {
                String dimName = masterKeys.getName(i);
                if (dimName.equals(dimensionName)) {
                    dim.setValue(dimName, masterKeys.getValue(i));
                    continue;
                }
                if (!dimName.equals("DATATIME")) continue;
                versionPeriod = this.unitfilter.getVersionPeriod(qContext, masterKeys);
                dim.setValue(dimName, (Object)versionPeriod);
            }
            if (!dim.hasValue("DATATIME") && StringUtils.isNotEmpty((String)qContext.getPeriodEntityId()) && (periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(qContext.getPeriodEntityId())) != null) {
                versionPeriod = periodProvider.getCurPeriod().getCode();
                dim.setValue("DATATIME", (Object)versionPeriod);
            }
            AuthorityType authType = "true".equals(superUnitAuth = (String)qContext.getReadContext().getOptions().get("NR.superUnitAuthority")) ? AuthorityType.None : AuthorityType.Read;
            qContext.setVersionPeriod(versionPeriod);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setAuthorityOperations(authType);
            iEntityQuery.setMasterKeys(dim);
            iEntityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityDefine.getId()));
            iEntityQuery.setExpression(filter);
            iEntityQuery.sorted(sorted);
            iEntityQuery.queryStopModel(0);
            ExecutorContext entityExecutorContext = new ExecutorContext(this.dataDefinitionController);
            entityExecutorContext.setPeriodView(qContext.getPeriodEntityId());
            if (this.calibreDimTableProvider != null) {
                IFmlExecEnvironment env = qContext.getExeContext().getEnv();
                if (env != null) {
                    entityExecutorContext.setEnv(env);
                }
                VariableManager vm = qContext.getExeContext().getVariableManager();
                for (Variable var : vm.getAllVars()) {
                    VariableManager entityVm = entityExecutorContext.getVariableManager();
                    entityVm.add(var);
                }
            }
            entityTable = iEntityQuery.executeReader((IContext)entityExecutorContext);
        }
        catch (Exception e) {
            qContext.getReadContext().getLogger().error(e.getMessage(), (Throwable)e);
        }
        return entityTable;
    }

    public StringBuilder parseEntityFilter(QueryContext qContext, DimensionValueSet masterKeys, DataTable dataTable, IEntityModel entityModel, String dimensionName) {
        StringBuilder filterBuff = new StringBuilder();
        if (StringUtil.isNotEmpty((String)dataTable.getFilter())) {
            FilterUtils.appendFilter(filterBuff, dataTable.getFilter());
        }
        for (DataField field : dataTable.getFields()) {
            String fieldFilter = field.getFilter();
            if (StringUtil.isNotEmpty((String)fieldFilter)) {
                qContext.getLogger().debug("\u7ef4\u5ea6\u8868\u5b57\u6bb5" + field.getField().getName() + " entityFieldFilter (" + fieldFilter + ") append to entityRowFilter");
                FilterUtils.appendFilter(filterBuff, fieldFilter);
            }
            if (field.getValues() == null || field.getValues().size() <= 0) continue;
            if (field.getField().getName().equals(entityModel.getBizKeyField().getCode())) {
                StringBuilder buff = new StringBuilder();
                buff.append("\u7ef4\u5ea6\u8868\u5b57\u6bb5").append(field.getField().getName()).append("\u7684\u53d6\u503c\u5217\u8868  ");
                FilterUtils.printValueList(buff, field.getValues());
                buff.append(" \u8f6c\u6362\u4e3a\u4e3b\u7ef4\u5ea6").append(dimensionName).append("\u7684\u53d6\u503c\u8303\u56f4");
                qContext.getLogger().debug(buff.toString());
                qContext.initOrderCache(dimensionName, field.getValues());
                masterKeys.setValue(dimensionName, (Object)field.getValues());
                continue;
            }
            StringBuilder buf = new StringBuilder();
            buf.append(dimensionName).append(".").append(field.getField().getName());
            buf.append(" in {");
            for (String value : field.getValues()) {
                buf.append("'").append(value).append("',");
            }
            buf.setLength(buf.length() - 1);
            buf.append("}");
            String infilter = buf.toString();
            qContext.getLogger().debug("\u7ef4\u5ea6\u8868\u5b57\u6bb5" + field.getField().getName() + "\u7684\u53d6\u503c\u5217\u8868 " + field.getValues() + "\u8f6c\u6362\u4e3a\u8868\u8fbe\u5f0f " + infilter);
            FilterUtils.appendFilter(filterBuff, infilter);
        }
        return filterBuff;
    }

    public boolean isSystemIdentity() {
        String identityId = NpContextHolder.getContext().getIdentityId();
        return this.systemIdentityService.isSystemIdentity(identityId);
    }

    public List<String> getAuthList(QueryContext qContext, DimensionValueSet masterKeys, IEntityDefine entityDefine, List<IEntityRow> authEntityRows) throws UnauthorizedEntityException, JQException {
        ArrayList<String> authList = new ArrayList<String>();
        boolean isUnitDim = qContext.isUnitDim(entityDefine.getDimensionName());
        boolean sorted = false;
        if (isUnitDim && qContext.isEliminateUnitDim()) {
            sorted = true;
        }
        List<IEntityRow> rows = this.getEntityRows(qContext, entityDefine, masterKeys, null, true, sorted);
        if (isUnitDim) {
            qContext.putMainDimTitlesToCache(rows);
        }
        authEntityRows.addAll(rows);
        for (IEntityRow row : rows) {
            authList.add(row.getEntityKeyData());
        }
        return authList;
    }

    public int getAllUnitCount(QueryContext qContext) throws Exception {
        String unitTableName = qContext.getUnitEntityDefine().getCode();
        Date[] dateRegion = null;
        String versionPeriod = qContext.getVersionPeriod();
        if (versionPeriod != null) {
            dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(new PeriodWrapper(versionPeriod));
        }
        if (unitTableName.startsWith("MD_ORG")) {
            PageVO pageVO;
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname(unitTableName);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            if (dateRegion != null && dateRegion.length == 2) {
                orgDTO.setVersionDate(dateRegion[1]);
            }
            if ((pageVO = this.orgDataClient.list(orgDTO)) != null) {
                return pageVO.getTotal();
            }
        } else {
            PageVO pageVO;
            BaseDataDTO basedataDTO = new BaseDataDTO();
            basedataDTO.setTableName(unitTableName);
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            if (dateRegion != null && dateRegion.length == 2) {
                basedataDTO.setVersionDate(dateRegion[1]);
            }
            if ((pageVO = this.baseDataClient.list(basedataDTO)) != null) {
                return pageVO.getTotal();
            }
        }
        return 0;
    }

    public ICalibreDimTableProvider getCalibreDimTableProvider() {
        return this.calibreDimTableProvider;
    }

    public IDataDefinitionRuntimeController getDataDefinitionController() {
        return this.dataDefinitionController;
    }

    public IEntityViewRunTimeController getiEntityViewRunTimeController() {
        return this.iEntityViewRunTimeController;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public UnitFilter getUnitfilter() {
        return this.unitfilter;
    }

    private static /* synthetic */ void lambda$queryEntityDimTable$1(int orderIndex, Map cache, IEntityRow entityRow, int orderDataType, DataRow row, String key, Integer value) {
        if (value == orderIndex && cache != null) {
            Integer order = (Integer)cache.get(entityRow.getEntityKeyData());
            if (orderDataType == 10) {
                row.setValue(value.intValue(), (Object)new BigDecimal(order));
            } else {
                row.setValue(value.intValue(), (Object)order);
            }
        } else {
            AbstractData itemData = entityRow.getValue(key);
            if (!itemData.isNull) {
                row.setValue(value.intValue(), itemData.getAsObject());
            }
        }
    }
}

