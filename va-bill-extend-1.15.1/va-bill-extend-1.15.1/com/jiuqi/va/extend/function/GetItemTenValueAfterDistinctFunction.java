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
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GetItemTenValueAfterDistinctFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public GetItemTenValueAfterDistinctFunction() {
        this.parameters().add(new Parameter("field", 0, "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("delimiter", 6, "\u5206\u9694\u7b26", true));
        this.parameters().add(new Parameter("top", 3, "\u524d\u51e0\u4e2a\u503c", true));
    }

    public String addDescribe() {
        return "\u51fd\u6570\u793a\u4f8b\uff1aGetItemTenValueAfterDistinct (TABLE[FIELD],\",\",5);\r\n\u8fd4\u56deTABLE\u5b50\u8868\u4e2dFIELD\u5b57\u6bb5\u53bb\u91cd\u4ee5\u540e\u7684\u524d5\u4e2a\u503c\uff0c\u7528,\u9694\u5f00\u3002\r\n\u5982\u679cdelimiter\u548ctop\u672a\u914d\u7f6e\u9ed8\u8ba4\u53d6\u524d10\u4e2a\u503c\u5e76\u4e14\u9017\u53f7\u9694\u5f00\u3002\r\n";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "GetItemTenValueAfterDistinct";
    }

    public String title() {
        return "\u83b7\u53d6\u5355\u636e\u5b50\u8868\u6307\u5b9a\u5b57\u6bb5\u53bb\u91cd\u540e\u7684\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
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
        buffer.append("    ").append("delemiter").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5206\u9694\u7b26").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("top").append("\uff1a").append(DataType.toString((int)3)).append("\uff1b\u53d6\u524d\u51e0\u4e2a\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u7533\u8bf7\u4e0e\u501f\u6b3e\u5b50\u8868\u3010FO_APPLOANBILL_ITEM\u3011\u90e8\u95e8\u7f16\u7801\u3010DEPTCODE\u3011\u53bb\u91cd\u540e\u7684\u524d\u4e94\u4e2a\u503c\uff0c\u5e76\u4e14\u7528\u9017\u53f7\u9694\u5f00").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetItemTenValueAfterDistinct (FO_APPLOANBILL_ITEM[ DEPTCODE], \",\",5)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("001001,001,002,003");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("field", DataType.toString((int)0), "\u5355\u636e\u5b50\u8868\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("delemiter", DataType.toString((int)6), "\u5206\u9694\u7b26\uff0c\u4e3a\u7a7a\u65f6\u4f7f\u7528\u82f1\u6587\u9017\u53f7", Boolean.valueOf(false));
        ParameterDescription parameterDescription2 = new ParameterDescription("top", DataType.toString((int)3), "\u53d6\u524d\u51e0\u4e2a\u503c\uff0c\u4e3a\u7a7a\u65f6\u53d6\u53bb\u91cd\u540e\u7684\u524d10\u4e2a\u503c", Boolean.valueOf(false));
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u6307\u5b9a\u5b50\u8868\u4e2d\u6307\u5b9a\u5b57\u6bb5\u53bb\u91cd\u540e\u7684\u503c", "GetItemTenValueAfterDistinct (FO_APPLOANBILL_ITEM[ DEPTCODE], \",\",5)", "001001,001,002,003", "\u83b7\u53d6\u7533\u8bf7\u4e0e\u501f\u6b3e\u5b50\u8868\uff08FO_APPLOANBILL_ITEM\uff09\u90e8\u95e8\u7f16\u7801\uff08DEPTCODE\uff09\u53bb\u91cd\u540e\u7684\u524d5\u4e2a\u503c\uff0c\u5e76\u4e14\u7528\u9017\u53f7\u9694\u5f00\u3002");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        if (!parameters.isEmpty()) {
            String delimiter = ",";
            int top = 10;
            if (parameters.size() > 1) {
                delimiter = (String)parameters.get(1).evaluate((IContext)cxt);
            }
            if (parameters.size() > 2) {
                top = ((Double)parameters.get(2).evaluate((IContext)cxt)).intValue();
            }
            TableFieldNode node = (TableFieldNode)parameters.get(0);
            String tableName = node.getTableName();
            String fieldName = node.getFieldName().toUpperCase();
            List<Object> list = new ArrayList();
            BillModelImpl model = (BillModelImpl)cxt.model;
            DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
            if (detailTable.getRows() != null && detailTable.getRows().size() > 0) {
                ListContainer dataRowList = detailTable.getRows();
                for (int j = 0; j < dataRowList.size(); ++j) {
                    Object value = ((DataRowImpl)dataRowList.get(j)).getValue(fieldName);
                    if (value == null) continue;
                    list.add(value);
                }
                list = list.stream().distinct().collect(Collectors.toList());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < list.size() && i < top; ++i) {
                    sb.append(list.get(i).toString() + delimiter);
                }
                if (sb.length() > 0) {
                    return sb.substring(0, sb.length() - delimiter.length());
                }
            }
        }
        return null;
    }
}

