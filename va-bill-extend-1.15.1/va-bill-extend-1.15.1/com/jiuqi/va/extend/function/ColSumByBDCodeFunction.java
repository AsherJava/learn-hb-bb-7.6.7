/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataRowState
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
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRowState;
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
import org.springframework.util.StringUtils;

@Component
public class ColSumByBDCodeFunction
extends ModelFunction
implements AggregatedNode {
    private static final long serialVersionUID = 1L;

    public ColSumByBDCodeFunction() {
        this.parameters().add(new Parameter("value", 0, "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("value", 0, "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("value", 6, "\u5b57\u7b26\u4e32/\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
    }

    public String addDescribe() {
        return "\u51fd\u6570\u793a\u4f8b\uff1aColSumByBDCode(TABLE[FIELD1],TABLE[FIELD2],\"0101\") \n   \u8868\u793a\u5f53TABLE\u8868\u7684FIELD2\u5b57\u6bb5\u662f0101\u5f00\u5934\u65f6\uff0c\u6c42FIELD1\u5b57\u6bb5\u7684\u6570\u636e\u4e4b\u548c\uff0c\u8fd4\u56de\u7c7b\u578b\u4e3aFIELD1\u5b57\u6bb5\u7684\u7c7b\u578b";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "ColSumByBDCode";
    }

    public String title() {
        return "\u6839\u636e\u5b50\u8868\u5b57\u6bb5\u503c\uff0c\u5bf9\u53e6\u4e00\u5217\u6570\u636e\u6c42\u548c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public boolean isInfiniteParameter() {
        return true;
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5355\u636e\u5b50\u8868\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5b57\u7b26\u4e32/\u5355\u636e\u5b50\u8868\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u90e8\u95e8\u4ee5\"002\"\u6216\"003\"\u5f00\u5934\u65f6\uff0c\u5bf9\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u91d1\u989d\u5b57\u6bb5\u6c42\u548c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ColSumByBDCode(FO_EXPENSEBILL_ITEM[BILLMONEY],FO_EXPENSEBILL_ITEM[DEPTCODE],\"002\",\"003\")").append(FunctionUtils.LINE_SEPARATOR);
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
        ParameterDescription parameterDescription1 = new ParameterDescription("value", DataType.toString((int)0), " \u5355\u636e\u5b50\u8868\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("value", DataType.toString((int)6), " \u5b57\u7b26\u4e32/\u5355\u636e\u5b50\u8868\u5b57\u6bb5", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u90e8\u95e8\u4ee5\"002\"\u6216\"003\"\u5f00\u5934\u65f6\uff0c\u5bf9\u62a5\u9500\u5355\u660e\u7ec6\u5b50\u8868\u91d1\u989d\u5b57\u6bb5\u6c42\u548c", "ColSumByBDCode(FO_EXPENSEBILL_ITEM[BILLMONEY],FO_EXPENSEBILL_ITEM[DEPTCODE],\"002\",\"003\")", "300");
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
        int size = parameters.size();
        switch (nodeDataType) {
            case 3: 
            case 10: {
                break;
            }
            default: {
                throw new SyntaxException(parameters.get(0).getToken(), BillExtend18nUtil.getMessage("va.billextend.param.type.error"));
            }
        }
        ArrayList<String> values = new ArrayList<String>();
        for (int i = 2; i < size; ++i) {
            values.add(parameters.get(i).evaluate((IContext)cxt).toString());
        }
        BillModelImpl model = (BillModelImpl)cxt.model;
        DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
        if (detailTable.getRows() != null && detailTable.getRows().size() > 0) {
            ListContainer list = detailTable.getRows();
            BigDecimal sum = new BigDecimal(0.0);
            for (int i = 0; i < list.size(); ++i) {
                boolean flag;
                if (DataRowState.UNUSED.equals((Object)((DataRowImpl)list.get(i)).getState()) || DataRowState.DELETED.equals((Object)((DataRowImpl)list.get(i)).getState()) || !(flag = this.handle(cxt, parameters, values, (ListContainer<DataRowImpl>)list, i))) continue;
                sum = sum.add(((DataRowImpl)list.get(i)).getValue(fieldName, BigDecimal.class) == null ? new BigDecimal(0.0) : (BigDecimal)((DataRowImpl)list.get(i)).getValue(fieldName, BigDecimal.class));
            }
            return sum;
        }
        return BigDecimal.ZERO;
    }

    boolean handle(ModelDataContext cxt, List<IASTNode> parameters, List<String> values, ListContainer<DataRowImpl> list, int i) {
        if (values == null || values.size() == 0) {
            return false;
        }
        TableFieldNode node1 = (TableFieldNode)parameters.get(1);
        String fieldName = node1.getFieldName().toUpperCase();
        String fieldValue = ((DataRowImpl)list.get(i)).getString(fieldName);
        boolean flag = false;
        for (String value : values) {
            if (!StringUtils.hasText(value) || fieldValue == null || !fieldValue.toString().startsWith(value)) continue;
            flag = true;
            break;
        }
        return flag;
    }
}

