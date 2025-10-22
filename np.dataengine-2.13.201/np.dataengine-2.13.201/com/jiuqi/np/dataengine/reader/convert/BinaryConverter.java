/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class BinaryConverter
implements IFieldDataConverter {
    public BinaryConverter(ColumnModelDefine field) {
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        if (value instanceof BlobValue) {
            return ((BlobValue)value)._getBytes();
        }
        return value;
    }
}

