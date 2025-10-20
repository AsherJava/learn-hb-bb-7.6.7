/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.quickreport.engine.parser.field.FieldReader;
import com.jiuqi.bi.syntax.SyntaxException;

public final class FirstReader
extends FieldReader {
    public FirstReader(int fieldIndex) {
        super(fieldIndex);
    }

    @Override
    public Object read(BIDataSet ds) throws SyntaxException {
        for (BIDataRow row : ds) {
            Object value = row.getValue(this.fieldIndex);
            if (value == null) continue;
            return value;
        }
        return null;
    }
}

