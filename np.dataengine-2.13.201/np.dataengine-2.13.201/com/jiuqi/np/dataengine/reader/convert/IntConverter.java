/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class IntConverter
implements IFieldDataConverter {
    public IntConverter(ColumnModelDefine field) {
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        return Convert.toInt((Object)value);
    }
}

