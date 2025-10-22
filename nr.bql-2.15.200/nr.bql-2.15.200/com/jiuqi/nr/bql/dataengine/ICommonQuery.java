/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.dataengine.setting.IDataValidateProvider
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ICommonQuery {
    public void queryToReader(ExecutorContext var1) throws Exception;

    public void setReader(QueryDataReader var1);

    public QueryDataReader getReader();

    public DimensionValueSet getMasterKeys();

    public void setMasterKeys(DimensionValueSet var1);

    public void setRecKeys(List<String> var1);

    public void setRowFilter(String var1);

    public void setFilterDataByAuthority(boolean var1);

    public void setDefaultGroupName(String var1);

    public void setDesignTimeData(boolean var1);

    public void setIgnoreDataVersion(boolean var1);

    public int addColumn(ColumnModelDefine var1);

    public int addColumn(ColumnModelDefine var1, PeriodModifier var2, DimensionValueSet var3);

    public int addExpressionColumn(String var1);

    public int addCustomSqlColumn(String var1);

    public void addOrderByItem(ColumnModelDefine var1, boolean var2);

    public void addOrderByItem(String var1, boolean var2);

    public void addSpecifiedOrderByItem(ColumnModelDefine var1);

    public void clearOrderByItems();

    public void setColumnFilterValueList(int var1, ArrayList<Object> var2);

    public void setMainTable(String var1);

    public void setPagingInfo(int var1, int var2);

    public void setPagingInfoByRowIndex(int var1, int var2);

    public void setDataValidateProvider(IDataValidateProvider var1);

    public void setQueryParam(QueryParam var1);

    public void setQueryPeriod(String var1, String var2, PeriodType var3);

    public void setQueryVersionStartDate(Date var1);

    public void setQueryVersionDate(Date var1);

    public void setTempAssistantTable(String var1, OrderTempAssistantTable var2);

    public void setIgnoreDefaultOrderBy(boolean var1);

    public void addRightJoinDimTable(String var1);

    public int getColumnSize();

    public void setOption(String var1, Object var2);

    public void setDBQueryExecutorProvider(DBQueryExecutorProvider var1);

    public void addExpandDimValues(String var1, Object var2);
}

