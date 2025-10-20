/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
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
import com.jiuqi.bi.quickreport.engine.parser.restriction.DSFieldInfo;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;

public final class MappingFilterDescriptor
extends FilterDescriptor {
    private final DSFieldInfo mappingField;

    public MappingFilterDescriptor(String dataSetName, DSField field, DSFieldInfo mappingField) {
        super(dataSetName, field);
        this.mappingField = mappingField;
    }

    public DSFieldInfo getMappingField() {
        return this.mappingField;
    }

    @Override
    public FilterItem toFilter() throws ReportContextException {
        throw new ReportContextException("\u7a0b\u5e8f\u903b\u8f91\u9519\u8bef\uff0c\u6570\u636e\u96c6\u5173\u8054\u6761\u4ef6\u4e0d\u80fd\u76f4\u63a5\u7528\u4e8e\u6570\u636e\u96c6\u8fc7\u6ee4");
    }

    @Override
    public IASTNode toASTFilter(IContext context) throws ReportContextException {
        DSFieldNode fieldNode = this.createFieldNode(context, this.getDataSetName(), this.getField());
        DSFieldNode mapField = this.createFieldNode(context, this.mappingField.dataSetName, this.mappingField.field);
        return new Equal(null, (IASTNode)fieldNode, (IASTNode)mapField);
    }

    @Override
    public int hashCode() {
        int h = this.mappingField == null ? 0 : this.mappingField.hashCode();
        return super.hashCode() * 31 + h;
    }

    @Override
    public int compareTo(IFilterDescriptor o) {
        int cmp = super.compareTo(o);
        if (cmp != 0) {
            return cmp;
        }
        MappingFilterDescriptor filter = (MappingFilterDescriptor)o;
        return this.mappingField.compareTo(filter.mappingField);
    }

    public MappingFilterDescriptor reverse() {
        return new MappingFilterDescriptor(this.mappingField.dataSetName, this.mappingField.field, new DSFieldInfo(this.getDataSetName(), this.getField()));
    }

    @Override
    protected String toFilterString() {
        return this.mappingField.toString();
    }

    @Override
    public IFilterDescriptor mapTo(String dsName, DSField field) {
        throw new UnsupportedOperationException("\u7a0b\u5e8f\u903b\u8f91\u9519\u8bef\uff0c\u6570\u636e\u96c6\u5173\u8054\u6761\u4ef6\u4e0d\u80fd\u518d\u8fdb\u884c\u6620\u5c04\u8f6c\u6362");
    }
}

