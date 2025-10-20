/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.AggregatedNode
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.formula.intf.TableFieldNode
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.AggregatedNode;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ColSumByConditionFunction
extends ModelFunction
implements AggregatedNode {
    private static final long serialVersionUID = 1L;

    public ColSumByConditionFunction() {
        this.parameters().add(new Parameter("value", 0, "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("value", 1, "\u6761\u4ef6\u8868\u8fbe\u5f0f", false));
    }

    public String addDescribe() {
        return "\u6839\u636e\u6761\u4ef6\u6ee1\u8db3\u65f6\u5bf9\u5355\u636e\u5b50\u8868\u5b57\u6bb5\u8fdb\u884c\u6c42\u548c\u8fd0\u7b97";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "ColSumByCondition";
    }

    public String title() {
        return "\u6839\u636e\u6761\u4ef6\u6c42\u548c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    public void toDeclaration(StringBuilder buffer) {
        try {
            int retType = this.getResultType(null, null);
            buffer.append(DataType.toExpression((int)retType)).append(' ').append(this.name()).append('(');
        }
        catch (SyntaxException e) {
            throw new SyntaxRuntimeException((Throwable)e);
        }
        this.printParamDeclaration(buffer);
        buffer.append(");");
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5355\u636e\u5b50\u8868\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)1)).append("\uff1b\u6761\u4ef6\u8868\u8fbe\u5f0f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u90e8\u95e8=\"002\"\u65f6\uff0c\u5bf9\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u91d1\u989d\u5b57\u6bb5\u6c42\u548c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ColSumByCondition(FO_EXPENSEBILL_ITEM[BILLMONEY],FO_EXPENSEBILL_ITEM[DEPTCODE]=\"002\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("300");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("value", DataType.toString((int)0), "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("value", DataType.toString((int)1), " \u6761\u4ef6\u8868\u8fbe\u5f0f", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u90e8\u95e8=\"002\"\u65f6\uff0c\u5bf9\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u91d1\u989d\u5b57\u6bb5\u6c42\u548c", "ColSumByCondition(FO_EXPENSEBILL_ITEM[BILLMONEY],FO_EXPENSEBILL_ITEM[DEPTCODE]=\"002\")", "300");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        TableFieldNode node = (TableFieldNode)parameters.get(0);
        String tableName = node.getTableName();
        String fieldName = node.getFieldName().toUpperCase();
        int nodeDataType = parameters.get(0).getType(context);
        switch (nodeDataType) {
            case 3: 
            case 10: {
                break;
            }
            default: {
                throw new SyntaxException(parameters.get(0).getToken(), BillExtend18nUtil.getMessage("va.billextend.param.type.error"));
            }
        }
        BillModelImpl model = (BillModelImpl)cxt.model;
        DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
        if (detailTable.getRows() != null && detailTable.getRows().size() > 0) {
            ListContainer list = detailTable.getRows();
            BigDecimal sum = new BigDecimal(0.0);
            for (int i = 0; i < list.size(); ++i) {
                boolean flag = this.handle(cxt, parameters, (ListContainer<DataRowImpl>)list, i);
                if (!flag) continue;
                BigDecimal value = (BigDecimal)((DataRowImpl)list.get(i)).getValue(fieldName, BigDecimal.class);
                sum = sum.add(value == null ? BigDecimal.ZERO : value);
            }
            return sum;
        }
        return BigDecimal.ZERO;
    }

    boolean handle(ModelDataContext cxt, List<IASTNode> parameters, ListContainer<DataRowImpl> list, int i) {
        Object node0Data;
        TableFieldNode node = (TableFieldNode)parameters.get(0);
        String tableName = node.getTableName();
        cxt.put(tableName, list.get(i));
        try {
            node0Data = parameters.get(1).evaluate((IContext)cxt);
        }
        catch (SyntaxException e) {
            node0Data = null;
        }
        if (node0Data instanceof Boolean) {
            return (Boolean)node0Data;
        }
        if (node0Data instanceof ArrayData) {
            ArrayData arrayData = (ArrayData)node0Data;
            if (arrayData.get(i) instanceof Boolean) {
                return (Boolean)arrayData.get(i);
            }
            return false;
        }
        return true;
    }
}

