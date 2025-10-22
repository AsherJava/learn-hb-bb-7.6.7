/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.sql.SummarizingMethod
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.jtable.dataset.AbstractAutoSumQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import java.util.List;

public class FloatAutoSumQueryTabeStrategy
extends AbstractAutoSumQueryTableStrategy {
    public FloatAutoSumQueryTabeStrategy(IGroupingQuery groupingQuery, AbstractRegionRelationEvn regionRelationEvn, GroupingRelationEvn groupingRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
    }

    @Override
    protected void setGroupingSeting() {
        RegionGradeInfo grade = this.regionQueryInfo.getRestructureInfo().getGrade();
        if (grade.getGradeCells() != null && grade.getGradeCells().size() > 0) {
            this.groupingQuery.setSummarizingMethod(SummarizingMethod.RollUp);
        } else {
            this.groupingQuery.setSummarizingMethod(SummarizingMethod.None);
        }
        this.groupingQuery.setHasRootGatherRow(grade.isSum());
        this.groupingQuery.setWantDetail(grade.isDetail());
        this.groupingQuery.setHidenOneDetailRow(grade.isHidenRow());
        this.groupingQuery.setSortGroupingAndDetailRows(true);
        List<IExpression> expressions = this.getExpressions();
        if (expressions != null && expressions.size() > 0) {
            this.groupingQuery.setCalcExpressions(expressions);
        }
    }

    @Override
    protected void addRegionFilter() {
        List<String> filterFormulaList = this.regionQueryInfo.getFilterInfo().getFilterFormula();
        if (filterFormulaList != null && filterFormulaList.size() > 0) {
            for (String filterFormula : filterFormulaList) {
                if (!StringUtils.isNotEmpty((String)filterFormula)) continue;
                if (this.filterBuf.length() != 0) {
                    this.filterBuf.append(" AND ");
                }
                this.filterBuf.append(" (" + filterFormula + ") ");
            }
        }
        if (StringUtils.isNotEmpty((String)this.regionRelationEvn.getRegionData().getFilterCondition())) {
            if (this.filterBuf.length() != 0) {
                this.filterBuf.append(" AND ");
            }
            this.filterBuf.append(" (" + this.regionRelationEvn.getRegionData().getFilterCondition() + ") ");
        }
    }

    @Override
    protected void addPageInfo() {
    }

    @Override
    protected void addQueryColumn() {
        this.cells.add("ID");
        if (this.regionRelationEvn.getAllowDuplicate()) {
            FieldData bizKeyOrderField = null;
            for (FieldData fieldDefine : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                if (fieldDefine.getFieldValueType() != FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) continue;
                bizKeyOrderField = fieldDefine;
            }
            if (bizKeyOrderField != null) {
                int columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, bizKeyOrderField.getFieldKey());
                this.regionRelationEvn.putCellIndex("ID", columnIndex);
            }
        }
        this.cells.add("FLOATORDER");
        int columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, this.regionRelationEvn.getFloatOrderFields().get(0).getFieldKey());
        this.regionRelationEvn.putCellIndex("FLOATORDER", columnIndex);
        this.cells.add("SUM");
        this.addAutoSumColumn();
        this.addPeriodColumn();
        this.addNormalColumn();
    }

    @Override
    protected void addOrderColumn() {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            for (CellQueryInfo cellQueryInfo : this.regionQueryInfo.getFilterInfo().getCellQuerys()) {
                FieldData field = this.regionRelationEvn.getFieldByDataLink(cellQueryInfo.getCellKey());
                if (field == null) continue;
                String sort = cellQueryInfo.getSort();
                if ("desc".equals(sort)) {
                    this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.groupingQuery, field.getFieldKey(), true);
                    continue;
                }
                if (!"asc".equals(sort)) continue;
                this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.groupingQuery, field.getFieldKey(), false);
            }
        }
        for (FieldData floatOrderField : this.regionRelationEvn.getFloatOrderFields()) {
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.groupingQuery, floatOrderField.getFieldKey(), false);
        }
    }
}

