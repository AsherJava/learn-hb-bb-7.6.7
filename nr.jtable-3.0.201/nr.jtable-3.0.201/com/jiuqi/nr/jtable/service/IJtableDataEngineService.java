/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.summary.SummaryScheme
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodRegionDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IJtableDataEngineService {
    public String dataSum(JtableContext var1, List<String> var2);

    @Deprecated
    public String calculate(JtableContext var1, List<String> var2);

    public void calculateByCondition(JtableContext var1, String var2);

    @Deprecated
    public FormulaCheckReturnInfo check(JtableContext var1, List<String> var2);

    public IGroupingQuery getGroupingQuery(JtableContext var1, String var2);

    public IDataQuery getDataQuery(JtableContext var1, String var2);

    public ExecutorContext getExecutorContext(JtableContext var1);

    public ExecutorContext getExecutorContext(JtableContext var1, DimensionValueSet var2);

    public String getDimensionName(FieldData var1);

    public List<List<FieldData>> getBizKeyOrderFieldList(String var1, JtableContext var2);

    public String getDimensionFieldKey(String var1, String var2);

    public Map<String, String> getDimensionNameColumnMap(JtableContext var1, String var2);

    public int addQueryColumn(ICommonQuery var1, String var2);

    public void addOrderByItem(ICommonQuery var1, String var2, boolean var3);

    public void setEntityLevelGather(IGroupingQuery var1, String var2, int var3, String var4, List<Integer> var5, SummaryScheme var6);

    public void setDataRegTotalInfo(IGroupingQuery var1, Map<String, GradeCellInfo> var2, Map<String, Integer> var3, ArrayList<Integer> var4);

    public AbstractData expressionEvaluat(String var1, JtableContext var2, DimensionValueSet var3);

    public List<IParsedExpression> getExpressionsByLinks(List<String> var1, String var2, String var3, DataEngineConsts.FormulaType var4, int var5);

    public MultiPeriodRegionDataSet getMultiPeriodRegionDataSet(JtableContext var1, RegionData var2, String var3, String var4);
}

