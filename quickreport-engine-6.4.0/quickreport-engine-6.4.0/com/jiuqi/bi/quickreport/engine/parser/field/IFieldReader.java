/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.quickreport.engine.parser.field;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.syntax.SyntaxException;

public interface IFieldReader {
    public Object read(BIDataSet var1) throws SyntaxException;
}

