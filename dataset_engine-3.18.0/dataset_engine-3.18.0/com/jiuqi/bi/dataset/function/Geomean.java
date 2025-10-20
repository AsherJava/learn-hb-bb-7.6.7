/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Iterator;
import java.util.List;

public class Geomean
extends DSFunction {
    private static final long serialVersionUID = 1912395816837597285L;

    public Geomean() {
        this.parameters().add(new Parameter("field", 0, "\u5ea6\u91cf\u5b57\u6bb5"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        DSFieldNode fieldNode = (DSFieldNode)paramNodes.get(0);
        int colIndex = filterDs.getMetadata().indexOf(fieldNode.getName());
        Iterator<BIDataRow> itor = filterDs.iterator();
        int count = 0;
        double p = 1.0;
        while (itor.hasNext()) {
            BIDataRow row = itor.next();
            if (row.wasNull(colIndex)) continue;
            double val = row.getDouble(colIndex);
            p *= val;
            ++count;
        }
        return count == 0 ? null : Double.valueOf(Math.pow(p, 1.0 / (double)count));
    }

    public String name() {
        return "DS_GOC";
    }

    public String title() {
        return "\u6c42\u67d0\u4e2a\u5b57\u6bb5\u7684\u51e0\u4f55\u5e73\u5747\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }
}

