/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import org.springframework.util.ObjectUtils;

public class FileParseStrategy
extends StringParseStrategy {
    @Override
    protected int getDataType() {
        return 36;
    }

    @Override
    protected ParseReturnRes parse(String valueStr, DataField field) {
        if (ObjectUtils.isEmpty(valueStr) || "null".equals(valueStr)) {
            return this.okValue(null);
        }
        return this.okValue(valueStr);
    }

    @Override
    protected ParseReturnRes parse(String valueStr, IFMDMAttribute attribute) {
        if (ObjectUtils.isEmpty(valueStr) || "null".equals(valueStr)) {
            return this.okValue(null);
        }
        return this.okValue(valueStr);
    }
}

