/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.consts;

import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import java.util.List;

public class FetchFloatRowResult {
    private List<Object[]> rowDatas;
    private List<ImpExpInnerColumnHandler> floatColumns;

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public List<ImpExpInnerColumnHandler> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(List<ImpExpInnerColumnHandler> floatColumns) {
        this.floatColumns = floatColumns;
    }
}

