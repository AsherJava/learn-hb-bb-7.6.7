/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;

public final class ValueFilterDescriptor
extends FilterDescriptor {
    private final Object value;

    public ValueFilterDescriptor(String dataSetName, DSField field, Object value) {
        super(dataSetName, field);
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public FilterItem toFilter() {
        FilterItem filter = new FilterItem();
        filter.setFieldName(this.getFieldName());
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(this.value);
        filter.setKeyList(values);
        return filter;
    }

    @Override
    public IASTNode toASTFilter(IContext context) throws ReportContextException {
        DSFieldNode fieldNode = this.createFieldNode(context, this.getDataSetName(), this.getField());
        DataNode valueNode = new DataNode(null, this.getField().getValType(), this.value);
        return new Equal(null, (IASTNode)fieldNode, (IASTNode)valueNode);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + (this.value == null ? 0 : this.value.hashCode());
    }

    @Override
    public int compareTo(IFilterDescriptor o) {
        int c = super.compareTo(o);
        if (c != 0) {
            return c;
        }
        ValueFilterDescriptor filter = (ValueFilterDescriptor)o;
        if (this.value != filter.value) {
            if (this.value == null) {
                return -1;
            }
            if (filter.value == null) {
                return 1;
            }
            c = DataType.compareObject((Object)this.value, (Object)filter.value);
            if (c != 0) {
                return c;
            }
        }
        return 0;
    }

    @Override
    protected String toFilterString() {
        return DataType.formatValue((int)0, (Object)this.value);
    }

    @Override
    public IFilterDescriptor mapTo(String dsName, DSField field) {
        return new ValueFilterDescriptor(dsName, field, this.value);
    }
}

