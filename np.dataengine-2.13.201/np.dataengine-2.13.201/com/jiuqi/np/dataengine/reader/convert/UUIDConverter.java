/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.UUID;

public class UUIDConverter
implements IFieldDataConverter {
    public UUIDConverter(ColumnModelDefine field) {
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        if (value != null) {
            if (value instanceof UUID) {
                return value;
            }
            if (value instanceof String) {
                return UUID.fromString((String)value);
            }
            if (value instanceof BlobValue) {
                return Convert.toUUID((byte[])((BlobValue)value)._getBytes());
            }
            return Convert.toUUID((Object)value);
        }
        return value;
    }
}

