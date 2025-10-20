/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.quickreport.engine.build;

import com.jiuqi.bi.quickreport.engine.build.StyleValue;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.engine.result.TraceInfo;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.syntax.DataType;
import java.util.List;

public final class CellValue {
    public Object value;
    public Object displayValue;
    private String comment;
    public StyleValue styleValue;
    public InteractiveInfo interactiveInfo;
    public AxisDataNode _masterValue;
    public final CellBindingInfo _bindingInfo;
    public List<AxisDataNode> _restrictions;
    private List<TraceInfo> traceInfos;

    public CellValue() {
        this(null);
    }

    public CellValue(CellBindingInfo bindingInfo) {
        this._bindingInfo = bindingInfo;
    }

    public List<TraceInfo> getTraceInfos() {
        return this.traceInfos;
    }

    public void setTraceInfos(List<TraceInfo> traceInfos) {
        this.traceInfos = traceInfos;
    }

    public DataBarInfo getDataBarInfo() {
        if (this.styleValue == null || this.styleValue.getDataBarStyle() == null || this._bindingInfo == null) {
            return null;
        }
        for (IStyleProcessor processor = this._bindingInfo.getStyleProcessor(); processor != null; processor = processor.next()) {
            Object info = processor.getStyleInfo();
            if (!(info instanceof DataBarInfo)) continue;
            DataBarInfo barInfo = (DataBarInfo)info;
            barInfo.setForegroundColor(this.styleValue.getDataBarStyle().getForegroundColor());
            barInfo.setBackgroundColor(this.styleValue.getDataBarStyle().getBackgroundColor());
            return barInfo;
        }
        return null;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFoldable() {
        return this._masterValue != null && this._bindingInfo != null && this._bindingInfo.getCellMap() != null && this._bindingInfo.getCellMap().getHierarchyMode() == HierarchyMode.TIERED && this._bindingInfo.getCellMap().getExpandMode() == ExpandMode.ROWEXPANDING;
    }

    public String toString() {
        return this.value == null ? "<null>" : DataType.formatValue((int)0, (Object)this.value);
    }
}

