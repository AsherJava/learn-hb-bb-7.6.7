/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.ruler.ModelNode
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetRowNumFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public GetRowNumFunction() {
        this.parameters().add(new Parameter("id", 0, "\u5b50\u8868\u884cID\u5b57\u6bb5", false));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u5b50\u8868\u884c\u5e8f\u53f7";
    }

    public String name() {
        return "GetRowNum";
    }

    public String title() {
        return "\u83b7\u53d6\u5b50\u8868\u884c\u5e8f\u53f7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)cxt.model;
        IASTNode node0 = parameters.get(0);
        if (!(node0 instanceof ModelNode)) {
            throw new SyntaxException("\u53c2\u6570\u9519\u8bef\uff0c\u5fc5\u987b\u4e3aTABLE[ID]\u683c\u5f0f");
        }
        ModelNode modelNode = (ModelNode)node0;
        String fieldName = modelNode.getFieldName();
        if (!"ID".equals(fieldName)) {
            throw new SyntaxException("\u53c2\u6570\u9519\u8bef\uff0c\u5fc5\u987b\u4e3aTABLE[ID]\u683c\u5f0f");
        }
        String tableName = modelNode.getTableName();
        Object rowID = node0.evaluate(context);
        DataTable table = model.getTable(tableName);
        int index = table.getRows().findIndex(row -> rowID.equals(row.getId()));
        if (index < 0) {
            return null;
        }
        return index + 1;
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)3) + "\uff1a" + DataType.toString((int)3));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("id", DataType.toString((int)0), "\u5b50\u8868\u884cID\u5b57\u6bb5", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u62a5\u9500\u660e\u7ec6\u8868\u7684\u884c\u5e8f\u53f7\u3002", "GetRowNum(FO_EXPENSEBILL_LOANITEM[ID])", DataType.toString((int)3));
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

