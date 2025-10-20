/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.ModelNode
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetRowDataByIndex
extends ModelFunction
implements AggregatedNode {
    private static final long serialVersionUID = 1L;

    public GetRowDataByIndex() {
        this.parameters().add(new Parameter("dfieldName", 0, "\u6307\u5b9a\u5b50\u8868\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("index", 3, "\u4f5c\u4e3a\u5f00\u5934\u7684\u5b57\u7b26\u4e32", false));
    }

    public String addDescribe() {
        return "\u901a\u8fc7\u7ed9\u5b9a\u7684\u884c\u53f7\uff0c\u83b7\u53d6\u5bf9\u5e94\u884c\u7684\u6307\u5b9a\u5b57\u6bb5\u7684\u6570\u636e";
    }

    public String name() {
        return "GetRowDataByIndex";
    }

    public String title() {
        return "\u6839\u636e\u884c\u4e0b\u6807\u83b7\u53d6\u884c\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)cxt.model;
        ModelNode node0 = (ModelNode)parameters.get(0);
        int node0DataType = node0.getType(context);
        int rowIndex = (int)Double.valueOf(parameters.get(1).evaluate(context).toString()).doubleValue();
        DataTableImpl rowData = (DataTableImpl)model.getData().getTables().find(node0.getTableName());
        ListContainer rowsData = rowData.getRows();
        if (rowsData.size() == 0) {
            return cxt.valueOf(null, node0DataType);
        }
        if (rowIndex <= 0) {
            return cxt.valueOf(null, node0DataType);
        }
        if (rowsData.size() < rowIndex) {
            return cxt.valueOf(null, node0DataType);
        }
        DataRowImpl dataRowImpl = (DataRowImpl)rowsData.get(rowIndex - 1);
        Object value = dataRowImpl.getValue(node0.getFieldName());
        if (value == null) {
            return null;
        }
        return cxt.valueOf(value, node0DataType);
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("dfieldName").append("\uff1a").append(DataType.toString((int)3)).append("\uff1b\u53c2\u6570\u503c\uff0c\u6307\u5b9a\u8981\u83b7\u53d6\u7684\u8868\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("index").append("\uff1a").append(DataType.toString((int)3)).append("\uff1b\u6307\u5b9a\u884c\u53f7\uff0c\u6700\u5c0f\u503c\u4e3a1\uff0c\u884c\u53f7\u8d85\u51fa\u5b50\u8868\u884c\u8303\u56f4\u65f6\u8fd4\u56denull\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8be5\u51fd\u6570\u7684\u8fd4\u56de\u503c\u7c7b\u578b\u4e0e\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u5b57\u6bb5\u7684\u7c7b\u578b\u4fdd\u6301\u4e00\u81f4\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u62a5\u9500\u660e\u7ec6\u8868\u4e2d\u7b2c3\u884c\u7684\u62a5\u9500\u4eba\u3002 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetRowDataByIndex(FO_EXPENSEBILL_LOANITEM[STAFFCODE],3)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toString((int)0));
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0) + "\uff1b\u8be5\u51fd\u6570\u7684\u8fd4\u56de\u503c\u7c7b\u578b\u4e0e\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u5b57\u6bb5\u7684\u7c7b\u578b\u4fdd\u6301\u4e00\u81f4\u3002");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("dfieldName", DataType.toString((int)0), "\u53c2\u6570\u503c\uff0c\u6307\u5b9a\u8981\u83b7\u53d6\u7684\u8868\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("index", DataType.toString((int)3), "\u6307\u5b9a\u884c\u53f7\uff0c\u6700\u5c0f\u503c\u4e3a1\uff0c\u884c\u53f7\u8d85\u51fa\u5b50\u8868\u884c\u8303\u56f4\u65f6\u8fd4\u56denull\u3002", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u62a5\u9500\u660e\u7ec6\u8868\u4e2d\u7b2c3\u884c\u7684\u62a5\u9500\u4eba\u3002", "GetRowDataByIndex(FO_EXPENSEBILL_LOANITEM[STAFFCODE],3)", DataType.toString((int)0));
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

