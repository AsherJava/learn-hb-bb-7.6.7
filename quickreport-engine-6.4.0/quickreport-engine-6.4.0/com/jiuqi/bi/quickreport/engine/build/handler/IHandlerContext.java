/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.syntax.parser.IContext;

public interface IHandlerContext {
    public ResultGridData getResultGrid();

    public GridData getRawGrid();

    public IContext getContext();
}

