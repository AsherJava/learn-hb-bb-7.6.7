/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRegionQueryTabeStrategy
extends AbstractRegionQueryTableStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SimpleRegionQueryTabeStrategy.class);

    public SimpleRegionQueryTabeStrategy(IDataQuery dataQuery, AbstractRegionRelationEvn regionRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(dataQuery, regionRelationEvn, regionQueryInfo);
    }

    @Override
    public void clearRegionTable() {
        IDataTable modifyTable = this.getRegionModifyTable();
        IFieldsInfo fieldsInfo = modifyTable.getFieldsInfo();
        for (int i = 0; i < modifyTable.getCount(); ++i) {
            IDataRow dataRow = modifyTable.getItem(i);
            for (int j = 0; j < fieldsInfo.getFieldCount(); ++j) {
                FieldDefine fieldDefine = fieldsInfo.getFieldDefine(j);
                if (fieldDefine == null || !fieldDefine.getNullable().booleanValue()) continue;
                dataRow.setValue(fieldDefine, null);
            }
        }
        try {
            modifyTable.commitChanges(true);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u56fa\u5b9a\u884c\u6570\u636e\u6e05\u9664\u51fa\u9519"});
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
        Map<String, String> dataLinkFormulaMap = this.regionRelationEvn.getDataLinkFormulaMap();
        if (!this.regionRelationEvn.isCommitData()) {
            if (!dataLinkFormulaMap.isEmpty()) {
                this.dataQuery.setStatic(true);
            }
            for (String string : dataLinkFormulaMap.keySet()) {
                this.cells.add(string);
                int columnIndex3 = this.dataQuery.addExpressionColumn(dataLinkFormulaMap.get(string));
                this.regionRelationEvn.putCellIndex(string, columnIndex3);
            }
        }
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        for (String dataLinkKey : dataLinkFieldMap.keySet()) {
            if (dataLinkFormulaMap.containsKey(dataLinkKey)) continue;
            this.cells.add(dataLinkKey);
            FieldData field = dataLinkFieldMap.get(dataLinkKey);
            columnIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.dataQuery, field.getFieldKey());
            this.regionRelationEvn.putCellIndex(dataLinkKey, columnIndex);
        }
        Map<String, String> map = this.regionRelationEvn.getFieldBalanceFormulaMap();
        if (!this.regionRelationEvn.isCommitData() && this.needBalance) {
            for (String balanceFieldKey : map.keySet()) {
                columnIndex = this.dataQuery.addExpressionColumn(map.get(balanceFieldKey));
                this.regionRelationEvn.putCellIndex(balanceFieldKey, columnIndex);
            }
        }
    }
}

