/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.util.FieldSqlConditionUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryRegion;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;

public abstract class SummaryRegionExecutor {
    protected RuntimeSummaryRegion region;

    public SummaryRegionExecutor(RuntimeSummaryRegion region) {
        this.region = region;
    }

    public abstract void execute(SumContext var1) throws Exception;

    public void clearData(SumContext context) throws Exception {
        QueryParam queryParam = context.getQueryParam();
        for (String tableCode : this.region.getTargetTableCodes()) {
            TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfoByCode(tableCode);
            StringBuilder sql = new StringBuilder("delete from ");
            sql.append(tableCode).append(" where ");
            boolean needAnd = false;
            DimensionValueSet targetDimValues = context.getTargetDimValues();
            for (int i = 0; i < targetDimValues.size(); ++i) {
                String dimension = targetDimValues.getName(i);
                Object dimValue = targetDimValues.getValue(i);
                ColumnModelDefine dimField = tableInfo.getDimensionField(dimension);
                FieldSqlConditionUtil.appendFieldCondition((IDatabase)queryParam.getDatabase(), (Connection)queryParam.getConnection(), (StringBuilder)sql, (String)dimField.getName(), (int)dimField.getColumnType().getValue(), (Object)dimValue, (boolean)needAnd, null, null, null, (boolean)false);
                needAnd = true;
            }
            DataEngineUtil.execute((Connection)queryParam.getConnection(), (String)sql.toString(), (IMonitor)context.getMonitor());
        }
    }

    public RuntimeSummaryRegion getRegion() {
        return this.region;
    }
}

