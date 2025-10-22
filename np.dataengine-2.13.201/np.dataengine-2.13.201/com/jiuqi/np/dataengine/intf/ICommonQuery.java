/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ICommonQuery {
    public DimensionValueSet getMasterKeys();

    public void setMasterKeys(DimensionValueSet var1);

    public void setRecKeys(List<String> var1);

    public void setRowFilter(String var1);

    public void setFilterDataByAuthority(boolean var1);

    public void setDefaultGroupName(String var1);

    public void setDesignTimeData(boolean var1);

    public void setIgnoreDataVersion(boolean var1);

    public int addColumn(FieldDefine var1);

    public int addColumn(FieldDefine var1, PeriodModifier var2, DimensionValueSet var3);

    public int addExpressionColumn(String var1);

    public int addCustomSqlColumn(String var1);

    public int addLookupColumn(FieldDefine var1, FieldDefine var2);

    public int addCaptionLookupColumn(FieldDefine var1, EntityViewDefine var2);

    public void addOrderByItem(FieldDefine var1, boolean var2);

    public void addSpecifiedOrderByItem(FieldDefine var1);

    public void addOrderByItem(String var1, boolean var2);

    public void clearOrderByItems();

    public void setColumnFilterValueList(int var1, ArrayList<Object> var2);

    public void setMainTable(FieldDefine var1);

    public void setMainTable(String var1);

    public void setPagingInfo(int var1, int var2);

    public void setPagingInfoByRowIndex(int var1, int var2);

    public void setSqlJoinProvider(ISqlJoinProvider var1);

    public void setQuerySqlUpdater(IQuerySqlUpdater var1);

    public void setDataValidateProvider(IDataValidateProvider var1);

    public void setQueryParam(QueryParam var1);

    public void setQueryPeriod(String var1, String var2, PeriodType var3);

    public void setQueryVersionStartDate(Date var1);

    public void setQueryVersionDate(Date var1);

    public void setTempAssistantTable(String var1, TempAssistantTable var2);

    public void setQueryModule(boolean var1);

    public void setIgnoreDefaultOrderBy(boolean var1);

    public IReadonlyTable executeReader(ExecutorContext var1) throws Exception;

    public void addRightJoinDimTable(String var1);

    public int getColumnSize();
}

