/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.Collection;

public final class ExpressionFilterDescriptor
extends FilterDescriptor {
    private final String formula;
    private final IASTNode expression;

    public ExpressionFilterDescriptor(String dataSetName, DSField field, String formula, IReportExpression expression) {
        this(dataSetName, field, formula, expression.getRootNode());
    }

    public ExpressionFilterDescriptor(String dataSetName, DSField field, String formula, IASTNode expression) {
        super(dataSetName, field);
        this.formula = formula;
        this.expression = expression;
    }

    public IASTNode getExpression() {
        return this.expression;
    }

    @Override
    public FilterItem toFilter() {
        FilterItem filter = new FilterItem();
        filter.setFieldName(this.getFieldName());
        filter.setExpr(this.formula);
        return filter;
    }

    @Override
    public IASTNode toASTFilter(IContext context) throws ReportContextException {
        return this.expression;
    }

    @Override
    public void getRefFields(Collection<String> fields) {
        for (IASTNode node : this.expression) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)node;
            if (!this.getDataSetName().equalsIgnoreCase(fieldNode.getDataSet().getName())) continue;
            fields.add(fieldNode.getField().getName());
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + (this.formula == null ? 0 : this.formula.hashCode());
    }

    @Override
    public int compareTo(IFilterDescriptor o) {
        int c = super.compareTo(o);
        if (c != 0) {
            return c;
        }
        ExpressionFilterDescriptor filter = (ExpressionFilterDescriptor)o;
        return StringUtils.compare((String)this.formula, (String)filter.formula);
    }

    @Override
    protected String toFilterString() {
        return this.formula;
    }

    @Override
    public IFilterDescriptor mapTo(String dsName, DSField field) {
        throw new UnsupportedOperationException("\u7a0b\u5e8f\u903b\u8f91\u9519\u8bef\uff0c\u8868\u8fbe\u5f0f\u9650\u5b9a\u6761\u4ef6\u4e0d\u80fd\u518d\u8fdb\u884c\u6620\u5c04\u8f6c\u6362");
    }
}

