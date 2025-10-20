/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class Quartile
extends DSFunction {
    private static final long serialVersionUID = -8499227047765667952L;

    public Quartile() {
        this.parameters().add(new Parameter("percent", 3, "\u5206\u4f4d\u767e\u5206\u6bd4"));
        this.parameters().add(new Parameter("field", 0, "\u5ea6\u91cf\u5b57\u6bb5"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        double percent = this.analysisPercent(context, paramNodes.get(0));
        DSFieldNode fieldNode = (DSFieldNode)paramNodes.get(1);
        SortItem sortItem = new SortItem(fieldNode.getName(), 1);
        ArrayList<SortItem> sortItems = new ArrayList<SortItem>();
        sortItems.add(sortItem);
        BIDataSet dataset = filterDs.sort(sortItems);
        return this.getMedian(percent, fieldNode.getName(), dataset);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node = parameters.get(1);
        if (!(node instanceof DSFieldNode)) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u9519\u8bef\uff0c\u8282\u70b9\u3010" + node.interpret(context, Language.FORMULA, null) + "\u3011\u4e0d\u662f\u4e00\u4e2a\u6570\u636e\u96c6\u5b57\u6bb5\u8282\u70b9");
        }
        DSFieldNode dsNode = (DSFieldNode)node;
        BIDataSetFieldInfo info = dsNode.getFieldInfo();
        if (info.getFieldType() != FieldType.MEASURE) {
            throw new SyntaxException("\u5b57\u6bb5" + info.getName() + "\u4e0d\u662f\u4e00\u4e2a\u5ea6\u91cf\u5b57\u6bb5");
        }
        return super.validate(context, parameters);
    }

    private double analysisPercent(IContext context, IASTNode node) throws SyntaxException {
        Double value = (Double)node.evaluate(context);
        if (value <= 0.0 || value >= 100.0) {
            throw new SyntaxException("DS_QUARTILE\u51fd\u6570\u4e2d\u7684\u5206\u4f4d\u767e\u5206\u6bd4\u503c\u5fc5\u987b\u5927\u4e8e0\u4e14\u5c0f\u4e8e100");
        }
        if (value >= 1.0) {
            return value / 100.0;
        }
        return value;
    }

    private Double getMedian(double percent, String fieldName, BIDataSet dataSet) throws SyntaxException {
        int recordCount = dataSet.getRecordCount();
        int colIndex = dataSet.getMetadata().indexOf(fieldName);
        if (recordCount == 0) {
            return null;
        }
        if (recordCount == 1) {
            return dataSet.get(0).getDouble(colIndex);
        }
        if (DataType.compare((double)percent, (double)1.0) == 0) {
            return dataSet.get(recordCount - 1).getDouble(colIndex);
        }
        double location = (double)(recordCount + 1) * percent;
        if (location <= 1.0) {
            return dataSet.get(0).getDouble(colIndex);
        }
        int leftLocation = (int)Math.floor(location);
        int rightLocation = (int)Math.ceil(location);
        BIDataRow leftRow = dataSet.get(leftLocation - 1);
        if (leftRow.wasNull(colIndex)) {
            return null;
        }
        double leftValue = leftRow.getDouble(colIndex);
        BIDataRow rightRow = dataSet.get(rightLocation - 1);
        if (rightRow.wasNull(colIndex)) {
            return null;
        }
        double rightValue = rightRow.getDouble(colIndex);
        return (leftValue + rightValue) / 2.0;
    }

    public String name() {
        return "DS_QUARTILE";
    }

    public String title() {
        return "\u5bf9\u6570\u636e\u96c6\u8fdb\u884c\u8fc7\u6ee4\uff0c\u6309\u7167\u5ea6\u91cf\u5b57\u6bb5\u8fdb\u884c\u5347\u5e8f\u6392\u5e8f\uff0c\u8ba1\u7b97\u5ea6\u91cf\u5b57\u6bb5\u5728\u6307\u5b9a\u5206\u4f4d\u5904\u5de6\u53f3\u4e24\u8fb9\u7684\u4e2d\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 3;
    }
}

