/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.DataType
 */
package com.jiuqi.dc.mappingscheme.client.option;

import com.jiuqi.dc.base.common.enums.DataType;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;

public class DataSchemeOptionIntegerValue
extends DataSchemeOptionValue {
    public DataSchemeOptionIntegerValue(Integer value) {
        super(DataType.INT, value);
    }

    public DataSchemeOptionIntegerValue(String value) {
        super(DataType.INT, Integer.parseInt(value));
    }
}

