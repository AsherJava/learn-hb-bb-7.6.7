/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.operator.In
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
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ValuesFilterDescriptor
extends FilterDescriptor {
    private List<Object> values;

    public ValuesFilterDescriptor(String dataSetName, DSField field, List<Object> values) {
        super(dataSetName, field);
        this.values = values;
        Collections.sort(this.values, (o1, o2) -> DataType.compareObject((Object)o1, (Object)o2));
    }

    public List<Object> getValues() {
        return this.values;
    }

    @Override
    public FilterItem toFilter() {
        FilterItem filter = new FilterItem();
        filter.setFieldName(this.getFieldName());
        filter.setKeyList(this.values);
        return filter;
    }

    @Override
    public IASTNode toASTFilter(IContext context) throws ReportContextException {
        if (this.values.isEmpty()) {
            return DataNode.TRUE;
        }
        DSFieldNode fieldNode = this.createFieldNode(context, this.getDataSetName(), this.getField());
        ArrayData value = new ArrayData(this.getField().getValType(), this.values);
        DataNode valueNode = DataNode.valueOf((ArrayData)value);
        return new In(null, (IASTNode)fieldNode, (IASTNode)valueNode);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + this.values.hashCode();
    }

    @Override
    public int compareTo(IFilterDescriptor o) {
        int c = super.compareTo(o);
        if (c != 0) {
            return c;
        }
        ValuesFilterDescriptor filter = (ValuesFilterDescriptor)o;
        c = this.values.size() - filter.values.size();
        if (c != 0) {
            return c;
        }
        for (int i = 0; i < this.values.size(); ++i) {
            Object v2;
            Object v1 = this.values.get(i);
            c = DataType.compareObject((Object)v1, (Object)(v2 = filter.values.get(i)));
            if (c == 0) continue;
            return c;
        }
        return 0;
    }

    @Override
    protected String toFilterString() {
        return this.values.toString();
    }

    @Override
    public Object clone() {
        ValuesFilterDescriptor filter = (ValuesFilterDescriptor)super.clone();
        filter.values = new ArrayList<Object>(this.values);
        return filter;
    }

    @Override
    public IFilterDescriptor mapTo(String dsName, DSField field) {
        return new ValuesFilterDescriptor(dsName, field, this.values);
    }
}

