/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.format.IFormatable
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportCellNode;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportRegionNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.format.IFormatable;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

public final class ROW
extends Function
implements IFormatable {
    private static final long serialVersionUID = 1L;

    public ROW() {
        this.parameters().add(new Parameter("reference", 0, "\u9700\u8981\u5f97\u5230\u5176\u884c\u53f7\u7684\u5355\u5143\u683c\u6216\u5355\u5143\u683c\u533a\u57df\u3002", true));
    }

    public String name() {
        return "ROW";
    }

    public String title() {
        return "\u8fd4\u56de\u5f15\u7528\u7684\u884c\u53f7\u3002";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5f15\u7528\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode p0;
        if (parameters.size() > 0 && !((p0 = parameters.get(0)) instanceof ReportCellNode) && !(p0 instanceof ReportRegionNode)) {
            throw new SyntaxException(p0.getToken(), "\u53c2\u6570\u5fc5\u987b\u4e3a\u5355\u5143\u683c\u6216\u533a\u57df\u5f15\u7528\u3002");
        }
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.isEmpty()) {
            return this.getLocalRow((ReportContext)context);
        }
        return this.getRefRow((ReportContext)context, parameters.get(0));
    }

    private int getLocalRow(ReportContext context) throws SyntaxException {
        if (context.getCurrentCell() == null) {
            throw new SyntaxException("\u516c\u5f0f\u6267\u884c\u73af\u5883\u5f02\u5e38\uff0c\u65e0\u6cd5\u83b7\u53d6\u5f53\u524d\u884c\u53f7\u3002");
        }
        return context.getCurrentCell().getPosition().row();
    }

    private int getRefRow(ReportContext context, IASTNode node) throws SyntaxException {
        if (node instanceof ReportCellNode) {
            return ((ReportCellNode)node).getFirstRow(context);
        }
        if (node instanceof ReportRegionNode) {
            return ((ReportRegionNode)node).getFirstRow(context);
        }
        throw new SyntaxException(node.getToken(), "\u975e\u6cd5\u7684\u4f20\u5165\u53c2\u6570\u3002");
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return new IDataFormator(){

            public Format getFormator(IContext context) throws SyntaxException {
                return new DecimalFormat("#,##0");
            }
        };
    }
}

