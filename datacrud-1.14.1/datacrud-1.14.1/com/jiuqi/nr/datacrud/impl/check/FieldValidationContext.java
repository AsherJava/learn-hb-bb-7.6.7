/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datacrud.impl.check;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datascheme.api.DataField;

public class FieldValidationContext
implements IContext {
    private DataField field;
    private Object data;

    public DataField getField() {
        return this.field;
    }

    public void setField(DataField field) {
        this.field = field;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

