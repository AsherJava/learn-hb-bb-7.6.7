/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.sql.SummarizingMethod
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.jtable.dataset.AbstractAutoSumQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import java.util.List;
import java.util.Map;

public class SimpleAutoSumQueryTabeStrategy
extends AbstractAutoSumQueryTableStrategy {
    public SimpleAutoSumQueryTabeStrategy(IGroupingQuery groupingQuery, AbstractRegionRelationEvn regionRelationEvn, GroupingRelationEvn groupingRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
    }

    @Override
    protected void setGroupingSeting() {
        this.groupingQuery.setSummarizingMethod(SummarizingMethod.RollUp);
        this.groupingQuery.setHasRootGatherRow(true);
        this.groupingQuery.setWantDetail(false);
        this.groupingQuery.setSortGroupingAndDetailRows(true);
        List<IExpression> expressions = this.getExpressions();
        if (expressions != null && expressions.size() > 0) {
            this.groupingQuery.setCalcExpressions(expressions);
        }
    }

    @Override
    protected void addRegionFilter() {
    }

    @Override
    protected void addQueryColumn() {
        this.addAutoSumColumn();
        this.addPeriodColumn();
        this.addNormalColumn();
    }

    @Override
    protected void addOrderColumn() {
    }

    @Override
    protected void addNormalColumn() {
        int columnIndex;
        Map<String, String> dataLinkFormulaMap = this.regionRelationEvn.getDataLinkFormulaMap();
        for (String string : dataLinkFormulaMap.keySet()) {
            this.cells.add(string);
            int columnIndex2 = this.groupingQuery.addExpressionColumn(dataLinkFormulaMap.get(string));
            this.regionRelationEvn.putCellIndex(string, columnIndex2);
        }
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        for (String dataLinkKey : dataLinkFieldMap.keySet()) {
            if (dataLinkFormulaMap.containsKey(dataLinkKey)) continue;
            this.cells.add(dataLinkKey);
            FieldData field = dataLinkFieldMap.get(dataLinkKey);
            columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, field.getFieldKey());
            this.regionRelationEvn.putCellIndex(dataLinkKey, columnIndex);
            this.addColumnFilter(dataLinkKey, columnIndex);
        }
        Map<String, String> map = this.regionRelationEvn.getFieldBalanceFormulaMap();
        if (!this.regionRelationEvn.isCommitData() && this.needBalance) {
            for (String balanceFieldKey : map.keySet()) {
                columnIndex = this.groupingQuery.addExpressionColumn(map.get(balanceFieldKey));
                this.regionRelationEvn.putCellIndex(balanceFieldKey, columnIndex);
            }
        }
    }
}

