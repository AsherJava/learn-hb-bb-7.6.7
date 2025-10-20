/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Iterator;
import java.util.List;

public class Max
extends DSFunction {
    private static final long serialVersionUID = 7529655053344355690L;

    public Max() {
        this.parameters().add(new Parameter("field", 0, "\u5f85\u6c42\u503c\u7684\u5b57\u6bb5\u540d\u79f0"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        IASTNode p0 = paramNodes.get(0);
        String fieldname = this.getFieldName(p0, context);
        Column column = filterDs.getMetadata().find(fieldname);
        if (column != null) {
            int index = column.getIndex();
            int tp = ((BIDataSetFieldInfo)column.getInfo()).getValType();
            int type = DataType.translateToSyntaxType(DataType.valueOf(tp));
            Object max = null;
            Iterator<BIDataRow> itor = filterDs.iterator();
            while (itor.hasNext()) {
                Object value = itor.next().getValue(index);
                int compareTo = com.jiuqi.bi.syntax.DataType.compare((int)type, max, (Object)value);
                if (compareTo >= 0) continue;
                max = value;
            }
            return max;
        }
        throw new SyntaxException("\u65e0\u6548\u7684\u5b57\u6bb5\u540d\u79f0");
    }

    public String name() {
        return "DS_MAX";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u5217\u7684\u6700\u5927\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof DSFormulaContext) {
            IASTNode p0 = parameters.get(0);
            String fieldname = this.getFieldName(p0, context);
            DSFormulaContext fc = (DSFormulaContext)context;
            Column column = fc.getDataSet().getMetadata().find(fieldname);
            int tp = ((BIDataSetFieldInfo)column.getInfo()).getValType();
            return DataType.translateToSyntaxType(DataType.valueOf(tp));
        }
        return 0;
    }

    private String getFieldName(IASTNode node, IContext context) throws SyntaxException {
        if (node instanceof DSFieldNode) {
            return ((DSFieldNode)node).getName();
        }
        if (node instanceof DataNode) {
            Object value = node.evaluate(context);
            if (value instanceof String) {
                return (String)value;
            }
            throw new SyntaxException("\u4f20\u5165\u7684\u5b57\u6bb5\u540d\u79f0\u4e0d\u5408\u6cd5");
        }
        throw new SyntaxException("\u4f20\u5165\u7684\u5b57\u6bb5\u540d\u79f0\u4e0d\u5408\u6cd5");
    }
}

