/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFieldDefineFinder
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFieldDefineFinder;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;

public class FmlTrackQueryContext
extends QueryContext {
    private final IFieldDefineFinder fieldDefineFinder;
    private final IColumnModelFinder columnModelFinder;

    public FmlTrackQueryContext(ExecutorContext exeContext, IMonitor monitor, IFieldDefineFinder fieldDefineFinder) throws ParseException {
        super(exeContext, monitor);
        this.fieldDefineFinder = fieldDefineFinder;
        this.columnModelFinder = null;
    }

    public FmlTrackQueryContext(ExecutorContext exeContext, IMonitor monitor, IColumnModelFinder columnModelFinder) throws ParseException {
        super(exeContext, monitor);
        this.columnModelFinder = columnModelFinder;
        this.fieldDefineFinder = null;
    }

    public IFieldDefineFinder getFieldDefineFinder() {
        return this.fieldDefineFinder;
    }

    public IColumnModelFinder getColumnModelFinder() {
        return this.columnModelFinder;
    }
}

