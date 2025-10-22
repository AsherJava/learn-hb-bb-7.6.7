/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionQueryTabeStrategy
extends AbstractRegionQueryTableStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionQueryTabeStrategy.class);

    public FloatRegionQueryTabeStrategy(IDataQuery dataQuery, AbstractRegionRelationEvn regionRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(dataQuery, regionRelationEvn, regionQueryInfo);
    }

    @Override
    public IndexItem getRowIndex(DimensionValueSet locatDimensionValueSet) {
        try {
            return this.dataQuery.queryRowIndex(locatDimensionValueSet, this.context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void clearRegionTable() {
        try {
            IDataUpdator openForUpdate = this.dataQuery.openForUpdate(this.context, true);
            openForUpdate.commitChanges();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u6d6e\u52a8\u884c\u6570\u636e\u6e05\u9664\u51fa\u9519"});
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
                this.dataQuery.setPagingInfo(limit, pagerInfo.getOffset());
            }
        } else if (this.regionRelationEvn.getRegionData().getPageSize() > 0) {
            this.dataQuery.setPagingInfo(this.regionRelationEvn.getRegionData().getPageSize(), 0);
        }
    }

    @Override
    protected void addQueryColumn() {
        int columnIndex;
        List<String> fieldKeys = this.regionQueryInfo.getFilterInfo().getFieldKeys();
        if (fieldKeys != null && fieldKeys.size() > 0) {
            Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
            for (String string : dataLinkFieldMap.keySet()) {
                FieldData field = dataLinkFieldMap.get(string);
                if (!fieldKeys.contains(field.getFieldKey())) continue;
                this.cells.add(string);
                int columnIndex2 = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, field.getFieldKey());
                this.regionRelationEvn.putCellIndex(string, columnIndex2);
            }
            return;
        }
        this.cells.add("ID");
        if (this.regionRelationEvn.getAllowDuplicate()) {
            FieldData bizKeyOrderField = null;
            for (FieldData fieldData : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                if (fieldData.getFieldValueType() != FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) continue;
                bizKeyOrderField = fieldData;
            }
            if (bizKeyOrderField != null) {
                int columnIndex3 = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, bizKeyOrderField.getFieldKey());
                this.regionRelationEvn.putCellIndex("ID", columnIndex3);
            }
        }
        this.cells.add("FLOATORDER");
        int columnIndex4 = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, this.regionRelationEvn.getFloatOrderFields().get(0).getFieldKey());
        this.regionRelationEvn.putCellIndex("FLOATORDER", columnIndex4);
        Map<String, String> dataLinkFormulaMap = this.regionRelationEvn.getDataLinkFormulaMap();
        if (!this.regionRelationEvn.isCommitData()) {
            for (String string : dataLinkFormulaMap.keySet()) {
                this.cells.add(string.toString());
                int columnIndex5 = this.dataQuery.addExpressionColumn(dataLinkFormulaMap.get(string));
                this.regionRelationEvn.putCellIndex(string.toString(), columnIndex5);
            }
        }
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        for (String dataLinkKey : dataLinkFieldMap.keySet()) {
            if (dataLinkFormulaMap.containsKey(dataLinkKey)) continue;
            this.cells.add(dataLinkKey.toString());
            FieldData fieldDefine = dataLinkFieldMap.get(dataLinkKey);
            columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, fieldDefine.getFieldKey());
            this.regionRelationEvn.putCellIndex(dataLinkKey.toString(), columnIndex);
            this.addColumnFilter(dataLinkKey, columnIndex);
        }
        Map<String, String> map = this.regionRelationEvn.getFieldBalanceFormulaMap();
        if (!this.regionRelationEvn.isCommitData() && this.needBalance) {
            for (String balanceFieldKey : map.keySet()) {
                columnIndex = this.dataQuery.addExpressionColumn(map.get(balanceFieldKey));
                this.regionRelationEvn.putCellIndex(balanceFieldKey, columnIndex);
            }
        }
    }

    @Override
    protected void addOrderColumn() {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            for (CellQueryInfo cellQueryInfo : this.regionQueryInfo.getFilterInfo().getCellQuerys()) {
                IJtableParamService jtableParamService;
                FieldData field = this.regionRelationEvn.getFieldByDataLink(cellQueryInfo.getCellKey());
                if (field == null && (field = (jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class)).getField(cellQueryInfo.getCellKey())) == null) continue;
                boolean addOrder = false;
                Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
                Iterator<String> iterator = dataLinkFieldMap.keySet().iterator();
                if (iterator.hasNext()) {
                    String key = iterator.next();
                    FieldData fieldData = dataLinkFieldMap.get(key);
                    String ownerTableKey = fieldData.getOwnerTableKey();
                    boolean bl = addOrder = ownerTableKey != null && ownerTableKey.equals(field.getOwnerTableKey());
                }
                if (!addOrder) continue;
                String sort = cellQueryInfo.getSort();
                if ("desc".equals(sort)) {
                    this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, field.getFieldKey(), true);
                    continue;
                }
                if (!"asc".equals(sort)) continue;
                this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, field.getFieldKey(), false);
            }
        }
        for (FieldData floatOrderField : this.regionRelationEvn.getFloatOrderFields()) {
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, floatOrderField.getFieldKey(), false);
        }
    }
}

