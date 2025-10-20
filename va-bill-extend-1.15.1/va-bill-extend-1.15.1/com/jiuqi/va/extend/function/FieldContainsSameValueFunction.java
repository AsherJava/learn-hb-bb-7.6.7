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
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
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
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FieldContainsSameValueFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public FieldContainsSameValueFunction() {
        this.parameters().add(new Parameter("field", 0, "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
    }

    public String addDescribe() {
        return "\u68c0\u67e5\u5355\u636e\u8868\u4e2d\u5b57\u6bb5\u662f\u5426\u5b58\u5728\u91cd\u590d\u7684\u503c\uff08\u4ec5\u6821\u9a8c\u5f53\u524d\u6a21\u578b\u6570\u636e\uff09\uff0c\u5b58\u5728\u65f6\u8fd4\u56detrue\uff0c\u5426\u5219\u8fd4\u56defalse\u3002\u7531\u4e8e\u4e3b\u8868\u53ea\u6709\u4e00\u6761\u6570\u636e\uff0c\u9ed8\u8ba4\u8fd4\u56defalse";
    }

    public boolean enableDebug() {
        return true;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return "FieldContainsSameValue";
    }

    public String title() {
        return "\u68c0\u67e5\u5b57\u6bb5\u662f\u5426\u6709\u91cd\u590d\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
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
        buffer.append("    ").append("field").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5355\u636e\u5b50\u8868\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u68c0\u67e5\u6536\u6b3e\u4fe1\u606f\u8868\u5b50\u8868\u90e8\u95e8\u7f16\u7801\u3010DEPTCODE\u3011\u3001\u3010PAYEECODE\u3011\u662f\u5426\u6709\u91cd\u590d\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("FieldContainsSameValue(FO_PAYACCOUNT[DEPTCODE],FO_PAYACCOUNT[PAYEECODE])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("field", DataType.toString((int)0), "\u5355\u636e\u5b57\u6bb5", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u68c0\u67e5\u6536\u6b3e\u4fe1\u606f\u8868\u5b50\u8868\u90e8\u95e8\u3001\u804c\u5458\u662f\u5426\u6709\u91cd\u590d\u503c", "!FieldContainsSameValue(FO_PAYACCOUNT[DEPTCODE],FO_PAYACCOUNT[PAYEECODE])", "\u6536\u6b3e\u4fe1\u606f\u5b50\u8868\u7b2cn\u884c\u90e8\u95e8\u3001\u804c\u5458\u4e0d\u80fd\u91cd\u590d", "\u5f53\u524d\u5355\u636e\uff0c\u6536\u6b3e\u4fe1\u606f\u8868\uff08FO_PAYACCOUNT\uff09\u7684\u591a\u884c\u6570\u636e\u4e2d\uff0c\u90e8\u95e8\uff08DEPTCODE\uff09\u3001\u804c\u5458\uff08PAYEECODE\uff09\u5b58\u5728\u91cd\u590d\u6570\u636e\u65f6\uff0c\u63d0\u793a\u201c\u6536\u6b3e\u4fe1\u606f\u5b50\u8868\u7b2cn\u884c\u90e8\u95e8\u3001\u804c\u5458\u4e0d\u80fd\u91cd\u590d\u201d\u3002");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        ArrayList<String> fieldNameList = new ArrayList<String>();
        String tableName = null;
        for (int i = 0; i < parameters.size(); ++i) {
            TableFieldNode node = (TableFieldNode)parameters.get(i);
            tableName = node.getTableName();
            String fieldName = node.getFieldName().toUpperCase();
            fieldNameList.add(fieldName);
        }
        BillModelImpl model = (BillModelImpl)cxt.model;
        DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
        if (detailTable.getRows() == null || detailTable.getRows().size() == 0) {
            return true;
        }
        DataRowImpl currentrow = (DataRowImpl)cxt.get(tableName);
        Object currentID = currentrow.getId();
        String currentValue = "";
        ArrayList<String> list = new ArrayList<String>();
        ListContainer dataRowList = detailTable.getRows();
        for (int j = 0; j < dataRowList.size(); ++j) {
            Object ID;
            StringBuilder builder = new StringBuilder();
            for (String fieldName : fieldNameList) {
                Object temp = ((DataRowImpl)dataRowList.get(j)).getValue(fieldName);
                if (temp == null) continue;
                builder.append(temp);
            }
            String value = "";
            if (!builder.toString().isEmpty()) {
                value = builder.toString();
            }
            if (Objects.equals(currentID, ID = ((DataRowImpl)dataRowList.get(j)).getId())) {
                currentValue = value;
            }
            list.add(value);
        }
        Map countMap = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Long num = countMap.get(currentValue);
        return num != 1L;
    }
}

