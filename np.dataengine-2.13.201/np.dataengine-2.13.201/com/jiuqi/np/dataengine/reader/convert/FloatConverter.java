/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class FloatConverter
implements IFieldDataConverter {
    private int scale;

    public FloatConverter(ColumnModelDefine field) {
        this.scale = field.getDecimal();
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        double floatValue = Convert.toDouble((Object)value);
        return Round.callFunction((Number)floatValue, (int)this.scale);
    }
}

