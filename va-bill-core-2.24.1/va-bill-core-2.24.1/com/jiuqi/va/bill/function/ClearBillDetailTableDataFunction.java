/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
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
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ClearBillDetailTableDataFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;

    public ClearBillDetailTableDataFunction() {
        this.parameters().add(new Parameter("detailTable", 6, "\u6e05\u9664\u6570\u636e\u5b50\u8868\u6807\u8bc6", false));
        this.parameters().add(new Parameter("condition", 1, "\u6761\u4ef6", true));
    }

    public String addDescribe() {
        return "\u6e05\u9664\u5355\u636e\u5b50\u8868\u6570\u636e";
    }

    public String name() {
        return "ClearBillDetailTableData";
    }

    public String title() {
        return "\u6e05\u9664\u5b50\u8868\u516c\u5f0f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String table = (String)parameters.get(0).evaluate(context);
        if (!StringUtils.hasText(table)) {
            return false;
        }
        String tableName = table.toUpperCase();
        ModelDataContext cxt = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)cxt.model;
        DataTableImpl detailTable = (DataTableImpl)model.getData().getTables().get(tableName);
        List rows = detailTable.getRowsData(false);
        if (rows.size() == 0) {
            return true;
        }
        if (parameters.size() == 2) {
            IASTNode conditionNode = parameters.get(1);
            ArrayList deleteRowIds = new ArrayList();
            for (Map row : rows) {
                ModelDataContext tmpDataContext = new ModelDataContext((Model)model);
                tmpDataContext.put("model_param_node_tablename", (Object)tableName);
                for (Map.Entry entry : row.entrySet()) {
                    tmpDataContext.put((String)entry.getKey(), entry.getValue());
                }
                if (!((Boolean)Convert.cast((Object)conditionNode.evaluate((IContext)tmpDataContext), Boolean.class)).booleanValue()) continue;
                deleteRowIds.add(row.get("ID"));
            }
            if (deleteRowIds.size() > 0) {
                for (Object rowId : deleteRowIds) {
                    detailTable.deleteRowById(rowId);
                }
            }
        } else {
            detailTable.deleteRow(0, detailTable.getRows().size());
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
        buffer.append("    ").append("detailTable\uff1a").append(DataType.toString((int)6)).append("\uff1b\u6e05\u9664\u6570\u636e\u5b50\u8868\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("condition\uff1a").append(DataType.toString((int)1)).append("\uff1b\u6761\u4ef6\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u5220\u9664\u5b50\u8868\u6240\u6709\u884c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append("\u6e05\u9664\u6210\u529f\u8fd4\u56detrue\uff0c\u6e05\u9664\u5931\u8d25\u8fd4\u56deflase").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5355\u636e\u5b50\u8868BILLMONEY\u5927\u4e8e0\u65f6\uff0c\u5220\u9664\u8be5\u5b50\u8868\u884c\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ClearBillDetailTableData(\"FO_APARBILL_ITEM\", [BILLMONEY] > 0)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1) + "\uff1b\u6e05\u9664\u6210\u529f\u8fd4\u56detrue\uff0c\u6e05\u9664\u5931\u8d25\u8fd4\u56deflase");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("detailTable");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u6e05\u9664\u6570\u636e\u5b50\u8868\u6807\u8bc6");
        parameterDescription.setRequired(Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("condition");
        parameterDescription1.setType(DataType.toString((int)1));
        parameterDescription1.setDescription("\u6761\u4ef6\uff0c\u53ef\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65f6\u5220\u9664\u5b50\u8868\u6240\u6709\u884c");
        parameterDescription1.setRequired(Boolean.valueOf(false));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u5355\u636e\u5b50\u8868BILLMONEY\u5927\u4e8e0\u65f6\uff0c\u5220\u9664\u8be5\u5b50\u8868\u884c\u3002");
        formulaExample.setFormula("ClearBillDetailTableData(\"FO_APARBILL_ITEM\", [BILLMONEY] > 0)");
        formulaExample.setReturnValue("true");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

