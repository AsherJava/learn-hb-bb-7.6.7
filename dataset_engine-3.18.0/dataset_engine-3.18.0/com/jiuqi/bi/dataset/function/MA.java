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
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Iterator;
import java.util.List;

public class MA
extends DSFunction {
    private static final long serialVersionUID = 7137836670004053547L;

    public MA() {
        this.parameters().add(new Parameter("field", 0, "\u5ea6\u91cf\u5b57\u6bb5"));
        this.parameters().add(new Parameter("length", 3, "\u533a\u95f4\u957f\u5ea6"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        try {
            DSFormulaContext dsFormulaContext = (DSFormulaContext)context;
            BIDataSetImpl dataSet = dsFormulaContext.getDataSet();
            DSFieldNode dsNode = (DSFieldNode)paramNodes.get(0);
            BIDataSetFieldInfo fieldInfo = dsNode.getFieldInfo();
            int length = this.analysisLength(context, paramNodes.get(1));
            BIDataSetImpl filteredDataSet = dataSet;
            if (paramNodes.size() > 2) {
                List<IASTNode> filterNodes = paramNodes.subList(2, paramNodes.size());
                filteredDataSet = (BIDataSetImpl)dataSet.doFilter(filterNodes, dsFormulaContext);
            }
            if (filteredDataSet.getRecordCount() < length) {
                throw new SyntaxException("DS_MA\u51fd\u6570\u7684\u533a\u95f4\u957f\u5ea6\u8d85\u8fc7\u4e86\u6570\u636e\u96c6\u957f\u5ea6");
            }
            return this.getAvg(length, fieldInfo.getName(), filteredDataSet);
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    private int analysisLength(IContext context, IASTNode node) throws SyntaxException {
        Double value = (Double)node.evaluate(context);
        if (value != (double)value.intValue()) {
            throw new SyntaxException("DS_MA\u51fd\u6570\u4e2d\u7684\u533a\u95f4\u957f\u5ea6\u5fc5\u987b\u4e3a\u6574\u6570");
        }
        if (value < 0.0) {
            throw new SyntaxException("DS_MA\u51fd\u6570\u4e2d\u7684\u533a\u95f4\u957f\u5ea6\u5fc5\u987b\u4e3a\u5927\u4e8e0");
        }
        return value.intValue();
    }

    private Double getAvg(int n, String fieldName, BIDataSetImpl dataSet) {
        Double avg = 0.0;
        int colIndex = dataSet.getMetadata().indexOf(fieldName.toUpperCase());
        Iterator<BIDataRow> iterator = dataSet.iterator();
        for (int i = 0; iterator.hasNext() && i < n; ++i) {
            BIDataRow biDataRow = iterator.next();
            Double value = biDataRow.getDouble(colIndex);
            avg = avg + value;
        }
        return avg / (double)n;
    }

    public String name() {
        return "DS_MA";
    }

    public String title() {
        return "\u7edf\u8ba1\u6307\u5b9a\u5b57\u6bb5\u5728\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u8fc7\u6ee4\u540e\u7684\u6570\u636e\u96c6\u4e2d\u6307\u5b9a\u533a\u95f4\u957f\u5ea6\u8bb0\u5f55\u7684\u5e73\u5747\u503c";
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 3;
    }
}

