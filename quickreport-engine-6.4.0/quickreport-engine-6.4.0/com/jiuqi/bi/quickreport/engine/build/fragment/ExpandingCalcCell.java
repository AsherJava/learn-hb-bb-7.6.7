/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.quickreport.engine.build.fragment;

import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.syntax.DataType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ExpandingCalcCell
implements Cloneable {
    private final CellBindingInfo cell;
    private final List<AxisDataNode> restrictions;
    private final List<IFilterDescriptor> extraFilters;

    public ExpandingCalcCell(CellBindingInfo cell) {
        this.cell = cell;
        this.restrictions = new ArrayList<AxisDataNode>();
        this.extraFilters = new ArrayList<IFilterDescriptor>();
    }

    public CellBindingInfo getCell() {
        return this.cell;
    }

    public List<AxisDataNode> getRestrictions() {
        return this.restrictions;
    }

    public List<IFilterDescriptor> getExtraFilters() {
        return this.extraFilters;
    }

    public void addRestrictions(Collection<AxisDataNode> restrictions) {
        for (AxisDataNode restriction : restrictions) {
            this.addRestriction(restriction);
        }
    }

    public void addRestriction(AxisDataNode restriction) {
        this.restrictions.add(restriction);
        restriction.getRegion().appendNexFilters(this.extraFilters);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.cell);
        for (AxisDataNode cellVal : this.restrictions) {
            buffer.append(", ");
            if (cellVal.getRegion().isStatic()) {
                buffer.append(cellVal.getKeyValue());
                continue;
            }
            buffer.append((Object)cellVal.getRegion().getKeyField()).append('=').append(DataType.formatValue((int)0, (Object)cellVal.getKeyValue()));
        }
        for (IFilterDescriptor filter : this.getExtraFilters()) {
            buffer.append(", ").append(filter);
        }
        buffer.append(']');
        return buffer.toString();
    }

    public Object clone() {
        ExpandingCalcCell result = new ExpandingCalcCell(this.cell);
        result.restrictions.addAll(this.restrictions);
        result.extraFilters.addAll(this.extraFilters);
        return result;
    }
}

