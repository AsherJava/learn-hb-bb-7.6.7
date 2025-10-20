/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.org.impl.tablepaste.common;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnValueParser;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;

public class ColumnIntegerParser
implements ColumnValueParser {
    @Override
    public Object parse(PasteDataVO source) {
        String sourceValue = source.getColumnValue();
        if (StringUtils.isEmpty((String)sourceValue)) {
            return "";
        }
        String columnValue = sourceValue.replace(",", "");
        Number retValue = 0;
        try {
            retValue = NumberUtils.round((double)NumberUtils.parseDouble((Object)columnValue), (int)0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }
}

