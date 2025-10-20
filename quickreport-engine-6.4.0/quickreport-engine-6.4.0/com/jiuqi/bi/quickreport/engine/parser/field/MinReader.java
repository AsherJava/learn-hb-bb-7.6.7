/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.quickreport.engine.parser.field.FieldReader;
import com.jiuqi.bi.syntax.SyntaxException;

public final class MinReader
extends FieldReader {
    public MinReader(int fieldIndex) {
        super(fieldIndex);
    }

    @Override
    public Object read(BIDataSet ds) throws SyntaxException {
        try {
            return ds.min(this.fieldIndex);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }
}

