/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.ncell.common;

import com.jiuqi.dc.base.impl.ncell.common.ColumnValueParser;
import com.jiuqi.dc.base.impl.ncell.vo.PasteDataVO;

public class ColumnStringParser
implements ColumnValueParser {
    @Override
    public String parse(PasteDataVO source) {
        return source.getColumnValue();
    }
}

