/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.zb.scheme.dto;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;

public class FieldContext
implements IContext {
    private ZbInfo dataField;

    public ZbInfo getDataField() {
        return this.dataField;
    }

    public void setDataField(ZbInfo dataField) {
        this.dataField = dataField;
    }
}

