/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.data.engine.grouping.multi.IMultiGroupingColumn;
import java.util.ArrayList;

public class MultiGroupingRow
extends DataRowImpl {
    private final IMultiGroupingColumn[] columns;
    private int childrenSize;

    @Deprecated
    public int getChildrenSize() {
        return this.childrenSize;
    }

    @Deprecated
    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    @Deprecated
    public void childrenSizePlus() {
        ++this.childrenSize;
    }

    public MultiGroupingRow(IMultiGroupingColumn[] columns, ReadonlyTableImpl table, DimensionValueSet groupKey) {
        super(table, groupKey, new ArrayList(columns.length));
        this.columns = columns;
    }

    public Object readValue(QueryContext context, int index) {
        return this.columns[index].readValue(context);
    }

    public Object getValueObject(int index) {
        return this.columns[index].readValue(null);
    }

    public void setValue(int index, Object value) {
        this.columns[index].writeValue(value);
    }

    public void buildRow() {
        IFieldsInfo fieldsInfo = this.tableImpl.getFieldsInfo();
        ArrayList values = this.getRowDatas();
        for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
            Object sumValue = null;
            FieldType dataType = fieldsInfo.getDataType(i);
            if (dataType == FieldType.FIELD_TYPE_DECIMAL || dataType == FieldType.FIELD_TYPE_INTEGER || dataType == FieldType.FIELD_TYPE_FLOAT || dataType == FieldType.FIELD_TYPE_STRING) {
                sumValue = this.readValue(null, i);
            }
            values.add(sumValue);
        }
        super.buildRow();
    }
}

