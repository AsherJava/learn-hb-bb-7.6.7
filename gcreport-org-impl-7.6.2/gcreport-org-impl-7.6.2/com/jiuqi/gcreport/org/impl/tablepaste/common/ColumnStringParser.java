/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.common;

import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnValueParser;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;

public class ColumnStringParser
implements ColumnValueParser {
    @Override
    public String parse(PasteDataVO source) {
        return source.getColumnValue();
    }
}

