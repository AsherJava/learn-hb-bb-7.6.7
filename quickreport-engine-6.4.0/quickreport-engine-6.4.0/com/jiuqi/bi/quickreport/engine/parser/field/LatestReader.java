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

public final class LatestReader
extends FieldReader {
    private final int timekeyIndex;

    public LatestReader(int fieldIndex, int timekeyIndex) {
        super(fieldIndex);
        this.timekeyIndex = timekeyIndex;
    }

    @Override
    public Object read(BIDataSet ds) throws SyntaxException {
        Object value = null;
        String lastTime = null;
        for (BIDataRow row : ds) {
            Object newValue = row.getValue(this.fieldIndex);
            if (newValue == null) continue;
            String newTime = row.getString(this.timekeyIndex);
            if (lastTime != null && (newTime == null || newTime.compareTo(lastTime) <= 0)) continue;
            value = newValue;
            lastTime = newTime;
        }
        return value;
    }
}

