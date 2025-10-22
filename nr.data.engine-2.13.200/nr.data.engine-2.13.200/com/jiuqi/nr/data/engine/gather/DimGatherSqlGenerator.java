/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMemTableLoader
 *  com.jiuqi.np.dataengine.intf.IQuerySqlUpdater
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMemTableLoader;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.nr.data.engine.gather.GatherDataTable;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.engine.gather.GatherSqlGenerater;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.engine.gather.SqlItem;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

public class DimGatherSqlGenerator
extends GatherSqlGenerater {
    private static final Logger logger = LoggerFactory.getLogger(DimGatherSqlGenerator.class);
    private final String dimName;
    private final String dwCode;

    public DimGatherSqlGenerator(ExecutorContext executorContext, Connection connection, boolean isNodeCheck, String executionId, boolean containTargetKey, String taskKey, String formSchemeKey, Map<String, String> gatherSingleDims, IMemTableLoader memTableLoader, FileCalculateService fileCalculateService, String dimName, String targetKey) throws ParseException, SQLException {
        super(executorContext, connection, isNodeCheck, executionId, containTargetKey, taskKey, formSchemeKey, gatherSingleDims, memTableLoader, fileCalculateService);
        this.dimName = dimName;
        this.dwCode = targetKey;
    }

    @Override
    public void executeFixedTableGatherForSql(GatherDataTable gatherDataTable, String gatherTempTable, String noGatherTempTable, Integer maxLevel, boolean isMinus, String periodCode, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        List<SqlItem> resultSql = this.buildClearSqlsForFixedTable(gatherDataTable, gatherTempTable, noGatherTempTable, maxLevel, periodCode, targetDimensions);
        if (this.containTargetKey && resultSql.size() == 2) {
            DataEngineUtil.executeUpdate((Connection)this.connection, (String)resultSql.get(1).getExecutorSql(), (Object[])resultSql.get(1).getParamValues().toArray());
        } else {
            for (SqlItem deleteSql : resultSql) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                this.printLoggerSQL(deleteSql, "\u6c47\u603b\u524d\u7f6e", gatherDataTable.getTableModelDefine().getName());
                DataEngineUtil.executeUpdate((Connection)this.connection, (String)deleteSql.getExecutorSql(), (Object[])deleteSql.getParamValues().toArray());
                stopWatch.stop();
                logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
            }
        }
        String sourceSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, periodCode, isMinus, false, -1, sourceDimensions, targetDimensions, false);
        if (querySqlUpdater != null) {
            QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
            sourceSql = querySqlUpdater.updateQuerySql(queryTable, "c1", sourceSql);
        }
        Integer level = maxLevel;
        while (level >= 1) {
            ArrayList<Object> paramValues = new ArrayList<Object>();
            this.addPeriodAndVersion(gatherTableDefine, periodCode, level, paramValues);
            List<HashMap<String, Object>> valMapList = this.doExecuteQuery(gatherDataTable, sourceSql, periodCode, paramValues);
            String updateTemplateSql = this.buildExecuteUpdateSqlNormal(gatherDataTable, versionField, false);
            for (HashMap<String, Object> valMap : valMapList) {
                ArrayList<Object> params = new ArrayList<Object>();
                for (ColumnModelDefine gatherField : gatherDataTable.getAllColumns()) {
                    if (gatherField.getColumnType() == ColumnModelType.ATTACHMENT) continue;
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

    protected void addPeriodAndVersion(GatherTableDefine gatherTableDefine, String periodCode, Integer level, List<Object> paramValues) {
        if (level != null) {
            paramValues.add(level);
        }
        paramValues.add(this.executionId);
        paramValues.add(this.dwCode);
        if (gatherTableDefine.getPeriodField() != null && !StringUtils.isEmpty((String)periodCode)) {
            paramValues.add(periodCode);
        }
    }

    @Override
    public void executeGatherTableByOrderField(GatherDataTable gatherDataTable, GatherEntityValue gatherEntityValue, NotGatherEntityValue notGatherEntityValue, String gatherTempTable, String noGatherTable, Integer maxLevel, boolean isMinus, String periodCode, boolean listGather, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, IQuerySqlUpdater querySqlUpdater, String versionField, String dwDimensionName, Map<Integer, Map<String, String>> bizKeyOrderMappings) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        this.doClearDataBeforeFloatGather(gatherDataTable, gatherTempTable, noGatherTable, periodCode, targetDimensions);
        Integer level = maxLevel;
        while (level >= 1) {
            String sourceSql = this.buildGroupSqlForChildren(gatherDataTable, gatherTempTable, periodCode, isMinus, listGather, level, sourceDimensions, targetDimensions, true);
            if (querySqlUpdater != null) {
                QueryTable queryTable = new QueryTable("c1", this.getRealTableName(gatherDataTable));
                sourceSql = querySqlUpdater.updateQuerySql(queryTable, "c1", sourceSql);
            }
            this.printLoggerSQL(sourceSql, null, "\u6d6e\u52a8\u8868\u6c47\u603b\u67e5\u8be2", gatherDataTable.getTableModelDefine().getName());
            this.doFloatTableGather(gatherDataTable, sourceSql);
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

    protected String buildGroupSqlForChildren(GatherDataTable gatherDataTable, String gatherTempTable, String periodCode, boolean isMinus, boolean listGather, int level, DimensionValueSet sourceDimensions, DimensionValueSet targetDimensions, boolean exeHardParse) throws SQLException {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        StringBuilder sqlBuilder = this.getSelectDimSql(gatherDataTable, isMinus, listGather, targetDimensions);
        String tableName = this.getRealTableName(gatherDataTable);
        sqlBuilder.append(this.buildGatherFieldsSql(gatherDataTable.getAllColumns(), "c1", !listGather, isMinus, String.format("%s.%s", "c2", "GT_ISMINUS"), false, null));
        this.addFromSql(gatherTempTable, periodCode, sqlBuilder, tableName, gatherDataTable);
        this.addWhereSql(gatherDataTable, sqlBuilder, level, periodCode, exeHardParse, sourceDimensions);
        if (!listGather || isMinus) {
            this.addGroupBySql(gatherDataTable, sqlBuilder, targetDimensions);
            boolean needOrder = this.checkNeedOrder(gatherTableDefine);
            if (needOrder) {
                sqlBuilder.append(" order by ");
                this.addOrderBySql(gatherDataTable, sqlBuilder);
            }
        } else {
            sqlBuilder.append(" order by ");
            StringBuilder orderSql = this.listGatherAddOrderBySql(gatherDataTable);
            sqlBuilder.append((CharSequence)orderSql);
            sqlBuilder.setLength(sqlBuilder.length() - 1);
        }
        return sqlBuilder.toString();
    }

    protected void addFromSql(String gatherTempTable, String periodCode, StringBuilder sqlBuilder, String tableName, GatherDataTable gatherDataTable) {
        sqlBuilder.append(" from ");
        sqlBuilder.append(String.format("%s %s", tableName, "c1"));
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        DataTableType dataTableType = gatherTableDefine.getTableDefine().getDataTableType();
        if (!CollectionUtils.isEmpty(this.gatherSingleDims) && dataTableType != DataTableType.MD_INFO) {
            this.appendOrgSQL(sqlBuilder, gatherTableDefine, periodCode);
        }
        sqlBuilder.append(" left join ").append(String.format("%s %s", gatherTempTable, "c2"));
        TableModelRunInfo tableRunInfo = this.dataDefinitionsCache.getTableInfo(gatherDataTable.getTableModelDefine().getName());
        ColumnModelDefine dimensionField = tableRunInfo.getDimensionField(this.dimName);
        sqlBuilder.append(" on ").append(String.format("%s.%s=%s.%s", "c1", dimensionField.getName(), "c2", "GT_ID"));
    }

    protected void addWhereSql(GatherDataTable gatherDataTable, StringBuilder sqlBuilder, int level, String periodCode, boolean exeHardParse, DimensionValueSet sourceDimensions) {
        String tableName;
        String filterSql;
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        if (level < 0) {
            sqlBuilder.append(" where ").append(String.format("%s.%s=?", "c2", "GT_LEVEL"));
        } else {
            sqlBuilder.append(" where ").append(String.format("%s.%s=%s", "c2", "GT_LEVEL", level));
        }
        this.appendExecutionSql(sqlBuilder, true, exeHardParse, null, "c2");
        DataField periodField = gatherTableDefine.getPeriodField();
        if (periodField != null && !StringUtils.isEmpty((String)periodCode)) {
            if (exeHardParse) {
                sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(gatherTableDefine.getUnitField()), this.dwCode));
                sqlBuilder.append(" and ").append(String.format("%s.%s='%s'", "c1", this.queryFieldName(periodField), periodCode));
            } else {
                sqlBuilder.append(" and ").append(String.format("%s.%s=?", "c1", this.queryFieldName(gatherTableDefine.getUnitField())));
                sqlBuilder.append(" and ").append(String.format("%s.%s=?", "c1", this.queryFieldName(periodField)));
            }
        }
        String filterCondition = gatherTableDefine.getFilterCondition();
        if (!gatherTableDefine.isFixed() && StringUtils.isNotEmpty((String)filterCondition) && StringUtils.isNotEmpty((String)(filterSql = this.parseFilterToSQL(this.executorContext, filterCondition, tableName = this.getRealTableName(gatherDataTable), "c1")))) {
            sqlBuilder.append(" and (").append(filterSql).append(")");
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            HashMap<String, ColumnModelDefine> dimFields = this.getTargetDimFields(gatherDataTable, sourceDimensions);
            dimFields.remove(this.dimName);
            this.buildWhereDimensions(sqlBuilder, dimFields, sourceDimensions, true, String.format("%s.", "c1"), null);
        }
    }
}

