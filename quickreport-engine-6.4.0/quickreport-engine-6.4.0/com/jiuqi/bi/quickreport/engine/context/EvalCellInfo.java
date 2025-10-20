/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.context.IEvalTracer;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.syntax.cell.Position;

public final class EvalCellInfo {
    private final Position position;
    private final CellBindingInfo bindingInfo;
    private IEvalTracer tracer;

    public EvalCellInfo(Position position, CellBindingInfo bindingInfo) {
        this.position = position;
        this.bindingInfo = bindingInfo;
    }

    public String getSheetName() {
        return this.bindingInfo.getPosition().getSheetName();
    }

    public Position getPosition() {
        return this.position;
    }

    public CellBindingInfo getBindingInfo() {
        return this.bindingInfo;
    }

    public IEvalTracer getTracer() {
        return this.tracer;
    }

    public IEvalTracer setTracer(IEvalTracer tracer) {
        IEvalTracer prev = this.tracer;
        this.tracer = tracer;
        return prev;
    }

    public GridArea getArea() {
        return this.bindingInfo.getOwnerArea();
    }

    public String toString() {
        return this.bindingInfo + "@" + this.position;
    }
}

