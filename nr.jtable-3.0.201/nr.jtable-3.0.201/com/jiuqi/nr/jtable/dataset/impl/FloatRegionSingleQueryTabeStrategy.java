/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionSingleQueryTabeStrategy
extends AbstractRegionQueryTableStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FloatRegionSingleQueryTabeStrategy.class);

    public FloatRegionSingleQueryTabeStrategy(IDataQuery dataQuery, AbstractRegionRelationEvn regionRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(dataQuery, regionRelationEvn, regionQueryInfo);
    }

    @Override
    public IReadonlyTable getRegionLocatQueryTable(String floatOrder, int offset) {
        this.dataQuery.clearOrderByItems();
        FieldData floatOrderField = this.regionRelationEvn.getFloatOrderFields().get(0);
        StringBuffer floatOrderFilterBuf = new StringBuffer();
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(floatOrderField.getOwnerTableKey());
        String tableName = dataTable.getCode();
        if (floatOrder.toLowerCase().equals(firstItem)) {
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, floatOrderField.getFieldKey(), false);
            this.dataQuery.setPagingInfoByRowIndex(1, 0);
        } else if (offset == 0) {
            floatOrderFilterBuf.append(" " + tableName + "[" + floatOrderField.getFieldCode() + "]  =  " + floatOrder + " ");
        } else if (offset > 0) {
            floatOrderFilterBuf.append(" " + tableName + "[" + floatOrderField.getFieldCode() + "]  >  " + floatOrder + " ");
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, floatOrderField.getFieldKey(), false);
            this.dataQuery.setPagingInfoByRowIndex(1, Math.abs(offset) - 1);
        } else {
            floatOrderFilterBuf.append(" " + tableName + "[" + floatOrderField.getFieldCode() + "]  <  " + floatOrder + " ");
            this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.dataQuery, floatOrderField.getFieldKey(), true);
            this.dataQuery.setPagingInfoByRowIndex(1, Math.abs(offset) - 1);
        }
        this.dataQuery.setRowFilter(floatOrderFilterBuf.toString());
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = this.dataQuery.executeReader(this.context);
        }
        catch (Exception e) {
            String contextStr = DimensionValueSetUtil.getContextStr(this.regionQueryInfo.getContext(), this.regionQueryInfo.getRegionKey(), null);
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage();
            logger.error(errorStr, e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{errorStr, e.getMessage()});
        }
        return readonlyTable;
    }

    @Override
    public IReadonlyTable getRegionLocatQueryTable(DimensionValueSet locateDimensionValueSet) {
        this.dataQuery.clearOrderByItems();
        this.dataQuery.setRowFilter("");
        JtableContext jtableContext = this.regionQueryInfo.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        dimensionValueSet.combine(locateDimensionValueSet);
        this.dataQuery.setMasterKeys(dimensionValueSet);
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = this.dataQuery.executeReader(this.context);
        }
        catch (Exception e) {
            String contextStr = DimensionValueSetUtil.getContextStr(this.regionQueryInfo.getContext(), this.regionQueryInfo.getRegionKey(), null);
            String errorStr = "\u67e5\u8be2\u51fa\u9519\uff0c\u5f53\u524d\u73af\u5883\u4e3a" + contextStr + ";\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage();
            logger.error(errorStr, e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{errorStr, e.getMessage()});
        }
        return readonlyTable;
    }

    @Override
    public IndexItem getRowIndex(DimensionValueSet locatDimensionValueSet) {
        JtableContext jtableContext = this.regionQueryInfo.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext.getDimensionSet());
        IDataQuery floatOrderDataQuery = this.jtableDataEngineService.getDataQuery(jtableContext, this.regionRelationEvn.getRegionData().getKey());
        floatOrderDataQuery.setMasterKeys(dimensionValueSet);
        FieldData floatOrderField = this.regionRelationEvn.getFloatOrderFields().get(0);
        this.jtableDataEngineService.addQueryColumn((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey());
        this.jtableDataEngineService.addOrderByItem((ICommonQuery)floatOrderDataQuery, floatOrderField.getFieldKey(), false);
        try {
            return floatOrderDataQuery.queryRowIndex(locatDimensionValueSet, this.context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void clearRegionTable() {
    }

    @Override
    protected void addRegionFilter() {
    }

    @Override
    protected void addPageInfo() {
    }

    @Override
    protected void addQueryColumn() {
        int columnIndex;
        this.cells.add("ID");
        if (this.regionRelationEvn.getAllowDuplicate()) {
            FieldData bizKeyOrderField = null;
            for (FieldData fieldData : this.regionRelationEvn.getBizKeyOrderFields().get(0)) {
                if (fieldData.getFieldValueType() != FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) continue;
                bizKeyOrderField = fieldData;
            }
            if (bizKeyOrderField != null) {
                int columnIndex2 = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, bizKeyOrderField.getFieldKey());
                this.regionRelationEvn.putCellIndex("ID", columnIndex2);
            }
        }
        this.cells.add("FLOATORDER");
        int columnIndex3 = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, this.regionRelationEvn.getFloatOrderFields().get(0).getFieldKey());
        this.regionRelationEvn.putCellIndex("FLOATORDER", columnIndex3);
        Map<String, String> dataLinkFormulaMap = this.regionRelationEvn.getDataLinkFormulaMap();
        for (String string : dataLinkFormulaMap.keySet()) {
            this.cells.add(string);
            int columnIndex4 = this.dataQuery.addExpressionColumn(dataLinkFormulaMap.get(string));
            this.regionRelationEvn.putCellIndex(string, columnIndex4);
        }
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        for (String dataLinkKey : dataLinkFieldMap.keySet()) {
            if (dataLinkFormulaMap.containsKey(dataLinkKey)) continue;
            this.cells.add(dataLinkKey);
            FieldData fieldDefine = dataLinkFieldMap.get(dataLinkKey);
            columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, fieldDefine.getFieldKey());
            this.regionRelationEvn.putCellIndex(dataLinkKey, columnIndex);
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
    }
}

