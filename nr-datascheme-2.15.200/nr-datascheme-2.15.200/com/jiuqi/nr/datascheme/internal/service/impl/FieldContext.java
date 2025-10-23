/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datascheme.api.DataField;

public class FieldContext
implements IContext {
    private DataField dataField;

    public DataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }
}

