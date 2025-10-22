/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.IContext;

public interface RowFilter {
    public String name();

    public boolean supportFormula();

    public String toFormula();

    public boolean filter(String var1, IContext var2) throws SyntaxException;
}

