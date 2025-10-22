/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IProcessMonitor
 */
package com.jiuqi.nr.definition.facade.print.common.interactor;

import com.jiuqi.grid.GridData;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IProcessMonitor;

public class GridDataContentStream
implements IContentStream {
    private GridData bakData;
    private GridData gridData;

    public GridDataContentStream(GridData gridData) {
        this.gridData = gridData;
        this.bakData = (GridData)gridData.clone();
    }

    public int available() {
        if (this.gridData != null) {
            return 1;
        }
        return 0;
    }

    public void close() {
        this.gridData = null;
    }

    public int count() {
        return -1;
    }

    public Object read(IProcessMonitor monitor) {
        GridData gird = (GridData)this.gridData.clone();
        this.gridData = null;
        return gird;
    }

    public Object read(int suggestion, IProcessMonitor monitor) {
        GridData gird = (GridData)this.gridData.clone();
        this.gridData = null;
        return gird;
    }

    public void reset(IProcessMonitor monitor) {
        this.gridData = (GridData)this.bakData.clone();
    }

    public void restore(Object content, IProcessMonitor monitor) {
    }

    public void skip(int n, IProcessMonitor monitor) {
    }
}

