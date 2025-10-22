/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDeleteSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.dbutil.TableEntityData;
import com.jiuqi.nr.batch.summary.service.engine.BSummaryTempTable;
import com.jiuqi.nr.batch.summary.service.engine.BSummaryTempTableRegister;
import com.jiuqi.nr.batch.summary.service.engine.TempTableProvider;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TempTableProviderImpl
implements TempTableProvider {
    @Resource
    protected ITableDBUtil tableDBUtil;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private IRunTimeViewController nrTaskRuntimeService;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private DimensionProviderFactory dimensionProviderFactory;
    @Resource
    private ITempTableManager tempTableManager;

    @Override
    public ITableEntity createTempTable(Connection connection, SummaryScheme summaryScheme) throws SQLException {
        BSummaryTempTable bSummaryTempTable = new BSummaryTempTable();
        List<LogicField> logicFields = this.getTempTableKeyColumns(summaryScheme);
        bSummaryTempTable.setPrimaryColumns(logicFields);
        this.createTable(bSummaryTempTable, logicFields);
        return bSummaryTempTable;
    }

    @Override
    public void insertTempTableData(Connection connection, ITableEntity tempTable, TargetDimProvider targetDimProvider, SummaryScheme summaryScheme, String period) {
        String task = summaryScheme.getTask();
        FormSchemeDefine formScheme = this.queryFormSchemeDefine(task, period);
        if (formScheme != null) {
            Map<String, String> dimName2ColumnName = this.getTempTableColumnName2DimensionName(formScheme);
            List<LogicField> tableColumns = tempTable.getAllColumns();
            TableEntityData tempTableData = new TableEntityData((String[])tableColumns.stream().map(LogicField::getFieldName).toArray(String[]::new));
            List<String> targetDims = targetDimProvider.getTargetDims(period);
            int rowIdx = 0;
            for (String targetDim : targetDims) {
                List<String> entityRowKeys = targetDimProvider.getEntityRowKeys(period, targetDim);
                if (entityRowKeys == null || entityRowKeys.isEmpty()) continue;
                DimensionCollectionBuilder dimensionBuilder = this.getDimensionBuilder(formScheme, entityRowKeys, period);
                DimensionCollection dimensionCollection = dimensionBuilder.getCollection();
                for (DimensionValueSet dimensionValueSet : dimensionCollection) {
                    tempTableData.setCellValue(rowIdx, "DM_KJ", (Object)targetDim);
                    for (int idx = 0; idx < dimensionValueSet.size(); ++idx) {
                        String fieldName = dimName2ColumnName.get(dimensionValueSet.getName(idx));
                        if (!tempTable.hasColumn(fieldName)) continue;
                        tempTableData.setCellValue(rowIdx, fieldName, dimensionValueSet.getValue(idx));
                    }
                    ++rowIdx;
                }
            }
            this.tableDBUtil.insertTableData(connection, tempTable, tempTableData);
        }
    }

    protected Map<String, String> getTempTableColumnName2DimensionName(FormSchemeDefine formScheme) {
        HashMap<String, String> dimName2ColumnName = new HashMap<String, String>();
        TaskDefine taskDefine = this.nrTaskRuntimeService.queryTaskDefine(formScheme.getTaskKey());
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            if (this.isADJust(dimension)) continue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimName2ColumnName.put(dimensionName, "MDCODE");
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimName2ColumnName.put(dimensionName, "DATATIME");
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
            TableModelDefine tableModel = this.entityMetaService.getTableModel(dimension.getDimKey());
            dimName2ColumnName.put(dimensionName, tableModel.getName());
        }
        return dimName2ColumnName;
    }

    @Override
    public void dropTemTable(Connection connection, ITableEntity tempTable) {
        if (tempTable != null) {
            this.tableDBUtil.dropTable(connection, tempTable);
        }
    }

    @Override
    public void clearTempTableData(Connection connection, ITableEntity tempTable) {
        if (tempTable != null) {
            ITableDeleteSqlBuilder deleteSqlBuilder = new ITableDeleteSqlBuilder(tempTable.getTableName());
            this.tableDBUtil.implementSQL(connection, deleteSqlBuilder.toString());
        }
    }

    protected DimensionCollectionBuilder getDimensionBuilder(FormSchemeDefine formScheme, List<String> entityKeys, String period) {
        TaskDefine taskDefine = this.nrTaskRuntimeService.queryTaskDefine(formScheme.getTaskKey());
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            contextEntityId = taskDefine.getDw();
        }
        for (DataDimension dimension : dataSchemeDimension) {
            if (this.isADJust(dimension)) continue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, contextEntityId, new Object[]{entityKeys});
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{period});
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
            DimensionProviderData providerData = new DimensionProviderData();
            providerData.setDataSchemeKey(taskDefine.getDataScheme());
            providerData.setAuthorityType(AuthorityType.Read);
            VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
            dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
        }
        return dimensionBuilder;
    }

    protected FormSchemeDefine queryFormSchemeDefine(String taskId, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.nrTaskRuntimeService.querySchemePeriodLinkByPeriodAndTask(period, taskId);
            if (schemePeriodLinkDefine != null) {
                return this.nrTaskRuntimeService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    protected List<LogicField> getTempTableKeyColumns(SummaryScheme summaryScheme) {
        ArrayList<LogicField> columns = new ArrayList<LogicField>();
        TaskDefine taskDefine = this.nrTaskRuntimeService.queryTaskDefine(summaryScheme.getTask());
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            LogicField field = new LogicField();
            if (this.isADJust(dimension)) continue;
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                field.setFieldName("MDCODE");
            } else {
                if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
                TableModelDefine tableModel = this.entityMetaService.getTableModel(dimension.getDimKey());
                field.setFieldName(tableModel.getName());
            }
            field.setDataType(6);
            field.setSize(50);
            field.setNullable(false);
            field.setDefaultValue(dimension.getDefaultValue());
            columns.add(field);
        }
        return columns;
    }

    private void createTable(BSummaryTempTable summaryTempTable, List<LogicField> logicFields) throws SQLException {
        LogicField md_kj = new LogicField();
        md_kj.setFieldName("DM_KJ");
        md_kj.setDataType(6);
        md_kj.setSize(50);
        md_kj.setNullable(false);
        logicFields.add(md_kj);
        BSummaryTempTableRegister tempTableMeta = new BSummaryTempTableRegister();
        tempTableMeta.setLogicFields(logicFields);
        tempTableMeta.setPrimaryKeyFields(logicFields.stream().map(LogicField::getFieldName).collect(Collectors.toList()));
        ITempTable tempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)tempTableMeta);
        summaryTempTable.setTempTableDefine(tempTable);
    }

    public String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }

    private boolean isADJust(DataDimension dimension) {
        return "ADJUST".equals(dimension.getDimKey());
    }
}

