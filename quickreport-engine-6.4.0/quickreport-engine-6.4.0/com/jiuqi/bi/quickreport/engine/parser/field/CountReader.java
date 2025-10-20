/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.quickreport.engine.parser.field.FieldReader;
import com.jiuqi.bi.syntax.SyntaxException;

public final class CountReader
extends FieldReader {
    public CountReader(int fieldIndex) {
        super(fieldIndex);
    }

    @Override
    public Object read(BIDataSet ds) throws SyntaxException {
        return ds.getRecordCount();
    }
}

