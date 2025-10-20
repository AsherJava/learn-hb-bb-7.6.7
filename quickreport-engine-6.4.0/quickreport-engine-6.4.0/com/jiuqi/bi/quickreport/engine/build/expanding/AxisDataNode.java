/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.DataType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AxisDataNode
implements Comparable<AxisDataNode> {
    private Object keyValue;
    private Object value;
    private Object displayValue;
    private Object orderValue;
    private Object parentValue;
    private int level;
    private int childrenSize;
    private int rank;
    private String comment;
    private final ExpandingRegion region;
    private final List<AxisDataNode> children;

    public AxisDataNode(ExpandingRegion region) {
        this.region = region;
        this.children = new ArrayList<AxisDataNode>();
    }

    public ExpandingRegion getRegion() {
        return this.region;
    }

    public Object getKeyValue() {
        return this.keyValue;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getDisplayValue() {
        return this.displayValue;
    }

    public void setDisplayValue(Object displayValue) {
        this.displayValue = displayValue;
    }

    public Object getOrderValue() {
        return this.orderValue;
    }

    public void setOrderValue(Object orderValue) {
        this.orderValue = orderValue;
    }

    public Object getParentValue() {
        return this.parentValue;
    }

    public void setParentValue(Object parentValue) {
        this.parentValue = parentValue;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getChildrenSize() {
        return this.childrenSize;
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<AxisDataNode> getChildren() {
        return this.children;
    }

    public AxisDataNode firstChild() {
        return this.children.isEmpty() ? null : this.children.get(0);
    }

    public AxisDataNode lastChild() {
        return this.children.isEmpty() ? null : this.children.get(this.children.size() - 1);
    }

    public CellValue toCellValue() {
        CellValue cellValue = new CellValue(this.region.getMasterCell());
        cellValue.value = this.value;
        cellValue.displayValue = this.getDisplayValue();
        cellValue.setComment(this.comment);
        cellValue._masterValue = this;
        return cellValue;
    }

    public IFilterDescriptor toFilter() {
        if (this.region.isStatic()) {
            return null;
        }
        DSFieldNode fieldNode = this.region.getKeyField();
        return new ValueFilterDescriptor(fieldNode.getDataSet().getName(), fieldNode.getField(), this.keyValue);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if (this.region == null) {
            buffer.append("<root>");
        } else {
            buffer.append((Object)this.region.getField()).append('=').append(DataType.formatValue((int)0, (Object)this.value));
        }
        buffer.append(" : {");
        boolean started = false;
        for (AxisDataNode item : this.children) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            buffer.append(DataType.formatValue((int)0, (Object)item.value));
        }
        buffer.append('}');
        return buffer.toString();
    }

    @Override
    public int compareTo(AxisDataNode node) {
        if (this.orderValue != null && node.orderValue != null) {
            if (this.orderValue instanceof Double) {
                double value1 = (Double)this.orderValue;
                double value2 = (Double)node.orderValue;
                return DataType.compare((double)value1, (double)value2);
            }
            if (this.orderValue instanceof Comparable) {
                return ((Comparable)this.orderValue).compareTo(node.orderValue);
            }
        }
        return 0;
    }

    public void fillFilters(Collection<IFilterDescriptor> filters) {
        this.region.appendNexFilters(filters);
        filters.add(this.toFilter());
    }

    public int estimateCellSize() {
        return this.children.stream().mapToInt(AxisDataNode::estimateCellSize).reduce(this.region == null ? 0 : this.region.getDeltaSize(), Integer::sum);
    }
}

