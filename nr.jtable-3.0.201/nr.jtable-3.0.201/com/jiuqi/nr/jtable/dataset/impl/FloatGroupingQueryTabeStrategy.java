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
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import java.util.List;

public class FloatGroupingQueryTabeStrategy
extends AbstractRegionGroupingQueryTableStrategy {
    public FloatGroupingQueryTabeStrategy(IGroupingQuery groupingQuery, AbstractRegionRelationEvn regionRelationEvn, GroupingRelationEvn groupingRelationEvn, RegionQueryInfo regionQueryInfo) {
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
        if (this.regionQueryInfo.getPagerInfo() != null) {
            PagerInfo pagerInfo = this.regionQueryInfo.getPagerInfo();
            int limit = pagerInfo.getLimit();
            if (limit == 0 && this.regionRelationEvn.getRegionData().getPageSize() > 0) {
                limit = this.regionRelationEvn.getRegionData().getPageSize();
            }
            if (limit > 0) {
                this.groupingQuery.setPagingInfo(limit, pagerInfo.getOffset());
            }
        } else if (this.regionRelationEvn.getRegionData().getPageSize() > 0) {
            this.groupingQuery.setPagingInfo(this.regionRelationEvn.getRegionData().getPageSize(), 0);
        }
    }

    @Override
    protected void addPeriodColumn() {
        if (this.regionQueryInfo.getRestructureInfo().getGrade().getGradeCells() != null && this.regionQueryInfo.getRestructureInfo().getGrade().getGradeCells().size() > 0) {
            return;
        }
        String periodDimension = this.groupingRelationEvn.getPeriodDimension();
        FieldData field = this.regionRelationEvn.getDataLinkFieldMap().values().iterator().next();
        List dataFieldDeployInfos = this.deployInfoService.getByDataFieldKeys(new String[]{field.getFieldKey()});
        String periodFieldKey = this.groupingRelationEvn.getPeriodFieldKey(((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableName());
        int periodIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, periodFieldKey);
        this.regionRelationEvn.putCellIndex(periodDimension, periodIndex);
        this.groupingLinks.add(periodDimension);
        this.groupingQuery.addGroupColumn(periodIndex);
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
        this.addPeriodColumn();
        this.addNormalColumn();
    }

    @Override
    protected void addOrderColumn() {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            for (CellQueryInfo cellQueryInfo : this.regionQueryInfo.getFilterInfo().getCellQuerys()) {
                FieldData field = this.regionRelationEvn.getFieldByDataLink(cellQueryInfo.getCellKey());
                if (field == null && (field = this.regionRelationEvn.getFieldData(cellQueryInfo.getCellKey())) == null) continue;
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

