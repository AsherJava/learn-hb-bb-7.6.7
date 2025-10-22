/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader.convert;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.convert.IFieldDataConverter;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;

public class BigDecimaConverter
implements IFieldDataConverter {
    private int scale;

    public BigDecimaConverter(ColumnModelDefine field) {
        this.scale = field.getDecimal();
    }

    @Override
    public Object convert(QueryContext qContext, Object value) {
        BigDecimal decimalValue = DataTypesConvert.toBigDecimal(value);
        return decimalValue.setScale(this.scale, 4);
    }
}

