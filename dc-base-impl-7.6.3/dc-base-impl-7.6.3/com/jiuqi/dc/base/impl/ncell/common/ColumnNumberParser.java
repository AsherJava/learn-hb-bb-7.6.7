/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.impl.ncell.common;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.impl.ncell.common.ColumnValueParser;
import com.jiuqi.dc.base.impl.ncell.vo.PasteDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnNumberParser
implements ColumnValueParser {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object parse(PasteDataVO source) {
        String sourceValue = source.getColumnValue();
        if (StringUtils.isEmpty((String)sourceValue)) {
            return "";
        }
        String columnValue = sourceValue.replace(",", "");
        int getDigits = source.getColumnDefine().getGetDigits();
        double round = 0.0;
        try {
            round = NumberUtils.round((double)Double.parseDouble(columnValue), (int)getDigits);
        }
        catch (NumberFormatException e) {
            this.logger.error("\u89e3\u6790\u6570\u503c\u5f02\u5e38", e);
        }
        return round;
    }
}

