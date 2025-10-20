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
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
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
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ClearBillTableDataFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;
    private static final Logger log = LoggerFactory.getLogger(ClearBillTableDataFunction.class);

    public ClearBillTableDataFunction() {
        this.parameters().add(new Parameter("table", 6, "\u8868\u6807\u8bc6", false));
        this.parameters().add(new Parameter("field", 6, "\u5b57\u6bb5\u6807\u8bc6", false));
    }

    public String addDescribe() {
        return "\u6e05\u7a7a\u6307\u5b9a\u5b57\u6bb5";
    }

    public String name() {
        return "ClearBillTableFields";
    }

    public String title() {
        return "\u6e05\u7a7a\u6307\u5b9a\u5b57\u6bb5";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String table = (String)parameters.get(0).evaluate(context);
        if (!StringUtils.hasText(table)) {
            return false;
        }
        ModelDataContext ctx = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)ctx.model;
        DataTable dataTable = model.getTable(table.toUpperCase());
        if (dataTable == null) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.cleartablefunction.tablenotexist", new Object[]{table.toUpperCase()}));
        }
        Map ctxParams = ctx.getParams();
        if (ctxParams == null || ctxParams.get(table.toUpperCase()) == null) {
            ListContainer rows = dataTable.getRows();
            rows.stream().forEach(o -> {
                for (int i = 1; i < parameters.size(); ++i) {
                    try {
                        o.setValue(String.valueOf(((IASTNode)parameters.get(i)).evaluate(context)).toUpperCase(), null);
                        continue;
                    }
                    catch (SyntaxException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            });
        } else {
            Object object = ctxParams.get(table.toUpperCase());
            if (object instanceof DataRow) {
                DataRowImpl dataRow = (DataRowImpl)object;
                for (int i = 1; i < parameters.size(); ++i) {
                    dataRow.setValue(String.valueOf(parameters.get(i).evaluate(context)).toUpperCase(), null);
                }
            }
        }
        return true;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("table\uff1a").append(DataType.toString((int)6)).append("\uff1b\u8868\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("field\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5b57\u6bb5\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append("\u6e05\u7a7a\u6210\u529f\u8fd4\u56detrue\uff0c\u6e05\u7a7a\u5931\u8d25\u8fd4\u56defalse").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6e05\u7a7a\u5355\u636e\u5b50\u8868\u804c\u5458\u3001\u90e8\u95e8\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ClearBillTableFields(\"FO_APARBILL_ITEM\", \"STAFFCODE\", \"DEPTCODE\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1) + "\uff1b\u6e05\u7a7a\u6210\u529f\u8fd4\u56detrue\uff0c\u6e05\u7a7a\u5931\u8d25\u8fd4\u56defalse");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("table", DataType.toString((int)6), "\u8868\u6807\u8bc6", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("field", DataType.toString((int)6), "\u5b57\u6bb5\u6807\u8bc6", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u6e05\u7a7a\u5355\u636e\u5b50\u8868\u804c\u5458\u3001\u90e8\u95e8\u3002 ", "ClearBillTableFields(\"FO_APARBILL_ITEM\", \"STAFFCODE\", \"DEPTCODE\")", "true");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

